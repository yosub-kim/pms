package kr.co.kmac.pms.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.annotation.Configuration;

/**
 * 아이디 생성기
 * 
 * @author 장민호
 * @version $Id: IdGenerator.java,v 1.1 2009/09/19 11:15:32 cvs3 Exp $
 */
@Configuration
public class IdGenerator {
	private static long counter = 0L;
	/**
	 * <code>PREFIX_ROLE</code> 롤
	 */
	public static final String PREFIX_ROLE = "ROLE";
	/**
	 * <code>PREFIX_MENU</code> 메뉴
	 */
	public static final String PREFIX_MENU = "MENU";
	/**
	 * <code>PREFIX_WORL</code> 업무리스트
	 */
	public static final String PREFIX_WORK = "WORK";
	/**
	 * <code>PREFIX_WORL</code> 업무 내용 아이디
	 */
	public static final String PREFIX_TASK = "TASK";
	/**
	 * <code>PREFIX_WORL</code> 프로세스 카테고리
	 */
	public static final String PREFIX_CATEGORY = "PRODUCT";

	// 아래에 계속 추가

	/**
	 * 아이디를 얻는다.
	 * 
	 * @param prefix 접두어
	 * @return
	 */
	public static String generate(String prefix) {
		String id = null;
		synchronized (IdGenerator.class) {
			id = Long.toHexString(System.currentTimeMillis() + counter++).toUpperCase();
		}
		if (prefix != null)
			id = prefix + id;
		return id;
	}

	/**
	 * 필요한 개수의 아이디를 얻는다.
	 * 
	 * @param n 필요한 아이디 개수
	 * @param prefix 접두어
	 * @return
	 */
	public static List<String> generateForMulti(int n, String prefix) {
		Calendar currentDate = Calendar.getInstance();
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < n; i++) {
			currentDate.set(Calendar.SECOND, i);
			String id = null;
			synchronized (IdGenerator.class) {
				id = Long.toHexString(System.currentTimeMillis() + counter++).toUpperCase();
			}
			if (prefix != null)
				id = prefix + id;
			ids.add(id);
		}
		return ids;
	}
}
