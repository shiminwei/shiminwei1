package com.ahcd.common;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class HttpServletResponseUtils {

	public static void writer(HttpServletResponse response, String str) {
		try {
			response.setHeader("Content-type", "text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
