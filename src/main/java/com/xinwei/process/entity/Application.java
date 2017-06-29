package com.xinwei.process.entity;

public class Application {
	/*
	 * var alldata={"pName":$scope.application.projectName,
 "personName":$scope.application.contactName,
 "person":$scope.application.contactTel,
 "address":$scope.application.registerAddress,
 "amount":$scope.application.amount,
"cname":$scope.application.companyName,
"cType":$scope.application.comType
};
*/
	//项目名
	private String pName;
	//联系名
	private String personName;
	//联系电话
	private String person;
	//注册地址
	private String address;
	private String addressCode;
	//申报金额
	private String amount;
	//公司名称
	private String cname;
	//公司类型
	private String cType;
	private String email;
	
	public String getAddressCode() {
		return addressCode;
	}
	public void setAddressCode(String addressCode) {
		this.addressCode = addressCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getpName() {
		return pName;
	}
	public void setpName(String pName) {
		this.pName = pName;
	}
	public String getPersonName() {
		return personName;
	}
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	public String getPerson() {
		return person;
	}
	public void setPerson(String person) {
		this.person = person;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getcType() {
		return cType;
	}
	public void setcType(String cType) {
		this.cType = cType;
	}
	@Override
	public String toString() {
		return "Application [pName=" + pName + ", personName=" + personName + ", person=" + person + ", address="
				+ address + ", amount=" + amount + ", cname=" + cname + ", cType=" + cType + ", email=" + email + "]";
	}
	
	
}
