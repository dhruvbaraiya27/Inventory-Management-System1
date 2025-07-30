package com.inventory.designpattern.facade;

import com.inventory.model.Invoice;
import com.inventory.repository.InvoiceRepository;

public class PDFService extends ServiceFacade{

	@Override
	protected void sendMsg(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void createPDF(int id, InvoiceRepository repo) {
		Invoice invoice = repo.getInvoicebyID(id);
		PDFCreator pdf = new PDFCreator();
		pdf.generatePDF(invoice);
	}

	public static void generate(int id, InvoiceRepository repo) {
		new PDFService().createPDF(id, repo);
	}
}