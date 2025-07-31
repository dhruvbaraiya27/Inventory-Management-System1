package edu.neu.csye7374.pattern.command;
import java.io.IOException;


public class RequestClient implements Runnable, Command{

	static String requestMessage;
	
	public RequestClient() {
	}

	public RequestClient(String requestMessage) {
		super();
		RequestClient.requestMessage = requestMessage;
	}

	@Override
	public void run() {
		try {
			new NetworkManager().Client(RequestClient.requestMessage);
		} catch (IOException e) {

			e.printStackTrace();
		}
		
	}

	@Override
	public void execute() {
		Thread t = new Thread(new RequestClient(RequestClient.requestMessage));
		t.start();
		
	}

} 