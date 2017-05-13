/*
 * 尽职调查
 */
/**
 *静态表格事件BLUR绑定并监听输入内容
 * **/
function initTableEvent()
{
	$("table").on("blur","td[contenteditable='true']",function(){
		var _this=$(this);
		var id=$(this).parent().attr("id");
		console.log(id);
		var name=_this.attr("data-columnName");
		var value=_this.text();
		var scope=getAngularScope("projectModel");
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
 *问题表格加载初始化
 * **/
function initQuestion2()
{
	initTableEvent();
	initRuleCommon("datetimepicker",'','','yyyy-MM-dd',2,"tableProjectInfo");
}
/**
 *获取时间
 * **/
function getInputDatePickerVal(){
    var value =$("#submitTime").find("input").val();
	 return value;
}  
/**
 *初始户加载问题列表JSON
 * **/
function initData()
{   
	var scope=getAngularScope("projectModel");
	getQuestionListByServer("",setQuestionUi);
	//从后台取基本的信息
	obj={
			"request.projectId":scope.gloablParm.projectId
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
	/**
 *将项目信息渲染scope
 * **/
function setQuestionUi(data)
{
	var scope=getAngularScope("projectModel");
	scope.questionList=data.data.pages;
	scope.$applyAsync(scope.questionList);
}
	
}
//回调函数

function approvalRefreshUi()
{
}


function submitQuestion()
{
	var scope=getAngularScope("projectModel");
	var submitTime=getInputDatePickerVal();
	scope.reportInfo.submitTime=submitTime;
	var reportInfo={
			reportInfo:scope.reportInfo,
			questionList:scope.questionList
	};
	var result = {};
	result.result = 0;
	result.type="jzdc";
	var obj={
			"request.taskId":scope.mainParm.taskId,
			"request.projectId":scope.mainParm.projectId,  //项目ID
			"request.serviceType":"jzdc",  //业务类型
			"request.result":JSON.stringify(result),  //业务类型
			"request.data1":JSON.stringify(scope.reportInfo),  //尽职调查--填写评价
			"request.data2":JSON.stringify(scope.questionList), //尽职调查--填写得分
			"request.data3":JSON.stringify(scope.projectInfo)  //尽职调查--项目基本信息
	};
	completeTask(result,obj,function(data){
		if(data.result==0){
			window.history.go(-1);
		}
	})
}
function initQuestion()
{
	var scope=getAngularScope("projectModel");
	scope.questionList=[];//问题列表
	scope.projectInfo={};//项目基础信息
	
	scope.reportInfo={
			proposalManager:"",//对项目经理建议
			finishVerdict:"",//结论
			evaluateManager:"",//对项目经理的评价
			submitUser:"",//调查人
			submitTime:""//提交时间
	};
	scope.projectLable={
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
	initData();
	initQuestion2();
}
initApproval();












































