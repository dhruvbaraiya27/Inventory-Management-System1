package edu.neu.csye7374.pattern.strategy;

import edu.neu.csye7374.model.BillingRecord;
import edu.neu.csye7374.repository.BillingRecordRepository;
import edu.neu.csye7374.repository.OrderRepository;

public class BillingRecordStrategy implements OperationStrategyAPI{

    private BillingRecordRepository invoiceRepo;
    private int id;
    private BillingRecord invoice;
    private OrderRepository orderRepo;

    public BillingRecordStrategy(BillingRecordRepository invoiceRepo, BillingRecord invoice) {
        super();
        this.invoiceRepo = invoiceRepo;
        this.invoice = invoice;
    }

    public BillingRecordStrategy(BillingRecordRepository invoiceRepo, int id , OrderRepository orderRepo) {
        this.invoiceRepo = invoiceRepo;
        this.id = id;
        this.orderRepo = orderRepo;
    }

    public BillingRecordStrategy(BillingRecordRepository invoiceRepo, int id) {
        super();
        this.invoiceRepo = invoiceRepo;
        this.id = id;
    }

    @Override
    public void add() {

    }

    @Override
    public void update() {
    }

    @Override
    public void delete() {
    }

}

