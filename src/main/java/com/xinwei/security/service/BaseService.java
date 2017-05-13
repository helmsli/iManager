package com.xinwei.security.service;

import java.io.Serializable;
import java.util.Map;

import com.xinwei.util.page.Page;


public interface BaseService<T, ID extends Serializable> {
	void save(T entity);
	
	void update(T entity);
	
	void delete(ID id);
	
	T get(ID id);
	
	Page<T> findByPage(Map<String,Object> map);
}
