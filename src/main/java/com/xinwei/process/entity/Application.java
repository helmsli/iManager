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
	
	private String serviceAddress;
	private String serviceAddressCode;
	
	//申报金额
	private String amount;
	//公司名称
	private String cname;
	//公司类型
	private String cType;
	private String email;
	private String serviceAmount;
	
	public String getServiceAddress() {
		return serviceAddress;
	}
	public void setServiceAddress(String serviceAddress) {
		this.serviceAddress = serviceAddress;
	}
	public String getServiceAddressCode() {
		return serviceAddressCode;
	}
	public void setServiceAddressCode(String serviceAddressCode) {
		this.serviceAddressCode = serviceAddressCode;
	}
	public String getServiceAmount() {
		return serviceAmount;
	}
	public void setServiceAmount(String serviceAmount) {
		this.serviceAmount = serviceAmount;
	}
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
				+ address + ", addressCode=" + addressCode + ", serviceAddress=" + serviceAddress
				+ ", serviceAddressCode=" + serviceAddressCode + ", amount=" + amount + ", cname=" + cname + ", cType="
				+ cType + ", email=" + email + ", serviceAmount=" + serviceAmount + "]";
	}
	
	
	
}
