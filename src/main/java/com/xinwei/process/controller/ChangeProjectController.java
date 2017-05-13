package com.xinwei.process.controller;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.process.constant.ApprovedConstants;
import com.xinwei.process.constant.ChangeConstants;
import com.xinwei.process.constant.ChangeConstants.ServiceType;
import com.xinwei.process.entity.ApprovalResult;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.Project;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.TaskDefKeyNameService;
import com.xinwei.process.service.UserTaskService;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.JsonUtil;

/**
 * 项目变更控制器
 *
 */
@Controller
@RequestMapping("/changeproject")
public class ChangeProjectController extends BaseController {// 0221
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;// 流程相关服务
	@Resource
	private CommonBizService commonBizService;// 变更服务
	@Resource
	private ProjectService projectServiceImpl;// 项目相关服务
	@Resource
	private RuntimeService runtimeService;//Activiti中流程运行时相关服务
	@Resource
	private UserTaskService userTaskServiceImpl;// 用户任务相关服务
	@Resource
	private TaskDefKeyNameService taskDefKeyNameServiceImpl;// 获取状态名服务

	private Logger logger = LoggerFactory
			.getLogger(ChangeProjectController.class);

	/**
	 * 保存变更(包括修改后保存)
	 * 
	 * @param CommonBiz
	 * @return
	 */
	@RequestMapping(value = "/createChange", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String postChangeRequest(CommonBiz commonBiz) {
		ResultVO<Object> resultVO = new ResultVO<>();
		// 根据project获取项目经理，
		Project project = projectService.selectByPrimaryKey(commonBiz
				.getProjectId());
		User user = getCurrentUser();
		//如果项目信息、当前用户信息不为空
		if(null !=project && null != user){
			logger.debug(user.getId() + "manager:" + project.getProjectManagerId());
			//仅仅该项目经理能够进行该项目的变更操作
			if (user.getId().longValue() != project.getProjectManagerId()
					.longValue()) {
				// 构造没有权限提出变更申请
				resultVO.setResult(ChangeConstants.ErrorCode.NOT_AUTHORITY_CHANGE);
				return resultVO.toString();
			}
			String result = "0";
			// 新增
			if (isNewChange(project)) {
				result = createNewChange(commonBiz, project,user);
			} else {// 修改
				result = modifyChange(commonBiz, project,user);
			}	
			resultVO.setResult(result);
		}else{
			resultVO.setResult(ResultVO.FAILURE);
		}
		// 响应客户端
		resultVO.setOthers("dataId", commonBiz.getDataId());
		return resultVO.toString();
		
	}

	/*
	 * 创建新的变更
	 */
	private String createNewChange(CommonBiz commonBiz, Project project,User user) {
		//获取变更状态
		String changeCurrentState = project.getChangeCurrentStateFromStatusInfo();
		if (!StringUtils.isBlank(changeCurrentState)
				&& !changeCurrentState
						.equals(ChangeConstants.State.CODE_ENDEVENT)) {
			// 判断project表格中状态，如果状态不是空或结束，不允许变更；
			return ChangeConstants.ErrorCode.STATUS_NOT_ALLOW_CHANGE;
		}
		// 设置创建者，创建时间
		commonBiz.setCreatePerson(user.getId().toString());
		commonBiz.setCreateTime(Calendar.getInstance().getTime());
		
		// 构造状态信息
		String state = ChangeConstants.State.STARTEVENT;
		// 获取状态名
		String stateName = taskDefKeyNameServiceImpl.selectStateNameByPrimaryKey(state);
		StateInfo stateInfo = new StateInfo(state,stateName,null,null);
		commonBiz
				.setStatus(stateInfo.toString());
		commonBiz.setServiceType(ServiceType.TYPE_XMBIANGENG);
		// 保存变更
		String dataId = commonBizService.save(commonBiz);

		// 修改project表中： 变更DataID
		projectServiceImpl.updateProjectPropertyByProjectId(ProjectService.CHANGEDATAID, dataId, commonBiz.getProjectId());
		
		Map<String, Object> variables = new HashMap<String, Object>();
		// 获取项目主流程中的所有流程变量
		variables = runtimeService.getVariables(project.getProjectProcessInstanceId());
		
		variables.put(ChangeConstants.ActivitiContextKey.ChangeServiceID,
				commonBiz.getDataId());
		// 创建一个流程，并启动该流程实例(在启动流程实例时，将当前CommonBiz的ID作为businessKey)，将流程实例ID保存到CommonBiz；
		ProcessInstance processInstance = processServiceImpl.startProcess(
				ChangeConstants.getProcessKey(project.getCategoryId()),project.getClass().getSimpleName(), project
						.getProjectId().toString(),
				commonBiz.getCreatePerson(), variables);

		// 设置CommonBiz对象的流程实例ID
		commonBiz.setProcessInstanceId(processInstance.getId());
		commonBizService.update(commonBiz);
		
		// 修改project表中： 变更流程实例ID
		projectServiceImpl.updateProjectPropertyByProjectId(ProjectService.CHANGEPROCESSINSTANCEID, processInstance.getId(), commonBiz.getProjectId());
		return "0";
	}

	/*
	 * 修改变更
	 */
	private String modifyChange(CommonBiz commonBiz, Project project,User user) {
		// CommonBiz设置更改人，更改时间
		commonBiz.setUpdatePerson(user.getUsername());
		commonBiz.setUpdateTime(Calendar.getInstance().getTime());

		// 设置流程变量
		CommonBiz oldEntity = commonBizService.selectByPrimaryKey(commonBiz
				.getDataId());
		commonBiz.setProcessInstanceId(oldEntity.getProcessInstanceId());
		commonBiz.setStatus(oldEntity.getStatus());
		commonBiz.setServiceType(oldEntity.getServiceType());
		//更新commonBiz对象
		commonBizService.update(commonBiz);

		// 获取当前任务
		List<Task> currentTask = userTaskServiceImpl
				.findActiveTaskByProcessInstanceId(commonBiz
						.getProcessInstanceId());
		// 获取task对象
		Task task = currentTask.get(0);

		// 获取修改结论
		String stringResult = commonBiz.getResult();
		
		ApprovalResult approvalResult = JsonUtil.fromJson(stringResult,
				ApprovalResult.class);
		
		Map<String, Object> variables = new HashMap<String, Object>();
		// 如果有结论信息
		if (null != approvalResult) {
			logger.debug("approvalResult: " + approvalResult.getResult());			
			// 设置审批结果
			setApproveResult(task, variables, approvalResult);
		}

		userTaskServiceImpl.completeTask(task.getId(), variables);
		return "0";
	}

	/*
	 * 判断是否为新创建变更
	 */
	private boolean isNewChange(Project project) {
		if (StringUtils.isBlank(project.getChangeCurrentStateFromStatusInfo())
				|| project.getChangeCurrentStateFromStatusInfo()
						.compareToIgnoreCase(
								ChangeConstants.State.CODE_STARTEVENT) == 0
				|| project.getChangeCurrentStateFromStatusInfo()
						.compareToIgnoreCase(
								ChangeConstants.State.CODE_ENDEVENT) == 0)
			return true;
		return false;
	}

	// 设置审批结果
	private void setApproveResult(Task task, Map<String, Object> variables,
			ApprovalResult approvalResult) {
		// 如果审批意见为通过
		if (ApprovedConstants.TaskApproveResult.CODE_AGREE == approvalResult
				.getResult()) {
			variables.put(task.getTaskDefinitionKey()
					+ ChangeConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.Agree);
		} else if (ApprovedConstants.TaskApproveResult.CODE_REJECT == approvalResult
				.getResult()) {
			// 审批意见为不通过
			variables.put(task.getTaskDefinitionKey()
					+ ChangeConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.Reject);
		} else {
			// 审批意见为结束流程
			variables.put(task.getTaskDefinitionKey()
					+ ChangeConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.cancel);
		}
	}

	/**
	 * 根据projectId查询CommonBiz对象
	 * 
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	@RequestMapping(value = "/getCommonBizByProjectId", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getCommonBizByProjectId(Long projectId) {
		ResultVO<Project> resultVO = new ResultVO<Project>();
		try {
			Project project = projectService.selectByPrimaryKey(projectId);
			if(null != project){
				String changeDataId = project.getChangeDataId();
				CommonBiz commonBiz = commonBizService.selectByPrimaryKey(changeDataId);
				resultVO.setOthers("commonBiz", commonBiz);
			}
			else{
				resultVO.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			resultVO.setResult(resultVO.FAILURE);
			e.printStackTrace();
		}
		return resultVO.toString();
	}
}
