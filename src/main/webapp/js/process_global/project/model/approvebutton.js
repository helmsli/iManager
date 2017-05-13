
var approveButtionTitle=
{
		   //初期
		    
		    "jzdc": "尽职调查", //初期委员会审批
		    "jzdc_url":"",//初期委员会审批
		    "pm_bumenjl_pingshen": "部门经理审批", //初期项目经理审批
		    //中期
		   
		    "mid_commiteeApproval": "pm_juecewyh_pingshen", //委员会审批
		    "mid_departManagerApproval": "step_012", //项目经理审批
		    //终期
		  
		    "final_commiteeApproval": "step_017", //委员会审批
		    "final_departManagerApproval": "step_018", //项目经理审批
		  
};
//回调函数
function approvalRefreshUi()
{
	var scope=getAngularScope("projectModel");
	var title = "办理";
	alert(typeof approval_title[scope.gloablParm.state] !="undefined");
	if(typeof approval_title[scope.gloablParm.state] !="undefined")
	{
		title = approveButtionTitle[scope.gloablParm.state];
	}  
	
	scope.suggestion={
			buttontitle:title,
		result:0,
		comment:""	
	}
}

function submitApproval()
{
	var scope=getAngularScope("projectModel");
	url = "myTaskAgent_question.html?projectName="+scope.gloablParm.projectName+"&projectId="+scope.gloablParm.projectId+"&taskId="+scope.gloablParm.taskid+"&state="+scope.gloablParm.state;
	location.href=url;
}
function initApproval()
{
	initFileStateListener(approvalRefreshUi);
}

initApproval();



/**
 * 拿到系统的scope，赋值，向你register；如果页面加载完毕，由框架调用callbackHtml;
 * 如果HTML没有加载完毕，
 */

