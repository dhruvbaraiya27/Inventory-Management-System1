package edu.neu.csye7374.pattern.observer;


import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.repository.CustomerRepository;

public class CustomerObserver extends Observer {

    private CustomerRepository customerRepo;

    public CustomerObserver(ProductNotifier notifier, CustomerRepository customerRepo) {
        this.notifier = notifier;
        this.notifier.attach(this);
        this.customerRepo = customerRepo;
    }

    @Override
    public void update(Item item) {
        NotifyCustomers notify = new NotifyCustomers(item, customerRepo);
        notify.notifyAllCustomers();
    }
}
