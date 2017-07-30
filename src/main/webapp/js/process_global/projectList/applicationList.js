var nodeInfo = {};
var owner = "0";

var STATUS_APPLY="0";
var STATUS_NEED_CHANGE="1";
var STATUS_REJECTED = "2";
var STATUS_AGREED = "255";
function isOwner()
{ 
	return owner=="1";
}
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
	        "项目分类",            
			"项目名称",
			"申报单位",
			"地区/科室",
			"金额",
			"联系人",
			"申报时间",
			"状态",
			"操作"
	];
	$scope.queryProjectName="";//这个是批量下载时需要的queryProjectName
	$scope.dataList=[];
	$scope.pageFlag=true;
	$scope.serviceType="application";
	$scope.isManager=true;
	
	//获得项目的类别是coomarts还是别的
	$scope.getCategorySearchId=function(projectName){
		var projectName=projectName.toLowerCase();
		var categoryId=1;
		switch (projectName) {
		case "coomarts":
			categoryId = 1;
			break;
		case "camtalk":
			categoryId = "canlian";
			break;
		case "lottery":
			categoryId = 3;
			break;
		}
		return categoryId;
		

	}
	/**
	 * 附件下载走的方法
	 */
	$scope.attachDownLoad=function(annexName){
		console.log(annexName);
		if(annexName!=undefined&&annexName!=null&&annexName!=""){
			 var url="/projectAnnex/fileDownLoad";
		     url= getBasePath()+url;
		     url=url+"?request.annexName="+annexName;
		     console.log(url);
		     location.href =url;
		}
		else
		{
		   alert("没有相关文件下载，用户没有上传相关文件.");	
		}
		
	}
	$scope.showOwner=function(status,data){
		data.isManager=true;
		if(isOwner())
		{
			if(status==STATUS_NEED_CHANGE)
			{
				data.isManager=false;
				data.ownerTaskName="修改";
				return true;
			}
			else
			{
				data.isManager=false;
				return false;
			}
		}
		else
		{
			if(status==STATUS_NEED_CHANGE)
			{
				data.isManager=false;
			}
				
		}
		return false;
	}
	
	//数据搜索，暂时屏蔽
	$scope.initTabSerach=function(){
		id="searchButton" 
		if(queryProjectName=undefined){
			queryProjectName="";
		}
		document.getElementById("searchButton").disabled=false;
		queryProjectName=document.getElementById("search").value;   
		console.log("开始搜索了xcd"+queryProjectName);
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
        var serviceType=myParm.serviceType;
        var pageNum = pageNum||1;
    	var  pageSize = 10;
        var categoryId =$scope.getCategorySearchId(projectName);
        var obj={
        		"request.serviceType": serviceType,	
        		"request.projectName": queryProjectName,	
        		"request.categoryId": categoryId,
        		"page.pageNum": pageNum,
        		"page.pageSize": pageSize
        }
        
        console.log(JSON.stringify(obj));
        getDataMonitorDataList(projectName,obj);
	}
	//数据初始化--数据监测(包括数据监测和月度监测)
	$scope.initTab=function(pageNum){
		
		document.getElementById("searchButton").disabled=true;
		var myParm=parseQueryString();//所有的参数
		var serviceType=$scope.serviceType;
        var projectName=myParm.projectName; 
        var pageNum = pageNum||1;
    	var  pageSize = 10;
        var categoryId =$scope.getCategorySearchId(projectName);
        var obj={
        		"request.serviceType": serviceType,	
        		"request.categoryId": categoryId,
        		"page.pageNum": pageNum,
        		"page.pageSize": pageSize
        }
        console.log(JSON.stringify(obj));
        queryApplicationList(obj,callbackQueryApplicationList);
	}
	$scope.openCreateMonthlyReport = function()
	{
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		//var state="B";
		//如果state状态中含有b，那么证明是变更的审批，则会跳到变更的办理审批中去。
		
		url="createMonthlyReport.html?projectName="+projectName+"&projectId="+"222"+"&taskId="+"333"+"&from="+from; 
		
		location.href=url;
	}
	
	$scope.downLoadApplicationFile = function(monthlyReportFile)
	{
		//console.log("ddddddddd");
		//console.log(JSON.stringify(monthlyReportFile));
		//alert(JSON.stringify(monthlyReportFile));
		try
		{
		if(monthlyReportFile!="")
		{
			var scopeParent=getAngularScope("myDataController");
			scopeParent.attachDownLoad(monthlyReportFile.annexName);
		}
		else
		{
			
			   alert("没有相关文件下载，用户没有上传相关文件.");	
			
		}
		}
		catch(e)
		{
			   alert("没有相关文件下载，用户没有上传相关文件.");	
				
		}
		
		
	}
	/***
	 * 查看尽职调查详细
	 * @ {Object} userId
	 */
	$scope.openApplyDetail = function(dataId,status,data){
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||"";
		var action = "modify";
		//var status = "apply";
		if(isOwner())
		{
			if(STATUS_NEED_CHANGE==status)
			{
				status = "applyModify";
			}
			//test
			//status = "applyModify";
		}
		
		
		
		if(action=="modify"){
			url="projectManager/applicationApprove.html?projectName="+projectName + "&state="+status+"&dataId="+dataId+"&from="+from;
		}else{
			url="projectManager/applicationApprove.html?projectName="+projectName + "&state="+status+"&dataId="+dataId+"&from="+from;
		}
		location.href=url;
	}

}]);
function queryApplicationList(obj,callBack)
{
	var options ={
	        "url":"/commonbiz/getApplication/listByDistrict",
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
function callbackQueryApplicationList(data){
	console.log(data);
	var scope=getAngularScope("myDataController");
	//分页
	if(data.result == 0){
		
		var dataLists=data.responseInfo.lists;
		var pageInfo=data.responseInfo.page;
		owner  = data.responseInfo.owner;
		
		if(scope.pageFlag)
		{
			scope.pageFlag=false;
			pageNav.go(pageInfo.pageNum, pageInfo.pages);
		}
		if(dataLists.length == 0){
			$("#tabNoData").removeClass("hide");
		}else{
			console.log("有数据");
			$("#tabNoData").addClass("hide");
			$("#tabPageNav").removeClass("hide");
			if(scope.flag){
		    	scope.flag = false;
		    }
		}
		
		//todo:monthly
		 for(var i in dataLists)
		{
			 try{
				 dataLists[i].apply_type="申请项目";
				 if(dataLists[i].taskId=="wei")
				 {
					 dataLists[i].apply_type="微创投项目";
				 }
				 else if(dataLists[i].taskId=="ding")
				 {
					 dataLists[i].apply_type="定向项目";
				 }
				 
				 //解析详细信息
				 if(dataLists[i].data5!="")
				 {
						 dataLists[i].detail =   JSON.parse(dataLists[i].data5);
				 }
				 //申报书附件
				 if(dataLists[i].data6!="")
				{
					  var fileApplications=   JSON.parse(dataLists[i].data6);
					  dataLists[i].fileApplication=fileApplications[0];
				}
				 //公司资质
				 if(dataLists[i].data7!="")
					{
						 var fileCompanys =   JSON.parse(dataLists[i].data7);
						 dataLists[i].fileCompany=fileCompanys[0];
					}
				 //status：状态： 0 - 申报 1--申报书需要修改  2-- 申报没有中标   255-- 申报中标  4-- 初审审批同意
				 if(dataLists[i].status==STATUS_APPLY)
				 {
					 dataLists[i].statusName="等待审批";
				 }
				 else if(dataLists[i].status==STATUS_NEED_CHANGE)
				 {
					 dataLists[i].statusName="需要修改";
				 }
				 else if(dataLists[i].status==STATUS_REJECTED)
				 {
					 dataLists[i].statusName="审批不通过";
				 }
				 else if(dataLists[i].status==STATUS_AGREED)
				 {
					 dataLists[i].statusName="审批通过";
				 }
				 else
				 {
					 dataLists[i].statusName="等待审批";
				 }
				 
				  
			}
			 catch(err)
			 {
				 console.log(err);
			 }
		}
		//异步刷新界面
		setTimeout(function () {
			var scope=getAngularScope("myDataController");
			 scope.$apply(function() {
				 scope.dataList=dataLists;		
				 console.log(scope.dataList);
				
					
			 });
			
		  }, 200);
		
	}else{
		$("#tabNoData").removeClass("hide");
	}	
}
