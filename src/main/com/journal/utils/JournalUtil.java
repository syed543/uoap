package com.journal.utils;

import java.util.Random;
import java.util.UUID;

public class JournalUtil {
	
	public static String generatePassword() {
		
		//TODO: Need to see better logic to generate password.
		Random random = new Random();
		StringBuilder builder = new StringBuilder();
		
		int index = random.nextInt(9);
		builder.append(index);
		index = random.nextInt(26);
		builder.append((char) (97 + index));
		index = random.nextInt(26);
		builder.append((char) (65 + index));
		index = random.nextInt(26);
		builder.append((char) (97 + index));
		
		index = random.nextInt(9);
		builder.append(index);
		index = random.nextInt(26);
		builder.append((char) (97 + index));
		index = random.nextInt(26);
		builder.append((char) (65 + index));
		index = random.nextInt(26);
		builder.append((char) (97 + index));
		
		
		return builder.toString();
	}
	
	public static String[] getUniqueIds(int howmany) {
		
		String[] uniqueIds = new String[howmany];
		
		int index = 0;
		for (String ids: uniqueIds) {
			uniqueIds[index++] = UUID.randomUUID().toString();
		}
		
		return uniqueIds;
	}
}