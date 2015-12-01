package com.jinlufund.utils;

import java.io.UnsupportedEncodingException;

public class DigestUtils {

	public static String md5DigestAsHex(String str) {
		String md5 = null;
		try {
			md5 = org.springframework.util.DigestUtils.md5DigestAsHex(str.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return md5;
	}
	
	public static void main(String[] args) {
		String result = md5DigestAsHex("123456");
		System.out.println(result);
	}

}
