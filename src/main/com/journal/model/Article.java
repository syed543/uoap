package com.journal.model;

public class Article {
	
	private Integer id;
	private String title;
	private String abstractDesc;
	private String authors;
	private byte[] file;
	private String fileName;
	private Integer journalId;
	private String journalName;
	
	private Integer version;
	private Integer issue;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAbstractDesc() {
		return abstractDesc;
	}
	public void setAbstractDesc(String abstractDesc) {
		this.abstractDesc = abstractDesc;
	}
	public String getAuthors() {
		return authors;
	}
	public void setAuthors(String authors) {
		this.authors = authors;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Integer getJournalId() {
		return journalId;
	}
	public void setJournalId(Integer journalId) {
		this.journalId = journalId;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public Integer getIssue() {
		return issue;
	}
	public void setIssue(Integer issue) {
		this.issue = issue;
	}
}