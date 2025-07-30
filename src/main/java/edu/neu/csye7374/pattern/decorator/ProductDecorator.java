package edu.neu.csye7374.pattern.decorator;

import com.inventory.InventoryCartAPI;
import edu.neu.csye7374.pattern.decorator.InventoryProduct;
import edu.neu.csye7374.model.ItemPurchaseOrder;
import edu.neu.csye7374.model.Item;

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