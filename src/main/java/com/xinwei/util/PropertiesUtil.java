package com.xinwei.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static void main(String[] args) {
	    getPropertiesValue();
	}
	//在使用ClassLoader.getResourceAsStream时， 路径直接使用相对于classpath的绝对路径
	public static String getPropertiesValue() {
		String roleId="";
		InputStream in =PropertiesUtil.class.getClassLoader().getResourceAsStream("roleRegister.properties");  
		Properties prop = new Properties();  
		try {
			prop.load(in);
			roleId = prop.getProperty("request.roleIds"); 
			System.out.println(roleId);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return roleId;  
		
	}
}

