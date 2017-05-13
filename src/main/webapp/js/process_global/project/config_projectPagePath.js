var APP_RESULT=
	{"success":0,
	 "fail":1	
};
var APP_ContainAttach=
{"attach":0, 
  "attachKey":"commonbizfileKey"	 	
};

var setPerson={
	"dispatcherMasterApproval":["指定决策委员会","所有的决策委员会"]
}	

/**
 * 配置界面加载路径
 * ***/
var CONFIG_PATH=
		{
		    "STEP_pm_sanjibm_qingqiu_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    "STEP_pm_xiangmugly_chushen_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_PMSelfConclusion_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_departleaderConclusion_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_juecewyh_pingshen_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_bumenjl_pingshen_PATH": "userTemplate/approveTemplate/approveRelease.html?projectName=coomarts",
		    "STEP_pm_xiangmjl_tijiaofa_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    "STEP_pmSubmitMIdMaterial_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    "STEP_DispatcherMidMaster_PATH": "userTemplate/approveTemplate/setExpertList.html?projectName=coomarts",
		    "STEP_midApprovalReport_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    "STEP_inputMidGrade_PATH": "userTemplate/approveTemplate/setExpertScore.html?projectName=coomarts",
		    "STEP_midCommitteeApproval_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_departleaderMidApproval_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    
		    "STEP_committeeApprovalChange_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_dispatcherMasterApproval_PATH":"userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pmSubmitEndMaterial_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    "STEP_DispatcherEndMaster_PATH": "userTemplate/approveTemplate/setExpertList.html?projectName=coomarts",
		    "STEP_endApprovalReport_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    "STEP_inputEndGrade_PATH": "userTemplate/approveTemplate/setExpertScore.html?projectName=coomarts",
		    "STEP_committeeEndApproval_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_departleaderEndApproval_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_submitMonthlyReport_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		    //"STEP_PMSelfConclusion_PATH": "userTemplate/approveTemplate/setExpertScore.html?projectName=coomarts",
		    //"STEP_departleaderConclusion_PATH": "userTemplate/approveTemplate/setExpertScore.html?projectName=coomarts",
		    "STEP_19_PATH": "userTemplate/approveTemplate/page_019.html?projectName=coomarts",
		    "STEP_20_PATH": "userTemplate/approveTemplate/page_020.html?projectName=coomarts",
		    "STEP_21_PATH": "userTemplate/approveTemplate/page_021.html?projectName=coomarts",
		    "STEP_22_PATH": "userTemplate/approveTemplate/page_022.html?projectName=coomarts",
		    "STEP_23_PATH": "userTemplate/approveTemplate/page_023.html?projectName=coomarts",
		    "STEP_24_PATH": "userTemplate/approveTemplate/page_024.html?projectName=coomarts",
		    
		    
		    
		    "STEP_pm_begainsetExpertList_PATH": "userTemplate/approveTemplate/setExpertList.html?projectName=coomarts",
		    "STEP_pm_middlesetExpertList_PATH": "userTemplate/approveTemplate/setExpertList.html?projectName=coomarts",
		    "STEP_pm_finalsetExpertList_PATH": "userTemplate/approveTemplate/setExpertList.html?projectName=coomarts",
		    "STEP_pm_begainsetExpertScore_PATH": "userTemplate/approveTemplate/setExpertScore.html?projectName=coomarts",
		    "STEP_pm_middleExpertScore_PATH": "userTemplate/approveTemplate/setExpertScore.html?projectName=coomarts",
		    "STEP_pm_finalExpertScore_PATH": "userTemplate/approveTemplate/setExpertScore.html?projectName=coomarts",
		    
		    
		   
		    "STEP_pm_begainCommitteApprove_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_middleCommitteApprove_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_finalCommitteApprove_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_begainDepartLeaderApprove_PATH":"userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    
		    "STEP_departleaderApprovalChange_PATH":"userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_committeeApproval_PATH":"userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    
		    "STEP_departleaderApproval_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_officerApproval_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_middleDepartLeaderApprove_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_finalDepartLeaderApprove_PATH": "userTemplate/approveTemplate/approve.html?projectName=coomarts",
		    "STEP_pm_jinzhidiaocha_PATH": "userTemplate/approveTemplate/jzdc.html?projectName=coomarts",
		    "STEP_pm_tijiaofujian_PATH": "userTemplate/approveTemplate/submitAttachment.html?projectName=coomarts",
		   
		};

