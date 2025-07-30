package edu.neu.csye7374.pattern.facade;



public abstract class ServiceFacade {

	protected abstract void sendMsg(String msg);
	
	protected abstract void createPDF(int id, Object repo);
	
}