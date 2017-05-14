function downloadProject()
{
	var scope=getAngularScope("projectModel");
	var obj={	   
			"request.projectId":scope.gloablParm.projectId
	};
	downloadFile(obj,function(data){
		if(data.result == 0){
			var uploadResult = data.responseInfo;
			location.href=getBasePath() + "/projectAnnex/fileDownLoad?annexName=" + uploadResult.path;
		}
    });
}
 

/**
 * 通用的提交函数
 */
function downloadFile(obj,callBack){ 
	var options ={
	        "url": "/project/downLoadproject",
	        "data": obj,
	        callBack: function(data) {
	            callBack(data);
	        },
	        errCallBack:function(e)
	        {
	            console.log("服务器异常");
	        }
	    };
	 requestAjax(options);
}