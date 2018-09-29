package com.journal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.Editor;

public class EditorJDBCController {

	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void saveEditor(Editor editor) {
		String query = "insert into editor(firstName, lastName, email, avatar, description, affiliation, journalId) values" +
						"(?, ?, ?, ?, ?, ? ,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, editor.getFirstName(), editor.getLastName(), editor.getEmail(), editor.getAvatar(), 
				editor.getDescription(), editor.getAffilation(), editor.getJournalId());
		
		System.out.println("Editor uploaded");
	}
	
	public void updateEditor(Editor editor) {
		String query = "update editor set firstName = ?, lastName = ?, email = ?, avatar = ?, description = ?, affiliation = ?, journalId = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, editor.getFirstName(), editor.getLastName(), editor.getEmail(), editor.getAvatar(), 
				editor.getDescription(), editor.getAffilation(), editor.getJournalId(), editor.getId());
		
		System.out.println("Editor updated");
	}
}
