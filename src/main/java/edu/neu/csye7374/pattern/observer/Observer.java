package edu.neu.csye7374.pattern.observer;

import edu.neu.csye7374.model.Item;

public abstract class Observer {
    protected ProductNotifier notifier;

    public abstract void update(Item item);
}
