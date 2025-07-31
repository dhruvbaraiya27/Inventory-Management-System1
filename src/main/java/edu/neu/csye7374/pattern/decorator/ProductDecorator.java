package edu.neu.csye7374.pattern.decorator;


import edu.neu.csye7374.model.ItemPurchaseOrder;

public class ProductDecorator extends InventoryDecorator {

	int productQuantity;
	double productPrice;
	
	public ProductDecorator(InventoryCartAPI decoratedCart, InventoryProduct productItem, ItemPurchaseOrder itemPurchaseOrder) {
		super(decoratedCart);
		this.productQuantity = itemPurchaseOrder.getItemPurchaseOrderQuantity();
		this.productPrice = productItem.getPrice();
	}
	
	public double getCost() {
		return super.getCost()+(this.productQuantity * this.productPrice);
	}
} 