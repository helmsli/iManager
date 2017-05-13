/**
 * first:初期，mid ：中期： final:末期
 */
var setExpertScores_const=
{
		   //初期
		    "first_setExpertScores": "step_002", //初期专家评分
		    //中期
		   
		    "mid_setExpertScores": "step_010", //中期专家评分
		    //终期
		  
		    "final_setExpertScores": "step_016", //终期专家评分
		  
};
//回调函数
function setExpertScoresUi()
{
	var scope=getAngularScope("projectModel");
	scope.expertScore={}
	var expertReview={};
	var length=0;
	var scope=getAngularScope("projectModel");
	var projectId=scope.gloablParm.projectId;
	var state=scope.gloablParm.state;
	scope.expertScore=2;	
}

//点击上传需要绑定的函数，需要向后台提交的东西
var scope=getAngularScope("projectModel");
var state=scope.gloablParm.state;
scope.fileUpload={};
function uploadButtonSubmit(id){
	
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	var scope=getAngularScope("projectModel");
	    	data=JSON.parse(data);
	    	if(!scope.fileUpload)
	    	{
	    		scope.fileUpload=new Object();
	    	}
	    	console.log("**************");
	    	console.log(data);
	    	scope.fileUpload[scope.gloablParm.state]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	console.log(JSON.stringify(data.responseInfo.projectAnnexs));
	    	scope.$applyAsync(scope.projectAnnexs);
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}
function submitsetExpertScores()
{
	var scope=getAngularScope("projectModel");
	var result = {};
	
	//构造APProve的结果;
	result.result = APP_RESULT.success;
	result.comment = "";
	var data2="";
	if(scope.fileUpload[scope.gloablParm.state])
	{
		result.isAttach = APP_ContainAttach.attach;
		data2= JSON.stringify(scope.fileUpload[scope.gloablParm.state]);
	}
	var exportScore = {};
	exportScore.score =scope.expertScore;
	
	var obj={	   
			"request.taskId":scope.gloablParm.taskId,			
			"request.serviceType":scope.gloablParm.state,
			"request.data1":JSON.stringify(exportScore),
			"request.data2":data2
			
	};
	completeTask(result,obj,function(data){
		if(data.result == 0){
			window.history.go(-1);
		}else{
			log(data.result);
		}
    });	
}
function initsetExpertScores()
{
	//需要去请求一下专家列表，显示在页面上
	 initFileStateListener(setExpertScoresUi);
}



initsetExpertScores();



/**
 * 拿到系统的scope，赋值，向你register；如果页面加载完毕，由框架调用callbackHtml;
 * 如果HTML没有加载完毕，
 */






























