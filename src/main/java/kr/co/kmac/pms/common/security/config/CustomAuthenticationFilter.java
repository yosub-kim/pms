package kr.co.kmac.pms.common.security.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CustomAuthenticationFilter
{}
//extends UsernamePasswordAuthenticationFilter {
//
//	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//		super.setAuthenticationManager(authenticationManager);
//	}
//
//	@Override
//	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//			throws AuthenticationException {
//		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
//				request.getParameter("userEmail"), request.getParameter("userPw"));
//		setDetails(request, authRequest);
//		return this.getAuthenticationManager().authenticate(authRequest);
//	}
//}
