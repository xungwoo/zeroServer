package com.thirtygames.zero.common.etc.util;

import com.google.common.base.CharMatcher;


public class StringUtil {
	public static CharMatcher digits = CharMatcher.inRange('0', '9').precomputed();
	
	public static String removeSpecialChar(String str) {
	   return digits.retainFrom(str);
	}
}
