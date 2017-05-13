package com.xinwei.process.constant;

/**
 * 三级部门经理发布相关常量
 * 
 * @author fangping
 */
public class DepartmentLeaderPublishConstants {
	public interface State {
		// 项目变更状态名
		static final String PM_BUMENJLFB_START = "pm_bumenjlfb_start";//开始
		static final String PM_XIANGMUGLY_CHUSHEN = "pm_xiangmugly_chushen";//项目管理员初审
		static final String PM_SANJIBM_XG = "pm_sanjibm_xg";// 三级部门经理修改
		static final String PM_JUECEWYH_PINGSHEN = "pm_juecewyh_pingshen";//决策委员会评审
		static final String PM_BUMENJL_PINGSHEN = "pm_bumenjl_pingshen";//部门经理审批
		static final String PM_BUMENJLFB_END = "pm_bumenjlfb_end";//结束三级部门经理发布
		
		static final String PROCESS_PROJECT_ID = "projectid";
		static final String DISPATCH_THREELEAVELSLEADER = "dispatch_threeLevelsLeader";//指定三级部门经理
		}

}
