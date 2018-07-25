package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journal.dao.JournalJDBCTemplate;
import com.journal.model.Journal;

@Controller
public class JournalController {
	
	@Autowired
	private JournalJDBCTemplate journalJDBCTemplate;
	
	@RequestMapping(value="/addJournal", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
//	public Map<String, Object> addJournal(@ModelAttribute Journal journal, @RequestBody Journal journalData) throws IOException {
	public Map<String, Object> addJournal(@RequestParam String data, @RequestPart(required=false) MultipartFile icon, @RequestPart(required=false) MultipartFile banner) throws IOException {
		
		Journal journal = new ObjectMapper().readValue(data, Journal.class);
	
		if (icon != null) {
			journal.setJournalIcon(icon.getBytes());
			journal.setJournalIconFileName(icon.getOriginalFilename());
		}
		
		if (banner != null) {
			journal.setJournalBannerImage(banner.getBytes());
			journal.setJournalBannerImageFileName(banner.getOriginalFilename());
		}
		
		journalJDBCTemplate.saveJournal(journal);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Journal added succesfully");
		
		System.out.println("Journal added");
		return result;
	}

	@RequestMapping(value="/journals", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> journals() {
		
		List<Journal> journals  = journalJDBCTemplate.getAllJournals();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", journals);

		return result;
	}
	
	@RequestMapping(value="/updateJournal", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateJournal(@RequestParam Journal journal) {
		
		if (journal.getId() != null) {
			
			journalJDBCTemplate.updateJournal(journal);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("status", "200");
			result.put("message", "Journal added succesfully");

			return result;
		}
		
		return null;
	}
}