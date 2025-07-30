package com.inventory.designpattern.decorator;

import com.inventory.InventoryCartAPI;
import edu.neu.csye7374.pattern.decorator.InventoryProduct;
import edu.neu.csye7374.model.ProductPO;

public class ProductDecorator extends InventoryDecorator {

	int productQuantity;
	double productPrice;
	
	public ProductDecorator(InventoryCartAPI decoratedCart, InventoryProduct productItem, ProductPO productOrder) {
		super(decoratedCart);
		this.productQuantity = productOrder.getQuantity();
		this.productPrice = productItem.getPrice();
	}
	
	public double getCost() {
		return super.getCost()+(this.productQuantity * this.productPrice);
	}
} 