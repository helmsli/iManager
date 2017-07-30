

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
	
	gueryApprovalResult();
	
	var scope=getAngularScope("projectModel");
	//var title = APPROVE_TITLE[scope.gloablParm.state];
	scope.suggestion={
		title:"审批",
		result:4,
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

function gueryApprovalResult()
{
	
	var scope=getAngularScope("projectModel");
	console.log(scope.gloablParm);
	var data_id = Number(scope.gloablParm.dataId);
	console.log(data_id);
	
	var obj={	   
			"request.dataId":scope.gloablParm.dataId,			
			
			
	};
  	console.log(JSON.stringify(obj));
  	ajaxApprove(obj,function(data){
	    //console.log(JSON.stringify(data));
		if(data.result == 0){
			console.log(JSON.stringify(data));
			var commonbiz = data.responseInfo.commonBiz;
			
			 if(commonbiz.status==STATUS_APPLY)
			 {
				 commonbiz.approveResult="等待审批";
			 }
			 else if(commonbiz.status==STATUS_NEED_CHANGE)
			 {
				 commonbiz.approveResult="需要修改申报书";
			 }
			 else if(commonbiz.status==STATUS_REJECTED)
			 {
				 commonbiz.approveResult="审批不通过";
			 }
			 else if(commonbiz.status==STATUS_AGREED)
			 {
				 commonbiz.approveResult="审批通过";
			 }
			 else
			 {
				 commonbiz.approveResult="等待审批";
			 }
			 
			
			setTimeout(function () {
				 scope.$apply(function() {
					 scope.suggestion.title="审批结果";
					 scope.commonbiz=commonbiz;
					 /*
					 commonbiz.result="abc";
					 commonbiz.data8=commonbiz.data6;
					 */
					 scope.show_btn_refresh = false;
					 scope.show_btn_application=true;
					 if(commonbiz.result!="")
					 {
						 commonbiz.isShowResult=true;	 
					 }
					 var approveAttatch =[{"annexId":0,"originalFilename":"没有审批意见附件"}];
				     try
				     {
				    	 approveAttatch=JSON.parse(commonbiz.data8);
				    	 commonbiz.data8 = approveAttatch;
				     }
				     catch(e)
				     {
				    	 approveAttatch =[{"annexId":0,"originalFilename":"没有审批意见附件"}];
				     }
					
					 if(approveAttatch[0].annexId==0)
					 {
						 document.getElementById("btn_downloadApprove").disabled=true;	 
					 }
					 else
					 {
						 document.getElementById("btn_downloadApprove").disabled=false;
					  }
					 document.getElementById("submitMaterialMetaphaseInput").value = approveAttatch[0].originalFilename;
					 
					 
				 });
			     // AngularJS unaware of update to $scope
			  }, 50);
		}
		else
		{
			window.history.go(-1);	
		}
    });	


}
function ajaxApprove(obj,callBack){
   
	var options ={
	        "url": "/commonbiz/getByDataId",
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

function modifyApplication()
{
	var scope=getAngularScope("projectModel");
	var url= getBasePath()+"/views/application.html?dataId="+scope.gloablParm.dataId ;
	if(scope.commonbiz.taskId=="ding")
	{
		url= getBasePath()+"/views/application_ding.html?dataId="+scope.gloablParm.dataId ;
		 	
	}
	else if(scope.commonbiz.taskId=="wei")
	{
		url= getBasePath()+"/views/application_wei.html?dataId="+scope.gloablParm.dataId ;
		 	
	}
	
	
	
	 location.href =url;
}
function downloadApproveAttatch()
{
	try{
	var scope=getAngularScope("projectModel");
	//alert("click");
	 var url="/projectAnnex/fileDownLoad";
    url= getBasePath()+url;
    url=url+"?request.annexName="+scope.commonbiz.data8[0].annexName;
   // console.log(url);
    location.href =url;
	}
	catch(e)
	{
		
	}
}
initApproval();











