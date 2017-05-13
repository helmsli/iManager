App.controller('initQuestionData', function($scope) {
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
	var userInfo= localStorage.getItem("userInfo");
	userId=JSON.parse(userInfo);
	$scope.userId=userId.uid;
	$scope.templateName="";
	$scope.questionList=[];//问题列表
	$scope.projectInfo={};//项目基础信息
	
	$scope.reportInfo={
			specialExplain:"",//特殊情况说明
			innovation:"",//创新点和亮点
			changeConditions:"",//项目变更情况
			riskDescription:"",//风险说明
			summary:"",//经验总结、工作反思及遇到的困难或问题
			improvementPlan:"",//改进计划
	};
	$scope.projectLable={
			specialExplain:"特殊情况说明：",
			innovation:"创新点和亮点：",
			changeConditions:"项目变更情况：（如无变更填“无”）",
			riskDescription:"风险说明：",
			summary:"经验总结、工作反思及遇到的困难或问题：",
			improvementPlan:"改进计划：",
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
		var templateName=getProjectMonthSubmitTemplatePath(projectName);
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
	    var scopeParent=getAngularScope("initQuestionData");
	    //过滤没有内容的行，不发给后台了
	    var questionList=scopeParent.questionList;
	    var questionListNew=[];
	    for( var i in questionList){
	    	var lineString="";
	    	var question=questionList[i];
	    	lineString=lineString+question.codeTimes+question.content+question.produce+question.reviewTimes+question.time+question.title;
	    	if(lineString!=""&&lineString!=null){
	    		questionListNew.push(questionList[i]);
	    	}
	    }
	    console.log("新的");
	    console.log(questionListNew);
	    var result = {};
    	result.result = 0;
		var obj={
				"request.taskId":scopeParent.taskId,
				"request.serviceType":"GZJZ",  //业务类型(项目经理月度提交-工作进展表)
				"request.data1": JSON.stringify(questionListNew), //尽职调查--填写得分//尽职调查--填写评价
				"request.data2":JSON.stringify(scopeParent.reportInfo), 
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

/**
 *初始户加载问题列表JSON
 * **/
function initData()
{   
	var scopeParent=getAngularScope("initQuestionData");
	getQuestionListByServer("",setQuestionUi);
	//从后台取基本的信息
	obj={
			"request.projectId":scopeParent.mainParm.projectId
	};
	getBaseInfoByServer(obj,function(data){
		if(data.result==0){
			//console.log(data);
			var project=data.responseInfo.project;
			var cost=JSON.parse(project.projectCosts);
			var projectManager=data.responseInfo.projectManager;
			scopeParent.projectInfo={
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
	var scopeParent=getAngularScope("initQuestionData");

	scopeParent.$apply(function () {
	     scopeParent.questionList=data.data.pages;
	      });

	
	
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
	var scopeParent=getAngularScope("initQuestionData");
	scopeParent.questionList[groupId][name]=value;
	//console.log("*****************");
	//console.log(scopeParent.questionList);
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
    } else if(c==9){
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

/**
 *静态表格事件BLUR绑定并监听输入内容
 *B
 * **/
function initTableEvent()
{
	$("table").on("blur","td[contenteditable='true']",function(){
		
		var _this=$(this);
		var isNeedblur = _this.attr("needblur");
		//console.log(":%^%%%%%%%%%%%%%%%%%%:" + _this.attr("needblur") );
		//console.log($(this).parent());
		var id=$(this).parent().attr("id");
		var name=_this.attr("data-columnName");
		var value=_this.text();
		var scopeParent=getAngularScope("initQuestionData");
		
		if(isNeedblur=="false")
		{
			
		}else{
			scopeParent.reportInfo[name]=value;
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
	$("table").on("keydown","td[data-columnname='reviewTimes']",function(e){
		 return digitInputInt($(this), e);  
		 return;
	 });
	$("table").on("keydown","td[data-columnname='codeTimes']",function(e){
		 return digitInputInt($(this), e);  
		 return;
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
	$.getJSON("/process/js/process_global/examine/projectMonthProgressTemplate/projectMonthProgress_A.json",function(data){
		callBack(data);
	})
}

//评审次数代码走读次数等不可以写非数字
//处理只允许输入数字(不带小数点)
function digitInputInt(el, e) {  
    var ee = e || window.event; // FF、Chrome IE下获取事件对象  
    var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    var val = el.text();  
    if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
        	return false;
            prevent(e);
    } else if(c==9){
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











