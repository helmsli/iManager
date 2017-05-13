/**
 * 统一处理用户的项目的状态；
 */

function processProjectState(dataList)
{
    for(var i =0;i<dataList.length;i++)
    { 
        var data = dataList[i];
       // console.log("处理dataList");
        //console.log(data);
        if(data.mainCurrentState&&data.mainCurrentState!=null)
        {
        	var stateObject = JSON.parse(data.mainCurrentState);
        	dataList[i].state= stateObject.state;	
        	dataList[i].stateName = stateObject.stateName;
        	dataList[i].stateTaskId = stateObject.taskId;
        }else{
            if(data.stateInfo!=undefined){
        	var stateObject = JSON.parse(data.stateInfo);
        	dataList[i].state= stateObject.state;	
        	dataList[i].stateName = stateObject.stateName;
        	dataList[i].stateTaskId = stateObject.taskId;
        	
        }
        	
        }
        
    }
    return dataList;
}


/**
 * 统一处理任务项目的状态；
 */

function processTaskState(dataList)
{
    for(var i =0;i<dataList.length;i++)
    {
        var data = dataList[i];
        if(data.stateInfo&&data.stateInfo!=null)
        {
        	var stateObject = JSON.parse(data.stateInfo);
            data.state= stateObject.state;	
            data.stateName = stateObject.stateName;
            data.stateTaskId = stateObject.taskId;
            //data.mainCurrentState = data.stateName;
        }
    }
    return dataList;
}

/**
 * return:  允许变更 ，变更等待审批(决策委员会，部门经理)，变更已经审批通过，提交新的变更，变更驳回修改，
 */
function changeChangeAction(changeStatus,mainCtrolStata)
{
	/*console.log(5567778);
	console.log(changeStatus);
	console.log(mainCtrolStata);*/
	var scope=getAngularScope("myDataController");
	scope.changSata={
			"changStep_create":"B001",
			"changStep_committee_approval":"B002",
			"changStep_manager_approval":"B003",
			"changStep_need_modify":"B004",
			"changStep_comfirm":"B005",
			"changStep_end":"endChange"
	}
	
	scope.mainCtrolStata={
		//需要修改的时候
		"mainCtrolStata_can_modify":"pmModifyMaterial",
		//需要尽职调查的时候，
		"mainCtrolStata_need_survey":"006",
		"mainCtrolStata_need_survey_view":"008",
		//需要变更审批的时候
		"mainCtrolStata_can_approveChangeCommitte":"committeeApprovalChange",
		"mainCtrolStata_can_p_button_EditChange":"modifyChange",
		"mainCtrolStata_can_departleaderApprovalChange":"departleaderApprovalChange",
		
		//需要提交周期性报告
		"mainCtrolStata_need_period_report":"submitMonthlyReport",
		"mainCtrolStata_need_period_monitor":"submitMonthlyCheck",
		"mainCtrolStata_need_middle_report":"000",
		"mainCtrolStata_need_end_report":"000"
	}
	scope.buttonFlag={
			p_button_view:true,
		    pc_button_see:true,//变更查看（只要变更创建之后都是变更的查看，初始化应该是false，现在为了模拟数据显示为true）
			pc_button_create:false,
			pc_button_waitApproval:false,
			pc_button_cancel:false,
			pc_button_confirm:false,
			
			//我的代办任务列表的按钮
			pc_button_approval:false,//我的待办任务中的变更办理（只要变更创建之后都是变更的查看，初始化应该是false，现在为了模拟数据显示为true）
			p_button_survey:false,
			p_button_period_report:false,
			p_button_middle_report:false,
			p_button_end_report:false,
			p_button_survey_view:false,
			p_button_modify:false,
			pc_button_edit_create:false,
			p_button_approvalChange:false,
			p_button_EditChange:false,
	}
/*	var mainCtrolStataInt=parseInt(mainCtrolStata);
	var mainCtrolStataFlag=(mainCtrolStataInt==7||mainCtrolStataInt>7);
	if(mainCtrolStataFlag)
	{
		
		//可以申请变更
		var endChange=scope.changSata.changStep_end;
		if((changeStatus==null&&mainCtrolStataFlag)||(changeStatus==endChange&&mainCtrolStataFlag)){
			scope.buttonFlag.pc_button_edit_create=true;
		}
		//我的代办任务中的变更办理，默认的办理隐藏
		//To do
		//mainCtrolStataLowerFlag=mainCtrolStataFlag.toLowerCase();
		if(mainCtrolStataLowerFlag.indexOf("b")!=-1){
			pc_button_approval=true;
			p_button_approval=false;
		}
		switch(changeStatus){
		//变更驳回修改	
		case scope.changSata.changStep_need_modify:
			//console.log(scope.changSata.changStep_need_modify);
			scope.buttonFlag.pc_button_cancel=true;
			//scope.buttonFlag.pc_button_see=true;
			break;
		case scope.changSata.changStep_comfirm:
			scope.buttonFlag.pc_button_confirm = true; 
			//scope.buttonFlag.pc_button_see=true;
			break;
		default:
			scope.buttonFlag.pc_button_create=true;
		    //scope.buttonFlag.pc_button_see=true;
			break;
		}
	}*/

	//主流程中可不可以修改的查看
	if(mainCtrolStata==scope.mainCtrolStata.mainCtrolStata_can_modify){
		scope.buttonFlag.p_button_modify=true;
	}
	//尽职调查
	//alert(mainCtrolStata);
	//console.log(scope.mainCtrolStata.mainCtrolStata_need_period_report);
	switch(mainCtrolStata){

	case "jzdc":
		scope.buttonFlag.p_button_survey=true;
		scope.buttonFlag.p_button_approval=false;
		break;
	case scope.mainCtrolStata.mainCtrolStata_need_period_report:
		scope.buttonFlag.p_button_period_report=true;
		scope.buttonFlag.p_button_approval=false
		break;
	
	case scope.mainCtrolStata.mainCtrolStata_need_middle_report:
		scope.buttonFlag.p_button_middle_report=true;
		break;
	case scope.mainCtrolStata.mainCtrolStata_need_end_report:
		scope.buttonFlag.p_button_end_report=true;
		break;
		
	case scope.mainCtrolStata.mainCtrolStata_need_period_monitor:
		scope.buttonFlag.p_button__period_monitor=true;
		scope.buttonFlag.p_button_approval=false
		break;	
	default:
		scope.buttonFlag.p_button_approval=true;
		break;	
	}

	return scope.buttonFlag;
}

