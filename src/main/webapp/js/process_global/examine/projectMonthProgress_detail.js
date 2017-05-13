App.controller('initQuestionData', function($scope) {
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	$scope.dataId=parm.dataId;
	$scope.projectLable={
			specialExplain:"特殊情况说明：",
			innovation:"创新点和亮点：",
			changeConditions:"项目变更情况：（如无变更填“无”）",
			riskDescription:"风险说明：",
			summary:"经验总结、工作反思及遇到的困难或问题：",
			improvementPlan:"改进计划：",
	};
	//页面加载初始化
	$scope.initQuestion=function()
	{
		$scope.initTemplatePath();
	}
	//初始化模板加载路径
    $scope.initTemplatePath=function()
    {
		var parm = parseQueryString();
		var projectName=parm.projectName;
		var templateName=getProjectMonthSubmitTemplatePath(projectName);
		$scope.templateName=templateName;
    }
    //项目基本信息加载初始
    $scope.initQuestionTable=function()
	{
    	initTableEvent();
		initData();
	}
    //关闭
    $scope.cancelCurrentPageWin=function(){
    	window.history.go(-1);
    }
    
});

/**
 *初始户加载问题列表JSON
 * **/
function initData()
{   
	var scopeParent=getAngularScope("initQuestionData");
	obj={
		"request.dataId":scopeParent.dataId
	};
	getProjectMonthProgressByServer(obj,function(data){
		console.log(data);
		var questionList=[];
		if(data.result==0){
			var scopeParent=getAngularScope("initQuestionData");
			scopeParent.$apply(function(){
				scopeParent.questionList=JSON.parse(data.responseInfo.commonBiz.data1);
				scopeParent.reportInfo=JSON.parse(data.responseInfo.commonBiz.data2);
			});
		}
	})
}
function checkThisVal(obj)
{
	initTableEvent();
}
//变为不可编辑(有问题)
function initTableEvent()
{
	$("table td[contenteditable='true']").attr("contenteditable","false");
}	

//






