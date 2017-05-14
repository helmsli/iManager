package com.xinwei.process.entity;

/**
 * 数据权限
 *
 */
public class DataPermission extends DataInfo {
	
	public static final String PERMISSIONTYPE_ROLE = "0";
	public static final String PERMISSIONTYPE_USER = "1";
	
	public static final String PERMISSIONTYPE_User_Black = "2";
	public static final String PERMISSIONTYPE_Role_Black = "3";
	
	
	
	//权限是否是第三方监管，如果是项目优化，需要填写为第三方评估，
	public static final String PRIVILEGE_ThreeEval = "optimize";
	//申请
	public static final String PRIVILEGE_canReadWrite = "application";
	
	//发起者
    public static final String PRIVILEGE_Owner = "owner";
		
	//
	public static final String PRIVILEGE_canReadWriteowner = "application";
		
	
	//业务申请权限,用于
	public static final String ExtData3_Application = "apply";
	
	
	
    private String permissionType;//权限类型(组、用户)

    private String permissionId;//权限类型对应的ID

    //用来归属的第三方评估机构
    private String extData1=PRIVILEGE_canReadWrite;//扩展信息
    
    

    
    private String extData2;

    //权限的详细描述，比如申请全新
    private String extData3;

    /**
     * 设置用户不能申请数据，对于用户而言，如果已经申请了数据，需要将该数据列入黑名单
     * @param categoryId
     * @param dataType
     * @param dataId
     * @param userid
     */
    public void setDataCanNotApplyForUser(Long categoryId,String dataType,String dataId,String userid)
    {
    	this.setPermissionType(PERMISSIONTYPE_User_Black);
    	this.setCategoryId(categoryId);
    	this.setDataType(dataType);
    	this.setDataId(dataId);
    	this.setPermissionId(userid);
    	this.setExtData3(ExtData3_Application);
    }
    public void setDataCanNotApplyForRole(Long categoryId,String dataType,String dataId,String roleId)
    {
    	this.setPermissionType(PERMISSIONTYPE_Role_Black);
    	this.setCategoryId(categoryId);
    	this.setDataType(dataType);
    	this.setDataId(dataId);
    	this.setPermissionId(roleId);
    	this.setExtData3(ExtData3_Application);
    }
    
    public boolean isReadWrite()
    {
    	try {
			return (extData1.compareToIgnoreCase(PRIVILEGE_canReadWrite)==0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return true;
		}
    }
    
    public boolean isPrivilegeThreeEval()
    {
    	try {
			return (extData1.compareToIgnoreCase(PRIVILEGE_ThreeEval)==0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    
    public void setPrivilegeThrreeEval()
    {
    	extData1 = PRIVILEGE_ThreeEval;
    }
    
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