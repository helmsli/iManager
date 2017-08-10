package com.xinwei.process.controller;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import tk.mybatis.mapper.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.xinwei.excel.ExcelType;
import com.xinwei.process.constant.ApprovedConstants;
import com.xinwei.process.constant.ChangeConstants;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.dao.CommonBizMapper;
import com.xinwei.process.dao.DataPermissionMapper;
import com.xinwei.process.entity.Application;
import com.xinwei.process.entity.ApprovalResult;
import com.xinwei.process.entity.AssignPerson;
import com.xinwei.process.entity.CommitteeApproval;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.Company;
import com.xinwei.process.entity.CycleTime;
import com.xinwei.process.entity.DataInfo;
import com.xinwei.process.entity.DataPermission;
import com.xinwei.process.entity.DepartleaderApproval;
import com.xinwei.process.entity.DepartleaderPublish;
import com.xinwei.process.entity.ExpertReview;
import com.xinwei.process.entity.MonthlyReport;
import com.xinwei.process.entity.Project;
import com.xinwei.process.entity.ProjectAnnex;
import com.xinwei.process.entity.ThreeleaderApplication;
import com.xinwei.process.entity.UserTask;
import com.xinwei.process.service.CommitteeApprovalService;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.CompanyService;
import com.xinwei.process.service.DepartleaderApprovalService;
import com.xinwei.process.service.DepartleaderPublishService;
import com.xinwei.process.service.ExpertReviewService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectAnnexService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.TaskDefKeyNameService;
import com.xinwei.process.service.ThreeleaderApplicationService;
import com.xinwei.process.service.UserTaskService;
import com.xinwei.report.month.MonthlyReportReader;
import com.xinwei.report.month.process.ProgressReport;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.security.service.UserService;
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
	
	@Resource
	private DataPermissionMapper DataPermissionDao;
	
	@Autowired
	private UserService userService;
	@Autowired
	private CompanyService companyServiceImpl;
	
	@Value("${roleId_departLeader}")
	private Long roleId_departLeader;// 项目经理角色ID
	
	@Value("${roleId_committee}")
	private Long roleId_committee;// 项目经理角色ID
	
	
	@Value("${roleId_projectManager}")
	private Long roleId_projectManager;// 项目经理角色ID
	

	@Value("${uploadPath}")
	private String uploadPath; 
	private Gson gson = new GsonBuilder()
			.serializeNulls()//序列化null
			.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
			.create();
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
   
	protected long checkMonthlyReport(List<ProgressReport> progressReports)
	{
		
		ProgressReport progressReport = progressReports.get(0);
		//名称
		if(StringUtils.isEmpty(progressReport.getName()))
		{
			return ProjectConstants.MONTHLY_ERROR.ProjectName_ISNULL;
		}
		//月份
		if(StringUtils.isEmpty(progressReport.getMonth()))
		{
			return ProjectConstants.MONTHLY_ERROR.YueFen_ISNULL;
		}
		else
		{
			int month=0;
			try {
				month = Integer.parseInt(progressReport.getMonth());
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return ProjectConstants.MONTHLY_ERROR.YueFen_ISNULL;
			}
			if(month<1 || month>12)
			{
				return ProjectConstants.MONTHLY_ERROR.YueFen_ISNULL;
			}
		}
		//
		
		if(StringUtils.isEmpty(progressReport.getCharge()))
		{
			return ProjectConstants.MONTHLY_ERROR.ProjectCompany_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getMeetingNumber()))
		{
			return ProjectConstants.MONTHLY_ERROR.Trainning_times_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getOtherManagement()))
		{
			return ProjectConstants.MONTHLY_ERROR.NotTrainning_times_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getMajorIssues()))
		{
			return ProjectConstants.MONTHLY_ERROR.ChuDianZongjie_ISNULL;
		}
		
		if(StringUtils.isEmpty(progressReport.getReviewTimes()))
		{
			return ProjectConstants.MONTHLY_ERROR.ZhiJieShouYIRenshu_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getDocOutputNumber()))
		{
			return ProjectConstants.MONTHLY_ERROR.ZhiJieShouYIRenCi_ISNULL;
		}
		
		if(StringUtils.isEmpty(progressReport.getCodeReviewTimes()))
		{
			return ProjectConstants.MONTHLY_ERROR.JianJieShouYIRenshu_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getVersionOutputNumber()))
		{
			return ProjectConstants.MONTHLY_ERROR.JianJieShouYIRenCi_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getReviewTimes()))
		{
			return ProjectConstants.MONTHLY_ERROR.ZhiJieShouYIRenshu_ISNULL;
		}
		
		
		if(StringUtils.isEmpty(progressReport.getChange()))
		{
			return ProjectConstants.MONTHLY_ERROR.XiangMuBianGeng_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getVersionUpgrade()))
		{
			return ProjectConstants.MONTHLY_ERROR.MeiTiBaoDiaoCiShu_ISNULL;
		}
		
		if(!StringUtils.isEmpty(progressReport.getVersionUpgrade())&& StringUtils.isEmpty(progressReport.getUpdateProblemTracking()))
		{
			return ProjectConstants.MONTHLY_ERROR.MeiTiBaoDaoLianjie_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getImprovementPlan()))
		{
			return ProjectConstants.MONTHLY_ERROR.GaiJinJiHua_ISNULL;
		}
		if(StringUtils.isEmpty(progressReport.getNextMonthPlan()))
		{
			return ProjectConstants.MONTHLY_ERROR.XiaYueZhongDaJihua_ISNULL;
		}
		
		
		
		if(progressReport.getProgressList().size()==0)
		{
			return ProjectConstants.MONTHLY_ERROR.MainContent_ISNULL;
		}
		
		
		
		
		return Integer.parseInt(ResultVO.SUCCESS);
	}
	
	@RequestMapping(value = "/preViewMonthlyReport",produces = "text/html;charset=UTF-8",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String preViewMonthlyReport(HttpServletRequest request, HttpServletResponse response,CommonBiz commonBiz) {
		logger.debug("preViewMonthlyReport -->:" + commonBiz.toString());
		ResultVO<Object> result = new ResultVO<>();
		//获取当前登录用户信息
		User currentUser = getCurrentUser();
		//判断如果当前用户不为空，并且业务数据对象不为空
		try {
			if(null != currentUser && null != commonBiz){
				//读取excel信息进行校验
				MonthlyReportReader monthlyReportReader = new MonthlyReportReader();
				List<ProjectAnnex> monthlyReports = JsonUtil.fromJson(commonBiz.getData1(),  new TypeToken<List<ProjectAnnex>>() {  
			    }.getType());
				
				List<ProjectAnnex> monthlyReportAttatchments = JsonUtil.fromJson(commonBiz.getData2(),new TypeToken<List<ProjectAnnex>>() {  
			    }.getType());
				
				String path = request.getSession().getServletContext().getRealPath(uploadPath);
				//new File(path,monthlyReports.get(0).getAnnexName());
				String monthlyReportfilePath =path + "/" + monthlyReports.get(0).getAnnexName();
				logger.debug("monthly path:"+ monthlyReportfilePath);
				List<ProgressReport> monthlyReportContent=null;
				try {
					monthlyReportContent= monthlyReportReader.getMonthlyProcessReport( monthlyReportfilePath,ExcelType.XLS);
				    logger.debug(monthlyReportContent.toString());
				    long checkResult = checkMonthlyReport(monthlyReportContent);
				    if(checkResult!=Integer.parseInt(ResultVO.SUCCESS))
				    {
				    	result.setResult(String.valueOf(checkResult));
				    	return result.toString();
				    }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setResult(String.valueOf(ProjectConstants.MONTHLY_ERROR.Format_ERROR));
					return result.toString();
					
					
				}
				//当前用户ID
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : " + userId);
				//根据项目名称获取项目的信息
				List<Project> projectS = this.projectService.selectByProjectName(commonBiz.getProjectName());
				Project project = null;
				if (projectS.size()>0) {
					 project = projectS.get(0);
					logger.debug(project.toString());	
					//设置项目信息
							// 设置项目ID
					commonBiz.setProjectId(project.getProjectId());
							// 设置项目类别
					commonBiz.setProjectCategory(project.getCategoryId().toString());
							// 设置项目名称
					commonBiz.setProjectName(project.getProjectName());
				}
				else
				{
					commonBiz.setProjectId(00000000L);
				
					
					//项目名称
					commonBiz.setProjectName(monthlyReportContent.get(0).getName());
					//项目不存在
					result.setResult(ProjectConstants.PROJECT_ERROR.Project_NotExist.toString());
					logger.debug(result.toString());
					
					
					//return result.toString();
				}
				commonBiz.setData3(gson.toJson(monthlyReportContent));
					
				
				
				 logger.debug(gson.toJson(monthlyReportContent));
				 logger.debug(monthlyReportContent.toString());
						// 设置类型
					
						// 设置创建者
				commonBiz.setCreatePerson(userId);
						// 设置创建者姓名
				commonBiz.setCreatePersonName(currentUser.getFirstname());
						// 设置流程实例Id
				commonBiz.setProcessInstanceId("0");
			    // 设置业务归属
				commonBiz.setServiceOwner(currentUser.getId().toString());
				
				// 保存业务对象
						result.setResult(ResultVO.SUCCESS);
			            result.setOthers("commonbiz", commonBiz);
				} else {
					//任务对象为空
					result.setResult(ResultVO.FAILURE);
				}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setResult(ResultVO.FAILURE);
		}
			
		logger.debug("preViewMonthlyReport --> result: "+ result.getResult());
		return result.toString();
	}
	
	/**
	 * 根据发布ID或者第三方的权限；
	 * @param publisherId
	 * @return
	 */
	protected List<DataPermission> getCommitteeByPublisheid(Long categoryId,Long publisherId)
	{
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("categoryId", categoryId);
		queryMap.put("dataType", DataInfo.DATATYPE_PUBLISH);
		queryMap.put("dataId", publisherId);
		queryMap.put("extData1", DataPermission.PRIVILEGE_ThreeEval);
		queryMap.put("startRow",0);
		queryMap.put("pageSize", 1000);
		return DataPermissionDao.selectByConditions(queryMap);
	}
	
	
	/**
	 * 通用用户任务办理接口
	 * @param commonBiz 通用业务对象
	 * @return
	 */		
	@RequestMapping(value = "/submitMonthlyReport",produces = "text/html;charset=UTF-8",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String submitMonthlyReport(HttpServletRequest request, HttpServletResponse response,CommonBiz commonBiz) {
		logger.debug("submitMonthlyReport -->:" + commonBiz.toString());
		ResultVO<Object> result = new ResultVO<>();
		//获取当前登录用户信息
		try {
			User currentUser = getCurrentUser();
			//判断如果当前用户不为空，并且业务数据对象不为空
			if(null != currentUser && null != commonBiz){
				//读取excel信息进行校验
				MonthlyReportReader monthlyReportReader = new MonthlyReportReader();
				List<ProjectAnnex> monthlyReports = JsonUtil.fromJson(commonBiz.getData1(),  new TypeToken<List<ProjectAnnex>>() {  
			    }.getType());
				
				List<ProjectAnnex> monthlyReportAttatchments = JsonUtil.fromJson(commonBiz.getData2(),new TypeToken<List<ProjectAnnex>>() {  
			    }.getType());
				
				String path = request.getSession().getServletContext().getRealPath(uploadPath);
				//new File(path,monthlyReports.get(0).getAnnexName());
				String monthlyReportfilePath =path + "/" + monthlyReports.get(0).getAnnexName();
				logger.debug("monthly path:"+ monthlyReportfilePath);
				List<ProgressReport> monthlyReportContent=null;
				try {
					monthlyReportContent= monthlyReportReader.getMonthlyProcessReport( monthlyReportfilePath,ExcelType.XLS);
				    logger.debug(monthlyReportContent.toString());
				    long checkResult = checkMonthlyReport(monthlyReportContent);
				    if(checkResult!=Integer.parseInt(ResultVO.SUCCESS))
				    {
				    	result.setResult(String.valueOf(checkResult));
				    	logger.debug(result.toString());
				    	return result.toString();
				    }
		             //设置机构名称
				    commonBiz.setExtActivitiInfo(monthlyReportContent.get(0).getCharge().trim());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setResult(String.valueOf(ProjectConstants.MONTHLY_ERROR.Format_ERROR));
					return result.toString();
				}
				//当前用户ID
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's userid is : " + userId);
				//根据项目名称获取项目的信息
				CommonBiz project = null;
				if(!StringUtils.isEmpty(monthlyReportContent.get(0).getName().trim()))
				{
					try {
						Map<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("projectName", monthlyReportContent.get(0).getName().trim());
						queryMap.put("serviceType","application");
						
						CommonBizMapper commonBizMapper = CommonBizServiceImpl.getCommonBizMapper();
						queryMap.put("startRow", 0);
						queryMap.put("pageSize", 2);
						List<CommonBiz> lists=commonBizMapper.selectByCategoryTypePersonProjectName(queryMap);
						
						project = lists.get(0);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if (project!=null) {
					 
					logger.debug(project.toString());	
					//设置项目信息
							// 设置项目ID
					commonBiz.setProjectId(project.getProjectId());
					
							// 设置项目名称
					commonBiz.setProjectName(project.getProjectName().trim());
					
					commonBiz.setTaskId(project.getTaskId());
					commonBiz.setData4(project.getData1());
					commonBiz.setData5(project.getServiceOwner());
					
				}
				else
				{
					commonBiz.setProjectId(00000000L);
				
					
					//项目名称
					commonBiz.setProjectName(monthlyReportContent.get(0).getName().trim());
					//项目不存在
					result.setResult(ProjectConstants.PROJECT_ERROR.Project_NotExist.toString());
					logger.debug(result.toString());
					
					
					//return result.toString();
				}
				
				commonBiz.setData3(gson.toJson(monthlyReportContent));
				commonBiz.setExtStatus(monthlyReportContent.get(0).getMonth().trim());	
				
				
				 logger.debug(gson.toJson(monthlyReportContent));
				 logger.debug(monthlyReportContent.toString());
						// 设置类型
					
						// 设置创建者
				commonBiz.setCreatePerson(userId);
						// 设置创建者姓名
				commonBiz.setCreatePersonName(currentUser.getFirstname());
						// 设置流程实例Id
				commonBiz.setProcessInstanceId("0");
			    // 设置业务归属
				commonBiz.setServiceOwner(currentUser.getId().toString());
				
				//先更新
				CommonBiz oldCommonBiz = new CommonBiz();
				oldCommonBiz.setProjectName(commonBiz.getProjectName());
				oldCommonBiz.setProjectCategory(commonBiz.getProjectCategory());
				//设置机构名
				oldCommonBiz.setExtActivitiInfo(commonBiz.getExtActivitiInfo());
				//设置月份
				oldCommonBiz.setExtStatus(commonBiz.getExtStatus());
				List<CommonBiz> oldCommonBizs =  CommonBizServiceImpl.selectMonthlyReportWithResult(oldCommonBiz);
				// 保存业务对象
				String dataId = "";
				logger.debug("*********" + oldCommonBizs.size());
				if(oldCommonBizs==null || oldCommonBizs.size()==0)
				{
				 dataId = CommonBizServiceImpl.save(commonBiz);
				}
				else
				{
					dataId = oldCommonBizs.get(0).getDataId();
					commonBiz.setDataId(dataId);
					commonBiz.setCreateTime(new Date());
					CommonBizServiceImpl.update(commonBiz);
				}
				//设置数据权限
				//设置自己的权限
				StringBuilder sortKey = new StringBuilder();
				sortKey.append(monthlyReportContent.get(0).getName());
				sortKey.append("");
				sortKey.append(monthlyReportContent.get(0).getCharge());
				sortKey.append("");
				if(monthlyReportContent.get(0).getMonth().trim().length()<2)
				{
					sortKey.append("0");
					
				}
				sortKey.append(monthlyReportContent.get(0).getMonth().trim());
				
				DataPermission dataPermission = new DataPermission();
				dataPermission.setDataId(dataId);
				dataPermission.setDataType(DataPermission.DATATYPE_MonthlyReport);
				dataPermission.setCategoryId(Long.parseLong(commonBiz.getProjectCategory()));
				dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_USER);
				dataPermission.setPermissionId(currentUser.getId().toString());
				//排序
				dataPermission.setExtData2(sortKey.toString());
				
				this.DataPermissionDao.insert(dataPermission);
				
				
				
				//设置第三方权限
				if (false) 
				{
					
				}
				//设置所有第三方的权限
				else
				{
					dataPermission.setDataId(dataId);
					dataPermission.setDataType(DataPermission.DATATYPE_MonthlyReport);
					dataPermission.setCategoryId(Long.parseLong(commonBiz.getProjectCategory()));
					dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_ROLE);
					dataPermission.setPermissionId(this.roleId_committee.toString());
					dataPermission.setExtData2(sortKey.toString());					
					DataPermissionDao.insert(dataPermission);
						
				}
			    //设置部门经理权限
				dataPermission.setDataId(dataId);
				dataPermission.setDataType(DataPermission.DATATYPE_MonthlyReport);
				dataPermission.setCategoryId(Long.parseLong(commonBiz.getProjectCategory()));
				dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_ROLE);
				dataPermission.setPermissionId(this.roleId_departLeader.toString());
				dataPermission.setExtData2(sortKey.toString());
				
				DataPermissionDao.insert(dataPermission);
				result.setResult(ResultVO.SUCCESS);

				} else {
					//任务对象为空
					result.setResult(ResultVO.FAILURE);
				}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			//任务对象为空
			result.setResult(ResultVO.FAILURE);
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//任务对象为空
			result.setResult(ResultVO.FAILURE);
		}
			
		logger.debug("submitMonthlyReport --> result: "+ result.getResult());
		return result.toString();
	}
	
	
	/**
	 * 通用用户任务办理接口
	 * @param commonBiz 通用业务对象
	 * @return
	 */		
	@RequestMapping(value = "/submitApplication",produces = "text/html;charset=UTF-8",method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String submitApplication(HttpServletRequest request, HttpServletResponse response,CommonBiz commonBiz) {
		logger.debug("submitApplication -->:" + commonBiz.toString());
		ResultVO<Object> result = new ResultVO<>();
		//获取当前登录用户信息
		try {
				//读取excel信息进行校验
				//当前用户ID
				//根据项目名称获取项目的信息
				
				//先更新
				// 保存业务对象
				String dataId = "";
				boolean isFind = false;
				if(StringUtils.isNotEmpty(commonBiz.getDataId()))
				{
					CommonBiz oldmmonBiz =CommonBizServiceImpl.selectByPrimaryKey(commonBiz.getDataId());
				    
					if(oldmmonBiz!=null)
				    {
						
				    	try {
				    		commonBiz.setCreatePerson(oldmmonBiz.getCreatePerson());
							commonBiz.setResult(oldmmonBiz.getResult());
							commonBiz.setData8(oldmmonBiz.getData8());
							commonBiz.setCreateTime(Calendar.getInstance().getTime());
							commonBiz.setStatus(String.valueOf(ApprovedConstants.ApplicationResult.application));
							isFind = true;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				}
				
				if(isFind)
				{
					CommonBizServiceImpl.update(commonBiz);
				}
				else
				{
					//自动创建用户
					User user = userService.get(commonBiz.getCreatePerson());
					if(user==null)
					{
						/*
						 * var alldata={"pName":$scope.application.projectName,
    			     "personName":$scope.application.contactName,
    			     "person":$scope.application.contactTel,
    			     "address":$scope.application.registerAddress,
    			     "amount":$scope.application.amount,
    			    "cname":$scope.application.companyName,
    			    "cType":$scope.application.comType
    			    };
						 */
						Application application=null;
						try {
							application = gson.fromJson(commonBiz.getData5(), Application.class);
							logger.debug(application.toString());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							application=new Application();
							application.setEmail("123@chunze.com");
						}
						 try {
							 Company company = companyServiceImpl.selectCompaniesByName(commonBiz.getData3());
							 if(company==null)
							 {
								 company = new Company();
								 company.setCreatePerson(commonBiz.getCreatePerson());
								 company.setBusinessAddress(application.getAddressCode());
								 company.setCompanyProperty(commonBiz.getData4());
								 company.setCompanyName(commonBiz.getData3());
								 company.setEmail(application.getEmail());
								 company.setLinkMan(commonBiz.getCreatePersonName());
								 company.setLinkTel(commonBiz.getCreatePerson());
								 company.setRegisterAddress(application.getAddress());
							
								 company.setCreateTime(new Date());
								 this.companyServiceImpl.save(company);
							 }
							user=new User();
							 user.setCreateTime(new Date());
							 user.setUsername(commonBiz.getCreatePerson());
							user.setFirstname(commonBiz.getCreatePersonName());
							user.setCompany_name(commonBiz.getData3());
							user.setPhone(commonBiz.getCreatePerson());
							user.setStatus(0);
							user.setApprovalStatus(1);
							user.setIsDisabled(0);						
							user.setAddress(application.getAddress());
							user.setEmail(application.getEmail());
							
							
							
							user.setPassword(application.getEmail());
							List<Long> roleId = new ArrayList<Long>();
							roleId.add(roleId_projectManager);
							user.setRoleIds(roleId);
							this.userService.save(user);
							result.setOthers("email", application.getEmail());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							 result.setOthers("email", "");
						}
						 
					}
					else
					{
						 result.setOthers("email", "");
					}
					//end 自动创建用户
				    dataId = CommonBizServiceImpl.save(commonBiz);
				}
				result.setResult(ResultVO.SUCCESS);		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//任务对象为空
			result.setResult(ResultVO.FAILURE);
		}
			
		logger.debug("submitMonthlyReport --> result: "+ result.getResult());
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
					
					logger.debug("********************projectId:"+projectId );
					
					Project project = projectService.selectByPrimaryKey(projectId);
					logger.debug(project.toString());
					
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
		Gson gson = new GsonBuilder()
				.serializeNulls()//序列化null
				.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
				.create();
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
