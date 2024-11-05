package Extras;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class GetCookie {
	public static String myCookie(HttpServletRequest request, String Cookie_Name) {
		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}
		for (Cookie biscuits : cookies) {
			if (biscuits.getName().equalsIgnoreCase(Cookie_Name)) {
				return biscuits.getValue();
			}
		}
		return null;
	}
}
