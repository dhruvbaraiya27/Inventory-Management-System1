package edu.neu.csye7374.pattern.observer;

import edu.neu.csye7374.model.Item;
import edu.neu.csye7374.repository.ItemRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UpdateStockDatabase extends Observer{
    private ItemRepository itemRepo;

    public UpdateStockDatabase(ProductNotifier notifier, ItemRepository itemRepo) {
        this.notifier = notifier;
        this.itemRepo = itemRepo;
        this.notifier.attach(this);
    }

    @Override
    public void update(Item item) {
        if (itemRepo.itemExists(item.getItemName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item already exists");
        }
        itemRepo.save(item);
    }
}
