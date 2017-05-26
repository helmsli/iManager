package com.xinwei.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletResponse;

/**
 * response 输出json文本
 * @author shaoyong
 *
 */
public class ResponseOutJsonUtil {
	
	/**
	 * response 输出JSON
	 * @param hresponse
	 * @param resultMap
	 * @throws IOException
	 */
	public static void out(ServletResponse response, String jsonString){
		
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/json; charset=utf-8");
			out = response.getWriter();
			out.println(jsonString);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
