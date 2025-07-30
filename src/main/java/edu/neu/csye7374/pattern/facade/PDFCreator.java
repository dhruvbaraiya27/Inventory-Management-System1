package com.inventory.designpattern.facade;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.inventory.model.Invoice;
import com.inventory.model.ProductPO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PDFCreator {
	
	private Invoice invoice;
	
	public void generatePDF(Invoice invoice) {
		this.invoice = invoice;
		Document doc = new Document();
		try {
			String filename = "Invoice_" + invoice.getId() + "_" + invoice.getPurchaseOrder().getBuyer().getCompanyName() + ".pdf";
			PdfWriter.getInstance(doc, new FileOutputStream(filename));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		doc.open();
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, BaseColor.BLACK);
		Font font2 = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
		Paragraph p1 = new Paragraph("!nventoria Invoice for your Purchase", font1);
		Paragraph p2 = new Paragraph("Payment Date: " + invoice.getPaymentDate(), font2);
		
		PdfPTable table = new PdfPTable(4);
		addHeader(table);
		addRows(table);
		try {
			doc.add(p1);
			doc.add(p2);
			doc.add( Chunk.NEWLINE );
			doc.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		doc.close();
	}
	
	private void addHeader(PdfPTable table) {
		Stream.of("Product", "Quantity", "Unit Price", "Total Price").forEach(title -> {
			PdfPCell cell = new PdfPCell();
			cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cell.setBorderWidth(2);
			cell.setPhrase(new Phrase(title));
			table.addCell(cell);
		});
	}

	private void addRows(PdfPTable table) {
		
		List<ProductPO> products = invoice.getPurchaseOrder().getProducts();

		List<ProductPO> items = products.stream().distinct().collect(Collectors.toList());
		for(ProductPO item : items) {
			table.addCell(item.getProduct().getProductName());
			table.addCell(String.valueOf(item.getQuantity()));
			table.addCell("$" + item.getProduct().getPrice());
			double total = item.getProduct().getPrice() * item.getQuantity();
			table.addCell("$" + total);
		}
		
		table.addCell("Total");
		table.addCell("---");
		table.addCell("---");
		table.addCell("$" + invoice.getPurchaseOrder().getTotalAmount());
	}
}