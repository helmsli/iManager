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
	var roleIds={
			threeLeader:22
	}
	$scope.pageFlag=true;
	//数据初始化--待申报的项目/我的发布的项目
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    console.log("项目");
	    console.log(obj);
	    getDepartmentReleaseDataList(projectName,obj);
	}
	//数据初始化--
	$scope.initMyTaskForThreeLeader=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    console.log("任务");
	    console.log(obj);
	    getDepartmentReleaseDataForThreeLeaderList(projectName,obj);
	}
	//数据初始化--待申报的项目
	$scope.initMyTaskForShenBao=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    console.log("任务");
	    console.log(obj);
	    getDepartmentReleaseDataForShenBaoList(projectName,obj);
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
		var userInfo= localStorage.getItem("userInfo");
		roleIdValue=JSON.parse(userInfo).roleIds[0];
		console.log(userInfo);
		if(roleIdValue==roleIds.threeLeader){
			url="departmentCreate.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		}else{
			url="newApply.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		}
		
	   //url="departmentRelease_detail.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		
		location.href=url;
	}
	
}]);



