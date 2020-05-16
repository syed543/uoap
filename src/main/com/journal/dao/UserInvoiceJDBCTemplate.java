/**
 * UserInvoiceJDBCTemplate.java
 * Created Date : 12 May 2020
 */
package com.journal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.UserInvoice;

/**
 * @author imran
 *
 */
public class UserInvoiceJDBCTemplate {

	@Autowired
	private DataSource dataSource;

	public void setDataSource(DataSource datasource) {
		this.dataSource = datasource;
	}

	/**
	 * 
	 * @author imran
	 * @param userInvoice void
	 */
	public void saveUserInvoice(UserInvoice userInvoice) {

		String query = "insert into USERINVOICE(authorName, journalName, articleName, invoiceNumber, currencyCode, amount, creationDate, userId, paymentStatus, transactionId) values (?,?,?,?,?,?,?,?,?,?);";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

		jdbcTemplate.update(query, userInvoice.getAuthorName(),
				userInvoice.getJournalName(), userInvoice.getArticleName(),
				userInvoice.getInvoiceNumber(), userInvoice.getCurrencyCode(),
				userInvoice.getAmount(), userInvoice.getCreationDate(),
				userInvoice.getUserId(), userInvoice.getPaymentStatus(),
				userInvoice.getTransactionId());

	}

	/**
	 * 
	 * @author imran
	 * @param invoiceId
	 * @return UserInvoice
	 */
	@SuppressWarnings("unchecked")
	public UserInvoice getUserInvoiceById(String invoiceNumber) {
		String query = "select authorName, journalName, articleName, invoiceNumber, currencyCode, amount, userId from USERINVOICE where invoiceNumber = ?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		UserInvoice userInvoice = (UserInvoice) jdbcTemplate.query(query,
				new Object[] { invoiceNumber },
				new BeanPropertyRowMapper<UserInvoice>(UserInvoice.class));

		return userInvoice;
	}

	/**
	 * 
	 * @author imran
	 * @param userInvoice void
	 */
	public void updatePaymentStatus(UserInvoice userInvoice) {

		String query = "update USERINVOICE set paymentStatus = ?, transactionId =? where invoiceNumber = ?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, userInvoice.getPaymentStatus(),
				userInvoice.getTransactionId(), userInvoice.getInvoiceNumber());
	}
}
