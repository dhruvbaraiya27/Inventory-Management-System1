package com.inventory.designpattern.facade;

import com.inventory.designpattern.factory.CommFactory;
import com.inventory.repository.InvoiceRepository;

public class MessageSender extends ServiceFacade{

	@Override
	protected void sendMsg(String msg) {
		// TODO Auto-generated method stub
		new CommFactory().getObject().triggerServerClient(msg);
	}
	
	public static void send(String msg) {
		
		MessageSender sender = new MessageSender();
		sender.sendMsg(msg);
	
	}

	@Override
	protected void createPDF(int id, InvoiceRepository repo) {
		// TODO Auto-generated method stub
		
	}

}