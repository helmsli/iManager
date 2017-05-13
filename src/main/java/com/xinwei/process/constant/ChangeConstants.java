package com.xinwei.process.constant;

/**
 * 项目相关常量
 * 
 * @author fangping
 */
public class ChangeConstants {
	
	public static String getProcessKey(Long projectCategory)
	{
		return "changeProcess";
	}
	
	
	public static String getBusinessKey(Long projectid)
	{
		StringBuilder ret = new StringBuilder();
		
		ret.append(ChangeConstants.ActitiviFlow.Business_Key);
		ret.append(projectid);
		return ret.toString();
	}
	
	public interface State {
		// 项目变更状态名
		static final String STARTEVENT = "startChangeProcess";//开始变更
		static final String COMMITTEEAPPROVALCHANGE = "committeeApprovalChange";// B001决策委员会审批变更
		static final String DEPARTLEADERAPPROVALCHANGE = "departleaderApprovalChange";// B002 部门经理审批变更
		static final String MODIFYCHANGE = "modifyChange";// B003 修改变更
		static final String ENDEVENT = "endChangeProcess";//结束变更
		// 项目变更状态码
		static final String CODE_STARTEVENT = "startChangeProcess";//开始变更
		static final String CODE_COMMITTEE_APPROVALCHANGE = "B001";// 决策委员会审批变更
		static final String CODE_DEPARTLEADER_APPROVALCHANGE = "B002";// 部门经理审批变更
		static final String CODE_MODIFYCHANGE = "B003";// 修改变更
		static final String CODE_ENDEVENT = "endChangeProcess";//结束变更
	}
	
	
	public interface ActitiviFlow {
		static final String Process_Key = ":change_p";//变更业务流程关键字
		static final String Business_Key = "change_b:";//变更业务业务关键字
		
		//项目变更状态码
		static final String committeeApprovalChange = "committeeApprovalChange";//决策委员会审批
	
	}
	
	//置业务类型:
	public interface ServiceType {
		//项目变更状态码
		static final String TYPE_XMBIANGENG = "xMBianGeng";//项目变更
		static final String TYPE_JZDC = "JZDC";//尽职调查
	}
	
	public interface ActivitiContextKey {
		
		static final String ChangeServiceID = "ChangeServiceId";//申请变更   
		
		static final String result = "_result";//申请变更
	
	}
	
	public interface ErrorCode {
		//项目变更状态码
		static final String STATUS_NOT_ALLOW_CHANGE = "2";//该状态不允许修改
		static final String NOT_AUTHORITY_CHANGE = "3";// 该用户没有权限修改
	}

}
