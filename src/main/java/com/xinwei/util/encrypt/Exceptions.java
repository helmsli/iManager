/**
 * Copyright (c) 2005-2012 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.xinwei.util.encrypt;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 鍏充簬寮傚父鐨勫伐鍏风被.
 * 
 * @author calvin
 */
public class Exceptions {

	/**
	 * 灏咰heckedException杞崲涓篣ncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 灏咵rrorStack杞寲涓篠tring.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 鍒ゆ柇寮傚父鏄惁鐢辨煇浜涘簳灞傜殑寮傚父寮曡捣.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
