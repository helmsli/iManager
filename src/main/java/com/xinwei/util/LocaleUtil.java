package com.xinwei.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * java端获取国际化 key值
 * @author shaoyong
 *
 */
public class LocaleUtil {
	private static Logger logger = Logger.getLogger(LocaleUtil.class);
	private LocaleUtil(){}
	
	/**
	 * 存储在session中的国际化 key
	 */
	public static String LOCALE_SESSION_KEY = "locale";
	
	/**
	 * 资源文件基础名字
	 */
	public static String BASENAME="message";
	
	
	/**
	 * 获取国际资源文件中的key值
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getLocaleMessage(String key){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		//打印从资源文件中取得的消息
		String result = "";
		try {
			//取得系统默认的国家/语言环境
			Locale locale =(Locale)request.getSession().getAttribute(LOCALE_SESSION_KEY);
			if(null == locale){
				locale = Locale.getDefault();
			}
			//根据指定国家/语言环境加载资源文件
			ResourceBundle bundle = ResourceBundle.getBundle(BASENAME , locale);
			result = bundle.getString(key);
		} catch (MissingResourceException e) {
			logger.error("没有配置国际化key：" + key + "   " + e.getMessage());
		}
		return result;
	}
	
	
	
	
	
	
}
