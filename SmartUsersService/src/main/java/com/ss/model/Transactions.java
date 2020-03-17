/**SmartSoftware User - Service */
/**
 * Description: The persistent class for the Transaction database table.
 * Name of Project: SmartSoftware
 * Created on: March 25, 2020
 * Modified on: March 25, 2020 11:19:38 AM
 * @author Juned
 * Version: 
 */
package com.ss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(dynamicInsert = true)
@Table(name = "transaction")
@NamedQuery(name = "Transactions.findAll", query = "SELECT t FROM Transactions t")
public class Transactions extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "transaction_id")
	private int transactionId;

	@Column(name = "transaction_name")
	private String transactionName;

	@Column(name = "transaction_description")
	private String transactionDescription;

	@ManyToOne
	@JoinColumn(name = "transaction_type_id")
	private TransactionType transactionType;

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionName() {
		return transactionName;
	}

	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}

	public String getTransactionDescription() {
		return transactionDescription;
	}

	public void setTransactionDescription(String transactionDescription) {
		this.transactionDescription = transactionDescription;
	}

}