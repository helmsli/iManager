App.controller('projectManagerModel', function($scope) {
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	var state=parm.state;
	var taskId=parm.taskId;
	$scope.taskId=taskId;
	var projectCategory=parm.projectCategory;
	$scope.mainParm=parm;
	$scope.projectNameTittle=parm.projectNameTittle;
	console.log($scope.projectNameTittle);
	$scope.projectCategory=projectCategory;
	$scope.state=state;
	$scope.taskId=taskId;
	$scope.from=parm.from;
	$scope.complete=parm.complete;
	$scope.previewMonthlyReportFlag = false;
	var userInfo= localStorage.getItem("userInfo");
	userId=JSON.parse(userInfo);
	$scope.userId=userId.uid;
	
	$scope.InitMonthlyReportTask=function()
	{
		
	}
    
	
    /**
	 *项目经理提交周期报告(月报表)
	 * **/
   $scope.submitQuestion=function()
   {
	    var scope=getAngularScope("projectManagerModel");
	    //过滤没有内容的行，不发给后台了
	   
	    var result = {};
    	result.result = 0;
		var obj={
				"request.taskId":scope.taskId,
				"request.serviceType":"submitMonthlyReport",  //业务类型(项目经理月度提交-工作进展表)
				"request.data1": JSON.stringify(scope.commonBiz.data1), //尽职调查--填写得分//尽职调查--填写评价
				"request.data2":JSON.stringify(scope.commonBiz.data2),
				"request.data3":JSON.stringify(scope.commonBiz.data3)
				
		};
		completeTask(result,obj,function(data){
			if(data.result==0){
				window.history.go(-1);
			}
		})
    
   }
   //获取时间value
  function getInputDatePickerVal(){
      var value =$("#scoringTime").find("input").val();
 	 return value;
  }  
  //关闭
  $scope.cancelCurrentPageWin=function(){
	  window.history.go(-1);
  }
});

/***
 * 点击上传需要绑定的函数，需要向后台提交的东西
 */
var monthlyFileName="monthlyReport";
var monthlyFileAttatchment="monthlyAttatchment";
function uploadMonthlyReport(id){
	document.getElementById("btn_subMonthlyReport").disabled=true;
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
	    	scope.fileUpload[monthlyFileName]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	console.log(JSON.stringify(data.responseInfo.projectAnnexs));
	    	scope.$applyAsync(scope.projectAnnexs);
	    	preViewMonthlyReport();
	    	
	    	//$('#btn_subMonthlyReport').removeAttr("disabled");
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        location.reload();
	    }
	});
}


function uploadMonthlyAttatchment(id){
	document.getElementById("btn_subMonthlyReport").disabled=true;
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
	    	scope.fileUpload[monthlyFileAttatchment]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	console.log(JSON.stringify(data.responseInfo.projectAnnexs));
	    	scope.$applyAsync(scope.projectAnnexs);
	    	document.getElementById("btn_subMonthlyReport").disabled=false;
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	    	location.reload();
	    }
	});
}


/**
 * 提交月度报告
 */
function submitMonthlyReport()
{
	
	var scope=getAngularScope("projectManagerModel");
	var parm = parseQueryString();
	//类型名;coomarts,cootalk,lottery
	var projectName=parm.projectName;
	var categoryId=getMyProjectOptions(projectName);
	
	//照片等附件资料
	var mrAttatchment="";
	if(scope.fileUpload[monthlyFileAttatchment])
	{
		
		mrAttatchment= JSON.stringify(scope.fileUpload[monthlyFileAttatchment]);
	}
	//月报表
	var monthlyReport = "";
	if(scope.fileUpload[monthlyFileName])
	{
		
		monthlyReport= JSON.stringify(scope.fileUpload[monthlyFileName]);
	}
	var obj={	   
			"request.projectCategory":categoryId,
			"request.data1":monthlyReport,
			"request.data2":mrAttatchment
			
			
	};
	//console.log("发送给后台的obj");
	console.log(JSON.stringify(obj));
	submitMonthlyToServer("/task/submitMonthlyReport",obj,function(data){
		console.log("&&&&&&&&&&&&&&&&&&&&");
		console.log(data);
		if(data.result == 0){	
			
			window.history.go(-1);
		}
		else
		{
			try
			{
				console.log(JSON.stringify(data));
				var errMsg=getErrMsg(data.result);
				
				if(typeof(errMsg) != "undefined")
				{
					alert(errMsg);	
				}
	    		
				else
				{
					alert("请检查文件内容是否合法或者部分内容是否没有填写");
				}
				
			}
			
    		catch(err)
    		{
    			alert("请检查文件内容是否合法或者是否没有填写");
    		}
		}
    });		
}

