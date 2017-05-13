package com.xinwei.process.Listener;

import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.UserTaskService;

@Service("projectPreviousStateListener")
public class ProjectPreviousStateListener implements TaskListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private UserTaskService userTaskServiceImpl;
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private RuntimeService runtimeService;// 流程运行时相关服务
	@Override
	public void notify(DelegateTask delegateTask) {
		//获取业务key
		String businessKey = delegateTask.getExecution()
				.getProcessBusinessKey();
		//获取任务办理者
		String assignee = delegateTask.getAssignee();
		// 获取当前流程中的所有流程变量
		Map<String, Object> variables = runtimeService
			.getVariables(delegateTask.getExecution().getId());
		//如果为管理员审批，设置该项目后续流程中的管理员为当前办理者
		if(ProjectConstants.State.OFFICERAPPROVAL.equals(delegateTask.getTaskDefinitionKey())){
			// 指定某个管理员
			variables.put("officer", assignee);
			// 指定管理员组
			variables.put("officerGroup","");
		}
		//如果为决策委员会审批，设置该项目后续流程中的决策委员会人员为当前办理者
		if(ProjectConstants.State.COMMITTEEAPPROVAL.equals(delegateTask.getTaskDefinitionKey())){
			// 指定决策委员会人员
			variables.put("committee", assignee);
			// 指定多个决策委员会人员
			variables.put("committees", "");
			// 指定决策委员会组
			variables.put("committeeGroup","");
		}
		if(ProjectConstants.State.DEPARTLEADERAPPROVAL.equals(delegateTask.getTaskDefinitionKey())){
			// 指定某个部门经理
			variables.put("departLeader",assignee);
			// 指定某个部门经理组
			variables.put("departLeaderGroup", "");
		}
		//更改流程中的流程变量
		runtimeService.setVariables(delegateTask.getExecution().getId(), variables);
		// projectID
		Long projectId = processServiceImpl.getDataIdByBusinessKey(businessKey);
		// 获取stateInfo
		String previousStateJson = processServiceImpl.getStateInfoByDelegateTask(delegateTask);
		//更改主流程上一任务状态信息
		projectService.updateProjectPropertyByProjectId(ProjectService.MAINPREVIOUSSTATE, previousStateJson, projectId);
	}
}
	