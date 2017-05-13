package com.xinwei.process.Listener;

import javax.annotation.Resource;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Service;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.Project;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.util.JsonUtil;

/**
 * 变更审核通过监听器
 *
 */
@Service
public class ChangePassListener implements ExecutionListener {
	@Resource
	private ProjectService projectServiceImpl;
	@Resource
	private ProcessService processServiceImpl;// 流程相关服务
	@Resource
	private CommonBizService commonBizService;// 变更服务
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		// 获取业务key
		String businessKey = execution.getBusinessKey();
		if (null != businessKey) {
			// 获取projectID
			Long projectId = processServiceImpl
					.getDataIdByBusinessKey(businessKey);
			//获取项目对象
			Project project = projectServiceImpl.selectByPrimaryKey(projectId);
			//获取变更对象
			CommonBiz commonBiz = commonBizService.selectByPrimaryKey(project.getChangeDataId());
			
			//从commonBiz对象中获取项目需要变更的信息
			Project projectBaseInfo = JsonUtil.fromJson(commonBiz.getData1(), Project.class);
			Project projectCosts = JsonUtil.fromJson(commonBiz.getData2(), Project.class);
			Project projectTaskDetail = JsonUtil.fromJson(commonBiz.getData3(), Project.class);
			
			//构造要进行变更的项目对象
			//设置项目基本信息
			if(null != projectBaseInfo){			
				project.setProjectName(projectBaseInfo.getProjectName());
				project.setSubcategory(projectBaseInfo.getSubcategory());
				project.setCycleType(projectBaseInfo.getCycleType());
				project.setProjectExtInfo(projectBaseInfo.getProjectExtInfo());
				project.setStartTime(projectBaseInfo.getStartTime());
				project.setCompleteTime(projectBaseInfo.getCompleteTime());
				project.setTelno(projectBaseInfo.getTelno());
				project.setEmail(projectBaseInfo.getEmail());
				project.setProjectMilestone(projectBaseInfo.getProjectMilestone());
			}
			//设置项目成本信息
			if(null != projectCosts){
				project.setProjectCosts(projectCosts.getProjectCosts());		
			}
			//设置项目成员分工信息
			if(null != projectTaskDetail){
				project.setProjectTaskDetail(projectTaskDetail.getProjectTaskDetail());
			}
			
			//修改项目信息
			projectServiceImpl.update(project);
		}
	}
}