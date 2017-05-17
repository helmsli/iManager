package com.xinwei.doc.vo;

public class Leader extends Contact {
	public Leader() {
		// TODO Auto-generated constructor stub
	}

	public Leader(String name, String job, String tel, String phone, String mail) {
		super();
		this.name = name;
		this.job = job;
		this.tel = tel;
		this.phone = phone;
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "项目负责人 [姓名=" + name + ", 职务=" + job + ", 办公电话=" + tel + ", 手机=" + phone + ", 电子邮箱=" + mail + "]";
	}
}
