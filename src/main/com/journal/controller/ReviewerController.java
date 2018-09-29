package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journal.dao.ReviewerJDBCTemplate;
import com.journal.model.Reviewer;

public class ReviewerController {
	
private ReviewerJDBCTemplate reviewerJDBCTemplate;
	
	@RequestMapping(value="/addReviewer", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReviewer(@RequestBody Reviewer reviewer) throws IOException {
		
		reviewerJDBCTemplate.addReviewer(reviewer);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "200");
		result.put("message", "Reviewer added succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/updateReviewer", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateReviewer(@RequestBody Reviewer reviewer) throws IOException {
		
		reviewerJDBCTemplate.updateReviewer(reviewer);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "200");
		result.put("message", "Reviewer updated succesfully");
		
		return result;
	}
}
