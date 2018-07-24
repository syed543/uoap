package com.journal.model;

public class Journal {
	
	private Integer id;
	private String journalName;
	private byte[] journalIcon;
	private String journalIconFileName;
	private String journalDescription;
	private String journalLongDescription;
	private byte[] journalBannerImage;
	private String journalBannerImageFileName;

	public Integer getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJournalName() {
		return journalName;
	}
	public void setJournalName(String journalName) {
		this.journalName = journalName;
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
	public String getJournalDescription() {
		return journalDescription;
	}
	public void setJournalDescription(String journalDescription) {
		this.journalDescription = journalDescription;
	}
	public String getJournalLongDescription() {
		return journalLongDescription;
	}
	public void setJournalLongDescription(String journalLongDescription) {
		this.journalLongDescription = journalLongDescription;
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
}
