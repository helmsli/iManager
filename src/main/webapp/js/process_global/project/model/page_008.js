/*
 * yxg 2017-02-17
 * 项目经理自评
 * 
 * */
function projectManagerEvaluate(state,data,taskId)
{
	var scope=getAngularScope("initSeeData");
	var obj={
			"request.selfAppraise":scope.projectEvaluateContent,//后台接口绑定到页面输入框
			"request.taskId":taskId,
	}
	console.log("项目经理自评内容："+scope.projectEvaluateContent);
	projectManagerEvaluateByServer(obj,function(data){
		if(data.result == 0){
			window.history.go(-1);
			//后台返回值绑定到页面
			//scope.projectEvaluateContentView=data.responseInfo.expertReview.recordId;
			//window.history.go(-1);
		}
	})
}
