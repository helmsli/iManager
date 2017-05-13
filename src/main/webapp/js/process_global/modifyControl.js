App.controller('initBgData', function($scope) {
   $scope.baseInfoInit=function()
   {
	   $scope.initDataAfterModify();
   }
	/***
	 *  统一的数据处理，把data1和data2和data3处理变成以前查看的数据
	 * */
	function processData(data1,data2){
		for(var i in data2){
			data1[i]=data2[i];
		}
		return data1;
	}
	//用来处理到的数据更容易比较
	function changeObj(compareNewData,newObject){
		 var projectExtInfo=JSON.parse(newObject.projectExtInfo);
		 compareNewData.taskplan=projectExtInfo.taskplan;//项目实施计划及输出表
		 compareNewData.taskDescribe=projectExtInfo.taskDescribe;
		 compareNewData.taskbackground=projectExtInfo.taskbackground;
		 compareNewData.taskgoal=projectExtInfo.taskgoal;
		 compareNewData.taskDanger=projectExtInfo.taskDanger;
		 compareNewData.taskcreate=projectExtInfo.taskcreate;
		 compareNewData.projectTaskDetail=newObject.projectTaskDetail;//项目需要的准备条件
		 compareNewData.projectCosts=newObject.projectCosts;//项目预算
		 compareNewData.email=newObject.email;
		 compareNewData.completeTime=newObject.completeTime;
		 compareNewData.startTime=newObject.startTime;
		 compareNewData.subcategory=newObject.subcategory;
		 compareNewData.cycleType=newObject.cycleType;
		 compareNewData.projectName=newObject.projectName;
		 compareNewData.telno=newObject.telno;
		 return compareNewData;
	 }
	/***
	 * 初始化数据
	 * 功能：获取项目数据并写入全局变量 mainParm
	 * */
	$scope.initDataAfterModify=function ()
	{	
		 var myParm=parseQueryString();
		 var projectId=myParm.projectId;
		 var projectId=parseInt(projectId);
		//走ajax请求，请求真实数据的时候放开
		var obj={
				"request.projectId":projectId
			};
		
		console.log("变更查看的请求");
		console.log(obj);
		
		//从后台取到数据，显示在前台as
		bgDetailByServer(obj,function(data){
			if(data.result==0){
				console.log("我可以查看变更的数据了");
				console.log(data);
			 var data1=JSON.parse(data.responseInfo.commonBiz.data1);
			 var data2=JSON.parse(data.responseInfo.commonBiz.data2);
			 var data3=JSON.parse(data.responseInfo.commonBiz.data3);
			 var data5=JSON.parse(data.responseInfo.commonBiz.data5);
			 $scope.data5=JSON.parse(data.responseInfo.commonBiz.data5);
			 var oldObject=($scope.data5).responseInfo.project;
			 var newObject={};
			 prepaireCondition={};
			 for(var i in data1){
				 newObject[i]=data1[i];
				 
			 }
			 for(var i in data2){
				 newObject[i]=data2[i];
			 }
			 for(var i in data3){
				 newObject[i]=data3[i];
			 }
			 var compareOldData={};
			 var compareNewData={};
			 compareNewData=changeObj(compareNewData,newObject);
			 compareOldData=changeObj(compareOldData,oldObject);
			 console.log("比较一下");
			 console.log(compareNewData);
			 console.log(compareOldData);
			 for(var i in compareNewData){
				 if(compareNewData[i]!=compareOldData[i]){
					 
				 }
			 }
			
		
			 
			
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 
			 data1=processData(data1,data2);
			 $scope.project=processData(data1,data3);
				var projectExtInfo=JSON.parse($scope.project.projectExtInfo);
				try{
					$scope.project.CycleType=JSON.parse($scope.project.cycleType)[0].value;
					$scope.project.subcategory=JSON.parse($scope.project.subcategory)[0].value;
				}catch(e){
					log(e+"initData:"+e.messageInfo);
				}
				$scope.projectExtInfo=projectExtInfo;
				$scope.Taskplan=JSON.parse(projectExtInfo.taskplan);
				$scope.ProjectCosts=JSON.parse($scope.project.projectCosts);
				$scope.ProjectTaskDetail=JSON.parse($scope.project.projectTaskDetail);
		}
		})
	}
})









































				 				