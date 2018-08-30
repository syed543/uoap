package com.journal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.dao.record.SubmitterRecord;

public class SubmitterJDBCTemplate {
	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public SubmitterRecord getSubmitterByEmail(String email) {
		
		String query = "select id, title, firstName, lastName, email, password, generatedPass, postalAddress, country from submitter where email = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		try {
			
			SubmitterRecord record = (SubmitterRecord) jdbcTemplate.queryForObject(query, new Object[] {email}, new BeanPropertyRowMapper(SubmitterRecord.class));
			return record;
		} catch (EmptyResultDataAccessException eae) {
			
		}
		return null;
	}

	public SubmitterRecord saveSubmitter(SubmitterRecord submitterRecord) {

		String query = "insert into submitter(title, firstName, lastName, email, password, generatedPass, postalAddress, country) values" +
				"(?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, submitterRecord.getTitle(), submitterRecord.getFirstName(), submitterRecord.getLastName(), 
				submitterRecord.getEmail(), submitterRecord.getPassword(), submitterRecord.getGeneratedPass(), 
				submitterRecord.getAddress(), submitterRecord.getCountry());
		
		String auto = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'journal' AND TABLE_NAME = 'submitter'";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		submitterRecord.setId(autoInt - 1);
		
		return submitterRecord;
	}
	
	public void updatePassword(SubmitterRecord submitterRecord) {
		
		String query = "update submitter set password = ? where email = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, submitterRecord.getPassword(), submitterRecord.getEmail());
		
		System.out.println("User new password is updated");
	}
}