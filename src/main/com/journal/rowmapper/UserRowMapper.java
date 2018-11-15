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
		user.setfName(rs.getString("firstName"));
		user.setlName(rs.getString("lastName"));
		user.setPassword(rs.getString("password"));
		return user;
	}

}
