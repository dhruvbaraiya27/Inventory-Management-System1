package edu.neu.csye7374.pattern.state;

import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.repository.ItemRepository;

public class StockUpdate extends StockState {

    private Item item;
    private ItemRepository itemRepo;

    public StockUpdate(Item item, ItemRepository itemRepo) {
        this.item = item;
        this.itemRepo = itemRepo;
    }

    @Override
    public void action(StockContext state, int stock) {
        item.setItemQuantity(stock);
        itemRepo.update(item);
    }
}
