package com.xinwei.process.Listener;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.TaskDefKeyNameService;


@Service
public class ReportCompleteListener implements TaskListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private TaskDefKeyNameService taskDefKeyNameServiceImpl;// 获取状态名服务
	@Override
	public void notify(DelegateTask delegateTask) {
		//获取业务key
		String businessKey = delegateTask.getExecution()
				.getProcessBusinessKey();
		if (null!=businessKey) {
			Long projectId = processServiceImpl.getDataIdByBusinessKey(businessKey);
			//构建StateInfo对象
			String state = ProjectConstants.State.EndMonthlyReport;
			String stateName = taskDefKeyNameServiceImpl.selectStateNameByPrimaryKey(state);
			StateInfo reportCurrentStateInfo = new StateInfo(state,stateName,"","");
			String reportCurrentStateInfoJson = reportCurrentStateInfo.toString();
			// 更新周期性报告状态
			projectService.updateProjectPropertyByProjectId(ProjectService.REPORTCURRENTSTATE, reportCurrentStateInfoJson, projectId);
		}
	}
}