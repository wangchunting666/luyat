package com.luyat.common.util;

import java.util.Map;

public class RegexUtils {
	public static boolean matchs(Map<String,Object> paramMap,String name,String regex) {
		Object obj = paramMap.get(name);
		if(name != null && obj.toString().matches(regex)) {
			return true;
		}
		return false;
	}
}
