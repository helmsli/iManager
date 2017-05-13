package com.xinwei.process.service.impl;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.doc.project.vo.ProjectBudget;
import com.doc.project.vo.ProjectOut;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.dao.CommitteeApprovalMapper;
import com.xinwei.process.dao.DataCreateInfoMapper;
import com.xinwei.process.dao.DepartleaderApprovalMapper;
import com.xinwei.process.dao.ProjectAnnexMapper;
import com.xinwei.process.dao.ProjectMapper;
import com.xinwei.process.entity.CommitteeApproval;
import com.xinwei.process.entity.DataCreateInfo;
import com.xinwei.process.entity.DataInfo;
import com.xinwei.process.entity.DepartleaderApproval;
import com.xinwei.process.entity.ExpertReview;
import com.xinwei.process.entity.Project;
import com.xinwei.process.entity.ProjectAnnex;
import com.xinwei.process.entity.ProjectDetailInfo;
import com.xinwei.process.service.ExpertReviewService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.process.service.UserTaskService;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.exception.ExistedException;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.ListUtil;
import com.xinwei.util.date.DateUtil;
import com.xinwei.util.page.Page;

@Service
public class ProjectServiceImpl implements ProjectService {
	@Resource
	private ProjectMapper projectDao;
	@Resource
	private DataCreateInfoMapper dataCreateInfoDao;
	@Resource
	private ExpertReviewService expertReviewServiceImpl;
	@Resource
	private DepartleaderApprovalMapper departleaderApprovalDao;
	@Resource
	private CommitteeApprovalMapper committeeApprovalDao;
	@Resource
	private ProjectAnnexMapper projectAnnexdao;
	@Resource
	private RepositoryService repositoryService;
	@Resource
	private TaskService taskService;
	@Resource
	private HistoryService historyService;
	@Resource
	private RuntimeService runtimeService;
	@Resource
	private ProcessEngine processEngine;
	@Resource
	private UserTaskService userTaskServiceImpl;
	@Resource
	private ProcessService ProcessServiceImpl;

	@Value("${roleId_guest}")
	private Long roleId_guest;// 游客角色ID
	@Value("${roleId_projectManager}")
	private Long roleId_projectManager;// 项目经理角色ID
	@Value("${roleId_CooMartsOfficer}")
	private Long roleId_CooMartsOfficer;// Coomarts管理员角色ID
	@Value("${roleId_CamTalkOfficer}")
	private Long roleId_CamTalkOfficer;// CamTalk管理员角色ID
	@Value("${roleId_LotteryOfficer}")
	private Long roleId_LotteryOfficer;// Lottery管理员角色ID
	@Value("${roleId_threeLevelsLeader}")
	private Long roleId_threeLevelsLeader;// 三级部门经理角色ID
	@Value("${roleId_committee}")
	private Long roleId_committee;// 决策委员会角色ID
	@Value("${roleId_departLeader}")
	private Long roleId_departLeader;//部门经理角色ID
	
	@Override
	public void delete(Long project_id) {
		projectDao.deleteByPrimaryKey(project_id);
	}

	@Override
	public void update(Project project) {
		projectDao.updateByPrimaryKey(project);
	}

	@Override
	public Long save(Project project) throws ExistedException {
		// 获取当前时间
		Date now = new Date(System.currentTimeMillis());
		// 设置项目的申请时间
		project.setProjectApplyTime(now);
		// 初始化项目状态
		project.setState(ProjectConstants.State.CODE_START);
		// 保存项目
		projectDao.insert(project);
		// 保存该发布的创建信息
		saveDataCreateInfo(project);
		return project.getProjectId();
	}

