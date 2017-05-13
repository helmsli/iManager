
/**
 * first:初期，mid ：中期： final:末期
 * 
 */
var setExpertList_const=
{
		   //初期
		    "first_setExpertList": "step_001", //初期指定评审专家
		    //中期
		   
		    "mid_setExpertList": "step_008", //中期指定评审专家
		    //终期
		  
		    "final_setExpertList": "step_014", //中期指定评审专家
		  
};
//指定评审专家回调函数
function setExpertListRefreshUi()
{
	//console.log(999999999999);
	init("setExperListTable");
	//console.log(222243);
	
}

function submitSetExpertList()
{
	var scope=getAngularScope("projectModel");
	var result = {};
	
	//构造APProve的结果;
	result.result = APP_RESULT.success;
	result.comment = "";
	
	var tableData=JSON.stringify(tableSave('setExperListTable'));

	
	var obj={	   
			"request.taskId":scope.gloablParm.taskId,			
			"request.serviceType":scope.gloablParm.state,
			"request.data1":tableData
	};
	completeTask(result,obj,function(data){
		if(data.result == 0){	
			window.history.go(-1);
		}
    });	
}


function initApproval()
{
	//1.请求数据
  initFileStateListener(setExpertListRefreshUi);
}

initApproval();
















