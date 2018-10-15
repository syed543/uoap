package com.journal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.dao.record.ReviewerRecord;
import com.journal.dao.record.SubmitterRecord;
import com.journal.model.ReviewerModel;
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
		
		String query = "Select id, firstName, lastName, email, postalAddress, password, generatedPass, country from Reviewer where email = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		try {
			
			ReviewerRecord record = (ReviewerRecord) jdbcTemplate.queryForObject(query, new Object[] {mail}, new BeanPropertyRowMapper(ReviewerRecord.class));
			return record;
		} catch (EmptyResultDataAccessException eae) {
			
		}
		return null;
	}

	public void saveReviewer(ReviewerModel reviewerModel) {
		String query = "insert into Reviewer(firstName, lastName, email, postalAddress, password, generatedPass, country) values" +
						"(?, ?, ?, ?, ?, ?, ?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, reviewerModel.getFirstName(), reviewerModel.getLastName(), reviewerModel.getEmail(), 
				reviewerModel.getPostalAddress(), reviewerModel.getPassword(), reviewerModel.getGeneratedPass(),
				reviewerModel.getCountry());
		
		System.out.println("Reviewer created");
	}
	
	public void updateReviewer(ReviewerModel reviewerModel) {
		String query = "update Reviewer set firstName = ?, lastName = ?, postalAddress = ?, password = ?,  country = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, reviewerModel.getFirstName(), reviewerModel.getLastName(), reviewerModel.getPostalAddress(), 
				reviewerModel.getPassword(), reviewerModel.getCountry(), reviewerModel.getId());
		
		System.out.println("Reviewer updated");
	}

	public List<ReviewerModel> getReviewers() {
		
		String query = "Select id, firstName, lastName, email, password, generatedPass, country from Reviewer";

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
			reviewerModels.add(reviewerModel);
		}
		
		return reviewerModels;
	}

	public void deleteReviewer(String reviewerId) {
		
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
