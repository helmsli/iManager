package com.xinwei.process.Listener;

import javax.annotation.Resource;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.PublishApplyPersonService;
import com.xinwei.process.service.TaskDefKeyNameService;

@Service
public class EndEventListener implements ExecutionListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private PublishApplyPersonService publishApplyPersonServiceImpl;//发布申报人相关服务
	@Resource
	private TaskDefKeyNameService taskDefKeyNameServiceImpl;// 获取状态名服务
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String businessKey = execution.getBusinessKey();// projectID
		if (null != businessKey) {
			Long projectId = processServiceImpl.getDataIdByBusinessKey(businessKey);
			// 更新项目状态
			projectService.updateProjectPropertyByProjectId(
					ProjectService.STATTE, ProjectConstants.State.CODE_END,
					projectId);
			// 构建StateInfo对象
			StateInfo currentStateInfo = new StateInfo();
			currentStateInfo.setState(ProjectConstants.State.ENDEVENT);
			String stateName = taskDefKeyNameServiceImpl.selectStateNameByPrimaryKey(ProjectConstants.State.ENDEVENT);
			currentStateInfo.setStateName(stateName);
			currentStateInfo.setTaskId(null);
			// 更改主流程当前任务状态信息
			String currentStateInfoJson = currentStateInfo.toString();
			projectService.updateProjectPropertyByProjectId(
					ProjectService.MAINCURRENTSTATE, currentStateInfoJson,
					projectId);

		}

	}

}