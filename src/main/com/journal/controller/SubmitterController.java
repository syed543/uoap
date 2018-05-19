package com.journal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.journal.dao.SubmissionJDBCTemplate;
import com.journal.dao.SubmitterJDBCTemplate;
import com.journal.model.Submission;
import com.journal.model.Submitter;
import com.journal.utils.JournalMailUtil;
import com.journal.utils.JournalUtil;

@Controller
public class SubmitterController {
	
	@Autowired
	private SubmitterJDBCTemplate submitterJDBCTemplate;
	
	@Autowired
	private SubmissionJDBCTemplate submissionJDBCTemplate;
	
	@RequestMapping(value = "/submitter", method=RequestMethod.GET)
	public String submitterEditor(ModelAndView model) {
		System.out.println("Reguest for Register editor received");
		return "registerSubmitter";
	}
	
	@RequestMapping(value = "/registerSubmitter", method=RequestMethod.POST)
	@ResponseBody
	public String registerSubmitter(@RequestParam String email, @RequestParam(required = false) String phoneNo, 
		@RequestParam String firstName, @RequestParam(required = false) String lastName, @RequestParam(required = false) String countryName, 
		@RequestParam String journal, @RequestParam String articleType, @RequestParam String menuScriptTitle, @RequestParam String abstractName,
		@RequestParam(required = false) MultipartFile attachment) throws IOException {
	
		Submitter existingSubmitter  = null;
		if (!StringUtils.isEmpty(email)) {
			existingSubmitter = submitterJDBCTemplate.getSubmitterByEmailId(email);
		}
		
		if (existingSubmitter == null) {
			Submitter submitter = new Submitter();
			submitter.setEmail(email);
			submitter.setPhoneNo(phoneNo);
			submitter.setFirstName(firstName);
			submitter.setLastName(lastName);
			submitter.setCountryName(countryName);
			String password = JournalUtil.generatePassword();
			submitter.setPassword(password);
			submitter.setGenerated("y");
			submitterJDBCTemplate.save(submitter);

			JournalMailUtil.sendMail(submitter.getEmail(), "Your Submission is received", "The generated password is :" + password);
		} 
		
		Submission submission = new Submission();
		
		submission.setEmail(email);
		submission.setJournal(journal);
		submission.setArticleType(articleType);
		submission.setMenuScriptTitle(menuScriptTitle);
		submission.setAbstractName(abstractName);
		submission.setAttachmentFileName(attachment.getOriginalFilename());
		submission.setAttachment(attachment.getBytes());
		submissionJDBCTemplate.save(submission);
		
		if (existingSubmitter != null) {
			JournalMailUtil.sendMail(email, "Your Submission is received", "Your another Submission is got uploaded");
		}
			
		return "{\"result\":\"Submission uploaded successfully\"}";
	}
}