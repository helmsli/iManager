var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
			"项目编号",
			"项目名称",
			"申请时间",
			"项目状态",
			"操作"
	];
	$scope.pageFlag=true;
	//数据初始化--已完成的项目-列表
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    getUserFinishedTasklist(projectName,obj);

	}
	
	$scope.openMyapplySeeWin=function(projectId,state,complete,isSeeView){
		openSeeWin(projectId,state,complete,isSeeView)
	}
	/***
	 * 查看已完成项目
	 * @param {Object} userId
	 */
	var openSeeWin = function(projectId,state,complete,isSeeView){
		var url="",
			addFlag=false;
		if(!projectId){
			projectId = "";
			addFlag=true;
		}
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		//url = "myApply_see.html?projectName="+projectName+"&projectId="+projectId+"&state="+state+"&complete="+complete+"&seeState="+isSeeView+"&from="+from;
		url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&seeState="+isSeeView+"&state="+state+"&from="+from;
		
		location.href=url;
	}

}]);