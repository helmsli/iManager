//回调函数
function changeEnghlish(string){
	if(string=="天"){
		string="day"
	}
	if(string=="月"){
		string="month"
	}
	if(string=="年"){
		string="year"
	}
	return string;
}
function approvalRefreshUi()
{  
	document.getElementById("approveTitle").innerHTML="结项审批";
	var scope=getAngularScope("projectModel");
	try
	{
		console.log("******************************we");
		var contextDataStr =  localStorage.getItem("data");
		console.log(contextDataStr);
		var contextData = JSON.parse(contextDataStr);
		scope.commonbiz={};
		scope.commonbiz.data3=contextData.companyName;
		scope.commonbiz.projectName=contextData.projectName;
		console.log(contextDataStr);
	}
	catch(e)
	{}
	//var title = APPROVE_TITLE[scope.gloablParm.state];
	scope.suggestion={
		title:"审批",
		result:255255,
		comment:"",
		attachTitle:"附件",
		setCycleTime:false,
		commentsFlag:true,
		radioFlag:true,
		radioRefuseFlag:true//只有初期的部门经理可以拒绝
	}
	scope.setCycleTimeObj={
			cycle:"",
			unit:"天"
	};
  scope.setPersonFlag=false;

var state=scope.gloablParm.state;
scope.fileUpload={};
//状态配在config中	
if(state=="officerApproval"){
	scope.suggestion.attachTitle="附件";//如果是尽职调查，则显示尽职调查附件
	scope.setPersonTittle=setPerson.dispatcherMasterApproval[0];
	scope.setPersonFlag=true;
	scope.suggestion.commentsFlag=false;
}
if(state=="departleaderApproval"){
	scope.setCycleTime=true;
}
if(state=="PMSelfConclusion"||state=="departleaderConclusion"){
	scope.suggestion.radioFlag=false;
}
if(state=="departleaderApproval"){
	scope.suggestion.radioRefuseFlag=true;
}
$("#setTime").on("keydown",function(e){
	 return digitInputInt($(this), e);  
})
}


//点击上传需要绑定的函数，需要向后台提交的东西
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
	    	  
	    	document.getElementById("submitMaterialMetaphase").value="";
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	    	document.getElementById("submitMaterialMetaphase").value="";
	        alert(e);
	    }
	});
}

function submitApproval()
{
	
	var scope=getAngularScope("projectModel");
	var projectManagerModelScope=getAngularScope("projectManagerModel");
	var result = {};
	//构造APProve的结果;
	result.result = scope.suggestion.result;
	result.comment = scope.suggestion.comment;
	result.type = "approve";
	var data2="";
	if(scope.fileUpload[scope.gloablParm.state])
	{
		result.isAttach = APP_ContainAttach.attach;
		data2= JSON.stringify(scope.fileUpload[scope.gloablParm.state]);
	}
	var obj={	   
			"request.dataId":scope.gloablParm.dataId,			
			"request.result":result.comment,
			"request.status":result.result.toString(),
			"request.data8":data2	
			
	};
  	console.log(JSON.stringify(obj));
  	ajaxApprove(obj,function(data){
	    //console.log(JSON.stringify(data));
		if(data.result == 0){
			window.history.go(-1);
		}
    });	


}
function ajaxApprove(obj,callBack){
   
	var options ={
	        "url": "/commonbiz/application/approve",
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


function initApproval()
{
	try{
	
	
	
	
	initFileStateListener(approvalRefreshUi);
	}
	catch(e)
	{}
}
//只允许输入数字
function digitInputInt(el, e) {  
    var ee = e || window.event; // FF、Chrome IE下获取事件对象  
    var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    var val = el.text();  
    if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
        	return false;
            prevent(e);
    } else if(c==9||c==116){
    	return true;
    	}else {
        if ((c != 8 && c != 46 && // 8 - Backspace, 46 - Delete  
            (c < 37 || c > 40) && // 37 (38) (39) (40) - Left (Up) (Right) (Down) Arrow  
            (c < 48 || c > 57) && // 48~57 - 主键盘上的0~9  
            (c < 96 || c > 105)) // 96~105 - 小键盘的0~9  
            || e.shiftKey) { // Shift键，对应的code为16  
            prevent(e); // 阻止事件传播到keydown  
        	return false;
        }  
    }  
    return true;
}  
function prevent(e) {
	e.returnValue = false;
}

initApproval();











