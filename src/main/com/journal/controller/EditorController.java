package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.journal.dao.EditorJDBCTemplate;
import com.journal.dao.record.EditorRecord;
import com.journal.model.EditorModel;
import com.journal.utils.JournalUtil;

@Controller
public class EditorController {
	
	@Autowired
	private EditorJDBCTemplate editorJDBCTemplate;
	
	@RequestMapping(value="/addEditor", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addEditor(@RequestParam String data, @RequestPart(required=false) MultipartFile file) 
			throws IOException {
		
		EditorModel editorModel = new ObjectMapper().readValue(data, EditorModel.class);
		
		EditorRecord record = editorJDBCTemplate.getEditorByMailId(editorModel.getEmail());
		Map<String, Object> result = new HashMap<String, Object>();
		
		if (record == null) {
			
			String password = JournalUtil.generatePassword();
			
			editorModel.setPassword(password);
			editorModel.setGeneratedPass("y");
			
			if (file != null) {
				
				editorModel.setAvatar(file.getBytes());
				editorModel.setAvatarFileName(file.getOriginalFilename());
			}
			
			editorJDBCTemplate.saveEditor(editorModel);
			
			result.put("statusCode", "200");
			result.put("message", "editor added succesfully");

		} else {
			
			result.put("statusCode", "200");
			result.put("message", "editor exists");
		}
		
		
		return result;
	}
	
	@RequestMapping(value="/updateEditor/{editorId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEditor(@PathVariable("editorId") int editorId, @RequestParam String data, @RequestPart(required=false) MultipartFile attachment) throws IOException {
		

		EditorModel editorModel = new ObjectMapper().readValue(data, EditorModel.class);
		
		if (attachment != null) {
			
			editorModel.setAvatar(attachment.getBytes());
			editorModel.setAvatarFileName(attachment.getOriginalFilename());
		}
		
		editorModel.setId(editorId);
		
		if (editorModel.getId() != null) {
			editorJDBCTemplate.updateEditor(editorModel);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Editor updated succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/deleteEditor/{editorId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteeditor(@PathVariable("editorId") int editorId) {
		
		if (editorId > 0 ) {
			editorJDBCTemplate.deleteEditor(editorId);
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "editor deleted succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/editors", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> geteditors() {
		
		List<EditorModel> editors = editorJDBCTemplate.getEditors();
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", editors);
		result.put("count", editors.size());

		return result;
	}
}