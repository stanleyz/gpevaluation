/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.util;

import java.util.Properties;

import org.apache.commons.lang.StringUtils;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class StringUtil {
	public static Properties getProperties(String s, String separator) {
		Properties p = new Properties();
		s = StringUtils.substring(s, 1, -1).trim();
		String[] splited = StringUtils.split(s, separator);
		int i = 0;
		for (String each : splited) {
			i = each.indexOf("=");
			p.put(each.substring(0, i).trim(), each.substring(i + 1).trim());
		}
		return p;
	}

	public static String replaceIgnorecase(String text, String searchString,
			String replaceString) {
		text = StringUtils.replace(text, searchString.toLowerCase(),
				replaceString);
		text = StringUtils.replace(text, searchString.toUpperCase(),
				replaceString);
		return text;

	}
}
