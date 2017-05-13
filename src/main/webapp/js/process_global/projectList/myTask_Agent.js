var nodeInfo = {};
var shengbaoState=["pm_xiangmugly_chushen","pm_juecewyh_pingshen","pm_bumenjl_pingshen","pm_sanjibm_xg","pm_bumenjlfb_end"];
var shengbaobohui=["pm_sanjibm_xg"];
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
			"任务编号",
			"任务名称",
			"任务创建时间",
			"项目名称",
			"操作"
	];
	$scope.pageFlag=true;
	//数据初始化--我的申请项目	
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    getMyAgentTaskDataList(projectName,obj);
	}
	
	
	
	$scope.openMyTaskQuestionWin=function(projectId,taskid,state){
		openMyTaskQuestion(projectId,taskid,state)
	}
	
	$scope.openMyapplySeeWin=function(projectId,taskid,state){
		openSeeWin(projectId,taskid,state)
	}
	$scope.openNewMyapplySeeWin=function(projectId,taskid,state,dataId){
		openNewSeeWin(projectId,taskid,state,dataId)
	}
	//提交月度报告
	$scope.openPeriodReport=function(projectId,stateTaskId,state,projectNameTittle){
		console.log(projectNameTittle);
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url="projectMonthProgress.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+stateTaskId+"&state="+state+"&projectNameTittle="+encodeURI(projectNameTittle)+"&from="+from;
		location.href=url;
	}
	//提交周期监测
	$scope.openPeriodMonitor=function(projectId,stateTaskId,state,projectName){
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url="committeeMonitor.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+stateTaskId+"&state="+state+"&projectNameTittle="+projectName+"&from="+from;
		location.href=url;
	}
	//修改，跳到修改
	$scope.openModefiySeeWin=function(projectId,dataId,changeCurrentState){
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url = "newApply.html?projectName="+projectName+"&projectId="+projectId+"&dataId="+dataId+"&changeCurrentState="+changeCurrentState+"&from="+from;
		location.href=url;
	}
	
	//办理变更，跳到变更的我的查看
	$scope.bgApprove=function(projectId,state,bg,taskid){
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&bg="+bg+"&taskId="+taskid+"&state="+state+"&from="+from;
		location.href=url;
	}
	/***
	 * 我的待办项目-办理页面
	 * @param {Object} userId
	 */
	var openSeeWin = function(projectId,taskid,state){
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		//var state="B";
		//如果state状态中含有b，那么证明是变更的审批，则会跳到变更的办理审批中去。
		if(state.indexOf("B")!=-1){
			url="myApply_seeBg.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&from="+from; 
		}else{
			url = "myApply_see.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&from="+from;
		}
		location.href=url;
	}
	/***
	 * 我的待办项目-办理页面
	 * @param {Object} userId
	 */
	
	var openNewSeeWin = function(projectId,taskid,state,dataId){
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		//var state="B";
		//如果state状态中含有b，那么证明是变更的审批，则会跳到变更的办理审批中去。
	/*	if(state.indexOf("B")!=-1){
			//url="myApply_seeBg.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&from="+from; 
		}else{
			//url = "myApply_see.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&from="+from;
			
		}*/
		//alert(state);
		if(state=="committeeApprovalChange"||state=="departleaderApprovalChange"){
			url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&bg="+1+"&state="+state+"&from="+from;
		}else{
			url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&from="+from;
		}
	
		/*for(var i in shengbaoState){
			if(shengbaoState[i]==state){
				url="projectManager/project_Release_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&from="+from;
			}
		}*/
	/*	for(var i in shengbaobohui){
			if(shengbaobohui[i]==state){
				url="departmentRelease_Edit.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&dataId="+dataId+"&from="+from;
				//alert(dataId);
			}
		}*/
		location.href=url;
	}
	
	/***
	 * 我的待办项目-尽职调查
	 * @param {Object} userId
	 */
	var openMyTaskQuestion = function(projectId,taskid,state){
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url = "myTaskAgent_question.html?projectName="+projectName+"&projectId="+projectId+"&taskId="+taskid+"&state="+state+"&from="+from;
		location.href=url;
	}

}]);