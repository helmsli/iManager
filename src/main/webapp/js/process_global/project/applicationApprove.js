App.controller('projectModel', ['$scope',initScope]);
function initScope($scope) {
	$scope.aproveFlag=true;
	$scope.templatePath="";
	$scope.viewPanel="";//当前要查看的模块
	$scope.gloablParm={};
	$scope.show_btn_refresh=false;
	$scope.show_btn_application=false;
	var parm = parseQueryString();
	$scope.gloablParm=parm;
	console.log("***********************");
	
	console.log($scope.gloablParm);
	//$scope.gloablParm.state= "application";
	$scope.registerJSFlag={};
	//项目基础信息
	$scope.projectInfo={};
	$scope.jzdcPath="";
	$scope.suggestion={};
	$scope.suggestion.result=4;
	 $scope.setPersonFlag=false;
	//分数模型
	$scope.expertScoreList=[0,1,2,3,4];
	//界面初决加载初始化模型
	$scope.initModel=function()
	{
		initMainControll();
		
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
	var scope=getAngularScope("projectModel");
	var state=scope.gloablParm.state;
	var templatePath=getApproveFilePath(state);
	console.log(templatePath);
	initScriptFile(state);
	initTemplatePath(templatePath);
	
	
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
				 console.log("register html:*******");
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
	//console.log(lists);
	if(lists!=undefined){
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
 /***
	 *  变更时统一的数据处理，把data1和data2和data3处理变成以前查看的数据
	 * */
	function processData(data1,data2){
		for(var i in data2){
			data1[i]=data2[i];
		}
		return data1;
	}
	//变更用来处理到的数据更容易比较
	function changeObj(compareNewData,newObject){
		 var projectExtInfo=JSON.parse(newObject.projectExtInfo);
		 compareNewData.taskplan=projectExtInfo.taskplan;//项目实施计划及输出表
		 compareNewData.taskDescribe=projectExtInfo.taskDescribe;
		 compareNewData.taskbackground=projectExtInfo.taskbackground;
		 compareNewData.taskgoal=projectExtInfo.taskgoal;
		 compareNewData.taskDanger=projectExtInfo.taskDanger;
		 compareNewData.taskcreate=projectExtInfo.taskcreate;
		 compareNewData.projectTaskDetail=newObject.projectTaskDetail;//项目需要的准备条件
		 compareNewData.projectCosts=newObject.projectCosts;//项目预算
		 compareNewData.email=newObject.email;
		 compareNewData.completeTime=newObject.completeTime;
		 compareNewData.startTime=newObject.startTime;
		 compareNewData.subcategory=newObject.subcategory;
		 compareNewData.cycleType=newObject.cycleType;
		 compareNewData.projectName=newObject.projectName;
		 compareNewData.telno=newObject.telno;
		 return compareNewData;
	 }
 function setUIBgData(data){
	 
	 var scope=getAngularScope("projectModel");

		console.log("我可以查看变更的数据了");
		console.log(data);
	 var data1=JSON.parse(data.responseInfo.commonBiz.data1);
	 var data2=JSON.parse(data.responseInfo.commonBiz.data2);
	 var data3=JSON.parse(data.responseInfo.commonBiz.data3);
	 var data5=JSON.parse(data.responseInfo.commonBiz.data5);
	 scope.data5=JSON.parse(data.responseInfo.commonBiz.data5);
	 var stateName=JSON.parse(data.responseInfo.commonBiz.status).stateName;

	
	 var oldObject=(scope.data5).responseInfo.project;
	 var newObject={};
	 prepaireCondition={};
	 for(var i in data1){
		 newObject[i]=data1[i];
		 
	 }
	 for(var i in data2){
		 newObject[i]=data2[i];
	 }
	 for(var i in data3){
		 newObject[i]=data3[i];
	 }
	 var compareOldData={};
	 var compareNewData={};
	 compareNewData=changeObj(compareNewData,newObject);
	 compareOldData=changeObj(compareOldData,oldObject);
	 console.log("比较一下");
	 console.log(compareNewData);
	 console.log(compareOldData);
	 for(var i in compareNewData){
		 if(i=="completeTime"||i=="startTime"){
			 compareNewData[i]=compareNewData[i].split(" ")[0];
			 compareOldData[i]=compareOldData[i].split(" ")[0];
			
		 }
		 if(compareNewData[i]!=compareOldData[i]){
			 $("[data-datename='"+i+"']").addClass("red");
		 }
	 }
	 data1=processData(data1,data2);
	 scope.project=processData(data1,data3);
		var projectExtInfo=JSON.parse(scope.project.projectExtInfo);
		
		//填写申报单位信息
		try{
			//console.log(JSON.stringify(dataLists[i]));
			//console.log("***************：" +dataLists[i].projectTaskDetail );
			var projectdetail=JSON.parse(scope.project.projectTaskDetail);
			
			
			if(projectdetail){
				scope.project.project_detail=projectdetail;
				//console.log("***************0000:"+ dataLists[i].project_detail.declarationUnit);
				
			    
			}   	
		}
		catch(err)
		  {
		  //在这里处理错误
		  }
		
		try{
			scope.project.CycleType=JSON.parse(scope.project.cycleType)[0].value;
			scope.project.subcategory=JSON.parse(scope.project.subcategory)[0].value;
			scope.project.endTime=scope.project.completeTime;
			scope.project.projectApplyTime=oldObject.projectApplyTime;
		    scope.project.stateName=stateName;
		}catch(e){
			log(e+"initData:"+e.messageInfo);
		}
		console.log(scope.project);
		scope.projectExtInfo=projectExtInfo;
		scope.Taskplan=JSON.parse(projectExtInfo.taskplan);
		scope.ProjectCosts=JSON.parse(scope.project.projectCosts);
		scope.projectTaskDetail=JSON.parse(compareNewData.projectTaskDetail);
		scope.$applyAsync(scope.ProjectTaskDetail);
	 
	 /*
	 var scope=getAngularScope("projectModel");
	 var data1=JSON.parse(data.responseInfo.commonBiz.data1);
	 var data2=JSON.parse(data.responseInfo.commonBiz.data2);
	 var data3=JSON.parse(data.responseInfo.commonBiz.data3);
	 var project=data1;
	 scope.project=project;
	 console.log("测试我的变更的data");
	 console.log(data);
	 
	 scope.ProjectCosts=data2.projectCosts;
	 scope.projectTaskDetail=data3.projectTaskDetail;*/
 }
function setUIData(data)
{
	var scope=getAngularScope("projectModel");
	var responseInfoData=data.responseInfo;
	scope.newApplyAttachment=JSON.parse(responseInfoData.project.projectExtInfo).newApplyAttachment;
	scope.newApplyAttachmentShow=true;
	console.log(scope.newApplyAttachment);
	var project=responseInfoData.project;
	var projectExtInfo=JSON.parse(project.projectExtInfo);
	var projectInfo=project;
	var cycleType=JSON.parse(projectInfo.cycleType);
	try{
		project.CycleType=JSON.parse(project.cycleType)[0].value;
		project.subcategory=JSON.parse(project.subcategory)[0].value;
	}catch(e){
		console.log(e+"initData:"+e.messageInfo);
	}
	//获取项目扩展信息，来自申报书
	try{
		//console.log(JSON.stringify(dataLists[i]));
		//console.log("***************：" +dataLists[i].projectTaskDetail );
		var projectdetail=JSON.parse(projectInfo.projectTaskDetail);
		
		
		if(projectdetail){
			project.project_detail=projectdetail;
			//console.log("***************0000:"+ dataLists[i].project_detail.declarationUnit);
			
		    
		}   	
	}
	catch(err)
	  {
	  //在这里处理错误
	  }
	
	
	scope.project=project;
	var projectTaskDetail=project.projectTaskDetail;
	scope.projectTaskDetail=JSON.parse(projectTaskDetail)
	var mainCurrentState=JSON.parse(project.mainCurrentState);
	scope.project.stateName=mainCurrentState.stateName;
	scope.projectExtInfo=projectExtInfo;
	scope.Taskplan=JSON.parse(projectExtInfo.taskplan);
	scope.ProjectCosts=JSON.parse(project.projectCosts);
	scope.ProjectTaskDetail=JSON.parse(project.projectTaskDetail);	
	projectInfo.cycleType=cycleType.value;
	projectInfo.startTime=projectInfo.startTime;
	projectInfo.endTime=projectInfo.completeTime;
	scope.project=projectInfo;

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