/**
 * 统一列表分页渲染初始化
 * */
pageNav.fn = function(pageNum){
	var scope=getAngularScope("myDataController");
	scope.initMyTaskForThreeLeader(pageNum);
 };
 
/***
 * 我的申请项目的渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsApplyListCall(data){
	 var scope=getAngularScope("myDataController");
	 scope.currentChangeState=[];
	 scope.mainCurrentState=[];
	 if(data.result == 0){
		var dataLists=processProjectState(data.responseInfo.lists);
        for(var i in dataLists){
        	scope.mainCurrentState[i]=dataLists[i].mainCurrentState;
        	var changeCurrentState=JSON.parse(dataLists[i].changeCurrentState);
        	var mainCurrentStateObj=JSON.parse(scope.mainCurrentState[i]);
        	if(changeCurrentState!=null){
        		//scope.currentChangeState[i]=changeCurrentState.state;
           
           	dataLists[i].changeCurrentState=changeCurrentState.state;
           	dataLists[i].buttonFlag=changeChangeAction(scope.currentChangeState[i],mainCurrentStateObj.state);
        	
        	}
        	dataLists[i].assignName=mainCurrentStateObj.assignName;
        }
        //得到所有的按钮控制标志绑定到前台
        scope.dataLists=dataLists;
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
			for(var i in dataLists ){
				var changeCurrentState=dataLists[i].changeCurrentState;
				if(changeCurrentState==null||changeCurrentState=="endChangeProcess"){
					dataLists[i].pc_button_edit_create=true;
					
				}
			}
			console.log(scope.dataList);
			scope.$applyAsync(scope.dataList);
		    if(scope.flag){
		    	scope.flag = false;
		    }
		}
	}else{
		$("#tabNoData").removeClass("hide");
	}		    	
};

/***
 * 我的申请项目中已完成项目的渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsFinishedListCall(data){
	 var scope=getAngularScope("myDataController");
	 if(data.result == 0){
		var dataLists=data.responseInfo.lists;
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
			console.log(scope.dataList);
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
 * 我的代办任务列表的渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsAgentTaskListCall(data){
	console.log("我的待办中的");
	console.log(data);
	var scope=getAngularScope("myDataController");
	 scope.currentChangeState=[];
	 scope.mainCurrentState=[];
	if(data.result == 0){
	var dataLists=processProjectState(data.responseInfo.taskList);

    //得到所有的按钮控制标志绑定到前台
    scope.dataLists=dataLists;
   
    //如果是修改则办理按钮就不可见
    if(scope.dataLists.length!=0){
        for(var i in dataLists){
        
        	scope.mainCurrentState[i]=dataLists[i].state;
        	dataLists[i].buttonFlag=changeChangeAction("",scope.mainCurrentState[i]);
        	dataLists[i].state=JSON.parse(dataLists[i].stateInfo).state;
        	dataLists[i].buttonFlag=changeChangeAction("",dataLists[i].state);//因为主流程的状态和变更流程的状态在我的代办任务中是一个状态例如“B001”
        	dataLists[i].projectId=	dataLists[i].dataId;
        	if(dataLists[i].state==scope.mainCtrolStata.mainCtrolStata_can_modify){
        		scope.buttonFlag.p_button_modify=true;
        		scope.buttonFlag.p_button_approval=false;
        	}
        	//如果是变更审批的话
          if(dataLists[i].state==scope.mainCtrolStata.mainCtrolStata_can_approveChangeCommitte||dataLists[i].state==scope.mainCtrolStata.mainCtrolStata_can_departleaderApprovalChange)
            	{
            		console.log("jjjjj");
            		scope.buttonFlag.p_button_approvalChange=true;
            		scope.buttonFlag.p_button_approval=false;
            	}
        if(dataLists[i].state==scope.mainCtrolStata.mainCtrolStata_can_p_button_EditChange)
      	{
      		scope.buttonFlag.p_button_EditChange=true;
      		scope.buttonFlag.p_button_approval=false;
      	}
        
       
        }
    
    }
	
    console.log(dataLists);
    //console.log("测试有没有DataId");
	//console.log(dataLists[0].dataId);
	//得到我的申请的每个列表
	//changeChangeAction("001","005");//模拟得到各个列表的状态
     var pageInfo=data.responseInfo.page;
		if(scope.pageFlag)
		{
			scope.pageFlag=false;
			pageNav.go(pageInfo.pageNum, pageInfo.pages);
		}
		if(dataLists.length == 0){
			$("#tableNoData").removeClass("hide");
		}else{
			$("#tabPageNav").removeClass("hide");
			scope.dataList=dataLists;
		    scope.$applyAsync(scope.dataList);
		    if(scope.flag){
		    	scope.flag = false;
		    }
		}
	}else{
		$("#tableNoData").removeClass("hide");
	}		
};

/***
 * 我的代办任务中已完成的渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsAgentFinishedTaskListCall(data){
	var scope=getAngularScope("myDataController");
	if(data.result == 0){
		//var dataLists=data.responseInfo.taskList;
		var dataLists=processTaskState(data.responseInfo.taskList);
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
};

/**
 * 我的项目--相关资料界面渲染(coomarts项目)
 * 
 * **/
