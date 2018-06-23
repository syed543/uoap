package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.journal.dao.JournalJDBCTemplate;
import com.journal.model.Journal;

public class JournalController {
	
	private JournalJDBCTemplate journalJDBCTemplate;
	
	@RequestMapping(value="/addJournal", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>addJournal(@RequestParam String journalName, @RequestParam(required = false) MultipartFile journalIcon, 
			@RequestParam String journalDescription, @RequestParam String journalLongDescription,
			@RequestParam(required = false) MultipartFile journalBannerImage) throws IOException {
		
		Journal journal = new Journal();
		
		journal.setJournalName(journalName);
		journal.setJournalIconFileName(journalIcon.getOriginalFilename());
		journal.setJournalIcon(journalIcon.getBytes());
		journal.setJournalDescription(journalDescription);
		journal.setJournalLongDescription(journalLongDescription);
		journal.setJournalBannerImageFileName(journalBannerImage.getOriginalFilename());
		journal.setJournalBannerImage(journalBannerImage.getBytes());
		
		journalJDBCTemplate.saveJournal(journal);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "200");
		result.put("message", "Journal added succesfully");
		
		return result;
	}

	@RequestMapping(value="/journals", method=RequestMethod.GET)
	@ResponseBody
	public List<Journal> journals() {
		
		List<Journal> journals  = journalJDBCTemplate.getAllJournals();
		return journals;
	}
}
