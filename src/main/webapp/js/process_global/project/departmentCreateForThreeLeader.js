var arrList="";
App.controller('projectManagerModel', ['$scope',initScope]);
function initScope($scope) {
	$scope.id="";
	
	//配置项目类型
	$scope.projectType=[{name:"请选择项目类型"},{name:"cooMarts"},{name:"camTalk"},{name:"lottery"}];
	$scope.project={
			title:"",
			description:"",
			projectType:"请选择项目类型",
			dataListTittle:"指定三级部门经理"
				
		}
	//var state="pm_process_bumenjlfb";
	var projectModelScope=getAngularScope("projectModel");
	//console.log(projectModelScope);
	if(projectModelScope==undefined){
		$scope.state="dispatch_threeLevelsLeader"
	}else{
		var gloablParm=projectModelScope.gloablParm;
		$scope.state=gloablParm.state
	}

	$scope.fileUpload={};
	//如果不选择的话默认是获取到的全部的三级部门经理
	$scope.project.assignPersonText="";
	//默认是全部的部门
	//用来存放三级部门的id的数组
	$scope.threeLeaderIdLsit=[];
	//初始化需要去后台取的默认数据(项目经理);
	$scope.getInitData=function(){
		var obj={
				 "request.state":$scope.state
		}
		console.log(obj);
		getInitDataByserver(obj,function(data){
			console.log(data);
			if(data.result == "0"){
				$scope.project.dataListTittle=data.responseInfo.name;
				//console.log($scope.dataListTittle);
				$scope.threeLeaderIdLsit=data.responseInfo.allowPersion;
				//console.log($scope.threeLeaderIdLsit);
				$scope.project.assignPersonText=data.responseInfo.name;
				$scope.id=$scope.threeLeaderIdLsit[0].id;
				for(var i=0; i<$scope.threeLeaderIdLsit.length;i++){
					$scope.threeLeaderIdLsit[i].name=data.responseInfo.name;
				}
				//console.log("hhhh444");
				//console.log($scope.id);
			}
		})
	}
	//点击查询的时候从后台去取三级项目经理的列表
	$scope.initThreeLeader=function(){
		initSetPersonData($scope.id);
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
/**
 * 提交函数
 */
function submitCreate()
{
	/*if(){
		
	}*/
	var scope=getAngularScope("projectManagerModel");
	var parm = parseQueryString();
	var projectName=parm.projectName;
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
	scope.threeLeaderIdLsit=unique(scope.threeLeaderIdLsit);
	var obj={	   
			"request.title":scope.project.title,
			"request.categoryId":categoryId,
		    "request.serviceType":scope.project.projectType,
			"request.description":scope.project.description,
			"request.assignPersonText":scope.project.assignPersonText,
			"request.data1":JSON.stringify(scope.threeLeaderIdLsit),//要发给后台的指定申报的三级部门列表
			"request.data2":data2,
			"request.data3":"1",//指定三级部门的是1
			
	};
	//console.log("发送给后台的obj");
	console.log(JSON.stringify(obj));
	leaderCreate(result,obj,function(data){
		if(data.result == 0){	
			window.history.go(-1);
		}
    });
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
	 		scope.ThreeLeaderLsit=data.responseInfo.lists;
			scope.$applyAsync(scope.ThreeLeaderLsit);
			
			var childrenScope=getAngularScope("myDataController");
			//查询出来的专家列表
			childrenScope.dataList=scope.ThreeLeaderLsit;
			childrenScope.$applyAsync(childrenScope.ThreeLeaderLsit);
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






		
