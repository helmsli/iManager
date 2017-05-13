/**
 * first:初期，mid ：中期： final:末期
 */
var appraise_const=
{
		    projectManagerAppraise: "step_020", //项目经理自评
		    departLeaderAppraise: "step_021", //经理评价
};
var appraise_tipString=
{
		   managerAppraise: "请项目经理自己对本项目进行相关的评价", //项目经理自评
		   leaderAppraise: "请部门经理对本项目进行相关的评价", //经理评价
};
//回调函数
function appraiseRefreshUi()
{
}

function submitApproval()
{
	var scope=getAngularScope("projectModel");
	var obj={
			"request.departLeaderAppraise":scope.deparEvaluateContent,//后台接口绑定到页面输入框
			"request.taskId":scope.parm.taskId,
	}
	departManagerEvaluateByServer(obj,function(data){
		if(data.result == 0){
			console.log("项目经理自评提交后台成功");
			window.history.go(-1);
		}else{
			console.log("erropr");
		}
	})

}
//初始化页面上要显示的东西
function initAppraise()
{
	var scope=getAngularScope("projectModel");
	scope.deparEvaluateContent="";
	switch(scope.parm.state){
	case appraise_const.projectManagerAppraise:
	scope.projectAndLeaderAppraise=appraise_tipString.managerAppraise;
		break;
		
	case appraise_const.departLeaderAppraise:
		scope.projectAndLeaderAppraise=appraise_tipString.leaderAppraise;
			break;
	}
	scope.regisgerJs(scope.parm.state);	
}
initApproval();


