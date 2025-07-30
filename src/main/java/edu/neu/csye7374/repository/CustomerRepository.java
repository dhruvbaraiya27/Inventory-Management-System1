package edu.neu.csye7374.repository;

import java.util.List;

import edu.neu.csye7374.model.Customer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;


@Repository
@Transactional
public class CustomerRepository {

    @Autowired
    private SessionFactory factory;

    // Create
    public void save(Customer customer) {
        getSession().save(customer);
    }

    // Read all
    public List<Customer> getCustomers() {
        String hql = "FROM Customer";
        Query query = getSession().createQuery(hql);
        List<Customer> results = query.list();
        return results;
    }

    // Read by ID
    public Customer getCustomerById(int id) {
        return getSession().get(Customer.class, id);
    }

    // Update
    public void update(Customer customer) {
        getSession().update(customer);
        getSession().flush();
    }

    // Delete
    public void delete(Customer customer) {
        getSession().delete(customer);
        getSession().flush();
    }

    // Check if a company already exists
    public boolean companyExists(String company) {
        String hql = "FROM Customer WHERE companyName = :company";
        Query query = getSession().createQuery(hql);
        query.setParameter("company", company);
        Customer customer = (Customer) query.uniqueResult();
        return customer != null;
    }

    private Session getSession() {
        Session session = factory.getCurrentSession();
        if (session == null) {
            session = factory.openSession();
        }
        return session;
    }
}