	/*
	 * 保存数据创建信息
	 */
	private void saveDataCreateInfo(Project project) {
		DataCreateInfo dataCreateInfo = new DataCreateInfo();
		dataCreateInfo.setCategoryId(project.getCategoryId());
		dataCreateInfo.setDataId(project.getProjectId().toString());
		dataCreateInfo.setDataType(DataInfo.DATATYPE_PROJECT);
		dataCreateInfo.setCreatorId(Long.parseLong(project.getProjectApplyPerson()));
		dataCreateInfo.setCreateTime(project.getProjectApplyTime());
		//申报字段用来区分项目经理自己创建的项目申请
		if(!StringUtils.isBlank(project.getPublishTitle())){
			
			dataCreateInfo.setExtData1(Project.APPLY);
		}else{
			dataCreateInfo.setExtData1(Project.CREATE);
		}
		dataCreateInfoDao.insert(dataCreateInfo);
		
	}
	
	@Override
	public List<Project> selectAll() {
		return projectDao.selectAll();
	}

	@Override
	public Project selectByPrimaryKey(Long project_id) {
		return projectDao.selectByPrimaryKey(project_id);
	}

	public Page<Project> getProjectListByUserId(Map<String, Object> map) {
		Page<Project> page = new Page<Project>(
				projectDao.getProjectListByUserIdCount(map));
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(projectDao.getProjectListByUserId(map));
		return page;
	}

	/**
	 * 获取评审详情
	 */
	@Override
	public ProjectDetailInfo getProjectDetailInfo(Long projectId) {
		ProjectDetailInfo projectDetailInfo = new ProjectDetailInfo();
		// 项目基本信息
		Project project = projectDao.selectByPrimaryKey(projectId);
		projectDetailInfo.setProject(project);
		// 项目各阶段信息
		projectDetailInfo
				.setBeginStage(getProjectStageInfo(projectId, "begin"));// 获取初期项目信息
		projectDetailInfo.setMiddleStage(getProjectStageInfo(projectId,
				"middle"));// 获取中期项目信息
		projectDetailInfo.setLastStage(getProjectStageInfo(projectId, "last"));// 获取终期项目信息
		// 项目周期性报告
		List<ProjectAnnex> projectAnnexLists = projectAnnexdao
				.selectMonthlyReportByProjectId(projectId);
		projectDetailInfo.setProjectAnnexLists(projectAnnexLists);
		return projectDetailInfo;
	}

	private Map<String, Object> getProjectStageInfo(Long projectId, String stage) {
		Map<String, Object> stageMap = new HashMap<String, Object>();
		List<ExpertReview> expertReviewByStage = new ArrayList<ExpertReview>();
		List<DepartleaderApproval> departleaderApprovalByStage = new ArrayList<DepartleaderApproval>();
		List<CommitteeApproval> committeeApprovalByStage = new ArrayList<CommitteeApproval>();
		// 专家评审
		expertReviewByStage = expertReviewServiceImpl.getExpertReviewByStage(
				projectId, stage);
		// 部门经理审批
		departleaderApprovalByStage = departleaderApprovalDao
				.getDepartleaderApprovalByStage(projectId, stage);
		// 决策委员会审批
		committeeApprovalByStage = committeeApprovalDao
				.getCommitteeApprovalByStage(projectId, stage);
		stageMap.put("expertReview", expertReviewByStage);
		stageMap.put("committeeApproval", committeeApprovalByStage);
		stageMap.put("departleaderApproval", departleaderApprovalByStage);
		return stageMap;
	}

