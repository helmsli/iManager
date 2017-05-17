var fileUploadNewApply=[];
function getAllData(){
	   
	   var myParm=parseQueryString();//所有的参数
       var Bg=myParm.Bg;
       var dataId=myParm.dataId;
       var changeCurrentState=myParm.changeCurrentState;
       if(changeCurrentState=="modifyChange"){
    	   Bg=2;//变更驳回修改
       }
      
	   var tablePostData=localStorage.getItem("tablePost");
	   tablePostData=JSON.parse(tablePostData);
	   var tablePostDataObj={};
	   tablePostDataObj.CPU=tablePostData.CPU;
	   tablePostDataObj.memory=tablePostData.memory;
	   tablePostDataObj.bandWidth=tablePostData.bandWidth;
	   tablePostDataObj.serversNumber=tablePostData.serversNumber;
	   tablePostDataObj.UpgradeTime=tablePostData.UpgradeTime;
	   tablePostDataObj.otherConditions=tablePostData.otherConditions;
	   console.log("##########");
	   console.log(tablePostDataObj);
	   tablePostDataObj=JSON.stringify(tablePostDataObj);
	   var tableRuleData=localStorage.getItem("tableRule");
	   var tableRuleInnerData=localStorage.getItem("tableRuleInner");
	   var tableEditData=localStorage.getItem("tableEdit");
	   var requestRuleData=JSON.parse(tableRuleData);
	   var requestInnerData=JSON.parse(tableRuleInnerData);
	   var requestEditData=JSON.parse(tableEditData);
	   var requestProjectExtInfo={"taskDescribe":requestEditData.taskDescribe,
						           "taskbackground":requestEditData.taskbackground,
						           "taskgoal":requestEditData.taskgoal,
						           "taskDanger":requestEditData.taskDanger,
						           "taskcreate":requestEditData.taskcreate,
						           "taskplan":tableRuleInnerData,
						           "newApplyAttachment":fileUploadNewApply["newApply"]
						           };
	   var userInfo=localStorage.getItem("userInfo");
	   //通过URL获取参数
	   var parm=parseQueryString();
	   //获取projectId
	   var projectId=parm.projectId;
	   var projectName=parm.projectName;
	   var publishId="";
	   
	   if(requestProjectExtInfo.taskbackground.trim()=="")
	   {
		   //alert("请填写申报单位");
		  // return;
	   }
	   if(requestEditData.taskName.trim()=="")
	   {
		   //alert("请填写项目名称");
		   //return;
	   }
	   requestProjectExtInfo.taskDescribe="";
	   if(requestProjectExtInfo.taskDescribe.trim()=="")
	   {
		   //alert("请填写申报金额");
		   //return;
	   }
	   if(fileUploadNewApply["newApply"]==undefined)
		{
		  alert("请上传项目申报书");
		  return;
		}
	   
	   //若projectId不存在，则默认为""
	   if(!projectId){
		   projectId=""
	   }
	   //alert(myParm.publishId);
	   if(myParm.publishId){
    	   publishId=myParm.publishId;
       }
	   //若单选为其他时取输入框中的值
	   function getCategoryId(projectName) {
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
     var categoryId= getCategoryId(projectName);
     console.log(categoryId);
     console.log(JSON.parse(userInfo));
	   
     //走创建的
	   if(!Bg){
		   
		   var obj={
				   "request.projectId":projectId,
				   "request.projectName":requestEditData.taskName,
				   "request.projectManagerId":JSON.parse(userInfo).uid,
				   "request.projectManagerName":JSON.parse(userInfo).fullName,
				   "request.categoryId":categoryId,
				   "request.subcategory":JSON.stringify(requestEditData['radioed']['02']),
				   "request.cycleType":JSON.stringify(requestEditData['radioed']['01']),
				   "request.projectExtInfo":JSON.stringify(requestProjectExtInfo),
				   "request.projectTaskDetail":tablePostDataObj,
				   "request.projectCosts":JSON.stringify(requestRuleData),
			       "request.startTime":requestEditData.startTime,
				   "request.completeTime":requestEditData.endTime,
				   "request.telno":requestEditData.myNum,
				   "request.email":requestEditData.email,
				   "request.projectMilestone":"项目里程碑",
				   "request.publishId":publishId
			    };
		       //console.log(requestPostData);
			   console.log(JSON.stringify(obj));
			   createTable(obj,function(data){
			    	if(data.result == 0){
			    		swal({
					    	title: "提交成功",
					    	text: "1秒后自动关闭",
					    	timer: 1000,
					    	confirmButtonText: "确定"
					    },function(){
					    	clearStorageData();
					    	window.location.href = "myApply.html?projectName="+projectName;
					    });
			    	}else{
			    		var errMsg=getErrMsg(data.result);
			    		alert(errMsg);
			    	}
			    })  
	   }
	   //走变更的
	   if(Bg){
		   var scope=getAngularScope("EditData"); 
		   console.log("走变更的");
		    //data1
		   project={
				   "projectId":projectId,
				   "projectName":requestEditData.taskName,
				   "projectManagerId":JSON.parse(userInfo).uid,
				   "projectManagerName":JSON.parse(userInfo).fullName,
				   "categoryId":categoryId,
				   "subcategory":JSON.stringify(requestEditData['radioed']['02']),
				   "cycleType":JSON.stringify(requestEditData['radioed']['01']),
				   "projectExtInfo":JSON.stringify(requestProjectExtInfo),
			       "startTime":requestEditData.startTime,
				   "completeTime":requestEditData.endTime,
				   "telno":requestEditData.myNum,
				   "email":requestEditData.email,
				   "projectMilestone":"项目里程碑"
		      }
		   //data2
		   
		   projectCosts ={
				   "projectCosts":tableRuleData
		   }
		   //data3
		   projectTaskDetail={
				   projectTaskDetail:tablePostDataObj 
		   }
		   data1=project;
		   data2=projectCosts;
		   data3=projectTaskDetail;
		  var  projectId =parseInt(projectId);
		  console.log(typeof projectId);
		  var result={};
		  if(Bg==2){
			  result={"result":0,"comment":"dede","type":"approve"};
		  }
		   var obj={
				    "request.projectId":projectId,
				    "request.dataId":scope.changeDataId,
				    "request.result":JSON.stringify(result),
				    "request.projectCategory":categoryId,
				    "request.data1":JSON.stringify(data1),
					"request.data2":JSON.stringify(data2),
				    "request.data3":JSON.stringify(data3),
					"request.data5": JSON.stringify(scope.data)
		   } 
		   console.log("hahhahah");
		   console.log(JSON.stringify(obj));
		   edeitTableSaveAjax(obj,function(data){
		    	if(data.result == 0){
		    		swal({
				    	title: "提交成功",
				    	text: "1秒后自动关闭",
				    	timer: 1000,
				    	confirmButtonText: "确定"
				    },function(){
				    	clearStorageData();
				    	window.location.href = "myApply.html?projectName="+projectName;
				    });
		    	}else{
		    		var errMsg=getErrMsg(data.result);
		    		alert(errMsg);
		    	}
		    }) 
	   }
	  
}

function refuse(){
	   //通过URL获取参数
	   var parm=parseQueryString();
	   //获取projectId
	   var projectId=parm.projectId;
	   var projectName=parm.projectName;
	   var scope=getAngularScope("EditData"); 
	result={"result":1,"comment":"dede","type":"approve"};
	   var obj={
			    "request.projectId":projectId,
			    "request.dataId":scope.changeDataId,
			    "request.result":JSON.stringify(result),
			    "request.projectCategory":categoryId,
			    "request.data1":"",
				"request.data2":"",
			    "request.data3":"",
				"request.data5": ""
	   } 
	   edeitTableSaveAjax(obj,function(data){
	    	if(data.result == 0){
	    		swal({
			    	title: "提交成功",
			    	text: "1秒后自动关闭",
			    	timer: 1000,
			    	confirmButtonText: "确定"
			    },function(){
			    	clearStorageData();
			    	window.location.href = "myApply.html?projectName="+projectName;
			    });
	    	}else{
	    		var errMsg=getErrMsg(data.result);
	    		alert(errMsg);
	    	}
	    }) 
}
function clearStorageData()
{
	var storage = window.localStorage; 
	for (var i=0, len = storage.length; i  <  len; i++){
		if(storage[i]=="tablePost"||storage[i]=="tableRule"||storage[i]=="tableRuleInner"||storage[i]=="tableEdit")
		{
			localStorage.removeItem(storage[i]);
		}
	}
	//localStorage.clear();
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
	    	console.log(data);
	    	fileUploadNewApply["newApply"]= data.responseInfo.projectAnnexs;
	    	projectAnnexs=data.responseInfo.projectAnnexs;
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}

var submitSimpleFlag=true;

function submitSimple(){
	
	

	if(submitSimpleFlag){
		 tableEditSave('tableEdit');
		 tableSave('tableRuleInner');
		 tableSave('tableRule');
		 tableEditSave('tablePost');
		 getAllData();
		 submitSimpleFlag=false;
    }
	else
	{
		
	}
    setTimeout(function(){
    	submitSimpleFlag=true;
    },1500); //点击后相隔多长时间可执行
    
  }









