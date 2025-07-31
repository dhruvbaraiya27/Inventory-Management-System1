package edu.neu.csye7374.pattern.factory;

import edu.neu.csye7374.pattern.command.Command;
import edu.neu.csye7374.pattern.command.CommandInvoker;

public abstract class Factory {
	/**
	 * Returns an object
	 */
	public abstract CommandInvoker getObject();
}
