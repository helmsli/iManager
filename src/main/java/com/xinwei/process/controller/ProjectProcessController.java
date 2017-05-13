package com.xinwei.process.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskInfo;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xinwei.process.constant.ApprovedConstants;
import com.xinwei.process.constant.ChangeConstants;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.ApprovalResult;
import com.xinwei.process.entity.AssignPerson;
import com.xinwei.process.entity.CommitteeApproval;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.CycleTime;
import com.xinwei.process.entity.DepartleaderApproval;
import com.xinwei.process.entity.DepartleaderPublish;
import com.xinwei.process.entity.ExpertReview;
import com.xinwei.process.entity.Project;
import com.xinwei.process.entity.ThreeleaderApplication;
import com.xinwei.process.entity.UserTask;
import com.xinwei.process.service.CommitteeApprovalService;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.DepartleaderApprovalService;
import com.xinwei.process.service.DepartleaderPublishService;
import com.xinwei.process.service.ExpertReviewService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectAnnexService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.TaskDefKeyNameService;
import com.xinwei.process.service.ThreeleaderApplicationService;
import com.xinwei.process.service.UserTaskService;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.page.Page;

/**
 * 项目流程控制器
 *
 */
@Controller
@RequestMapping("/task")
public class ProjectProcessController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(ProjectProcessController.class);
	@Resource
	private RuntimeService runtimeService;// 流程运行时相关服务
	@Resource
	private ProjectService projectService;// 项目相关服务
	@Resource
	private ProcessService processServiceImpl;// 流程相关服务
	@Resource
	private UserTaskService userTaskServiceImpl;// 用户任务相关服务
	@Resource
	private ExpertReviewService expertReviewServiceImpl;// 项目评审相关服务
	@Resource
	private DepartleaderApprovalService departleaderApprovalServiceImpl;// 部门领导审批相关服务
	@Resource
	private CommitteeApprovalService CommitteeApprovalServiceImpl;// 决策委员会审批相关服务
	@Resource
	private ProjectAnnexService projectAnnexServiceImpl;// 项目评审相关服务
	@Resource
	private CommonBizService CommonBizServiceImpl;//通用的业务数据服务
	@Resource
	private ThreeleaderApplicationService ThreeleaderApplicationServiceImpl;
	
	@Resource
	private DepartleaderPublishService departleaderPublishServiceImpl;//部门领导发布服务
	
	@Resource
	private TaskDefKeyNameService taskDefKeyNameServiceImpl;// 根据TaskDefinitionKey获取任务状态名服务
	@Resource
	private UserRoleService userRoleServiceImpl; // 用户组服务
	/**
	 * 获取某个用户的所有待办任务列表
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/usertasklist", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getUserTaskList(String userId) {
		logger.debug("GetUserTaskList--> userId: " + userId );
		ResultVO<Object> resultVO = new ResultVO<>();
		List<Task> taskList = userTaskServiceImpl.getTaskList(userId);
		List<UserTask> userTaskList = new ArrayList<UserTask>();
		//构造UserTask对象列表
		for(Task task: taskList){
			try {
				//构造UserTask对象
				UserTask userTask = buildUserTaskByTaskInfo(task);
				userTaskList.add(userTask);
			} catch (Exception e) {
				logger.debug("Exception : "+ e.getMessage());
				e.printStackTrace();
				continue;
			}	
		}
		resultVO.setOthers("taskList",userTaskList);
		logger.debug("GetUserTaskList-->result: "+resultVO.getResult());
		return resultVO.toString();
	}
	
	@RequestMapping(value = "/tasklist/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getTaskList(String categoryId, String userId) {
		logger.debug("GetTaskList--> userId: " + userId + ", categoryId: "+ categoryId);
		Long count = userTaskServiceImpl.countTasksByUidAndCategoryId(userId, categoryId);
		Page<UserTask> page = new Page<UserTask>(count);
		int startRow = page.getStartRow();
		int pageSize = page.getPageSize();
		List<Task> taskList = userTaskServiceImpl.getTaskListByUidAndCategoryId(userId, categoryId, startRow, pageSize);
		List<UserTask> userTaskList = new ArrayList<UserTask>();
		//构造UserTask对象列表
		for(Task task: taskList){	
			try {
				//构造UserTask对象
				UserTask userTask = buildUserTaskByTaskInfo(task);
				userTaskList.add(userTask);
			} catch (Exception e) {
				logger.debug("Exception : "+ e.getMessage());
				e.printStackTrace();
				continue;
			}	
			
		}
		page.setList(userTaskList);
		
		ResultVO<UserTask> resultVO = new ResultVO<UserTask>();
		resultVO.setPage(page); 
		resultVO.setOthers("taskList",page.getList());
		logger.debug("GetTaskList-->result: "+resultVO.getResult());
		return resultVO.toString();
	}

	@RequestMapping(value = "/finishedTasklist/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getFinishedTaskList(String categoryId,
			String userId) {
		logger.debug("GetFinishedTaskList--> userId: " + userId + ", categoryId: "+ categoryId);
		Long count = userTaskServiceImpl.countHistoryTasksByUidAndCategoryId(userId, categoryId);
		Page<UserTask> page = new Page<UserTask>(count);
		int startRow = page.getStartRow();
		int pageSize = page.getPageSize();
		List<HistoricTaskInstance> historicTaskList = userTaskServiceImpl.getHistoryTasksByUidAndCategoryId(userId, categoryId, startRow, pageSize);
		List<UserTask> userTaskList = new ArrayList<UserTask>();
		//构造UserTask对象列表
		for(HistoricTaskInstance historicTask: historicTaskList){
			try{
				//构造UserTask对象
				UserTask userTask = buildUserTaskByTaskInfo(historicTask);
				userTaskList.add(userTask);
			}catch(Exception e){
				logger.debug("Exception : "+ e.getMessage());
				e.printStackTrace();
				continue;
			}
		}
		page.setList(userTaskList);
		
		ResultVO<UserTask> resultVO = new ResultVO<UserTask>();
		resultVO.setPage(page); 
		resultVO.setOthers("taskList",page.getList());
		logger.debug("GetFinishedTaskList-->result: "+resultVO.getResult());
		return resultVO.toString();
	}

	/**
	 * 签收任务
	 * 
	 * @param taskId
	 *            任务id
	 * @param userId
	 *            用户id
	 * @return
	 */
	@RequestMapping(value = "/claim", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String claim(String taskId, String userId) {
		logger.debug("Claim --> userId: " + userId + ", taskId: "+ taskId);
		userTaskServiceImpl.claimTask(taskId, userId);
		return new ResultVO<>().toString();
	}
	
	/**
	 * 三级部门经理拒绝申报
	 * @param taskId 任务Id
	 *            
	 */
	@RequestMapping(value = "/rejectApply", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String rejectApply(String taskId) {
		logger.debug("rejectApply --> taskId: " + taskId);
		ResultVO<Object> result = new ResultVO<>();
		if (null != taskId && !"".equals(taskId)){
			// 完成任务
			Map<String, Object> variables = new HashMap<String, Object>();
			userTaskServiceImpl.completeTask(taskId, variables);
		} else {
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("rejectApply --> result: " + result.getResult());
		return result.toString();
	}

	/**
	 * 管理人员指定评审专家
	 * 
	 * @param taskId
	 *            任务Id
	 * @param expertReview
	 *            专家评审记录
	 * @return 专家评审记录ID
	 */
	/*
	@RequestMapping(value = "/dispatcherMaster", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String dispatcherMaster(ExpertReview expertReview,
			String taskId) {
		logger.debug("DispatcherMaster --> taskId: "+ taskId);
		ResultVO<Object> result = new ResultVO<>();
		//获取任务对象
		Task task = userTaskServiceImpl.getTask(taskId);
		if(null != task){
			
			String projectId = getDataIdByProcessInstanceId(task.getProcessInstanceId()).toString();
			// 设置项目ID
			expertReview.setProjectId(Long.parseLong(projectId));
			// 设置评审阶段
			String stage = "";
			if (ProjectConstants.State.DISPATCHERMASTERAPPROVAL.equals(task.getTaskDefinitionKey())) {
				stage = ProjectConstants.Stage.BEGIN;
			} else if (ProjectConstants.State.DISPATCHERMIDMASTER.equals(task.getTaskDefinitionKey())) {
				stage = ProjectConstants.Stage.MIDDLE;
			} else {
				stage = ProjectConstants.Stage.LAST;
			}
			expertReview.setStage(stage);

			// 保存专家评审对象
			Long recordId = expertReviewServiceImpl.save(expertReview);
			// 完成任务
			Map<String, Object> variables = new HashMap<String, Object>();
			userTaskServiceImpl.completeTask(taskId, variables);
			result.setOthers("recordId", recordId);
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("DispatcherMaster --> result: "+ result.getResult());
		return result.toString();
	}*/

	/**
	 * 管理人员录入专家评分
	 * 
	 * @param expertReview
	 *            专家评审记录
	 * @return
	 */
	@RequestMapping(value = "/inputMasterGrade", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String inputMasterGrade(ExpertReview expertReview,
			String taskId) {
		logger.debug("InputMasterGrade --> taskId: "+ taskId);
		ResultVO<Object> result = new ResultVO<>();
		Task task = userTaskServiceImpl.getTask(taskId);
		Long recordId = null;
		if(null != task){
			
			ExpertReview toUpdateExpertReview = expertReviewServiceImpl
					.selectByPrimaryKey(expertReview.getRecordId());
			
			if(null !=toUpdateExpertReview){
				// 保存评分记录
				toUpdateExpertReview.setExpertScore(expertReview.getExpertScore());
				expertReviewServiceImpl.update(toUpdateExpertReview);
				recordId = toUpdateExpertReview.getRecordId();
				// 完成任务
				Map<String, Object> variables = new HashMap<String, Object>();
				userTaskServiceImpl.completeTask(taskId, variables);
			}else{
				result.setResult(ResultVO.FAILURE);
			}
			result.setOthers("recordId", recordId.toString());
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("InputMasterGrade --> result: "+ result.getResult());
		return result.toString();
	}
	
	/**
	 * 通用用户任务办理接口
	 * @param commonBiz 通用业务对象
	 * @return
	 */		
	@RequestMapping(value = "/completeTask",produces = "text/html;charset=UTF-8",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String completeTask(CommonBiz commonBiz) {
		logger.debug("completeTask -->:" + commonBiz.toString());
		ResultVO<Object> result = new ResultVO<>();
		//获取当前登录用户信息
		User currentUser = getCurrentUser();
		//判断如果当前用户不为空，并且业务数据对象不为空
		if(null != currentUser && null != commonBiz){
			//当前用户ID
			String userId = currentUser.getId().toString();
			logger.debug("currentUser's userid is : " + userId);
			
			String taskId = commonBiz.getTaskId();
			logger.debug("completeTask --> taskId: "+ taskId);
			Task task = userTaskServiceImpl.getTask(taskId);
			if (null != task ) {
				//进一步判断任务是否已经完成
				HistoricTaskInstance historicTask = userTaskServiceImpl.getHistoryTask(taskId);
				if(!"completed".equals(historicTask.getDeleteReason())){
					
					//如果任务尚未签收，签收任务
					if(StringUtil.isEmpty(task.getAssignee())){
						userTaskServiceImpl.claimTask(taskId, userId);
					}
					// 根据任务获取项目信息
					Long projectId = getDataIdByProcessInstanceId(task
							.getProcessInstanceId());
					Project project = projectService.selectByPrimaryKey(projectId);
					if (null != project) {
						//设置项目信息
						// 设置项目ID
						commonBiz.setProjectId(projectId);
						// 设置项目类别
						commonBiz.setProjectCategory(project.getCategoryId().toString());
						// 设置项目名称
						commonBiz.setProjectName(project.getProjectName());
					}
					// 设置类型
					commonBiz.setServiceType(task.getTaskDefinitionKey());
					// 设置创建者
					commonBiz.setCreatePerson(userId);
					// 设置创建者姓名
					commonBiz.setCreatePersonName(currentUser.getFirstname());
					// 设置流程实例Id
					commonBiz.setProcessInstanceId(task.getProcessInstanceId());
					// 设置业务所归属的决策委员会
					setServiceOwner(commonBiz, task);
					// 保存业务对象
					String dataId = CommonBizServiceImpl.save(commonBiz);
					
					// 设置流程变量
					Map<String, Object> variables = new HashMap<String, Object>();
					// 获取结论
					String stringResult = commonBiz.getResult();
					ApprovalResult approvalResult = JsonUtil.fromJson(stringResult,
							ApprovalResult.class);
					// 如果有结论信息
					if (null != approvalResult) {
						logger.debug("approvalResult: "
								+ approvalResult.getResult());
						// 设置审批结果
						setApproveResult(task, variables, approvalResult);
					}
					//如果为管理员审批，获取其指定的决策委员会人员
					if(ProjectConstants.State.OFFICERAPPROVAL.equals(task.getTaskDefinitionKey())){
						String strAssignList = commonBiz.getData10();
						//设置决策委员会
						setCommittee(variables, strAssignList);
					}
					//如果为部门经理审批，获取其指定的周期监测时间
					if(ProjectConstants.State.DEPARTLEADERAPPROVAL.equals(task.getTaskDefinitionKey())){
						String strCycleTime = commonBiz.getData9();
						//设置周期性监测时间
						setCycleTime(variables, strCycleTime);
					}
					// 完成任务
					userTaskServiceImpl.completeTask(taskId, variables);
					result.setOthers("dataId", dataId);
				}
				result.setResult(ResultVO.SUCCESS);

			} else {
				//任务对象为空
				result.setResult(ResultVO.FAILURE);
			}
		}else{
			// 用户信息为空
			result.setResult(ResultVO.USERNULL);
		}	
		logger.debug("commpleteTask --> result: "+ result.getResult());
		return result.toString();
	}

	// 设置业务归属的决策委员会
	private void setServiceOwner(CommonBiz commonBiz, Task task) {
		boolean isCommittee = false;
		Map<String, Object> variables = runtimeService.getVariables(task.getExecutionId());
		if(variables.containsKey("committee"))
		{
			String committee = (String)variables.get("committee");
			if(!StringUtils.isBlank(committee))
			{
			    commonBiz.setServiceOwner(committee);
			    isCommittee = true;
			}
		}
		if(!isCommittee)
		{
			if(variables.containsKey("committeeGroup"))
			{
				String committeeGroup = (String)variables.get("committeeGroup");
				if(!StringUtils.isBlank(committeeGroup))
				{	
					StringBuilder userIds = new StringBuilder("");
					List<User> users = userRoleServiceImpl.findAllUsersByRoleId(Long.parseLong(committeeGroup));
					if(null != users && !users.isEmpty()){
						userIds.append(users.get(0).getId().toString());
						for(int i = 1; i<users.size();i++){
							userIds.append(",")
							.append(users.get(i).getId().toString());
						}
					}
				    commonBiz.setServiceOwner(userIds.toString());   
				}
			}
		}
	}

	//设置审批结果
	private void setApproveResult(Task task, Map<String, Object> variables,
			ApprovalResult approvalResult) {
		// 如果审批意见为通过
		if (ApprovedConstants.TaskApproveResult.CODE_AGREE == approvalResult
				.getResult()) {
			variables.put(task.getTaskDefinitionKey()
					+ ChangeConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.Agree);
		} 
		else if (ApprovedConstants.TaskApproveResult.CODE_REJECT == approvalResult
				.getResult()) {
			// 审批意见为不通过
			variables.put(task.getTaskDefinitionKey()
					+ ChangeConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.Reject);
		}else {
			// 审批意见为结束流程
			variables.put(task.getTaskDefinitionKey()
					+ ChangeConstants.ActivitiContextKey.result,
					ApprovedConstants.ApproveResult.cancel);
		}
	}
	
	//设置决策委员会人员
	private void setCommittee(Map<String, Object> variables,
			String strAssignList) {
		Gson gson = new Gson();
		//获取指定人员列表
		List<AssignPerson> assignList = gson.fromJson(strAssignList, new TypeToken<List<AssignPerson>>() {}.getType());
		if (null != assignList && !assignList.isEmpty()) {
			AssignPerson assignPerson=assignList.get(0);
			//如果指定为决策委员会组
			if(AssignPerson.Role==assignPerson.getRoleType()){
				variables.put("committeeGroup",assignPerson.getId().toString());
			}else{
				//指定为多个用户
				StringBuilder candidateUser = new StringBuilder("");
				candidateUser.append(assignPerson.getId().toString());
				for(int i = 1;i<assignList.size();i++){
					candidateUser.append(",")
					.append(assignList.get(i).getId());
				}
				variables.put("committees", candidateUser.toString());
				variables.put("committeeGroup", "");
			}
		}
	}
	
	//设置周期监测时间
	private void setCycleTime(Map<String, Object> variables, String strCycleTime) {
		String cycle = "P7D";//默认为7天
		try {
			CycleTime cycleTime = JsonUtil.fromJson(strCycleTime,CycleTime.class);
			if(null!= cycleTime){
				if(CycleTime.DAY.equals(cycleTime.getUnit())){
					cycle = "P"+cycleTime.getCycle()+"D";
				}else if(CycleTime.MONTH.equals(cycleTime.getUnit())){
					cycle = "P"+cycleTime.getCycle()+"M";
				}else if(CycleTime.YEAR.equals(cycleTime.getUnit())){
					cycle = "P"+cycleTime.getCycle()+"Y";
				}else{
					cycle="P7D";//默认为7天
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			cycle = "P7D";//默认为7天
		}
		//测试阶段设置为间隔2分钟
		cycle="PT2M";
		variables.put("cycleTime", cycle);
	}
	
	/**
	 * 决策委员会审批
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/committeeApproval", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String committeeApproval(
			CommitteeApproval committeeApproval, String taskId) {
		logger.debug("CommitteeApproval --> taskId: "+ taskId);
		ResultVO<Object> result = new ResultVO<>();
		
		Task task = userTaskServiceImpl.getTask(taskId);
		if(null != task){
			
			String taskDefinitionKey = task.getTaskDefinitionKey();
			String projectId = getDataIdByProcessInstanceId(task.getProcessInstanceId()).toString();
			// 设置项目ID
			committeeApproval.setProjectId(Long.parseLong(projectId));
			String stage = "";
			// 设置评审阶段
			if (ProjectConstants.State.COMMITTEEAPPROVAL.equals(taskDefinitionKey)) {
				stage = ProjectConstants.Stage.BEGIN;
			} else if (ProjectConstants.State.MIDCOMMITTEEAPPROVAL.equals(taskDefinitionKey)) {
				stage = ProjectConstants.Stage.MIDDLE;
			} else {
				stage = ProjectConstants.Stage.LAST;
			}
			committeeApproval.setStage(stage);
			// 保存决策委员会审批记录
			Long recordId = CommitteeApprovalServiceImpl.save(committeeApproval);
			
			// 设置审批意见流程变量
			Map<String, Object> variables = new HashMap<String, Object>();
			String approvalConclusion = committeeApproval.getConclusion();
			//流程变量key
			String variableKey = taskDefinitionKey+"_result";
			
			if ("同意".equals(approvalConclusion)) {
				variables.put(variableKey, ApprovedConstants.ApproveResult.Agree);
			} else {
				variables.put(variableKey, ApprovedConstants.ApproveResult.Reject);
			}
			userTaskServiceImpl.completeTask(taskId, variables);
			
			result.setOthers("recordId", recordId);
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("CommitteeApproval --> result: "+ result.getResult());
		return result.toString();
	}

	/**
	 * 部门经理审批
	 * 
	 * @param project
	 */
	@RequestMapping(value = "/departleaderApproval", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String departleaderApproval(
			DepartleaderApproval departleaderApproval, String taskId) {
		logger.debug("DepartleaderApproval --> taskId: "+ taskId);
		ResultVO<Object> result = new ResultVO<>();
		Task task = userTaskServiceImpl.getTask(taskId);
		if(null != task){
			String taskDefinitionKey = task.getTaskDefinitionKey();
			String projectId = getDataIdByProcessInstanceId(task.getProcessInstanceId()).toString();
			// 设置项目ID
			departleaderApproval.setProjectId(Long.parseLong(projectId));
			String stage = "";
			// 设置评审阶段
			if (ProjectConstants.State.DEPARTLEADERAPPROVAL.equals(taskDefinitionKey)) {
				stage = ProjectConstants.Stage.BEGIN;//初期
			} else if (ProjectConstants.State.DEPARTLEADERMIDAPPROVAL.equals(taskDefinitionKey)) {
				stage = ProjectConstants.Stage.MIDDLE;//中期
			} else {
				stage = ProjectConstants.Stage.LAST;//终期
			}
			departleaderApproval.setStage(stage);
			// 保存领导审批记录
			Long recordId = departleaderApprovalServiceImpl
					.save(departleaderApproval);
			
			// 设置审批意见流程变量
			Map<String, Object> variables = new HashMap<String, Object>();
			String approvalConclusion = departleaderApproval.getConclusion();
			//流程变量key
			String variableKey = taskDefinitionKey+"_result";
			if ("同意".equals(approvalConclusion)) {
				variables.put(variableKey, ApprovedConstants.ApproveResult.Agree);
			} else {
				variables.put(variableKey, ApprovedConstants.ApproveResult.Reject);
			}
			//完成任务
			userTaskServiceImpl.completeTask(taskId, variables);
			
			result.setOthers("recordId", recordId);
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("DepartleaderApproval --> result: "+ result.getResult());
		return result.toString();
	}
	
	/**
	 * 尽职调查
	 * @param project
	 */
	/*
	@RequestMapping(value="/jzdc", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String JZDC(CommonBiz commonBiz,String taskId) 
    {
		logger.debug("JZDC --> taskId: "+ taskId);
		ResultVO<Object> result = new ResultVO<>();
		Task task = userTaskServiceImpl.getTask(taskId);
		if(null != task && task.getTaskDefinitionKey().equals(ProjectConstants.State.JZDC.trim())){
			//获取当前用户信息
			User user = getCurrentUser();
			commonBiz.setServiceType("jzdc");
			//获取项目信息
			Project project = projectService.selectByPrimaryKey(commonBiz.getProjectId());
			if(null != project){
				commonBiz.setProjectCategory(project.getCategoryId().toString());
			}
			//设置创建人
			commonBiz.setCreatePerson(user.getId().toString());
			//设置任务ID
			commonBiz.setTaskId(taskId);
			//设置流程实例ID
			commonBiz.setProcessInstanceId(task.getProcessInstanceId());
			//保存尽职调查对象
			String dataId = CommonBizServiceImpl.save(commonBiz);
			//完成任务
			userTaskServiceImpl.completeTask(taskId, null);
			logger.debug("JZDC --> result: "+ result.getResult());
			result.setOthers("dataId", dataId);
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		return result.toString();
    }*/

	/**
	 * 项目经理自评
	 * @param project
	 */
	@RequestMapping(value="/selfAppraise", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String selfAppraise(String selfAppraise,String taskId) 
    {
		logger.debug("SelfAppraise --> taskId: "+ taskId);
		ResultVO<Object> result = new ResultVO<>();
		Task task = userTaskServiceImpl.getTask(taskId);
		if(null != task && task.getTaskDefinitionKey().equals(ProjectConstants.State.PMSELFCONCLUSION)){
			
			String processInstanceId = task.getProcessInstanceId();
			Long projectId =getDataIdByProcessInstanceId(processInstanceId);
			Project project = projectService.selectByPrimaryKey(projectId);
			if (null != project) {
				// 修改项目信息
				project.setSelfAppraise(selfAppraise);
				projectService.update(project);
				
				Map<String, Object> variables = new HashMap<String, Object>();
				// 完成任务
				userTaskServiceImpl.completeTask(taskId, variables);
				result.setOthers("projectId", project.getProjectId());
			}else{
				result.setResult(ResultVO.FAILURE);
			}
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("SelfAppraise --> result: "+ result.getResult());
		return result.toString();
	}
	
	/**
	 * 部门经理评价
	 * @param project
	 */
	@RequestMapping(value="/departLeaderAppraise", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String departLeaderAppraise(String departLeaderAppraise,String taskId) 
	{
		logger.debug("DepartLeaderAppraise --> taskId: "+ taskId);
		ResultVO<Object> result = new ResultVO<>();
		Task task = userTaskServiceImpl.getTask(taskId);
		if(null != task && task.getTaskDefinitionKey().equals(ProjectConstants.State.DEPARTLEADERCONCLUSION)){
			
			String processInstanceId = task.getProcessInstanceId();
			Long projectId = getDataIdByProcessInstanceId(processInstanceId);
			Project project = projectService.selectByPrimaryKey(projectId);
			
			if (null != project) {
				// 修改项目信息
				project.setDepartLeaderAppraise(departLeaderAppraise);
				projectService.update(project);
				// 完成任务
				Map<String, Object> variables = new HashMap<String, Object>();
				userTaskServiceImpl.completeTask(taskId, variables);
				result.setOthers("projectId", project.getProjectId());
			}else{
				result.setResult(ResultVO.FAILURE);
			}
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("DepartLeaderAppraise --> result: "+ result.getResult());
		return result.toString();
	}
	
	/**
	 * 根据流程实例ID获取业务数据ID
	 * 
	 * @param processInstanceId 流程实例ID
	 * @return
	 */
	public Long getDataIdByProcessInstanceId(String processInstanceId) {
		
		String businessKey = getBusinessKeyByByProcessInstanceId(processInstanceId);
		Long dataId  = processServiceImpl.getDataIdByBusinessKey(businessKey);
		return dataId;
	}
	
	/**
	 * 根据流程实例ID获取业务对象类名
	 * 
	 * @param processInstanceId 流程实例ID
	 * @return 
	 */
	public String getClassNameByProcessInstanceId(String processInstanceId) {
		String businessKey = getBusinessKeyByByProcessInstanceId(processInstanceId);
		String className = processServiceImpl.getCLassNameByBusinessKey(businessKey);
		return className;
	}
	
	public String getBusinessKeyByByProcessInstanceId(String processInstanceId){
		String businessKey= null;
		// 获取正在执行的流程实例
		ProcessInstance processInstance = processServiceImpl
				.getProcessInstance(processInstanceId);
		if (null != processInstance) {
			 //获取业务key
			 businessKey= processInstance.getBusinessKey();
		} else {// 如果项目流程已经结束
			// 获取历史流程实例
			HistoricProcessInstance historicProcessInstance = processServiceImpl
					.findHistoryProcessInstance(processInstanceId);
			if (null != historicProcessInstance) {
				businessKey = historicProcessInstance.getBusinessKey();
			}
		}
		return businessKey;
	}
	
	
	
	

	/**
	 * 构建用户任务对象
	 * 
	 * @param taskInfo
	 *            Activiti中任务对象
	 * @return
	 */
	public UserTask buildUserTaskByTaskInfo(TaskInfo taskInfo) {

		UserTask userTask = new UserTask();
		if (null != taskInfo) {
			//获取流程实例Id
			String processInstanceId = taskInfo.getProcessInstanceId();
			//获取className
			String className = getClassNameByProcessInstanceId(processInstanceId);
			// dataID
			Long dataId = getDataIdByProcessInstanceId(processInstanceId);

			String taskDefinitionKey = taskInfo.getTaskDefinitionKey();
			//根据taskDefinitionKey获取自定义的taskName
			String taskName = taskDefKeyNameServiceImpl.selectStateNameByPrimaryKey(taskDefinitionKey);
			if (StringUtils.isBlank(taskName)) {
				taskName = taskInfo.getName();
			} 
			//构建用户任务对象
			userTask.setId(taskInfo.getId());
			userTask.setName(taskName);
			userTask.setAssignee(taskInfo.getAssignee());
			userTask.setCreateTime(taskInfo.getCreateTime());
			userTask.setClassName(className);
			userTask.setDataId(dataId);
			
			String stateInfo = processServiceImpl.getStateInfoByTask(taskInfo);
			userTask.setStateInfo(stateInfo);

			if(Project.class.getSimpleName().equals(className)){
				Project project = projectService.selectByPrimaryKey(dataId);
				if (null != project) {
					userTask.setProjectId(project.getProjectId());
					userTask.setProjectName(project.getProjectName());
				}
				
			}
			else if(ThreeleaderApplication.class.getSimpleName().equals(className))
			{
				ThreeleaderApplication threeleaderApplication=ThreeleaderApplicationServiceImpl.getById(dataId);
				userTask.setProjectId(threeleaderApplication.getApplicationId());
				DepartleaderPublish departleaderPublish = departleaderPublishServiceImpl.getById(threeleaderApplication.getDepartleaderPublishId());
				userTask.setProjectName(departleaderPublish.getTitle());
			}
		}
		return userTask;
	}
	
	
	
	
	
	

	/**
	 * 高亮跟踪流程实例
	 */
	@RequestMapping(value="/traceProcessImage",produces = MediaType.IMAGE_PNG_VALUE)
	@ResponseBody
	public byte[] traceProcessImage(String processInstanceId, HttpServletResponse response) {
		try {
			return projectService.traceProcessImage(processInstanceId);
		} catch (Exception e) {
			System.err.println("获取图片流错误！！");
			e.printStackTrace();
			return null;
		}
	}
}
