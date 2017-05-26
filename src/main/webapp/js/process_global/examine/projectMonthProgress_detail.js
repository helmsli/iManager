App.controller('initQuestionData', function($scope) {
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	$scope.dataId=parm.dataId;
	//配合projectMonthProgress_detail.html页面
	$scope.projectLable={
			projectName:"项目名称",
			companyName :"机构名称",
			traningAmount:"培训次数",
			NoTraningAmount:"非培训的服务次数",
			chuDianZongjie:"触点总结",
			zhiJieShouYiRenShu:"直接受益人数",
			zhiJieShouYiRenCi:"直接受益人次",
			jianJieShouYiRenShu:"间接受益人数",
			jianJieShouYiRenCi:"间接受益人次",
			xiangMuBiangeng:"项目变更情况（如无变更填“无”）",
			meiTiBaoDaoCishu:"媒体报道次数",
			meiTiBaoDaoLianJie:"媒体报道链接",
			specialExplain:"社会效果:",
			gaiJinJiHua:"改进计划",
			xiaYueJiHua:"下月重大活动计划    （时间、内容）",
			benYueGongZuoJieshao:"本月主要工作内容介绍"
	};
	//页面加载初始化
	$scope.initQuestion=function()
	{
		$scope.initTemplatePath();
	}
	//初始化模板加载路径
    $scope.initTemplatePath=function()
    {
		var parm = parseQueryString();
		var projectName=parm.projectName;
		var templateName=getProjectMonthSubmitTemplatePath(projectName);
		$scope.templateName=templateName;
    }
    //项目基本信息加载初始
    $scope.initQuestionTable=function()
	{
    	initTableEvent();
		initData();
	}
    //关闭
    $scope.cancelCurrentPageWin=function(){
    	window.history.go(-1);
    }
    
});

/**
 *按照ID查询月报表
 * **/
function initData()
{   
	var scopeParent=getAngularScope("initQuestionData");
	obj={
		"request.dataId":scopeParent.dataId
	};
	getProjectMonthProgressByServer(obj,function(data){
		console.log(data);
		
		if(data.result==0){
			
			var monthlyReportFile ={};
			var monthlyReportAttatchment ={};
			var reportDetail={};
			try{
				 if(data.responseInfo.commonBiz.data1!="")
				{
					 monthlyReportFile =   JSON.parse(data.responseInfo.commonBiz.data1);
				}
				 if(data.responseInfo.commonBiz.data2!=""){
					 monthlyReportAttatchment=JSON.parse(data.responseInfo.commonBiz.data2);
				 }
				
			 	 if(data.responseInfo.commonBiz.data3!="")
			 	{
				  reportDetail =JSON.parse(data.responseInfo.commonBiz.data3);
				  reportDetail = reportDetail[0];
				  console.log(reportDetail);
				  setTimeout(function(){
					 		var scopeParent=getAngularScope("initQuestionData");
					 		/*
					 		 * [{"name":"北京春泽项目","month":"5","charge":"北京春泽","meetingNumber":"10次","otherManagement":"11次","majorIssues":"触点总结\n（本月好的经验/问题总结，项目运作小的或大的改进、提高等让人有所触动之处）","reviewTimes":"1","docOutputNumber":"2","codeReviewTimes":"3","versionOutputNumber":"4","change":"无项目变更","versionUpgrade":"5","updateProblemTracking":"http://www.sina.com.cn","improvementPlan":"无新的改进计划","nextMonthPlan":"下月重大活动计划和时间","progressList":[{"seq":"1","time":"2017-05-12","name":"序号1","content":"序号1内容1","output":"序号1产出"},{"seq":"2","time":"2017-06-12","name":"序号2","content":"序号2内容","output":"序号2产出"},{"seq":"3","time":"2017-07-12","name":"序号3","content":"序号3内容","output":"序号3产出"}]}]
					 		 */
					 		scopeParent.$apply(function () {
						 		scopeParent.reportInfo=reportDetail;
						 		
						 		
						 	 });
				 	     },100);
			 	}
			 	else
			 		{
			 		  alert("数据错误，请查询别的数据"); 
			 		}
					
			 	
				}
				 catch(err)
				 {
					 console.log(err);
				 }
			
			
			
		}
	})
}
function checkThisVal(obj)
{
	initTableEvent();
}
//变为不可编辑(有问题)
function initTableEvent()
{
	$("table td[contenteditable='true']").attr("contenteditable","false");
}	

//






