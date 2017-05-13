package com.xinwei.process.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.activiti.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xinwei.process.constant.DepartmentLeaderPublishConstants;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.DepartleaderPublish;
import com.xinwei.process.entity.FlowProcessDatakey;
import com.xinwei.process.entity.FlowProcessDatakeyList;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.entity.ThreeleaderApplication;
import com.xinwei.process.service.DepartleaderPublishService;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ThreeleaderApplicationService;
import com.xinwei.process.service.UserTaskService;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.security.service.UserService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/apply")
public class ThreeleaderApplyController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ThreeleaderApplyController.class);
	@Resource
	private ThreeleaderApplicationService threeleaderApplicationServiceImpl;//三级部门经理申报服务
	@Resource
	private DepartleaderPublishService departleaderPublishServiceImpl;//部门领导发布服务
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private UserService userService;
	@Resource
	UserRoleService userRoleService;
	@Resource
	private UserTaskService userTaskServiceImpl;// 用户任务相关服务

	@Value("${officer_CoolMarts}")
	private String officer_CoolMarts;// CoolMarts管理员ID
	@Value("${officer_CamTalk}")
	private String officer_CamTalk;// CamTalk管理员ID
	@Value("${officer_CoolMarts}")
	private String officer_Lottery;// Lottery管理员ID
	@Value("${committee}")
	private String committee;// 决策委员会人员ID
	@Value("${roleId_threeLevelsLeader}")
	private Long roleId_threeLevelsLeader;
	
	
	/**
	 * 三级部门经理申报
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/create", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String sanjibmqingqiu(ThreeleaderApplication threeleaderApplication) {
		logger.debug("sanjibmqingqiu start....");
		ResultVO<Object> result = new ResultVO<>();
		try {
			
			//获取当前登录用户信息
			User currentUser = getCurrentUser();
			//判断如果用户不为空
			if(null != currentUser){
				//当前用户ID
				String userId = currentUser.getId().toString();
				logger.debug("currentUser's id is : " + userId);
				
				//判断当前用户是否已经申报
				// 判断用户是否已经申报过
				ThreeleaderApplication application = threeleaderApplicationServiceImpl
						.getByApplyPersonAndPublishId(userId.toString(),
								threeleaderApplication.getDepartleaderPublishId());
				if(null != application){
					// 给客户端响应
					logger.debug("application is existed!");
					result.setResult(ResultVO.FAILURE);
				}else{
					
					//DepartleaderPublish departleaderPublish = departleaderPublishServiceImpl.getById(publishId)
					//设置申报用户
					threeleaderApplication.setApplyPerson(userId);
					// 保存申报信息
					Long applicationId = threeleaderApplicationServiceImpl.save(threeleaderApplication);
					
					// 设置流程变量
					Map<String, Object> variables = new HashMap<String, Object>();
					// 指定三级部门经理
					variables.put("threeLevelsLeader", userId);
					// 根据项目类别指定相应的管理人员
					String officer = this.getAssigneeByProjectCategory(threeleaderApplication
							.getCategoryId().toString());
					variables.put("officer", officer);
					// 指定决策委员会
					variables.put("committee", committee);
					// 获取部门经理发布信息
					DepartleaderPublish departleaderPublish = departleaderPublishServiceImpl
							.getById(threeleaderApplication.getDepartleaderPublishId());
					// 指定部门经理为发布的创建者
					variables.put("departLeader", departleaderPublish.getCreatePerson());
					FlowProcessDatakeyList flowList = new FlowProcessDatakeyList();
					flowList.add(new FlowProcessDatakey(FlowProcessDatakey.PUBLISH,threeleaderApplication.getDepartleaderPublishId()));
					variables.put(DepartmentLeaderPublishConstants.State.PROCESS_PROJECT_ID,flowList.toString());
					
					// 启动项目流程
					ProcessInstance processInstance = processServiceImpl.startProcess(
							"pm_process_bumenjlfb",threeleaderApplication.getClass().getSimpleName(),
							applicationId.toString(), userId, variables);
					logger.debug("sanjibmqingqiu -->processInstanceId: " + processInstance.getId());
					// 修改流程实例ID
					threeleaderApplicationServiceImpl.updateProcessInstanceIdByApplicationId(processInstance.getId(),applicationId);
					// 给客户端响应
					result.setOthers("applicationId", applicationId);
					logger.debug("sanjibmqingqiu -->threeleaderApplicationId:  " + applicationId);
				}
			}else{
				logger.debug("Current user's information is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			// 给客户端响应
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("sanjibmqingqiu -->result: " + result.toString());
		return result.toString();
	}
	
	/**
	 * 三级部门经理修改申报
	 * 
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/modify", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String modify(ThreeleaderApplication threeleaderApplication) {
		logger.debug("modifyChange --> threeleaderApplicationid: " + threeleaderApplication.getApplicationId());
		User user = getCurrentUser();
		ResultVO<ThreeleaderApplication> result = new ResultVO<>();
		try {
			ThreeleaderApplication oldApplication = threeleaderApplicationServiceImpl.getById(threeleaderApplication
					.getApplicationId());
			if(null!= user && null!= oldApplication){
				logger.debug("currentUser's id is : " + user.getId());
				//判断当前用户与申报人是否一致，若一致允许修改，否则不允许
				if(user.getId().toString().equals(oldApplication.getApplyPerson())){
					//设置更新时间
					threeleaderApplication.setUpdateTime(new Date());
					//设置申报时间
					threeleaderApplication.setApplyTime(oldApplication.getApplyTime());
					//设置申报人
					threeleaderApplication.setApplyPerson(oldApplication.getApplyPerson());
					//设置申报状态（标识是否结束）
					threeleaderApplication.setState(oldApplication.getState());
					//设置发布信息
					threeleaderApplication.setCategoryId(oldApplication.getCategoryId());
					threeleaderApplication.setDepartleaderPublishId(oldApplication.getDepartleaderPublishId());
					//流程变量
					threeleaderApplication.setProcessInstanceId(oldApplication.getProcessInstanceId());
					//更新该申报记录
					threeleaderApplicationServiceImpl.updateByPrimaryKey(threeleaderApplication);	
					
					//获取currentState
					StateInfo currentState = JsonUtil.fromJson(oldApplication.getCurrentState(), StateInfo.class);
					//获取任务ID
					String taskId = currentState.getTaskId();
					Map<String, Object> variables = new HashMap<String, Object>();
					// 完成任务
					userTaskServiceImpl.completeTask(taskId, variables);
					// 给客户端响应
					result.setOthers("applicationId", threeleaderApplication.getApplicationId());
				}else{
					logger.debug("modifyChange -->: " + "当前用户没有权限修改");	
					// 给客户端响应没有权限修改
					result.setResult(ResultVO.NOAUTHORITY);
				}
			}else{
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			logger.error("modifyChange -->exception: " +e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("modifyChange -->result: " + result.toString());
		return result.toString();
	}
	
	/**
	 * 获取具体的申报信息
	 * @param applicationId 申报id
	 * @return
	 */
	@RequestMapping(value="/getApplication", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getApplication(Long applicationId) {
		logger.debug("GetApplication --> applicationId: " + applicationId);
		ResultVO<ThreeleaderApplication> result = new ResultVO<>();
		//获取申报信息
		ThreeleaderApplication threeleaderApplication = threeleaderApplicationServiceImpl.getById(applicationId);
		if(null != threeleaderApplication ){
			
			//返回申报信息
			result.setOthers("threeleaderApplication", threeleaderApplication);
			//获取发布信息
			DepartleaderPublish departleaderPublish = departleaderPublishServiceImpl.getById(threeleaderApplication.getDepartleaderPublishId());
			//返回申报信息
			result.setOthers("departleaderPublish", departleaderPublish);
			//获取申报者信息
		    User user = userService.get(Long.parseLong(threeleaderApplication.getApplyPerson()));
			if(null != user){
				//获取角色信息
				List<Role> roles = userRoleService.findRolesByUserId(user.getId());
				user.setRoles(roles);
				//返回申报信息
				result.setOthers("user", user);
			}
		}else{
			// 给客户端响应
			result.setResult(ResultVO.FAILURE);
		}
		logger.debug("GetPublish -->result: " + result.toString());
		return result.toString();
	}
	
	/**
	 * 根据项目种类Id、当前用户信息分页查询申报列表
	 * 
	 * @param categoryId 项目种类ID
	 * @return
	 */
	@RequestMapping(value = "/getApplicationsByCategoryId/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getApplicationsByCategoryId(Long categoryId) {
		logger.debug("GetApplicationsByCategoryId --> categoryId: " + categoryId);
		ResultVO<ThreeleaderApplication> resultVO = new ResultVO<ThreeleaderApplication>();
		//获取当前用户信息
		User currentUser = getCurrentUser();
		try {
			logger.debug("CurrentUser's ID is :" + currentUser.getId());
			Map<String, Object> queryMap = new HashMap<String, Object>();
			
			queryMap.put("categoryId", categoryId);

			Page<ThreeleaderApplication> page = threeleaderApplicationServiceImpl.getByUserAndCategoryId(currentUser,queryMap);

			resultVO.setPage(page);
			resultVO.setLists(page.getList());
		} catch (Exception e) {
			logger.debug("GetApplicationsByCategoryId Exception --> : " + e.getMessage());
			e.printStackTrace();
			//返回失败
			resultVO.setResult(ResultVO.FAILURE);
		}
		logger.debug("GetApplicationsByCategoryId -->result:" + resultVO.getResult());
		return resultVO.toString();
	}
	
	// 根据项目类别设置相应的管理人员
	private String getAssigneeByProjectCategory(String categoryId) {
		if (categoryId.equals(ProjectConstants.CATEGORY.CoolMarts)) {
			return officer_CoolMarts;
		} else if (categoryId.equals(ProjectConstants.CATEGORY.CamTalk)) {
			return officer_CamTalk;
		} else {
			return officer_Lottery;
		}
	}
	
}
