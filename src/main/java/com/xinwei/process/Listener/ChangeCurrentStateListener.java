package com.xinwei.process.Listener;

import javax.annotation.Resource;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.stereotype.Service;

import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.Project;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;

@Service("changeCurrentStateListener")
public class ChangeCurrentStateListener implements TaskListener {
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private CommonBizService commonBizService;// 变更服务
	@Override
	public void notify(DelegateTask delegateTask) {
		
		//获取业务key
		String businessKey = delegateTask.getExecution()
				.getProcessBusinessKey();
		// ProjectID
		Long projectId = processServiceImpl.getDataIdByBusinessKey(businessKey);
		//获取项目对象
		Project project = projectService.selectByPrimaryKey(projectId);
		//获取变更对象
		CommonBiz commonBiz = commonBizService.selectByPrimaryKey(project.getChangeDataId());
		//获取项目类别
		String categoryId =  project.getCategoryId().toString();
		//将项目类别设置到任务表中
		delegateTask.setCategory(categoryId);
		
		//根据当前任务获取StateInfo
		String changeCurrentStateInfoJson = processServiceImpl.getStateInfoByDelegateTask(delegateTask);;
		// 更改变更状态
		commonBizService.updateChangeStatusByDataId(changeCurrentStateInfoJson,
				commonBiz.getDataId());
		//更改变更流程当前状态信息
		projectService.updateProjectPropertyByProjectId(ProjectService.CHANGECURRENTSTATE, changeCurrentStateInfoJson, projectId);
	}
}
	