package edu.neu.csye7374.pattern.factory;

import com.inventory.designpattern.command.Command;

public abstract class Factory {
	/**
	 * Returns an object
	 */
	public abstract Command getObject();
}
