package edu.neu.csye7374.pattern.state;

import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.pattern.facade.MessageSender;
import edu.neu.csye7374.repository.ItemRepository;

public class StockAlert extends StockState {
    private Item item;
    private ItemRepository itemRepo;

    public StockAlert(Item item, ItemRepository itemRepo) {
        this.item = item;
        this.itemRepo = itemRepo;
    }

    @Override
    public void action(StockContext context, int stock) {
        MessageSender.send("\n******\nLOW STOCK for " + item.getItemName() + "\n*****\n");
        item.setItemQuantity(stock);
        itemRepo.update(item);
    }
}
