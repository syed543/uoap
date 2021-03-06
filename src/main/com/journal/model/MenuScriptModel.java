package com.journal.model;

public class MenuScriptModel {
	
	private int id,journal,status;//1: Open, 2: Assigned, 3:In Review 4: Approved, 5: Rejected
	private String fName,lName,title,email, article, postalAddress,country,menuTitle,abstractTitle;
	private String reviewerName;
	private String reviewerEmail;
	private String journalName;
	private String feedback;
	private Integer reviewerId;
	private int[] reviewers;
	private String articleType;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getJournal() {
		return journal;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setJournal(int journal) {
		this.journal = journal;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPostalAddress() {
		return postalAddress;
	}
	public void setPostalAddress(String postalAddress) {
		this.postalAddress = postalAddress;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getMenuTitle() {
		return menuTitle;
	}
	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}
	public String getAbstractTitle() {
		return abstractTitle;
	}
	public void setAbstractTitle(String abstractTitle) {
		this.abstractTitle = abstractTitle;
	}
	public String getReviewerName() {
		return reviewerName;
	}
	public void setReviewerName(String reviewerName) {
		this.reviewerName = reviewerName;
	}
	public String getReviewerEmail() {
		return reviewerEmail;
	}
	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
	}
	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public Integer getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(Integer reviewerId) {
		this.reviewerId = reviewerId;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	public int[] getReviewers() {
		return reviewers;
	}
	public void setReviewers(int[] reviewers) {
		this.reviewers = reviewers;
	}
}