package com.xinwei.process.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xinwei.process.constant.ApprovedConstants;
import com.xinwei.process.constant.ChangeConstants;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.Conclusion;
import com.xinwei.process.entity.DataInfo;
import com.xinwei.process.entity.DataPermission;
import com.xinwei.process.entity.MonthlyCheck;
import com.xinwei.process.entity.MonthlyReport;
import com.xinwei.process.entity.ReportActivity;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.process.service.RoleServiceTypeService;
import com.xinwei.process.service.impl.ApplicationExportExcel;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.date.DateUtil;
import com.xinwei.util.excel.POIExcel;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/commonbiz")
public class CommonBizController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(CommonBizController.class);

	@Autowired
	private CommonBizService service;
	@Autowired
	private RoleServiceTypeService roleServiceTypeService;
	@Value("${uploadPath}")
	private String uploadPath; 
	@Value("${roleId_departLeader}")
	private Long roleId_departLeader;// 项目经理角色ID
	
	@Value("${roleId_threeLevelsLeader}")
	private Long roleId_threeLevelsLeader;// 项目经理角色ID
	
	
	
	@Value("${roleId_committee}")
	private Long roleId_committee;// 决策委员会组ID
	
	private Gson gson = new GsonBuilder()
			.serializeNulls()//序列化null
			.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
			.create();
	@RequestMapping(value = "/reloadCommonBizCache", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String reloadCommonBizCache() {
		roleServiceTypeService.reset(null);
		ResultVO<CommonBiz> result = new ResultVO<>();
		result.setResult(ResultVO.SUCCESS);
		return result.toString();
	}

	// 根据DataId查询
	@RequestMapping(value = "/getByDataId", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getByDataId(Long dataId) {
		ResultVO<Object> result = new ResultVO<>();
		try {
			CommonBiz commonBiz = service.selectByPrimaryKey(dataId.toString());
			result.setOthers("commonBiz", commonBiz);
			
		} catch (Exception e) {
			result.setResult(ResultVO.FAILURE);
			e.printStackTrace();
		}
		return result.toString();
	}
	// 根据projectId查询所有CommonBiz对象
	@RequestMapping(value = "/getCommonBizData", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getCommonBizData(Long projectId) {
		ResultVO<CommonBiz> result = new ResultVO<>();
		// 获取当前登录用户信息
		User currentUser = getCurrentUser();
		List<Role> dbIdRoles = currentUser.getRoles();
		int notPermession = 0;
		// 获取权限列表
		List<String> permessions = new java.util.ArrayList<>();
		for (Role role : dbIdRoles) {
			List<String> tempPermession = roleServiceTypeService
					.selectServiceTypeByCache(role.getId().intValue());
			permessions.addAll(tempPermession);
		}

		List<CommonBiz> commonBizList = service
				.getCommonBizByProjectId(projectId);
		logger.debug(commonBizList.toString());
		List<CommonBiz> filterCommonBizList = new ArrayList<CommonBiz>();
		List<CommonBiz> secfilterCommonBizList = new ArrayList<CommonBiz>();

		for (CommonBiz commonBiz : commonBizList) {
			String serviceType = commonBiz.getServiceType();
			logger.debug(serviceType + ":" + permessions + ":" + dbIdRoles);
			if (permessions.contains(serviceType)) {
				filterCommonBizList.add(commonBiz);
			} else {
				notPermession++;
			}
		}

		// 如果是 评审指标 或者 项目初审 则设置data1空
		for (CommonBiz biz : filterCommonBizList) {
			if (biz.getServiceType().equals("pszb")
					|| biz.getServiceType().equals("xmcs")) {
				biz.setData1("");
				secfilterCommonBizList.add(biz);
			} else {
				secfilterCommonBizList.add(biz);
			}
		}
		if (filterCommonBizList.size() > 0) {
			logger.info("secfilterCommonBizList大小:  "
					+ secfilterCommonBizList.size());
		} else if (filterCommonBizList.size() == 0) {
			logger.info("secfilterCommonBizList大小：无数据");
		}
		result.setOthers("commonBizList", secfilterCommonBizList);
		result.setOthers("notallow", notPermession);
		return result.toString();
	}

	// 根据项目ID和业务类型查询
	@RequestMapping(value = "/getCommonBizDatas", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getCommonBizDatas(Long projectId,
			String serviceType) {
		ResultVO<Object> result = new ResultVO<>();
		try {
			List<CommonBiz> commonBizList = service
					.selectByProjectIdAndServiceType(projectId, serviceType);
			if (commonBizList != null) {
				result.setOthers("commonBizList", commonBizList);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.FAILURE);
			e.printStackTrace();
		}
		return result.toString();
	}

	@RequestMapping(value = "/getMonthlyReport/list", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getMonthlyReportList(Long categoryId,String serviceType) {
		ResultVO<CommonBiz> result = new ResultVO<>();
		try {
			//获取当前登录用户信息		
			User currentUser = getCurrentUser();
			//判断如果用户不为空
			if (null != currentUser) {
				//获取当前用户ID
				Long userId = currentUser.getId();
				logger.debug("Current User's ID is : " + userId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("projectCategory", categoryId);
				queryMap.put("dataType", serviceType);			
				queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);				
				queryMap.put("permissionId", currentUser.getId().toString());				
				//获取当前用户角色,
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
				//根据当前用户信息分页获取数据监测
				Page<CommonBiz> page = service.selectMonthReportList(currentUser,
						queryMap);
				result.setPage(page);
				result.setLists(page.getList());	
			}else{
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.EXCEPTION);
			e.printStackTrace();
		}
		return result.toString();
	}


	@RequestMapping(value = "/application/approve", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String applicationApprove(CommonBiz commonBiz) {
		ResultVO<Object> result = new ResultVO<>();
		logger.debug(commonBiz.toString());
		try {
			//获取当前登录用户信息		
			User currentUser = getCurrentUser();
			//判断如果用户不为空
			if (null != currentUser) {
				CommonBiz oldCommonBiz = service.selectByPrimaryKey(commonBiz.getDataId());
				oldCommonBiz.setStatus(commonBiz.getStatus());
				
				oldCommonBiz.setResult(commonBiz.getResult());
				oldCommonBiz.setUpdatePerson(currentUser.getFirstname());
				oldCommonBiz.setUpdateTime(Calendar.getInstance().getTime());
				oldCommonBiz.setData8(commonBiz.getData8());
				oldCommonBiz.setExtActivitiInfo(oldCommonBiz.getCreatePerson());
				service.update(oldCommonBiz);
				result.setResult(ResultVO.SUCCESS);
				
			}else{
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.EXCEPTION);
			e.printStackTrace();
		}
		return result.toString();
	}

	
	@RequestMapping(value = "/application/conclude", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String applicationConclude(CommonBiz commonBiz) {
		ResultVO<Object> result = new ResultVO<>();
		logger.debug(commonBiz.toString());
		try {
			//获取当前登录用户信息		
			User currentUser = getCurrentUser();
			//判断如果用户不为空
			if (null != currentUser) {
			     
				CommonBiz oldCommonBiz = service.selectByPrimaryKey(commonBiz.getDataId());
				logger.debug(gson.toJson(oldCommonBiz));
				Conclusion oldconclusion=new Conclusion();
				try {
					oldconclusion= gson.fromJson(oldCommonBiz.getData9(), Conclusion.class);
					if(oldconclusion==null)
					{
						 oldconclusion=new Conclusion();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					oldconclusion=new Conclusion();
					e.printStackTrace();
				}
				
				
				try {
					Conclusion conclusion = gson.fromJson(commonBiz.getData9(), Conclusion.class);
					oldconclusion.setApplyTime(Calendar.getInstance().getTime());
					oldconclusion.setApplyFile(conclusion.getApplyFile());
					oldconclusion.setApplyOthers(conclusion.getApplyOthers());
					oldCommonBiz.setData9(gson.toJson(oldconclusion));
					if(StringUtils.isNoneBlank(commonBiz.getStatus()))
					{
						oldCommonBiz.setStatus(commonBiz.getStatus());	
					}
					else
					{
						oldCommonBiz.setStatus(ApprovedConstants.ApplicationResult.status_ApplyConclude);
					}
					service.update(oldCommonBiz);
					result.setResult(ResultVO.SUCCESS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					result.setResult(ResultVO.EXCEPTION);
				}
				
				
			}else{
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.EXCEPTION);
			e.printStackTrace();
		}
		return result.toString();
	}

	
	@RequestMapping(value = "/countMonthlyReport", method = { RequestMethod.GET,
			RequestMethod.POST })
	
	public @ResponseBody String countMonthlyReport(String categoryId,String serviceType) {
		ResultVO<CommonBiz> result = new ResultVO<>();
		//获取登录用户名的地区和ID
		
		return result.toString();
	}
	
	
	
	
	@RequestMapping(value = "/countApplicationService", method = { RequestMethod.GET,
			RequestMethod.POST })
	
	public @ResponseBody String countApplicationService(String categoryId,String serviceType) {
		ResultVO<CommonBiz> result = new ResultVO<>();
		//获取当前登录用户信息		
		User currentUser = getCurrentUser();
		//判断如果用户不为空
		try {
			if (null != currentUser) {
				Long userId = currentUser.getId();
				logger.debug("Current User's ID is : " + userId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("projectCategory", categoryId);
				queryMap.put("serviceType", serviceType);	
				queryMap.put("create_time", new Date());
				//queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);				
				//queryMap.put("permissionId", currentUser.getId().toString());				
				//获取当前用户角色,
				//0 -- projectmanager 1-- manager  2 -- threeleader
				int isRoleManager = 0;
				List<Long> roles = currentUser.getRoleIds();
				if (roles.size() > 0) {
					
					for (Long rolesid : roles) {
						if(roleId_departLeader.longValue()==rolesid.longValue()||roleId_committee.longValue()==rolesid.longValue())
						{
							isRoleManager=1;
							
						}
						if(roleId_threeLevelsLeader.longValue()==rolesid.longValue())
						{
							//
							isRoleManager=2;
							break;
						}
					}

				}

				if(isRoleManager>0)
				{
					//获取当前用户的所在地区
					String district = currentUser.getAddress();
					if(isRoleManager==2)
					{
						district=ApprovedConstants.ApplicationDistrict.district_ding;
					}
					
					if(!ChangeConstants.isAllDistrict(district))
					{
						queryMap.put("serviceOwner", district);				
						
					}
					result.setOthers(ChangeConstants.Application_Owner,"0");
				}
				//get crete by myself
				else
				{
					queryMap.put("createPerson",currentUser.getPhone());				
					result.setOthers(ChangeConstants.Application_Owner,"1");
				}
				List<CommonBiz>commonBizs =service.countServicePerson(this.getCurrentUser(), queryMap);
				result.setLists(commonBizs);
				result.setResult(result.SUCCESS);
			}//if (null != currentUser) {
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
		
		return result.toString();
	}
	
	@RequestMapping(value = "/getApplication/listByDistrict", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getApplicationListByDistrict(String categoryId,String serviceType,String status,String region,String pType,String keshi) {
		ResultVO<CommonBiz> result = new ResultVO<>();
		try {
			//获取当前登录用户信息		
			User currentUser = getCurrentUser();
			//判断如果用户不为空
			if (null != currentUser) {
				Long userId = currentUser.getId();
				logger.debug("Current User's ID is : " + userId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("projectCategory", categoryId);
				queryMap.put("serviceType", serviceType);
				//queryMap.put("userid", userId);
				if(!StringUtils.isBlank(status))
				{
					queryMap.put("status", status);	
				}				
				if(!StringUtils.isBlank(pType))
				{
					queryMap.put("taskId", pType);
				}
				if(!StringUtils.isBlank(keshi))
				{
					queryMap.put("data1", keshi);
				}
				//queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);				
				//queryMap.put("permissionId", currentUser.getId().toString());				
				
				
				
				//获取当前用户角色,
				//0 -- projectmanager 1-- manager  2 -- threeleader
				int isRoleManager = 0;
				List<Long> roles = currentUser.getRoleIds();
				if (roles.size() > 0) {
					
					for (Long rolesid : roles) {
						if(roleId_departLeader.longValue()==rolesid.longValue()||roleId_committee.longValue()==rolesid.longValue())
						{
							isRoleManager=1;
							
						}
						if(roleId_threeLevelsLeader.longValue()==rolesid.longValue())
						{
							//
							isRoleManager=2;
							break;
						}
					}

				}
				
				if(isRoleManager>0)
				{
					//获取当前用户的所在地区
					String district = currentUser.getAddress();
					if(isRoleManager==2)
					{
						district=ApprovedConstants.ApplicationDistrict.district_ding;
					}
					
					if(!ChangeConstants.isAllDistrict(district))
					{
						queryMap.put("serviceOwner", district);				
						
					}
					else
					{
						//如果按照地区查询，放入地区信息
						if(!StringUtils.isBlank(region))
						{
							queryMap.put("serviceOwner", region);
						}
					}
					result.setOthers(ChangeConstants.Application_Owner,"0");
				}
				//get crete by myself
				else
				{
					queryMap.put("userid", userId);
					queryMap.put("createPerson",currentUser.getPhone());				
					result.setOthers(ChangeConstants.Application_Owner,"1");
				}
				
				
				//根据当前用户信息分页获取数据监测
				Page<CommonBiz> page = service.selectQueryBizPage(currentUser,
						queryMap);
				result.setPage(page);
				result.setLists(page.getList());	
			}else{
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.EXCEPTION);
			e.printStackTrace();
		}
		return result.toString();
	}
   
	/**
	 * 第三方下载申报数据
	 * @param categoryId
	 * @param serviceType
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getApplication/downloadApp", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String downloadAppByDistrict(HttpServletRequest request,String categoryId,String serviceType,String status) {
		ResultVO<CommonBiz> result = new ResultVO<>();
		try {
			if(StringUtils.isBlank(categoryId))
			{
				categoryId = "canlian";	
			}
			if(StringUtils.isBlank(serviceType))
			{
				serviceType="application";
			}
			
			
			
			//获取当前登录用户信息		
			User currentUser = getCurrentUser();
			//判断如果用户不为空
			if (null != currentUser) {
				
				
				
				Long userId = currentUser.getId();
				logger.debug("Current User's ID is : " + userId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("projectCategory", categoryId);
				queryMap.put("serviceType", serviceType);
				
				if(!StringUtils.isBlank(status))
				{
					queryMap.put("status", status);	
				}
				
				//queryMap.put("permissionType", DataPermission.PERMISSIONTYPE_USER);				
				//queryMap.put("permissionId", currentUser.getId().toString());				
				
				
				
				//获取当前用户角色,
				//0 -- projectmanager 1-- manager  2 -- threeleader
				int isRoleManager = 0;
				List<Long> roles = currentUser.getRoleIds();
				if (roles.size() > 0) {
					
					for (Long rolesid : roles) {
						if(roleId_departLeader.longValue()==rolesid.longValue()||roleId_committee.longValue()==rolesid.longValue())
						{
							isRoleManager=1;
							
						}
						if(roleId_threeLevelsLeader.longValue()==rolesid.longValue())
						{
							//
							isRoleManager=2;
							break;
						}
					}

				}
				
				if(isRoleManager>0)
				{
					//获取当前用户的所在地区
					String district = currentUser.getAddress();
					if(isRoleManager==2)
					{
						district=ApprovedConstants.ApplicationDistrict.district_ding;
					}
					
					if(!ChangeConstants.isAllDistrict(district))
					{
						queryMap.put("serviceOwner", district);				
						
					}
					result.setOthers(ChangeConstants.Application_Owner,"0");
				}
				//get crete by myself
				else
				{
					queryMap.put("userid", userId);
					queryMap.put("createPerson",currentUser.getPhone());				
					result.setOthers(ChangeConstants.Application_Owner,"1");
				}
				
				
				//根据当前用户信息分页获取数据监测
				Page<CommonBiz> page = service.selectQueryBizNoPage(currentUser,
						queryMap);
				result.setPage(page);
				//result.setLists(page.getList());
				
				
				//获取模版文件目录
				String templateFile = request.getSession().getServletContext()
						.getRealPath(uploadPath)+File.separator +ApplicationExportExcel.getInstance().getDefaultAppTemplateFile();
				//设置上传目录，用于获取申报书等
				ApplicationExportExcel.getInstance().setUploadDir(request.getSession().getServletContext()
						.getRealPath(uploadPath));
				String tempRelativeFile = ApplicationExportExcel.getInstance().getTempRelativeFilePath();
				String destPath = request.getSession().getServletContext()
						.getRealPath(ApplicationExportExcel.getInstance().getApplicationTempPath())+ File.separator+tempRelativeFile;
				String zipDestFile = ApplicationExportExcel.getInstance().createApplicationReport(page.getList(),destPath, templateFile);
				result.setOthers("exportApplicationFile", zipDestFile);
				
				
				
				
			}else{
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.EXCEPTION);
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 第三方下载申报数据
	 * @param categoryId
	 * @param serviceType
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/getApplication/downloadSelected", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String downloadAppBySelected(HttpServletRequest request,String categoryId,String serviceType,String selectedIds) {
		ResultVO<CommonBiz> result = new ResultVO<>();
		try {
			if(StringUtils.isBlank(categoryId))
			{
				categoryId = "canlian";	
			}
			if(StringUtils.isBlank(serviceType))
			{
				serviceType="application";
			}
			
			
			
			//获取当前登录用户信息		
			User currentUser = getCurrentUser();
			//判断如果用户不为空
			if (null != currentUser) {
				
				
				
				Long userId = currentUser.getId();
				logger.debug("Current User's ID is : " + userId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("projectCategory", categoryId);
				queryMap.put("serviceType", serviceType);
								
				
				//根据当前用户信息分页获取数据监测
				String [] ids = StringUtils.split(selectedIds,",");
				
				List<String> idList = java.util.Arrays.asList(ids);
				List<CommonBiz> resultBizs = service.getCommonBizMapper().selectByIdList(idList);
				
				//result.setLists(page.getList());				
				
				//获取模版文件目录
				String templateFile = request.getSession().getServletContext()
						.getRealPath(uploadPath)+File.separator +ApplicationExportExcel.getInstance().getDefaultAppTemplateFile();
				//设置上传目录，用于获取申报书等
				ApplicationExportExcel.getInstance().setUploadDir(request.getSession().getServletContext()
						.getRealPath(uploadPath));
				String tempRelativeFile = ApplicationExportExcel.getInstance().getTempRelativeFilePath();
				String destPath = request.getSession().getServletContext()
						.getRealPath(ApplicationExportExcel.getInstance().getApplicationTempPath())+ File.separator+tempRelativeFile;
				String zipDestFile = ApplicationExportExcel.getInstance().createApplicationReport(resultBizs,destPath, templateFile);
				result.setOthers("exportApplicationFile", zipDestFile);
				
				
				
				
			}else{
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.EXCEPTION);
			e.printStackTrace();
		}
		return result.toString();
	}
	
	
	// 根据项目类别和业务类型以及当前用户角色信息查询
		@RequestMapping(value = "/getByCatetgoryAndServiceType/list", method = { RequestMethod.GET,
				RequestMethod.POST })
		public @ResponseBody String getByCatetgoryAndServiceType(Long categoryId,String serviceType) {
			ResultVO<CommonBiz> result = new ResultVO<>();
			try {
				//获取当前登录用户信息
				User currentUser = getCurrentUser();
				//判断如果用户不为空
				if (null != currentUser) {
					//获取当前用户ID
					Long userId = currentUser.getId();
					logger.debug("Current User's ID is : " + userId);
					Map<String, Object> queryMap = new HashMap<String, Object>();
					queryMap.put("projectCategory", categoryId);
					queryMap.put("serviceType", serviceType);
					//根据当前用户信息分页获取数据监测
					Page<CommonBiz> page = service.selectByCategoryServiceTypeAndProjectName(currentUser,
							queryMap);
					result.setPage(page);
					result.setLists(page.getList());	
				}else{
					logger.debug("Current user's infomation is null");
					// 给客户端响应
					result.setResult(ResultVO.USERNULL);
				}
			} catch (Exception e) {
				result.setResult(ResultVO.EXCEPTION);
				e.printStackTrace();
			}
			return result.toString();
		}
	// 根据项目类别、业务类型、当前用户角色信息、项目名称（参数可选，支持项目名称模糊查询）查询
	@RequestMapping(value = "/getByConditions/list", produces = "text/html;charset=UTF-8",method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getByCatetgoryServiceTypeAndProjectName(HttpServletRequest request,Long categoryId,
			String serviceType,String projectName) {
		
		ResultVO<CommonBiz> result = new ResultVO<>();
		try {
			// 获取当前登录用户信息
			User currentUser = getCurrentUser();
			// 判断如果用户不为空
			if (null != currentUser) {
				// 获取当前用户ID
				Long userId = currentUser.getId();
				logger.debug("Current User's ID is : " + userId);
				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("projectName", projectName);
				queryMap.put("projectCategory", categoryId);
				queryMap.put("serviceType", serviceType);
				//分页获取数据监测信息
				Page<CommonBiz> page = service
						.selectByCategoryServiceTypeAndProjectName(currentUser,
								queryMap);
				result.setPage(page);
				result.setLists(page.getList());
				// 如果为周期报告或周期检测则进行导出
				if (ProjectConstants.State.SUBMITMONTHLYCHECK
						.equals(serviceType.trim())
						|| ProjectConstants.State.SUBMITMONTHLYREPORT
								.equals(serviceType.trim())) {
					final String type = serviceType;
					final List<CommonBiz> commonBizList = page.getList();
					//文件上传地址
					final String path = request.getSession().getServletContext()
							.getRealPath(uploadPath);
					final String annexName = DateUtil.DateToString(new Date(),"yyyy-MM-dd-HH-mm-ss")
							+ "_"
							+ currentUser.getId().toString()
							+ String.valueOf(Math.round(Math.random() * 100))
							+ ".xlsx";
					// 开启子线程进行导出
					new Thread(new Runnable() {
						@Override
						public void run() {
							
							if (ProjectConstants.State.SUBMITMONTHLYREPORT
									.equals(type.trim())) {
								// 导出周期报告
								exportMonthlyReport(commonBizList,path, annexName);
							} else {
								// 导出周期检测
								exportMonthlyCheck(commonBizList,path, annexName);
							}
						}

					}).start();
					result.setOthers("annexName", annexName);
				}
			} else {
				logger.debug("Current user's infomation is null");
				// 给客户端响应
				result.setResult(ResultVO.USERNULL);
			}
		} catch (Exception e) {
			result.setResult(ResultVO.EXCEPTION);
			e.printStackTrace();
		}
		return result.toString();
	}
	
	// 导出Excel
	private void exportMonthlyReport(List<CommonBiz> commonBizList,
			String path, String annexName) {
		try {
			String templatePath = Thread.currentThread().getContextClassLoader().getResource("").getPath()+"excel/template/monthlyReport.xlsx";
			// 获取周期报告模板
			POIExcel excel = new POIExcel(templatePath);
			int currentRow = 2;
			// 遍历周期报告列表
			for(CommonBiz commonBiz :commonBizList){
				
				// 写入项目名称
				excel.writeToExcel(0, currentRow, 0, commonBiz.getProjectName());
				// 构造周期报告对象
				MonthlyReport report = gson.fromJson(commonBiz.getData2(), MonthlyReport.class);
				// 写入特殊情况说明、创新点和亮点等列
				excel.writeToExcel(0,currentRow, 8, report.getSpecialExplain());
				excel.writeToExcel(0,currentRow, 9, report.getInnovation());
				excel.writeToExcel(0,currentRow, 10, report.getChangeConditions());
				excel.writeToExcel(0,currentRow, 11, report.getRiskDescription());
				excel.writeToExcel(0,currentRow, 12, report.getSummary());
				excel.writeToExcel(0,currentRow, 13, report.getImprovementPlan());
				
				//获取活动列表
				List<ReportActivity> activitis = gson.fromJson(commonBiz.getData1(), new TypeToken<List<ReportActivity>>() {}.getType());
				//活动数 
				int activityNumber = activitis.size(); 
				//写入活动 
				for(int i =0; i < activityNumber; i++){
					//写入活动
					writeActiviti(excel,currentRow,activitis.get(i)); 
					//行号加一
					currentRow ++;
				} 
				//如果超过一行，进行单元格合并
				if(activityNumber > 0){
					
					int startRow = currentRow-activityNumber;
					int endRow = currentRow-1;
					//合并单元格 
					excel.mergeCell(excel.getSheet(0),startRow,endRow,0,0);
					excel.mergeCell(excel.getSheet(0),startRow,endRow,8,8);
					excel.mergeCell(excel.getSheet(0),startRow,endRow,9,9);
					excel.mergeCell(excel.getSheet(0),startRow,endRow,10,10);
					excel.mergeCell(excel.getSheet(0),startRow,endRow,11,11);
					excel.mergeCell(excel.getSheet(0),startRow,endRow,12,12);
					excel.mergeCell(excel.getSheet(0),startRow,endRow,13,13);
				}
			}
			String pathName = path + File.separator + annexName;
			// 导出excel文件
			excel.writeAndClose(pathName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//写入活动
	private void writeActiviti(POIExcel excel, int currentRow, ReportActivity reportActivity) throws Exception {
		excel.writeToExcel(0,currentRow, 1, reportActivity.getId());
		excel.writeToExcel(0,currentRow, 2, reportActivity.getTime());
		excel.writeToExcel(0,currentRow, 3, reportActivity.getTitle());
		excel.writeToExcel(0,currentRow, 4, reportActivity.getContent());
		excel.writeToExcel(0,currentRow, 5, reportActivity.getProduce());
		excel.writeToExcel(0,currentRow, 6, reportActivity.getReviewTimes());
		excel.writeToExcel(0,currentRow, 7, reportActivity.getCodeTimes());
	}

	//导出周期检测
	private void exportMonthlyCheck(
			List<CommonBiz> commonBizList, String path, String annexName) {
		try {
			String templatePath = Thread.currentThread().getContextClassLoader()
					.getResource("").getPath()+"excel/template/monthlyCheck.xlsx";
			// 获取周期报告模板
			POIExcel excel = new POIExcel(templatePath);
			int currentRow = 2;
			// 遍历周期检测列表
			for(CommonBiz commonBiz :commonBizList){
				// 写入项目名称
				excel.writeToExcel(0, currentRow, 0, commonBiz.getProjectName());
				// 构造周期监测对象
				MonthlyCheck check = gson.fromJson(commonBiz.getData1(),MonthlyCheck.class);
				//写入数据
				excel.writeToExcel(0,currentRow, 1, check.getScore());
				excel.writeToExcel(0,currentRow, 2, check.getReviewNum());
				excel.writeToExcel(0,currentRow, 3, check.getFileNum());
				excel.writeToExcel(0,currentRow, 4, check.getCodeNum());
				excel.writeToExcel(0,currentRow, 5, check.getWhetherAdjust());
				excel.writeToExcel(0,currentRow, 6, check.getAdjustBefore());
				excel.writeToExcel(0,currentRow, 7, check.getAdjustAfter());
				excel.writeToExcel(0,currentRow, 8, check.getAdjustWhy());
				excel.writeToExcel(0,currentRow, 9, check.getOpinion());
				excel.writeToExcel(0,currentRow, 10, check.getExamine());
				currentRow++;
			}
			
			String pathName = path + File.separator + annexName;
			// 导出excel文件
			excel.writeAndClose(pathName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}