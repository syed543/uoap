package com.journal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.dao.record.EditorRecord;
import com.journal.model.EditorModel;

public class EditorJDBCTemplate {

	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void saveEditor(EditorModel editorModel) {
		String query = "insert into Editor(firstName, lastName, email, avatar, avatarFileName, description, affiliation, journalId) values" +
						"(?, ?, ?, ?, ?, ?, ? ,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, editorModel.getFirstName(), editorModel.getLastName(), editorModel.getEmail(), 
				editorModel.getAvatar(), editorModel.getAvatarFileName(), editorModel.getDescription(), 
				editorModel.getAffiliation(), editorModel.getJournalId());
		
		String auto = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'journal' AND TABLE_NAME = 'Editor'";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		editorModel.setId(autoInt - 1);
		
		System.out.println("Editor Created");
	}
	
	public void updateEditor(EditorModel editorModel) {
		String query = "update editor set firstName = ?, lastName = ?,  avatar = ?, avatarFileName = ?, description = ?, affiliation = ?, journalId = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, editorModel.getFirstName(), editorModel.getLastName(), editorModel.getAvatar(), 
				editorModel.getAvatarFileName(), editorModel.getDescription(), editorModel.getAffiliation(), 
				editorModel.getJournalId());
		
		System.out.println("Editor updated");
	}

	public EditorRecord getEditorByMailId(String mail) {
		
		String query = "Select id, firstName, lastName, email from Editor where email = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		try {
			
			EditorRecord record = (EditorRecord) jdbcTemplate.queryForObject(query, new Object[] {mail}, new BeanPropertyRowMapper(EditorRecord.class));
			return record;
		} catch (EmptyResultDataAccessException eae) {
			
		}
		return null;
	}

	public List<EditorModel> getEditors() {
		
		String query = "Select e.id as id, firstName, lastName, email, description, affiliation, journalName from Editor e, Journal j where e.journalId = j.id";

		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> editorRows = jdbcTemplate.queryForList(query);
		
		List<EditorModel> editorModels = new ArrayList<EditorModel>();
		
		for (Map<String, Object> editorRow : editorRows) {
			
			EditorModel editorModel = new EditorModel();
			editorModel.setId((Integer) editorRow.get("id"));
			editorModel.setFirstName((String) editorRow.get("firstname"));
			editorModel.setLastName((String) editorRow.get("lastName"));
			editorModel.setEmail((String) editorRow.get("email"));
			editorModel.setDescription((String) editorRow.get("description"));
			editorModel.setAffiliation((String) editorRow.get("affiliation"));
			editorModel.setJournalName((String) editorRow.get("journalName"));
			editorModels.add(editorModel);
		}
		
		return editorModels;
	}

	public void deleteEditor(String editorId) {
		
		String query  = "delete from Editor where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {editorId});
	}

	public void setPassword(EditorRecord editorRecord) {

		
		String query = "update Editor set password = ? where email = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, editorRecord.getPassword(), editorRecord.getEmail());
		
		System.out.println("User new password is updated");
	
	}
}