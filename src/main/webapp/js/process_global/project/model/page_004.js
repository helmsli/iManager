//部门经理审批的后台调用
  function deparLeaderApproal(step,data,taskId){
	  try{
		  var userInfo= localStorage.getItem("userInfo");
			userInfo=JSON.parse(userInfo);
			var scope=getAngularScope("initSeeData");
			var obj={	   
					"request.taskId":taskId,
					"request.conclusion":scope.managerSuggestion.suggestionFlag,
					"request.comments": scope.managerSuggestion.suggestionDescription,
					"request.userId": userInfo.uid
		
			};
			managerSuggestion(obj,function(data){
				if(data.result == 0){
					window.history.go(-1);
					//window.location.replace('myAgencyTask.html');
				}
		    });
	 }catch(e)
	 {
		  log("fn:managerSuggestionDate  STEP:"+step+"ERROR:"+e.messageInfo);
	 }
}
