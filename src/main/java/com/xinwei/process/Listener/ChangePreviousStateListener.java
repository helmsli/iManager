package com.xinwei.process.Listener;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.UserTaskService;

@Service("changePreviousStateListener")
public class ChangePreviousStateListener implements TaskListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private UserTaskService userTaskServiceImpl;
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Override
	public void notify(DelegateTask delegateTask) {
		
		//获取业务key
		String businessKey = delegateTask.getExecution()
				.getProcessBusinessKey();// projectID
		Long projectId = processServiceImpl.getDataIdByBusinessKey(businessKey);
		
		//构建StateInfo对象	
		String changePreviousStateInfoStateJson =processServiceImpl.getStateInfoByDelegateTask(delegateTask);
		//设置更改流程上一任务状态信息
		projectService.updateProjectPropertyByProjectId(ProjectService.CHANGEPREVIOUSSTATE, changePreviousStateInfoStateJson, projectId);
	}
}
	