package com.xinwei.process.Listener;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;

import com.xinwei.process.constant.ChangeConstants;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.Project;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.TaskDefKeyNameService;
import com.xinwei.util.JsonUtil;

@Service
public class ChangeEndListener implements ExecutionListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;// 流程相关服务
	@Resource
	private CommonBizService commonBizService;// 变更服务
	@Resource
	private TaskDefKeyNameService taskDefKeyNameServiceImpl;// 获取状态名服务
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// 获取业务key
		String businessKey = execution.getBusinessKey();
		if (null != businessKey) {
			// 获取projectID
			Long projectId = processServiceImpl
					.getDataIdByBusinessKey(businessKey);
			//获取项目对象
			Project project = projectService.selectByPrimaryKey(projectId);
			//获取变更对象
			CommonBiz commonBiz = commonBizService.selectByPrimaryKey(project.getChangeDataId());
			// 构建StateInfo对象endChangeProcess
			StateInfo endProcessStateInfo = new StateInfo();
			endProcessStateInfo.setState(ChangeConstants.State.ENDEVENT);
			String stateName = taskDefKeyNameServiceImpl.selectStateNameByPrimaryKey(ChangeConstants.State.ENDEVENT);
			endProcessStateInfo.setStateName(stateName);
			endProcessStateInfo.setTaskId("");
			
			String endProcessStateInfoJson = endProcessStateInfo.toString();
			// 更改变更状态
			commonBizService.updateChangeStatusByDataId(endProcessStateInfoJson,
					commonBiz.getDataId());
			// 更新项目中的变更状态
			projectService.updateProjectPropertyByProjectId(
					ProjectService.CHANGECURRENTSTATE, endProcessStateInfoJson,
					commonBiz.getProjectId());
		}

	}
}