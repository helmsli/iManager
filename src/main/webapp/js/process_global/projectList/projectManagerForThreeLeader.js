var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
			"申报编号",
			"申报类型",
			"申报主题",
			"发布时间",
			"操作"
	];
	$scope.pageFlag=true;
	//数据初始化--待申报的项目/我的发布	
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    console.log(obj);
	    getDepartmentReleaseDataList(projectName,obj);
	}

	/*
	 * 查看经理发布的项目
	 */
	$scope.openSeeDepartLeaderRelease = function(publishId,complete)
	{
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||"";
		var seeState=true;
		url="departmentRelease_detail.html?projectName="+projectName+"&publishId="+publishId+"&seeState="+seeState+"&from="+from;
		location.href=url;
	}
	
	/*
	 * 申报经理发布的项目
	 */
	$scope.declareDepartLeaderRelease = function(publishId,complete)
	{
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
	   //url="departmentRelease_detail.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		url="newApply.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		location.href=url;
	}
	
}]);
