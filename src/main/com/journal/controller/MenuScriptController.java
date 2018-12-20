package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.journal.dao.MenuScriptTemplate;
import com.journal.dao.SubmitterJDBCTemplate;
import com.journal.dao.record.MenuScriptRecord;
import com.journal.dao.record.SubmitterRecord;
import com.journal.model.MenuScriptModel;
import com.journal.model.User;
import com.journal.utils.JournalConstants;
import com.journal.utils.JournalMailUtil;
import com.journal.utils.JournalUtil;

@Controller
public class MenuScriptController {
	
	@Autowired
	private MenuScriptTemplate menuScriptTemplate;
	
	@Autowired
	private SubmitterJDBCTemplate submitterJDBCTemplate;
	
	@RequestMapping(value="/addMenuScript", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMenuScript(@RequestParam String data, @RequestPart(required=false) MultipartFile attachment) 
			throws IOException {
		
		MenuScriptModel model = new ObjectMapper().readValue(data, MenuScriptModel.class);

		MenuScriptRecord record = new MenuScriptRecord(model);
		
		SubmitterRecord submitterRecord = new SubmitterRecord(model);
		
		SubmitterRecord existingSubmitterRecord = submitterJDBCTemplate.getSubmitterByEmail(submitterRecord.getEmail());
		
		if (existingSubmitterRecord == null) {

			String password = JournalUtil.generatePassword();
			
			submitterRecord.setPassword(password);
			submitterRecord.setGeneratedPass("y");
			
			submitterJDBCTemplate.saveSubmitter(submitterRecord);
			
			try {
			
			JournalMailUtil.sendMail(submitterRecord.getEmail(), "Submitter Registration done", "The generated password is :" + password);
			} catch (Exception e) {
				e.printStackTrace();
			}
			record.setSubmitterId(submitterRecord.getId());
		} else {
			
			record.setSubmitterId(existingSubmitterRecord.getId());
		}
		
		record.setStatus(JournalConstants.MENUSCRIPT_STATUS_OPEN); //1: Open, 2: Assigned, 3:In Review 4: Approved, 5: Rejected
		//If Reviewer accepts it will be In Review.
		//If Reviewer rejects it will be in Open.
		
		if (attachment != null) {
			
			record.setMenuScriptData(attachment.getBytes());
			record.setMenuScriptFileName(attachment.getOriginalFilename());
		}
		
		menuScriptTemplate.saveMenuScript(record);
		try {
		JournalMailUtil.sendMail(submitterRecord.getEmail(), "MenuScript submitted successfully", 
				"MenuScript uploaded successfully with menuScript title:" + record.getMenuScriptTitle());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Menu Script added successfully");
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript added successfully");
		
		return result;
	}
	
	@RequestMapping(value="/getMenuScript", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getMenuScript(Integer menuScriptId) {
		
		MenuScriptModel model = menuScriptTemplate.getMenuScriptById(menuScriptId);
		
		Map<String, Object> result = new HashMap<String, Object>(3);
		result.put("statusCode", 200);
		result.put("message", "MenuScript added successfully");
		result.put("body", model);
		
		return result;
	}
	
	@RequestMapping(value="/updateMenuScript/{menuScriptId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMenuScript(HttpSession session, @PathVariable int menuScriptId, @RequestParam String data) 
			throws JsonParseException, JsonMappingException, IOException {
		
		MenuScriptModel model = new ObjectMapper().readValue(data, MenuScriptModel.class);

		MenuScriptRecord record = new MenuScriptRecord(model);
		
		record.setId(menuScriptId);
		
		User user  = (User) session.getAttribute("user");
		
		menuScriptTemplate.updateMenuScript(user.getUsertype(), record);
		
		SubmitterRecord submitterRecord = menuScriptTemplate.getMenuScriptSubmiiter(menuScriptId);
		
		JournalMailUtil.sendMail(submitterRecord.getEmail(), "MenuScript Feedback Provided:", 
				"Feed back for MenuScript:" + record.getFeedBack());
		
		System.out.println("Menu Script added successfully");
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript updated successfully");
		
		return result;
	}
	
	@RequestMapping(value="/approveMenuScript/{menuScriptId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> approveMenuScript(@PathVariable int menuScriptId) {
		
		menuScriptTemplate.updateStatus(JournalConstants.MENUSCRIPT_STATUS_APPROVED, menuScriptId);

		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript Approved");
		
		return result;
	}
	
	@RequestMapping(value="/rejectMenuScript/{menuScriptId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> rejectMenuScript(@PathVariable int menuScriptId) {
		
		menuScriptTemplate.updateStatus(JournalConstants.MENUSCRIPT_STATUS_REJECTED, menuScriptId);

		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript Rejected");
		
		return result;
	}
	
	@RequestMapping(value="/reviewerDecline/{menuScriptId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reviewerDecline(@PathVariable int menuScriptId) {
		
		menuScriptTemplate.updateStatus(JournalConstants.MENUSCRIPT_STATUS_OPEN, menuScriptId);

		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript Declined");
		
		return result;
	}
	
	@RequestMapping(value="/reviewerAccept/{menuScriptId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> reviewerAccept(@PathVariable int menuScriptId) {
		
		menuScriptTemplate.updateStatus(JournalConstants.MENUSCRIPT_STATUS_INREVIEW, menuScriptId);

		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript In Review");
		
		return result;
	}
	
	@RequestMapping(value = "/deleteMenuScript{menuScriptId}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteMenuScript(@PathVariable int menuScriptId) {
		
		if (menuScriptId > 0) {
			menuScriptTemplate.deleteMenuScript(menuScriptId);
		}
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript deleted successfully");
		
		return result;
	}
	
	@RequestMapping(value="/menuScriptList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> menuScriptList(HttpSession session, @RequestParam String email) {
		
		User user  = (User) session.getAttribute("user");
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");

		if (user == null) {
			return result;
		}
		
		List<MenuScriptModel> menuScriptModels  =  menuScriptTemplate.getMenuScriptsByEmailAndUserType(user.getUsertype(), user.getEmail());
		
		result.put("data", menuScriptModels);
		result.put("count", menuScriptModels.size());

		return result;
	}
}