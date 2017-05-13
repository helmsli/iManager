package com.xinwei.security.vo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xinwei.util.page.Page;

public class ResultVO<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	//private static Gson gson = new Gson();
	private static Gson gson = new GsonBuilder()
			.serializeNulls()//序列化null
			.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
			.create();
    
	public final static String SUCCESS = "0";//成功
	public final static String FAILURE = "1";//失败
	public final static String EXCEPTION = "-1";//服务器内部异常
	public final static String USERNULL= "2";//登录用户信息为空
	public final static String NOAUTHORITY="3";//没有权限
	// 返回状态码
	private String result = SUCCESS;
	
	// 返回多个信息集合
	private Map<String, Object> responseInfo = new HashMap<String, Object>();

	public ResultVO() {
	}

	/**
	 * 默认是成功
	 * 
	 * @param message
	 */
	public ResultVO(String result) {
		super();
		this.result = result;
	}

	/*
	 * public Map<String, Object> getResponseInfo() { return responseInfo; }
	 */

	public void setResponseInfo(Map<String, Object> responseInfo) {
		this.responseInfo = responseInfo;
	}

	public void setPage(Page<T> page) {
		this.responseInfo.put("page", page);
	}

	public void setLists(List lists) {
		this.responseInfo.put("lists", lists);
	}

	public void setEntity(T entity) {
		this.responseInfo.put("entity", entity);
	}

	public void setId(Long id) {
		this.responseInfo.put("id", id);
	}

	/**
	 * 设置其它值
	 * 
	 * @param key
	 * @param value
	 */
	public void setOthers(String key, Object value) {
		this.responseInfo.put(key, value);
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return gson.toJson(this);
	}

}
