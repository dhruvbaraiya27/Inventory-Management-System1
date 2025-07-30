package edu.neu.csye7374.pattern.facade;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import edu.neu.csye7374.model.ItemPurchaseOrder;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PDFCreator {
	
	private ItemPurchaseOrder purchaseOrder;
	
	public void generatePDF(ItemPurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
		Document doc = new Document();
		try {
			String filename = "Invoice_" + purchaseOrder.getItemPurchaseOrderId() + "_" + "Company.pdf";
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
		Paragraph p2 = new Paragraph("Payment Date: " + new java.util.Date(), font2);
		
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
		
		// For now, add sample data since we don't have the exact structure
		table.addCell("Sample Product");
		table.addCell("1");
		table.addCell("$10.00");
		table.addCell("$10.00");
		
		table.addCell("Total");
		table.addCell("---");
		table.addCell("---");
		table.addCell("$" + (purchaseOrder.getItemPurchaseOrderProduct() != null ? purchaseOrder.getItemPurchaseOrderProduct().getItemPrice() * purchaseOrder.getItemPurchaseOrderQuantity() : 0.0));
	}
}