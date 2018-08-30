package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journal.dao.ReviewerJDBCTemplate;
import com.journal.dao.record.ReviewerRecord;
import com.journal.model.ReviewerModel;
import com.journal.utils.JournalUtil;

public class ReviewerController {
	
	@Autowired
	private ReviewerJDBCTemplate reviewerJDBCTemplate;
	
	@RequestMapping(value="/addReviewer", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReviewer(@RequestBody ReviewerModel reviewerModel) throws IOException {
		
		ReviewerRecord record = reviewerJDBCTemplate.getReviewerByMailId(reviewerModel.getEmail());
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (record == null) {
			
			String password = JournalUtil.generatePassword();
			
			reviewerModel.setPassword(password);
			reviewerModel.setGeneratedPass("y");
			
			reviewerJDBCTemplate.saveReviewer(reviewerModel);
			result.put("status", "200");
			result.put("message", "Reviewer added succesfully");

		} else {
			
			result.put("status", "200");
			result.put("message", "Reviewer exists");
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/updateReviewer", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateReviewer(@RequestBody ReviewerModel reviewerModel) throws IOException {
		
		reviewerJDBCTemplate.updateReviewer(reviewerModel);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "200");
		result.put("message", "Reviewer updated succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/deleteReviewer", method=RequestMethod.GET)
	public Map<String, Object> deleteReviewer(String reviewerId) {
		
		reviewerJDBCTemplate.deleteReviewer(reviewerId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "200");
		result.put("message", "Reviewer deleted succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/getReviewers", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReviewers() {
		
		List<ReviewerModel> reviewers = reviewerJDBCTemplate.getReviewers();
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", reviewers);
		result.put("count", reviewers.size());

		return result;
	}
}