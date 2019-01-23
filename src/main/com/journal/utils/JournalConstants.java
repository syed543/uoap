package com.journal.utils;

import java.io.File;

public interface JournalConstants {
	
	String JOURNAL_ICONS_FOLDER = "Journal" + File.separator + "Icons";
	
	String JOURNAL_BANNER_FOLDER = "Journal" + File.separator + "Banners";
	
	String ADMIN = "Admin";
	
	String REVIEWER = "Reviewer";
	
	String EDITOR = "Editor";
	
	String SUBMITTER = "Submitter";
	
	//1: Open, 2: Assigned, 3:In Review 4: Approved, 5: Rejected
	int MENUSCRIPT_STATUS_OPEN = 1;
	
	int MENUSCRIPT_STATUS_ASSIGNED = 2;
	
	int MENUSCRIPT_STATUS_INREVIEW = 3;
	
	int MENUSCRIPT_STATUS_APPROVED = 4;
	
	int MENUSCRIPT_STATUS_REJECTED = 5;

}
