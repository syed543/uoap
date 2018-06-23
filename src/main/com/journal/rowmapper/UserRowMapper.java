package com.journal.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.journal.model.User;

public class UserRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		System.out.println("Getting record");
		
		User user = new User();
		user.setEmail(rs.getString("email"));
		user.setFirstName(rs.getString("firstName"));
		user.setCountryName(rs.getString("countryName"));
		user.setLastName(rs.getString("lastName"));
		user.setPhoneNo(rs.getString("phoneNo"));
		user.setPassword(rs.getString("password"));
		user.setUsertype(rs.getString("userType"));
		return user;
	}

}
