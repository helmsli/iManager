/**
 * first:初期，mid ：中期： final:末期
 */
var approval_const=
{
		   //初期
		    "first_commiteeApproval": "step_003", //初期委员会审批
		    "first_departManagerApproval": "step_004", //初期项目经理审批
		    //中期
		   
		    "mid_commiteeApproval": "step_011", //委员会审批
		    "mid_departManagerApproval": "step_012", //项目经理审批
		    //终期
		  
		    "final_commiteeApproval": "step_017", //委员会审批
		    "final_departManagerApproval": "step_018", //项目经理审批
		  
};
//回调函数
function approvalRefreshUi()
{
}
//提交按钮需要绑定的函数，需要向后台提交的东西
function submitApproval()
{
	var scope=getAngularScope("projectModel");
	switch(scope.parm.state)
	{
	//决策委员审批：
	case approval_const.first_commiteeApproval:
	case approval_const.mid_commiteeApproval:
	case approval_const.final_commiteeApproval:
		var obj={	   
			"request.taskId":scope.parm.taskId,
			"request.conclusion":scope.suggestion.suggestionFlag,
			"request.comments": scope.suggestion.suggestionDescription,
			"request.userId": scope.parm.uid,
	};
	committeeSuggestion(obj,function(data){
		if(data.result == 0){	
			window.history.go(-1);
		}
    });	
	break;
	//部门经理审批
	case approval_const.first_departManagerApproval:
	case approval_const.mid_departManagerApproval:
	case approval_const.final_departManagerApproval:
			var obj={	   
					"request.taskId":scope.parm.taskId,
					"request.conclusion":scope.suggestion.suggestionFlag,
					"request.comments": scope.suggestion.suggestionDescription,
					"request.userId": scope.parm.uid
			};
			managerSuggestion(obj,function(data){
				if(data.result == 0){
					window.history.go(-1);
				}
		    });
	break;
	
	}
 //提交附件材料
	var obj1={
			"request.taskId":scope.parm.taskId
		};
	submitEndMaterial(obj1,function(data){
		if(data.result == 0){
			window.history.go(-1);
		}
    });

	//根据scope的模型判断需要从网络获取哪些数据；
	//如果这个js是中期，查询中期材料，将中期材料的数据赋值给scope的一个对象；
	
	
	
}
//点击上传需要绑定的函数，需要向后台提交的东西
function uploadButtonSubmit(id){
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	data=JSON.parse(data);
	    	if(!scope.fileUpload)
	    	{
	    		scope.fileUpload=new Object();
	    	}
	    	scope.fileUpload[mainParm.detailStep]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	scope.$applyAsync(scope.projectAnnexs);
	        var obj={
	        	"request.projectAnnexs":JSON.stringify(data.responseInfo.projectAnnexs),
	        	"request.taskId":scope.parm.taskId,
	        }
	        submitMaterial(obj,function(data){
	        	console.log(data);
	        });
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}




function initApproval()
{
	var scope=getAngularScope("projectModel");
	scope.approve={
			committee:"决策委员会",
			departLeader:"部门经理审批"
	}
	switch(scope.parm.state)
	{
	//决策委员审批：
	case approval_const.first_commiteeApproval:
	case approval_const.mid_commiteeApproval:
	case approval_const.final_commiteeApproval:
		  
	      scope.approve.title=scope.approve.committee;
	      scope.suggestion={
		  suggestionFlag:1,
		  suggestionDescription:""
	     }
		break;
	//部门经理审批
	case approval_const.first_departManagerApproval:
	case approval_const.mid_departManagerApproval:
	case approval_const.final_departManagerApproval:
		
	    scope.approve.title=scope.approve.departLeader;	
        scope.suggestion={
    	  suggestionFlag:1,
    	  suggestionDescription:""	
	    }
		break;
	}
	//根据scope的模型判断需要从网络获取哪些数据；
	//如果这个js是中期，查询中期材料，将中期材料的数据赋值给scope的一个对象；
	
	//放在ajax请求success的时候
	//放在ajax请求success的时候
	//scope.regisgerJs(state);	
}
initApproval();



/**
 * 拿到系统的scope，赋值，向你register；如果页面加载完毕，由框架调用callbackHtml;
 * 如果HTML没有加载完毕，
 */

