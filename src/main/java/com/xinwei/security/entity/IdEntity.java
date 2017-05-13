package com.xinwei.security.entity;

import java.io.Serializable;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 */
public abstract class IdEntity implements Serializable {
	/** 描述  */
	private static final long serialVersionUID = 8430941165882152228L;

	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
