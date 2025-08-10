package edu.neu.csye7374.designpattern.facade;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.neu.csye7374.model.Invoice;
import edu.neu.csye7374.model.ProductPO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class createPDF {
	
	private Invoice invoice;
	
	public String generatePDF(Invoice invoice) {
		this.invoice = invoice;
		Document document = new Document();
		String filename = null;
		try {
			filename = "Invoice_" + invoice.getId() + "_" + invoice.getPurchaseOrder().getBuyer().getCompanyName() + ".pdf";
			PdfWriter.getInstance(document, new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		document.open();
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
		Paragraph p1 = new Paragraph("!nventoria Invoice for your Purchase", font1);
		Paragraph p2 = new Paragraph("Payment Date: " + invoice.getPaymentDate(), font2);
		
		PdfPTable table = new PdfPTable(4);
		addTableHeader(table);
		addRows(table);
		try {
			document.add(p1);
			document.add(p2);
			document.add( Chunk.NEWLINE );
			document.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		document.close();
		return filename;
	}
	
	private void addTableHeader(PdfPTable table) {
		Stream.of("Product", "Quantity", "Unit Price", "Total Price").forEach(columnTitle -> {
			PdfPCell header = new PdfPCell();
			header.setBackgroundColor(BaseColor.LIGHT_GRAY);
			header.setBorderWidth(2);
			header.setPhrase(new Phrase(columnTitle));
			table.addCell(header);
		});
	}

	private void addRows(PdfPTable table) {
		try {
			List<ProductPO> productPOs = invoice.getPurchaseOrder().getProducts();
			
			System.out.println("DEBUG: Number of products in invoice: " + (productPOs != null ? productPOs.size() : "null"));
			
			if (productPOs != null && !productPOs.isEmpty()) {
				List<ProductPO> distinctProductList = productPOs.stream().distinct().collect(Collectors.toList());
				
				for(ProductPO productPO : distinctProductList) {
					if (productPO != null && productPO.getProduct() != null) {
						table.addCell(productPO.getProduct().getProductName());
						table.addCell(String.valueOf(productPO.getQuantity()));
						table.addCell("$" + String.format("%.2f", productPO.getProduct().getPrice()));
						double result = productPO.getProduct().getPrice() * productPO.getQuantity();
						table.addCell("$" + String.format("%.2f", result));
					}
				}
			} else {
				// Add a row indicating no products found
				table.addCell("No products found");
				table.addCell("0");
				table.addCell("$0.00");
				table.addCell("$0.00");
			}
			
			// Add total row
			table.addCell("Total");
			table.addCell("---");
			table.addCell("---");
			double totalAmount = invoice.getPurchaseOrder().getTotalAmount();
			table.addCell("$" + String.format("%.2f", totalAmount));
			
		} catch (Exception e) {
			System.out.println("ERROR in addRows: " + e.getMessage());
			e.printStackTrace();
			
			// Add error row
			table.addCell("Error loading products");
			table.addCell("--");
			table.addCell("--");
			table.addCell("--");
		}
	}
}
