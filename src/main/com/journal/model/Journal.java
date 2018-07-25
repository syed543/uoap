package com.journal.model;

import org.springframework.web.multipart.MultipartFile;

public class Journal {
	
	private Integer id;
	private String journal_name;
	private MultipartFile journalIconFile;
	private MultipartFile journalBannerImageFile;
	private byte[] journalIcon;
	private String journalIconFileName;
	private String journal_description;
	private String journal_long_description;
	private byte[] journalBannerImage;
	private String journalBannerImageFileName;

	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public byte[] getJournalIcon() {
		return journalIcon;
	}
	public void setJournalIcon(byte[] journalIcon) {
		this.journalIcon = journalIcon;
	}
	public String getJournalIconFileName() {
		return journalIconFileName;
	}
	public void setJournalIconFileName(String journalIconFileName) {
		this.journalIconFileName = journalIconFileName;
	}
	public byte[] getJournalBannerImage() {
		return journalBannerImage;
	}
	public void setJournalBannerImage(byte[] journalBannerImage) {
		this.journalBannerImage = journalBannerImage;
	}
	public String getJournalBannerImageFileName() {
		return journalBannerImageFileName;
	}
	public void setJournalBannerImageFileName(String journalBannerImageFileName) {
		this.journalBannerImageFileName = journalBannerImageFileName;
	}
	public MultipartFile getJournalIconFile() {
		return journalIconFile;
	}
	public void setJournalIconFile(MultipartFile journalIconFile) {
		this.journalIconFile = journalIconFile;
	}
	public MultipartFile getJournalBannerImageFile() {
		return journalBannerImageFile;
	}
	public void setJournalBannerImageFile(MultipartFile journalBannerImageFile) {
		this.journalBannerImageFile = journalBannerImageFile;
	}
	public String getJournal_name() {
		return journal_name;
	}
	public void setJournal_name(String journal_name) {
		this.journal_name = journal_name;
	}
	public String getJournal_description() {
		return journal_description;
	}
	public void setJournal_description(String journal_description) {
		this.journal_description = journal_description;
	}
	public String getJournal_long_description() {
		return journal_long_description;
	}
	public void setJournal_long_description(String journal_long_description) {
		this.journal_long_description = journal_long_description;
	}
	
}
