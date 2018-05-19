package com.journal.utils;

import java.util.Random;

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
}