/***
 * 配置JS文件加载路径
 * **/
var SCRIPTFile={
	    "STEP_pm_sanjibm_qingqiu_PATH": "/js/process_global/project/model/submitAttachment.js",
	    "STEP_pm_xiangmugly_chushen_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_pm_juecewyh_pingshen_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_PMSelfConclusion_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_committeeApprovalChange_PATH": "/js/process_global/project/model/approve.js",
	    
	    "STEP_departleaderConclusion_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_pm_bumenjl_pingshen_PATH": "/js/process_global/project/model/approveRelease.js",
	    "STEP_pm_xiangmjl_tijiaofa_PATH": "/js/process_global/project/model/submitAttachment.js",
	    "STEP_pmSubmitMIdMaterial_PATH": "/js/process_global/project/model/submitAttachment.js",
	    "STEP_DispatcherMidMaster_PATH": "/js/process_global/project/model/setExpertList.js",
	    "STEP_midApprovalReport_PATH": "/js/process_global/project/model/submitAttachment.js",
	    "STEP_inputMidGrade_PATH": "/js/process_global/project/model/setExpertScore.js",
	    
	    "STEP_departleaderApprovalChange_PATH":"/js/process_global/project/model/approve.js",
	    "STEP_committeeApproval_PATH":"/js/process_global/project/model/approve.js",
	    "STEP_officerApproval_PATH":"/js/process_global/project/model/approve.js",
	    "STEP_midCommitteeApproval_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_departleaderMidApproval_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_dispatcherMasterApproval_PATH":"/js/process_global/project/model/approve.js",
	    "STEP_pmSubmitEndMaterial_PATH": "/js/process_global/project/model/submitAttachment.js",
	    "STEP_DispatcherEndMaster_PATH": "/js/process_global/project/model/setExpertList.js",
	    "STEP_endApprovalReport_PATH": "/js/process_global/project/model/submitAttachment.js",
	    "STEP_inputEndGrade_PATH": "/js/process_global/project/model/setExpertScore.js",
	    "STEP_committeeEndApproval_PATH": "/js/process_global/project/model/approve.js",
	    
	    "STEP_departleaderApproval_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_departleaderEndApproval_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_submitMonthlyReport_PATH": "/js/process_global/project/model/submitAttachment.js",
	    //"STEP_PMSelfConclusion_PATH": "/js/process_global/project/model/setExpertScore.js",
	    //"STEP_departleaderConclusion_PATH": "/js/process_global/project/model/setExpertScore.js",
	    
	    "STEP_pm_begainsetExpertList_PATH":  "/js/process_global/project/model/setExpertList.js",
	    "STEP_pm_middlesetExpertList_PATH":  "/js/process_global/project/model/setExpertList.js",
	    "STEP_pm_finalsetExpertList_PATH":  "/js/process_global/project/model/setExpertList.js",
	    "STEP_pm_begainsetExpertScore_PATH": "/js/process_global/project/model/setExpertScore.js",
	    "STEP_pm_middleExpertScore_PATH":  "/js/process_global/project/model/setExpertScore.js",
	    "STEP_pm_finalExpertScore_PATH":  "/js/process_global/project/model/setExpertScore.js",
	    
	    
	    "STEP_pm_begainCommitteApprove_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_pm_middleCommitteApprove_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_pm_finalCommitteApprove_PATH":  "/js/process_global/project/model/approve.js",
	    "STEP_pm_begainDepartLeaderApprove_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_pm_middleDepartLeaderApprove_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_pm_finalDepartLeaderApprove_PATH": "/js/process_global/project/model/approve.js",
	    "STEP_pm_jinzhidiaocha_PATH": "/js/process_global/project/model/jzdc.js",
	    "STEP_pm_tijiaofujian_PATH": "/js/process_global/project/model/submitAttachment.js",
	};

	var APPROVE_TITLE={
		"AGREE":"同意",
		"DISAGREE":"不同意",
		"pm_sanjibm_qingqiu": "三级部门经理提交材料",
	    "pm_xiangmugly_chushen": "项目管理员初审",
	    "pm_juecewyh_pingshen": "决策委员会评审",
	    "pm_bumenjl_pingshen": "部门经理评审",
	    "pm_xiangmjl_tijiaofa": "项目经理提交",
	    "jzdc":"尽职调查",
	    "pm_process_bumenjlfb":"部门经理发布流程",
	    "pm_sanjibm_jujue":"拒绝申报",
	    "pm_sanjibm_request_result":"项目管理员初审结果",
	    "pm_juecewyh_pingshen_result":"决策委员会评审结果",
	    "pm_bmjl_pingshen_result":"部门经理评审结果",
	    "pmSubmitMIdMaterial":"项目经理提交中期评审材料",
	    "DispatcherMidMaster":"管理人员指定中期评审专家",
	    "midApprovalReport":"项目经理提交中期评审报告",
	    "midConclusion":"中期评审结论",
	    "pmSubmitEndMaterial":"项目经理提交终期评审材料",
	    "PMSelfConclusion":"项目经理自评",
	    "DispatcherEndMaster":"管理人员指定终期评审专家",
	    "endConclusion":"终期评审结论",
	    "resultOfEndChecked":"项目管理人员审核终期结果",
	    "resultOfMidChecked":"项目管理人员审核中期结果",
	    "departleaderMidApproval":"部门经理审核中期结果",
	    "departleaderEndApproval":"部门经理审核终期结果",
	    "inputMidGrade":"管理人员录入中期评审专家评分",
	    "endApprovalReport":"项目经理提交终期评审报告",
	    "inputEndGrade":"管理人员录入终期评审专家评分",
	    "departleaderConclusion":"部门经理评价",
	    "midCommitteeApproval":"决策委员会中期评审",
	    "committeeEndApproval":"决策委员会终期评审",
	    "midConclusionPass":"中期评审通过",
	    "resultOfMidCheckedNotPass":"中期结果不合格",
	    "resultOfMidCheckedPass":"中期结果合格",
	    "submitMonthlyReport":"提交周期性报告",
	    "midConclusionNotPass":"中期评审不通过",
	    "endConclusionNotPass":"终期评审不通过",
	    "endConclusionPass":"终期评审通过",
	    "resultOfEndCheckedNotPass":"终期结果不合格",
	    "resultOfEndCheckedPass":"终期结果合格",
	    "dispatcherMasterApproval":"指定评审专家立项审批",
	    "inputmastergrade":"管理人员录入评审专家评分",
	    "primaryReviewConclusion":"初期评审结论",
	    "departleaderApproval":"部门经理审批",
	    "pm_process_xiangmujlsq_end":"项目经理申请流程结束",
	    "pmModifyMaterial":"项目经理修改评审材料",
	    "committeeApproval":"决策委员会审批",
	    "modifyMaterial2dispatcherMaster":"修改评审材料",
	    "primaryReviewConclusionPass":"初期评审通过",
	    "primaryReviewConclusionNotPass":"初期评审不通过",
	    "resultOfDepartApprovalNotPass":"评审通过",
	    "resultOfDepartApprovalNeedModify":"修改评审材料",
	    "officerApproval":"管理员审批",
	    "committeeApprovalChange":"决策委员会审批变更",
	    "departleaderApprovalChange":"部门经理审批变更"
	    
	};
