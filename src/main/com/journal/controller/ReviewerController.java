package com.journal.controller;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journal.dao.ReviewerJDBCTemplate;
import com.journal.dao.record.ReviewerRecord;
import com.journal.model.ReviewerModel;
import com.journal.utils.Encryptor;
import com.journal.utils.JournalMailUtil;
import com.journal.utils.JournalUtil;

@Controller
public class ReviewerController {
	
	@Autowired
	private ReviewerJDBCTemplate reviewerJDBCTemplate;
	
	@RequestMapping(value="/addReviewer", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addReviewer(@RequestBody ReviewerModel reviewerModel) throws IOException, InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		ReviewerRecord record = reviewerJDBCTemplate.getReviewerByMailId(reviewerModel.getEmail());
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (record == null) {
			
			String password = JournalUtil.generatePassword();
			
			reviewerModel.setPassword(Encryptor.getEncodedEncrytedString(password));
			reviewerModel.setGeneratedPass("y");
			
			reviewerJDBCTemplate.saveReviewer(reviewerModel);
			
			try {
				
				JournalMailUtil.sendMail(reviewerModel.getEmail(), "Reviewer Registration done", "The generated password is :" + password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			result.put("statusCode", "200");
			result.put("message", "Reviewer added succesfully");

		} else {
			
			result.put("statusCode", "200");
			result.put("message", "Reviewer exists");
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/updateReviewer/{reviewerId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateReviewer(@PathVariable int reviewerId, @RequestBody ReviewerModel reviewerModel) throws IOException {
		
		if (reviewerId > 0) {
			reviewerModel.setId(reviewerId);
			reviewerJDBCTemplate.updateReviewer(reviewerModel);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Reviewer updated succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/deleteReviewer/{reviewerId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteReviewer(@PathVariable int reviewerId) {
		

		if (reviewerId > 0) {
			reviewerJDBCTemplate.deleteReviewer(reviewerId);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Reviewer deleted succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/reviewers", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReviewers() {
		
		List<ReviewerModel> reviewers = reviewerJDBCTemplate.getReviewers();
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", reviewers);
		result.put("count", reviewers.size());

		return result;
	}
	
	@RequestMapping(value="/getReviewersByJournalId/{journalId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getReviewersByJournalId(@PathVariable int journalId) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");

		
		if (journalId > 0) {
			
			List<ReviewerRecord> reviewers = reviewerJDBCTemplate.getReviewersByJournalId(journalId);
			result.put("data", reviewers);
			result.put("count", reviewers.size());
		}
		return result;
	}
}