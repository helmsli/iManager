//决策委员会意见后台数据交互调用
function committeeApproal(step,data,taskId){
	try{
		var userInfo= localStorage.getItem("userInfo");
		userInfo=JSON.parse(userInfo);
		var scope=getAngularScope("initSeeData");
		scope.userId=userInfo.uid;
			var obj={	   
					"request.taskId":taskId,
					"request.conclusion":scope.suggestion.suggestionFlag,
					"request.comments": scope.suggestion.suggestionDescription,
					"request.userId": userInfo.uid,
			};
			committeeSuggestion(obj,function(data){
				if(data.result == 0){	
					window.history.go(-1);
					
					//window.location.replace('myAgencyTask.html');
				}
		    });	
	}catch(e){
		 log("fn:committeeSuggestionDate  STEP:"+step+"ERROR:"+e.messageInfo);
	}
}