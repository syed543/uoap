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
import com.journal.dao.record.MenuScriptRecord;
import com.journal.model.MenuScriptModel;

public class MenuScriptTemplate {

	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public MenuScriptRecord saveMenuScript(MenuScriptRecord menuScriptRecord) {
		String query = "insert into menuscript(submitterId, journalid, menuScriptTitle, abstractTitle, attachment, attachmentName) values" +
				"(?, ? ,?, ?, ?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, menuScriptRecord.getSubmitterId(), menuScriptRecord.getJournalId(), menuScriptRecord.getMenuScriptTitle(), 
				menuScriptRecord.getAbstractData(), menuScriptRecord.getMenuScriptData(), menuScriptRecord.getMenuScriptFileName());
		
		String auto = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'journal' AND TABLE_NAME = 'menuscript'";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		menuScriptRecord.setId(autoInt - 1);
		
		return menuScriptRecord;
	}
	
	public MenuScriptRecord updateMenuScript(MenuScriptRecord menuScriptRecord) {
		String query = "update menuscript set journalid = ?, menuScriptTitle = ?, abstractTitle = ?, attachment =? , attachmentName = ? where id = ?";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, menuScriptRecord.getJournalId(), menuScriptRecord.getMenuScriptTitle(), menuScriptRecord.getAbstractData(), 
				menuScriptRecord.getMenuScriptData(), menuScriptRecord.getMenuScriptFileName(), menuScriptRecord.getId());
		
		return menuScriptRecord;
	}

	public List<MenuScriptModel> getAllMenuScripts() {
		
		String query = "select m.id, firstName, lastName, menuScriptTitle, journalName from " + 
				"submitter s, " + 
				"menuscript m, " + 
				"journal j " + 
				"where m.submitterId = s.id " + 
				"and m.journalid = j.id";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> menuScriptRows = jdbcTemplate.queryForList(query);
		
		List<MenuScriptModel> menuScriptModels = new ArrayList<MenuScriptModel>();
		
		for (Map<String, Object> menuScriptRow : menuScriptRows) {
			
			MenuScriptModel menuScriptModel = new MenuScriptModel();
			menuScriptModel.setId((Integer) menuScriptRow.get("id"));
			menuScriptModel.setfName((String) menuScriptRow.get("firstname"));
			menuScriptModel.setlName((String) menuScriptRow.get("lastName"));
			menuScriptModel.setMenuTitle((String) menuScriptRow.get("menuScriptTitle"));
			menuScriptModel.setJournalName((String) menuScriptRow.get("journalName"));
			menuScriptModels.add(menuScriptModel);
		}
		
		return menuScriptModels;
	}
	
	public List<MenuScriptModel> getMenuScriptsByEmail(String email) {
		
		String query = "select m.id, firstName, lastName, menuScriptTitle, journalName from " + 
				"submitter s, " + 
				"menuscript m, " + 
				"journal j " + 
				"where m.submitterId = s.id " + 
				"and m.journalid = j.id and s.email = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		try {
			
			List<MenuScriptModel> menuScripts = jdbcTemplate.queryForList(query, new Object[] {email}, MenuScriptModel.class);
			
			System.out.println("Menu scripts : " + menuScripts.size() + " for email : " + email);
			return menuScripts;
		} catch (EmptyResultDataAccessException eae) {
			
		}
		
		return null;
		
//		List<MenuScriptModel> menuScriptModels = new ArrayList<MenuScriptModel>();
//		
//		for (Map<String, Object> menuScriptRow : menuScriptRows) {
//			
//			MenuScriptModel menuScriptModel = new MenuScriptModel();
//			menuScriptModel.setId((Integer) menuScriptRow.get("id"));
//			menuScriptModel.setfName((String) menuScriptRow.get("firstname"));
//			menuScriptModel.setlName((String) menuScriptRow.get("lastName"));
//			menuScriptModel.setMenuTitle((String) menuScriptRow.get("menuScriptTitle"));
//			menuScriptModel.setJournalName((String) menuScriptRow.get("journalName"));
//			menuScriptModels.add(menuScriptModel);
//		}
//		
//		return menuScriptModels;
	}

	public MenuScriptModel getMenuScriptById(Integer menuScriptId) {
		
		String query = "select m.id as id, firstName, lastName, menuScriptTitle, abstractTitle, journalName  from " + 
				"submitter s, " + 
				"menuscript m, " + 
				"journal j " + 
				"where m.submitterId = s.id " + 
				"and m.journalid = j.id and m.id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		MenuScriptModel model = jdbcTemplate.queryForObject(query, new Object[] {menuScriptId}, MenuScriptModel.class);
		
		return model;
	}

	public void deleteMenuScript(Integer menuScriptId) {
		
		String query  = "delete from menuscript where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {menuScriptId});
	}
}