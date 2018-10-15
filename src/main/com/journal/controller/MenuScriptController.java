package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
			
			JournalMailUtil.sendMail(submitterRecord.getEmail(), "Submitter Registration done", "The generated password is :" + password);
			record.setSubmitterId(submitterRecord.getId());
		} else {
			
			record.setSubmitterId(existingSubmitterRecord.getId());
		}
		
		if (attachment != null) {
			
			record.setMenuScriptData(attachment.getBytes());
			record.setMenuScriptFileName(attachment.getOriginalFilename());
		}
		
		menuScriptTemplate.saveMenuScript(record);
		JournalMailUtil.sendMail(submitterRecord.getEmail(), "MenuScript submitted successfully", 
				"MenuScript uploaded successfully with menuScript title:" + record.getMenuScriptTitle());
		
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
	
	@RequestMapping(value="/updateMenuScript", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMenuScript(@RequestParam String data, @RequestPart(required=false) MultipartFile attachment) 
			throws JsonParseException, JsonMappingException, IOException {
		
		MenuScriptModel model = new ObjectMapper().readValue(data, MenuScriptModel.class);

		MenuScriptRecord record = new MenuScriptRecord(model);
		
		if (attachment != null) {
			
			record.setMenuScriptData(attachment.getBytes());
			record.setMenuScriptFileName(attachment.getOriginalFilename());
		}
		
		menuScriptTemplate.updateMenuScript(record);
		JournalMailUtil.sendMail(model.getEmail(), "MenuScript Updated successfully", 
				"MenuScript update successfully with menuScript title:" + record.getMenuScriptTitle());
		
		System.out.println("Menu Script added successfully");
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript updated successfully");
		
		return result;
	}
	
	@RequestMapping(value = "/deleteMenuScript", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteMenuScript(Integer menuScriptId) {
		
		menuScriptTemplate.deleteMenuScript(menuScriptId);
		
		Map<String, Object> result = new HashMap<String, Object>(2);
		result.put("statusCode", 200);
		result.put("message", "MenuScript updated successfully");
		
		return result;
	}
	
	@RequestMapping(value="/menuScriptList", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> menuScriptList() {
		
		List<MenuScriptModel> menuScriptModels  = menuScriptTemplate.getAllMenuScripts();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", menuScriptModels);
		result.put("count", menuScriptModels.size());

		return result;
	}
}