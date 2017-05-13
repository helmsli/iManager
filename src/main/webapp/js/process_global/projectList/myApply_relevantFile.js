var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', '$ocLazyLoad', function($scope) { 
	//表格标题
	$scope.titleList = [
			"编号",
			"名称",
			"创建人",
			"创建时间",
			"操作"
	];
	
    $scope.pageFlag=true;
	//数据初始化--我的申请项目	
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj={
	    		  "request.projectId":myParm.projectId
	    };
	    console.log(myParm);
	    //obj = getMyProjectOptions(projectName,pageNum);
	    getMyApplyRelevantFileDataList(projectName,obj);
	    
	}
	
	$scope.openMyapplySeeWin=function(projectId){
		openSeeWin(projectId)
	}
	
	$scope.cancelMyapplySeeWin=function(){
		window.history.go(-1);
	}
	
}]);

/***
 * 查看尽职调查
 * 
 */
function openSeeWin(projectId){
	var parm=parseQueryString();
	var projectName =parm.projectName||""; 
	url = "question_detail.html?projectName="+projectName+"&projectId="+projectId+"&from="+from+"&projectName="+projectName;
	location.href=url;
}
