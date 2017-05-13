App.controller('projectModel', ['$scope',initScope]);
function initScope($scope) {
	$scope.aproveFlag=true;
	$scope.templatePath="";
	$scope.viewPanel="";//当前要查看的模块
	$scope.gloablParm={};
	$scope.registerJSFlag={};
	//项目基础信息
	$scope.projectInfo={};
	$scope.jzdcPath="";
	//分数模型
	$scope.expertScoreList=[0,1,2,3,4];
	//界面初决加载初始化模型
	$scope.initModel=function()
	{
		initMainControll();
		initProjectInfo();
	}
	//统一提交入口
	$scope.stateDisplay=function(callBack)
	{
		subMitFnCall(callBack);
	}
	
	//关闭按扭功能
	$scope.cancelMyapplySeeWin=function()
	{
		window.history.go(-1);
		
	}
	
	/**
	 * 附件下载走的方法
	 */
	$scope.attachDownLoad=function(annexName){
		 var url="/projectAnnex/fileDownLoad";
		     url= getBasePath()+url;
		     url=url+"?request.annexName="+annexName;
		     //console.log(url);
		     location.href =url;
	}
}


/**
 *界面控制主流程
 * **/
function initMainControll()
{
	var parm = parseQueryString();
	var state= getProjectState();
	var templatePath=getApproveFilePath(state);
	initScriptFile(state);
	initViewModel();
	initJzdcFilePath();
	if(!parm.seeState)
	{
		initTemplatePath(templatePath);
	}
}

function initViewModel()
{
	var projectId=0;
	//setViewModel(ProjectFlowInfoData);
	var parm = parseQueryString();
	if(parm.applicationId){
		projectId=parm.applicationId;
	}else{
		projectId=parm.projectId;
	}
	var obj={
			"request.projectId":projectId
	}
	console.log(obj);
	//从后台取到数据，显示在前台as
	getCommonBizByServer(obj,function(data){
		//console.log("测试一下getCommonBizByServer的obj");
		//console.log(obj);
		if(data.result == 0){
			console.log("测试一下getCommonBizByServer");
			console.log(data);
			setViewModel(data);
		}
	});
}

function initJzdcFilePath()
{
	var scope=getAngularScope("projectModel");
	var gloablParm=scope.gloablParm;
	var url = "../question_detail.html?projectName="+gloablParm.projectName+"&projectId="+gloablParm.projectId;
	//var url = "myTaskAgent_question.html?projectName="+gloablParm.projectName+"&projectId="+gloablParm.projectId+"&taskId="+gloablParm.taskid+"&state="+gloablParm.state;
	scope.jzdcPath=url;
}


/**
 * 获取步聚名称
 * **/
function getProjectState()
{
	var parm = parseQueryString();
	var state=parm.state;
	var scope=getAngularScope("projectModel");
	scope.gloablParm=parm;
	if(parm.seeState=="true" || parm.seeState==1)
	{
		scope.aproveFlag=false;
	}else{
		scope.aproveFlag=true;
		viewPanel="aprove";
	}
	return state;
}
/**
 * 获取aprove
 * **/
function getApproveFilePath(state)
{
	var miniSateName="STEP_"+state+"_PATH";
	//COFING_PATH变量存储在CONFIG_projectpath.js文件中
	
	return CONFIG_PATH[miniSateName];
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
	//alert("scope.templatePath:"+scope.templatePath);
	//加载模板
}

/***
 * 获取资源文件名称资源文件
 * 参数:state
 * 返回:文件路径
 * */
function getPageScriptFilePath(state) {
	var FileName="STEP_"+state+"_PATH";
	return SCRIPTFile[FileName];
}

function getResourceFile(fileName)
{
	var filePath=getPageScriptFilePath(state);
	var basePath=getBasePath();
	filePath=basePath+filePath;
	return filePath;
}
/***
 * 加载资源文件
 * 参数:state
 * 描述:调用loadSCRIPT文件加载资源完成后回调
 * */
