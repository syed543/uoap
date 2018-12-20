package com.journal.dao.record;

import com.journal.model.MenuScriptModel;

public class MenuScriptRecord {
	
	public MenuScriptRecord(MenuScriptModel model) {
		
		journalId = model.getJournal();
		menuScriptTitle = model.getMenuTitle();
		abstractData = model.getAbstractTitle();
		feedBack = model.getFeedback();
	}
	
	public MenuScriptRecord() {
		
	}

	private int id;
	private int submitterId;
	private int journalId;
	private int status; //1: Open, 2: Assigned, 3:In Review 4: Approved, 5: Rejected
	private int reviewer;
	private String menuScriptTitle;
	private String abstractData;
	private String feedBack;
	
	private byte[] menuScriptData;
	private String menuScriptFileName;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getSubmitterId() {
		return submitterId;
	}

	public void setSubmitterId(int submitterId) {
		this.submitterId = submitterId;
	}

	public int getJournalId() {
		return journalId;
	}
	public void setJournalId(int journalId) {
		this.journalId = journalId;
	}
	public String getMenuScriptTitle() {
		return menuScriptTitle;
	}
	public void setMenuScriptTitle(String menuScriptTitle) {
		this.menuScriptTitle = menuScriptTitle;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAbstractData() {
		return abstractData;
	}
	public void setAbstractData(String abstractData) {
		this.abstractData = abstractData;
	}
	public byte[] getMenuScriptData() {
		return menuScriptData;
	}
	public void setMenuScriptData(byte[] menuScriptData) {
		this.menuScriptData = menuScriptData;
	}
	public String getMenuScriptFileName() {
		return menuScriptFileName;
	}
	public void setMenuScriptFileName(String menuScriptFileName) {
		this.menuScriptFileName = menuScriptFileName;
	}

	public String getFeedBack() {
		return feedBack;
	}

	public void setFeedBack(String feedBack) {
		this.feedBack = feedBack;
	}
	
}
