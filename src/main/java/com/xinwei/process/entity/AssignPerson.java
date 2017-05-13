package com.xinwei.process.entity;

import com.xinwei.security.entity.IdEntity;

public class AssignPerson extends IdEntity {
	public static final int Allow = 0;
	public static final int Forbiden = 1;
	public static final int Role = 0;
	public static final int User = 1;
	
	private int roleType;
	private int privilege = Allow;

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public int getPrivilege() {
		return privilege;
	}

	public void setPrivilege(int privilege) {
		this.privilege = privilege;
	}

}
