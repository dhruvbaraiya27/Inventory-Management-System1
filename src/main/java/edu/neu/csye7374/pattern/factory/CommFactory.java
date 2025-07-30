package edu.neu.csye7374.pattern.factory;

import com.inventory.designpattern.command.Command;

// Implementing Lazy Factory Pattern
public class CommFactory extends Factory{

	private static Command comm;
	
	@Override
	public Command getObject() {
		
		if(comm == null) {
			comm = new Command() {
				@Override
				public void execute() {
					System.out.println("Command executed");
				}
			};
		}
		return comm;
	}

}