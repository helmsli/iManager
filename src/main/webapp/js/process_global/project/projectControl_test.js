App.controller('projectModel', ['$scope',initScope]);


function initScope($scope) {
	
		$scope.templatePath="";
		//分数模型
		$scope.expertScoreList=[0,1,2,3,4];
		//项目基础信息
		$scope.projectInfo={};
		//专家信息
		$scope.expertReview={};
		$scope.expertReviewFlag={};
		//决策委员会信息
		$scope.committeeApproval={};
		$scope.committeeApprovalFlag={};
		//部门经理信息
		$scope.departleaderApproval={};
		$scope.departleaderApprovalFlag={};
		
		//周期报告
		$scope.cycleReport=[];
		//评审材料
		$scope.reviewFile=[{"time":"2017-01-05","filePath":"a.doc"}];
		
		//尽职调查
		$scope.surveyReport={reportInfo:null,projectInfo:null,questionInfo:null};
		
		
		
		//界面初决加载初始化模型
		$scope.initModel=function()
		{
			console.log("=========初始化模型");
			getProjectInfo();
			pageControll();
		}
		
		
		//指定评审专家初始化表格模块
		$scope.setExperListTableInit=function()
		{
			init("setExperListTable");
		}
		
		
}


var pageModelState2={
		"Offercer":{"create":true}
}

/*var roleName={
		ADMIN:14,
		CooMartsOfficer:15,
};*/

var pageModelState={
		//项目经理
		Leader:{
			create:"createTemplate/leader/createTemplateA.html",//初期项目经理申请模块/提交/(尽职调查)/月度报告---(控制)
			middle:"createTemplate/leader/submitMiddleFile.html",//中期项目经理评审----/提交中期评审材料/中期评审报告(控制)
			finished:"createTemplate/leader/submitMiddleFile.html"//终期项目经理评审----/提交中期评审材料/中期评审报告(控制)
		},
		//部门
		Department:{
			create:"userTemplate/Department/submit_Create_Check.html?projectName=coomarts",//初期部门经理评审----通过/不通过+决策内容(控制)
			middle:"createTemplate/departMent/submit_Middle_Check.html",//中期部门经理评审----通过/不通过+决策内容(控制)
			finished:"createTemplate/departMent/submit_finished_Check.html"//终期部门经理评审----通过/不通过+决策内容(控制)
		},
		//决策人员
		Committee:{
			create:"createTemplate/Committee/submit_create_check.html",//初期决策评审----通过/不通过+决策内容(控制)
			middle:"createTemplate/Committee/submit_Middle_check.html",//中期决策评审----通过/不通过+决策内容(控制)
			finished:"createTemplate/Committee/submit_finished_check.html"//终期决策评审----通过/不通过+决策内容(控制)
		},
		//项目管理员
		Offercer:{
			create:"userTemplate/Offercer/create_setExpertList.html?projectName=coomarts",//只能指定评审专家 有个指定评审专家的表格(只填写专家)+录入专家分数---显示/不显示
			//create:"userTemplate/Offercer/create_setExpertListScore?projectName=coomarts",
			middle:"createTemplate/Offercer/middle_setExpertList.html",//只能指定中期评审专家--(只填写专家)-表格显示/控制
			finished:"createTemplate/Offercer/finished_setExpertList.html"//终期评审专家显示(只填写专家)
		}
};

/**
 *界面控制主流程
 * **/
function pageControll()
{
	
	var roleName=getRoleName(pageModelState2);
	var roleList=getUserRole(roleName,pageModelState);//请求权限
	var state=getMainState(pageModelState2[roleName]);//请求主状态
	console.log(roleName+"--"+roleList+"=-=--"+state);
	var templatePath=getTemplatePath(roleList,state);//请求模板名称
	initTemplatePath(templatePath);//初始化模板路径
}
/**
 * 请求主状态名
 * 参数:主状态列表
 * 返回:主状态名 stateName
 * **/
function getMainState(mainState)
{
	var mainStateName="";
	for(var name in mainState)
		{
			if(mainState[name] && mainState[name]==true)
			{
				return mainStateName=name; 
				break;
			}
		}
	return mainStateName;
}
/**
 * 请求角色名
 * 参数:角色权限表模型,必须保证对应模型;
 * **/
function getUserRole(roleType,roleList)
{
	return roleList[roleType];//找主状态
}
/**
 * 请求角色名
 * 参数:角色权限表模型,必须保证对应模型;
 * **/
