package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.journal.dao.EditorJDBCTemplate;
import com.journal.model.Editor;

@Controller
public class EditorController {
	

private EditorJDBCTemplate editorJDBCController;
	
	@RequestMapping(value="/addEditor", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addEditor(@RequestBody Editor editor) throws IOException {
		
		editorJDBCController.saveEditor(editor);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "200");
		result.put("message", "Editor added succesfully");
		
		return result;
	}
	
	@RequestMapping(value="/updateEditor", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateEditor(@RequestBody Editor editor) throws IOException {
		
		editorJDBCController.updateEditor(editor);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("status", "200");
		result.put("message", "Editor updated succesfully");
		
		return result;
	}
}