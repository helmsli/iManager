package com.xinwei.doc.vo;

public class Contact {
	protected String name;
	protected String job;
	protected String tel;	
	protected String phone;
	protected String mail;
	public Contact() {
	}
	
	public Contact(String name, String job, String tel, String phone, String mail) {
		super();
		this.name = name;
		this.job = job;
		this.tel = tel;
		this.phone = phone;
		this.mail = mail;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getJob() {
		return job;
	}


	public void setJob(String job) {
		this.job = job;
	}


	public String getTel() {
		return tel;
	}


	public void setTel(String tel) {
		this.tel = tel;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getMail() {
		return mail;
	}


	public void setMail(String mail) {
		this.mail = mail;
	}


	@Override
	public String toString() {
		return "项目联系人 [姓名=" + name + ", 职务=" + job + ", 办公电话=" + tel + ", 手机=" + phone + ", 电子邮箱=" + mail + "]";
	}
}
