package com.xinwei.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 对前台参数进行处理，去掉前缀，成为所需要的参数
 * @author shaoyong
 */
public class ParameterFilter extends BaseFilter {
	public final static String PAGE_PREFIX = "page.";
	public final static String REQUEST_PREFIX = "request.";

	 @Override  
	    public void init() throws ServletException {//初始化  
	        FilterConfig config = getConfig();  
	    } 
	

	public void doWhiteFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)  
	        throws IOException, ServletException {  
		
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String,String[]> result = new HashMap<String,String[]>();  
		String newKey = "";
		for(String key : parameterMap.keySet())
		{
			if(key.startsWith(PAGE_PREFIX)){
				newKey = key.substring(PAGE_PREFIX.length());
			}else if(key.startsWith(REQUEST_PREFIX)){
				newKey = key.substring(REQUEST_PREFIX.length());
			}else{
				newKey = key;
			}
			result.put(newKey, parameterMap.get(key));
		}
		
		request = new ParameterRequestWrapper((HttpServletRequest)request, result);  
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
