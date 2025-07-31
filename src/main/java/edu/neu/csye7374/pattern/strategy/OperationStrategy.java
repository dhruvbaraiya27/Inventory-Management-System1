package edu.neu.csye7374.pattern.strategy;

public class OperationStrategy implements OperationStrategyAPI {

    private OperationStrategyAPI operationStrategy;

    public OperationStrategy(OperationStrategyAPI operationStrategy) {
        this.operationStrategy = operationStrategy;
    }
    @Override
    public void add() {
        operationStrategy.add();
    }

    @Override
    public void delete() {
        operationStrategy.delete();
    }

    @Override
    public void update() {
        operationStrategy.update();
    }
}
