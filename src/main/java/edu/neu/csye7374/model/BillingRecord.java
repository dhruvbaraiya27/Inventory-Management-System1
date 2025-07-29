package edu.neu.csye7374.model;

public class BillingRecord {
    private int billingId;

    private OrderRequest billingPurchaseOrder;

    private String billingPaymentDate;

    public int getBillingId() {
        return billingId;
    }

    public void setBillingId(int billingId) {
        this.billingId = billingId;
    }

    public OrderRequest getBillingPurchaseOrder() {
        return billingPurchaseOrder;
    }

    public void setBillingPurchaseOrder(OrderRequest billingPurchaseOrder) {
        this.billingPurchaseOrder = billingPurchaseOrder;
    }

    public String getBillingPaymentDate() {
        return billingPaymentDate;
    }

    public void setBillingPaymentDate(String billingPaymentDate) {
        this.billingPaymentDate = billingPaymentDate;
    }

    @Override
    public String toString() {
        return "BillingRecord{" + "billingId=" + billingId + ", billingPurchaseOrder=" + billingPurchaseOrder + ", billingPaymentDate='" + billingPaymentDate + '\'' + '}';
    }
}
