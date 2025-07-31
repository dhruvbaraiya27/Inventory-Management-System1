package edu.neu.csye7374.pattern.state;

public abstract class StockState {
    public abstract void action(StockContext context, int stock);
}
