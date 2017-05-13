var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
			"任务编号",
			"任务名称",
			"任务完成时间",
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
	    getMyAgentTaskFinishedDataList(projectName,obj);
	}
	/***
	 * 查看已完成项目
	 * @param {Object} userId
	 */
	var openSeeWin = function(projectId,state,dataId,complete)
	{
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
			addFlag=false;
		if(!projectId){
			//如果没有project=""那么说明是申报完成的
			projectId = "";
			addFlag=true;
			url="projectManager/project_Release_detail_view.html?projectName="+projectName+"&applicationId="+dataId+"&seeState=1&state="+state+"&from="+from;
		}else{
			url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&seeState=1&state="+state+"&from="+from;
			
		}
		location.href=url;
		
	}
	
	
	$scope.openMyapplySeeWin=function(projectId,state,dataId,complete)
	{
		openSeeWin(projectId,state,dataId,complete);
	}
	
	//新查看
	
	$scope.openNewMyapplySeeWin=function(projectId,state,complete)
	{
		openNewSeeWin(projectId,state,complete)
	}
	
	
	/***
	 * 修改申请项目
	 * @param {Object} userId
	 */
	var openEditWin = function(projectId)
	{
		var url="",
			addFlag=false;
		if(!projectId){
			projectId = "";
			addFlag=true;
		}
		url = "newApply.html?projectId="+projectId+"&addFlag="+addFlag+"&from="+from;
		location.href=url;
	}
	$scope.openMyapplyEditWin=function(projectId)
	{
		openEditWin(projectId)
	}

}]);