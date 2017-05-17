package com.xinwei.doc.vo;

import java.util.ArrayList;
import java.util.List;

public class ProjectInfo {
	private String name;
	private String declarationUnit;
	private String declarationUnitType;
	private String openingUnit;
	private String openingBank;
	private String bankAccount;
	private String applicationAmount;

	private String startDate;
	private String endDate;
	private String projectArea;
	private String serviceField;
	private String projectDesc;
	private String declaredFunds;
	private CounterpartFunds counterpartFunds;
	private List<Contact> contactList;

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeclarationUnit() {
		return declarationUnit;
	}

	public void setDeclarationUnit(String DeclarationUnit) {
		this.declarationUnit = DeclarationUnit;
	}

	public String getDeclarationUnitType() {
		return declarationUnitType;
	}

	public void setDeclarationUnitType(String DeclarationUnit类型) {
		this.declarationUnitType = DeclarationUnit类型;
	}

	public String getOpeningUnit() {
		return openingUnit;
	}

	public void setOpeningUnit(String OpeningUnit) {
		this.openingUnit = OpeningUnit;
	}

	public String getOpeningBank() {
		return openingBank;
	}

	public void setOpeningBank(String OpeningBank) {
		this.openingBank = OpeningBank;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String BankAccount) {
		this.bankAccount = BankAccount;
	}

	public String getApplicationAmount() {
		return applicationAmount;
	}

	public void setApplicationAmount(String ApplicationAmount) {
		this.applicationAmount = ApplicationAmount;
	}

	public String getProjectArea() {
		return projectArea;
	}

	public void setProjectArea(String ProjectArea) {
		this.projectArea = ProjectArea;
	}

	public String getServiceField() {
		return serviceField;
	}

	public void setServiceField(String ServiceField) {
		this.serviceField = ServiceField;
	}

	public String getProjectDesc() {
		return projectDesc;
	}

	public void setProjectDesc(String ProjectDesc) {
		this.projectDesc = ProjectDesc;
	}

	public String getDeclaredFunds() {
		return declaredFunds;
	}

	public void setDeclaredFunds(String declaredFunds) {
		this.declaredFunds = declaredFunds;
	}

	public CounterpartFunds getCounterpartFunds() {
		return counterpartFunds;
	}

	public void setCounterpartFunds(CounterpartFunds CounterpartFunds) {
		this.counterpartFunds = CounterpartFunds;
	}

	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> contactList) {
		this.contactList = contactList;
	}

	public void addContact(Contact contact) {
		if (this.contactList == null) {
			this.contactList = new ArrayList<Contact>();
		}
		this.contactList.add(contact);
	}

	@Override
	public String toString() {
		return "项目信息 [项目名称=" + name + ", 申报单位=" + declarationUnit + ", 申报单位类型=" + declarationUnitType + ", 开户单位名称="
				+ openingUnit + ", 开户银行=" + openingBank + ", 银行账号=" + bankAccount + ", 申请金额=" + applicationAmount
				+ ", 执行周期=" + startDate+"至"+endDate + ", 项目实施地域=" + projectArea + ", 服务领域=" + serviceField + ", 项目简介=" + projectDesc
				+ ", 申报资金=" + declaredFunds + ", 配套资金=" + counterpartFunds + ", 联系人=" + contactList + "]";
	}

}
