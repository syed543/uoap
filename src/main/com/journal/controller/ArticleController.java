package com.journal.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.journal.dao.ArticleJDBCTemplate;
import com.journal.model.Article;

@Controller
public class ArticleController {
	
	@Autowired
	private ArticleJDBCTemplate articleJDBCTemplate;
	
	@RequestMapping(value="/addArticle", method=RequestMethod.POST, consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseBody
	public Map<String, Object> addArticle(@RequestParam String data, @RequestPart(required=false) MultipartFile icon, @RequestPart(required=false) MultipartFile file) throws IOException {
		
		Article article = new ObjectMapper().readValue(data, Article.class);
	
		if (file != null) {
			article.setFile(file.getBytes());
			article.setFileName(file.getOriginalFilename());
		}
		
		Integer autoId = articleJDBCTemplate.saveArticle(article);
		
		if (icon != null) {
			
//			File iconFile = new File(JournalConstants.JOURNAL_IMAGES_FOLDER + "")
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Article added succesfully");
		
		System.out.println("Article added");
		return result;
	}

	@RequestMapping(value="/getArticles", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getArticles() {
		
		List<Article> articles  = articleJDBCTemplate.getAllArticles(0);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", articles);
		result.put("count", articles.size());

		return result;
	}
	
	@RequestMapping(value="/getArticlesByJournalId/{journalId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getArticlesByJournalId(@PathVariable int journalId) {
		
		List<Article> articles  = articleJDBCTemplate.getAllArticles(journalId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("data", articles);
		result.put("count", articles.size());

		return result;
	}
	
	@RequestMapping(value="/updateArticle/{articleId}", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateArticle(@RequestParam String data, @PathVariable("articleId") int articleId, 
			@RequestPart(required=false) MultipartFile icon, @RequestPart(required=false) MultipartFile file) throws JsonParseException, JsonMappingException, IOException {
		
		Article article = new ObjectMapper().readValue(data, Article.class);
		
		article.setId(articleId);
		
		if (file != null) {
			article.setFile(file.getBytes());
			article.setFileName(file.getOriginalFilename());
		}

		if (article.getId() != null) {
			
			articleJDBCTemplate.updateArticle(article);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("statusCode", "200");
			result.put("message", "Article added succesfully");

			return result;
		}
		
		return null;
	}
	
	@RequestMapping(value="/deleteArticle/{articleId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deleteArticle(@PathVariable("articleId") int articleId) {
		System.out.println("Deleting article with id : " + articleId);
		articleJDBCTemplate.deleteJournal(articleId);
		System.out.println("Deleted article with id : " + articleId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Article added succesfully");
		return result;
	}
	
//	@RequestMapping(value="getEditorsByJournalId/{journalId}", method=RequestMethod.GET)
//	@ResponseBody
//	public Map<String, Object> getEditorsByJournalId(@PathVariable("journalId") int journalId) {
//		
//		
//		Map<String, Object> result = new HashMap<String, Object>();
//		result.put("statusCode", "200");
//		result.put("message", "Article added succesfully");
//		return result;
//	}
	
	@RequestMapping(value="/downloadArticle/{articleId}", method=RequestMethod.GET)
	public void downloadArticle(@PathVariable("articleId") int articleId, HttpServletResponse response) throws IOException {
		
		byte[] fileDat = articleJDBCTemplate.getArticleFileByArticleId(articleId);
		
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; fileName=article.pdf");
		response.getOutputStream().write(fileDat);
		
		response.flushBuffer();
	}
	
	@RequestMapping(value="updateArticleState/{articleId}", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> updateArticleState(@PathVariable("articleId") int articleId) {
		
		articleJDBCTemplate.updateArticleState(articleId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("statusCode", "200");
		result.put("message", "Article state updated succesfully");
		return result;
	}
	
}