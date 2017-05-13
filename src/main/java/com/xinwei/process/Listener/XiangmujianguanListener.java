package com.xinwei.process.Listener;

import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import com.xinwei.process.entity.Project;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;

/**
 * 连接项目监管流程监听器
 * @author xuejinku
 *
 */
@Service
public class XiangmujianguanListener implements ExecutionListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;
	@Resource
	private RuntimeService runtimeService;// 流程运行时相关服务
	
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String businessKey = execution.getBusinessKey();// projectID
		if (null != businessKey) {
			Long projectId = processServiceImpl.getDataIdByBusinessKey(businessKey);
			
			// 获取当前项目信息
			Project project = projectService.selectByPrimaryKey(projectId);
			// 获取当前流程中的所有流程变量
			Map<String, Object> variables = runtimeService
					.getVariables(execution.getId()); 
			// 设置监管流程中的初始流程变量
			// 根据项目周期类型判断是否需要提交周期性报告，如需要设置周期时间
			variables.put("submitMonthlyReport_result", "reject"); // 设置流程变量：需要提交周期性报告
			variables.put("cycleTime", "PT5M"); // 设置周期时间（测试阶段：间隔5分钟）
			variables.put("departleaderEndApproval_result", "reject");// 初始化终期部门经理审批意见
			// 指定项目经理
			String projectManagerId = project.getProjectManagerId().toString();
			// 启动项目监管流程
			ProcessInstance processInstance = processServiceImpl.startProcess(
					"pm_process_jianguan",project.getClass().getSimpleName(), projectId.toString(), projectManagerId, variables);
		}
	}
}