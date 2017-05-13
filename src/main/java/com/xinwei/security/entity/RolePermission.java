package com.xinwei.security.entity;

import com.xinwei.security.entity.IdEntity;

public class RolePermission extends IdEntity {
	
	private static final long serialVersionUID = -8888778227379780116L;
	
	
	public RolePermission(){}
	
	public RolePermission(Long role_id,Long resource_id,Integer resource_type){
		this.role_id = role_id;
		this.resource_id = resource_id;
		this.resource_type = resource_type;
	}
	
	
	private transient Long role_id;
	
	private Long resource_id; 
	
	private Integer resource_type;
	
	//资源关键字（辅助字段）
	private transient String resource_sn;
	//资源名（辅助字段）
	private transient String resource_name;
	
	private Menu menu;
	
	private Function function;
	
	

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public Function getFunction() {
		return function;
	}

	public void setFunction(Function function) {
		this.function = function;
	}

	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public Long getResource_id() {
		return resource_id;
	}

	public void setResource_id(Long resource_id) {
		this.resource_id = resource_id;
	}

	public Integer getResource_type() {
		return resource_type;
	}

	public void setResource_type(Integer resource_type) {
		this.resource_type = resource_type;
	}

	public String getResource_sn() {
		return resource_sn;
	}

	public void setResource_sn(String resource_sn) {
		this.resource_sn = resource_sn;
	}

	public String getResource_name() {
		return resource_name;
	}

	public void setResource_name(String resource_name) {
		this.resource_name = resource_name;
	}
	
	
	
	
	
}
