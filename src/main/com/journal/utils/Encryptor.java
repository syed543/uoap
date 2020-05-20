package com.journal.utils;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class Encryptor {
	
	private static Key aesKey = new SecretKeySpec("journal4journal4".getBytes(), "AES");

	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		
		String text = "Hello World";
		
		String eeString = getEncodedEncrytedString(text);
		
		System.out.println("EeString: " + eeString);
		
		String normalText = getDecodedDecrytedString(eeString);
		
		System.out.println("ddString:"+ normalText);
		
		
	}
	
	public static String getEncodedEncrytedString(String password) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = Cipher.getInstance("AES");
		
		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		
		byte[] encrypted = cipher.doFinal(password.getBytes());
		
		String encodedStr = Base64.encodeBase64String(encrypted);
		
		return encodedStr;
	}
	
	public static String getDecodedDecrytedString(String encodedEncryptedPassword) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		Cipher cipher = Cipher.getInstance("AES");
		
		cipher.init(Cipher.DECRYPT_MODE, aesKey);

		byte[] decodedEncryptedStr = Base64.decodeBase64(encodedEncryptedPassword);
		
		byte[] decrypted = cipher.doFinal(decodedEncryptedStr);
		
		
		return new String(decrypted);
	}
	
	
}
