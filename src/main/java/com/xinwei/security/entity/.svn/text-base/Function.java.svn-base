
package com.xinwei.security.entity;

public class Function extends IdEntity implements Comparable<Function> {

	private static final long serialVersionUID = 1L;

	private Long menu_id;

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

	private String remark;

	//辅助字段，用于前台展示
	private Integer type = 1;

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

	public Long getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(Long menu_id) {
		this.menu_id = menu_id;
	}

	/*
	 * 
	 */
	@Override
	public int compareTo(Function m) {
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

	@Override
	public int hashCode() {
		return this.id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Function) {
			Function function = (Function) obj;
			return this.id == function.getId();
		}
		return false;
	}

}
