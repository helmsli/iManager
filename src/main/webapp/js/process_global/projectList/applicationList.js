var nodeInfo = {};
var owner = "0";

var STATUS_APPLY="0";
var STATUS_NEED_CHANGE="1";
var STATUS_REJECTED = "2";
var STATUS_AGREED = "255";
//提交结项材料

var STATUS_apply_end = "25501";
//以结项
var STATUS_finished_end="255255";


function isOwner()
{ 
	return owner=="1";
}
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
	        "",
	        "项目分类",            
			"项目名称",
			"申报单位",
			"科室",
			"地区",
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
	$scope.setCheAll = function(){
		var checkBoxApi = new CheckBoxApi("listChe");
		var checkAllFlag = $("#che_all_bottom").prop("checked");
		checkBoxApi.setAllChecked(checkAllFlag);
		var checkedLen=checkBoxApi.getCheckAllValue().checkedAll.length;
		console.log(checkedLen);
	}
	$scope.selectChe = function(obj){
		var checkedFlag=obj.currentTarget.checked;
		var checkBoxApi = new CheckBoxApi("listChe");
		var totalCheckbox=checkBoxApi.checkBox.length;
		var checkedLen=checkBoxApi.getCheckAllValue().checkedAll.length;
		console.log(checkedLen);
		$("#che_all_bottom").prop("checked",(totalCheckbox==checkedLen));
	}
	
	$scope.showOwner=function(status,data){
		
		data.isManager=true;
		 var isStart255 = status.indexOf("255");
		 if(isStart255==0)
		  {
				data.isManager=false;
		  }
		 
		 
		
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
			if(status==STATUS_NEED_CHANGE||STATUS_REJECTED==status)
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
		var pcond = myParm.pcond;
		var querCondition = {};
		if(typeof(pcond)=="undefined")
        {
			//localStorage.removeItem("pcond");
			querCondition={};
        	
        }
		else
		{
			querCondition = localStorage.getItem("pcond");
			querCondition=JSON.parse(querCondition)||{};
		}
		
		
		var serviceType=$scope.serviceType;
        var projectName=myParm.projectName; 
        var projectRegion = querCondition["region"];
        if(typeof(projectRegion)=="undefined")
        {
        	
        	projectRegion="";
        	
        }
        var projectType = querCondition["pType"];
        if(typeof(projectType)=="undefined")
        {
        	
        	projectType="";
        	
        }
        var keshi   = querCondition["keshi"];
        if(typeof(keshi)=="undefined")
        {
        	
        	keshi="";
        	
        }
        //alert(keshi);
        var pageNum = pageNum||1;
    	var  pageSize = 10;
        var categoryId =$scope.getCategorySearchId(projectName);
        var obj={
        		"request.serviceType": serviceType,	
        		"request.categoryId": categoryId,
        		"request.region":projectRegion,
        		"request.pType":projectType,
        		"request.keshi":keshi,
        		"page.pageNum": pageNum,
        		"page.pageSize": pageSize
        }
        console.log(JSON.stringify(obj));
        queryApplicationList(obj,callbackQueryApplicationList);
        //将全选按钮设置为非全选
        $("#che_all_bottom").prop("checked",false);
        
        
	}
	$scope.downloadApplication = function()
	{
		
		
		var checkBoxApi = new CheckBoxApi("listChe");
		var totalCheckbox=checkBoxApi.checkBox.length;
		var allList = checkBoxApi.getCheckAllValue();
		var checkedLen=allList.checkedAll.length;
		console.log(checkedLen);
		$("#che_all_bottom").prop("checked",(totalCheckbox==checkedLen));
		console.log("*******************:allchecked" + checkedLen +":alllist" + totalCheckbox);
		if(checkedLen>0)
		{
			getAppExportByIds(allList.checkedAll);
		}
		else
		{
			getAppExportAll();	
		}
		
		satarAjax();
		
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
				//解析详细信息
				 try{
					 if(dataLists[i].data5!="")
					 {
							 dataLists[i].detail =   JSON.parse(dataLists[i].data5);
					 }
				 }
				 catch(e)
				 {
					 
				 }
				 
				// alert(dataLists[i].taskId);
				 
				 dataLists[i].apply_type="申请项目";
				 dataLists[i].keshi="无";
				 dataLists[i].district=dataLists[i].data1;
				 
				 if(dataLists[i].taskId == "wei")
				 {
					 dataLists[i].apply_type="微创投项目";
				 }
				 else if(dataLists[i].taskId == "ding")
				 {
					// alert("abd");
					 dataLists[i].apply_type="定向项目";
					 try{
						 dataLists[i].keshi=dataLists[i].data1;
						 dataLists[i].district=dataLists[i].detail.address;
						 if(dataLists[i].district==undefined||dataLists[i].district==""||"科室"==dataLists[i].district)
						 {
							 dataLists[i].district="无";
					     }
						 
					 }
					 catch(e)
					 {
						 
					 }
				 }
				 
				 
				 
				 dataLists[i].showContactInfo = dataLists[i].createPersonName + "(" +  dataLists[i].createPerson+  ")";
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
					 var fdStart = dataLists[i].status.indexOf("255255");
					 var isStart255 = dataLists[i].status.indexOf("255");
					 if(fdStart==0)
					 {
						 dataLists[i].statusName="项目已经结项";
					 }
					 else if(isStart255==0)
					 {
						 dataLists[i].statusName="结项中";
					 }
					 else
					{
						 dataLists[i].statusName="等待审批";	 
					 }
						 
					 
					 
					 
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
function satarAjax() {
    
	var myRow='<div class="md-preloader pl-size-md">'
		+'<svg viewbox="0 0 75 75"><circle cx="37.5" cy="37.5" r="33.5" class="pl-red" stroke-width="4" /></svg>'
		+'</div>'
		+'<p>Please wait...</p>';
	swal({
		title: " ",
		text: myRow,
		html:true,
		timer: 8000,
        showConfirmButton: false
	});
	
	

}
function getAppExportAll()
{
	
	var myParm=parseQueryString();//所有的参数
    var projectName=myParm.projectName; 
    var obj={
    		"request.serviceType": "application",	
    		"request.categoryId": "canlian"        		
    }
   
   
    console.log(obj);
    
    
	var options ={
	        "url":"/commonbiz/getApplication/downloadApp",
	        "data": obj,
	        callBack: function(data) {
	        	callbackExportApply(data);
	        	
	        	//sweetAlert.close();
	        	
	        },
	        errCallBack:function(e)
	        {
	            console.log("服务器异常");
	            //sweetAlert.close();
	            location.reload();
	        }
	    };
	 requestAjax(options);
}
function getAppExportByIds(ids)
{
	/**
	 * 
	 */
	var idsString = "";
	for(var i=0;i<ids.length;i++){
		 if(i>0)
		 {
			 idsString = idsString + "," + ids[i];
	     }
		 else
		{
			 idsString = idsString + ids[i];
		}
	}
	
	var myParm=parseQueryString();//所有的参数
    var projectName=myParm.projectName; 
    var obj={
    		"request.serviceType": "application",	
    		"request.categoryId": "canlian",   
    		"request.selectedIds":idsString
    }
   
   
    console.log(obj);
    
	var options ={
	        "url":"/commonbiz/getApplication/downloadSelected",
	        "data": obj,
	        callBack: function(data) {
	        	callbackExportApply(data);
	        	
	        	//sweetAlert.close();
	        	
	        },
	        errCallBack:function(e)
	        {
	            console.log("服务器异常");
	            location.reload();
	            //sweetAlert.close();
	        }
	    };
	 requestAjax(options);
}

function callbackExportApply(data)
{
	console.log(data);
	var scope=getAngularScope("myDataController");
	//分页
	if(data.result == 0){
		
		var file=data.responseInfo.exportApplicationFile;
		var type="applyExport";
		//alert("click");
		 var url="/projectAnnex/downLoadExport";
	     url= getBasePath()+url;
	     url=url+"?request.fileName="+file + "&type=applyExport";
	     console.log(url);
	     location.href =url;
	}
	else
	{
		
	}
}
