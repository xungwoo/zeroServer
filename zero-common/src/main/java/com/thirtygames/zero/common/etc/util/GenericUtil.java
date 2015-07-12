package com.thirtygames.zero.common.etc.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class GenericUtil {
	public static Class<?> getClassOfGenericTypeIn(Class<?> clazz, int index) {
		
		
			ParameterizedType genericSuperclass;
			if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
				genericSuperclass = (ParameterizedType) clazz.getGenericSuperclass();
			} else {
				genericSuperclass = (ParameterizedType) clazz.getSuperclass().getGenericSuperclass();
			}
			
			Type wantedClassType = genericSuperclass.getActualTypeArguments()[index];
			if (wantedClassType instanceof ParameterizedType) {
				return (Class<?>) ((ParameterizedType) wantedClassType).getRawType();
			} else {
				return (Class<?>) wantedClassType;
			}
	}

	public static <T> String getArrayString(List<T> list) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("[");
		int i = 0;
		for (T temp : list) {
			if (i != 0) {
				buffer.append(",");
			}
			buffer.append("\"");
			buffer.append(temp.toString());
			buffer.append("\"");
			i++;
		}
		buffer.append("]");

		return buffer.toString();
	}

}
