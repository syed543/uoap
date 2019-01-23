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

	public void saveJournal(Journal journal) {
		String query = "insert into JOURNAL(journalName, journalIcon, journalIconFileName, journalDescription, journalLongDescription, journalBannerImage, journalBannerImageFileName) values" +
				"(?, ?, ?, ?, ?, ? ,?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, journal.getJournal_name(), journal.getJournalIcon(), journal.getJournalIconFileName(), 
				journal.getJournal_description(), journal.getJournal_long_description(), journal.getJournalBannerImage(), 
				journal.getJournalBannerImageFileName());
		
		String auto = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'journal' AND TABLE_NAME = 'JOURNAL'";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		journal.setId(autoInt - 1);
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
	
	public Journal getJournalById(int journalId) {
		
		String query = "select id, journalName, journalDescription, journalLongDescription from JOURNAL where id = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		Journal journal = jdbcTemplate.queryForObject(query, new Object[] {journalId}, Journal.class);
		
		return journal;
	}
	
	public void updateJournal(Journal journal) {
		
		List<Object> params = new ArrayList<Object>(8);
		
		String query = "update JOURNAL set journalName = ?,  journalDescription = ?, journalLongDescription = ? ";
		params.add(journal.getJournal_name());
		params.add(journal.getJournal_description());
		params.add(journal.getJournal_long_description());
		
		if (journal.getJournalIcon() != null) {
			query = query + ", journalIcon = ?, journalIconFileName = ? ";
			params.add(journal.getJournalIcon());
			params.add(journal.getJournalIconFileName());
		}
		
		if (journal.getJournalBannerImage() != null) {
			query = query + ", journalBannerImage = ?, journalBannerImageFileName = ?";
			params.add(journal.getJournalBannerImage());
			params.add(journal.getJournalBannerImageFileName());
		}
		
		query = query.concat(" where id = ?");
		params.add(journal.getId());
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, params.toArray(new Object[0]));
	}
	
	public void deleteJournal(int journalId) {
		
		String query = "delete from JOURNAL where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {journalId});
	}

}