function getMyApplyCoomartsRelevantFileDataListCall(data){
	 var scope=getAngularScope("myDataController");
	 if(data.result == 0){
		var dataLists=data.responseInfo.relatedMaterials;
		var typeList=[];
		for(var i in dataLists ){
			 typeList[i]=dataLists[i].type;
		 }
		 for(var i in typeList){
			 if(typeList[i]=="jzdc"){
				 dataLists[i].typeFlag=true;
			 }else{
				 dataLists[i].typeFlag=false;
			 }
		 }
		var pageInfo=data.responseInfo.page;
		/*if(scope.pageFlag)
		{
			scope.pageFlag=false;
			pageNav.go(pageInfo.pageNum, pageInfo.pages);
		}*/
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

/*
 * 查看所有项目列表界面渲染(coomarts项目)
 * 
 */
function getCoomartsAllProjectSeeListCall(data){
	 var scope=getAngularScope("myDataController");
 		 scope.mainCurrentState=[];
	 if(data.result == 0){
		var dataLists=data.responseInfo.lists;
		for(var i in dataLists){
			var mainCurrentState=JSON.parse(dataLists[i].mainCurrentState);
			if(mainCurrentState){
				dataLists[i].stateName=mainCurrentState.stateName;
			}
		}
		scope.dataLists=dataLists;
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
			console.log(scope.dataList);
			scope.$applyAsync(scope.dataList);
			if(scope.flag){
		    	scope.flag = false;
		    }
		}
	}else{
		$("#tabNoData").removeClass("hide");
	}		    	 
}



/*
 * 查看所有进行中的项目列表界面渲染(coomarts项目)
 * 
 */
function getCoomartsAllProjectMarchSeeListCall(data){
	 var scope=getAngularScope("myDataController");
 		 scope.mainCurrentState=[];
	 if(data.result == 0){
		var dataLists=data.responseInfo.lists;
		for(var i in dataLists){
			var mainCurrentState=JSON.parse(dataLists[i].mainCurrentState);
			if(mainCurrentState){
				dataLists[i].stateName=mainCurrentState.stateName;
			}
		}
		scope.dataLists=dataLists;
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
 * 我的发布列表渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsDepartmentReleaseListCall(data){
	/*var declareFlag={
			"canDeclare":"0",//可以申报
			"cantnotDeclare":"1",//不能申报
			"declaring":"2"//申报中
	}*/
	console.log("eeeeeeeeeeee");
	var scope=getAngularScope("myDataController");
	if(data.result == 0){
		console.log(data);
		var userInfo= localStorage.getItem("userInfo");
			userId=JSON.parse(userInfo).uid;
		var dataLists=data.responseInfo.lists;
		var apply=data.responseInfo.apply;
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
			 if(apply=="yes")//如果是projectManager角色下的用户登录
			 {
				 for(var i in dataLists){
					 dataLists[i].canDeclareFlag=true;
				 }
			}else{
				for(var i in dataLists){
			//to do 
				 dataLists[i].canDeclareFlag=false;
				}
			}
			scope.dataList=dataLists;
			//console.log("测试我的经理发不");
			//console.log(dataLists);
		    scope.$applyAsync(scope.dataList);
		    if(scope.flag){
		    	scope.flag = false;
		    }
		}
	}else{
		$("#tabNoData").removeClass("hide");
	}		  
};

/***
 * 我发布的任务列表渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsDepartmentReleaseListForThreeLeaderCall(data){
	/*var declareFlag={
			"canDeclare":"0",//可以申报
			"cantnotDeclare":"1",//不能申报
			"declaring":"2"//申报中
	}*/
	var scope=getAngularScope("myDataController");
	if(data.result == 0){
		var userInfo= localStorage.getItem("userInfo");
			userId=JSON.parse(userInfo).uid;
		var dataLists=data.responseInfo.lists;
		var apply=data.responseInfo.apply;
		var pageInfo=data.responseInfo.page;
		if(scope.pageFlag)
		{
			scope.pageFlag=false;
			if(pageInfo!=undefined){
				pageNav.go(pageInfo.pageNum, pageInfo.pages);
			}
		
		}
		if(dataLists!=undefined&&dataLists.length == 0){
			$("#tabNoData").removeClass("hide");
		}else{
			$("#tabPageNav").removeClass("hide");
			 if(apply=="yes")//如果是projectManager角色下的用户登录
			 {
				 for(var i in dataLists){
					 dataLists[i].canDeclareFlag=true;
				 }
			}else{
				for(var i in dataLists){
				 dataLists[i].canDeclareFlag=false;
				}
			}
			scope.dataList=dataLists;
			//console.log("测试我的经理发不");
			//console.log(dataLists);
		    scope.$applyAsync(scope.dataList);
		    if(scope.flag){
		    	scope.flag = false;
		    }
		}
	}else{
		$("#tabNoData").removeClass("hide");
	}		  
};


/***
 * 我申报的项目列表渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsProjectManagerDeclareListCall(data){
	var scope=getAngularScope("myDataController");
	//console.log(data);
	if(data.result == 0){
		var dataLists=data.responseInfo.lists;
		var pageInfo=data.responseInfo.page;
		    for(var i in dataLists){
		    	var currentState=JSON.parse(dataLists[i].mainCurrentState);
		    	if(currentState){
		    		dataLists[i].stateName=currentState.stateName;
		    	}
		    }
		    //console.log(dataLists);
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
};


/***
 * 数据监测列表渲染界面(coomarts项目)
 * 
 * **/
function getCooMartsDataMonitorListCall(data){
	//console.log(data);
	var scope=getAngularScope("myDataController");
	//分页
	if(data.result == 0){
		if(data.responseInfo.annexName!=undefined){
			scope.annexName=data.responseInfo.annexName;
		}
		var dataLists=data.responseInfo.lists;
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
			if(scope.flag){
		    	scope.flag = false;
		    }
		}
		scope.dataList=dataLists;
		//console.log(scope.dataList);
		scope.$applyAsync(scope.dataList);
	}else{
		$("#tabNoData").removeClass("hide");
	}	
}
/***
 * 数据监测搜索时列表渲染界面(coomarts项目)
 * 
 * **/
function getSearchCooMartsDataMonitorListCall(data){
	//console.log(data);
	var scope=getAngularScope("myDataController");
	//分页
	if(data.result == 0){
		
		var dataLists=data.responseInfo.lists;
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
			scope.annexName=data.annexName;//到时根据后台的返回再具体的修改
			scope.$applyAsync(scope.dataList);
			scope.$applyAsync(scope.annexName);
			if(scope.flag){
		    	scope.flag = false;
		    }
		}
	}else{
		$("#tabNoData").removeClass("hide");
	}	
}























































