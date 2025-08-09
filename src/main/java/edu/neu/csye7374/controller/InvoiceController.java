package edu.neu.csye7374.controller;

import edu.neu.csye7374.model.Invoice;
import edu.neu.csye7374.repository.InvoiceRepository;
import edu.neu.csye7374.repository.OrderRepository;
import edu.neu.csye7374.designpattern.strategy.InventoryStrategy;
import edu.neu.csye7374.designpattern.strategy.InvoiceStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @apiNote - REST Controller for Invoice
 */
@RestController
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
}
