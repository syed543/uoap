package com.journal.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import org.apache.commons.io.FileUtils;

public class WebAppUtils {
	
	private static String webAppPath = WebAppUtils.class.getResource("/../..").getPath();
	
	public static String getWebAppPath() {
		
		return webAppPath;
	}
	
	public static void uploadFile(String type, int id, String fileName, byte[] fileData) throws IOException {
		
		if (type != null) {
			
			File folderPath = new File(getWebAppPath() + File.separator + JournalConstants.AVATARS + File.separator + type + File.separator + id);
			FileUtils.deleteDirectory(folderPath);
			
			if (!folderPath.mkdirs()) {
				return;
			}
			
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(folderPath.getPath() + File.separator + fileName)));
			bos.write(fileData);
			bos.flush();
			bos.close();
		}
	}
	
	public static void copyJournalTemplateFile(String fileName) throws IOException {
		
		fileName = fileName.replaceAll(" ", "_");
		
		File src = new File(webAppPath + File.separator + JournalConstants.JOURNAL_TEMPLATE_FILE);
		File dest = new File(webAppPath + File.separator + fileName + ".html");
		
		Files.copy(src.toPath(), dest.toPath());
		
	}
}