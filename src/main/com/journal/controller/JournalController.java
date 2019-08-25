package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.journal.dao.JournalJDBCTemplate;
import com.journal.model.Journal;
import com.journal.utils.JournalConstants;
import com.journal.utils.WebAppUtils;

@Controller
public class JournalController {
	
	@Autowired
	private JournalJDBCTemplate journalJDBCTemplate;
	
	@RequestMapping(value="/addJournal", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
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
		
		if (journal.getId() != null && journal.getId() > 0) {
			
			WebAppUtils.copyJournalTemplateFile(journal.getJournal_name());
			
			WebAppUtils.uploadFile(JournalConstants.JOURNAL_ICONS_FOLDER, journal.getId(), journal.getJournalIconFileName(), journal.getJournalIcon());
			
			WebAppUtils.uploadFile(JournalConstants.JOURNAL_BANNER_FOLDER, journal.getId(), journal.getJournalBannerImageFileName(), journal.getJournalBannerImage());
		}
		
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
		result.put("count", journals.size());

		return result;
	}

	@RequestMapping(value="/getJournalById/{journalId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getJournalById(@PathVariable int journalId) {
		
		Journal journal  = journalJDBCTemplate.getJournalById(journalId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", journal);

		return result;

	}
	
	@RequestMapping(value="/updateJournal/{journalId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateJournal(@RequestParam String data, @PathVariable("journalId") int journalId, 
			@RequestPart(required=false) MultipartFile icon, @RequestPart(required=false) MultipartFile banner) throws JsonParseException, JsonMappingException, IOException {
		
		Journal journal = new ObjectMapper().readValue(data, Journal.class);
		
		journal.setId(journalId);
		
		if (icon != null) {
			journal.setJournalIcon(icon.getBytes());
			journal.setJournalIconFileName(icon.getOriginalFilename());
		}
		
		if (banner != null) {
			journal.setJournalBannerImage(banner.getBytes());
			journal.setJournalBannerImageFileName(banner.getOriginalFilename());
		}
			
		if (journal.getId() != null) {
			
			journalJDBCTemplate.updateJournal(journal);
			
			if (icon != null) {
				WebAppUtils.uploadFile(JournalConstants.JOURNAL_ICONS_FOLDER, journal.getId(), journal.getJournalIconFileName(), journal.getJournalIcon());
			}
			
			if (banner != null) {
				WebAppUtils.uploadFile(JournalConstants.JOURNAL_BANNER_FOLDER, journal.getId(), journal.getJournalBannerImageFileName(), journal.getJournalBannerImage());
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("statusCode", "200");
			result.put("message", "Journal added succesfully");

			return result;
		}
		
		return null;
	}
	
	@RequestMapping(value="/deleteJournal/{journalId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteJournal(@PathVariable("journalId") int journalId) {
		System.out.println("Deleting journal with id : " + journalId);
		journalJDBCTemplate.deleteJournal(journalId);
		System.out.println("Deleted journal with id : " + journalId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Journal added succesfully");
		return result;
	}
	
	public void getJournal() {
		
	}
}