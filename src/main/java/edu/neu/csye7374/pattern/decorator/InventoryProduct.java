package edu.neu.csye7374.pattern.decorator;

import edu.neu.csye7374.pattern.decorator.InventoryCartAPI;

public class InventoryProduct implements InventoryCartAPI{

	double productTotalCost;
	double productPrice;
	
	public InventoryProduct() {
		this.productTotalCost = 0;
		this.productPrice = 0;
	}
	
	public InventoryProduct(double price) {
		this.productTotalCost = price;
		this.productPrice = price;
	}

	@Override
	public double getCost() {
		return this.productTotalCost;
	}
	
	public double getPrice() {
		return this.productPrice;
	}

}
