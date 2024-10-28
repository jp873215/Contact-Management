package com.ex.extras;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class deleteCookie {
	public static boolean delete(HttpServletRequest request, HttpServletResponse response) {
		
		Cookie[] cookies = request.getCookies();
		int len = cookies.length;
		for(Cookie cook : cookies) {
			String name = cook.getName();
			Cookie newcook = new Cookie(name,"");
			newcook.setMaxAge(0);
			response.addCookie(newcook);
			len -= 1;
		}
		if (len==0) {
			System.out.println("TRUE");
			return true;
		}
		return false;
		
	}
}
