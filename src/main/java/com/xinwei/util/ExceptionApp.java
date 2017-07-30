package com.xinwei.util;

public class ExceptionApp extends Exception {
	public static final int EXCEPTION_SrcFileNoExist=1000;
	
	private int exceptionCode;

	
	public ExceptionApp(int exceptionCode)
	{
		this.exceptionCode=exceptionCode;
	}
	/**
	 * 
	 * @return
	 */
	public int getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * 
	 * @param exceptionCode
	 */
	public void setExceptionCode(int exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
}
