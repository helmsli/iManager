/*
 * yxg 2017-02-18
 * 部门经理评价
 * 
 * */
function departManagerEvaluate(state,data,taskId)
{
	var scope=getAngularScope("initSeeData");
	var obj={
			"request.departLeaderAppraise":scope.deparEvaluateContent,//后台接口绑定到页面输入框
			"request.taskId":taskId,
	}
	console.log("部门经理评价内容:"+scope.deparEvaluateContent);
	departManagerEvaluateByServer(obj,function(data){
		if(data.result == 0){
			console.log("项目经理自评提交后台成功");
			window.history.go(-1);
			//后台返回值绑定到页面
			//scope.deparEvaluateContentView=data.responseInfo.expertReview.recordId;
			//window.history.go(-1);
		}else{
			console.log("erropr");
		}
	})
}

