package edu.neu.csye7374.model;

public class ItemPurchaseOrder {
    private int itemPurchaseOrderId;

    private Item itemPurchaseOrderProduct;

    private OrderRequest itemPurchaseOrderRequest;

    private int itemPurchaseOrderQuantity;

    public int getItemPurchaseOrderId() {
        return itemPurchaseOrderId;
    }

    public void setItemPurchaseOrderId(int itemPurchaseOrderId) {
        this.itemPurchaseOrderId = itemPurchaseOrderId;
    }

    public Item getItemPurchaseOrderProduct() {
        return itemPurchaseOrderProduct;
    }

    public void setItemPurchaseOrderProduct(Item itemPurchaseOrderProduct) {
        this.itemPurchaseOrderProduct = itemPurchaseOrderProduct;
    }

    public OrderRequest getItemPurchaseOrderRequest() {
        return itemPurchaseOrderRequest;
    }

    public void setItemPurchaseOrderRequest(OrderRequest itemPurchaseOrderRequest) {
        this.itemPurchaseOrderRequest = itemPurchaseOrderRequest;
    }

    public int getItemPurchaseOrderQuantity() {
        return itemPurchaseOrderQuantity;
    }

    public void setItemPurchaseOrderQuantity(int itemPurchaseOrderQuantity) {
        this.itemPurchaseOrderQuantity = itemPurchaseOrderQuantity;
    }

    @Override
    public String toString() {
        return "ItemPurchaseOrder{" + "itemPurchaseOrderId=" + itemPurchaseOrderId + ", itemPurchaseOrderProduct=" + itemPurchaseOrderProduct + ", itemPurchaseOrderRequest=" + itemPurchaseOrderRequest + ", itemPurchaseOrderQuantity=" + itemPurchaseOrderQuantity + '}';
    }
}