function getRoleName(roleModel)
{
	var roleType="";
	for(var key in roleModel)
	{
		roleType=key;
	}
	return roleType; //返回角色名
}

/**
 * 获取用户权限模板
 * **/
function getProjectAndUserRole()
{
	return pageModelState2;
}
/**
 * 请求模板路径
 * 参数:权限name,主状态
 * 返回模板路径
 * **/

function getTemplatePath(roleList,state)
{
	return roleList[state];
}
/**
 * 初始化模板路径
 * 参数:模板名称
 * 渲染到scope;
 * **/
function initTemplatePath(filePath)
{
	var scope=getAngularScope("projectModel");
	scope.templatePath=filePath;
	//加载模板
	getPathName();
}


function getPathName()
{
	var configPath={};
	for(var i=0;i<25; i++)
	{
		var pathName="STEP_"+i+"_PATH";
		var pathVal="approveTemplate/page_0"+i+".html";
		configPath[pathName]=pathVal;
	}
	console.log(JSON.stringify(configPath));
}

/**
 * 将初始MODEL模型转换为需要的数组模型对象
 * 
 * modelFlag:bool 控制界面显示,modelData:原始数据
 * 返回:{modelFlag:newModel,modelData:dataModel}
 * 
 * **/

function mainModelExchange(model)
{

	var newModel={};
	var dataModel={};
	for(var key in model.beginStage)
		{
			newModel[key]={
						"create":!isEmptyObject(model.beginStage[key]),
						"middle":!isEmptyObject(model.middleStage[key]),
						"finished":!isEmptyObject(model.lastStage[key])
			};
			dataModel[key]={
					"create":model.beginStage[key],
					"middle":model.middleStage[key],
					"finished":model.lastStage[key]
			};
		};
	return {modelFlag:newModel,modelData:dataModel};
}

function getProjectInfo()
{
	var data=projectData;
	data=data.responseInfo.projectDetailInfo;
	var scope=getAngularScope("projectModel");
	var projectInfo=data.project;
	var cycleType=JSON.parse(projectInfo.cycleType);
	projectInfo.cycleType=cycleType.value;
	scope.project=projectInfo;
	console.log("================");
	console.log(scope.project);
	console.log("*****************");
	
	//模型数据转换
	var model={
			beginStage:data.beginStage,
			middleStage:data.middleStage,
			lastStage:data.lastStage
	};
	
	var newDataModel=mainModelExchange(model);
	//项目信息
	scope.expertReview=expertScoreExchangeObj(newDataModel.modelData.expertReview);
	scope.expertReviewFlag=newDataModel.modelFlag.expertReview;//是否有报告
	//报告审核
	scope.committeeApproval=newDataModel.modelData.committeeApproval;
	scope.committeeApprovalFlag=newDataModel.modelFlag.committeeApproval;
	//决策委员会审核
	scope.departleaderApproval=newDataModel.modelData.departleaderApproval;
	scope.departleaderApprovalFlag=newDataModel.modelFlag.departleaderApproval;
	//部门经理
	console.log("================");
	console.log(scope.expertReview);
	console.log("*****************");
}
/**
 * 对专家列表与专家打分等信息等JSON字符串进行校验并转换为json对象
 * 如果没有到达阶段,数据为空,则转换为 bool值用于界面控制
 * **/
function expertScoreExchangeObj(data)
{
	//项目每个阶段,都包括了历史数据
	for(var key in data)
	{
		var state=data[key];
		var stateData=state[0];
		var state_show=isEmptyObject(stateData);
		if(!isEmptyObject(stateData))
		{
			var expertList_show=isEmptyObject(stateData.expertList);
			var experScore_show=isEmptyObject(stateData.expertScore);
			if(expertList_show)
			{
				data[key][0].expertList=expertList_show;
				
			}else{
				stateData.expertList=JSON.parse(data.create[0].expertList);
				data[key][0].expertList=stateData.expertList;
			}
			
			if(experScore_show)
			{
				data[key][0].expertScore=experScore_show;
				
			}else{
				stateData.expertScore=JSON.parse(data.create[0].expertScore);
				data[key][0].expertScore=stateData.expertScore;
			}
			
		}else{
			data[key]=state_show;
		}
		
		//data[key][0].expertList=stateData.expertList;
		//data[key][0].expertScore=stateData.expertScore;
	}
	return data;
}

