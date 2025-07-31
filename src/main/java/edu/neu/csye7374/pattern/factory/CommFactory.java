package edu.neu.csye7374.pattern.factory;


import edu.neu.csye7374.pattern.command.Command;
import edu.neu.csye7374.pattern.command.CommandInvoker;

// Implementing Lazy Factory Pattern
public class CommFactory extends Factory{

	private static CommandInvoker comm;
	
	@Override
	public CommandInvoker getObject() {
		
		if(comm == null) {
			comm = new CommandInvoker();
		}
		return comm;
	}

}