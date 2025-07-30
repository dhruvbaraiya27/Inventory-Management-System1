package edu.neu.csye7374.pattern.facade;

import edu.neu.csye7374.model.ItemPurchaseOrder;

public class PDFService extends ServiceFacade{

	@Override
	protected void sendMsg(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createPDF(int id, Object repo) {
		// For now, create a sample purchase order
		ItemPurchaseOrder purchaseOrder = new ItemPurchaseOrder();
		purchaseOrder.setItemPurchaseOrderId(id);
		PDFCreator pdf = new PDFCreator();
		pdf.generatePDF(purchaseOrder);
	}

	public static void generate(int id, Object repo) {
		new PDFService().createPDF(id, repo);
	}
}