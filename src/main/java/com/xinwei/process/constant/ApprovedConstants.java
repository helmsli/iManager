package com.xinwei.process.constant;

/**
 * 单位相关常量：当前状态（未审核/已审核）
 * 
 * @author fangping
 */
public interface ApprovedConstants {
	
	String NOT_APPROVED= "未审核";//状态（未审核）
	
	Integer CODE_NOT_APPROVED=0;//未审核状态码

	public interface ApproveResult {
		//审核结果
		String Agree = "pass";//同意申请
		String Reject = "reject";//拒绝
		String NeedModify = "needmodify";//驳回需要修改
		String cancel = "cancel";//取消
		
		Integer CODE_Agree = 1;//审核通过
		Integer CODE_Reject = 2;//审核不通过
	}
	public interface TaskApproveResult{
		
		int CODE_REJECT = 1;
		int CODE_AGREE  = 0;
	}
}
