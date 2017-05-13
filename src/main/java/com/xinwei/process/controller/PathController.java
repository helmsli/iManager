 
package com.xinwei.process.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/views")
public class PathController {


	/*@RequestMapping(value="/{html}.html", method={RequestMethod.GET, RequestMethod.POST})
	public  String basePath(@PathVariable("html") String html) {	
		return  html;
	}*/
	
	/*
	*//**
	 * save
	 * @param project
	 * @return
	 *//*
	@RequestMapping(value="/{project}/{html}.html", method={RequestMethod.GET, RequestMethod.POST})
	public  String path(@PathVariable("project") String project,@PathVariable("html") String html,String projectName) {	
		System.out.println("projectName===========" + projectName);
		if(StringUtils.isNotEmpty(projectName)){
			return "manager" + "/" + html;
		}else{
			return  project + "/" + html;
		}
	}
	*/
	
	
	@RequestMapping(value="/**/*.html", method={RequestMethod.GET, RequestMethod.POST})
	public  String path(HttpServletRequest request,String projectName) {	
		String contextPath = request.getContextPath();
		String requestURI = request.getRequestURI();
		
		String result = requestURI.substring(contextPath.length());
		if(result.startsWith("/views") )
		{
			result = result.substring("/views".length());
			if(StringUtils.isNotEmpty(projectName) )
				result = result.replaceFirst("\\/\\w+", "/manager");
		}
		result = result.substring(0,result.length()-".html".length());
		return result;
	}
	
	
	public static void main(String[] args) {
		
		String replaceFirst = "/vi222e_ws/sss/slkj.html".replaceFirst("\\/\\w+", "/manager");
		System.out.println(replaceFirst);
	}
}
