package com.inventory.designpattern.decorator;

import com.inventory.InventoryCartAPI;

public class InventoryDecorator implements InventoryCartAPI{
	
	InventoryCartAPI decoratedCart;
		
	public InventoryDecorator(InventoryCartAPI decoratedCart) {
		this.decoratedCart = decoratedCart;
	}

	@Override
	public double getCost() {
		return this.decoratedCart.getCost();
	}

} 