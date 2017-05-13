App.controller('initQuestionData', function($scope) {
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	$scope.projectId=projectId;
	
	$scope.templateName="";
	$scope.questionList=[];//问题列表
	$scope.projectInfo={};//项目基础信息
	
	$scope.reportInfo={
			proposalManager:"",//对项目经理建议
			finishVerdict:"",//结论
			evaluateManager:"",//对项目经理的评价
			submitUser:"",//调查人
			submitTime:""//提交时间
	};
	$scope.projectLable={
	projectId:"项目编号",
	budget:"项目预算金额",
	projectName:"项目名称",
	projectManagerName:"项目经理",
	leaderDepartment:"项目经理所在部门",
	evaluateManager:"对项目经理的评价",//对项目经理的评价
	proposalManager:"对项目经理的建议",//对项目经理建议
	finishVerdict:"结论",//结论
	submitUser:"调查人",//调查人
	submitTime:"提交时间"//提交时间
};
	
	/**
	 * 页面加载初始化
	 * **/
	$scope.initQuestion=function()
	{
		$scope.initTemplatePath();
	}
	/**
	 *问题表格加载初始化
	 * **/
	$scope.initQuestion2=function()
	{
		//initTableEvent();
	}
	/**
	 *初始化模板加载路径
	 * **/
    $scope.initTemplatePath=function()
    {
		var parm = parseQueryString();
		var projectName=parm.projectName;
		var templateName=getQuestionTemplatePath(projectName);
		//加载模板
		$scope.templateName=templateName;
    }
    /**
	 *项目基本信息加载初始
	 * **/
    $scope.initQuestionTable=function()
	{
		initData();
	}
	
    /**
	 *提交报告
	 * **/
   $scope.submitQuestion=function()
   {
    	
   }
    //关闭--尽职调查
	/*var cancelMyagencyTaskWin = function(){
		var parm=parseQueryString();
		var from=parm.from;
		window.history.go(-1);
	}*/
	$scope.cancelMyapplySeeWin=function(){
		/*var currentUserId= localStorage.getItem("userInfo");
			currentUserId=JSON.parse(currentUserId);
			currentUserId=currentUserId.uid;
		if(currentUserId != 4){  //Id:4为项目经理发布   Id:5为项目管理员  Id:6为决策委员会   Id:为部门经理审批
			cancelMyagencyTaskWin();
		}else{
			cancelMyagencyTaskWin();
		}*/
		window.history.go(-1);
	}
    
});

/**
 *初始户加载问题列表JSON
 * **/
function initData()
{   
	var scope=getAngularScope("initQuestionData");
	//getQuestionListByServer("",setQuestionUi);
	//从后台取基本的信息
	obj={
			"request.projectId":scope.projectId
	};
	getQuestionByServer(obj,function(data){
		if(data.result==0){
			var reportInfo=JSON.parse(data.responseInfo.jzdc.data1);//data1:尽职调查--填写评价
			var question=JSON.parse(data.responseInfo.jzdc.data2);//data2:尽职调查--填写得分
			var projectInfo=JSON.parse(data.responseInfo.jzdc.data3);//data3:尽职调查--项目基本信息
			scope.projectInfo={
					"projectId":	projectInfo.projectId,
					"budget":projectInfo.budget,
					"projectName":projectInfo.projectName,
					projectManagerName:projectInfo.projectManagerName,
					leaderDepartment:projectInfo.leaderDepartment
			};
			//问题列表
			scope.questionList=question;
			//报告信息
			scope.reportInfo=reportInfo;
			var newProjectInfo=reportInfoExchange(scope.projectInfo,scope.reportInfo);
			 scope.$applyAsync(scope.questionList);
			 scope.$applyAsync(newProjectInfo);
			 initTableEvent();
		}
		console.log(data);
		
	})
	
}

function reportInfoExchange(obj1,obj2)
{
	for(var key in obj2)
	{
		obj1[key]=obj2[key];
	}
	return obj1;
}
/**
 *问题表格校验
 *参数：obj=elements 对象
 *将表格的数据渲染到scope questionList 对象
 * **/
function checkThisVal(obj)
{
	initTableEvent();
}
/**
 *静态表格事件BLUR绑定并监听输入内容
 *B
 * **/
function initTableEvent()
{
	$("table td[contenteditable='true']").attr("contenteditable",false);
}
