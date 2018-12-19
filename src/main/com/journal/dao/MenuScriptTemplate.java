package com.journal.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.dao.record.MenuScriptRecord;
import com.journal.dao.record.SubmitterRecord;
import com.journal.model.MenuScriptModel;
import com.journal.utils.JournalConstants;

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
		String query = "insert into menuscript(submitterId, journalid, menuScriptTitle, status, abstractTitle, attachment, attachmentName) values" +
				"(?, ? ,?, ?, ?, ?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, menuScriptRecord.getSubmitterId(), menuScriptRecord.getJournalId(), menuScriptRecord.getMenuScriptTitle(), menuScriptRecord.getStatus(),
				menuScriptRecord.getAbstractData(), menuScriptRecord.getMenuScriptData(), menuScriptRecord.getMenuScriptFileName());
		
		String auto = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'journal' AND TABLE_NAME = 'menuscript'";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		menuScriptRecord.setId(autoInt - 1);
		
		return menuScriptRecord;
	}
	
	public SubmitterRecord getMenuScriptSubmiiter(int menuScriptId) {
		
		String query = "select title, firstName, lastName, email from submitter s, menuscript m where s.id = m.submitterId and m.id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		SubmitterRecord record = (SubmitterRecord) jdbcTemplate.queryForObject(query, new Object[] {menuScriptId}, new BeanPropertyRowMapper(SubmitterRecord.class));
		
		return record;
	}
	
	public MenuScriptRecord updateMenuScript(String userType, MenuScriptRecord menuScriptRecord) {
		
		if (!(JournalConstants.ADMIN.equals(userType) || JournalConstants.REVIEWER.equals(userType) || JournalConstants.EDITOR.equals(userType))) {
			return menuScriptRecord;
		}
		String query = "update menuscript set feedback = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, menuScriptRecord.getFeedBack(), menuScriptRecord.getId());
		
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
	
	public List<MenuScriptModel> getMenuScriptsByEmailAndUserType(String userType, String email) {
		
		String query = null;
		
		if (JournalConstants.ADMIN.equals(userType)) {
		
			query = "select m.id, firstName, lastName, menuScriptTitle, abstractTitle, feedback, journalName, status, j.id as jid from " + 
				"submitter s, " + 
				"menuscript m, " + 
				"journal j " + 
				"where m.submitterId = s.id " + 
				"and m.journalid = j.id";
		} else if (JournalConstants.REVIEWER.equals(userType)) {
			
			query = "select m.id, r.firstName, r.lastName, menuScriptTitle, abstractTitle, feedback, journalName, status, j.id as jid from " + 
					"submitter s, " + 
					"menuscript m, " + 
					"journal j, " +
					"reviewer r " +
					"where m.submitterId = s.id " + 
					"and m.journalid = j.id and m.reviewer = r.id and s.email = ?";
		} else if (JournalConstants.EDITOR.equals(userType)) {
			
			query = "select m.id, e.firstName, e.lastName, menuScriptTitle, abstractTitle, feedback, journalName, status, j.id as jid from submitter s, menuscript m," + 
					"journal j, editor e where s.id = m.submitterId and m.journalid = j.id and m.journalid = e.journalId and e.email = ?"; 
		} else if (JournalConstants.SUBMITTER.equals(userType)) {
			
			query = "select m.id, s.firstName, s.lastName, menuScriptTitle, abstractTitle, feedback, journalName, status, j.id as jid from submitter s, menuscript m," + 
					"journal j where m.submitterId = s.id and m.journalid = j.id and s.email = ?"; 
		}
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> menuScripts = null;
		
		try {
			
			if (JournalConstants.ADMIN.equals(userType)) {
				menuScripts = jdbcTemplate.queryForList(query);
			} else if (JournalConstants.REVIEWER.equals(userType) || JournalConstants.EDITOR.equals(userType) || JournalConstants.SUBMITTER.equals(userType)) {
				menuScripts = jdbcTemplate.queryForList(query, new Object[] {email});
			}

		} catch (EmptyResultDataAccessException eae) {
			return Collections.emptyList();
		}
		
		if (menuScripts == null || menuScripts.size() == 0) {
			return Collections.emptyList();
		}
		
		List<MenuScriptModel> menuScriptModels = new ArrayList<MenuScriptModel>(menuScripts.size());
		
		for (Map<String, Object> menuScriptRow : menuScripts) {
			
			MenuScriptModel menuScriptModel = new MenuScriptModel();
			menuScriptModel.setId((Integer) menuScriptRow.get("id"));
			menuScriptModel.setfName((String) menuScriptRow.get("firstname"));
			menuScriptModel.setlName((String) menuScriptRow.get("lastName"));
			menuScriptModel.setMenuTitle((String) menuScriptRow.get("menuScriptTitle"));
			menuScriptModel.setAbstractTitle((String) menuScriptRow.get("abstractTitle"));
			menuScriptModel.setJournalName((String) menuScriptRow.get("journalName"));
			menuScriptModel.setFeedback((String) menuScriptRow.get("feedback"));
			menuScriptModel.setJournal((Integer) menuScriptRow.get("jid"));
			String status = (String) menuScriptRow.get("status");
			if (status != null) {
				menuScriptModel.setStatus(Integer.parseInt(status));
			} else {
				menuScriptModel.setStatus(1);
			}
			menuScriptModels.add(menuScriptModel);
		}
		
		return menuScriptModels;
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