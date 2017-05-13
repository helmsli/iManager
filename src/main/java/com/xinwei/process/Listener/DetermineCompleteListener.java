package com.xinwei.process.Listener;

import javax.annotation.Resource;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

/**
 * 判断项目是否审批通过监听器
 * @author xuejinku
 *
 */
@Service("determineCompleteListener")
public class DetermineCompleteListener implements ExecutionListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5797861377999424722L;
	@Resource
	private RuntimeService runtimeService;// 流程运行时相关服务
	
	public void notify(DelegateExecution execution) throws Exception {
		System.out.println("--------监听器执行------------");
		//获取流程中的变量--终期部门经理审批意见 
	    String departleaderEndApproval_result = (String)runtimeService.getVariable(execution.getProcessInstanceId(), "departleaderEndApproval_result");  
		//根据终期部门经理审批意见设置提交周报流程变量
		runtimeService.setVariable(execution.getProcessInstanceId(), "submitMonthlyReport_result", departleaderEndApproval_result);
		System.out.println("--------监听器执行完毕------------");
	}

}
