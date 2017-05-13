var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
			"申报编号",
			"申报主题",
			"申报状态",
			"申报时间",
			"操作"
	];
	$scope.pageFlag=true;
	//数据初始化--我的申请项目	
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    getThreeleaderDeclareDataList(projectName,obj);
	}

	/*
	 * 查看经理发布的项目
	 */
	$scope.openSeeDepartLeaderRelease = function(projectId,complete)
	{
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||"";
		var seeState=true;
		//url="departmentRelease_detail.html?projectName="+projectName+"&publishId="+publishId+"&seeState="+seeState+"&from="+from;
		url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&seeState="+seeState+"&from="+from;
		location.href=url;
	}
	
}]);
