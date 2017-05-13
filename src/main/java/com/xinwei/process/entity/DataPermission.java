package com.xinwei.process.entity;

/**
 * 数据权限
 *
 */
public class DataPermission extends DataInfo {
	
	public static final String PERMISSIONTYPE_ROLE = "0";
	public static final String PERMISSIONTYPE_USER = "1";
	
    private String permissionType;//权限类型(组、用户)

    private String permissionId;//权限类型对应的ID

    private String extData1;//扩展信息

    private String extData2;

    private String extData3;

    public String getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(String permissionType) {
        this.permissionType = permissionType;
    }

    public String getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId;
    }

    public String getExtData1() {
        return extData1;
    }

    public void setExtData1(String extData1) {
        this.extData1 = extData1;
    }

    public String getExtData2() {
        return extData2;
    }

    public void setExtData2(String extData2) {
        this.extData2 = extData2;
    }

    public String getExtData3() {
        return extData3;
    }

    public void setExtData3(String extData3) {
        this.extData3 = extData3;
    }
}