App.controller('departLeaderReleaseEdit', ['$scope',initScope]);
function initScope($scope) {
	//查看和办理走一个界面
	var parm = parseQueryString();
	var seeState=parm.seeState;
	//$scope.publishId=parm.publishId;
	$scope.dataId=parm.dataId;
	$scope.approveFlag=true;
	$scope.isAttach=false;
	$scope.fileUpload={};
	if(seeState){
		$scope.approveFlag=false;
	}
	console.log($scope.approveFlag);
	$scope.dataDetail={};
	var projectId="";
	 //去后台去取查看经理发布的数据的详情和基本信息
	 var obj={
			 "request.applicationId":$scope.dataId
	 } 
	 threeManageAfterDeclareSeeByServer(obj,function(data){
		  if(data.result==0){
			  console.log(data);
			$scope.dataDetail=data.responseInfo.departleaderPublish;
			var dataDetail=$scope.dataDetail;
			var data2=dataDetail.data2;
			console.log(data);
			if(data2){
				$scope.isAttach=true;
				$scope.dataDetailAttach=JSON.parse(data2);
			}
			 console.log("hehdhehd");
			  console.log($scope.dataDetailAttach);
			
		  }
	  })	
	  
	  
	  
	  /**
	   * 附件下载走的方法
	   */
	 $scope.attachDownLoad=function(annexName){
		 var url="/projectAnnex/fileDownLoad";
		     url= getBasePath()+url;
		     url=url+"?request.annexName="+annexName;
		     console.log(url);
		     location.href =url;
	  }
}
/***
 * 点击上传需要绑定的函数，需要向后台提交的东西
 */
function uploadButtonSubmit(id){
	var scope=getAngularScope("departLeaderReleaseEdit");
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	var scope=getAngularScope("departLeaderReleaseEdit");
	    	data=JSON.parse(data);
	    	if(!scope.fileUpload)
	    	{
	    		scope.fileUpload=new Object();
	    	}
	    	scope.fileUpload[scope.state]= data.responseInfo.projectAnnexs;
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
/**
 * 提交申报函数
 */
function submitDeclare()
{
	var scope=getAngularScope("departLeaderReleaseEdit");
	var parm = parseQueryString();
	var projectName=parm.projectName;
	var categoryId=getMyProjectOptions(projectName);
	var result = {};
	result.result = APP_RESULT.success;
	result.type = "pm_process_sjbmjlsb";
	var data2="";
	if(scope.fileUpload[scope.state])
	{
		result.isAttach = APP_ContainAttach.attach;
		data2= JSON.stringify(scope.fileUpload[scope.state]);
	}
	var obj={	   
			"request.applicationId":scope.dataId,
			"request.data2":data2,
	};
	threeLeaderEditDeclareByServer(obj,function(data){
		if(data.result == 0){
			console.log("申报竟然可以了");
			window.history.go(-1);
		}
    });	
	//console.log(JSON.stringify(obj));
}
//三级部门申报或者查看中的取消按钮
function cancleButton(){
	window.history.go(-1);
}
/**
 * 根据传入的projectName（项目类型名称），返回项目的categoryId。
 * @param projectName
 * @returns {Number}
 */
function getMyProjectOptions(projectName){
	var projectName=projectName.toLowerCase();
	//var categoryId=1;
	switch (projectName) {
	case "coomarts":
		categoryId = 1;
		break;
	case "camtalk":
		categoryId = 2;
		break;
	case "lottery":
		categoryId = 3;
		break;
	}
	return categoryId;
	
}
