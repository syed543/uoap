package com.journal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.Submitter;
import com.journal.rowmapper.SubmitterRowMapper;

public class SubmitterJDBCTemplate {
	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource datasource) {
		this.dataSource = datasource;
	}
	
	public void save(Submitter submitter) {
		
		String query = "insert into submitter (email, phoneNo, firstName, lastName, countryName, password, generated) values (?, ?,?, ?,?, ?,?);";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, submitter.getEmail(), submitter.getPhoneNo(), submitter.getFirstName(), 
				submitter.getLastName(), submitter.getCountryName(), submitter.getPassword(), submitter.getGenerated());
		
		System.out.println("Submitter created");
	}

	public Submitter getSubmitterByEmailId(String email) {
		String query = "select email, phoneNo, fistName, lastName, countryName from wubmitter where email = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		Submitter submitter = (Submitter) jdbcTemplate.queryForObject(query, new Object[] {email}, new SubmitterRowMapper());
		return submitter;
	}
}