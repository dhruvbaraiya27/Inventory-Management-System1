package edu.neu.csye7374.repository;

import edu.neu.csye7374.model.Item;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class ItemRepository {

    @Autowired
    private SessionFactory factory;

    // Create
    public void save(Item item) {
        getSession().save(item);
    }

    // Read all
    public List<Item> getItems() {
        String hql = "FROM Item";
        Query<Item> query = getSession().createQuery(hql, Item.class);
        return query.list();
    }

    // Read by ID
    public Item getItemById(int id) {
        return getSession().get(Item.class, id);
    }

    // Update
    public void update(Item item) {
        getSession().update(item);
        getSession().flush();
    }

    // Delete
    public void delete(Item item) {
        getSession().delete(item);
        getSession().flush();
    }

    // Check if item with given name exists
    public boolean itemExists(String itemName) {
        String hql = "FROM Item WHERE itemName = :itemName";
        Query<Item> query = getSession().createQuery(hql, Item.class);
        query.setParameter("itemName", itemName);
        Item result = query.uniqueResult();
        return result != null;
    }

    // Utility method to get current session
    private Session getSession() {
        Session session = factory.getCurrentSession();
        if (session == null) {
            session = factory.openSession();
        }
        return session;
    }
}