function initScriptFile(state)
{
	var filePath=getPageScriptFilePath(state);
	var basePath=getBasePath();
	filePath=basePath+filePath;
	var fileId="script_"+state;
	//console.log(filePath);
	loadFileScript(fileId,filePath,function(){
		//console.log(7777777777);
		//globalRegisterJs(state);
	});
}


/**
 * 全局HTML加载注册函数
 * 参数：sate,callBack回调函数;
 * **/
function globalRegisterHtml(state)
{
	
	var scope=getAngularScope("projectModel");
	//console.log("HTML加载完毕");
	var stateName=getStateName(state);
	if(!scope.registerHtmlFlag)
	{
		scope.registerHtmlFlag={};	
	}
	scope.registerHtmlFlag[stateName]=true;
	
	//如果HTML加载完毕需要执行JS registerHtmlCall
	//console.log("++++++++++++++++++++++++++");
	//console.log(scope.registerHtmlCall);
	
	if(typeof scope.registerHtmlCall !="undefined" && typeof scope.registerHtmlCall[stateName] !="undefined")
		{
		
		var callBack = scope.registerHtmlCall[stateName];
			if(callBack)
				{
				  //console.log("register html:*******");
					callBack();
				}
		}
}
/**
 * 全局HTML加载注册函数
 * 参数：获取state全局变量KEY值
 * 返回:相对应的state key值;
 * **/
function getStateName(state)
{
	return "state_"+state;
}

/**
 * 全局注册函数JS
 * 参数：sate,callBack回调函数;
 * **/
function globalRegisterJs(state,callBack)
{
	var scope=getAngularScope("projectModel");
	var stateName=getStateName(state);
	//console.log("js加载完毕");
	if(!scope.registerJSFlag)
	{
		scope.registerJSFlag={};
	}
	
	if(!scope.registerHtmlCall)
	{
		scope.registerHtmlCall={};
		
	}
	//console.log(scope.registerHtmlCall);
	//console.log("==================");
	//console.log(scope.registerHtmlCall[stateName]);
	scope.registerHtmlCall[stateName]=callBack;
	//console.log(scope.registerHtmlCall[stateName]);
	scope.registerJSFlag[stateName]=true;	
	
}


/**
 * 获取项目任务信息
 * modelFlag:bool 控制界面显示,modelData:原始数据
 * **/
function setViewModel(data)
{
	var responseInfo=data.responseInfo;
	if(responseInfo.commonBizList!=undefined){
		var lists=responseInfo.lists;
		var i=0;
		var listsJSON=projectResultExchangeJson(responseInfo.lists);
		var scope=getAngularScope("projectModel");
		scope.oldProjectFlowInfo=lists;
		scope.projectFlowInfo=listsJSON;
		scope.projectFlowInfo.show=true;
	};
	
}

/***
 * 流程详细显示控制函数
 * 查询commonbiz的所有数据，根据这些数据决定显示哪些流程
 * 将数据中的result字段,转换为JSON的数据格式,并在每个LIST子任务下增加
 * service_type_show字段:用来控制显示和隐藏
 * 参数:lists
 * 返回:lists;
 * **/
function projectResultExchangeJson(lists)
{
	for(var i=0; i<lists.length; i++)
	{
		
		var indexKey = i;
		var serviceTypeShow="show";
		
		if(!lists[indexKey].serviceType)
		{
			lists[indexKey].serviceType=false;
			lists[indexKey][serviceTypeShow]=false;
		}else{
			//APPROVE_TITLE
			lists[indexKey][serviceTypeShow]=true;
			var serverType=lists[indexKey].serviceType;
			lists[indexKey].title=APPROVE_TITLE[serverType];
		}
		
		for(var key in lists[i])
		{
			if(key==="result" || key.indexOf("data")>=0)
			{
				if(!isEmptyObject(lists[indexKey][key]))
				{
					//console.log(lists[indexKey][key]);
					lists[indexKey][key]=JSON.parse(lists[indexKey][key]);
				}		
				
			}
		}
		//
		{
			var result = lists[indexKey]["result"];
			if(result)
			{
			   if(result.hasOwnProperty("isAttach")&&result.isAttach==APP_ContainAttach.attach)
			   {
				   var fileInfo = lists[indexKey]["data2"]||{};
				   if(!isEmptyObject(fileInfo))
				   {
					   result.isAttach = true;
					   lists[indexKey]["filepath"]=fileInfo;
				   }
					   
				}
			}
		}
	}
	return lists;
}


