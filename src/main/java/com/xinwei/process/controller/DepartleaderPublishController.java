package com.xinwei.process.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.process.constant.DepartmentLeaderPublishConstants;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.ApprovalResult;
import com.xinwei.process.entity.AssignPerson;
import com.xinwei.process.entity.DataInfo;
import com.xinwei.process.entity.DataPermission;
import com.xinwei.process.entity.DepartleaderPublish;
import com.xinwei.process.entity.PublishOptimize;
import com.xinwei.process.service.DataPermissionService;
import com.xinwei.process.service.DepartleaderPublishService;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/publish")
public class DepartleaderPublishController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(DepartleaderPublishController.class);
	@Resource
	private DepartleaderPublishService departleaderPublishServiceImpl;// 部门经理发布服务
	@Resource
	private DataPermissionService dataPermissionServiceImpl;
	@Value("${roleId_committee}")
	private Long roleId_committee;// 决策委员会角色ID
	@Value("${roleId_projectManager}")
	private Long roleId_projectManager;// 项目经理角色ID
	@Value("${roleId_threeLevelsLeader}")
	private Long roleId_threeLevelsLeader;// 三级部门经理角色ID
	@Value("${roleId_departLeader}")
	private Long roleId_departLeader;

	@RequestMapping(value = "/queryPrivilege", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String queryPrivilege(String state) {
		logger.debug("queryPrivilege:" + state);
		ResultVO<Object> result = new ResultVO<>();
		List<AssignPerson> lists = new java.util.ArrayList<>();
		AssignPerson assignPerson = new AssignPerson();
		assignPerson.setRoleType(AssignPerson.Role);
		// 如果当前状态为部门经理创建发布，则需要指定项目经理，返回项目经理角色Id
		if (DepartmentLeaderPublishConstants.State.PM_BUMENJLFB_START.equalsIgnoreCase(state)) {
			assignPerson.setId(roleId_projectManager);
			result.setOthers("name", "所有组织和机构");
		} else if (DepartmentLeaderPublishConstants.State.DISPATCH_THREELEAVELSLEADER.equalsIgnoreCase(state)) {
			assignPerson.setId(roleId_threeLevelsLeader);
			result.setOthers("name", "所有部门");
		} else if (ProjectConstants.State.OFFICERAPPROVAL.equalsIgnoreCase(state)) {
			// 项目管理人员进行尽职调查并指定决策委员会
			assignPerson.setId(roleId_committee);
			result.setOthers("name", "第三方评估");

		} else {
			result.setResult(ResultVO.FAILURE);
		}
		lists.add(assignPerson);
		result.setOthers("allowPersion", lists);
		logger.debug(result.toString());
		return result.toString();
	}

	/**
	 * 部门经理发布
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/create", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String bumenjlfb(DepartleaderPublish departleaderPublish) {
		logger.debug("departleaderPublish start....:" + departleaderPublish.toString());
		ResultVO<Object> result = new ResultVO<>();
		try {
			// 获取当前登录用户信息
			User currentUser = getCurrentUser();
			// 判断如果用户不为空
			if (null != currentUser) {
				// 当前用户ID
				String userId = currentUser.getId().toString();
				// 设置发布创建用户
				departleaderPublish.setCreatePerson(userId);
				// 保存发布信息
				// 如果定向项目创建初期需要第三方评估进行相关优化
				departleaderPublish.setData10();
				if (departleaderPublish.isDingxiang()) {
					departleaderPublish.setData9(departleaderPublish.DATA9_NEED_optimize);
				}
				else
				{
					departleaderPublish.setData9(departleaderPublish.DATA9_ALLOW_APPLICATION);
				}

				Long departleaderPublishId = departleaderPublishServiceImpl.save(departleaderPublish);

				// 给客户端响应
				result.setOthers("departleaderPublishId", departleaderPublishId);
				logger.debug("bumenjlfb -->departleaderPublishId:  " + departleaderPublishId);
			} else {
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			// 给客户端响应
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("departleaderPublish --> result: " + result.toString());
		return result.toString();
	}

	/**
	 * 项目优惠信息
	 * @param departleaderPublish
	 * @return
	 */
	@RequestMapping(value = "/optimize", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String projectOptimize(DepartleaderPublish departleaderPublish) {
		logger.debug("projectOptimize start....:" + departleaderPublish.toString());
		ResultVO<Object> result = new ResultVO<>();
		// 获取当前登录用户信息
		try {
			User currentUser = getCurrentUser();
			// 判断如果用户不为空
			if (null != currentUser) {
				PublishOptimize publishOptimize = JsonUtil.fromJson(departleaderPublish.getData8(),
						PublishOptimize.class);
				publishOptimize.setUser(currentUser);
				publishOptimize.setNow();
				departleaderPublish.setData8(JsonUtil.toJson(publishOptimize));
				departleaderPublish.setData9(DepartleaderPublish.DATA9_ALLOW_APPLICATION);
				Long departleaderPublishId = departleaderPublishServiceImpl.saveOptimize(departleaderPublish);
			}
			else
				
			{
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.setResult(ResultVO.EXCEPTION);
		}					  
		logger.debug("projectOptimize --> result: " + result.toString());
		return result.toString();
	}

	/**
	 * 三级部门经理进行申报
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/threeLeaderApply", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String threeLeaderApply(Long departleaderPublishId, DepartleaderPublish departleaderPublish) {
		logger.debug("threeLeaderApply start....");
		ResultVO<Object> result = new ResultVO<>();
		try {
			// 获取当前登录用户信息
			User currentUser = getCurrentUser();
			// 判断如果用户不为空
			if (null != currentUser) {
				// 当前用户ID
				String userId = currentUser.getId().toString();
				DepartleaderPublish publish = departleaderPublishServiceImpl.getById(departleaderPublishId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("categoryId", publish.getCategoryId());
				queryMap.put("dataId", publish.getPublishId().toString());
				queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);
				queryMap.put("permissionId", userId.toString());
				// 从数据权限表中删除该条记录
				dataPermissionServiceImpl.deleteByConditions(queryMap);
				// 设置发布创建用户
				departleaderPublish.setCreatePerson(userId);
				// 保存发布信息
				Long publishId = departleaderPublishServiceImpl.save(departleaderPublish);

				// 给客户端响应
				result.setOthers("departleaderPublishId", publishId);
				logger.debug("threeLeaderApply -->publishId:  " + publishId);
			} else {
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
			// 给客户端响应
			result.setResult(ResultVO.EXCEPTION);
		}
		logger.debug("threeLeaderApply --> result: " + result.toString());
		return result.toString();
	}

	/**
	 * 获取具体的发布信息
	 * 
	 * @param publishId
	 *            发布id
	 * @return
	 */
	@RequestMapping(value = "/getPublish", produces = "text/html;charset=UTF-8", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getPublish(Long publishId) {
		logger.debug("GetPublish --> publishId: " + publishId);
		ResultVO<DepartleaderPublish> result = new ResultVO<>();
		DepartleaderPublish departleaderPublish = departleaderPublishServiceImpl.getById(publishId);
		result.setOthers("departleaderPublish", departleaderPublish);
		logger.debug("GetPublish -->result: " + result.toString());
		return result.toString();
	}

	/**
	 * 分页查看所有发布列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getList() {
		ResultVO<DepartleaderPublish> result = new ResultVO<>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		// 判断如果用户不为空
		if (null != currentUser) {

			// 获取当前用户ID
			Long userId = currentUser.getId();
			logger.debug("Current User's ID is : " + userId);
			// 是否进行申报
			result.setOthers("apply", "no");

			// 分页获取所有部门经理发布列表
			Page<DepartleaderPublish> page = departleaderPublishServiceImpl.getList();
			result.setPage(page);
			List<DepartleaderPublish> publishList = page.getList();
			result.setLists(publishList);

		} else {
			logger.debug("Current user's infomation is null");
			// 给客户端响应
			result.setResult(ResultVO.USERNULL);
		}
		return result.toString();

	}

	/**
	 * 根据类别ID分页查看发布列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getByCategoryId/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getByCategoryId(Long categoryId) {
		ResultVO<DepartleaderPublish> result = new ResultVO<>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		// 判断如果用户不为空
		if (null != currentUser) {
			// 获取当前用户ID
			Long userId = currentUser.getId();
			logger.debug("Current User's ID is : " + userId);
			result.setOthers("apply", "no");

			// 根据种类ID分页获取发布列表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("categoryId", categoryId);
			queryMap.put("dataType", DataInfo.DATATYPE_PUBLISH);
			Page<DepartleaderPublish> page = departleaderPublishServiceImpl.getListByConditions(currentUser, queryMap);
			result.setPage(page);
			result.setLists(page.getList());
		} else {
			logger.debug("Current user's infomation is null");
			// 给客户端响应
			result.setResult(ResultVO.USERNULL);
		}
		return result.toString();

	}

	/**
	 * 根据类别ID分页查看我的发布列表(指定给项目经理)
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getByCategoryCreatePerson/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getByCategoryCreatePerson(Long categoryId) {
		ResultVO<DepartleaderPublish> result = new ResultVO<>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		// 判断如果用户不为空
		if (null != currentUser) {
			// 获取当前用户ID
			Long userId = currentUser.getId();
			logger.debug("Current User's ID is : " + userId);
			result.setOthers("apply", "no");

			// 根据种类ID、创建用户ID分页获取发布列表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("creatorId", userId);
			queryMap.put("categoryId", categoryId);
			queryMap.put("dataType", DataInfo.DATATYPE_PUBLISH);
			queryMap.put("extData3", DepartleaderPublish.DISPATCH_PROJECTMANAGER);
			Page<DepartleaderPublish> page = departleaderPublishServiceImpl.getListByConditions(currentUser, queryMap);
			result.setPage(page);
			result.setLists(page.getList());
		} else {
			logger.debug("Current user's infomation is null");
			// 给客户端响应
			result.setResult(ResultVO.USERNULL);
		}
		return result.toString();

	}

	/**
	 * 根据类别ID分页查看待申报的发布列表,待申报任务列表查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getApplyList/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getApplyList(Long categoryId) {
		ResultVO<DepartleaderPublish> result = new ResultVO<>();
		try {
			// 获取当前登录用户信息
			User currentUser = getCurrentUser();
			// 判断如果用户不为空
			if (null != currentUser) {
				// 获取当前用户ID
				Long userId = currentUser.getId();
				logger.debug("Current User's ID is : " + userId);
				result.setOthers("apply", "yes");

				// 根据种类ID分页获取发布列表
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("categoryId", categoryId);
				queryMap.put("dataType", DataInfo.DATATYPE_PUBLISH);
				queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);

				List<Long> roles = currentUser.getRoleIds();
				if (roles.size() > 0) {
					StringBuilder stringRoles = new StringBuilder();
					int rolesIndex = 0;
					for (Long rolesid : roles) {
						if (rolesIndex > 0) {
							stringRoles.append(",");
						}
						stringRoles.append(rolesid);
					}

					queryMap.put("permissionRoleType", DataPermission.PERMISSIONTYPE_ROLE);
					queryMap.put("permissionRoleId", stringRoles.toString());

				}

				queryMap.put("permissionId", userId.toString());
				queryMap.put(DepartleaderPublish.DATA9_QUERY_KEY, DepartleaderPublish.DATA9_ALLOW_APPLICATION);
				
				Page<DepartleaderPublish> page = departleaderPublishServiceImpl.getApplyListByCategoryId(currentUser,
						queryMap);
				result.setPage(page);
				result.setLists(page.getList());
			} else {
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.debug(result.toString());
		return result.toString();

	}

	/**
	 * 根据类别ID分页查看待申报的发布列表,待申报任务列表查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getNeedOptimizeList/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getNeedOptimizeList(Long categoryId) {
		ResultVO<DepartleaderPublish> result = new ResultVO<>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		// 判断如果用户不为空
		if (null != currentUser) {
			// 获取当前用户ID
			Long userId = currentUser.getId();
			logger.debug("Current User's ID is : " + userId);
			result.setOthers("apply", "yes");

			// 根据种类ID分页获取发布列表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("categoryId", categoryId);
			queryMap.put("dataType", DataInfo.DATATYPE_PUBLISH);
			queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);

			List<Long> roles = currentUser.getRoleIds();
			if (roles.size() > 0) {
				StringBuilder stringRoles = new StringBuilder();
				int rolesIndex = 0;
				for (Long rolesid : roles) {
					if (rolesIndex > 0) {
						stringRoles.append(",");
					}
					stringRoles.append(rolesid);
				}

				queryMap.put("permissionRoleType", DataPermission.PERMISSIONTYPE_ROLE);
				queryMap.put("permissionRoleId", stringRoles.toString());

			}

			queryMap.put("permissionId", userId.toString());
			// 设置查询条件
			queryMap.put(DepartleaderPublish.DATA9_QUERY_KEY, DepartleaderPublish.DATA9_NEED_optimize);
			Page<DepartleaderPublish> page = departleaderPublishServiceImpl.getApplyListByCategoryId(currentUser,
					queryMap);
			result.setPage(page);
			result.setLists(page.getList());
		} else {
			logger.debug("Current user's infomation is null");
			// 给客户端响应
			result.setResult(ResultVO.USERNULL);
		}
		return result.toString();

	}

	/**
	 * 部门经理分页查看指定给三级部门经理的发布列表
	 * 
	 * @return
	 */
	@RequestMapping(value = "getToThreeLeader/list", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getToThreeLeader(Long categoryId) {
		ResultVO<DepartleaderPublish> result = new ResultVO<>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		// 判断如果用户不为空
		if (null != currentUser) {
			// 获取当前用户ID
			Long userId = currentUser.getId();
			logger.debug("Current User's ID is : " + userId);
			// 当前角色是否可以进行申报
			result.setOthers("apply", "no");

			// 根据种类ID分页获取部门经理指定给三级部门经理的发布列表
			Map<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("categoryId", categoryId);
			queryMap.put("dataType", DataInfo.DATATYPE_PUBLISH);
			queryMap.put("creatorId", userId);
			queryMap.put("extData3", DepartleaderPublish.DISPATCH_THREELEADER);
			Page<DepartleaderPublish> page = departleaderPublishServiceImpl.getToThreeLeaderByCategoryId(queryMap);
			result.setPage(page);
			result.setLists(page.getList());

		} else {
			logger.debug("Current user's infomation is null");
			// 给客户端响应
			result.setResult(ResultVO.USERNULL);
		}
		return result.toString();

	}
}
