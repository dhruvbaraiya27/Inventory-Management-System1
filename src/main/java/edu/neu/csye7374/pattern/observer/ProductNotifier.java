package edu.neu.csye7374.pattern.observer;

import edu.neu.csye7374.model.Item;


import java.util.ArrayList;
import java.util.List;

public class ProductNotifier {
    private List<Observer> subscribers = new ArrayList<>();

    public void setState(Item item) {
        notifyAllSubscribers(item);
    }

    public void attach(Observer sub) {
        subscribers.add(sub);
    }

    public void notifyAllSubscribers(Item item) {
        for (Observer observer : subscribers) {
            observer.update(item);
        }
    }
}
