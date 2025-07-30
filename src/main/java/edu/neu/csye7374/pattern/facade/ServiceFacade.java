package com.inventory.designpattern.facade;

import com.inventory.repository.InvoiceRepository;

public abstract class ServiceFacade {

	protected abstract void sendMsg(String msg);
	
	protected abstract void createPDF(int id, InvoiceRepository repo);
	
}