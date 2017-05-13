package com.xinwei.process.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.dao.CommonBizMapper;
import com.xinwei.process.dao.ProjectMapper;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.service.CommonBizService;
import com.xinwei.security.entity.User;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.date.DateUtil;
import com.xinwei.util.page.Page;

@Service
public class CommonBizServiceImpl implements CommonBizService {
	private final String COMMON_BIZ_SEQ = "common_biz_seq";// 编号
	@Autowired
	private CommonBizMapper commonBizDao;
	@Autowired
	private ProjectMapper projectDao;
	@Value("${roleId_projectManager}")
	private Long roleId_projectManager;// 项目经理角色ID
	@Value("${roleId_committee}")
	private Long roleId_committee;// 决策委员会角色ID
	@Override
	public CommonBiz selectByPrimaryKey(String dataId) {
		return commonBizDao.selectByPrimaryKey(dataId);
	}

	@Resource
	private XwSysSeqService xwSysSeqService;

	@Override
	public String save(CommonBiz commonBiz) {
		// 生成项目编号
		Long commnonBizSeqCode = xwSysSeqService.getXwSequence(COMMON_BIZ_SEQ, 1)
				.getStartSequence();
		String dataId = DateUtil.DateToString(new Date(), "yyyyMMddHHmmss")+commnonBizSeqCode;
		commonBiz.setDataId(dataId);
		commonBiz.setCreateTime(new Date());
		commonBizDao.insert(commonBiz);
		return commonBiz.getDataId();
	}

	@Override
	public void delete(String dataId) {
		commonBizDao.deleteByPrimaryKey(dataId);
	}

	@Override
	public void update(CommonBiz commonBiz) {
		commonBizDao.updateByPrimaryKey(commonBiz);
	}

	@Override
	public List<CommonBiz> selectByProjectIdAndStatus(Long projectId,
			String status) {
		return commonBizDao.selectByProjectIdAndStatus(projectId, status);
	}

	@Override
	public List<CommonBiz> selectByProjectIdAndServiceType(Long projectId,
			String serviceType) {
		return commonBizDao.selectByProjectIdAndServiceType(projectId, serviceType);
	}
	
	@Override
	public List<CommonBiz> selectAll() {
		return commonBizDao.selectAll();
	}

	// 更新审批结果、变更状态，更新人和更新时间
	@Override
	public void updateApprovalChangeByDataId(CommonBiz commonBiz) {
		commonBizDao.updateApprovalChangeByDataId(commonBiz);
	}
   
	//根据projectId查询CommonBiz对象
	@Override
	public List<CommonBiz> getCommonBizByProjectId(Long projectId) {
		List<CommonBiz> commonBizs = commonBizDao.selectByProjectId(projectId);
		return commonBizs;
	}
	
	@Override
	public Page<CommonBiz> selectByCategoryServiceTypeAndProjectName(
			User user, Map<String, Object> map) {
		Page<CommonBiz> page = new Page<CommonBiz>(0L);
		List<Long> roleIds = user.getRoleIds();
		if(null!=roleIds && !roleIds.isEmpty()){
			//如果为项目经理，只能查看自己的
			if(roleIds.contains(roleId_projectManager)){
				map.put("createPerson", user.getId().toString());
				
			}else if(roleIds.contains(roleId_committee)){
				//如果为决策委员会
				String serviceType = (String)map.get("serviceType");
				//如果业务类型为周期监测，只能查看自己提交的周期监测
				if(ProjectConstants.State.SUBMITMONTHLYCHECK.equals(serviceType)){
					map.put("createPerson", user.getId().toString());
				}else if(ProjectConstants.State.SUBMITMONTHLYREPORT.equals(serviceType)){
					//如果业务类型为周期报告，只能查看自己监测的项目
					map.put("serviceOwner", user.getId().toString());
				}
				else{
					
				}	
			}else{
				//可以查看所有（包括周期报告或周期检测）
			}
			page = new Page<CommonBiz>(commonBizDao.countByCategoryTypePersonProjectName(map));
			map.put("startRow", page.getStartRow());
			map.put("pageSize", page.getPageSize());
			page.setList(commonBizDao.selectByCategoryTypePersonProjectName(map));
		}
		return page;
	}

	@Override
	public void updateChangeStatusByDataId(String status,String dataId) {
		commonBizDao.updateChangeStatusByDataId(status,dataId);
	}

	@Override
	public void updateProcessInstanceAndTaskIdByDataId(
			String processInstanceId, String taskId, String dataId) {
		commonBizDao.updateProcessInstanceAndTaskIdByDataId(processInstanceId,taskId,dataId);
	}
	
}
