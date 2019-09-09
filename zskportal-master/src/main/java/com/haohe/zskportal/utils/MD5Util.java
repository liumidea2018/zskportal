package com.haohe.zskportal.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

public class MD5Util {

	private static Md5PasswordEncoder pwdEncoder = new Md5PasswordEncoder();

	protected MD5Util(){
	}

//	private static final String ALGORITH_NAME = "md5";
//
//	private static final int HASH_ITERATIONS = 2;
//
//	public static String encrypt(String password) {
//		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(password), HASH_ITERATIONS).toHex();
//	}

	public static String encrypt(String username, String password) {
		pwdEncoder.encodePassword(password,username);
		return pwdEncoder.encodePassword(password,username);
//		return new SimpleHash(ALGORITH_NAME, password, ByteSource.Util.bytes(username.toLowerCase() + password),
//				HASH_ITERATIONS).toHex();
	}

//	public static void main(String[] args) {
//		//admin  b594510740d2ac4261c1b2fe87850d08 123456
//		System.out.println(encrypt("admin","123456"));
//	}

}
