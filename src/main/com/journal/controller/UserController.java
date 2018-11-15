package com.journal.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journal.dao.UserJDBCTemplate;
import com.journal.model.User;
import com.journal.utils.JournalMailUtil;
import com.journal.utils.JournalUtil;

@Controller
public class UserController {
	
	@Autowired
	private UserJDBCTemplate userJDBCTemplate;
	
	@RequestMapping(value = "/registerUser", method=RequestMethod.POST)
	@ResponseBody
	public String registerSubmitter(@RequestParam String email, @RequestParam(required = false) String phoneNo, 
		@RequestParam String firstName, @RequestParam(required = false) String lastName, 
		@RequestParam String userType, @RequestParam(required = false) String countryName,
		@RequestParam String address) throws IOException {
	
		User existingUser  = null;
		if (!StringUtils.isEmpty(email)) {
			existingUser = userJDBCTemplate.getUserByEmailId(email);
		}
		
		if (existingUser == null) {
			User user = new User();
			user.setEmail(email);
//			user.setPhoneNo(phoneNo);
//			user.setFirstName(firstName);
//			user.setLastName(lastName);
//			user.setCountryName(countryName);
			String password = JournalUtil.generatePassword();
			user.setPassword(password);
			user.setUsertype(userType);
//			user.setAddress(address);
			user.setGenerated("y");
			userJDBCTemplate.save(user);

			JournalMailUtil.sendMail(user.getEmail(), "User Registration done", "The generated password is :" + password);
		} 
		return "{\"result\":\"Submission uploaded successfully\"}";
	}
}