package com.journal.dao.record;

import com.journal.model.MenuScriptModel;

public class SubmitterRecord {
	
	public SubmitterRecord(MenuScriptModel model) {
		
		this.title = model.getTitle();
		this.firstName = model.getfName();
		this.lastName = model.getlName();
		this.email = model.getEmail();
		this.address = model.getPostalAddress();
		this.country = model.getCountry();
	}
	
	public SubmitterRecord() {
		
	}
	
	private int id;
	private String title;
	private String firstName;
	private String lastName;
	private String email;
	private String password;
	private String generatedPass;
	private String address;
	private String country;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
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
}