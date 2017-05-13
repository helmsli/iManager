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
			score:"",//总分
			scoringMan:"",//打分人
			scoringDepartment:"",//打分部门
			scoringTime:"",//打分日期
	};
	$scope.projectLable={
			score:"总分：",//总分
			scoringMan:"打分人：",//打分人
			scoringDepartment:"打分部门：",//打分部门
			scoringTime:"打分日期：",//打分日期
	};
	$scope.projectInfo.totalScoreVal=100;
	
	/**
	 * 页面加载初始化
	 * **/
	$scope.initQuestion=function()
	{
		$scope.initTemplatePath();
	}
	/**
	 *问题表格加载初始化
	 * **/
	$scope.initQuestion2=function()
	{
		initTableEvent();
		initRuleCommon("datetimepicker",'','','yyyy-MM-dd',2,"tableProjectInfo");
		initTableSubtotal();
	}
	/**
	 *初始化模板加载路径
	 * **/
    $scope.initTemplatePath=function()
    {
		var parm = parseQueryString();
		var projectName=parm.projectName;
		var templateName=getExamineTrialTemplatePath(projectName);
		//加载模板
		$scope.templateName=templateName;
    }
    /**
	 *项目基本信息加载初始
	 * **/
    $scope.initQuestionTable=function()
	{
		initData();
		//initRuleCommon("datetimepicker","","",format,row,"tableProjectInfo");
	}
	
    /**
	 *提交报告
	 * **/
   $scope.submitQuestion=function()
   {
		var scope=$scope;
    	var scoringTime=getInputDatePickerVal();
    		scope.reportInfo.scoringTime=scoringTime;
    	var reportInfo={
    			reportInfo:scope.reportInfo,
    			questionList:scope.questionList,
    			
    	};
    	var result = {};
    	result.result = 0;
		var obj={
				"request.taskId":scope.mainParm.taskId,
				"request.projectId":scope.mainParm.projectId,  //项目ID
				"request.serviceType":"JZDC",  //业务类型
				"request.result":JSON.stringify(result),  //业务类型
				"request.data1":JSON.stringify(scope.reportInfo),  //尽职调查--填写评价
				"request.data2":JSON.stringify(scope.questionList), //尽职调查--填写得分
				"request.data3":JSON.stringify(scope.projectInfo)  //尽职调查--项目基本信息
				//"request.data1":scope.projectInfoObj //数据1
		};
		/*submitReportInfoByServer(obj,function(data){
			if(data.result==0){
				window.history.go(-1);
			}
		})*/
    	console.log(obj);
    	//提交
   }
   //获取时间value
  function getInputDatePickerVal(){
      var value =$("#scoringTime").find("input").val();
 	 return value;
  }  
    
    //关闭--尽职调查
	var cancelMyagencyTaskWin = function(){
		var parm=parseQueryString();
		var from=parm.from;
		location.replace(from);
	}
	
	$scope.cancelMyapplySeeWin=function(){
		var currentUserId= localStorage.getItem("userInfo");
			currentUserId=JSON.parse(currentUserId);
			currentUserId=currentUserId.uid;
		if(currentUserId != 4){  //Id:4为项目经理发布   Id:5为项目管理员  Id:6为决策委员会   Id:为部门经理审批
			cancelMyAgencyTaskWin();
		}else{
			cancelMyagencyTaskWin();
		}
	}
    
});

/**
 *初始户加载问题列表JSON
 * **/
function initData()
{   
	var scope=getAngularScope("initQuestionData");
	getQuestionListByServer("",setQuestionUi);
	//从后台取基本的信息
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

/**
 *将项目信息渲染scope
 * **/
function setQuestionUi(data)
{
	var scope=getAngularScope("initQuestionData");
	scope.questionList=data.data.pages;
	scope.$applyAsync(scope.questionList);
	//模拟从后台取出的项目基本信息的数据
	/*scope.projectInfo={
			projectId:"123456",
			budget:"100.00",
			projectName:"尽职调查报告",
			projectManagerName:"李国强",
			leaderDepartment:"运营支撑产品平台部",
		};
	*/
	//开始进行尽职调查时从后台取出基本信息
	
	

	
	
}
/**
 *问题表格校验
 *参数：obj=elements 对象
 *将表格的数据渲染到scope questionList 对象
 * **/
function checkThisVal(obj)
{
	var name=obj.getAttribute("data-columnName");
	var value=obj.innerText;
	var groupId=obj.parentNode.getAttribute("data-id");
	var scope=getAngularScope("initQuestionData");
	scope.questionList[groupId][name]=value;
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
		if(id!="questionTemplateC" ||!id)
		{
			scope.reportInfo[name]=value;
		}else{
			var groupId=_this.parent().attr("id");
			scope.questionList[groupId][name]=value;
		}
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


/*
 * 计算总分并得出结果
 * */
function initTableSubtotal()
{
	 //blur check	 
	 $("table").on("blur","td[data-type='number']",function(){
			var _this=$(this);
			var oneValue=_this.text();
			var allValue=[];
			var totalScore=0;
			$("td[data-type='number']").each(function(){
				allValue.push($(this).text());
			})
			for(var i =0;i<allValue.length-1;i++){
				allValue[i]=parseInt(allValue[i]);
				if(isNaN(allValue[i])){
					allValue[i]=0;
				}
				totalScore=allValue[i]+totalScore;
			}
			$("#totalScore").text(totalScore);
	 }); 
}




/**
 * 请求问题列表
 * 参数：obj ,callBack
 * requestInfo=
	{
		"request.projectId ":"项目id"
	}
	return callBack(data);
 * **/
function getQuestionListByServer(type,callBack)
{
	$.getJSON("/process/js/process_global/examine/examineTrialTemplate/examineTrial_A.json",function(data){
		callBack(data);
	})
}
/**
 * 查看报告的回调函数
 * 参数：obj ,callBack
 * **/
function getReportInfo(data)
{
	//chuli
}




















