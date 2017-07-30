var roleIds={
		threeLeader:22
}

var applicationvalidOptions= [];
applicationvalidOptions = [{
	"id":"companyName",
	"validateRule":{"require":true}	
},{
	"id":"projectName",
	"validateRule":{"require":true}
},{
	"id":"amount",
	"validateRule":{"require":true}
},{
	"id":"serviceAmount",
	"validateRule":{"isPositNumber":true,"require":true}
},{
	"id":"registerAddress",
	"isOnChange":false,
	"validateRule":{"require":true}

},{
	"id":"companyRegisterAddress",
	"isOnChange":false,
	"validateRule":{"require":true}

},{
	"id":"input_projectTitle",
	"validateRule":{"require":true}
},{
	"id":"input_releaseUrl",
	"validateRule":{"require":true}
},{
	"id":"input_project_desc",
	"validateRule":{"require":true}

},{
	"id":"companyRegisterAddress",
	"validateRule":{"require":true}
}


];


App.controller('projectManagerModel', ['$scope',initScope]);
function initScope($scope) {
	$scope.id="";
	
	//配置项目类型
	$scope.projectTypes=[{name:"请选择项目类型"},{name:"定向项目",id:"dingxiang"},{name:"申请项目",id:"shenqing"}];
	//$scope.project.projectType="定向项目";
	$scope.publish={};
	$scope.formValid = new FormValid({"formId":"formApplication",formField:applicationvalidOptions});
	
	try 
    { 
     $scope.beijing_diqu = beijing_diqu;
     $scope.XiangMu_diqu = XiangMu_diqu;
     console.log(beijing_diqu);
     console.log("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
    }
    catch (e) 
    {}
    
    $scope.selectCompanyDistrict =function(code,name)
	{
		
		
		setTimeout(function () {
			 $scope.$apply(function() {
			$scope.publish.companyRegisterAddress=name;
			$scope.publish.companyRegisterAddressCode=code;
			 });
			
			 var checkbox = document.getElementById("company-"+code);
				checkbox.checked = false; 
		     // AngularJS unaware of update to $scope
		  }, 200);
		$('#modelForRegisterAddress').modal('hide');
		
	}
	$scope.project={
			title:"",
			description:"",
			projectType:"请选择项目类型",
			dataListTittle:"指定三级部门经理",
			releaseUrl:""
				
		}
	//var state="pm_process_bumenjlfb";
	var projectModelScope=getAngularScope("projectModel");
	//console.log(projectModelScope);
	if(projectModelScope==undefined){
		$scope.state="pm_bumenjlfb_start"
	}else{
		var gloablParm=projectModelScope.gloablParm;
		$scope.state=gloablParm.state
	}

	$scope.fileUpload={};
	//如果不选择的话默认是获取到的全部的三级部门经理
	$scope.project.assignPersonText="";
	$scope.project.assignThreeEval="";
	$scope.project.assignorganization = "";
	//默认是全部的部门
	//用来存放三级部门的id的数组
	$scope.threeLeaderIdLsit=[];
	$scope.orgazationIdLsit=[];
	$scope.threeEvalIdLsit=[];
	$scope.orgazationArrList=[];
	
	$scope.threeEvalArrList=[];
	
	
	
	
	//初始化需要去后台取的默认数据(项目经理);
	$scope.getInitData=function(){
		//如果是三级部门经理的话就去查询详细的信息
		var userInfo= localStorage.getItem("userInfo");
		roleIdValue=JSON.parse(userInfo).roleIds[0];
		if(roleIdValue==roleIds.threeLeader){
			
			getDetail();
			}
		//todo:
		//initialize the orgazation
		$scope.project.assignType = 0; 
		getprivilege(function (){
			$scope.project.assignorganization = $scope.project.assignPersonText;
			
			$scope.orgazationIdLsit = $scope.threeLeaderIdLsit;
			//alert("org:"+JSON.stringify($scope.orgazationIdLsit));
		});
		//initialize the threeEval
		$scope.project.assignType = 1; 
		getprivilege(function (){
			$scope.project.assignThreeEval = $scope.project.assignPersonText;
			
			$scope.threeEvalIdLsit = $scope.threeLeaderIdLsit;
			//alert("abc:" + JSON.stringify($scope.threeEvalIdLsit) + " str:" + $scope.project.assignThreeEval);
		});
	}
	//点击查询的时候从后台去取三级项目经理的列表
	$scope.initOrganization=function(){
		var selectModalScope=getAngularScope("myDataController");
		
		$scope.project.assignType = 0; 
		 $scope.project.assignPersonText=$scope.project.assignorganization ;
		
		 $scope.threeLeaderIdLsit=$scope.orgazationIdLsit;
		 selectModalScope.arrList = $scope.orgazationArrList;
		 
		getprivilege(function (){
			initSetPersonData($scope.id);
			});
		
		
	}
	$scope.initThreeEval=function(){
		var phase  = $scope.$$phase;
		var selectModalScope=getAngularScope("myDataController");
		 $scope.project.assignType = 1;
		 $scope.project.assignPersonText=$scope.project.assignThreeEval ;
			
		  $scope.threeLeaderIdLsit=$scope.threeEvalIdLsit  ;
		  $scope.arrList = $scope.threeEvalArrList;
		  selectModalScope.arrList = $scope.threeEvalArrList;
		  console.log("arrlist");
		 console.log($scope.arrList);
         console.log(selectModalScope.arrList);
			
		
		getprivilege(function (){
			initSetPersonData($scope.id);
			});
		
		
	}
	
	$scope.confirmSelectAssignCallback = function()
	{
		//orgazation
		var selectModalScope=getAngularScope("myDataController");
		
		if(0==$scope.project.assignType)
		{
			$scope.project.assignorganization = $scope.project.assignPersonText;
			
			$scope.orgazationIdLsit = $scope.threeLeaderIdLsit;
			
			
			
			 $scope.orgazationArrList = selectModalScope.arrList;
		}
		else
		{
			$scope.project.assignThreeEval = $scope.project.assignPersonText;
			
			$scope.threeEvalIdLsit = $scope.threeLeaderIdLsit;
			
			$scope.threeEvalArrList = selectModalScope.arrList;
		}
		
	}
	
	$scope.changeProjectType=function()
	{
		/*
		var scope=getAngularScope("projectManagerModel");
		//alert(scope.project.projectType);
		var label_assgnPersion = document.getElementById("label_assgnPersion");
		
		if("定向项目"==scope.project.projectType)
		{
			label_assgnPersion.innerText="指定第三方评估:";
			scope.project.assignPersonText="所有第三方评估"
		}
		else
		{
			label_assgnPersion.innerText="指定机构/组织:";
		}
		//alert(scope.project.assignPersonText);
		*/
	}
	
}



/***
 * 点击上传需要绑定的函数，需要向后台提交的东西
 */
function uploadButtonSubmit(id){
	var scope=getAngularScope("projectManagerModel");
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId:id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	var scope=getAngularScope("projectManagerModel");
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
/***
 * 如果是三级部门经理登录的话，得去后台取相应的详细信息
 */
function getDetail(){
	var parm = parseQueryString();
	var publishId=parm.publishId;
	 //去后台去取查看经理发布的数据的详情和基本信息
	 var obj={
			 "request.publishId":publishId
	 } 
	  getDepartLeaderReleaseSeeByServer(obj,function(data){
		  if(data.result==0){
			  var project=data.responseInfo.departleaderPublish;
			  var scope=getAngularScope("projectManagerModel");
			scope.$apply(function () {
				scope.project.title=project.title;
				scope.project.projectType=project.serviceType;
				scope.project.description=project.description;
		  });
	  }
	  })	
	  
}




/**
 * 提交函数
 */
function submitCreate()
{
	//alert("submit");
	
	
	
	var userInfo= localStorage.getItem("userInfo");
	roleIdValue=JSON.parse(userInfo).roleIds[0];
	
	var scope=getAngularScope("projectManagerModel");
	
	var subFlag=scope.formValid.beforeSubmit();
	if(!subFlag )
	{
		return;
	}
	var parm = parseQueryString();
	var projectName=parm.projectName;
	var publishId=parm.publishId;
	var categoryId=getMyProjectOptions(projectName);
	var result = {};
	result.result = APP_RESULT.success;
	result.type = "pm_process_bumenjlfb";
	var data2="";
	scope.setManagers=JSON.stringify(scope.setManagers);
	if(scope.fileUpload[scope.state])
	{
		result.isAttach = APP_ContainAttach.attach;
		data2= JSON.stringify(scope.fileUpload[scope.state]);
	}
	var threeEvalList = unique(scope.threeEvalIdLsit);
	scope.orgazationIdLsit = unique(scope.orgazationIdLsit);
	scope.threeLeaderIdLsit=unique(scope.threeLeaderIdLsit);
	var data4 = {assignThreeEval:scope.project.assignThreeEval,releaseUrl:scope.project.releaseUrl};
	//alert("data4");
	alert(JSON.stringify(data4));
	var obj={	
			"request.departleaderPublishId":publishId,
			"request.title":scope.project.title,
			"request.categoryId":categoryId,
		    "request.serviceType":scope.project.projectType,
			"request.description":scope.project.description,
			"request.assignPersonText":scope.project.assignorganization,
			"request.data1":scope.publish.companyRegisterAddressCode,
			"request.data2":data2,
			"request.data3":JSON.stringify(threeEvalList),
			"request.data4":JSON.stringify(data4),
			"request.data5":JSON.stringify(scope.orgazationIdLsit),//要发给后台的指定申报的三级部门列表
			"request.data6":scope.publish.companyRegisterAddress
			
	};
	//console.log("发送给后台的obj");
	console.log(JSON.stringify(obj));
	
	leaderCreate(result,obj,function(data){
		if(data.result == 0){	
			window.history.go(-1);
		}
    });
	
	//如果是三级部门经理的话则承接部门经理的发布再发布
	
	
}
/**
 * 根据传入的projectName（项目类型名称），返回项目的categoryId。
 * @param projectName
 * @returns {Number}
 */
function getMyProjectOptions(projectName,pageNum){
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



function getprivilege(callBack)
{
	var scope=getAngularScope("projectManagerModel");
	var privilegeState = scope.state;
	//查詢第三方的權限
	if(1==scope.project.assignType)
	{
		privilegeState="officerApproval";
		//alert(2222);
	}
	else
	{
	   //alert(433333);	
	}
	var obj={
			 "request.state":privilegeState
	}
	console.log(obj);
	getInitDataByserver(obj,function(data){
		console.log(data);
		if(data.result == "0"){
			
			var scope=getAngularScope("projectManagerModel");
			scope.project.dataListTittle=data.responseInfo.name;
			//console.log($scope.dataListTittle);
			scope.threeLeaderIdLsit=data.responseInfo.allowPersion;
			//console.log($scope.threeLeaderIdLsit);
			scope.project.assignPersonText=data.responseInfo.name;
			scope.id=scope.threeLeaderIdLsit[0].id;
			for(var i=0; i<scope.threeLeaderIdLsit.length;i++){
				scope.threeLeaderIdLsit[i].name=data.responseInfo.name;
			}
			if($.isFunction(callBack))
			{
				callBack();
			}
				
			//console.log("hhhh444");
			//console.log($scope.id);
		}
	})
}

/**
 * 点击选择三级部门经理初始化数据从后台去取三级项目经理的列表
 * @param id
 */
function initSetPersonData(id){
	var scope=getAngularScope("projectManagerModel");
	var obj={
		"request.role_id":id,
		"request.containRoleId":0,
		"page.pageNum":1,
		"page.pageSize":10
	};
	
	getRoleUsers(obj,function(data){
	 	if(data.result == "0"){
	 		//console.log("查询三级部门领导444444");
	 		//console.log(data);
	 		var scope=getAngularScope("projectManagerModel");	 		
	 		scope.ThreeLeaderLsit=data.responseInfo.lists;
			scope.$applyAsync(scope.ThreeLeaderLsit);
			
			var childrenScope=getAngularScope("myDataController");
			//查询出来的专家列表
			childrenScope.dataList=scope.ThreeLeaderLsit;
			childrenScope.$applyAsync(childrenScope.ThreeLeaderLsit);
			//console.log("22222");
			//console.log(childrenScope.dataList);
			childrenScope.dataListTittle=scope.project.dataListTittle;
			
		}
	 });
}
/**
 * 把选择的三级项目经理发给后台
 */
function addUsers(id){
	var scope=getAngularScope("projectManagerModel");
	var userChecked=[];
	$("#userBox input[type='checkbox']").each(function(){
		var _self=$(this);
		var checkFlag=_self.prop("checked");
		if(checkFlag == true){
			userChecked.push(_self.val());
		}
	});
	if(userChecked.length==0){
		$scope.setManagers=$scope.managerType;
	}else{
		$scope.setManagers=userChecked;
	}
	
};
/**
 * 数组去重
 */
function unique(array){ 
	var r = []; 
	for(var i = 0, l = array.length; i < l; i++) { 
	 for(var j = i + 1; j < l; j++) 
	  if (array[i].id == array[j].id) j = ++i; 
	 r.push(array[i]); 
	 }
	 return r; 
	};







		
