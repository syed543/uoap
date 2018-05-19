package com.journal.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.Submission;

public class SubmissionJDBCTemplate {
	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void save(Submission submission) {
		String query = "insert into submission(email, journal, articleType, menuScriptTitle, abstractName, attachmentFileName, attachment) values" +
						"(?, ?, ?, ?, ?, ? ,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, submission.getEmail(), submission.getJournal(), submission.getArticleType(), 
				submission.getMenuScriptTitle(), submission.getAbstractName(), submission.getAttachmentFileName(), submission.getAttachment());
		
		System.out.println("Submission uploaded");
	}
}