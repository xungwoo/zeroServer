package com.thirtygames.zero.common.etc.util;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnumUtil {

	
	public static String[] getNames(Class<? extends Enum<?>> e) {
	    return Arrays.toString(e.getEnumConstants()).replaceAll("\\[|]", "").split(", ");
	}	

	public static String getGridOpts(Class<? extends Enum<?>> e) {
		String result = "";
		for (Enum<?> status : e.getEnumConstants()) {
			result += status.name();
		}
		
		return result;			
	}	
	
	
}
