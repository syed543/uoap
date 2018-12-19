package com.journal.dao.record;

public class ReviewerRecord {
	
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String generatedPass;
	private int journalId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGeneratedPass() {
		return generatedPass;
	}
	public void setGeneratedPass(String generatedPass) {
		this.generatedPass = generatedPass;
	}
	public int getJournalId() {
		return journalId;
	}
	public void setJournalId(int journalId) {
		this.journalId = journalId;
	}
}