function preViewMonthlyReport()
{
	var scope=getAngularScope("projectManagerModel");
	var parm = parseQueryString();
	//类型名;coomarts,cootalk,lottery
	var projectName=parm.projectName;
	var categoryId=getMyProjectOptions(projectName);
	
	//照片等附件资料
	var mrAttatchment="";
	if(scope.fileUpload[scope.state])
	{
		
		mrAttatchment= JSON.stringify(scope.fileUpload[scope.state]);
	}
	//月报表
	var monthlyReport = "";
	if(scope.fileUpload[monthlyFileName])
	{
		
		monthlyReport= JSON.stringify(scope.fileUpload[monthlyFileName]);
	}
	var obj={	   
			"request.projectCategory":categoryId,
			"request.data1":monthlyReport,
			"request.data2":mrAttatchment
			
			
	};
	//console.log("发送给后台的obj");
	console.log(JSON.stringify(obj));
	submitMonthlyToServer("/task/preViewMonthlyReport",obj,function(data){
		if(data.result == 0){	
			try
			{ 
				console.log("**************************");
			    console.log(data);
				var commonBiz = data.responseInfo.commonbiz;
				 console.log(data);
					
				
				commonBiz.monthlyReportFile =   JSON.parse(commonBiz.data1);
				
				if(commonBiz.data2!=""){
					commonBiz.monthlyReportAttatchment =   JSON.parse(commonBiz.data2);
				}
				 
				if(commonBiz.data3!="")
				{
					commonBiz.reportDetail =JSON.parse(commonBiz.data3);
						
					commonBiz.reportDetail = commonBiz.reportDetail[0];	
					if(commonBiz.reportDetail.hasOwnProperty("month"))
					{
						commonBiz.reportDetail.month = commonBiz.reportDetail.month + "月份"; 
					}
						
					
					 console.log(commonBiz);
					if(commonBiz.reportDetail.hasOwnProperty("progressList"))
					 {	 
						//console.log(JSON.stringify(commonBiz.reportDetail.progressList));
						// commonBiz.reportDetail.progressList = JSON.parse(commonBiz.reportDetail.progressList);
					  }
				}
				
				
				 
				scope.commonBiz = commonBiz;
				scope.previewMonthlyReportFlag=true;
				 setTimeout(function() {
					 var scope=getAngularScope("projectManagerModel");
					 scope.$apply(function() {  
				            //wrapped this within $apply  
						 scope.commonBiz = commonBiz;   
				           // console.log('message:' + $scope.message);  
				          });  
					 
					    
				     }, 200);
				     
				 	
					 console.log("aaa");
					 document.getElementById("btn_subMonthlyReport").disabled=false;
			}
			catch(err)
			{
				
			}
				 
		}
		else
			{
				try
				{
					console.log(JSON.stringify(data));
					var errMsg=getErrMsg(data.result);
					
					if(typeof(errMsg) != "undefined")
					{
						alert(errMsg);	
					}
		    		
					else
					{
						alert("请检查文件内容是否合法或者部分内容是否没有填写");
					}
					
				}				
	    		catch(err)
	    		{
	    			alert("请检查文件内容是否合法或者部分内容是否没有填写");
	    		}
			}
    });		
}


/**
 * 经理创建新的发布(coomarts)
 */
function submitMonthlyToServer(url,obj,callBack){
	 
	var options ={
	        "url": url,
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

function downloadMonthlyTemplate()
{
	
	//alert("click");
	 var url="/projectAnnex/fileDownLoad";
     url= getBasePath()+url;
     url=url+"?request.annexName="+"monthlyReportTemplate.xls";
    // console.log(url);
     location.href =url;
}