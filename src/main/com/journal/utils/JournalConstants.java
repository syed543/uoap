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

	String JOURNAL_TEMPLATE_FILE = "journal1.html";

	String JOURNAL_ICONS_FOLDER = JORUNAL + File.separator + ICONS;

	String JOURNAL_BANNER_FOLDER = JORUNAL + File.separator + BANNERS;

	// 1: Open, 2: Assigned, 3:In Review 4: Approved, 5: Rejected
	int MENUSCRIPT_STATUS_OPEN = 1;

	int MENUSCRIPT_STATUS_ASSIGNED = 2;

	int MENUSCRIPT_STATUS_INREVIEW = 3;

	int MENUSCRIPT_STATUS_APPROVED = 4;

	int MENUSCRIPT_STATUS_REJECTED = 5;

	String SECURE_HTTP = "https://";
	String HOST_NAME = "uoap.com";
	String CONTEXT = "journal";

	String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	int INVOICE_NUMBER_LENGTH = 6;
	
	String PAYMENT_PENDING = "PENDING";
	String PAYMENT_FAILED = "FAILED";
	String PAYMENT_SUCCESS = "SUCCESS";
	
	int TOKEN_EXPIRY=5;
	String EXPIRY_IN_DAYS="DAYS";
	String EXPIRY_IN_HOURS="HOURS";
	String EXPIRY_IN_MIN="MINUTES";
}
