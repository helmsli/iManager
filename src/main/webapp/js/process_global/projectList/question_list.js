var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
			"项目编号",
			"项目名称",
			"项目经理",
			"项目经理所在部门",
			"项目预算金额",
			"操作"
	];
	$scope.dataList=[];
	$scope.pageFlag=true;
	//数据初始化--我的申请项目	
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
        var obj=getMyProjectOptions(projectName,pageNum);
	    getAllQuestionListByServer(obj,getAllQuestionListCall);
	}
	
	function getAllQuestionListCall(data){
		var scope=getAngularScope("myDataController");
		var list=data.responseInfo.lists;
		var newList=[];
		if(list){
			for(var i=0;i<list.length;i++)
			{
				var projectInfo=JSON.parse(list[i].data3);//data3:尽职调查--项目基本信息
				var newListItem={
						taskId:list[i].taskId,
						projectId:projectInfo.projectId,
						budget:projectInfo.budget,
						projectName:projectInfo.projectName,
						projectManagerName:projectInfo.projectManagerName,
						leaderDepartment:projectInfo.leaderDepartment,
						serviceType:list[i].serviceType
				};
				newList[i]=newListItem;
			}
		}
	
		scope.dataList=newList;
		//分页
		if(data.result == 0){
			var dataLists=scope.dataList;
			var pageInfo=data.responseInfo.page;
			if(scope.pageFlag)
			{
				scope.pageFlag=false;
				pageNav.go(pageInfo.pageNum, pageInfo.pages);
			}
			if(dataLists.length == 0){
				$("#tabNoData").removeClass("hide");
			}else{
				$("#tabPageNav").removeClass("hide");
				scope.dataList=dataLists;
				scope.$applyAsync(scope.dataList);
				if(scope.flag){
			    	scope.flag = false;
			    }
			}
		}else{
			$("#tabNoData").removeClass("hide");
		}	
    }
	
	/***
	 * 查看尽职调查详细
	 * @param {Object} userId
	 */
	$scope.openQuestionDetail = function(projectId){
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url = "question_detail.html?projectName="+projectName+"&projectId="+projectId+"&from="+from;
		location.href=url;
	}

}]);