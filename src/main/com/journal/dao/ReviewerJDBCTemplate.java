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

import com.journal.dao.record.ReviewerRecord;
import com.journal.model.ReviewerModel;

public class ReviewerJDBCTemplate {

	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public ReviewerRecord getReviewerByMailId(String mail) {
		
		String query = "Select id, firstName, lastName, email, password, generatedPass, country, journalId from Reviewer where email = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		try {
			
			ReviewerRecord record = (ReviewerRecord) jdbcTemplate.queryForObject(query, new Object[] {mail}, new BeanPropertyRowMapper(ReviewerRecord.class));
			return record;
		} catch (EmptyResultDataAccessException eae) {
			
		}
		return null;
	}
	
	public ReviewerRecord getReviewerById(int id) {
		
		String query = "Select id, firstName, lastName, email, password, generatedPass, country, journalId from Reviewer where id = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		try {
			
			ReviewerRecord record = (ReviewerRecord) jdbcTemplate.queryForObject(query, new Object[] {id}, new BeanPropertyRowMapper(ReviewerRecord.class));
			return record;
		} catch (EmptyResultDataAccessException eae) {
			
		}
		return null;
	}
	
	public List<ReviewerRecord> getReviewersByJournalId(int journalId) {
		
		String query = "Select id, firstName, lastName, email, country from Reviewer where journalId = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		try {
			
			//TODO - Use exatractor.
			List<Map<String, Object>> records = jdbcTemplate.queryForList(query, new Object[] {journalId});
			
			if (records != null && records.size() > 0) {
				
				List<ReviewerRecord> reviewerRecords = new ArrayList<ReviewerRecord>(records.size());
				for (Map<String, Object> record : records) {
					
					ReviewerRecord rec = new ReviewerRecord();
					rec.setId((Integer)record.get("id"));
					rec.setFirstName((String) record.get("firstName"));
					rec.setLastName((String) record.get("lastName"));
					rec.setEmail((String) record.get("email"));
					
					reviewerRecords.add(rec);
//					rec.set((String) record.get("country"));;
				}
				
				return reviewerRecords;
			}
		} catch (EmptyResultDataAccessException eae) {
			
		}
		return Collections.emptyList();
	}
	
	public void saveReviewer(ReviewerModel reviewerModel) {
		String query = "insert into Reviewer(firstName, lastName, email, password, generatedPass, country, journalId) values" +
						"(?, ?, ?, ?, ?, ?, ?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, reviewerModel.getFirstName(), reviewerModel.getLastName(), reviewerModel.getEmail(), 
				reviewerModel.getPassword(), reviewerModel.getGeneratedPass(),
				reviewerModel.getCountry(), reviewerModel.getJournalId());
		
		System.out.println("Reviewer created");
	}
	
	public void updateReviewer(ReviewerModel reviewerModel) {
		String query = "update Reviewer set firstName = ?, lastName = ?,  email = ?, journalId = ?, country = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, reviewerModel.getFirstName(), reviewerModel.getLastName(), 
				reviewerModel.getEmail(), reviewerModel.getJournalId(), reviewerModel.getCountry(), reviewerModel.getId());
		
		System.out.println("Reviewer updated");
	}

	public List<ReviewerModel> getReviewers() {
		
		String query = "Select r.id as id, firstName, lastName, email, journalId, journalName, country from Reviewer r, Journal j where r.journalId = j.id";

		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> reviewerRows = jdbcTemplate.queryForList(query);
		
		List<ReviewerModel> reviewerModels = new ArrayList<ReviewerModel>();
		
		for (Map<String, Object> menuScriptRow : reviewerRows) {
			
			ReviewerModel reviewerModel = new ReviewerModel();
			reviewerModel.setId((Integer) menuScriptRow.get("id"));
			reviewerModel.setFirstName((String) menuScriptRow.get("firstname"));
			reviewerModel.setLastName((String) menuScriptRow.get("lastName"));
			reviewerModel.setEmail((String) menuScriptRow.get("email"));
			reviewerModel.setCountry((String) menuScriptRow.get("country"));
			reviewerModel.setJournalId((Integer) menuScriptRow.get("journalId"));
			reviewerModel.setJournalName((String) menuScriptRow.get("journalName"));
			reviewerModels.add(reviewerModel);
		}
		
		return reviewerModels;
	}

	public void deleteReviewer(int reviewerId) {
		
		String query  = "delete from Reviewer where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {reviewerId});
	}

	public void updatePassword(ReviewerRecord reviewerRecord) {
		
		String query = "update reviewer set password = ? where email = ?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, reviewerRecord.getPassword(), reviewerRecord.getEmail());
		
		System.out.println("Reviewer new password is updated");
	}
}
