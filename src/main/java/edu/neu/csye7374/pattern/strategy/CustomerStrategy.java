package edu.neu.csye7374.pattern.strategy;

import edu.neu.csye7374.model.Customer;
import edu.neu.csye7374.repository.CustomerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class CustomerStrategy implements OperationStrategyAPI {

    private int id;
    private CustomerRepository customerRepo;
    private Customer customer;

    public CustomerStrategy(CustomerRepository customerRepo, Customer customer) {
        this.customerRepo = customerRepo;
        this.customer = customer;
    }

    public CustomerStrategy(CustomerRepository customerRepo, int id) {
        this.id = id;
        this.customerRepo = customerRepo;
    }

    @Override
    public void add() {
        if(customerRepo.companyExists(customer.getCustomerCompanyName()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Company already exists");
        customerRepo.save(customer);
    }

    @Override
    public void update() {
        customerRepo.update(customer);
    }

    @Override
    public void delete() {
        Customer customer = customerRepo.getCustomerById(id);
        customerRepo.delete(customer);
    }

}