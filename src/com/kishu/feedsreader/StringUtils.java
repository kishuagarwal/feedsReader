package com.kishu.feedsreader;

public class StringUtils {

	public static String getParamValue(String s, String paramName) {

		int pos = s.indexOf(paramName + "=");
		if (pos == -1)
			return null;
		int posOfAnd = s.indexOf('&', pos);
		return s.substring(pos + paramName.length() + 1, posOfAnd);
	}

}
