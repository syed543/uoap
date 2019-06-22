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
		String query = "insert into ARTICLE(title, abstractDesc, authors, journalId, article, articleFileName, version, issueNo, articleState, articleType, showOnDetailsPage) values" +
				"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, article.getTitle(), article.getAbstractDesc(), article.getAuthors(), article.getJournalId(),
				article.getFile(), article.getFileName(), article.getVersion(), article.getIssueNo(),  1, article.getArticleType(), article.isShowOnDetailsPage());
		
		String auto = "select max(id) from ARTICLE";
		
		Integer autoInt = jdbcTemplate.queryForInt(auto);
		
		return autoInt;
	}

	public List<Article> getAllArticles(int journalId) {
		
		String query = "select a.id as id, title, abstractDesc, authors,  journalName, version, issueNo, articleState, articleType, case articleState when 1 then 'inPress' when 2 then 'currentIssue' when 3 then 'archive' else 'Archieve' end as articleStateStr, showOnDetailsPage,"
				+ " j.id as jid from Article a, Journal j where a.journalId = j.id";
		
		if (journalId != 0) {
			query = query.concat(" and j.id = ?");
		}
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> articleRows = null;
		
		if (journalId != 0) {
			articleRows = jdbcTemplate.queryForList(query, new Object[] {journalId});
		} else {
			articleRows = jdbcTemplate.queryForList(query);
		}
		
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
			article.setIssueNo((Integer) articleRow.get("issueNo"));
			article.setArticleStateId((Integer) articleRow.get("articleState")) ;
			article.setArticleType((String) articleRow.get("articleType")) ;
			article.setArticleState((String) articleRow.get("articleStateStr")) ;
			Boolean value = (Boolean) articleRow.get("showOnDetailsPage");
			if (value != null) {
				article.setShowOnDetailsPage(value);
			}
			articles.add(article);
		}
		
		return articles;
	}
	
	public List<Article> getAllArticlesByState(int state) {
		
		String query = "select a.id as id, title, abstractDesc, authors,  journalName, version, issueNo, articleState, articleType, case articleState when 1 then 'inPress' when 2 then 'currentIssue' when 3 then 'archive' else 'Archieve' end as articleStateStr,"
				+ " j.id as jid, showOnDetailsPage from Article a, Journal j where a.journalId = j.id and articleState = ?";
		
		JdbcTemplate jdbcTemplate  = new JdbcTemplate(dataSource);

		List<Map<String, Object>> articleRows = null;
		
		articleRows = jdbcTemplate.queryForList(query, new Object[] {state});
		
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
			article.setIssueNo((Integer) articleRow.get("issueNo"));
			article.setArticleStateId((Integer) articleRow.get("articleState")) ;
			article.setArticleType((String) articleRow.get("articleType")) ;
			article.setArticleState((String) articleRow.get("articleStateStr")) ;
			Boolean value = (Boolean) articleRow.get("showOnDetailsPage");
			if (value != null) {
				article.setShowOnDetailsPage(value);
			}
			articles.add(article);
		}
		
		return articles;
	}

	
	public List<Article> getArticlesByJournalId(int journalId) {
		
		String query = "select a.id as id, title, abstractDesc, authors,  journalName, version, issueNo, articleType, articleState, case articleState when 1 then 'inPress' when 2 then 'currentIssue' when 3 then 'archive' else 'Archieve' end as articleStateStr,"
				+ " j.id as jid, showOnDetailsPage from Article a, Journal j where a.journalId = j.id and j.id = ?";
		
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
			article.setIssueNo((Integer) articleRow.get("issueNo"));
			article.setArticleType((String) articleRow.get("articleType")) ;
			article.setArticleStateId((Integer) articleRow.get("articleState")) ;
			article.setArticleState((String) articleRow.get("articleStateStr")) ;
			Boolean value = (Boolean) articleRow.get("showOnDetailsPage");
			if (value != null) {
				article.setShowOnDetailsPage(value);
			}

			articles.add(article);
		}
		
		return articles;
	}
	
	public void updateArticle(Article article) {
		
		List<Object> params = new ArrayList<Object>(8);
		
		String query = "update ARTICLE set title = ?,  abstractDesc = ?, authors = ?, journalId = ?, version = ?, issueNo = ?, articleType = ?, showOnDetailsPage = ? ";
		params.add(article.getTitle());
		params.add(article.getAbstractDesc());
		params.add(article.getAuthors());
		params.add(article.getJournalId());
		params.add(article.getVersion());
		params.add(article.getIssueNo());
		params.add(article.getArticleType());
		params.add(article.isShowOnDetailsPage());
		
		
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
	
	public void updateArticleState(int articleId) {
		
		String query = "update ARTICLE set articleState = articleState + 1 where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		jdbcTemplate.update(query, new Object[] {articleId});
	}
	
	
	public byte[] getArticleFileByArticleId(int articleId) {
		
		String query = "select article from ARTICLE where id = ?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		
		byte[] data  = jdbcTemplate.queryForObject(query, new Object[] {articleId}, byte[].class);
		
		return data;
		
	}
}