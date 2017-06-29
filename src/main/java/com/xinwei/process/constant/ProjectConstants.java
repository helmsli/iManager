package com.xinwei.process.constant;

import org.apache.commons.lang3.StringUtils;

/**
 * 项目相关常量
 * 
 * @author xuejinku
 */
public interface ProjectConstants {

	
	   
	public interface MonthlyConst{
		static final String oldCommonBizCategory="oldMonthlyReport";
	}
	// 项目当前状态
	public interface State {
		// 项目状态名
		static final String STARTEVENT = "startProjectProcess";// 开始
		static final String OFFICERAPPROVAL = "officerApproval";// 项目管理员审批
		static final String COMMITTEEAPPROVAL = "committeeApproval";//决策委员会审批
		static final String DEPARTLEADERAPPROVAL = "departleaderApproval";// 部门经理审批
		static final String PMMODIFYMATERIAL = "pmModifyMaterial";//项目经理修改申报材料
		static final String PMSUBMITMIDMATERIAL = "pmSubmitMIdMaterial";//项目经理提交中期评审材料
		static final String DISPATCHERMIDMASTER = "DispatcherMidMaster";//项目管理人员指定中期评审专家
		static final String MIDAPPROVALREPORT = "midApprovalReport";//项目经理提交中期评审报告
		static final String INPUTMIDGRADE = "inputMidGrade";//项目管理人员录入中期评审专家评分
		static final String MIDCOMMITTEEAPPROVAL = "midCommitteeApproval";//决策委员会中期评审
		static final String DEPARTLEADERMIDAPPROVAL = "departleaderMidApproval";// 部门经理审核中期结果
		static final String PMSUBMITENDMATERIAL = "pmSubmitEndMaterial";//项目经理提交终期评审材料
		static final String DISPATCHERENDMASTER = "DispatcherEndMaster";//项目管理人员指定终期评审专家
		static final String ENDAPPROVALREPORT = "endApprovalReport";//项目经理提交终期评审报告
		static final String INPUTENDGRADE = "inputEndGrade";// 项目管理人员录入终期评审专家评分
		static final String COMMITTEEENDAPPROVAL = "committeeEndApproval";//决策委员会终期评审
		static final String DEPARTLEADERENDAPPROVAL = "departleaderEndApproval";//部门经理审核终期结果
		static final String SUBMITMONTHLYREPORT = "submitMonthlyReport";// 提交周期报告
		static final String SUBMITMONTHLYCHECK = "submitMonthlyCheck";// 提交周期监测
		static final String PMSELFCONCLUSION = "PMSelfConclusion";//项目经理自评
		static final String DEPARTLEADERCONCLUSION = "departleaderConclusion";//部门经理评价
		static final String ENDEVENT = "endProjectProcess";//结束

		static final String EndMonthlyReport="endSubmitMonthlyReport";//结束提交周期报告
		// 项目状态码(标识进行中，结束)
		static final String CODE_START = "start";//开始
		static final String CODE_END = "end";//结束
	}

	// 项目阶段
	public interface Stage {
		static final String BEGIN = "begin";
		static final String MIDDLE = "middle";
		static final String LAST = "last";
	}

	// 项目类别
	public interface CATEGORY {
		static final String CoolMarts = "1";
		static final String CamTalk = "2";
		static final String Lottery = "3";
	}

	// （附件、中期评审材料、中期评审报告、终期评审材料、终期评审报告、项目周期报告）
	public interface ANNEX_TYPE {
		static final Long ANNEX = 1L;// 附件

		static final Long MIDMATERIAL = 2L;// 中期评审材料
		static final Long MIDREPORT = 3L;// 中期评审报告

		static final Long TERMIANLMATERIAL = 4L;// 终期评审材料
		static final Long TERMIANLREPORT = 5L;// 终期评审报告
		// 项目周期报告
		static final Long MONTHLYREPORT = 6L;// 项目周期报告
	}
	
	//-1000到-2000
	public interface MONTHLY_ERROR {
		static final Long ProjectName_ISNULL = -1000L;// 申报的项目名称为空
		static final Long ProjectCompany_ISNULL = -1001L;//申报书单位为空
		static final Long Trainning_times_ISNULL =  -1002L;//培训次数为空
		static final Long NotTrainning_times_ISNULL =  -1003L;//非培训的服务次数
		static final Long MainContent_ISNULL =  -1004L;//本月主要工作内容介绍的服务次数
		static final Long ChuDianZongjie_ISNULL =  -1005L;//触点总结（本月好的经验/问题总结，项目运作小的或大的改进、提高等让人有所触动之处
		static final Long ZhiJieShouYIRenshu_ISNULL =  -1006L;//直接受益人数
		static final Long ZhiJieShouYIRenCi_ISNULL =  -1007L;//直接受益人次
		
		static final Long JianJieShouYIRenshu_ISNULL =  -1008L;//间接受益人数
		static final Long JianJieShouYIRenCi_ISNULL =  -1009L;//间接受益人次
		
		static final Long XiangMuBianGeng_ISNULL =  -1010L;//间项目变更情况（如无变更填“无”）
		static final Long MeiTiBaoDiaoCiShu_ISNULL =  -1011L;//媒体报道次数
		static final Long MeiTiBaoDaoLianjie_ISNULL =  -1012L;//媒体报道链接
		static final Long GaiJinJiHua_ISNULL =  -1013L;//改进计划
		
		static final Long XiaYueZhongDaJihua_ISNULL =  -1014L;//下月重大活动计划
		static final Long YueFen_ISNULL =  -1015L;//下月重大活动计划
		static final Long Format_ERROR =  -1050L;//下月重大活动计划
	}
	//-1到-255
	public interface PROJECT_ERROR {
		static final Long APPLICATION_ISNULL = -1L;// 申报书上传失败，上传的是空值

		static final Long ProjectName_ISNULL = -2L;// 申报的项目名称为空
		static final Long ProjectCatetory_ISNULL = -3L;// 申报的项目类型为空

		static final Long ProjectStartTime_ISNULL = -4L;// 申报的项目开始时间或者结束时间为空
		
		static final Long ProjectContacts_ISNULL = -5L;//申报书联系人信息不全
		static final Long ProjectContactsTelOrEmail_ISNULL = -6L;//申报书联系人Email或者电话为空
		
		static final Long ProjectCompany_ISNULL = -7L;//申报书单位为空
		static final Long ProjectServiceField_ISNULL = -8L;//申报书单位为空
		
		static final Long Project_NotExist = -9L;//项目不存在
		static final Long Project_committee_NotExist = -10L;//没有指定第三方
		
		static final Long TERMIANLREPORT = 5L;// 终期评审报告
		// 项目周期报告
		static final Long MONTHLYREPORT = 6L;// 项目周期报告
	}
}
