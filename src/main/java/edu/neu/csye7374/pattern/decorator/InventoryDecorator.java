package edu.neu.csye7374.pattern.decorator;

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
