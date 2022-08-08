package kr.co.kmac.pms;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoggerInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	private void wirteLog(String msg){
		logger.info(msg);
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		wirteLog("=================preHandle========================");

		printRequestInfo(request);
		 
        
		
		return super.preHandle(request, response, handler);
	}
	

	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		wirteLog("==================== END ======================");

	}
	
	private void printRequestInfo(HttpServletRequest req) {
		//logger.
	    StringBuffer requestURL = req.getRequestURL();
	    String queryString = req.getQueryString();

	    if (queryString == null) {
	    	wirteLog("url: " + requestURL.toString());
	    } else {
	    	wirteLog("url: " + requestURL.append('?').append(queryString).toString());
	    }

	    wirteLog( "form method:" + req.getMethod());

	    // print all the headers
	    Enumeration headerNames = req.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
	        String headerName = (String)headerNames.nextElement();
	        wirteLog("header: " + headerName + ":" + req.getHeader(headerName));
	    }

	    // print all the request params
	    Enumeration params = req.getParameterNames();
	    while(params.hasMoreElements()){
	        String paramName = (String)params.nextElement();
	        wirteLog("Parameter: "+paramName+"= "+req.getParameter(paramName) + "");
	    }
	}

}