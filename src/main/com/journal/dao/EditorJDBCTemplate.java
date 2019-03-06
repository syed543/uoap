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
import com.journal.model.Editor;
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
		String query = "insert into Editor(firstName, lastName, email, avatar, avatarFileName, description, affiliation, journalId, password, generatedPass,"
				+ "designation, department, country, contactno, isChiefEditor) values" +
						"(?, ?, ?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, editorModel.getFirstName(), editorModel.getLastName(), editorModel.getEmail(), 
				editorModel.getAvatar(), editorModel.getAvatarFileName(), editorModel.getDescription(), 
				editorModel.getAffiliation(), editorModel.getJournalId(), editorModel.getPassword(), editorModel.getGeneratedPass(),
				editorModel.getDesignation(), editorModel.getDepartment(), editorModel.getCountry(), editorModel.getContactNo(), editorModel.isChiefEditor());
		
		String auto = "select max(id) from Editor";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		editorModel.setId(autoInt);
	}
	
	public void updateEditor(EditorModel editorModel) {
		String query = "update editor set firstName = ?, lastName = ?,  avatar = ?, avatarFileName = ?, description = ?, affiliation = ?, journalId = ?,"
				+ "designation = ?, department = ?, country = ?, contactno = ?, isChiefEditor = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, editorModel.getFirstName(), editorModel.getLastName(), editorModel.getAvatar(), 
				editorModel.getAvatarFileName(), editorModel.getDescription(), editorModel.getAffiliation(), 
				editorModel.getJournalId(),
				editorModel.getDesignation(), editorModel.getDepartment(), editorModel.getCountry(), editorModel.getContactNo(), editorModel.isChiefEditor(),
				editorModel.getId());
		
		System.out.println("Editor updated");
	}

	public EditorRecord getEditorByMailId(String mail) {
		
		String query = "Select id, firstName, lastName, email, password,designation, department, country, contactno, isChiefEditor from Editor where email = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		try {
			
			EditorRecord record = (EditorRecord) jdbcTemplate.queryForObject(query, new Object[] {mail}, new BeanPropertyRowMapper(EditorRecord.class));
			return record;
		} catch (EmptyResultDataAccessException eae) {
			
		}
		return null;
	}
	
	public List<Editor> getEditorsByJournalId(int journalId) {
			
			String query = "select e.id as id, firstName, lastName, email, avatar, avatarFileName, description, affiliation, journalId, designation, department,country, " +
			"contactno, isChiefEditor from Editor e, Journal j where e.journalId = j.id and j.id = ?";
			
			JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
	
			List<Map<String, Object>> articleRows = jdbcTemplate.queryForList(query, new Object[] {journalId});
			
			List<Editor> articles = new ArrayList<Editor>();
			
			for (Map<String, Object> articleRow : articleRows) {
				
				Editor editor = new Editor();
				editor.setId((Integer) articleRow.get("id"));
				editor.setFirstName((String) articleRow.get("firstName"));
				editor.setLastName((String) articleRow.get("lastName"));
				editor.setEmail((String) articleRow.get("email"));
//				editor.setAvatar((String) articleRow.get("avatar"));
				editor.setAvatarFileName((String) articleRow.get("avatarFileName"));
				editor.setDescription((String) articleRow.get("description"));
				editor.setAffilation((String) articleRow.get("affiliation"));
				editor.setJournalId((Integer) articleRow.get("journalId")) ;
				editor.setDesignation((String) articleRow.get("designation")) ;
				editor.setDepartment((String) articleRow.get("department"));
				editor.setCountry((String) articleRow.get("country"));
				editor.setContactno((String) articleRow.get("contactno"));
				editor.setChiefEditor((Boolean) articleRow.get("isChiefEditor")) ;
	
				articles.add(editor);
			}
			
			return articles;
		}

	public List<EditorModel> getEditors() {
		
		String query = "Select e.id as id, firstName, lastName, email, description, affiliation, journalName, designation, department, country, contactno, isChiefEditor, j.id as jid from Editor e, Journal j where e.journalId = j.id";

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
			
			editorModel.setDepartment((String) editorRow.get("designation"));
			editorModel.setDesignation((String) editorRow.get("department"));
			editorModel.setCountry((String) editorRow.get("country"));
			editorModel.setContactNo((String) editorRow.get("contactno"));
			if(editorRow.get("isChiefEditor") != null) {
				editorModel.setChiefEditor((Boolean)editorRow.get("isChiefEditor"));
			}
			
			editorModel.setJournalId((Integer) editorRow.get("jid"));
			
			editorModels.add(editorModel);
		}
		
		return editorModels;
	}

	public void deleteEditor(int editorId) {
		
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