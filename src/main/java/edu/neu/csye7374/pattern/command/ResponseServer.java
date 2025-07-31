package edu.neu.csye7374.pattern.command;
import java.io.IOException;

public class ResponseServer implements Runnable, Command{

	@Override
	public void run() {
		try {
			new NetworkManager().Server();
		} catch (IOException e) {
			System.err.println("Error in ResponseServer");
			e.printStackTrace();
		}
		
	}

	@Override
	public void execute() {
		Thread serverThread = new Thread(new ResponseServer());
		serverThread.start();
		
	}

} 