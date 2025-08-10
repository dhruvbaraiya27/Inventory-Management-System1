package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Invoice;
import edu.neu.csye7374.repository.InvoiceRepository;
import edu.neu.csye7374.repository.OrderRepository;
import edu.neu.csye7374.designpattern.strategy.InventoryStrategy;
import edu.neu.csye7374.designpattern.strategy.InvoiceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;

/**
 * @apiNote - REST Controller for Invoice
 */
@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/invoice")
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepo;

    @Autowired
    private OrderRepository purchaseOrderRepo;

    @GetMapping("/getAll")
    public List<Invoice> getAll() {
        return invoiceRepo.getInvoices();
    }

    @GetMapping("/getInvoice/{id}")
    public Invoice getInvoice(@PathVariable int id) {
        return invoiceRepo.getInvoicebyID(id);
    }

    @PostMapping("/generateInvoice/{id}")
    public void save(@PathVariable int id) {

        InventoryStrategy strategy = new InventoryStrategy(new InvoiceStrategy(invoiceRepo, id, purchaseOrderRepo));
        strategy.executeAdd();
    }

    @PutMapping("/update")
    public void update(@RequestBody Invoice invoice) {
        InventoryStrategy strategy = new InventoryStrategy(new InvoiceStrategy(invoiceRepo, invoice));
        strategy.executeUpdate();
    }

    @DeleteMapping("/delete/{id}")
    public void deletebyID(@PathVariable int id) {
        InventoryStrategy strategy = new InventoryStrategy(new InvoiceStrategy(invoiceRepo, id));
        strategy.executeDelete();
    }
    
    @PostMapping("/cleanup")
    public String cleanupOrphanedInvoices() {
        try {
            invoiceRepo.cleanupOrphanedInvoices();
            return "Orphaned invoice records cleaned up successfully";
        } catch (Exception e) {
            return "Error cleaning up orphaned records: " + e.getMessage();
        }
    }
    
    @GetMapping("/hasPdf/{id}")
    public ResponseEntity<Boolean> checkIfPdfExists(@PathVariable int id) {
        try {
            Invoice invoice = invoiceRepo.getInvoicebyID(id);
            if (invoice == null || invoice.getPurchaseOrder() == null) {
                return ResponseEntity.ok(false);
            }
            
            // Use stored PDF file path or generate expected filename
            String filename;
            if (invoice.getPdfFilePath() != null && !invoice.getPdfFilePath().isEmpty()) {
                filename = invoice.getPdfFilePath();
            } else {
                filename = "Invoice_" + invoice.getId() + "_" + 
                          invoice.getPurchaseOrder().getBuyer().getCompanyName() + ".pdf";
            }
            
            // Check if the file exists
            File pdfFile = new File(filename);
            return ResponseEntity.ok(pdfFile.exists());
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(false);
        }
    }

    @GetMapping("/viewPdf/{id}")
    public ResponseEntity<Resource> viewInvoicePdf(@PathVariable int id) {
        try {
            // Get the invoice
            Invoice invoice = invoiceRepo.getInvoicebyID(id);
            if (invoice == null || invoice.getPurchaseOrder() == null) {
                return ResponseEntity.notFound().build();
            }
            
            // Use stored PDF file path or generate expected filename
            String filename;
            if (invoice.getPdfFilePath() != null && !invoice.getPdfFilePath().isEmpty()) {
                filename = invoice.getPdfFilePath();
            } else {
                filename = "Invoice_" + invoice.getId() + "_" + 
                          invoice.getPurchaseOrder().getBuyer().getCompanyName() + ".pdf";
            }
            
            // Check if the file exists
            File pdfFile = new File(filename);
            if (!pdfFile.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
            }
            
            // Create resource from file
            Resource resource = new FileSystemResource(pdfFile);
            
            // Set headers to display PDF inline in browser
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("inline", filename);
            headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
            
            return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfFile.length())
                .body(resource);
                
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
