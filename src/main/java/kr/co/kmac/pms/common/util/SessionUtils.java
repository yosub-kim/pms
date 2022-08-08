package kr.co.kmac.pms.common.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import kr.co.kmac.pms.common.domain.PmsUserDetail;

public abstract class SessionUtils {

	public static String getCookieValue(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie.getValue();
			}
		}
		return null;
	}

	public static String getSsn() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PmsUserDetail userDetails = (PmsUserDetail) principal;
		if (userDetails != null) {
			return userDetails.getSsn();
		}
		throw new IllegalStateException("The session does not have proper authentication.");
	}
	
	public static String getJobclass() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PmsUserDetail userDetails = (PmsUserDetail) principal;

		if (userDetails != null) {
			return userDetails.getJobClass();
		}
		throw new IllegalStateException("The session does not have proper authentication.");
	}
	
	public static String getDept() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PmsUserDetail userDetails = (PmsUserDetail) principal;

		if (userDetails != null) {
			return userDetails.getDept();
		}
		throw new IllegalStateException("The session does not have proper authentication.");
	}
	
	public static String getUserId() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PmsUserDetail userDetails = (PmsUserDetail) principal;

		if (userDetails != null) {
			return userDetails.getUserId();
		}
		throw new IllegalStateException("The session does not have proper authentication.");
	}
	
	public static String getCompanyPosition() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PmsUserDetail userDetails = (PmsUserDetail) principal;

		if (userDetails != null) {
			return userDetails.getCompanyPosition();
		}
		throw new IllegalStateException("The session does not have proper authentication.");
	}
	
	public static String getEmail() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PmsUserDetail userDetails = (PmsUserDetail) principal;

		if (userDetails != null) {
			return userDetails.getEmail();
		}
		throw new IllegalStateException("The session does not have proper authentication.");
	}

	public static User getUserObjext() {
		Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return (User) obj;
	}

	public static SecurityContext getUserContext() {
		SecurityContext obj = SecurityContextHolder.getContext();
		return obj;
	}

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");

		if (ip == null) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null) {
			ip = request.getRemoteAddr();
		}

		return ip;
	}
}
