App.controller('initQuestionData', function($scope) {
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	$scope.dataId=parm.dataId;
	$scope.projectLable={
			projectBase:"项目基本情况",
			projectExecuted:"项目执行情况",
			score:"评分",
			reviewNum:"开评审会次数",
			fileNum:"输出文档个数",
			codeNum:"代码走读次数",
			whetherAdjust:"有无调整",
			adjustBefore:"调整前情况",
			adjustAfter:"调整后情况",
			adjustWhy:"调整理由",
			opinion:"意见",
			examine:"审批情况",
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
		var templateName=getCommitteeMonitorTemplatePath(projectName);
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
		if(data.result==0){
			var scopeParent=getAngularScope("initQuestionData");
			scopeParent.$apply(function () {
				scopeParent.reportInfo=JSON.parse(data.responseInfo.commonBiz.data1);
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
	






