package com.journal.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journal.dao.EditorJDBCTemplate;
import com.journal.dao.ReviewerJDBCTemplate;
import com.journal.dao.SubmitterJDBCTemplate;
import com.journal.dao.UserJDBCTemplate;
import com.journal.dao.record.EditorRecord;
import com.journal.dao.record.ReviewerRecord;
import com.journal.dao.record.SubmitterRecord;
import com.journal.model.User;
import com.journal.requests.Login;
import com.journal.utils.JournalMailUtil;
import com.journal.utils.JournalUtil;

@Controller
public class LoginController {
	
	@Autowired
	private UserJDBCTemplate userJDBCTemplate;
	
	@Autowired
	private SubmitterJDBCTemplate submitterJDBCTemplate;
	
	@Autowired
	private ReviewerJDBCTemplate reviewerJDBCTemplate;
	
	@Autowired
	private EditorJDBCTemplate editorJDBCTemplate;
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String>forgotPassword(@RequestParam String emailId, @RequestParam String userType) {
	
//		User user = userJDBCTemplate.getUserByEmailId(emailId);
		
		Object userRecord = null;
		
		if ("Submitter".equals(userType)) {
			
			userRecord = submitterJDBCTemplate.getSubmitterByEmail(emailId);
		} else if ("Reviewer".equals(userType)) {
			
			userRecord  = reviewerJDBCTemplate.getReviewerByMailId(emailId);
		} else if ("Editor".equals(userType)) {
			
			userRecord   = editorJDBCTemplate.getEditorByMailId(emailId);
		}
		
		
		Map<String, String> result = new HashMap<String, String>();
		
		if (userRecord  == null) {
			
			result.put("status", "User with this emailId doesn't exists");
			
			return result;
		} else {
			
			String password = JournalUtil.generatePassword();
			
			if ("Submitter".equals(userType)) {
				
				SubmitterRecord submitterRecord = (SubmitterRecord) userRecord;
				submitterRecord.setPassword(password);
				submitterJDBCTemplate.updatePassword(submitterRecord);
			} else if ("Reviewer".equals(userType)) {
				
				ReviewerRecord reviewerRecord = (ReviewerRecord) userRecord;
				reviewerRecord.setPassword(password);
				reviewerJDBCTemplate.updatePassword(reviewerRecord);
			} else if ("Editor".equals(userType)) {
				
				EditorRecord editorRecord = (EditorRecord) userRecord;
				editorRecord.setPassword(password);
				editorJDBCTemplate.setPassword(editorRecord);
			}
			
			JournalMailUtil.sendMail(emailId, "New Password", "Your New password is  : " + password);
			
			result.put("status", "New password is sent to your email");
			return result;
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestBody Login login, HttpServletRequest request) {

		SubmitterRecord submitterRecord = submitterJDBCTemplate.getSubmitterByEmail(login.getEmailid());
		Map<String, Object> result = new HashMap<String, Object>();
		
		User user = new User();
	
		if (submitterRecord != null && submitterRecord.getPassword().equals(login.getPassword())) {
			result.put("statusCode", 200);
			result.put("message", "Login successful");
			
			user.setfName(submitterRecord.getFirstName());
			user.setlName(submitterRecord.getLastName());
			user.setEmail(submitterRecord.getEmail());
			user.setPassword(submitterRecord.getPassword());
			Map<String, List> rolesMap  = getRoles(login.getEmailid());
			List<String> roles  = rolesMap.get("roles");
			
			if (roles != null) {
				
				if (roles.contains("Submitter")) {
					user.setUsertype("author");
				}
			}
			
//			user.setUsertype("admin");
			user.setPassword(null);
			
			HttpSession session = request.getSession(false);
			
			if (session != null) {
				session.invalidate();
			}
			
			HttpSession httpSession = request.getSession(true);
			httpSession.setAttribute("user", user);
			
			result.put("body", user);
			return result;
		}

		result.put("statusCode", 404);
		result.put("message", "User not found/Invalid Password");
		return result;
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logout(HttpServletRequest request, HttpServletResponse resposne) {
		
		HttpSession session = request.getSession(false);
		
		if (session != null) {
			session.invalidate();
		}
		return;
	}
	
	@RequestMapping(value="/getRoles", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, List> getRoles(@RequestParam String email) {
		
		SubmitterRecord submitterRecord = submitterJDBCTemplate.getSubmitterByEmail(email);
//		ReviewerRecord reviewerRecord = reviewerJDBCTemplate.getReviewerByMailId(email);
//		EditorRecord editorRecord = editorJDBCTemplate.getEditorByMailId(email);
		
		List<String> roles  = new ArrayList<String>();
		
		if (submitterRecord != null) {
			roles.add("Submitter");
		}
		
//		if (reviewerRecord != null) {
//			roles.add("Reviewer");
//		}
		
//		if (editorRecord != null) {
			roles.add("Editor");
//		}
		
		Map<String, List> results = new HashMap<String, List>();
		
		results.put("roles", roles);
		
		return results;
	}
	
	@RequestMapping(value="/changePassword", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> changePassword(@RequestParam String emailId, @RequestParam String password) {
		
		User user = new User();
		user.setEmail(emailId);
		user.setPassword(password);
		
		userJDBCTemplate.updatePassword(user);
		
		Map<String, String> result = new HashMap<String, String>();
		
		result.put("status", "Password updated successfully");
		
		return result;
	}
}