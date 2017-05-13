App.controller('initSeeData', function($scope) {
	//通过URL得到projectId和state
	//state  001:指定评审专家, 002:专家评分, 003:决策委员会评审, 004:部门经理评审, 005:待修改, 006:结项
	// uid   1:administrator 2:projectManager 3:officer 4:departLeader 5:committee
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	$scope.projectId=projectId;
	var state=parm.state;
	$scope.state=state;
	$scope.from=parm.from;
	$scope.complete=parm.complete;
	var userInfo= localStorage.getItem("userInfo");
	userId=JSON.parse(userInfo);
	$scope.userId=userId.uid;
	$scope.editFlag=parm.edit;
	$scope.templateName="";
	//要是角色是projectManagerA则把提交的按钮隐藏
	
	/*function submittHide(){
		if($scope.userId==4){
			$("#submmitButton").hide();
		}
	}*/
    $scope.initReview=function (){
    	/*submittHide();*/
    	$scope.initTemplatePath();
    	initBaseProject();
	}
    
    $scope.initTemplatePath=function()
    {
		var parm = parseQueryString();
		var projectName=parm.projectName;
		var templateName=getDetailTempletPath(projectName);
		//加载模板
		$scope.templateName=templateName;
    }
    
   var from=$scope.from
	/*
	 * 2017-01-04  杨秀国
	 * */
	//我的申请项目--查看
	var cancelWin = function(){
		window.location.replace(from);
	}
	//已完成项目--查看
	var cancelMyapplyCompletedWin = function(){
		window.location.replace(from);
	}
	//我的代办任务--查看
	var cancelMyAgencyTaskWin = function(){
		window.location.replace(from);
	}
	//已完成任务--查看
	var cancelMyagencyTaskWin = function(){
		window.location.replace(from);
	}
	//相关资料--查看
	var openMyApplyRelevantFile=function(projectId,state){
		openMyApplyRelevantFileSeeWin(projectId,state)
	}
	$scope.cancelMyapplySeeWin=function(){
		var currentUserId= localStorage.getItem("userInfo");
			currentUserId=JSON.parse(currentUserId);
			currentUserId=currentUserId.uid;
		if(currentUserId != 4){  //Id:4为项目经理发布   Id:5为项目管理员  Id:6为决策委员会   Id:为部门经理审批
			cancelMyagencyTaskWin();
		}else{
			cancelWin();
		}
	}
	//相关资料跳转
	$scope.openMyApplyRelevantFileSeeWin=function(projectId,state){
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url = "myApply_relevantFile.html?projectName="+projectName+"&projectId="+projectId+"&state="+state+"&from="+from;
		location.href=url;
	}
	
	
	//2017.1.5常贵春 按钮根据不同的状态码做绑定
	$scope.stateDisplay=function (){
		//这里需要多测试
		subMitFnCall(mainParm.detailStep,mainParm.data);
	}
	
});



