package com.journal.model;

public class Submission {
	
	private String email;
	private String journal;
	private String articleType;
	private String menuScriptTitle;
	private String abstractName;
	private String attachmentFileName;
	private byte[] attachment;
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJournal() {
		return journal;
	}
	public void setJournal(String journal) {
		this.journal = journal;
	}
	public String getArticleType() {
		return articleType;
	}
	public void setArticleType(String articleType) {
		this.articleType = articleType;
	}
	public String getMenuScriptTitle() {
		return menuScriptTitle;
	}
	public void setMenuScriptTitle(String menuScriptTitle) {
		this.menuScriptTitle = menuScriptTitle;
	}
	public String getAbstractName() {
		return abstractName;
	}
	public void setAbstractName(String abstractName) {
		this.abstractName = abstractName;
	}
	public String getAttachmentFileName() {
		return attachmentFileName;
	}
	public void setAttachmentFileName(String attachmentFileName) {
		this.attachmentFileName = attachmentFileName;
	}
	public byte[] getAttachment() {
		return attachment;
	}
	public void setAttachment(byte[] attachment) {
		this.attachment = attachment;
	}
}
