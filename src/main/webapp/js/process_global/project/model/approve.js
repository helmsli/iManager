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
	var scope=getAngularScope("projectModel");
	//var title = APPROVE_TITLE[scope.gloablParm.state];
	scope.suggestion={
		title:"审批",
		result:0,
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
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}

function submitApproval()
{
	var threeLeaderIdLsit="";
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
	if(projectManagerModelScope!=undefined){
		threeLeaderIdLsit=JSON.stringify(projectManagerModelScope.threeLeaderIdLsit);
	}
	scope.setCycleTimeObj.unit=changeEnghlish(scope.setCycleTimeObj.unit);
  	var obj={	   
			"request.taskId":scope.gloablParm.taskId,			
			"request.serviceType":scope.gloablParm.state,
			"request.data2":data2,
			"request.data10":threeLeaderIdLsit,//指定三级部门经理的
			"request.data9":JSON.stringify(scope.setCycleTimeObj)//指定监测时间的
	};
  	console.log(JSON.stringify(obj));
	completeTask(result,obj,function(data){
	    //console.log(JSON.stringify(data));
		if(data.result == 0){
			window.history.go(-1);
		}
    });	


}
function initApproval()
{
	try
	{
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











