package com.journal.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.journal.model.Article;

public class ArticleJDBCTemplate {

	
	@Autowired
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public Integer saveArticle(Article article) {
		String query = "insert into ARTICLE(title, abstractDesc, authors, journalId, article, articleFileName, version, issue) values" +
				"(?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, article.getTitle(), article.getAbstractDesc(), article.getAuthors(), article.getJournalId(),
				article.getFile(), article.getFileName(), article.getVersion(), article.getIssue());
		
		String auto = "SELECT AUTO_INCREMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'article' AND TABLE_NAME = 'ARTICLE'";
		
//		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		return 1;
	}

	public List<Article> getAllArticles() {
		
		String query = "select a.id as id, title, abstractDesc, authors,  journalName, version, issue, j.id as jid from Article a, Journal j where a.journalId = j.id";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> articleRows = jdbcTemplate.queryForList(query);
		
		List<Article> articles = new ArrayList<Article>();
		
		for (Map<String, Object> articleRow : articleRows) {
			
			Article article = new Article();
			article.setId((Integer) articleRow.get("id"));
			article.setTitle((String) articleRow.get("title"));
			article.setAbstractDesc((String) articleRow.get("abstractDesc"));
			article.setAuthors((String) articleRow.get("authors"));
			article.setJournalId((Integer) articleRow.get("jid"));
			article.setJournalName((String) articleRow.get("journalName"));
			article.setVersion((Integer) articleRow.get("version"));
			article.setIssue((Integer) articleRow.get("issue"));
			articles.add(article);
		}
		
		return articles;
	}
	
	public List<Article> getArticlesByJournalId(int journalId) {
		
		String query = "select a.id as id, title, abstractDesc, authors,  journalName, version, issue, j.id as jid from Article a, Journal j where a.journalId = j.id and j.id = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> articleRows = jdbcTemplate.queryForList(query, new Object[] {journalId});
		
		List<Article> articles = new ArrayList<Article>();
		
		for (Map<String, Object> articleRow : articleRows) {
			
			Article article = new Article();
			article.setId((Integer) articleRow.get("id"));
			article.setTitle((String) articleRow.get("title"));
			article.setAbstractDesc((String) articleRow.get("abstractDesc"));
			article.setAuthors((String) articleRow.get("authors"));
			article.setJournalId((Integer) articleRow.get("jid"));
			article.setJournalName((String) articleRow.get("journalName"));
			article.setVersion((Integer) articleRow.get("version"));
			article.setIssue((Integer) articleRow.get("issue"));
			articles.add(article);
		}
		
		return articles;
	}
	
	public void updateArticle(Article article) {
		
		List<Object> params = new ArrayList<Object>(8);
		
		String query = "update ARTICLE set title = ?,  abstractDesc = ?, authors = ?, journalId = ?, version = ?, issue = ?";
		params.add(article.getTitle());
		params.add(article.getAbstractDesc());
		params.add(article.getAuthors());
		params.add(article.getJournalId());
		params.add(article.getVersion());
		params.add(article.getIssue());
		
		
		if (article.getFile() != null) {
			query = query + ", article = ?, articleFileName = ? ";
			params.add(article.getFile());
			params.add(article.getFileName());
		}
		
		query = query.concat(" where id = ?");
		params.add(article.getId());
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(query, params.toArray(new Object[0]));
	}
	
	public void deleteJournal(int articleId) {
		
		String query = "delete from ARTICLE where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {articleId});
	}
}