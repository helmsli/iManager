var nodeInfo = {};

/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
			"项目名称",
			"机构名称",
			"月份",
			"提交人",
			"提交时间",
			"操作"
	];
	$scope.queryProjectName="";//这个是批量下载时需要的queryProjectName
	$scope.dataList=[];
	$scope.pageFlag=true;
	$scope.serviceType={
			submitMonthlyCheck:"submitMonthlyCheck"
	}
	//获得项目的类别是coomarts还是别的
	$scope.getCategorySearchId=function(projectName){
		var projectName=projectName.toLowerCase();
		var categoryId=1;
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
	//数据搜索--数据监测(包括数据监测和月度监测)
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
		console.log("searchButton");
		console.log("************"+pageNum);
		document.getElementById("searchButton").disabled=true;
		var myParm=parseQueryString();//所有的参数
		 var serviceType=myParm.serviceType;
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
        getDataMonthlyData(projectName,obj);
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
	
	$scope.downloadMonthlyReport = function(monthlyReportFile)
	{
		//console.log("ddddddddd");
		//console.log(JSON.stringify(monthlyReportFile));
		//alert(JSON.stringify(monthlyReportFile));
		if(monthlyReportFile!="")
		{
			var scopeParent=getAngularScope("myDataController");
			scopeParent.attachDownLoad(monthlyReportFile[0].annexName);
		}
		else
		{
			
			   alert("没有相关文件下载，用户没有上传相关文件.");	
			
		}
		
	}
	/***
	 * 查看尽职调查详细
	 * @param {Object} userId
	 */
	$scope.openQuestionDetail = function(dataId,serviceType){
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||"";
		if(serviceType==$scope.serviceType.submitMonthlyCheck){
			url="committeeMonitor_detail.html?projectName="+projectName+"&dataId="+dataId+"&from="+from;
		}else{
			url="projectMonthProgress_detail.html?projectName="+projectName+"&dataId="+dataId+"&from="+from;
		}
		location.href=url;
	}

}]);
