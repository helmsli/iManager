package com.xinwei.process.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xinwei.process.dao.DataCreateInfoMapper;
import com.xinwei.process.dao.DataPermissionMapper;
import com.xinwei.process.dao.DepartleaderPublishMapper;
import com.xinwei.process.dao.PublishApplyPersonMapper;
import com.xinwei.process.entity.AssignPerson;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.DataCreateInfo;
import com.xinwei.process.entity.DataInfo;
import com.xinwei.process.entity.DataPermission;
import com.xinwei.process.entity.DepartleaderPublish;
import com.xinwei.process.entity.PublishApplyPerson;
import com.xinwei.process.service.DepartleaderPublishService;
import com.xinwei.process.service.ProjectService;
import com.xinwei.security.dao.UserRoleDao;
import com.xinwei.security.entity.User;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.ListUtil;
import com.xinwei.util.page.Page;

@Service
public class DepartleaderPublishServiceImpl implements
		DepartleaderPublishService {

	@Resource
	private UserRoleDao userRoleDao;
	@Resource
	private DepartleaderPublishMapper departleaderPublishDao;
	@Resource
	private PublishApplyPersonMapper publishApplyPersonDao;
	@Resource
	private DataCreateInfoMapper dataCreateInfoDao;
	@Resource
	private DataPermissionMapper DataPermissionDao;
	@Resource
	private XwSysSeqService xwSysSeqService;
	@Resource
	private ProjectService projectService;//项目服务
	
	
	@Value("${roleId_departLeader}")
	private Long roleId_departLeader;// 项目经理角色ID
	
	
	private final String PUBLISH_SEQ = "departleader_publish_seq";//部门经理发布编号
	private Logger logger = LoggerFactory.getLogger(DepartleaderPublishServiceImpl.class);
	/**
	 * 创建一个发布信息
	 */
	@Override
	public Long save(DepartleaderPublish departleaderPublish) {
		// 生成项目编号
		Long publishSeqCode = xwSysSeqService.getXwSequence(PUBLISH_SEQ, 1)
				.getStartSequence();
		// 设置部门领导发布编号
		departleaderPublish.setPublishId(publishSeqCode);
		// 获取当前时间
		Date now = new Date(System.currentTimeMillis());
		// 设置发布时间
		departleaderPublish.setCreateTime(now);
		//保存到数据库
		departleaderPublishDao.insert(departleaderPublish);
		// 保存该发布的创建信息
		//saveDataCreateInfo(departleaderPublish);
		//保存该发布的数据可见权限信息
		//saveDataPermission(departleaderPublish);
		//savePublishApplyPerson(departleaderPublish);
		return publishSeqCode;
	}

	@Override
	public DepartleaderPublish getById(Long publishId) {
		return departleaderPublishDao.selectByPrimaryKey(publishId);
	}

	@Override
	public Page<DepartleaderPublish> getList() {
		Page<DepartleaderPublish> page = new Page<DepartleaderPublish>(departleaderPublishDao.countAll());
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(departleaderPublishDao.selectAllByPage(map));
		return page;
	}


	@Override
	public Page<DepartleaderPublish> getListByConditions(User user,
			Map<String, Object> queryMap) {
		Page<DepartleaderPublish> page = new Page<DepartleaderPublish>(
				dataCreateInfoDao.countByConditions(queryMap));
		queryMap.put("startRow", page.getStartRow());
		queryMap.put("pageSize", page.getPageSize());
		// 查询dataCreateInfo表，获取dataID列表
		List<String> dataIdList = dataCreateInfoDao
				.selectListByConditions(queryMap);
		if(null!=dataIdList && !dataIdList.isEmpty()){
			
			List<Long> publishIdList = ListUtil.fromStringToLongList(dataIdList);
			// 根据dataIdList(发布ID列表)获取发布集合
			List<DepartleaderPublish> lists = departleaderPublishDao
					.selectByIdList(publishIdList,DepartleaderPublish.DATA9_ALLOW_APPLICATION);
			page.setList(lists);
		}
		return page;
	}
	
	/**
	 * 根据当前用户查询所有的待申报列表
	 */
	@Override
	
	public Page<DepartleaderPublish> getApplyListByCategoryId(User currentUser,
			Map<String, Object> queryMap) {
		
		String Data9_type = DepartleaderPublish.DATA9_ALLOW_APPLICATION;
		queryMap.put("extData1", DataPermission.PRIVILEGE_canReadWrite);
		if(queryMap.containsKey(DepartleaderPublish.DATA9_QUERY_KEY))
		{
			Data9_type = queryMap.get(DepartleaderPublish.DATA9_QUERY_KEY).toString();
			//项目优化，填写第三方评估
			if(Data9_type.equalsIgnoreCase(DepartleaderPublish.DATA9_NEED_optimize))
			{
				queryMap.put("extData1", DataPermission.PRIVILEGE_ThreeEval);
			}
		}
		
		Page<DepartleaderPublish> page = new Page<DepartleaderPublish>(
				DataPermissionDao.countByConditions(queryMap));
		queryMap.put("startRow", page.getStartRow());
		queryMap.put("pageSize", page.getPageSize());
		// 查询dataPermission表，获取dataID列表
		List<String> dataIdList = DataPermissionDao
				.selectListByConditions(queryMap);	
		logger.debug("query optimize1:" + dataIdList.toString());
		/*
		//移除很名单中的数据
		if(null!=dataIdList && !dataIdList.isEmpty())
		{
			Map<String, Object> queryBlackListMap = new HashMap<String, Object>();
			queryBlackListMap.put("categoryId", queryMap.get("categoryId"));
			queryBlackListMap.put("dataType", queryMap.get("dataType"));
			queryBlackListMap.put("permissionType", DataPermission.PERMISSIONTYPE_User_Black);
			queryBlackListMap.put("permissionId", currentUser.getId().toString());
			
			 page = new Page<DepartleaderPublish>(
					DataPermissionDao.countByConditions(queryBlackListMap));
			 queryBlackListMap.put("startRow", page.getStartRow());
			 queryBlackListMap.put("pageSize", 1000);
			// 查询dataPermission表，获取dataID列表
			List<String> balckList = DataPermissionDao
					.selectListByConditions(queryBlackListMap);
			logger.debug("query optimize2:" + dataIdList.toString());
			dataIdList.removeAll(balckList);
		}
		*/
		logger.debug("query optimize3:" + dataIdList.toString());
		if(null!=dataIdList && !dataIdList.isEmpty()){
			
			
			List<Long> publishIdList = ListUtil.fromStringToLongList(dataIdList);
			// 根据dataIdList(发布ID列表)获取发布集合
			
			
			
			List<DepartleaderPublish> lists = departleaderPublishDao
					.selectByIdList(publishIdList,Data9_type);
			page.setList(lists);
		}
		return page;
	}
	
	@Override
	public Page<DepartleaderPublish> getToThreeLeaderByCategoryId(
			Map<String, Object> queryMap) {
		Long count = dataCreateInfoDao.countByConditions(queryMap);
		Page<DepartleaderPublish> page = new Page<DepartleaderPublish>(count);
		queryMap.put("startRow", page.getStartRow());
		queryMap.put("pageSize", page.getPageSize());
		// 查询dataCreateInfo表，获取dataID列表
		List<String> dataIdList = dataCreateInfoDao.selectListByConditions(queryMap);
		if(null!=dataIdList && !dataIdList.isEmpty()){
			
			List<Long> publishIdList =ListUtil.fromStringToLongList(dataIdList);
			//根据dataIdList(发布ID列表)获取发布集合
			List<DepartleaderPublish> lists = departleaderPublishDao.selectByIdList(publishIdList,DepartleaderPublish.DATA9_ALLOW_APPLICATION);
			page.setList(lists);
			
		}
		return page;
	}

	
	/*
	 *  保存创建信息
	 */
	private void saveDataCreateInfo(DepartleaderPublish departleaderPublish) {
		DataCreateInfo dataCreateInfo = new DataCreateInfo();
		dataCreateInfo.setCategoryId(departleaderPublish.getCategoryId());
		dataCreateInfo.setDataId(departleaderPublish.getPublishId().toString());
		dataCreateInfo.setDataType(DataInfo.DATATYPE_PUBLISH);
		dataCreateInfo.setCreatorId(Long.parseLong(departleaderPublish.getCreatePerson()));
		dataCreateInfo.setCreateTime(departleaderPublish.getCreateTime());
		dataCreateInfo.setExtData3(departleaderPublish.getData3());//标识指定给项目经理或三级部门经理
		dataCreateInfoDao.insert(dataCreateInfo);
	}

	/*
	 * 保存权限信息
	 */
	private void saveDataPermission(DepartleaderPublish publish) {
		Gson gson = new GsonBuilder()
				.serializeNulls()//序列化null
				.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
				.create();
		logger.debug("data1: "+ publish.getData1());
		//获取指定人员列表
		List<AssignPerson> assignList = gson.fromJson(publish.getData1(), new TypeToken<List<AssignPerson>>() {}.getType());
		List<AssignPerson> threeEvalList = gson.fromJson(publish.getData3(), new TypeToken<List<AssignPerson>>() {}.getType());
		
		if (null != assignList && !assignList.isEmpty()) {
			List<Long> userIdList = new ArrayList<Long>();
			List<Long> roleIdList = new ArrayList<Long>();
			for(AssignPerson assignPerson : assignList){
				//如果指定为角色
				if(AssignPerson.Role == assignPerson.getRoleType()){	
					//获取该角色下的所有用户ID列表
					List<User> userList = userRoleDao.findAllUsersByRoleId(assignPerson.getId());
					for(User user : userList){
						userIdList.add(user.getId());
					}
					roleIdList.add(assignPerson.getId());
				}else{
					//指定人员
					userIdList.add(assignPerson.getId());
				}			
			}
			for(Long userId : userIdList){
				DataPermission dataPermission = new DataPermission();
				dataPermission.setDataId(publish.getPublishId().toString());
				dataPermission.setDataType(DataInfo.DATATYPE_PUBLISH);
				dataPermission.setCategoryId(publish.getCategoryId());
				dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_USER);
				dataPermission.setPermissionId(userId.toString());
				
				if(publish.isDingxiang())
				{
					dataPermission.setPrivilegeThrreeEval();
				}
				
				DataPermissionDao.insert(dataPermission);
			}
			for(Long roleId : roleIdList){
				DataPermission dataPermission = new DataPermission();
				dataPermission.setDataId(publish.getPublishId().toString());
				dataPermission.setDataType(DataInfo.DATATYPE_PUBLISH);
				dataPermission.setCategoryId(publish.getCategoryId());
				dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_ROLE);
				dataPermission.setPermissionId(roleId.toString());		
				
				DataPermissionDao.insert(dataPermission);
			}
			
		}
		//增加部门经理和的权限 roleId_departLeader
		{
			List<Long> userIdList = new ArrayList<Long>();
			List<Long> roleIdList = new ArrayList<Long>();
			for(AssignPerson assignPerson : threeEvalList){
			//如果指定为角色
			roleIdList.add(roleId_departLeader);
					
			}
		}
		
		
		
		//增加第三方数据查询和监管；
		if (null != threeEvalList && !threeEvalList.isEmpty()) {
			List<Long> userIdList = new ArrayList<Long>();
			List<Long> roleIdList = new ArrayList<Long>();
			for(AssignPerson assignPerson : threeEvalList){
				//如果指定为角色
				if(AssignPerson.Role == assignPerson.getRoleType()){					
					roleIdList.add(assignPerson.getId());
				}else{
					//指定人员
					userIdList.add(assignPerson.getId());
				}			
			}
			for(Long userId : userIdList){
				DataPermission dataPermission = new DataPermission();
				dataPermission.setDataId(publish.getPublishId().toString());
				dataPermission.setDataType(DataInfo.DATATYPE_PUBLISH);
				dataPermission.setCategoryId(publish.getCategoryId());
				dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_USER);
				dataPermission.setPermissionId(userId.toString());
				//如果不是优化项目，不需要填写这个字段
				if(publish.isDingxiang())
				{
					dataPermission.setPrivilegeThrreeEval();
				}
				DataPermissionDao.insert(dataPermission);
			}
			for(Long roleId : roleIdList){
				DataPermission dataPermission = new DataPermission();
				dataPermission.setDataId(publish.getPublishId().toString());
				dataPermission.setDataType(DataInfo.DATATYPE_PUBLISH);
				dataPermission.setCategoryId(publish.getCategoryId());
				dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_ROLE);
				dataPermission.setPermissionId(roleId.toString());
				if(publish.isDingxiang())
				{
					dataPermission.setPrivilegeThrreeEval();
				}
				DataPermissionDao.insert(dataPermission);
			}
		}//end of if (null != threeEvalList 
		//增加创建者的数据权限
		DataPermission dataPermission = new DataPermission();
		dataPermission.setDataId(publish.getPublishId().toString());
		dataPermission.setDataType(DataInfo.DATATYPE_PUBLISH);
		dataPermission.setCategoryId(publish.getCategoryId());
		dataPermission.setPermissionType(DataPermission.PERMISSIONTYPE_USER);
		dataPermission.setPermissionId(publish.getCreatePerson());
		dataPermission.setExtData1(dataPermission.PRIVILEGE_Owner);
		DataPermissionDao.insert(dataPermission);
		
		
	}
		

	/*
	 * 保存发布、申报人信息
	 */
	private void savePublishApplyPerson(DepartleaderPublish publish) {
		Gson gson = new GsonBuilder()
				.serializeNulls()//序列化null
				.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
				.create();
		logger.debug("data1: "+ publish.getData1());
		//获取指定人员列表
		List<AssignPerson> assignList = gson.fromJson(publish.getData1(), new TypeToken<List<AssignPerson>>() {}.getType());
		// 判断是否指定为角色下全部用户
		if (null != assignList && !assignList.isEmpty()) {
			List<Long> userIdList = new ArrayList<Long>();
			for(AssignPerson assignPerson : assignList){
				//如果指定为角色下所有用户
				if(AssignPerson.Role == assignPerson.getRoleType()){	
					//获取该角色下的所有用户ID列表
					List<User> userList = userRoleDao.findAllUsersByRoleId(assignPerson.getId());
					for(User user : userList){
						userIdList.add(user.getId());
					}
				}else{
					userIdList.add(assignPerson.getId());
				}			
			}
			for(Long userId : userIdList){
				PublishApplyPerson publishApplyPerson = new PublishApplyPerson();
				publishApplyPerson.setPublishId(publish.getPublishId());
				publishApplyPerson.setApplyPersonId(userId);
				publishApplyPersonDao.insert(publishApplyPerson);
			}
		}
	}

	@Override
	public Long saveOptimize(DepartleaderPublish departleaderPublish) {
		// TODO Auto-generated method stub
		departleaderPublishDao.updateOptimize(departleaderPublish);
		DataPermission dataPermission = new DataPermission();
		dataPermission.setCategoryId(departleaderPublish.getCategoryId());
		dataPermission.setDataType(dataPermission.DATATYPE_PUBLISH);
		dataPermission.setDataId(departleaderPublish.getPublishId().toString());
		//设置为能够读取
		dataPermission.setExtData1(dataPermission.PRIVILEGE_canReadWrite);
		DataPermissionDao.updateToApplication(dataPermission);
		return departleaderPublish.getPublishId();
	}

	@Override
	public Page<DepartleaderPublish> selectByDistrict(User user, Map<String, Object> queryMap) {
		// TODO Auto-generated method stub
		Page<DepartleaderPublish> page = new Page<DepartleaderPublish>(departleaderPublishDao.countByDistrict(queryMap));
		queryMap.put("startRow", page.getStartRow());
		queryMap.put("pageSize", page.getPageSize());	
		page.setList(departleaderPublishDao.selectByDistrict(queryMap));
		return page;
	}
	
}
