package com.journal.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.journal.model.Submitter;

public class SubmitterRowMapper implements RowMapper {

	@Override
	public Object mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Submitter submitter = new Submitter();
		submitter.setEmail(rs.getString("email"));
		return submitter;
	}

}
