package com.journal.utils;

import java.io.File;

public interface JournalConstants {
	
	
	String ADMIN = "Admin";
	
	String JORUNAL = "Journal";
	
	String ICONS = "Icons";
	
	String BANNERS = "Banners";
	
	String REVIEWER = "Reviewer";
	
	String EDITOR = "Editor";
	
	String SUBMITTER = "Submitter";
	
	String AVATARS = "Avatars";

	String JOURNAL_ICONS_FOLDER = JORUNAL + File.separator+ ICONS;
	
	String JOURNAL_BANNER_FOLDER = JORUNAL + File.separator + BANNERS;
	
	//1: Open, 2: Assigned, 3:In Review 4: Approved, 5: Rejected
	int MENUSCRIPT_STATUS_OPEN = 1;
	
	int MENUSCRIPT_STATUS_ASSIGNED = 2;
	
	int MENUSCRIPT_STATUS_INREVIEW = 3;
	
	int MENUSCRIPT_STATUS_APPROVED = 4;
	
	int MENUSCRIPT_STATUS_REJECTED = 5;

}
