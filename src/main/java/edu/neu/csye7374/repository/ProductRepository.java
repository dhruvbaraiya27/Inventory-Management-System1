package edu.neu.csye7374.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.csye7374.model.Product;


@Repository
@Transactional
public class ProductRepository {

	@Autowired
	private SessionFactory factory;
	//C
	public void save(Product product) {
		getSession().save(product);
	}
	//R
	public List<Product> getProducts() {
		try {
			String hql = "SELECT DISTINCT p FROM Product p";
			Query<Product> query = getSession().createQuery(hql, Product.class);
			List<Product> results = query.list();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error fetching products: " + e.getMessage());
			// Return empty list if there's an error
			return new java.util.ArrayList<>();
		}
	}
	
	//R by ID
	public Product getProductbyID(int id) {
		try {
			Product product = getSession().get(Product.class, id);
			return product;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error fetching product by ID: " + e.getMessage());
			return null;
		}
    }
	
	//U
	public void update(Product product) {
		getSession().update(product);
		getSession().flush();
	}
	//D
	public void delete(Product product) {
		getSession().delete(product);
		getSession().flush();
	}
	
	public boolean productExists(String productName) {
		try {
			String hql = "FROM Product WHERE productName = :productName";        
			Query<Product> query = getSession().createQuery(hql, Product.class);
			query.setParameter("productName", productName);
			Product p = query.uniqueResult();
			return p != null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error checking if product exists: " + e.getMessage());
			return false;
		}
    }
	
	// Clean up orphaned ProductPO records that reference non-existent products
	public void cleanupOrphanedProductPO() {
		try {
			String hql = "DELETE FROM ProductPO p WHERE p.product IS NULL OR p.product.id NOT IN (SELECT pr.id FROM Product pr)";
			Query<?> query = getSession().createQuery(hql);
			int deletedCount = query.executeUpdate();
			System.out.println("Cleaned up " + deletedCount + " orphaned ProductPO records");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error cleaning up orphaned ProductPO records: " + e.getMessage());
		}
	}
	
	private Session getSession() {
		Session session = factory.getCurrentSession();
		if (session == null) {
			session = factory.openSession();
		}
		return session;
	}
}
