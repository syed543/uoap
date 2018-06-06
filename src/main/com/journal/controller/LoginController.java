package com.journal.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journal.dao.UserJDBCTemplate;
import com.journal.model.User;
import com.journal.utils.JournalMailUtil;
import com.journal.utils.JournalUtil;

@Controller
public class LoginController {
	
	@Autowired
	private UserJDBCTemplate userJDBCTemplate;
	
	@RequestMapping(value="/forgotPassword", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, String>forgotPassword(@RequestParam String emailId) {
		
		User user = userJDBCTemplate.getUserByEmailId(emailId);
		
		Map<String, String> result = new HashMap<String, String>();
		
		if (user == null) {
			
			result.put("status", "User with this emailId doesn't exists");
			
			return result;
		} else {
			
			String password = JournalUtil.generatePassword();
			user.setPassword(password);
			
			userJDBCTemplate.updatePassword(user);
			JournalMailUtil.sendMail(emailId, "Your new Password", "Your New password is  : " + password);
			
			result.put("status", "New password is sent to your email");
			return result;
		}
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> login(@RequestParam String username, @RequestParam String password) {
		
		User user = userJDBCTemplate.getUserByEmailId(username);
		
		Map<String, Object> result = new HashMap<String, Object>();
		if (user != null) {
			result.put("status", 200);
			result.put("message", "Login successful");
			result.put("body", user);
		} else {
			
			result.put("status", 404);
			result.put("message", "User not found");
		}
		
		return result;
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