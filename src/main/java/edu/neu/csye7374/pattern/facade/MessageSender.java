package edu.neu.csye7374.pattern.facade;

import edu.neu.csye7374.pattern.factory.CommFactory;


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
	protected void createPDF(int id, Object repo) {
		// TODO Auto-generated method stub
		
	}

}