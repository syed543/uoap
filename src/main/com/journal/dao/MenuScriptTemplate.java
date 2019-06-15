package com.journal.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.dao.record.MenuScriptRecord;
import com.journal.dao.record.SubmitterRecord;
import com.journal.model.MenuScriptModel;
import com.journal.utils.JournalConstants;
import com.journal.utils.JournalUtil;


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
		String query = "insert into menuscript(submitterId, journalid, menuScriptTitle, status, abstractTitle, attachment, attachmentName, articleType) values" +
				"(?, ? ,?, ?, ?, ?, ?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, menuScriptRecord.getSubmitterId(), menuScriptRecord.getJournalId(), menuScriptRecord.getMenuScriptTitle(), menuScriptRecord.getStatus(),
				menuScriptRecord.getAbstractData(), menuScriptRecord.getMenuScriptData(), menuScriptRecord.getMenuScriptFileName(), menuScriptRecord.getArticleType());
		
		String auto = "select max(id) from MENUSCRIPT";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		menuScriptRecord.setId(autoInt);
		
		return menuScriptRecord;
	}
	
	public SubmitterRecord getMenuScriptSubmiiter(int menuScriptId) {
		
		String query = "select title, firstName, lastName, email from submitter s, menuscript m where s.id = m.submitterId and m.id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		SubmitterRecord record = (SubmitterRecord) jdbcTemplate.queryForObject(query, new Object[] {menuScriptId}, new BeanPropertyRowMapper(SubmitterRecord.class));
		
		return record;
	}
	
	public MenuScriptRecord updateMenuScript(String userType, final MenuScriptRecord menuScriptRecord) {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		if (JournalConstants.ADMIN.equals(userType) || JournalConstants.EDITOR.equals(userType)) {
		
			String query = "update menuscript set feedback = ?, status = ? where id = ?";
			jdbcTemplate.update(query, menuScriptRecord.getFeedBack(), 2, menuScriptRecord.getId());
			
			if (menuScriptRecord.getReviewers() != null && menuScriptRecord.getReviewers().length > 0) {
				
				String deleteQuery = "delete from menuScriptAssignedReviewers where menuscriptid = ?";
				jdbcTemplate.update(deleteQuery, menuScriptRecord.getId());
				
				String insertQuery = "insert into menuScriptAssignedReviewers(menuscriptid, reviewerid, assignId) values (?, ?, ?)";
				
				menuScriptRecord.setUniqueIds(JournalUtil.getUniqueIds(menuScriptRecord.getReviewers().length));
				
				jdbcTemplate.batchUpdate(insertQuery, new BatchPreparedStatementSetter() {
					
					@Override
					public void setValues(PreparedStatement ps, int i) throws SQLException {
						ps.setInt(1, menuScriptRecord.getId());
						ps.setInt(2, menuScriptRecord.getReviewers()[i]);
						ps.setString(3, menuScriptRecord.getUniqueIds()[i]);
					}
					
					@Override
					public int getBatchSize() {
						return menuScriptRecord.getReviewers().length;
					}
				});
			}
		} else if (JournalConstants.REVIEWER.equals(userType)) {
			
			String query = "update menuscript set feedback = ? where id = ?";
			jdbcTemplate.update(query, menuScriptRecord.getFeedBack(), menuScriptRecord.getId());
		} else if (JournalConstants.SUBMITTER.equals(userType)) {
			String query = "update menuscript set menuScriptTitle = ?, abstractTitle = ?, articleType = ? where id = ?";
			jdbcTemplate.update(query, new Object[] {menuScriptRecord.getMenuScriptTitle(), menuScriptRecord.getAbstractData(), menuScriptRecord.getArticleType(), menuScriptRecord.getId()});
		}
		
		return menuScriptRecord;
	}
	
	public void updateStatus(int status, boolean unAssignReviewer, int menuScriptId) {
		
		String query = "update menuscript set status = ?" + (unAssignReviewer ? " , reviewer = NULL " : "" ) + " where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, status, menuScriptId);
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
		
			query = "select m.id, s.firstName firstName, s.lastName lastName, menuScriptTitle, abstractTitle, feedback, journalName, articleType, concat(r.firstName, ' ', r.lastName) as reivewerName, r.email reviewerEmail, status, j.id as jid, reviewer from " + 
				"submitter s, " + 
				"journal j, " + 
				"menuscript m left join reviewer r on m.reviewer = r.id " + 
				"where m.submitterId = s.id " + 
				"and m.journalid = j.id";
		} else if (JournalConstants.REVIEWER.equals(userType)) {
			
			query = "select m.id, s.firstName firstName, s.lastName lastName, menuScriptTitle, abstractTitle, feedback, journalName, articleType, concat(r.firstName, ' ', r.lastName) as reivewerName, r.email reviewerEmail, status, j.id as jid, reviewer from " + 
					"submitter s, " + 
					"journal j, " +
					"menuscript m left join reviewer r on m.reviewer = r.id " + 
					"where m.submitterId = s.id " + 
					"and m.journalid = j.id and m.reviewer = r.id and r.email = ?";
		} else if (JournalConstants.EDITOR.equals(userType)) {
			
			query = "select m.id, s.firstName firstName, s.lastName lastName, menuScriptTitle, abstractTitle, feedback, journalName, articleType, concat(r.firstName, ' ', r.lastName) as reivewerName, r.email reviewerEmail, status, j.id as jid, reviewer from submitter s, " + 
					"journal j, editor e, menuscript m left join reviewer r on m.reviewer = r.id " + 
					" where s.id = m.submitterId and m.journalid = j.id and m.journalid = e.journalId and e.email = ?"; 
		} else if (JournalConstants.SUBMITTER.equals(userType)) {
			
			query = "select m.id, s.firstName firstName, s.lastName lastName, menuScriptTitle, abstractTitle, feedback, journalName, articleType, concat(r.firstName, ' ', r.lastName) as reivewerName, r.email reviewerEmail, status, j.id as jid, reviewer from submitter s, journal j," + 
					"menuscript m left join reviewer r on m.reviewer = r.id " + 
					" where m.submitterId = s.id and m.journalid = j.id and s.email = ?"; 
		}
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> menuScripts = null;
		
		try {
			
			if (JournalConstants.ADMIN.equals(userType)) {
				System.out.println("Admin query: " + query);
				menuScripts = jdbcTemplate.queryForList(query);
			} else if (JournalConstants.REVIEWER.equals(userType) || JournalConstants.EDITOR.equals(userType) || JournalConstants.SUBMITTER.equals(userType)) {
				System.out.println("Others query:"+ query);
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
			menuScriptModel.setArticleType((String) menuScriptRow.get("articleType"));
			menuScriptModel.setReviewerName((String) menuScriptRow.get("reivewerName"));
			menuScriptModel.setReviewerEmail((String) menuScriptRow.get("reviewerEmail"));
			if (menuScriptRow.get("reviewer") != null) {
				menuScriptModel.setReviewerId((Integer) menuScriptRow.get("reviewer"));
			}
			Integer status = (Integer) menuScriptRow.get("status");
			if (status != null) {
				menuScriptModel.setStatus(status);
			} else {
				menuScriptModel.setStatus(1);
			}
			menuScriptModels.add(menuScriptModel);
		}
		
		return menuScriptModels;
	}
	
	public MenuScriptModel getMenuScriptById(Integer menuScriptId) {
		
		String query = "select m.id as id, firstName, lastName, menuScriptTitle, abstractTitle, journalName, articleType, reviewer as reviewerId  from " + 
				"submitter s, " + 
				"menuscript m, " + 
				"journal j " + 
				"where m.submitterId = s.id " + 
				"and m.journalid = j.id and m.id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		MenuScriptModel model = jdbcTemplate.queryForObject(query, new Object[] {menuScriptId}, new BeanPropertyRowMapper(MenuScriptModel.class));
		
		return model;
	}

	public void deleteMenuScript(Integer menuScriptId) {
		
		String query  = "delete from menuscript where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {menuScriptId});
	}

	public void assignReviewerToMenuScript(String menuScriptId, String reviewerid) {
		
		String query  = "update menuscript set reviewer = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {reviewerid, menuScriptId});
	}
}