//尽职调查提交给后台的
App.controller('initQuestionData', function($scope) {
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	var state=parm.state;
	var taskId=parm.taskId;
	var projectCategory=parm.projectCategory;
	$scope.mainParm=parm;
	
	$scope.projectCategory=projectCategory;
	$scope.state=state;
	$scope.taskId=taskId;
	$scope.from=parm.from;
	$scope.complete=parm.complete;
	var userInfo= localStorage.getItem("userInfo");
	userId=JSON.parse(userInfo);
	$scope.userId=userId.uid;
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
		initTableEvent();
		initRuleCommon("datetimepicker",'','','yyyy-MM-dd',2,"tableProjectInfo");
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
		//initRuleCommon("datetimepicker","","",format,row,"tableProjectInfo");
	}
	
    /**
	 *提交报告
	 * **/
   $scope.submitQuestion=function()
   {
		var scope=$scope;
    	var submitTime=getInputDatePickerVal();
    		scope.reportInfo.submitTime=submitTime;
    	var reportInfo={
    			reportInfo:scope.reportInfo,
    			questionList:scope.questionList
    	};
    	var result = {};
    	result.result = 0;
		var obj={
				"request.taskId":scope.mainParm.taskId,
				"request.projectId":scope.mainParm.projectId,  //项目ID
				"request.serviceType":"JZDC",  //业务类型
				"request.result":JSON.stringify(result),  //业务类型
				"request.data1":JSON.stringify(scope.reportInfo),  //尽职调查--填写评价
				"request.data2":JSON.stringify(scope.questionList), //尽职调查--填写得分
				"request.data3":JSON.stringify(scope.projectInfo)  //尽职调查--项目基本信息
				//"request.data1":scope.projectInfoObj //数据1
		};
		submitReportInfoByServer(obj,function(data){
			if(data.result==0){
				window.history.go(-1);
			}
		})
    	//console.log(JSON.stringify(obj));
    	//提交
   }
   //获取时间value
  function getInputDatePickerVal(){
      var value =$("#submitTime").find("input").val();
 	 return value;
  }  
    
    //关闭--尽职调查
	var cancelMyagencyTaskWin = function(){
		var parm=parseQueryString();
		var from=parm.from;
		location.replace(from);
	}
	
	$scope.cancelMyapplySeeWin=function(){
		var currentUserId= localStorage.getItem("userInfo");
			currentUserId=JSON.parse(currentUserId);
			currentUserId=currentUserId.uid;
		if(currentUserId != 4){  //Id:4为项目经理发布   Id:5为项目管理员  Id:6为决策委员会   Id:为部门经理审批
			cancelMyAgencyTaskWin();
		}else{
			cancelMyagencyTaskWin();
		}
	}
    
});

/**
 *初始户加载问题列表JSON
 * **/
function initData()
{   
	var scope=getAngularScope("initQuestionData");
	getQuestionListByServer("",setQuestionUi);
	//从后台取基本的信息
	obj={
			"request.projectId":scope.mainParm.projectId
	};
	getBaseInfoByServer(obj,function(data){
		if(data.result==0){
			console.log(data);
			var project=data.responseInfo.project;
			var cost=JSON.parse(project.projectCosts);
			var projectManager=data.responseInfo.projectManager;
			scope.projectInfo={
					projectId:project.projectId,
					projectManagerName:projectManager.username,
					budget:0,//没有总计，先拿payPrice
					projectName:project.projectName,
					leaderDepartment:projectManager.company_name,//需要的信息从data里边去取*/			
			};
		}
		
	})
	
}

/**
 *将项目信息渲染scope
 * **/
function setQuestionUi(data)
{
	var scope=getAngularScope("initQuestionData");
	scope.questionList=data.data.pages;
	scope.$applyAsync(scope.questionList);
	//模拟从后台取出的项目基本信息的数据
	/*scope.projectInfo={
			projectId:"123456",
			budget:"100.00",
			projectName:"尽职调查报告",
			projectManagerName:"李国强",
			leaderDepartment:"运营支撑产品平台部",
		};
	*/
	//开始进行尽职调查时从后台取出基本信息
	
	

	
	
}
/**
 *问题表格校验
 *参数：obj=elements 对象
 *将表格的数据渲染到scope questionList 对象
 * **/
function checkThisVal(obj)
{
	var name=obj.getAttribute("data-columnName");
	var value=obj.innerText;
	var groupId=obj.parentNode.getAttribute("data-id");
	var scope=getAngularScope("initQuestionData");
	scope.questionList[groupId][name]=value;
}

/**
 *静态表格事件BLUR绑定并监听输入内容
 *B
 * **/
function initTableEvent()
{
	$("table").on("blur","td[contenteditable='true']",function(){
		var _this=$(this);
		var id=$(this).parent().attr("id");
		console.log(id);
		var name=_this.attr("data-columnName");
		var value=_this.text();
		var scope=getAngularScope("initQuestionData");
		if(id!="questionTemplateC" ||!id)
		{
			scope.reportInfo[name]=value;
		}else{
			var groupId=_this.parent().attr("id");
			scope.questionList[groupId][name]=value;
		}
	});
}


/**
 * 请求问题列表
 * 参数：obj ,callBack
 * requestInfo=
	{
		"request.projectId ":"项目id"
	}
	return callBack(data);
 * **/
function getQuestionListByServer(type,callBack)
{
	$.getJSON("/process/js/process_global/question/questionTemplate/question_A.json",function(data){
		callBack(data);
	})
}
/**
 * 查看报告的回调函数
 * 参数：obj ,callBack
 * **/
function getReportInfo(data)
{
	//chuli
}




