/*
 * 
 */
function parseCommonBizAttach(commonBiz)
{
	if(!isEmptyObject(commonBiz))
	{
		var fileInfo = JSON.parse(dataInfoStr);
		return fileInfo;
	}
	else
	{
		return "";	
	}
	

}

/**
 * 将初始项目内容信息模型转换为需要的数组模型对象
 * modelFlag:bool 控制界面显示,modelData:原始数据
 * **/
function initProjectInfo()
{

	var parm = parseQueryString();
	var projectId=0;
	if(parm.projectId){
	   projectId=parm.projectId;
	}else{
		projectId=parm.applicationId;
	}
	var obj={
			"request.applicationId":projectId
	}
	//从后台取到数据，显示在前台as
	threeManageAfterDeclareSeeByServer(obj,function(data){
		if(data.result == 0){
			setUIData(data);
		}
	});
}


 function initFileStateListener(callBack)
{
	
	var scope=getAngularScope("projectModel");
	var state=scope.gloablParm.state;
	var stateName=getStateName(state);
	//console.log(state);
	//指定评审专家不需要经过任何的处理
	//console.log("initFileStateListener js:*******");
	if(typeof scope.registerHtmlFlag!="undefined" && typeof scope.registerHtmlFlag[stateName] !="undefined")
	{
		//console.log("register js:*******");
		callBack(scope);
	}
	else
	{
		globalRegisterJs(scope.gloablParm.state,callBack);
	}
}


function setUIData(data)
{
	var scope=getAngularScope("projectModel");
		scope.dataDetail=data.responseInfo.departleaderPublish;
	var dataDetail=scope.dataDetail;
	var data2=dataDetail.data2;
	//console.log(data);
	if(data2){
		scope.isAttach=true;
		scope.dataDetailAttach=JSON.parse(data2);
	}
     scope.user=data.responseInfo.user;
     scope.threeleaderApplication=data.responseInfo.threeleaderApplication;
     
     //by yxg 2017-03-02
     scope.applyDetail=data.responseInfo.threeleaderApplication;
     var applyDataDeatil=scope.applyDetail;
     var applyData2=applyDataDeatil.data2;
     if(applyData2){
    	 scope.applyIsAttach=true;
 		 scope.ApplyDetailAttach=JSON.parse(applyData2);
     }
     scope.currentState=JSON.parse(scope.threeleaderApplication.currentState);
     scope.threeleaderApplication.stateName=scope.currentState.stateName;
     console.log("打印申报者的信息");
     console.log(scope.user);
     scope.user.sex=(scope.user.sex==0?'男':'女');
     scope.user.cardtype=(scope.user.cardtype==0?'身份证':'护照');
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
		var stateData=data[key][0];
		var state_show=isEmptyObject(stateData);
		if(!state_show)
		{
			
			
			var expertList_show=isEmptyObject(stateData.expertList);
			var experScore_show=isEmptyObject(stateData.expertScore);
			//console.log(stateData+"==="+key+"==="+expertList_show+"=-="+experScore_show);
			
			if(expertList_show)
			{
				data[key][0].expertList=expertList_show;
			}else{
				
				if(data.create[0].expertList)
				{
					//console.log("++++++++++++++++++");
					//console.log(data[key][0].expertList);
					stateData.expertList=JSON.parse(data[key][0].expertList);
					//console.log("******************");
					//console.log(stateData.expertList);
					data[key][0].expertList=stateData.expertList;
				}
			}
			
			if(experScore_show)
			{
				data[key][0].expertScore=experScore_show;
				
			}else{
				if(data[key][0].expertScore)
				{
					stateData.expertScore=JSON.parse(data.create[0].expertScore);
					data[key][0].expertScore=stateData.expertScore;
				}
			}
			
		}else{
			data[key]=false;
		}
		//data[key][0].expertList=stateData.expertList;
		//data[key][0].expertScore=stateData.expertScore;
	}
	return data;
}






