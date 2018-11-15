package com.journal.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.User;
import com.journal.rowmapper.UserRowMapper;

public class UserJDBCTemplate {
	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource datasource) {
		this.dataSource = datasource;
	}
	
	public void save(User user) {
		
		String query = "insert into USER (email, phoneNo, firstName, lastName, countryName, password, generated, userType, address) values (?, ?,?, ?,?, ?,?, ?,?);";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
//		jdbcTemplate.update(query, user.getEmail(), user.getPhoneNo(), user.getFirstName(), 
//				user.getLastName(), user.getCountryName(), user.getPassword(), user.getGenerated(), user.getUsertype(), user.getAddress());
		
		System.out.println("User created");
	}

	public User getUserByEmailId(String email) {
		String query = "select email, phoneNo, firstName, lastName, countryName, password, userType from USER where email = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<User> users = jdbcTemplate.query(query, new Object[] {email}, new UserRowMapper());
		if (users != null && users.size() > 0) {
			return users.get(0);
		}
		return null;
	}

	public void updatePassword(User user) {
		
		String query = "update USER set password = ? where email = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, user.getPassword(), user.getEmail());
		
		System.out.println("User new password is updated");
		
	}
}