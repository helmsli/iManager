package com.xinwei.process.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.doc.WordUtil;
import com.google.gson.Gson;
import com.xinwei.doc.DocReader;
import com.xinwei.doc.vo.Contact;
import com.xinwei.doc.vo.ProjectInfo;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.DataInfo;
import com.xinwei.process.entity.DataPermission;
import com.xinwei.process.entity.DepartleaderPublish;
import com.xinwei.process.entity.Project;
import com.xinwei.process.entity.ProjectAnnex;
import com.xinwei.process.entity.ProjectDetailInfo;
import com.xinwei.process.entity.ProjectExtInfo;
import com.xinwei.process.entity.ProjectMaterial;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.DataPermissionService;
import com.xinwei.process.service.DepartleaderPublishService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectAnnexService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.UserTaskService;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.UserService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.page.Page;

/**
 * 项目控制器
 *
 */
@Controller
@RequestMapping("/project")
public class ProjectController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ProjectController.class);
	@Resource
	private ProjectService projectService;
	@Resource
	private ProcessService processServiceImpl;
	@Resource
	private UserTaskService userTaskServiceImpl;
	@Resource
	private UserService userService;
	@Resource
	private CommonBizService commonBizServiceImpl;// 通用业务数据服务
	@Resource
	private ProjectAnnexService projectAnnexServiceImpl;// 项目附件服务
	@Resource
	private DepartleaderPublishService departleaderPublishServiceImpl;//部门经理发布服务
	@Resource
	private DataPermissionService dataPermissionServiceImpl;//数据权限服务
	@Resource
	private XwSysSeqService xwSysSeqService;
	
	@Value("${uploadPath}")
	private String uploadPath;
	
	//从配置文件中读取流程中预设的人员
	@Value("${roleId_CooMartsOfficer}")
	private String roleId_CooMartsOfficer;// CoolMarts管理员组ID
	@Value("${roleId_CamTalkOfficer}")
	private String roleId_CamTalkOfficer;// CamTalk管理员组ID
	@Value("${roleId_LotteryOfficer}")
	private String roleId_LotteryOfficer;// Lottery管理员组ID
	@Value("${roleId_committee}")
	private String roleId_committee;// 决策委员会组ID
	@Value("${roleId_departLeader}")
	private String roleId_departLeader;// 部门经理组ID
	@Value("${downTempBase}")
	private String downTempBase;// 下载模板路径

	@Value("${departLeader}")
	private String g_departLeader;// 下载模板路径

	
	private final String PROJECT_SEQ = "project_seq";// 项目编号
	
	/**
	 * 项目经理申请、创建项目(包括修改后保存)
	 * @param project
	 * @return
	 */
	@RequestMapping(value = "/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String create(HttpServletRequest request, HttpServletResponse response,Project project) {
		logger.debug("CreateProject start...1"+ project.toString());
		ResultVO<Object> result = new ResultVO<>();
		// 获取当前登录用户信息
		try {
			User currentUser = getCurrentUser();
			//用來保存上傳文件路徑
			String path = request.getSession().getServletContext().getRealPath(uploadPath);   
			project.setProjectMilestone(path);
			
			// 判断如果用户不为空
			if (null != currentUser) {
				// 如果为新申请项目
				if (null == project.getProjectId()) {
					// 创建项目申请
					
					Long projectId = createNewProject(project, currentUser);
					// 给客户端响应
					if(projectId<0)
					{
						result.setResult(projectId.toString());
					}
					result.setOthers("projectId", projectId);
					logger.debug("CreateProject-->projectId: " + projectId);
					
				} else {
					// 修改后保存
					logger.debug("UpdateProject --> projectId: "
							+ project.getProjectId());
					// 进行修改
					modifyProject(project, result, currentUser);
				}
			} else {
				// 给客户端响应
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Throwable e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug(result.toString());
		return result.toString();
	}

	  public  Date getLastMonthOfDate(Date date) {  
	        Calendar para = Calendar.getInstance();  
	        para.setTime(date);  
	        para.set(Calendar.DATE, para.getActualMaximum(Calendar.DAY_OF_MONTH));  
	        para.set(Calendar.HOUR_OF_DAY, 23);  
	        para.set(Calendar.MINUTE, 59);  
	        para.set(Calendar.SECOND, 59);  
	        return para.getTime();  
	    }  
	  public  int getDay(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.DATE);
	    }

	  public  int getDaysOfMonth(int year, int month) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.set(year, month - 1, 1);
	        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
	    }
	    /**
	     * 返回日期的月份，1-12,即yyyy-MM-dd中的MM
	     *
	     * @param date
	     * @return
	     */
	    public  int getMonth(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.MONTH) + 1;
	    }

	  public  int getYear(Date date) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(date);
	        return calendar.get(Calendar.YEAR);
	    }
	  public  int calDiffMonth(Date start,Date end){
	        int result=0;
	        try {
	           
	            int startYear=getYear(start);
	            int startMonth=getMonth(start);
	            int startDay=getDay(start);
	            int endYear=getYear(end);
	            int endMonth=getMonth(end);
	            int endDay=getDay(end);
	            if (startDay>endDay){ //1月17  大于 2月28
	                if (endDay==getDaysOfMonth(getYear(new Date()),2)){   //也满足一月
	                    result=(endYear-startYear)*12+endMonth-startMonth;
	                }else{
	                    result=(endYear-startYear)*12+endMonth-startMonth-1;
	                }
	            }else{
	                result=(endYear-startYear)*12+endMonth-startMonth;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return result;
	    }
	
	/*
	 *  创建项目申请
	 */
	private Long createNewProject(Project project, User currentUser) {
		
		
		String strInfo = project.getProjectExtInfo();
		logger.debug("*********************************1");
		DocReader reader = new DocReader();
		logger.debug("*********************************2");
		ProjectExtInfo projectExtInfo = JsonUtil.fromJson(project.getProjectExtInfo(), ProjectExtInfo.class);
		List<ProjectAnnex> projectAnnexs = projectExtInfo.getNewApplyAttachment();
		if(projectAnnexs.size()==0)
		{
			logger.debug("####################");
		   return ProjectConstants.PROJECT_ERROR.APPLICATION_ISNULL;	
		}
		logger.debug("*********************************3");
		String srcPath = project.getProjectMilestone() + "/" +projectAnnexs.get(0).getAnnexName();
		logger.debug("*************");
		logger.debug(srcPath);
		try {
			ProjectInfo projectInfo = reader.readDoc(srcPath);
			//ProjectInfo projectInfo= doc.testReadByDoc(srcPath,maps);
			project.setProjectTaskDetail(JsonUtil.toJson(projectInfo));
			project.setProjectName(projectInfo.getName());
			projectInfo.getStartDate();
			logger.debug("startTime:" + projectInfo.getStartDate());
			logger.debug("endTime:" + projectInfo.getEndDate());
	        try {
				String startTimes[]=StringUtils.split(projectInfo.getStartDate(), "-");
				String endTimes[] = StringUtils.split(projectInfo.getEndDate(), "-");
				if(startTimes.length!=2||endTimes.length!=2)
				{
					 return ProjectConstants.PROJECT_ERROR.ProjectStartTime_ISNULL;
				}
				Calendar nowCalendar  = Calendar.getInstance();
				int year = Integer.parseInt(startTimes[0]);
				if(year<100)
				{
					year = 2000+year;
				}
			    
				nowCalendar.set(Calendar.YEAR, year);
			    int month = Integer.parseInt(startTimes[1]);
			    nowCalendar.set(Calendar.MONTH, month-1 );  
			    
			    project.setStartTime(nowCalendar.getTime());
				
			    
			    year = Integer.parseInt(endTimes[0]);
				if(year<100)
				{
					year = 2000+year;
				}
			    
				nowCalendar.set(Calendar.YEAR, year);
			    month = Integer.parseInt(endTimes[1]);
			    nowCalendar.set(Calendar.MONTH, month-1 );  
			    
			    project.setCompleteTime(getLastMonthOfDate(nowCalendar.getTime()));
				
			    
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				 return ProjectConstants.PROJECT_ERROR.ProjectStartTime_ISNULL;
			}
			
			if(StringUtils.isEmpty(projectInfo.getName()))
			{
				return ProjectConstants.PROJECT_ERROR.ProjectName_ISNULL;
			}
			
			if(StringUtils.isEmpty(projectInfo.getDeclarationUnitType()))
			{
				return ProjectConstants.PROJECT_ERROR.ProjectCatetory_ISNULL;
			}
			if(StringUtils.isEmpty(projectInfo.getDeclarationUnit()))
			{
				return ProjectConstants.PROJECT_ERROR.ProjectCompany_ISNULL;
			}
			project.setSubcategory("[{\"value\":\"" + projectInfo.getDeclarationUnitType()+"\",\"id\":\"001\"}]");
			project.setCycleType("[{\"value\":\"" + calDiffMonth(project.getStartTime(),project.getCompleteTime())+"个月\",\"id\":\"001\"}]");
			
			
			List<Contact> contacts = projectInfo.getContactList();
			if(contacts.size()<=1)
			{
				return ProjectConstants.PROJECT_ERROR.ProjectContacts_ISNULL;
			}
			if(StringUtils.isEmpty(contacts.get(0).getMail())||StringUtils.isEmpty(contacts.get(0).getTel())||StringUtils.isEmpty(contacts.get(0).getName()))
			{
				return ProjectConstants.PROJECT_ERROR.ProjectContactsTelOrEmail_ISNULL;
			}
			if(StringUtils.isEmpty(projectInfo.getServiceField()))
			{
				return ProjectConstants.PROJECT_ERROR.ProjectServiceField_ISNULL;
					
			}
			project.setEmail(contacts.get(0).getMail());
			project.setTelno(contacts.get(0).getTel());			
			project.setProjectManagerName(contacts.get(0).getName());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.debug("LLLLLLLLLLLLLLLLLLLLLL");
			return 0L;
		}
		
		
		// 生成项目编号
		Long projectSeqCode = xwSysSeqService.getXwSequence(PROJECT_SEQ, 1)
				.getStartSequence();
		// 设置项目编号
		project.setProjectId(projectSeqCode);
		// 当前用户ID
		Long userId = currentUser.getId();
		// 设置项目申请用户
		project.setProjectApplyPerson(userId.toString());
		// 获取发布信息
		DepartleaderPublish publish = departleaderPublishServiceImpl
				.getById(project.getPublishId());
		String departLeader = null;
		if(null != publish){
			// 设置发布主题
			project.setPublishTitle(publish.getTitle());
			// 指定部门经理为发布的创建者
			departLeader = publish.getCreatePerson();
			
			if(!StringUtils.isEmpty(g_departLeader))
			{
				departLeader = g_departLeader;
			}
			// 设置部门经理组为空
			roleId_departLeader = "";
			// 将该用户从该条发布的数据权限中移除
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("categoryId", publish.getCategoryId());
			queryMap.put("dataId", publish.getPublishId().toString());
			queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);
			queryMap.put("permissionId", userId.toString());
			// 从数据权限表中删除该条记录
			dataPermissionServiceImpl.deleteByConditions(queryMap);
			
			//设置该用户不能申请
			DataPermission notAllowApplication = new DataPermission();
			notAllowApplication.setDataCanNotApplyForUser(publish.getCategoryId(), DataPermission.DATATYPE_PUBLISH, publish.getPublishId().toString(), userId.toString());
			dataPermissionServiceImpl.save(notAllowApplication);
			
		}
		// 保存项目
		Long projectId = projectService.save(project);
		// 初始化流程变量
		Map<String, Object> variables = new HashMap<String, Object>();

		// 指定项目经理
		variables.put("projectManager", currentUser.getId().toString());
		// 根据项目类别指定相应的管理人员组
		String officerGroup = this.getAssigneeByProjectCategory(project
				.getCategoryId().toString());
		// 指定某个管理员
		variables.put("officer", null);
		// 指定管理员组
		variables.put("officerGroup",officerGroup);
		logger.debug("officerGroup:" + officerGroup);
		// 指定决策委员会人员
		variables.put("committee", null);
		// 指定多个决策委员会人员
		variables.put("committees", "");
		// 指定决策委员会组
		variables.put("committeeGroup", roleId_committee);
		// 指定某个部门经理
		variables.put("departLeader", departLeader);
		// 指定某个部门经理组
		variables.put("departLeaderGroup", roleId_departLeader);
		// 根据项目周期类型判断是否需要提交周期性报告，如需要设置周期时间
		variables.put("submitMonthlyReport_result", "pass"); // 设置流程变量：需要提交周期性报告
		// 初始化终期部门经理审批意见(依据部门经理终期审批意见判断是否还需要提交周期报告，通过则不用再进行提交)
		variables.put("departleaderEndApproval_result", "pass");
		logger.debug("Current User's ID is : " + userId);
		// 启动项目流程
		ProcessInstance processInstance = processServiceImpl
				.startProcess("pm_process_jianguan", project
						.getClass().getSimpleName(), projectId
						.toString(), userId.toString(), variables);
		logger.debug("CreateProject --> processInstanceId:  "
				+ processInstance.getId());
		// 设置project对象的流程实例ID
		Project latestProject = projectService
				.selectByPrimaryKey(projectId);
		latestProject.setProjectProcessInstanceId(processInstance
				.getId());
		projectService.updateProjectPropertyByProjectId(
				ProjectService.PROJECTPROCESSINSTANCEID,
				processInstance.getId(), projectId);
		return projectId;
	}

	/*
	 * 修改项目
	 */
	private Long modifyProject(Project project, ResultVO<Object> result,
			User currentUser) {
		// 获取当前项目信息
		Project oldProject = projectService.selectByPrimaryKey(project
				.getProjectId());
		
		
		
		//如果项目信息不为空，且当前用户与项目申报者一致则允许修改
		if (null != oldProject && currentUser.getId().toString()
				.equals(oldProject.getProjectApplyPerson())) {
			// 获取流程实例ID
			String proccessInstanceId = oldProject
					.getProjectProcessInstanceId();
			// 设置当前项目的流程信息
			project.setProjectProcessInstanceId(proccessInstanceId);
			project.setMainCurrentState(oldProject
					.getMainCurrentState());
			project.setMainPreviousState(oldProject
					.getMainPreviousState());
			// 设置项目状态
			project.setState(oldProject.getState());
			// 设置项目申请用户
			project.setProjectApplyPerson(oldProject.getProjectApplyPerson());
			// 设置项目申请时间
			project.setProjectApplyTime(oldProject
					.getProjectApplyTime());
			// 设置发布信息
			project.setPublishId(oldProject.getPublishId());
			project.setPublishTitle(oldProject.getPublishTitle());
			//从文件中读取项目信息
			String strInfo = project.getProjectExtInfo();
			DocReader reader = new DocReader();
			ProjectExtInfo projectExtInfo = JsonUtil.fromJson(project.getProjectExtInfo(), ProjectExtInfo.class);
			
			
			List<ProjectAnnex> projectAnnexs = projectExtInfo.getNewApplyAttachment();
			
			
			if(projectAnnexs.size()==0)
			{
				logger.debug("####################");
			   return ProjectConstants.PROJECT_ERROR.APPLICATION_ISNULL;	
			}
			
			String srcPath = project.getProjectMilestone() + "/" +projectAnnexs.get(0).getAnnexName();
			logger.debug("*************");
			logger.debug(srcPath);
			try {
				ProjectInfo projectInfo = reader.readDoc(srcPath);
				
				project.setProjectTaskDetail(JsonUtil.toJson(projectInfo));
				project.setProjectName(projectInfo.getName());
				try {
					String startTimes[]=StringUtils.split(projectInfo.getStartDate(), "-");
					String endTimes[] = StringUtils.split(projectInfo.getEndDate(), "-");
					if(startTimes.length!=2||endTimes.length!=2)
					{
						 return ProjectConstants.PROJECT_ERROR.ProjectStartTime_ISNULL;
					}
					Calendar nowCalendar  = Calendar.getInstance();
					int year = Integer.parseInt(startTimes[0]);
					if(year<100)
					{
						year = 2000+year;
					}
				    
					nowCalendar.set(Calendar.YEAR, year);
				    int month = Integer.parseInt(startTimes[1]);
				    nowCalendar.set(Calendar.MONTH, month-1 );  
				    
				    project.setStartTime(nowCalendar.getTime());
					
				    
				    year = Integer.parseInt(endTimes[0]);
					if(year<100)
					{
						year = 2000+year;
					}
				    
					nowCalendar.set(Calendar.YEAR, year);
				    month = Integer.parseInt(endTimes[1]);
				    nowCalendar.set(Calendar.MONTH, month-1 );  
				    
				    project.setCompleteTime(getLastMonthOfDate(nowCalendar.getTime()));
					
				    
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					 return ProjectConstants.PROJECT_ERROR.ProjectStartTime_ISNULL;
				}
				
				if(StringUtils.isEmpty(projectInfo.getName()))
				{
					return ProjectConstants.PROJECT_ERROR.ProjectName_ISNULL;
				}
				
				if(StringUtils.isEmpty(projectInfo.getDeclarationUnitType()))
				{
					return ProjectConstants.PROJECT_ERROR.ProjectCatetory_ISNULL;
				}
				if(StringUtils.isEmpty(projectInfo.getDeclarationUnit()))
				{
					return ProjectConstants.PROJECT_ERROR.ProjectCompany_ISNULL;
				}
				
				
				project.setSubcategory("[{\"value\":\"" + projectInfo.getDeclarationUnitType()+"\",\"id\":\"001\"}]");
				project.setCycleType("[{\"value\":\"" + calDiffMonth(project.getStartTime(),project.getCompleteTime())+"个月\",\"id\":\"001\"}]");
				 	
				List<Contact> contacts = projectInfo.getContactList();
				if(contacts.size()<=1)
				{
					return ProjectConstants.PROJECT_ERROR.ProjectContacts_ISNULL;
				}
				if(StringUtils.isEmpty(contacts.get(0).getMail())||StringUtils.isEmpty(contacts.get(0).getTel())||StringUtils.isEmpty(contacts.get(0).getName()))
				{
					return ProjectConstants.PROJECT_ERROR.ProjectContactsTelOrEmail_ISNULL;
				}
				if(StringUtils.isEmpty(projectInfo.getServiceField()))
				{
					return ProjectConstants.PROJECT_ERROR.ProjectServiceField_ISNULL;
						
				}
				project.setEmail(contacts.get(0).getMail());
				project.setTelno(contacts.get(0).getTel());
				project.setProjectManagerName(contacts.get(0).getName());
				
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				logger.debug("LLLLLLLLLLLLLLLLLLLLLL");
				return -255L;
			}
			//end 从文件读取项目信息
			// 更新项目信息
			projectService.update(project);
			Map<String, Object> variables = new HashMap<String, Object>();
			// 获取taskId
			StateInfo mainCurrentState = JsonUtil.fromJson(
					oldProject.getMainCurrentState(), StateInfo.class);
			// 完成任务
			userTaskServiceImpl.completeTask(
					mainCurrentState.getTaskId(), variables);
			// 给客户端响应
			result.setOthers("projectId", project.getProjectId());
			return oldProject.getProjectId();
		} else {
			// 给客户端响应
			result.setResult(ResultVO.FAILURE);
			logger.debug("UpdateProject-->fail: Cann't get project by the projectId! ");
			return -255L;
		}
	}
	
	/**
	 * 查看项目基本信息
	 * 
	 * @param id
	 */
	@RequestMapping(value = "/detail", method = { RequestMethod.GET,
			RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public @ResponseBody String detail(
			@RequestParam(value = "projectId", required = false) Long projectId) {
		ResultVO<Object> resultVO = new ResultVO<Object>();
		if (projectId != null) {
			Project project = projectService.selectByPrimaryKey(projectId);
			if (null != project) {
				// 设置项目基本信息
				resultVO.setOthers("project", project);
			} else {
				// 给客户端响应
				resultVO.setResult(ResultVO.FAILURE);
			}
		} else {
			resultVO.setResult(ResultVO.FAILURE);
		}
		return resultVO.toString();
	}

	/**
	 * 获取项目基本信息和项目经理信息
	 * 
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	@RequestMapping(value = "/getProjectAndProjectManagerInfo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getProjectAndProjectManagerInfo(Long projectId) {
		logger.debug("GetProjectAndProjectManagerInfo --> projectId: "
				+ projectId);
		ResultVO<Project> result = new ResultVO<>();
		Project project = null;
		User projectManager = null;
		if (projectId != null) {
			project = projectService.selectByPrimaryKey(projectId);
			if (null != project) {
				Long projectManagerId = project.getProjectManagerId();
				projectManager = userService.get(projectManagerId);
				result.setOthers("project", project);
				result.setOthers("projectManager", projectManager);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} else {
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("GetProjectAndProjectManagerInfo --> result: "
				+ result.getResult());
		return result.toString();
	}

	/**
	 * 查询项目详情（详细）
	 * 
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	@RequestMapping(value = "/getProjectDetailInfo", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getProjectDetailInfo(Long projectId) {
		logger.debug("GetProjectDetailInfo-->projectId: " + projectId);
		ProjectDetailInfo projectDetailInfo = projectService
				.getProjectDetailInfo(projectId);
		ResultVO<Project> resultVO = new ResultVO<Project>();
		resultVO.setOthers("projectDetailInfo", projectDetailInfo);
		logger.debug("GetProjectDetailInfo-->result: " + resultVO.getResult());
		return resultVO.toString();
	}

	/**
	 * 预加载项目
	 * 
	 * @param id
	 */
	@ModelAttribute("preloadProject")
	public Project getOne(
			@RequestParam(value = "projectId", required = false) Long projectId) {
		if (projectId != null) {
			Project project = projectService.selectByPrimaryKey(projectId);
			return project;
		}
		return null;
	}

	/**
	 * 修改项目
	 * 
	 * @param project
	 */
	@RequestMapping(value = "/update", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String update(
			@ModelAttribute("preloadProject") Project project) {
		projectService.update(project);
		return new ResultVO<>().toString();
	}

	/**
	 * 删除项目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String delete(Long id) {
		projectService.delete(id);
		return new ResultVO<>().toString();
	}
	
	
	/**
	 * 获取我申报的项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getApplyProjectList/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getApplyProjectList(Long categoryId) {
		logger.debug("GetApplyProjectList --> categoryId: " + categoryId);
		ResultVO<Project> resultVO = new ResultVO<Project>();
		// 获取当前用户信息
		User currentUser = getCurrentUser();
		//判断如果用户不为空
		if (null != currentUser) {
			//获取当前用户ID
			Long userId = currentUser.getId();
			logger.debug("current user is :" + userId);
			
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("categoryId", categoryId);
			queryMap.put("dataType", DataInfo.DATATYPE_PROJECT);
			queryMap.put("creatorId", userId);
			queryMap.put("extData1", Project.APPLY);
			Page<Project> page = projectService.getApplyProjectListByUserIdAndCategoryId(queryMap);
			resultVO.setPage(page);
			resultVO.setLists(page.getList());
		}else{
			logger.debug("Current user's infomation is null");
			// 给客户端响应
			resultVO.setResult(ResultVO.USERNULL);
		}
		return resultVO.toString();
	}

	/**
	 * 根据UID分页查询项目列表,查询没有结束的
	 * 
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value = "/myprojects/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getProjectListByUserId(Long categoryId) {
		ResultVO<Project> resultVO = new ResultVO<Project>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		if (null != currentUser) {
			Long userId = currentUser.getId();
			logger.debug("GetProjectListByUserId --> userId: " + userId
					+ ",  categoryId: " + categoryId);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("userId", userId);
			queryMap.put("categoryId", categoryId);

			Page<Project> page = projectService
					.getProjectListByUserId(queryMap);

			resultVO.setPage(page);
			resultVO.setLists(page.getList());
		} else {
			resultVO.setResult(ResultVO.FAILURE);
		}
		logger.debug("GetProjectListByUserId -->result:" + resultVO.getResult());
		return resultVO.toString();
	}

	/**
	 * 根据UID和项目类别分页查询已结项目列表
	 * 
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value = "/getFinishedProjectListByUserId/list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getFinishedProjectListByUserId(Long categoryId) {

		ResultVO<Project> resultVO = new ResultVO<Project>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		if (null != currentUser) {
			Long userId = currentUser.getId();
			logger.debug("GetFinishedProjectListByUserId -->userId:" + userId
					+ ",  categoryId" + categoryId);
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("userId", userId);
			queryMap.put("categoryId", categoryId);
			Page<Project> page = projectService
					.getFinishedProjectListByUserId(queryMap);
			resultVO.setPage(page);
			resultVO.setLists(page.getList());
		} else {
			resultVO.setResult(ResultVO.FAILURE);
		}
		logger.debug("GetFinishedProjectListByUserId -->result:"
				+ resultVO.getResult());
		return resultVO.toString();
	}

	/**
	 * 根据项目种类Id、当前用户信息分页查询项目列表
	 * 
	 * @param categoryId
	 *            项目种类ID
	 * @return
	 */
	@RequestMapping(value = "/getProjectsByCategoryId/list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getProjectListByCategoryId(Long categoryId) {
		logger.debug("GetProjectListByCategoryId --> categoryId: " + categoryId);
		// 获取当前用户信息
		User currentUser = getCurrentUser();
		logger.debug("登录用户为:" + currentUser.getId());
		Map<String, Object> queryMap = new HashMap<String, Object>();

		queryMap.put("categoryId", categoryId);

		Page<Project> page = projectService.getProjectListByUserAndCategoryId(
				currentUser, queryMap);

		ResultVO<Project> resultVO = new ResultVO<Project>();
		resultVO.setPage(page);
		resultVO.setLists(page.getList());
		logger.debug("GetProjectListByCategoryId -->result:"
				+ resultVO.getResult());
		return resultVO.toString();
	}

	/**
	 * 获取项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getProjectList", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getAllProjects() {
		List<Project> projects = projectService.selectAll();
		ResultVO<Object> result = new ResultVO<>();
		result.setOthers("projects", projects);
		return result.toString();
	}

	/**
	 * 获取项目尽职调查信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getJZDC", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getJZDC(Long projectId) {
		logger.debug("GetJZDC --> projectId: " + projectId);
		ResultVO<Object> result = new ResultVO<>();
		if (null != projectId) {
			List<CommonBiz> commonBizes = commonBizServiceImpl
					.selectByProjectIdAndServiceType(projectId, "jzdc");
			if (null != commonBizes && !commonBizes.isEmpty()) {
				CommonBiz jzdc = commonBizes.get(0);
				result.setOthers("jzdc", jzdc);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} else {
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("GetJZDC --> result: " + result.getResult());
		return result.toString();
	}

	/**
	 * 根据项目类别分页查询尽职调查列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getJZDCByProjectCategory/list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getJZDCByProjectCategory(Long categoryId) {
		logger.debug("GetJZDCByProjectCategory --> categoryId:  " + categoryId);
		// 获取当前用户信息
		User currentUser = getCurrentUser();
		logger.debug("登录用户为:" + currentUser.getId());
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("projectCategory", categoryId);
		queryMap.put("serviceType", "jzdc");
		Page<CommonBiz> page = commonBizServiceImpl
				.selectByCategoryServiceTypeAndProjectName(currentUser,queryMap);

		ResultVO<CommonBiz> resultVO = new ResultVO<CommonBiz>();
		resultVO.setPage(page);
		resultVO.setLists(page.getList());
		logger.debug("GetJZDCByProjectCategory -->result:"
				+ resultVO.getResult());
		return resultVO.toString();
	}

	/**
	 * 获取所有正在进行的项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getAllRunnigProjects", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getAllRunnigProjects() {
		ResultVO<Object> result = new ResultVO<>();
		User currentUser = getCurrentUser();
		logger.debug("登录用户为:" + currentUser.getId());
		if (null != currentUser) {
			Map<String, Object> runnigProjects = projectService
					.getAllRunnigProjects(currentUser);
			result.setOthers("runnigProjects", runnigProjects);
		} else {
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("getAllRunnigProjects-->result: " + result.toString());
		return result.toString();
	}

	
	
	
	
	/**
	 * 分页某个项目类型下正在进行的项目列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getRunnigProjectsByCategoryId/list", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getRunnigProjectsByCategoryId(Long categoryId) {
		logger.debug("getRunnigProjectsByCategoryId --> categoryId: "
				+ categoryId);
		ResultVO<Project> resultVO = new ResultVO<Project>();
		User currentUser = getCurrentUser();
		logger.debug("登录用户为:" + currentUser.getId());
		if (null != currentUser) {
			Map<String, Object> queryMap = new HashMap<String, Object>();

			queryMap.put("categoryId", categoryId);
			queryMap.put("state", ProjectConstants.State.CODE_START);
			Page<Project> page = projectService
					.getProjectListByCategoryIdAndState(currentUser, queryMap);

			resultVO.setPage(page);
			resultVO.setLists(page.getList());
		}
		logger.debug("GetRunnigProjectsByCategoryId-->result: "
				+ resultVO.getResult());
		return resultVO.toString();
	}

	/**
	 * 根据项目Id获取项目相关材料（附件、尽职调查）
	 * 
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	@RequestMapping(value = "/getRelatedMaterials", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getRelatedMaterials(Long projectId) {
		logger.debug("GetRelatedMaterials --> projectId:  " + projectId);
		ResultVO<Object> result = new ResultVO<>();
		// 项目材料列表
		List<ProjectMaterial> materials = new ArrayList<ProjectMaterial>();

		// 根据项目ID获取尽职调查
		CommonBiz jzdc = null;
		List<CommonBiz> commonBizes = commonBizServiceImpl
				.selectByProjectIdAndServiceType(projectId, "jzdc");
		if (null != commonBizes && !commonBizes.isEmpty()) {
			jzdc = commonBizes.get(0);
			ProjectMaterial material = new ProjectMaterial();
			material.setDataId(jzdc.getDataId());
			material.setName("尽职调查");
			material.setType("jzdc");
			material.setProjectId(jzdc.getProjectId().toString());
			String userName = getUserName(Long
					.parseLong(jzdc.getCreatePerson()));
			material.setCreatePerson(userName);
			material.setCreateTime(jzdc.getCreateTime());
			material.setDownLoadURL(null);
			materials.add(material);
		}

		// 根据项目ID获取所有项目附件
		List<ProjectAnnex> annexList = projectAnnexServiceImpl
				.selectByProjectId(projectId);
		if (null != annexList && !annexList.isEmpty()) {
			buildMaterialsByAnnexList(materials, annexList);
		}
		result.setOthers("relatedMaterials", materials);
		logger.debug("GetRelatedMaterials -->result:" + result.getResult());
		return result.toString();
	}

	/**
	 * 下载模板
	 * 
	 * @param projectId
	 *            项目ID
	 * @return
	 */
	@RequestMapping(value = "/downLoadproject", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String downLoadTemplate(HttpServletRequest request,
			Long projectId) {
		ResultVO<Project> result = new ResultVO<Project>();
		try {
			String path = request.getSession().getServletContext()
					.getRealPath(downTempBase);
			StringBuilder pathBuilder = new StringBuilder();
			pathBuilder.append(projectId);
			pathBuilder.append("_");
			pathBuilder.append(System.currentTimeMillis());
			pathBuilder.append(".doc");
			logger.debug("docPath:" + path + "::" + pathBuilder.toString());
			Project project = projectService.selectByPrimaryKey(projectId);

			String destPath = pathBuilder.toString();
			com.doc.project.vo.Project docEntity = projectService.createDownloadEntity(project);
			// 需要传输给服务绝对路径
			boolean ok = WordUtil.createWordByClassForTemplateLoading(
					docEntity, "/word/template", "project.ftl", path + "/"
							+ destPath);
			if (ok) {
				logger.debug("下载模板成功，路径：" + destPath);
				result.setOthers("path", destPath);
				result.setResult(ResultVO.SUCCESS);
			} else {
				logger.debug("下载模板路径失败！");
				result.setResult(ResultVO.FAILURE);
			}
			return result.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result.setResult(ResultVO.FAILURE);
			return result.toString();
		}
	}

	/*
	 * 根据用户ID获取用户名
	 */
	private String getUserName(Long userId) {
		String userName = null;
		User user = userService.get(userId);
		if (null != user) {
			userName = user.getUsername();
		}
		return userName;
	}

	private void buildMaterialsByAnnexList(List<ProjectMaterial> materials,
			List<ProjectAnnex> annexList) {
		for (ProjectAnnex annex : annexList) {
			ProjectMaterial material = new ProjectMaterial();
			material.setDataId(annex.getAnnexId().toString());
			material.setName(annex.getOriginalFilename());
			material.setType(annex.getTypeId().toString());
			material.setProjectId(annex.getProjectId().toString());
			String userName = getUserName(Long.parseLong(annex.getUserId()));
			material.setCreatePerson(userName);
			material.setCreateTime(annex.getUploadTime());
			material.setDownLoadURL(annex.getPath());
			materials.add(material);
		}

	}

	// 根据项目类别设置相应的管理人员
	private String getAssigneeByProjectCategory(String categoryId) {
		if (categoryId.equals(ProjectConstants.CATEGORY.CoolMarts)) {
			return roleId_CooMartsOfficer;
		} else if (categoryId.equals(ProjectConstants.CATEGORY.CamTalk)) {
			return roleId_CamTalkOfficer;
		} else {
			return roleId_LotteryOfficer;
		}
	}

}
