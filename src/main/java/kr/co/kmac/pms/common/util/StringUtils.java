package kr.co.kmac.pms.common.util;

public class StringUtils {

	public static String arrayToCommaString(String[] array) {
		String result = "";
		if (array.length > 0) {
			StringBuilder sb = new StringBuilder();
			for (String s : array) {
				sb.append(s).append(",");
			}
			result = sb.deleteCharAt(sb.length() - 1).toString();
		}
		return result;

	}

}
