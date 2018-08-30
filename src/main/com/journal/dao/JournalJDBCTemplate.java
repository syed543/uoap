package com.journal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.Journal;

public class JournalJDBCTemplate {

	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public Integer saveJournal(Journal journal) {
		String query = "insert into JOURNAL(journalName, journalIcon, journalIconFileName, journalDescription, journalLongDescription, journalBannerImage, journalBannerImageFileName) values" +
				"(?, ?, ?, ?, ?, ? ,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, journal.getJournal_name(), journal.getJournalIcon(), journal.getJournalIconFileName(), 
				journal.getJournal_description(), journal.getJournal_long_description(), journal.getJournalBannerImage(), 
				journal.getJournalBannerImageFileName());
		
		String auto = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'journal' AND TABLE_NAME = 'JOURNAL'";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		return autoInt - 1;
	}

	public List<Journal> getAllJournals() {
		
		String query = "select id, journalName, journalDescription, journalLongDescription from JOURNAL";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> journalRows = jdbcTemplate.queryForList(query);
		
		List<Journal> journals = new ArrayList<Journal>();
		
		for (Map<String, Object> journalRow : journalRows) {
			
			Journal journal = new Journal();
			journal.setId((Integer) journalRow.get("id"));
			journal.setJournal_name((String) journalRow.get("journalName"));
			journal.setJournal_description((String) journalRow.get("journalDescription"));
			journal.setJournal_long_description((String) journalRow.get("journalLongDescription"));
			
			journals.add(journal);
		}
		
		return journals;
	}
	
	public void updateJournal(Journal journal) {
		
		String query = "update JOURNAL set journalName = ?, journalIcon = ?, journalIconFileName = ?, journalDescription = ?, journalBannerImage = ?, journalBannerImageFileName = ? where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, journal.getJournal_name(), journal.getJournalIcon(), journal.getJournalIconFileName(),
				journal.getJournal_description(), journal.getJournal_long_description(), journal.getJournalBannerImage(),
				journal.getJournalBannerImageFileName());
		
	}

}
