package com.journal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.Reviewer;

public class ReviewerJDBCTemplate {

	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void addReviewer(Reviewer reviewer) {
		String query = "insert into Reviewer(firstName, lastName, email, country) values" +
						"(?, ?, ?, ?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, reviewer.getFirstName(), reviewer.getLastName(), reviewer.getEmail(), reviewer.getCountry());
		
		System.out.println("Reviewer uploaded");
	}
	
	public void updateReviewer(Reviewer reviewer) {
		String query = "update Reviewer set firstName = ?, lastName = ?, email = ?, country = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, reviewer.getFirstName(), reviewer.getLastName(), reviewer.getEmail(), reviewer.getCountry(), reviewer.getId());
		
		System.out.println("Reviewer updated");
	}
}
