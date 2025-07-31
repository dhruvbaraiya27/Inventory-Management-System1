package edu.neu.csye7374.pattern.strategy;

import edu.neu.csye7374.model.OrderRequest;
import edu.neu.csye7374.pattern.decorator.InventoryCartAPI;
import edu.neu.csye7374.pattern.state.StockUpdate;
import edu.neu.csye7374.repository.ItemRepository;
import edu.neu.csye7374.repository.OrderRepository;

public class OrderRequestStrategy implements OperationStrategyAPI{

    private OrderRepository orderRepo;
    private ItemRepository productRepo;
    private int id;
    private OrderRequest purchaseOrder;
    private OrderRequest insertedPO;

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