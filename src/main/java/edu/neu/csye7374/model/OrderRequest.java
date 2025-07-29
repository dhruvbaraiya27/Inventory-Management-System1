package edu.neu.csye7374.model;

import java.util.List;

public class OrderRequest {
    private int orderRequestId;

    private List<ItemPurchaseOrder> orderRequestProducts;

    private Customer orderRequestBuyer;

    private String orderRequestPaymentDueDate;

    private boolean orderRequestPaid;

    private double orderRequestTotalAmount;

    private BillingRecord orderRequestInvoice;

    public int getOrderRequestId() {
        return orderRequestId;
    }

    public void setOrderRequestId(int orderRequestId) {
        this.orderRequestId = orderRequestId;
    }

    public List<ItemPurchaseOrder> getOrderRequestProducts() {
        return orderRequestProducts;
    }

    public void setOrderRequestProducts(List<ItemPurchaseOrder> orderRequestProducts) {
        this.orderRequestProducts = orderRequestProducts;
    }

    public Customer getOrderRequestBuyer() {
        return orderRequestBuyer;
    }

    public void setOrderRequestBuyer(Customer orderRequestBuyer) {
        this.orderRequestBuyer = orderRequestBuyer;
    }

    public String getOrderRequestPaymentDueDate() {
        return orderRequestPaymentDueDate;
    }

    public void setOrderRequestPaymentDueDate(String orderRequestPaymentDueDate) {
        this.orderRequestPaymentDueDate = orderRequestPaymentDueDate;
    }

    public boolean isOrderRequestPaid() {
        return orderRequestPaid;
    }

    public void setOrderRequestPaid(boolean orderRequestPaid) {
        this.orderRequestPaid = orderRequestPaid;
    }

    public double getOrderRequestTotalAmount() {
        return orderRequestTotalAmount;
    }

    public void setOrderRequestTotalAmount(double orderRequestTotalAmount) {
        this.orderRequestTotalAmount = orderRequestTotalAmount;
    }

    public BillingRecord getOrderRequestInvoice() {
        return orderRequestInvoice;
    }

    public void setOrderRequestInvoice(BillingRecord orderRequestInvoice) {
        this.orderRequestInvoice = orderRequestInvoice;
    }

    @Override
    public String toString() {
        return "OrderRequest{" + "orderRequestId=" + orderRequestId + ", orderRequestProducts=" + orderRequestProducts + ", orderRequestBuyer=" + orderRequestBuyer + ", orderRequestPaymentDueDate='" + orderRequestPaymentDueDate + '\'' + ", orderRequestPaid=" + orderRequestPaid + ", orderRequestTotalAmount=" + orderRequestTotalAmount + ", orderRequestInvoice=" + orderRequestInvoice + '}';
    }
}