	// 查看项目(根据项目种类和状态获取所有正在运行的项目列表)
	@Override
	public Map<String, Object> getAllRunnigProjects(User currentUser) {
		Map<String, Object> runningMap = new HashMap<String, Object>();
		List<Project> coolMarsProjects = new ArrayList<Project>();
		List<Project> camTalkProjects = new ArrayList<Project>();
		List<Project> lotteryProjects = new ArrayList<Project>();
		String state = ProjectConstants.State.CODE_START;
		List<Long> roleIds = currentUser.getRoleIds();
		if (null != roleIds && !roleIds.isEmpty()) {
			// 如果为项目经理，只能查询自己申请的
			if (roleIds.contains(roleId_projectManager)) {
				// 根据项目project_manager_id、种类、状态获取所有
				coolMarsProjects = projectDao
						.selectAllByProjectManagerIdCategoryIdAndState(
								currentUser.getId(), 1L, state);
				camTalkProjects = projectDao
						.selectAllByProjectManagerIdCategoryIdAndState(
								currentUser.getId(), 2L, state);
				lotteryProjects = projectDao
						.selectAllByProjectManagerIdCategoryIdAndState(
								currentUser.getId(), 3L, state);

			}
			//如果为CooMarts管理员
			if (roleIds.contains(roleId_CooMartsOfficer)) {
				// 根据项目总类和状态获取所有
				coolMarsProjects = projectDao.selectAllByCategoryIdAndState(1L,
						state);
			} 
			//如果为CamTalk管理员
			if (roleIds.contains(roleId_CamTalkOfficer)) {
				camTalkProjects = projectDao.selectAllByCategoryIdAndState(2L,
						state);
			}
			//如果为Lottery管理员
			if (roleIds.contains(roleId_LotteryOfficer)) {
				lotteryProjects = projectDao.selectAllByCategoryIdAndState(3L,
						state);
			}
			// 如果为部门经理、决策委员会、以及三级部门经理可查看所有
			if (roleIds.contains(roleId_departLeader)
					|| roleIds.contains(roleId_committee)
					|| roleIds.contains(roleId_threeLevelsLeader)) {
				// 根据项目总类和状态获取所有
				coolMarsProjects = projectDao.selectAllByCategoryIdAndState(1L,
						state);
				camTalkProjects = projectDao.selectAllByCategoryIdAndState(2L,
						state);
				lotteryProjects = projectDao.selectAllByCategoryIdAndState(3L,
						state);
			}
						
		}
		runningMap.put("coolMarsProjects", coolMarsProjects);
		runningMap.put("camTalkProjects", camTalkProjects);
		runningMap.put("lotteryProjects", lotteryProjects);
		return runningMap;
	}

	// 根据UID和项目类别分页查询已结项目列表
	@Override
	public Page<Project> getFinishedProjectListByUserId(Map<String, Object> map) {
		Page<Project> page = new Page<Project>(
				projectDao.getFinishedProjectListByUserIdCount(map));
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(projectDao.getFinishedProjectListByUserId(map));
		return page;
	}

