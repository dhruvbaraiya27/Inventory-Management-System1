package edu.neu.csye7374.pattern.state;

public class StockContext {
    private StockState state;

    public StockContext() {
        state = null;
    }

    public StockState getState() {
        return state;
    }

    public void setState(StockState state) {
        this.state = state;
    }
}
