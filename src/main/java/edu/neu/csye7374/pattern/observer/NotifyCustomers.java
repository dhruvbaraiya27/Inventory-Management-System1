package edu.neu.csye7374.pattern.observer;


import edu.neu.csye7374.model.Customer;
import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.pattern.facade.MessageSender;
import edu.neu.csye7374.repository.CustomerRepository;

import java.util.List;

public class NotifyCustomers {

    private List<Customer> customers;
    private Item item;
    private CustomerRepository customerRepo;

    public NotifyCustomers(Item item, CustomerRepository customerRepo) {
        this.item = item;
        this.customerRepo = customerRepo;
    }

    public void notifyAllCustomers() {
        StringBuilder sb = new StringBuilder();
        customers = customerRepo.getCustomers();

        for (Customer customer : customers) {
            System.out.println(customer.getCustomerOwnerName() + " notified of Item " + item.getItemName() + " addition");
            sb.append("Hello ").append(customer.getCustomerOwnerName()).append(", new item available: ");
            sb.append(item.getItemName()).append("\n");

            MessageSender.send(sb.toString());
        }
    }
}