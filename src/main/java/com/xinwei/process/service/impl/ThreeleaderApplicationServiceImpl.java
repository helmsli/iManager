package com.xinwei.process.service.impl;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.xinwei.process.constant.DepartmentLeaderPublishConstants;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.dao.ThreeleaderApplicationMapper;
import com.xinwei.process.entity.ThreeleaderApplication;
import com.xinwei.process.service.ThreeleaderApplicationService;
import com.xinwei.security.entity.User;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.page.Page;

@Service
public class ThreeleaderApplicationServiceImpl implements
		ThreeleaderApplicationService {
	@Resource
	private ThreeleaderApplicationMapper threeleaderApplicationMapper;
	@Resource
	private XwSysSeqService xwSysSeqService;
	@Value("${roleId_threeLevelsLeader}")
	private Long roleId_threeLevelsLeader;// 三级部门经理角色ID
	@Value("${roleId_CooMartsOfficer}")
	private Long roleId_CooMartsOfficer;// CooMarts管理员角色ID
	@Value("${roleId_CamTalkOfficer}")
	private Long roleId_CamTalkOfficer;// CamTalk管理员角色ID
	@Value("${roleId_LotteryOfficer}")
	private Long roleId_LotteryOfficer;// Lottery管理员角色ID
	@Value("${roleId_committee}")
	private Long roleId_committee;// 决策委员会角色ID
	@Value("${roleId_departLeader}")
	private Long roleId_departLeader;// 部门经理角色ID
	//与项目表使用同一序列
	private final String PROJECT_SEQ = "project_seq";//项目编号
	
	@Override
	public Long save(ThreeleaderApplication threeleaderApplication) {
		// 生成项目编号
		Long publishSeqCode = xwSysSeqService.getXwSequence(PROJECT_SEQ, 1)
				.getStartSequence();
		// 设置申报编号
		threeleaderApplication.setApplicationId(publishSeqCode);
		// 获取当前时间
		Date now = new Date(System.currentTimeMillis());
		// 设置申报时间
		threeleaderApplication.setApplyTime(now);
		// 设置初识状态
		threeleaderApplication.setState(DepartmentLeaderPublishConstants.State.PM_BUMENJLFB_START);		
		// 保存到数据库
		threeleaderApplicationMapper.insert(threeleaderApplication);
		return publishSeqCode;
	}

	@Override
	public ThreeleaderApplication getById(Long applicationId) {
		
		return threeleaderApplicationMapper.selectByPrimaryKey(applicationId);
	}

	@Override
	public void updateCurrentStateByApplicationId(String currentState,
			Long applicationId) {
		threeleaderApplicationMapper.updateCurrentStateByApplicationId(currentState, applicationId);
		
	}

	@Override
	public void updateProcessInstanceIdByApplicationId(
			String processInstanceId, Long applicationId) {
		
		threeleaderApplicationMapper.updateProcessInstanceIdByApplicationId(processInstanceId, applicationId);
	}

	@Override
	public void updateStateByApplicationId(String state, Long applicationId) {
		threeleaderApplicationMapper.updateStateByApplicationId(state, applicationId);
	}

	@Override
	public Page<ThreeleaderApplication> getByUserAndCategoryId(
			User currentUser, Map<String, Object> map) {
		Page<ThreeleaderApplication> page = new Page<ThreeleaderApplication>(0L);
		//根据种类获取响应管理员角色Id
		Long categoryId = (Long)map.get("categoryId");
		Long roleId_offer = null;
		if(categoryId.toString().equals(ProjectConstants.CATEGORY.CoolMarts)){
			roleId_offer = roleId_CooMartsOfficer;
		}else if(categoryId.toString().equals(ProjectConstants.CATEGORY.CamTalk)){
			roleId_offer = roleId_CamTalkOfficer;
		}else if(categoryId.toString().equals(ProjectConstants.CATEGORY.Lottery)){
			roleId_offer = roleId_LotteryOfficer;
		}
		//根据当前用户角色进行筛选
		List<Long> roleIds = currentUser.getRoleIds();
		if(null!=roleIds && !roleIds.isEmpty()){
			
			//如果为项目管理人员，决策委员,部门经理会可以查看所有
			if(roleIds.contains(roleId_offer) || roleIds.contains(roleId_committee) || roleIds.contains(roleId_departLeader)){
				page = new Page<ThreeleaderApplication>(threeleaderApplicationMapper.countByCategoryId(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				page.setList(threeleaderApplicationMapper.selectByCategoryId(map));
			}else if(roleIds.contains(roleId_threeLevelsLeader)){
				//如果当前用户为三级部门经理，只能查看自己的
				map.put("userId", currentUser.getId());
				page = new Page<ThreeleaderApplication>(threeleaderApplicationMapper.countByUserIdCategoryId(map));
				map.put("startRow", page.getStartRow());
				map.put("pageSize", page.getPageSize());
				page.setList(threeleaderApplicationMapper.selectByUserIdCategoryId(map));
			}else{
				return page;
			}	
		}	
		return page;
	}

	@Override
	public ThreeleaderApplication getByApplyPersonAndPublishId(String applyPerson,
			Long departleaderPublishId) {
		
		return threeleaderApplicationMapper.selectByUserIdAndPublishId(applyPerson,departleaderPublishId);
	}

	@Override
	public void updateByPrimaryKey(ThreeleaderApplication threeleaderApplication) {
		 threeleaderApplicationMapper.updateByPrimaryKey(threeleaderApplication);
	}
	
	
}
