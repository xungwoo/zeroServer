package com.thirtygames.zero.admin.security;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
public class MySqlPasswordEncoder implements PasswordEncoder {

	@Override
	public String encode(CharSequence rawPassword) {
		if (rawPassword == null) {
			throw new NullPointerException();
		}

		byte[] bpara = new byte[rawPassword.length()];

		byte[] rethash;
		int i;
		for (i = 0; i < rawPassword.length(); i++)
			bpara[i] = (byte) (rawPassword.charAt(i) & 0xff);
		try {
			MessageDigest sha1er = MessageDigest.getInstance("SHA1");
			rethash = sha1er.digest(bpara); // stage1
			rethash = sha1er.digest(rethash); // stage2
		} catch (GeneralSecurityException e) {
			throw new RuntimeException(e);
		}
		StringBuffer r = new StringBuffer(41);
		r.append("*");
		for (i = 0; i < rethash.length; i++) {
			String x = Integer.toHexString(rethash[i] & 0xff).toUpperCase();
			if (x.length() < 2)
				r.append("0");
			r.append(x);
		}

		return r.toString();
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || rawPassword == null) {
			return false;
		}

		log.debug("encodedPassword:::" + encodedPassword);
		log.debug("rawPassword:::" + rawPassword);
		log.debug("encode(rawPassword):::" + encode(rawPassword));
		if (!encodedPassword.equals(encode(rawPassword))) {
			return false;
		}
		
		log.debug("True!!!");

		return true;
	}

}