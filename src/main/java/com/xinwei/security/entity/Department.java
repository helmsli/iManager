
package com.xinwei.security.entity;

import java.util.List;

import com.google.common.collect.Lists;

public class Department extends IdEntity implements Comparable<Department> {

	private static final long serialVersionUID = 1L;

	private Long parent_id;
	
	private String name;

	private String description;

	/**
	 * 部门的排序号,越小优先级越高
	 */
	private Integer priority = 99;
	
	//扩展字段
	private String remark;

	private List<Department> children = Lists.newArrayList();
	

	





	public String getRemark() {
		return remark;
	}





	public void setRemark(String remark) {
		this.remark = remark;
	}





	public List<Department> getChildren() {
		return children;
	}





	public void setChildren(List<Department> children) {
		this.children = children;
	}





	public Long getParent_id() {
		return parent_id;
	}





	public void setParent_id(Long parent_id) {
		this.parent_id = parent_id;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	public String getDescription() {
		return description;
	}





	public void setDescription(String description) {
		this.description = description;
	}





	public Integer getPriority() {
		return priority;
	}





	public void setPriority(Integer priority) {
		this.priority = priority;
	}





	/*
	 * 
	 */
	@Override
	public int compareTo(Department m) {
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
