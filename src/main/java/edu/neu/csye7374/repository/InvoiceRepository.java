package edu.neu.csye7374.repository;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import edu.neu.csye7374.model.Invoice;


@Repository
@Transactional
public class InvoiceRepository {

	@Autowired
	private SessionFactory factory;
	//C
	public int save(Invoice invoice) {
		int id = (int) getSession().save(invoice);
		return id;
	}
	//R
	public List<Invoice> getInvoices() {
		try {
			String hql = "SELECT DISTINCT i FROM Invoice i LEFT JOIN FETCH i.purchaseOrder po WHERE po IS NOT NULL";
			Query<Invoice> query = getSession().createQuery(hql, Invoice.class);
			List<Invoice> results = query.list();
			return results;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error fetching invoices: " + e.getMessage());
			// Fallback: try to get invoices without join
			try {
				String fallbackHql = "FROM Invoice";
				Query<Invoice> fallbackQuery = getSession().createQuery(fallbackHql, Invoice.class);
				List<Invoice> fallbackResults = fallbackQuery.list();
				// Filter out invoices with null purchase orders
				return fallbackResults.stream()
					.filter(inv -> {
						try {
							return inv.getPurchaseOrder() != null;
						} catch (Exception ex) {
							return false;
						}
					})
					.collect(java.util.stream.Collectors.toList());
			} catch (Exception ex) {
				ex.printStackTrace();
				return new java.util.ArrayList<>();
			}
		}
	}
	
	//R by ID
	public Invoice getInvoicebyID(int id) {
		try {
			String hql = "SELECT DISTINCT i FROM Invoice i " +
						"LEFT JOIN FETCH i.purchaseOrder po " +
						"LEFT JOIN FETCH po.products p " +
						"LEFT JOIN FETCH p.product " +
						"LEFT JOIN FETCH po.buyer " +
						"WHERE i.id = :id";
			Query<Invoice> query = getSession().createQuery(hql, Invoice.class);
			query.setParameter("id", id);
			Invoice invoice = query.uniqueResult();
			
			// Debug logging
			if (invoice != null) {
				System.out.println("DEBUG: Found invoice " + invoice.getId());
				if (invoice.getPurchaseOrder() != null) {
					System.out.println("DEBUG: Purchase order found, ID: " + invoice.getPurchaseOrder().getId());
					if (invoice.getPurchaseOrder().getProducts() != null) {
						System.out.println("DEBUG: Products count: " + invoice.getPurchaseOrder().getProducts().size());
					} else {
						System.out.println("DEBUG: Products list is null");
					}
				} else {
					System.out.println("DEBUG: Purchase order is null");
				}
			}
			
			return invoice;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error fetching invoice by ID: " + e.getMessage());
			return null;
		}
    }
	
	//U
	public void update(Invoice invoice) {
		getSession().update(invoice);
		getSession().flush();
	}
	//D
	public void delete(Invoice invoice) {
		getSession().delete(invoice);
		getSession().flush();
	}
	
	// Clean up orphaned invoice records
	public void cleanupOrphanedInvoices() {
		try {
			String hql = "DELETE FROM Invoice i WHERE i.purchaseOrder IS NULL";
			Query<?> query = getSession().createQuery(hql);
			int deletedCount = query.executeUpdate();
			System.out.println("Cleaned up " + deletedCount + " orphaned invoice records");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error cleaning up orphaned invoices: " + e.getMessage());
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
