package com.xinwei.process.constant;

/**
 * 项目相关常量
 * 
 * @author xuejinku
 */
public interface ProjectConstants {

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
}
