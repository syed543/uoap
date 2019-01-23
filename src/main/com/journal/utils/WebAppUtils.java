package com.journal.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WebAppUtils {
	
	private static String webAppPath = WebAppUtils.class.getResource("/../..").getPath();
	
	public static String getWebAppPath() {
		
		return webAppPath;
	}
	
	public static void uploadFile(String type, int id, String fileName, byte[] fileData) throws IOException {
		
		if (type != null) {
			
			File folderPath = new File(getWebAppPath() + File.pathSeparator + "avatars" + File.pathSeparator + type + File.pathSeparator + id);
			folderPath.delete();
			
			if (!folderPath.mkdirs()) {
				return;
			}
			
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(folderPath.getPath() + File.pathSeparator + fileName)));
			bos.write(fileData);
			bos.flush();
			bos.close();
		}
	}
}