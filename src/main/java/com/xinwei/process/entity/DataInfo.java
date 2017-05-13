package com.xinwei.process.entity;

public class DataInfo {

    public static final String DATATYPE_PUBLISH = "publish"; //部门经理发布
    public static final String DATATYPE_PROJECT = "project"; //项目申请
	
    public String dataId;//数据ID

    public Long categoryId;//业务种类

    public String dataType;//数据类型

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
    
}
