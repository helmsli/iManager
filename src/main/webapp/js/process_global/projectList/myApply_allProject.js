var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', '$ocLazyLoad', function($scope) { 
	
	//表格标题
	$scope.titleList = [
			"项目编号",
			"项目名称",
			"申请时间",
			"项目状态",
			"操作"
	];
	$scope.pageFlag=true;
	//数据初始化--所有项目列表
    $scope.initTab=function(pageNum){
    	var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    getAllProjectSeeList(projectName,obj);
	}
    
    $scope.openMyapplySeeWin=function(projectId,state,isSeeView){
		openSeeWin(projectId,state,isSeeView)
	}
	
}]);


function openSeeWin(projectId,state,isSeeView){
	var parm=parseQueryString();
	var projectName =parm.projectName||""; 
	//url = "myApply_see.html?projectName="+projectName+"&projectId="+projectId+"&state="+state+"&seeState="+isSeeView+"&from="+from;
	url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&seeState="+isSeeView+"&state="+state+"&from="+from;
	location.href=url;
}









