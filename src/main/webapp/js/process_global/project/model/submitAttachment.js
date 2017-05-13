function setAttachmentRefreshUi()
{
}
//提交按钮需要绑定的函数，需要向后台提交的东西
function submitAttachment()
{
	var scope=getAngularScope("projectModel");
	var result = {};
	//构造APProve的结果;
	result.result = APP_RESULT.success;
	result.comment = "";
	result.type = "file";
	//console.log("99999");
	//console.log(scope.fileUpload[scope.gloablParm.state]);
   //提交附件材料
	 var obj={
	        	"request.data2":JSON.stringify(scope.fileUpload[scope.gloablParm.state]),
	        	"request.taskId":scope.gloablParm.taskId,
	        	"request.serviceType":scope.gloablParm.state
	        }
	 completeTask(result,obj,function(data){
		if(data.result == 0){
			window.history.go(-1);
		}
    });
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
	    	//console.log("**************");
	    	//console.log(data);
	    	scope.fileUpload[scope.gloablParm.state]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	//console.log(JSON.stringify(data.responseInfo.projectAnnexs));
	    	scope.$applyAsync(scope.projectAnnexs);
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}
function initAttachment()
{
	//1.请求数据
	  initFileStateListener(setAttachmentRefreshUi);
	
}
initAttachment();



