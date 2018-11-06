package com.skynet.zero.util;

/**
 * 字符串操作工具类，实现字符串数据的处理
 * @author Administrator
 *
 */
public class StringUtil {
	/**
	 * 实现首字符大写
	 * @param str
	 * @return
	 */
	public static String initCap(String str) {
		if(str == null) {
			return null;
		}
		
		return str.substring(0, 1).toUpperCase() + str.substring(1);
		
	}
}
