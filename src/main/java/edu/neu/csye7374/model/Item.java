package edu.neu.csye7374.model;

import java.util.List;

public class Item {
    private int itemId;
    private String itemName;
    private int itemQuantity;
    private double itemPrice;
    private List<ItemPurchaseOrder> itemPurchaseOrders;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public List<ItemPurchaseOrder> getItemPurchaseOrders() {
        return itemPurchaseOrders;
    }

    public void setItemPurchaseOrders(List<ItemPurchaseOrder> itemPurchaseOrders) {
        this.itemPurchaseOrders = itemPurchaseOrders;
    }

    @Override
    public String toString() {
        return "Item{" + "itemId=" + itemId + ", itemName='" + itemName + '\'' + ", itemQuantity=" + itemQuantity + ", itemPrice=" + itemPrice + ", itemPurchaseOrders=" + itemPurchaseOrders + '}';
    }
}