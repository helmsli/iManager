package com.xinwei.process.Listener;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.Project;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;


@Service("reportCurrentStateListener")
public class ReportCurrentStateListener implements TaskListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Override
	public void notify(DelegateTask delegateTask) {
		
		//获取业务key
		String businessKey = delegateTask.getExecution()
				.getProcessBusinessKey();// projectID
		Long projectId = processServiceImpl.getDataIdByBusinessKey(businessKey);
		//获取项目对象
		Project project = projectService.selectByPrimaryKey(projectId);
		//获取项目类别
		String categoryId =  project.getCategoryId().toString();
		//将项目类别设置到任务表中
		delegateTask.setCategory(categoryId);
		//如果为项目经理提交周期报告
		if(ProjectConstants.State.SUBMITMONTHLYREPORT.equals(delegateTask.getTaskDefinitionKey())){
			//获取状态信息
			String reportCurrentStateInfoJson = processServiceImpl.getStateInfoByDelegateTask(delegateTask);
			//更改主流程上一任务状态信息
			projectService.updateProjectPropertyByProjectId(ProjectService.REPORTCURRENTSTATE, reportCurrentStateInfoJson, projectId);
		}
	}
}
	