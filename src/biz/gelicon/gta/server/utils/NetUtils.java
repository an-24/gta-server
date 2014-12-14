package biz.gelicon.gta.server.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class NetUtils {
	
	static public String getTokenFromCookie(HttpServletRequest request) {
    	for (Cookie cookie : request.getCookies()) {
    		if(cookie.getName().equals("token")) 
    			return cookie.getValue();
		}
		return null;
	}

}