	@Override
	public Page<Project> getProjectListByUserAndCategoryId(User currentUser,
			Map<String, Object> map) {
		Page<Project> page = new Page<Project>(0L);
		Long categoryId = (Long) map.get("categoryId");
		// 根据categoryId获取对应的管理员角色
		Long officerRoleId = getOfficerByCategoryId(categoryId.toString());
		List<Long> roleIds = currentUser.getRoleIds();
		if (null != roleIds && !roleIds.isEmpty()) {
			// 如果当前用户为项目经理，只能查询自己申请的
			if (roleIds.contains(roleId_projectManager)) {
				map.put("dataType", DataInfo.DATATYPE_PROJECT);
				map.put("creatorId", currentUser.getId());
				map.put("extData1", Project.CREATE);
				page = new Page<Project>(
						dataCreateInfoDao.countByConditions(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				// 查询dataCreateInfo表，获取dataID列表
				List<String> dataIdList = dataCreateInfoDao
							.selectListByConditions(map);
				if(null!=dataIdList && !dataIdList.isEmpty()){
					
					List<Long> projectIdList = ListUtil.fromStringToLongList(dataIdList);
					page.setList(projectDao.selectByIdList(projectIdList));
				}
			} 
			// 如果查询的项目种类与管理员角色相对应则可以查看
            if(roleIds.contains(officerRoleId)){
            	map.put("dataType", DataInfo.DATATYPE_PROJECT);
				page = new Page<Project>(
						dataCreateInfoDao.countByConditions(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				// 查询dataCreateInfo表，获取dataID列表
				List<String> dataIdList = dataCreateInfoDao
							.selectListByConditions(map);
				if(null!=dataIdList && !dataIdList.isEmpty()){
					
					List<Long> projectIdList = ListUtil.fromStringToLongList(dataIdList);
					page.setList(projectDao.selectByIdList(projectIdList));
				}
			}
            // 如果为部门经理、决策委员会、以及三级部门经理可查看所有
			if (roleIds.contains(roleId_departLeader)
					|| roleIds.contains(roleId_committee)
					|| roleIds.contains(roleId_threeLevelsLeader)) {
				
				map.put("dataType", DataInfo.DATATYPE_PROJECT);
				page = new Page<Project>(
						dataCreateInfoDao.countByConditions(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				// 查询dataCreateInfo表，获取dataID列表
				List<String> dataIdList = dataCreateInfoDao
							.selectListByConditions(map);
				if(null!=dataIdList && !dataIdList.isEmpty()){
					
					List<Long> projectIdList = ListUtil.fromStringToLongList(dataIdList);
					page.setList(projectDao.selectByIdList(projectIdList));
				}
			}
		}
		return page;
	}

	private Long getOfficerByCategoryId(String categoryIdStr) {
		if (categoryIdStr.equals(ProjectConstants.CATEGORY.CoolMarts)) {
			return roleId_CooMartsOfficer;
		} else if (categoryIdStr.equals(ProjectConstants.CATEGORY.CamTalk)) {
			return roleId_CamTalkOfficer;
		} else {
			return roleId_LotteryOfficer;
		}
	}

	/**
	 * 得到带有高亮节点的流程图
	 * 
	 * @param processInstanceId
	 *            流程实例id
	 */
	@Override
	public byte[] traceProcessImage(String processInstanceId) {
		// 创建核心引擎流程对象processEngine
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		TaskService taskService = processEngine.getTaskService();
		// 唯一
		Task task = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).singleResult();
		// 流程定义
		BpmnModel bpmnModel = processEngine.getRepositoryService()
				.getBpmnModel(task.getProcessDefinitionId());

		// 正在活动节点
		List<String> activeActivityIds = processEngine.getRuntimeService()
				.getActiveActivityIds(task.getExecutionId());

		ProcessEngineConfiguration processEngineConfiguration = processEngine
				.getProcessEngineConfiguration();

		// 得到图片输出流（这样加可防止生成的流程图片乱码）
		InputStream inputStream = processEngineConfiguration
				.getProcessDiagramGenerator().generateDiagram(bpmnModel, "png",
						activeActivityIds, new ArrayList(),
						processEngineConfiguration.getActivityFontName(),
						processEngineConfiguration.getLabelFontName(), null,
						1.0);
		try {
			return IOUtils.toByteArray(inputStream);
		} catch (Exception e) {
			throw new RuntimeException("生成流程图异常！", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
		}
	}

	/**
	 * 修改项目某个属性值
	 * 
	 * @param propertyName
	 *            属性名称
	 * @param propertyNewValue
	 *            属性新值
	 * @param projectId
	 *            项目ID
	 */
	@Override
	public void updateProjectPropertyByProjectId(String propertyName,
			String propertyNewValue, Long projectId) {
		if (null != projectId) {
			if (propertyName.equals(ProjectService.CHANGECURRENTSTATE)) {
				// 修改更改流程当前状态
				projectDao
						.updateChangeCurrentState(propertyNewValue, projectId);
			} else if (propertyName.equals(ProjectService.CHANGEDATAID)) {
				// 修改更改数据ID
				projectDao.updateChangeDataId(propertyNewValue, projectId);
			} else if (propertyName.equals(ProjectService.CHANGEPREVIOUSSTATE)) {
				// 修改项目更改流程上一状态信息
				projectDao.updateChangePreviousState(propertyNewValue,
						projectId);
			} else if (propertyName
					.equals(ProjectService.CHANGEPROCESSINSTANCEID)) {
				// 修改项目更改流程实例ID
				projectDao.updateChangeProcessInstanceId(propertyNewValue,
						projectId);
			} else if (propertyName.equals(ProjectService.DEPARTLEADERAPPRAISE)) {
				// 修改项目表部门经理评价
				projectDao.updateDepartLeaderAppraiseByProjectId(
						propertyNewValue, projectId);
			} else if (propertyName.equals(ProjectService.MAINCURRENTSTATE)) {
				// 修改项目主流程当前状态信息
				projectDao.updateMainCurrentState(propertyNewValue, projectId);
			} else if (propertyName.equals(ProjectService.MAINPREVIOUSSTATE)) {
				// 修改项目主流程上一状态信息
				projectDao.updateMainPreviousState(propertyNewValue, projectId);
			} else if (propertyName
					.equals(ProjectService.PROJECTPROCESSINSTANCEID)) {
				// 修改项目流程实例ID
				projectDao.updateProcessInstanceByProjectID(propertyNewValue,
						projectId);
			} else if (propertyName.equals(ProjectService.REPORTCURRENTSTATE)) {
				// 修改周期性报告当前状态
				projectDao
						.updateReportCurrentState(propertyNewValue, projectId);
			} else if (propertyName.equals(ProjectService.SELFAPPRAISE)) {
				// 修改项目经理自评
				projectDao.updateSelfAppraiseByProjectId(propertyNewValue,
						projectId);
			} else {
				// 修改项目状态
				projectDao.updateState(propertyNewValue, projectId);
			}
		}
	}

	// 更新多个属性
	@Override
	public void updateProjectPropertysByProjectId(String[] propertyName,
			String[] propertyNewValue, Long projectId) {
		for (int i = 0; i < propertyName.length; i++) {
			updateProjectPropertyByProjectId(propertyName[i],
					propertyNewValue[i], projectId);
		}
	}

	// 修改项目changeDataId&changeProcessInstanceId
	@Override
	public void updateProjectChangeByProjectId(String changeDataId,
			String changeProcessInstanceId, Long projectId) {
		projectDao.updateProjectChangeByProjectId(changeDataId,
				changeProcessInstanceId, projectId);
	}

	@Override
	public Project selectChangeInfoByProjectId(Long projectId) {
		return projectDao.selectChangeInfoByProjectId(projectId);
	}

	@Override
	public List<Project> selectAllByCategoryIdAndState(Long categoryId,
			String state) {

		return projectDao.selectAllByCategoryIdAndState(categoryId, state);
	}

	@Override
	public Page<Project> getProjectListByCategoryIdAndState(User currentUser,
			Map<String, Object> map) {
		Page<Project> page = new Page<Project>(0L);
		Long categoryId = (Long) map.get("categoryId");
		// 根据categoryId获取对应的管理员角色
		Long officerRoleId = getOfficerByCategoryId(categoryId.toString());
		List<Long> roleIds = currentUser.getRoleIds();
		if (null != roleIds && !roleIds.isEmpty()) {
			// 如果为项目经理，只能查询自己申请的
			if (roleIds.contains(roleId_projectManager)) {
			
				map.put("userId", currentUser.getId());
				page = new Page<Project>(
						projectDao
								.countByProjectManagerIdCategoryIdAndState(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				page.setList(projectDao
						.getByProjectManagerIdCategoryIdAndState(map));
			}
			// 如果查询的项目种类与管理员角色相对应则可以查看
            if(roleIds.contains(officerRoleId)){
            	page = new Page<Project>(
						projectDao.countByCategoryIdAndState(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				page.setList(projectDao.getByCategoryIdAndState(map));
            }
			// 如果为部门经理、决策委员会、以及三级部门经理可查看所有
			if (roleIds.contains(roleId_departLeader)
					|| roleIds.contains(roleId_committee)
					|| roleIds.contains(roleId_threeLevelsLeader)) {
				page = new Page<Project>(
						projectDao.countByCategoryIdAndState(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				page.setList(projectDao.getByCategoryIdAndState(map));
			}
		}
		return page;
	}

	@Override
	public List<Project> selectAllByProjectManagerIdCategoryIdAndState(
			Long projectManagerId, Long categoryId, String state) {

		return projectDao.selectAllByProjectManagerIdCategoryIdAndState(
				projectManagerId, categoryId, state);
	}

	@Override
	public Project getByApplyPersonAndPublishId(String projectApplyPerson,
			Long publishId) {
		return projectDao.selectByApplyPersonAndPublishId(projectApplyPerson,
				publishId);
	}

	@Override
	public Page<Project> getApplyProjectListByUserIdAndCategoryId(
			Map<String, Object> queryMap) {
		Page<Project> page = new Page<Project>(
				dataCreateInfoDao.countByConditions(queryMap));
		queryMap.put("startRow", page.getStartRow());
		queryMap.put("pageSize", page.getPageSize());
		// 查询dataCreateInfo表，获取dataID列表
		List<String> dataIdList = dataCreateInfoDao
					.selectListByConditions(queryMap);
		if(null!=dataIdList && !dataIdList.isEmpty()){
			
			List<Long> projectIdList = ListUtil.fromStringToLongList(dataIdList);
			page.setList(projectDao.selectByIdList(projectIdList));
		}
		return page;
	}

	// 创建下载模板对象
	@Override
	public com.doc.project.vo.Project createDownloadEntity(Project project) {
		com.doc.project.vo.Project templateProject = new com.doc.project.vo.Project();
		// 项目名称
		String projectName = project.getProjectName();
		if (StringUtils.isNotBlank(projectName)) {
			templateProject.setName(projectName);
		} else {
			templateProject.setName("");
		}
		// 项目负责人
		String projectManagerName = project.getProjectManagerName();
		if (StringUtils.isNotBlank(projectManagerName)) {
			templateProject.setLeader(projectManagerName);
		} else {
			templateProject.setLeader("");
		}
		// 开始时间
		String startTime = DateUtil.getDate(project.getStartTime());
		if (StringUtils.isNotEmpty(startTime)) {
			templateProject.setStartTime(startTime);
		} else {
			templateProject.setStartTime("");
		}
		// 完成时间
		String endTime = DateUtil.getDate(project.getCompleteTime());
		if (StringUtils.isNotEmpty(endTime)) {
			templateProject.setEndTime(endTime);
		} else {
			templateProject.setEndTime("");
		}
		// 电话
		String telno = project.getTelno();
		if (StringUtils.isNotBlank(telno)) {
			templateProject.setTelephone(telno);
		} else {
			templateProject.setTelephone("");
		}
		// 邮箱
		String email = project.getEmail();
		if (StringUtils.isNotBlank(email)) {
			templateProject.setEmail(email);
		} else {
			templateProject.setEmail("");
		}

		try {
			JSONArray projectOutJsonArray = new JSONArray(project
					.getProjectExtInfo().toString());
			for (int i = 0; i < projectOutJsonArray.length(); i++) {
				JSONObject outObj = projectOutJsonArray.getJSONObject(i);
				// 项目简介
				String taskDescribeStr = outObj.getString("taskDescribe");
				if (StringUtils.isNotBlank(taskDescribeStr)) {
					templateProject.setVerview(taskDescribeStr);
				} else {
					templateProject.setVerview("");
				}
				// 项目背景
				String taskbackgroundStr = outObj.getString("taskbackground");
				if (StringUtils.isNotBlank(taskbackgroundStr)) {
					templateProject.setBackground(taskbackgroundStr);
				} else {
					templateProject.setBackground("");
				}
				// 项目目标
				String taskgoalStr = outObj.getString("taskgoal");
				if (StringUtils.isNotBlank(taskgoalStr)) {
					templateProject.setObjectives(taskgoalStr);
				} else {
					templateProject.setObjectives("");
				}
				// 项目风险
				String taskDangerStr = outObj.getString("taskDanger");
				if (StringUtils.isNotBlank(taskDangerStr)) {
					templateProject.setRisk(taskDangerStr);
				} else {
					templateProject.setRisk("");
				}

				// 项目创新性和推广性
				String taskcreateStr = outObj.getString("taskcreate");
				if (StringUtils.isNotBlank(taskcreateStr)) {
					templateProject.setInnovationAndRomotion(taskcreateStr);
				} else {
					templateProject.setInnovationAndRomotion("");
				}

				// 项目实施计划及输出（主要是里程碑和输出）
				String taskplanStr = outObj.getString("taskplan");

				if (StringUtils.isNotBlank(taskplanStr)) {
					JSONArray taskplanArr = new JSONArray(taskplanStr);
					// 计算taskplanArr的个数
					int len = calcCounts(taskplanStr);
					for (int j = 0; j < len; j++) {
						JSONObject taskplanObj = taskplanArr.getJSONObject(j);
						// 获取taskPlan每一个对象中的值
						if (taskplanObj != null) {
							String planTime = taskplanObj.getString("planTime");
							String planContext = taskplanObj
									.getString("planContext");
							String planOutPUT = taskplanObj
									.getString("planOutPUT");
							String planOutWord = taskplanObj
									.getString("planOutWord");
							templateProject.addProjectOut(new ProjectOut(
									planTime, planContext, planOutPUT,
									planOutWord));
						} else {
							templateProject.addProjectOut(new ProjectOut("",
									"", "", ""));
						}
					}
				} else {
					taskplanStr = "";
				}
			}
		} catch (Exception e) {

			Map outObj = JsonUtil.fromJson(project.getProjectExtInfo()
					.toString());
			// 项目简介
			String taskDescribeStr = (String) outObj.get("taskDescribe");
			if (StringUtils.isNotBlank(taskDescribeStr)) {
				templateProject.setVerview(taskDescribeStr);
			} else {
				templateProject.setVerview("");
			}
			// 项目背景
			String taskbackgroundStr = (String) outObj.get("taskbackground");
			if (StringUtils.isNotBlank(taskbackgroundStr)) {
				templateProject.setBackground(taskbackgroundStr);
			} else {
				templateProject.setBackground("");
			}
			// 项目目标
			String taskgoalStr = (String) outObj.get("taskgoal");
			if (StringUtils.isNotBlank(taskgoalStr)) {
				templateProject.setObjectives(taskgoalStr);
			} else {
				templateProject.setObjectives("");
			}
			// 项目风险
			String taskDangerStr = (String) outObj.get("taskDanger");
			if (StringUtils.isNotBlank(taskDangerStr)) {
				templateProject.setRisk(taskDangerStr);
			} else {
				templateProject.setRisk("");
			}

			// 项目创新性和推广性
			String taskcreateStr = (String) outObj.get("taskcreate");
			if (StringUtils.isNotBlank(taskcreateStr)) {
				templateProject.setInnovationAndRomotion(taskcreateStr);
			} else {
				templateProject.setInnovationAndRomotion("");
			}

			// 项目实施计划及输出（主要是里程碑和输出）
			String taskplanStr = (String) outObj.get("taskplan");

			if (StringUtils.isNotBlank(taskplanStr)&&!("[{}]".equals(taskplanStr))) {
				JSONArray taskplanArr = new JSONArray(taskplanStr);
				// 计算taskplanArr的个数
				int len = calcCounts(taskplanStr);
				for (int j = 0; j < len; j++) {
					JSONObject taskplanObj = taskplanArr.getJSONObject(j);
					// 获取taskPlan每一个对象中的值
					if (taskplanObj != null) {
						String planTime = taskplanObj.getString("planTime");
						String planContext = taskplanObj
								.getString("planContext");
						String planOutPUT = taskplanObj.getString("planOutPUT");
						String planOutWord = taskplanObj
								.getString("planOutWord");
						templateProject.addProjectOut(new ProjectOut(planTime,
								planContext, planOutPUT, planOutWord));
					} else {
						templateProject.addProjectOut(new ProjectOut("", "",
								"", ""));
					}
				}
			} else {
				taskplanStr = "";
			}
		}

		JSONArray costsJsonArray = new JSONArray(project.getProjectCosts());
		for (int i = 0; i < costsJsonArray.length(); i++) {
			JSONObject budgetObj = costsJsonArray.getJSONObject(i);
			if (budgetObj != null) {
				// 支出名称
				String payName = budgetObj.getString("payName");
				if (StringUtils.isEmpty(payName)) {
					payName = "";
				}
				// 单价
				String payPrice = budgetObj.getString("payPrice");
				if (StringUtils.isEmpty(payPrice)) {
					payPrice = "";
				}
				// 数量
				String payNumber = budgetObj.getString("payNumber");
				if (StringUtils.isEmpty(payNumber)) {
					payNumber = "";
				}
				// 小计
				String paySbutotal = "";
				if (StringUtils.isEmpty(payPrice)
						|| StringUtils.isEmpty(payNumber)) {
					paySbutotal = "";
				} else {
					paySbutotal = String.valueOf(Double.valueOf(payPrice)
							* (Double.valueOf(payNumber)));
				}
				// 备注
				String payRemarks = budgetObj.getString("payRemarks");
				if (StringUtils.isEmpty(payRemarks)) {
					payRemarks = "";
				}
				templateProject.addProjectBudget(new ProjectBudget(1 + i,
						payName, payPrice, payNumber, paySbutotal, payRemarks));
			} else {
				templateProject.addProjectBudget(new ProjectBudget(1 + i, "",
						"", "", "", ""));
			}
		}

		String projectTaskDetail = project.getProjectTaskDetail();
		Map detail = JsonUtil.fromJson(projectTaskDetail);
		// String radio = detail.get("radio").toString();
		// Map radioJsonObj = JsonUtil.fromJson(radio);

		// 项目周期
		String cycleType = project.getCycleType();
		if (StringUtils.isNotBlank(cycleType)) {
			JSONArray cycleTypeArr = new JSONArray(cycleType);
			JSONObject radioObj = cycleTypeArr.getJSONObject(0);
			templateProject.setCycle(radioObj.get("value").toString());
		} else {
			templateProject.setCycle("");
		}
		// 设置项目类型[{"value":"博彩","id":"702"}]
		String subcategory = String.valueOf(project.getSubcategory());
		JSONArray subcategoryArr = new JSONArray(subcategory);
		JSONObject subcategoryObj = subcategoryArr.getJSONObject(0);
		String subcategoryStr = subcategoryObj.get("value").toString();
		if (StringUtils.isNotBlank(subcategoryStr)) {
			templateProject.setCategory(subcategoryStr);
		} else {
			templateProject.setCategory("");
		}

		// 项目需要的准备条件
		String cpu = detail.get("CPU").toString();
		if (StringUtils.isNotBlank(cpu)) {
			templateProject.addCPU(cpu);
		} else {
			templateProject.addCPU("");
		}
		// 内存
		String memory = detail.get("memory").toString();
		if (StringUtils.isNotBlank(memory)) {
			templateProject.addMemory(memory);
		} else {
			templateProject.addMemory("");
		}
		// 带宽
		String bandWidth = detail.get("bandWidth").toString();
		if (StringUtils.isNotBlank(bandWidth)) {
			templateProject.addBandwidth(bandWidth);
		} else {
			templateProject.addBandwidth("");
		}
		// 服务器数量
		String serverNumber = detail.get("serversNumber").toString();
		if (StringUtils.isNotBlank(serverNumber)) {
			templateProject.addServerNumber(serverNumber);
		} else {
			templateProject.addServerNumber("");
		}

		// 升级需要的时间
		String upgradeTime = detail.get("UpgradeTime").toString();
		if (StringUtils.isNotBlank(upgradeTime)) {
			templateProject.addUpgradeTime(upgradeTime);
		} else {
			templateProject.addBandwidth("");
		}
		// 其它条件
		String otherConditions = detail.get("otherConditions").toString();
		if (StringUtils.isNotBlank(otherConditions)) {
			templateProject.addOtherCondition(otherConditions);
		} else {
			templateProject.addOtherCondition("");
		}
		return templateProject;
	}

	private int calcCounts(String taskplanStr) {
		int sum = 0;
		char arr[] = taskplanStr.toCharArray();// 转换成字符数组
		Arrays.sort(arr);// 数组排序
		String temp = new String(arr);// 重新产生字符串
		char newArr[] = temp.toCharArray();
		for (int i = 0; i < newArr.length; i++) {
			char c = temp.charAt(i);
			String t = String.valueOf(c);
			if (t.equals("{")) {
				sum++;
			}
		}
		return sum;
	}

}
