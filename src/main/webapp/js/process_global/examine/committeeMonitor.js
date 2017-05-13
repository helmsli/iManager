App.controller('initQuestionData', function($scope) {
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	var state=parm.state;
	var taskId=parm.taskId;
	var projectCategory=parm.projectCategory;
	$scope.mainParm=parm;
	$scope.projectCategory=projectCategory;
	$scope.state=state;
	$scope.taskId=taskId;
	$scope.from=parm.from;
	$scope.complete=parm.complete;
	var userInfo= localStorage.getItem("userInfo");
	userId=JSON.parse(userInfo);
	$scope.userId=userId.uid;
	$scope.templateName="";
	$scope.questionList=[];//问题列表
	$scope.projectInfo={};//项目基础信息
	$scope.reportInfo={
			score:"",//评分
			reviewNum:"",//开评审会次数
			fileNum:"",//输出文档个数
			codeNum:"",//代码走读次数
			whetherAdjust:"",//有无调整
			adjustBefore:"",//调整前情况
			adjustAfter:"",//调整后情况
			adjustWhy:"",//调整理由
			opinion:"",//意见
			examine:"",//审批情况
	};
	$scope.projectLable={
			projectBase:"项目基本情况",
			projectExecuted:"项目执行情况",
			score:"评分",
			reviewNum:"开评审会次数",
			fileNum:"输出文档个数",
			codeNum:"代码走读次数",
			whetherAdjust:"有无调整",
			adjustBefore:"调整前情况",
			adjustAfter:"调整后情况",
			adjustWhy:"调整理由",
			opinion:"意见",
			examine:"审批情况",
	};
	
	/**
	 * 页面加载初始化
	 * **/
	$scope.initQuestion=function()
	{
		$scope.initTemplatePath();
	}
	/**
	 *初始化模板加载路径
	 * **/
    $scope.initTemplatePath=function()
    {
		var parm = parseQueryString();
		var projectName=parm.projectName;
		var templateName=getCommitteeMonitorTemplatePath(projectName);
		//加载模板
		$scope.templateName=templateName;
    }
    /**
	 *项目基本信息加载初始
	 * **/
    $scope.initQuestionTable=function()
	{
		initData();
		initTableEvent();
	}
	
    /**
	 *提交报告
	 * **/
   $scope.submitQuestion=function()
   {
		var scope=$scope;
    	var reportInfo={
    			reportInfo:scope.reportInfo,
    	};
    	var result = {};
    	result.result = 0;
		var obj={
				"request.taskId":scope.mainParm.taskId,
				"request.projectId":scope.mainParm.projectId,  //项目ID
				"request.serviceType":"YDJC",  //业务类型(决策委员会月度监测)
				"request.data1":JSON.stringify(scope.reportInfo),  //尽职调查--填写评价
		};
		console.log(JSON.stringify(obj));
		completeTask(result,obj,function(data){
			if(data.result==0){
				window.history.go(-1);
			}
		})
    	
    	//提交
	}
    //关闭
	$scope.cancelCurrentPageWin=function(){
		window.history.go(-1);
	}
});

/**
 *初始化加载问题列表JSON
 * **/
function initData()
{   
	var scope=getAngularScope("initQuestionData");
	obj={
			"request.projectId":scope.mainParm.projectId
	};
	getBaseInfoByServer(obj,function(data){
		if(data.result==0){
			console.log(data);
			var project=data.responseInfo.project;
			var cost=JSON.parse(project.projectCosts);
			var projectManager=data.responseInfo.projectManager;
			scope.projectInfo={
					projectId:project.projectId,
					projectManagerName:projectManager.username,
					budget:0,//没有总计，先拿payPrice
					projectName:project.projectName,
					leaderDepartment:projectManager.company_name,//需要的信息从data里边去取*/			
			};
		}
	})
}

/*
 * 阻止冒泡事件
 * */
function prevent(e) {
	e.returnValue = false;
}

/*
 * 处理只允许输入数字的函数（可带小数点）
 * */
function digitInputNumber(el, e) {  
    var ee = e || window.event; // FF、Chrome IE下获取事件对象  
    var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    var val = el.text();  
    if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
        if(val.indexOf(".") >= 0)
        {
        	return false;
            prevent(e);
        }
        
    } else {
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

/*
 * 处理只允许输入数字(不带小数点)
 * 
 * */
function digitInputInt(el, e) {  
    var ee = e || window.event; // FF、Chrome IE下获取事件对象  
    var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    var val = el.text();  
    if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
        	return false;
            prevent(e);
    } else {
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

/**
 *静态表格事件BLUR绑定并监听输入内容
 *B
 * **/
function initTableEvent()
{
	$("table").on("blur","td[contenteditable='true']",function(){
		var _this=$(this);
		var id=$(this).parent().attr("id");
		var name=_this.attr("data-columnName");
		var value=_this.text();
		var scope=getAngularScope("initQuestionData");
		scope.reportInfo[name]=value;
	});
	//分值框内只能输入数字
	$("table").on("keydown","td[contenteditable='true']",function(e){
		 var _this=$(this);
		 var dataType=_this.attr("data-type");
		 if(dataType=="number")
			{
			 return digitInputNumber($(this), e);  
			 return;
			}
		
	 });
}





















