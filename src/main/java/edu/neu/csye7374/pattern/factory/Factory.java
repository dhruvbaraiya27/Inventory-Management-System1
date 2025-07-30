package com.inventory.designpattern.factory;

import com.inventory.designpattern.command.Communication;

public abstract class Factory {
	/**
	 * Returns an object
	 */
	public abstract Communication getObject();
}
