package com.journal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.Journal;
import com.journal.model.Submission;

public class JournalJDBCTemplate {

	
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

	public void saveJournal(Journal journal) {
		String query = "insert into JOURNAL(journalName, journalIcon, journalIconFileName, journalDescription, journalLongDescription, journalBannerImage, journalBannerImageFileName) values" +
				"(?, ?, ?, ?, ?, ? ,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, journal.getJournalName(), journal.getJournalIcon(), journal.getJournalIconFileName(), 
				journal.getJournalDescription(), journal.getJournalLongDescription(), journal.getJournalBannerImage(), journal.getJournalBannerImageFileName());
		
		System.out.println("Submission uploaded");
	}

	public List<Journal> getAllJournals() {
		
		String query = "select id, journalName, journalDescription, journalLongDescription from JOURNALS";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);
		
		List<Map<String, Object>> journalRows = jdbcTemplate.queryForList(query);
		
		List<Journal> journals = new ArrayList<Journal>();
		
		for (Map<String, Object> journalRow : journalRows) {
			
			Journal journal = new Journal();
			journal.setId((Integer) journalRow.get("id"));
			journal.setJournalName((String) journalRow.get("journalName"));
			journal.setJournalDescription((String) journalRow.get("journalDescription"));
			journal.setJournalLongDescription((String) journalRow.get("journalLongDescription"));
			
			journals.add(journal);
		}
		
		return journals;
	}

}
