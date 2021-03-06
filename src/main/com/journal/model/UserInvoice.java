/**
 * UserInvoice.java
 * Created Date : 12 May 2020
 */
package com.journal.model;

import java.math.BigDecimal;
import java.util.Date;


/**
 * @author imran
 *
 */
public class UserInvoice {

	private Integer id;
	private String authorName;
	private String journalName;
	private String articleName;
	private String invoiceNumber;
	private String currencyCode;
	private BigDecimal amount;
	private Date creationDate;
	private String userId;
	private String paymentStatus;
	private String transactionId;
	private String articleNumber;
	private String authorEmailId;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
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
	 * @return the journalName
	 */
	public String getJournalName() {
		return journalName;
	}
	/**
	 * @param journalName the journalName to set
	 */
	public void setJournalName(String journalName) {
		this.journalName = journalName;
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
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	/**
	 * @return the articleNumber
	 */
	public String getArticleNumber() {
		return articleNumber;
	}
	/**
	 * @param articleNumber the articleNumber to set
	 */
	public void setArticleNumber(String articleNumber) {
		this.articleNumber = articleNumber;
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
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInvoice [id=");
		builder.append(id);
		builder.append(", authorName=");
		builder.append(authorName);
		builder.append(", journalName=");
		builder.append(journalName);
		builder.append(", articleName=");
		builder.append(articleName);
		builder.append(", invoiceNumber=");
		builder.append(invoiceNumber);
		builder.append(", currencyCode=");
		builder.append(currencyCode);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", creationDate=");
		builder.append(creationDate);
		builder.append(", userId=");
		builder.append(userId);
		builder.append(", paymentStatus=");
		builder.append(paymentStatus);
		builder.append(", transactionId=");
		builder.append(transactionId);
		builder.append(", articleNumber=");
		builder.append(articleNumber);
		builder.append(", authorEmailId=");
		builder.append(authorEmailId);
		builder.append("]");
		return builder.toString();
	}	
}