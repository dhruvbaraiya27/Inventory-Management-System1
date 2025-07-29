package edu.neu.csye7374.model;

import java.util.List;

public class Customer {
    private int customerId;

    private String customerOwnerName;

    private String customerCompanyName;

    private String customerZipcode;

    private List<OrderRequest> customerOrders;

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerOwnerName() {
        return customerOwnerName;
    }

    public void setCustomerOwnerName(String customerOwnerName) {
        this.customerOwnerName = customerOwnerName;
    }

    public String getCustomerCompanyName() {
        return customerCompanyName;
    }


    public void setCustomerCompanyName(String customerCompanyName) {
        this.customerCompanyName = customerCompanyName;
    }

    public String getCustomerZipcode() {
        return customerZipcode;
    }

    public void setCustomerZipcode(String customerZipcode) {
        this.customerZipcode = customerZipcode;
    }

    public List<OrderRequest> getCustomerOrders() {
        return customerOrders;
    }

    public void setCustomerOrders(List<OrderRequest> customerOrders) {
        this.customerOrders = customerOrders;
    }

    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", customerOwnerName='" + customerOwnerName + '\'' + ", customerCompanyName='" + customerCompanyName + '\'' + ", customerZipcode='" + customerZipcode + '\'' + ", customerOrders=" + customerOrders + '}';
    }
}
