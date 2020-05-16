/**
 * PaymentRequest.java
 * Created Date : 11 May 2020
 */
package com.journal.requests;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author imran
 *
 */
@XmlRootElement
public class PaymentRequest implements Serializable {

	/**
	 * serialVersionUID long
	 */
	private static final long serialVersionUID = 1L;

	@XmlElement(required = false)
	private String userId;

	@XmlElement(required = false)
	private BigDecimal amount;

	@XmlElement(required = false)
	private String journalName;

	@XmlElement(required = false)
	private String authorName;

	@XmlElement(required = false)
	private String authorEmailId;

	@XmlElement(required = false)
	private String currencyCode;

	@XmlElement(required = false)
	private String articleName;

	@XmlElement(required = false)
	private String invoiceNumber;

	@XmlElement(required = false)
	private String paymentStatus;
	
	@XmlElement(required = false)
	private String transactionId;

	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the journalName
	 */
	public String getJournalName() {
		return journalName;
	}

	/**
	 * @param journalId the journalId to set
	 */
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}

	/**
	 * @return the authorName
	 */
	public String getAuthorName() {
		return authorName;
	}

	/**
	 * @param authorName the authorName to set
	 */
	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	/**
	 * @return the authorEmailId
	 */
	public String getAuthorEmailId() {
		return authorEmailId;
	}

	/**
	 * @param authorEmailId the authorEmailId to set
	 */
	public void setAuthorEmailId(String authorEmailId) {
		this.authorEmailId = authorEmailId;
	}

	/**
	 * @return the articleName
	 */
	public String getArticleName() {
		return articleName;
	}

	/**
	 * @param articleName the articleName to set
	 */
	public void setArticleName(String articleName) {
		this.articleName = articleName;
	}

	/**
	 * @return the invoiceNumber
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	/**
	 * @param invoiceNumber the invoiceNumber to set
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return the paymentStatus
	 */
	public String getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * @param paymentStatus the paymentStatus to set
	 */
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}	

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PaymentRequest [userId=");
		builder.append(userId);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", journalName=");
		builder.append(journalName);
		builder.append(", authorName=");
		builder.append(authorName);
		builder.append(", authorEmailId=");
		builder.append(authorEmailId);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", articleName=");
		builder.append(articleName);
		builder.append("]");
		return builder.toString();
	}
}