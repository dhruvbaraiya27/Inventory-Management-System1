package edu.neu.csye7374.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
public class Invoice {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "purchaseOrder_id")
	@JsonManagedReference
	private PurchaseOrder purchaseOrder;

	@Column(nullable = false)
	private String paymentDate;
	
	@Column
	private String pdfFilePath;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public PurchaseOrder getPurchaseOrder() {
		return purchaseOrder;
	}

	public void setPurchaseOrder(PurchaseOrder purchaseOrder) {
		this.purchaseOrder = purchaseOrder;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getPdfFilePath() {
		return pdfFilePath;
	}

	public void setPdfFilePath(String pdfFilePath) {
		this.pdfFilePath = pdfFilePath;
	}
}
