var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', function($scope) {
	//表格标题
	$scope.titleList = [
	        "项目类型",         
			"服务地区",
			"申报项目个数合计",
			"预计服务人数合计",
			"申请金额合计(万元)"
			
	];
	
	$scope.pageFlag=true;
	//数据初始化--待申报的项目/我的发布的项目
	
	
	//数据初始化--待申报的项目
	$scope.initCountServicePerson=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
        var obj={
        		"request.serviceType": "application",	
        		"request.categoryId": "canlian"        		
        }
	   
	   
	    console.log(obj);
	    getCountServicePerson(obj);
	}
	
	
	$scope.clickTr=function(data)
	{
		  //alert("aaaaa:"+JSON.stringify(data));
		  var url="";
		  var parm=parseQueryString();
		  var projectName =parm.projectName||""; 
		  var myDate = new Date();
		  var nowMillSeconds = myDate.getTime(); 
		  var keshi = "";
		  var condition = {
				  "ptype":data.taskId,
				  "region":data.serviceOwner,
				  "keshi":keshi,				  
		  };
		  
		  
		  
		  if(data.taskId=="ding")
		   {
			  condition["keshi"]=data.data1;
			 // alert(JSON.stringify(condition));
			  localStorage.setItem("pcond",JSON.stringify(condition));
			  
			  url="newApply.html?projectName="+projectName+"&pcond=" +  nowMillSeconds +"&from="+from;;
			  
		  }
		  else
		  {
			  localStorage.setItem("pcond",JSON.stringify(condition));
			  
			  url="newApply.html?projectName="+projectName+"&pcond=" +  nowMillSeconds +"&from="+from;;
			    
		  }
		 // alert(url);
		  location.href=url;
	}
	
	$scope.exportApplyDetail=function()
	{
		satarAjax();
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
        var obj={
        		"request.serviceType": "application",	
        		"request.categoryId": "canlian"        		
        }
	   
	   
	    console.log(obj);
        getAppExportDetail(obj);
	}
	
	
	 $scope.attachDownLoad=function(annexName){
		 var url="/projectAnnex/fileDownLoad";
		     url= getBasePath()+url;
		     url=url+"?request.annexName="+annexName;
		     console.log(url);
		    // alert(url);
		     location.href =url;
	  }
	
	$scope.downloadAttatchment = function(publishId,data2)
	{
		//alert(data2);
		var objData2 = JSON.parse(data2||{});
		
		if(objData2 instanceof Array)
			{
			//alert(objData2[0].annexName);
			$scope.attachDownLoad(objData2[0].annexName);
			}
		else
			{
			alert("没有附件可以下载");
			}
	}
	/*
	 * 申报经理发布的项目
	 */
	$scope.declareDepartLeaderRelease = function(publishId,complete)
	{
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		var userInfo= localStorage.getItem("userInfo");
		roleIdValue=JSON.parse(userInfo).roleIds[0];
		console.log(userInfo);
		if(roleIdValue==roleIds.threeLeader){
			url="departmentCreate.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		}else{
			url="newApply.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		}
		
	   //url="departmentRelease_detail.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		
		location.href=url;
	}
	
	/*
	 * 申报经理发布的项目
	 */
	$scope.hrefProjectOptimize = function(publishId,complete)
	{
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		
		url="projectOptimize.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		
	   //url="departmentRelease_detail.html?projectName="+projectName+"&publishId="+publishId+"&from="+from;
		
		location.href=url;
	}
	
}]);

function getCountServicePerson(obj)
{
	var options ={
	        "url":"/commonbiz/countApplicationService",
	        "data": obj,
	        callBack: function(data) {
	        	callbackQueryApplicationList(data);
	        },
	        errCallBack:function(e)
	        {
	            console.log("服务器异常");
	        }
	    };
	 requestAjax(options);
}
function getAppExportDetail(obj)
{
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
	     url=url+"?request.fileName="+file + "&localFile=applyTotal.zip&type=applyExport";
	     console.log(url);
	     location.href =url;
	}
	else
	{
		
	}
}


function callbackQueryApplicationList(data){
	console.log(data);
	var scope=getAngularScope("myDataController");
	//分页
	if(data.result == 0){
		
		var dataLists=data.responseInfo.lists;
		var pageInfo=data.responseInfo.page;
		owner  = data.responseInfo.owner;
		
		
		
		
		//todo:monthly
		 for(var i in dataLists)
		{
			 try{
				 dataLists[i].apply_type="服务项目";
				 if(dataLists[i].taskId=="wei")
				 {
					 dataLists[i].apply_type="微创投项目";
				 }
				 else if(dataLists[i].taskId=="ding")
				 {
					 dataLists[i].apply_type="定向项目";
				 }
				 
				 //解析详细信息
				 if(dataLists[i].data5!="")
				 {
						 dataLists[i].detail =   JSON.parse(dataLists[i].data5);
				 }
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
					 dataLists[i].statusName="等待审批";
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

/*
 * xiuguo loading
 * function satarAjax() {
		    swal({
		    	title: "Are you sure?",
		        text: "You will not be able to recover this imaginary file!",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
		        confirmButtonText: "Yes, delete it!",
		        closeOnConfirm: false
		    }, function () {
		    	var myRow='<div class="md-preloader pl-size-md">'
	        		+'<svg viewbox="0 0 75 75"><circle cx="37.5" cy="37.5" r="33.5" class="pl-red" stroke-width="4" /></svg>'
	    			+'</div>'
	    			+'<p>Please wait...</p>';
    			swal({
    				title: " ",
    				text: myRow,
    				html:true,
    				timer: 2000,
    		        showConfirmButton: false
    			});
		    });
		}
	
 */
