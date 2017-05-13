
package com.xinwei.security.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class Menu extends IdEntity implements Comparable<Menu> {

	private static final long serialVersionUID = 1L;

	private String name;

	/**
	 * 模块的入口地址
	 */
	private String action;

	private String imgurl;

	private String description;

	/**
	 * 标志符，用于授权名称（类似module:save）
	 */
	private String sn;

	/**
	 * 模块的排序号,越小优先级越高
	 */
	private Integer priority = 99;

	private Long parent_id;

	private List<Menu> children = Lists.newArrayList();

	private List<Function> functions = Lists.newArrayList();

	private String remark;

	
	//辅助字段，用于前台展示
	private Integer type = 0;

	// 辅助字段，用于前台 是否勾选
	private Boolean checked = false;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 返回 name 的值
	 * 
	 * @return name
	 */
	public String getName() {
		// return LocaleUtil.getLocaleMessage(name);
		return name;
	}

	/**
	 * 设置 name 的值
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 返回 description 的值
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置 description 的值
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回 priority 的值
	 * 
	 * @return priority
	 */
	public Integer getPriority() {
		return priority;
	}

	/**
	 * 设置 priority 的值
	 * 
	 * @param priority
	 */
	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Long getParent_id() {
		return parent_id;
	}

	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}

	/**
	 * 返回 children 的值
	 * 
	 * @return children
	 */
	public List<Menu> getChildren() {
		return children;
	}

	/**
	 * 设置 children 的值
	 * 
	 * @param children
	 */
	public void setChildren(List<Menu> children) {
		this.children = children;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public List<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(List<Function> functions) {
		this.functions = functions;
	}

	/*
	 * 
	 */
	@Override
	public int compareTo(Menu m) {
		if (m == null) {
			return -1;
		} else if (m == this) {
			return 0;
		} else if (this.priority < m.getPriority()) {
			return -1;
		} else if (this.priority > m.getPriority()) {
			return 1;
		}

		return 0;
	}

}
