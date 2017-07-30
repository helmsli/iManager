var Application_file_apply="file_applicationFile";
var Application_file_componyId="fileCompanyId";
var APPLICATION_status_=0;
var APPLICATION_serviceType="application";
//设置提交项目申报书验证属性
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
					"id":"departmentName",
					"validateRule":{"require":true}
				
				},{
					"id":"contactTel",
					"validateRule":{"require":true,"isNumber":true}
				},{
					"id":"contactEmail",
					"validateRule":{"isMail":true,"require":true}
				},{
					"id":"textfile_applicationFile",
					"validateRule":{"require":true}
				
				},{
					"id":"contactName",
					"validateRule":{"require":true}
				
				}
				
				
				];
 




//用户注册控制器
App.controller('application_public', function($scope){
	//语言参数
	
	var parm = parseQueryString();
	$scope.gloablParm=parm;
	
    $scope.lang = window.localStorage.lang||'zh-CN';
	$scope.application={};
	$scope.application.comType="she";
	/*校验格式化*/
	$scope.formValid = new FormValid({"formId":"formApplication",formField:applicationvalidOptions});
	
	try 
    { 
     $scope.beijing_diqu = beijing_diqu;
     $scope.XiangMu_diqu = beijing_diqu;
    }
    catch (e) 
    {}
    
    
    $scope.saveApplication = function ()
    {
    	//alert("save application");
    	
    	
		//信息表单校验
		var subFlag=$scope.formValid.beforeSubmit();
		if(!subFlag )
		{
			return;
		}
		
		
    	var parm = parseQueryString();
    	//类型名;coomarts,cootalk,lottery
    	var projectName=parm.projectName;
    	
    	
    	
    	var categoryId="canlian";
    	
    	//申报书
    	var fileApplication="";
    	if($scope.fileUpload[Application_file_apply])
    	{
    		
    		fileApplication= JSON.stringify($scope.fileUpload[Application_file_apply]);
    	}
    	//月报表
    	var fileCompony = "";
    	if($scope.fileUpload[Application_file_componyId])
    	{
    		
    		fileCompony= JSON.stringify($scope.fileUpload[Application_file_componyId]);
    	}
    	var alldata={"pName":$scope.application.projectName,
    			     "personName":$scope.application.contactName,
    			     "person":$scope.application.contactTel,
    			     "address":"科室",
    			     "addressCode":"keshi",
    			     "serviceAddress":"科室",
    			     "serviceAddressCode":"keshi",
    			     "amount":$scope.application.amount,
    			    "cname":$scope.application.companyName,
    			    "cType":"others",
    			    "email":$scope.application.contactEmail,
    			    "serviceAmount":$scope.application.serviceAmount
    			    };
    	var obj={	   
    			"request.projectCategory":categoryId,
    			"request.projectId":0,    	
    			"request.serviceType":APPLICATION_serviceType,
    			"request.projectName":$scope.application.projectName,
    			"request.createPersonName":$scope.application.contactName,
    			"request.createPerson":$scope.application.contactTel,
    			"request.status":APPLICATION_status_,
    			"request.serviceOwner":"keshi",    			
    			"request.data1":$scope.application.departmentName,
    			"request.data2":$scope.application.amount,
    			"request.data3":$scope.application.companyName,
    			"request.data4":"others",
    			"request.data5":JSON.stringify(alldata),    			
    			"request.data6":fileApplication,
    			"request.data7":fileCompony,
    			"request.processInstanceId":$scope.application.serviceAmount,
    			"request.taskId":"ding",
    			
    	};
    	try
    	{
	    	var dataid = parm.dataId;
	    	if(typeof(dataid)=="undefined"){
	    		dataid="0";
	    	}
	    	
	    	if(dataid!="0")
	    	{
	    		obj.dataId=dataid;
	    	}
    	}
    	catch(e)
    	{
    		
    	}
    	//console.log("发送给后台的obj");
    	console.log(JSON.stringify(obj));
    	submitApplication("/task/submitApplication",obj,function(data){
    		console.log("&&&&&&&&&&&&&&&&&&&&");
    		console.log(data);
    		if(data.result == 0){	
    			
    			//alert("恭喜您，成功申请项目.");
    			$('#modelApplicationOk').modal();
    			
    			return;
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
    
    $scope.selectProjectTypeDistrict =function(code,name)
	{
		
		
		setTimeout(function () {
			 $scope.$apply(function() {
			$scope.application.registerAddress=name;
			$scope.application.registerAddressCode=code;
			 });
			 var subFlag=$scope.formValid.beforeSubmit('registerAddress');
			 var checkbox = document.getElementById(code);
				checkbox.checked = false; 
		     // AngularJS unaware of update to $scope
		  }, 200);
		$('#modelForProjectType').modal('hide');
		
	}
    
    $scope.selectCompanyDistrict =function(code,name)
	{
		
		
		setTimeout(function () {
			 $scope.$apply(function() {
			$scope.application.companyRegisterAddress=name;
			$scope.application.companyRegisterAddressCode=code;
			 });
			 var subFlag=$scope.formValid.beforeSubmit('companyRegisterAddress');
			 var checkbox = document.getElementById("company-"+code);
				checkbox.checked = false; 
		     // AngularJS unaware of update to $scope
		  }, 200);
		$('#modelForRegisterAddress').modal('hide');
		
	}
    
    //初始化函数
	$scope.init=function()
	{
		
		if(!$scope.gloablParm.dataId)
		{
			console.log("***********************");
			return;
		}
		var obj={	   
				"request.dataId":$scope.gloablParm.dataId,			
				
				
		};
	  	console.log(JSON.stringify(obj));
	  	getApplication(obj,function(data){
	  		console.log(data);
	  		if(data.result == 0){
	  			var commonbiz = data.responseInfo.commonBiz;
	  			setTimeout(function () {
	  				$scope.$apply(function() {
	  					
	  					$scope.application.projectName=commonbiz.projectName;
	  					$scope.application.contactName=commonbiz.createPersonName;
	  					$scope.application.contactTel=commonbiz.createPerson;
	  					$scope.application.departmentName=commonbiz.data1;
	  					$scope.application.amount=commonbiz.data2;
	  					$scope.application.companyName=commonbiz.data3;
	  					$scope.application.serviceAmount=commonbiz.processInstanceId;
	  			        try{
	  					   var allData = JSON.parse(commonbiz.data5);
	  					
	  			          $scope.application.contactEmail=allData.email;
	  			        }
	  			        catch(e)
	  			        {
	  			        	
	  			        }
	  					
	  				});//end apply
	  				
	  			});
	  		}
	  		else
	  		{
	  		  alert("网络错误，需要重新刷新");
	  		location.reload();
	  		}
	  	});
		
	}
	
	
	
});


function checkNum(obj) {
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
    }
    if (obj != null) {
        //检查小数点后是否对于两位
        if (obj.value.toString().split(".").length > 1 && obj.value.toString().split(".")[1].length > 4) {
            alert("小数点后多于四位！");
            obj.value = "";
        }
    }
};
function checkTel(obj) {
    //检查是否是非数字值
    if (isNaN(obj.value)) {
        obj.value = "";
    }
    if (obj != null) {
        //检查小数点后是否对于两位
        if (obj.value.toString().split(".").length > 1 && obj.value.toString().split(".")[1].length > 2) {
            alert("小数点后多于两位！");
            obj.value = "";
        }
    }
};

function uploadApplication(id){
	document.getElementById("btn_application").disabled=true;
	var scope=getAngularScope("application_public");
	console.log("*****************************:" + id + ":" + getBasePath());
	uploadFile.ajaxFileUpload({
		
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId:id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	var scope=getAngularScope("application_public");
	    	data=JSON.parse(data);
	    	if(!scope.fileUpload)
	    	{
	    		scope.fileUpload=new Object();
	    	}
	    	scope.fileUpload[id]= data.responseInfo.projectAnnexs;
	    
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	console.log(JSON.stringify(data.responseInfo.projectAnnexs));
	    	
	    	 scope.formValid.beforeSubmit('text'+id);
	    	$('#btn_application').removeAttr("disabled");
	    	 document.getElementById(id).value="";
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert("文件上传失败,请重新填写");
	    	//location.reload();
	        try{
	        	
		           var textid = "text" +id;
		           document.getElementById(textid).value="";
		           document.getElementById(id).value="";
		        }
		        catch(e)
		        {}
	    	
	    }
	});
}
/**
 * 经理创建新的发布(coomarts)
 */
function submitApplication(url,obj,callBack){
	 
	var options ={
	        "url": url,
	        "data": obj,
	        callBack: function(data) {
	            callBack(data);
	        },
	        errCallBack:function(e)
	        {
	            alert("服务器异常");
	            //location.reload();
	        }
	    };
	 requestAjax(options);
}
function downloadApplicationTemplate()
{
	
	//alert("click");
	 var url="/projectAnnex/fileDownLoad";
     url= getBasePath()+url;
     url=url+"?request.annexName="+"canlian_application_ding.docx";
    // console.log(url);
     location.href =url;
}
function getApplication(obj,callBack){
	   
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