/*
 * main STATE
 * */
var mainStepControl={
		STEP_CREATE:"main_01",
		MIDDLE_STEP:"main_02",
		FINNEL_STEP:"main_03"
}
/**
 * state 
 * **/
var controllerStep=
{
		    "STEP_0": "step_000",//项目开始
		    "STEP_1": "step_001", //初期指定审批专家
		    "STEP_2": "step_002", //初期专家打分
		    "STEP_3": "step_003", //初期委员会审批
		    "STEP_4": "step_004", //初期项目经理审批
		    "STEP_5": "step_005", //项目经理修改审批材料
		    "STEP_6": "step_006", //项目经理修改审批材料
		    //项目中期
		    "STEP_7": "step_007", //提交评审材料
		    "STEP_8": "step_008", //指定审批专家
		    "STEP_9": "step_009", //项目经理提交报告
		    "STEP_10": "step_010",// 专家打分
		    "STEP_11": "step_011", //委员会审批
		    "STEP_12": "step_012", //项目经理审批
		    //终期
		    "STEP_13": "step_013", //提交评审材料
		    "STEP_14": "step_014", //指定审批专家
		    "STEP_15": "step_015", //项目经理提交报告
		    "STEP_16": "step_016", //专家打分
		    "STEP_17": "step_017", //委员会审批
		    "STEP_18": "step_018", //项目经理审批
		    "STEP_19": "step_019", //提交月报
		    "STEP_20": "step_020", //项目经理自评
		    "STEP_21": "step_021",//经理评价
		    "STEP_22": "step_022",//end
		    "END_STEP":"step_022", //结束
};

var returnStep={
		minStep:"03/04/10/11/17/18",
		middleStep:{
			parallel:{
				09:true,
				10:false
			},
		},
		finalStep:{
			parallel:{
				15:true,
				16:false
			},
		}
}

var TITLE={
			LEADER_AGREE:"同意",
			COMMI_AGREE:"同意",
}

/**
 * global parm
 * */
var mainParm={
		detailStep:"",
		modifyStep:"",
		mainStep:"",
		reportCurrentTaskId:"",
		parm:{
			projectId:"",
			seeState:""
		},
	   data:null
};

var mainCurrentTaskId="";

/***
 * 初始化
 * 1.初始化参数
 * 2.初始化数据
 * 3.调用主控
 * */

function initBaseProject()
{
	var newParm=initParm();
	initState();
	initDataBeforeModify(newParm);
}


function gotoState(state,taskId)
{
	if(controllerStep.STEP_7==state){
		leaderMiddleSubmit(state,taskId);
	}
	
	if(controllerStep.STEP_13==state){
		leaderFinalSubmit(state,taskId);
	}
	
}

function getNewTaskId(detailStep,projectId,callBack)
{
	var obj={
			"request.userId":4,
			"request.categoryId":1
	};
	
	creaAgencyTaskList(obj,function(data){
	     if(data.result == 0){
				var dataLists=data.responseInfo.taskList;
				var pageInfo=data.responseInfo.page;
				
				if(dataLists.length == 0){
					
				}else{
					for(var i in dataLists){
						var taskProjectId=dataLists[i].projectId;
						var taskId=dataLists[i].id;
						(function(taskProjectId,taskId){
							if(taskProjectId == projectId){
								if(detailStep ==controllerStep.STEP_7){
									
								}
								if(detailStep ==controllerStep.STEP_8){
									midReport(taskId,callBack);
								}else if(detailStep ==controllerStep.STEP_14){
									endReport(taskId,callBack);
								}
							}
						})(taskProjectId,taskId);
					}
				}
			}	  
	});
}


/**
 * 
 * 获取界面参数并写入mainParm
 * 1.获取detailStep;
 * 2.通过小步骤获取大步骤；
 * **/
function initParm()
{
	var myParm=getParm();//所有的参数
	var changeStata=myParm.changeStata;//获得变更的状态码
	var minStep ="STEP_22";
	if(parseInt(myParm.state)>0)
	{
		 minStep="STEP_"+parseInt(myParm.state);  	
	}
	
	var endStep=replaceStep(controllerStep.END_STEP);
	
	if(myParm.state=="end")
	{
		minStep="END_STEP";
	}
	
	mainParm.detailStep=controllerStep[minStep];//获得小步骤
	//mainParm.detailStep="020";
	var mainStep=getMainStep(mainParm.detailStep);//获得大的步骤
	mainParm.mainStep=mainStep;
	mainParm.modifyStep=myParm.modifyStep;
	mainParm.parm.projectId=myParm.projectId;//获得projectId
	mainParm.parm.taskId = myParm.taskId;
	mainParm.parm.changeStata = changeStata;
	mainParm.parm.seeState=myParm.seeState;
	
	return mainParm;
}

/**
 * 
 * state
 * **/
function initState()
{
	var scope=getAngularScope("initSeeData");
	//从后台获取的数据
	//初期的专家名字、打分、委员会评审结果、经理的评审结果
	scope.begainSetExpert="";
	scope.begainExpertScore="";
	scope.begainCommitee="";
	scope.begaindepartLeader="";
	
	//中期的专家名字、打分、委员会评审结果、经理的评审结果
	scope.middleSetExpert="";
	scope.middleExpertScore="";
	scope.middleCommitee="";
	scope.middledepartLeader="";
	//终期的专家名字、打分、委员会评审结果、经理的评审结果
	
	scope.lastSetExpert="";
	scope.lastExpertScore="";
	scope.lastCommitee="";
	scope.lastdepartLeader="";
	//项目经理自评
	scope.projectEvaluateContent="";
	//部门经理评价
	scope.deparEvaluateContent="";
	
	//部门经理审批的后台调用参数
	
	scope.managerSuggestion = {
			 suggestionFlag:TITLE.LEADER_AGREE,
		     suggestionDescription:"" 
			  };
	
	scope.suggestion = {
			 suggestionFlag:TITLE.LEADER_AGREE,
		     suggestionDescription:"" 
		  }
	//项目评审材料数据展示开始
	scope.projectMaterialEvaluationShow=false;
	//提交项目材料/报告
	scope.submitProjectMaterial=false;
	//项目经理自评展示的控制
	scope.projectSelfEvaluationResult=false;
	//部门经理评价展示的控制
	scope.departLeaderEvaluationResult=false;
	//提交月度报告展示的控制
	scope.myApplySeeMonthlyReportResult=false;
	//提交项目经理自评
	scope.projectSelfEvaluation=false;
	//提交部门经理自评
	scope.departLeaderEvaluation=false;
	//提交项目月度报告
	scope.submitProjectMonthlyReport=false;
	//提交中期评审材料
	scope.submitMidtermReviewMaterials=false;
	//提交中期评审报告
	scope.submitMidtermReviewReport=false;
	//提交终期评审材料
	scope.submitLastReviewMaterials=false;
	//提交终期评审报告
	scope.submitLastReviewReport=false;
	//尽职调查查看是否显示
	scope.questionIsShow=false;
	//查看/办理页面提交按钮的状态
	scope.submitButton=true;
	
	//初、中、终期大模块是否要展示
	scope.begainProjectShow=false; 
	scope.middleProjectShow=false;
	scope.LastProjectShow=false;
	
/*	scope.isApprovalStateShow=false;//评审委员会是否展示模块
	scope.isleaderShow=false;//经理是否展示模块
*/	
	//办理时指定评审专家和打分的显示
	scope.SetExpertFlag=false;
	scope.ExpertScoreFlag=false;
	scope.setCommiteeFlag=false;
	scope.setDepartFlag=false;
	
	
	//初期的结果展示的各个小模块是否要显示
	scope.begainSetExpertFlag=false;
	scope.begainExpertScoreFlag=false;
	scope.begainCommiteeFlag=false;
	scope.begaindepartLeaderFlag=false;
	
	//中期的结果展示的各个小模块是否要显示
	scope.middleSetExpertFlag=false;
	scope.middleExpertScoreFlag=false;
	scope.middleCommiteeFlag=false;
	scope.middledepartLeaderFlag=false;
	//终期的结果展示的各个小模块是否要显示
	scope.lastSetExpertFlag=false;
	scope.lastExpertScoreFlag=false;
	scope.lastCommiteeFlag=false;
	scope.lastdepartLeaderFlag=false;
	
	//办理的页面显示逻辑
	if(mainParm.parm.seeState==1)
	{
		scope.projectView=false;
	}
	else
	{
		scope.projectView=true;
	}
}


/***
 * 初始化数据
 * 功能：获取项目数据并写入全局变量 mainParm
 * */
function initDataBeforeModify(options)
{	
	
	  
		var obj={
				"request.projectId":mainParm.parm.projectId
			};
		//从后台取到数据，显示在前台as
		queryItem(obj,function(data){
			//data=testData;
				if(data.result == 0){
				//任务、项目信息
					var scope=getAngularScope("initSeeData");
					var project=data.responseInfo.projectDetailInfo.project;
					var projectExtInfo=JSON.parse(project.projectExtInfo);
					try{
						project.CycleType=JSON.parse(project.cycleType)[0].value;
						project.subcategory=JSON.parse(project.subcategory)[0].value;
					}catch(e){
						log(e+"initData:"+e.messageInfo);
					}
					
					scope.project=project;
					var projectTaskDetail=scope.project.projectTaskDetail;
					scope.projectTaskDetail=JSON.parse(projectTaskDetail)
					var mainCurrentState=JSON.parse(project.mainCurrentState);
					scope.project.stateName=mainCurrentState.stateName;
					scope.projectExtInfo=projectExtInfo[0];
					scope.Taskplan=JSON.parse(projectExtInfo[0].taskplan);
					console.log(scope.projectTaskDetail);
					scope.ProjectCosts=JSON.parse(project.projectCosts);
					scope.ProjectTaskDetail=JSON.parse(project.projectTaskDetail);	
					//阶段评审信息
					var newData=data.responseInfo.projectDetailInfo;
					var beginStage=newData.beginStage;
					var middleStage=newData.middleStage;
					var lastStage=newData.lastStage;
					mainParm.data={
							beginData:beginStage,
							middleData:middleStage,
							finalData:lastStage,
							detailData:project
							
							
					};
					options.data=mainParm.data;
				};
				var taskId=options.data.detailData.projectTaskId;
				/*alert(JSON.stringify());*/
				/*var taskId=options.data.detailData.mainCurrentState;*/
				var mainCurrentState=options.data.detailData.mainCurrentState;
				var reportCurrentState=options.data.detailData.reportCurrentState;
				
				mainCurrentTaskId=JSON.parse(mainCurrentState).taskId;
				if(JSON.parse(reportCurrentState)!=undefined&&JSON.parse(reportCurrentState!=null)){
					var reportCurrentTaskId=JSON.parse(reportCurrentState).taskId;
					mainParm.reportCurrentTaskId=reportCurrentTaskId;
					//alert(reportCurrentTaskId);
				}
				gotoState(options.detailStep,taskId);
				beforeExpertScore();	//初始化的时候执行评分的预加载
				mainControl(options.mainStep,options.detailStep,options.data);
		})
		
		
}


/**
 * 获取mainStep;
 * @parm detailStep;
 * @return mainStep;
 * **/
function getMainStep(detailStep)
{     
		var mainStep="";
		switch(detailStep)
		{
			case controllerStep.STEP_1:
			case controllerStep.STEP_2:
			case controllerStep.STEP_3:
			case controllerStep.STEP_4:
			case controllerStep.STEP_5:
				mainStep=mainStepControl.STEP_CREATE;
			break;
			case controllerStep.STEP_6:
			case controllerStep.STEP_7:
			case controllerStep.STEP_8:
			case controllerStep.STEP_9:
			case controllerStep.STEP_10:
			case controllerStep.STEP_11:
			case controllerStep.STEP_12:
				mainStep=mainStepControl.MIDDLE_STEP;
			break;
			case controllerStep.STEP_6:
			case controllerStep.STEP_13:
			case controllerStep.STEP_14:
			case controllerStep.STEP_15:
			case controllerStep.STEP_16:
			case controllerStep.STEP_17:
			case controllerStep.STEP_18:
			case controllerStep.STEP_19:
			case controllerStep.STEP_20:
			case controllerStep.STEP_21:
			case controllerStep.STEP_22:
				mainStep=mainStepControl.FINNEL_STEP;
			break;
		}
		return mainStep;
}



/**
 * 获取小步骤state的值;
 * @return number;
 * **/
function replaceStep(str)
{
	var newStr=str.replace("step_","");
	return newStr;
}

/**
 * 获取界面参数;
 * @return object;
 * **/
function getParm()
{
	var arg=parseQueryString();
	return arg;
}

/**
 * 主控制函数
 * @parm mainStep :大步骤 detailStep：详细步骤 data：项目数据
 * 功能：大步骤控制器
 * @description 初期 createFlowControl 2.中期 midleFlowControl 3:末期 finalFlowControl
 * 界面要显示的主元素控制 mainControlDom；
 */
function mainControl(mainStep,detailStep,data)
{  
	switch(mainStep)
	{
	   case mainStepControl.STEP_CREATE:
		   createFlowControl(detailStep,data);
		   break;
	   case mainStepControl.MIDDLE_STEP:
		   midleFlowControl(detailStep,data);
		   break;
	   case mainStepControl.FINNEL_STEP:
		   finalFlowControl(detailStep,data);
		   break;
	}
	
	
	mainControlDom(mainStep,data);

}


/***
 * 
 * 项目初期控制器
 * @parm detailStep：详细步骤 parameters：项目数据
 * 功能：控制项目初期要显示的界面元素和数据
 * */
function createFlowControl(detailStep,data)
{  
	var projectAskStateShow=true;
	SubmittDomElementsForStep(detailStep,data);
	switch(detailStep)
	{
	 case controllerStep.STEP_0://
		 showPageDom(controllerStep.STEP_0,data);	  
		   break;
	   case controllerStep.STEP_1://
		   showPageDom(controllerStep.STEP_1,data);  
		   break;
	    case controllerStep.STEP_2://
	    	  showPageDom(controllerStep.STEP_2,data);
		   break;
		case controllerStep.STEP_3://
			showPageDom(controllerStep.STEP_3,data); 
		   break;
		case controllerStep.STEP_4://
			showPageDom(controllerStep.STEP_4,data);
		   break;
		case controllerStep.STEP_5://
			showPageDom(controllerStep.STEP_5,data);
			   break;	
	}
	var scope=getAngularScope("initSeeData");
	scope.projectAskStateShow=projectAskStateShow;

}



/***
 * 
 * 项目中期控制器
 * @parm detailStep：详细步骤 parameters：项目数据
 * 功能：控制项目中期要显示的界面元素和数据
 * */

function midleFlowControl(detailStep,data)
{ 
	var scope=getAngularScope("initSeeData");
	var isApprovalStateShow=false;//决策委员会；
	var isleaderShow=false;//leader 
	SubmittDomElementsForStep(detailStep,data);
	switch(detailStep)
	{

	 case controllerStep.STEP_7:010
		 showPageDom(controllerStep.STEP_7,data);
		  
		   break;
	   case controllerStep.STEP_8:
		   showPageDom(controllerStep.STEP_8,data);
		  
		   break;
	    case controllerStep.STEP_9:
	    	  showPageDom(controllerStep.STEP_9,data);
	  
		   break;
		case controllerStep.STEP_10:
			showPageDom(controllerStep.STEP_10,data);
	    
		   break;
		case controllerStep.STEP_11:
			showPageDom(controllerStep.STEP_11,data);
		   break;
		case controllerStep.STEP_12:
			showPageDom(controllerStep.STEP_12,data);
			   break;
	}
    
}


/***
 * 
 * 项目末期控制器
 * @parm detailStep：详细步骤 parameters：项目数据
 * 功能：控制项目末期要显示的界面元素和数据
 * */


function finalFlowControl(cuurentStep,data)
{
	//console.log("finalFlowControl:" + cuurentStep + " :" +controllerStep[cuurentStep]);
	//控制END
	if(cuurentStep==controllerStep.END_STEP)
	{
		console.log("finalFlowControl1232");
		//TODO
		//状态END_STEP现阶段不知道从哪个小步骤过来，
		showPageDom(controllerStep.END_STEP,data);
		return;
	}
	
	SubmittDomElementsForStep(cuurentStep,data);
	
	for(var step in controllerStep)
	{
		if(controllerStep[step]==cuurentStep)
		{
			showPageDom(controllerStep[step],data);
			break;
		}
	}
}

/**
 *  * 界面要显示的主元素控制 mainControlDom；
 *	@parm mainStep:大步骤,detailData：项目数据
 * @description 评审结果显示控制 
 * **/
function mainControlDom(mainStep,detailData)
{
	

	var projectState=mainStep;
	switch(mainStep)
	{
	   case mainStepControl.STEP_CREATE:
		   showDomId="middlePresentation";
		   break;
	   case mainStepControl.MIDDLE_STEP:
		   showDomId="middlePresentation";
		   break;
	   case mainStepControl.FINNEL_STEP:
		   showDomId="lastPresentation";
		   break;
	}
	//要显示的基础数据函数
	var scope=getAngularScope("initSeeData");
	scope.projectState=projectState;
}
/**
 *  小步骤要显示的元素；
 *	@parm detailSetp:小步骤,data：项目数据
 * @description 控制UI层显示的数据与元素 
 * **/
function showPageDom(currentStep,data)
{
	//alert(22222222222);
	for(var step in controllerStep)
	{
		if(controllerStep[step]==currentStep)
		{
			showDataController(currentStep,data);
			break;
		}
	}
}



/**
 * 元素叠加显示控制；
 *	@parm detailSetp:小步骤,data：项目数据
 * @description 控制UI层显示的数据与元素 
 * **/

function showDataController(detailSetp,data)
{ 
	console.log(4444444);//不加alert数据加载不进来，有待处理
	setCommiModeControl(detailSetp);
	for(var key in controllerStep)
	{
		var Step=controllerStep[key];
		Step=replaceStep(Step);
		detailSetp=replaceStep(detailSetp);
		(function(minStep,currentStep,scopeData){
			if(currentStep<=minStep)
			{
				showDomElementsForStep("step_"+currentStep,scopeData);
			}
		})(detailSetp,Step,data)
	}
	

	onlyViewDetail();
	
	
}
//只能查看页面详细信息，不能办理
function onlyViewDetail(){
	var scope=getAngularScope("initSeeData");
	if(mainParm.parm.seeState==1){
		scope.projectSelfEvaluation=false;
		scope.submitProjectMaterial=false;
		scope.projectView=false;
		scope.submitButton=false;
		
	}
}
//提交按钮需要调用的函数
function submitt(step,data){
	
	
}
/**
 * 每一步要显示的元素与数据
 *	@parm step:小步骤,data：项目数据
 * @description 控制UI层显示的数据与元素 
 * **/
function showDomElementsForStep(step,data)
{	
	var scope=getAngularScope("initSeeData");
	var expertReview=null;
	
		switch(step)
		{		
			case controllerStep.STEP_1:
				//初期的结果展示的各个小模块是否要显示
				
			break; 
		   //这个是初期的数据显示和赋值
			case controllerStep.STEP_2:
				scope.begainProjectShow=true; 
				scope.begainSetExpertFlag=true;
				expertReview=data.beginData.expertReview;
				scope.begainSetExpert=getExpertList(expertReview);
			break; 
			
			case controllerStep.STEP_3:
				scope.begainExpertScoreFlag=true;
			  
				expertReview=data.beginData.expertReview;
				scope.begainSetExpert=getExpertList(expertReview);
				scope.begainExpertScores=getExpertScore(expertReview);
				scope.Score=scope.begainExpertScore.postName;
				 
			break ;
			
			case controllerStep.STEP_4:
				scope.begainCommiteeFlag=true;
			    scope.begainCommitee=data.beginData.committeeApproval[0];
			    
			break ;
			
			case controllerStep.STEP_5:
				scope.begaindepartLeaderFlag=true;
			    scope.begaindepartLeader=data.beginData.departleaderApproval[0];
			break; 
			
			case controllerStep.STEP_6:
				scope.myApplySeeMonthlyReport=true;
			break; 
			
			case controllerStep.STEP_7:
				//提交项目材料报告
				scope.submitProjectMaterial=true;
				scope.begaindepartLeaderFlag=true;
				//提交中期评审材料
				scope.submitMidtermReviewMaterials=true;
			    scope.begaindepartLeader=data.beginData.departleaderApproval[0];
			    //
			    scope.projectMaterialEvaluationShow=true;
			    //尽职调查是不是可以查看
			    scope.questionIsShow=true;
			break; 
		    //提交完评审材料之后，提交项目材料报告不显示
		   case controllerStep.STEP_8:
			//提交项目材料报告
			scope.submitProjectMaterial=false;
			//提交中期评审材料
			scope.submitMidtermReviewMaterials=false;
			//项目评审报告不显示
			 scope.projectMaterialEvaluationShow=false;
				break;
				
	        //这是中期的数据显示和赋值
			case controllerStep.STEP_9:
				scope.middleProjectShow=true;
				scope.middleSetExpertFlag=true;
			    //不显示项目审批操作
				scope.projectView=false;
				expertReview=data.middleData.expertReview;
				scope.middleSetExpert=getExpertList(expertReview);
				//提交项目材料报告
				scope.submitProjectMaterial=true;
				//提交中期评审报告
				scope.submitMidtermReviewReport=true;
				//项目评审报告不显示
				 scope.projectMaterialEvaluationShow=true;
			break;
			case controllerStep.STEP_10:
				 //显示项目审批操作
				scope.projectView=true;
				//提交项目材料报告
				scope.submitProjectMaterial=false;
				//提交中期评审报告
				scope.submitMidtermReviewReport=false;
				//项目评审报告不显示
				 scope.projectMaterialEvaluationShow=false;
				  break;
			
			case controllerStep.STEP_11:
				scope.middleExpertScoreFlag=true;
			    expertReview=data.middleData.expertReview;
			    scope.middleExpertScore=getExpertScore(expertReview);
				scope.middleSetExpert=getExpertList(expertReview);
				
				   var expertTemporary=scope.middleExpertScore;
					for(var i in expertTemporary){
						expertTemporary[i].postName= scope.middleSetExpert[i].postName;
					}
					 scope.middleExpertScore=expertTemporary;
			break;
			
			case controllerStep.STEP_12:
				scope.middleCommiteeFlag=true;
			    scope.middleCommitee=data.middleData.committeeApproval[0];
			break ;
			
			case controllerStep.STEP_13:
				//提交项目材料报告
				scope.submitProjectMaterial=true;
				//提交终期评审材料
				scope.submitLastReviewMaterials=true;
				//项目评审报告显示
				 scope.projectMaterialEvaluationShow=true;
				scope.middledepartLeaderFlag=true;
			   scope.middledepartLeader=data.middleData.departleaderApproval[0];
			break ;
			
			case controllerStep.STEP_14:
				//提交项目材料报告
				scope.submitProjectMaterial=false;
				//提交终期评审材料
				scope.submitLastReviewMaterials=false;
				//项目评审报告显示
				 scope.projectMaterialEvaluationShow=false;
				break;
			
	        //这是终期的数据显示和赋值
			case controllerStep.STEP_15:
				//提交项目材料报告
				scope.submitProjectMaterial=true;
				//提交终期评审报告
				scope.submitLastReviewReport=true;
				//项目评审报告显示
				scope.projectMaterialEvaluationShow=true;
				scope.LastProjectShow=true;
				scope.lastSetExpertFlag=true;
				expertReview=data.finalData.expertReview;
				scope.lastSetExpert=getExpertList(expertReview);
			break;
			case controllerStep.STEP_16:
				//提交项目材料报告
				scope.submitProjectMaterial=false;
				//提交终期评审报告
				scope.submitLastReviewReport=false;
				//项目评审报告显示
				scope.projectMaterialEvaluationShow=false;
				break;
			case controllerStep.STEP_17:
				scope.lastExpertScoreFlag=true;
		        expertReview=data.finalData.expertReview;
		        scope.lastExpertScore=  getExpertScore(expertReview);
				scope.lastSetExpert=getExpertList(expertReview);
				   
				   var expertTemporary=scope.lastExpertScore;
					for(var i in expertTemporary){
						expertTemporary[i].postName= scope.lastSetExpert[i].postName;
					}
					 scope.lastExpertScore=expertTemporary;
			break ;
			
			case controllerStep.STEP_18:
				scope.lastCommiteeFlag=true;
			    scope.lastCommitee=data.middleData.committeeApproval[0];
			break;
			
			case controllerStep.STEP_19:
				scope.lastdepartLeaderFlag=true;
				//提交项目材料报告
				scope.submitProjectMaterial=true;
				//提交月度报告
				scope.submitProjectMonthlyReport=true;
			    scope.lastdepartLeader=data.middleData.departleaderApproval[0];
			break ;
			
			case controllerStep.STEP_20:
				scope.submitProjectMaterial=false;
				scope.projectSelfEvaluation=true;
				scope.submitProjectMonthlyReport=false;
				scope.submitButton=true;
			break ;
			
			case controllerStep.STEP_21:
				scope.projectSelfEvaluation=false;
				scope.departLeaderEvaluation=true;
				scope.projectSelfEvaluationResult=true;
			break ;
			
			default:
			//scope.departLeaderEvaluationResult=true;
			 break;
		}

		//控制是否显示办理页面，如果需要文件上传暂时不需要办理页面
		if(mainParm.parm.seeState==1)
		{
			scope.projectView=false;
			scope.departLeaderEvaluation = false;
		}
		else
		{
			switch(step)
			{
				case controllerStep.STEP_9:
				case controllerStep.STEP_7:
				case controllerStep.STEP_13:
				case controllerStep.STEP_15:
				case controllerStep.STEP_19:
				case controllerStep.STEP_21:
				
				{
					 //不显示项目审批操作
					scope.projectView=false;
					break;
				}
				case controllerStep.STEP_22:
				case controllerStep.END_STEP:
				{
					 //不显示项目审批操作
					scope.projectView=false;
					scope.departLeaderEvaluation = false;
					break;
				}
				default:
				{
					 //不显示项目审批操作
					scope.projectView=true;
					break;	
				}
					
			}	
			
		}
		console.log("^^^^^^^^^:" + step + " scope.departLeaderEvaluation :" + scope.departLeaderEvaluation);
}

function setCommiModeControl(currentStep)
{
	var scope=getAngularScope("initSeeData");
	if(currentStep==controllerStep.STEP_3||currentStep==controllerStep.STEP_11||currentStep==controllerStep.STEP_17)
	{
		scope.setCommiteeFlag=true;
	}
	if(currentStep==controllerStep.STEP_4||currentStep==controllerStep.STEP_12||currentStep==controllerStep.STEP_18)
	{
		scope.setDepartFlag=true;
	}
	log(scope.setCommiteeFlag);
}


/**
 * 每一步提交按钮提交的数据函数
 *	@parm step:小步骤
 * @description 控制UI层显示的数据与元素 
 * **/

function SubmittDomElementsForStep(step,data)
{
	var scope=getAngularScope("initSeeData");
	scope.SetExpertFlag=false;
	scope.ExpertScoreFlag=false;
	var expertList=null;
		switch(step)
		{		
			//提交按钮执行初期的指定评审专家
			case controllerStep.STEP_1:
				 scope.SetExpertFlag=true;
			break; 
			 //提交按钮执行初期的指定评审打分
			case controllerStep.STEP_2:
				
			    scope.SetExpertFlag=false;
			    scope.ExpertScoreFlag=true;
			    expertList= data.beginData.expertReview;
			    expertList=getExpertList(expertList,step);
			break; 
			     //提交按钮执行初期的委员会打分
				//提交按钮执行初期的经理打分
				//提交按钮执行中期的指定评审
				case controllerStep.STEP_8:
					scope.SetExpertFlag=true;
				break; 
				
				//提交按钮执行中期的评审专家大打分
				case controllerStep.STEP_10:
					scope.SetExpertFlag=false;
			        scope.ExpertScoreFlag=true;
			        expertList= data.middleData.expertReview;
			        expertList=getExpertList(expertList,step);
				break;
				
				//STEP_11提交按钮执行中期的决策委员会评价
				//STEP_12提交按钮执行中期的经理评价
				
				//提交按钮执行终期的指定专家
				case controllerStep.STEP_14:
				    scope.SetExpertFlag=true;
				break; 
				//STEP_16终期的专家打分
				case controllerStep.STEP_16:
				    scope.SetExpertFlag=false;
		            scope.ExpertScoreFlag=true;
		            expertList= data.finalData.expertReview;
		            expertList=getExpertList(expertList,step);
				break;
			 //STEP_17提交按钮执行终期的委员会评价
			 //STEP_18提交按钮执行终期的经理评价
		
	}

	expertList=setExpertList(expertList);
	scope.expertList=expertList;

}


function setExpertList(data)
{
	var newData=[];
	if(data!=null)
	{
			for(var i=0;i<data.length; i++)
			{
				newData[i]=data[i];
				newData[i].expertScore=5;
				newData[i].expertScoreList=[1,2,3,4,5];
			}
	}
	return newData;
}


function getExpertList(data,step)
{ 
	try{
		if(data.length>0)
		{
			return JSON.parse(data[0].expertList);
		}else{
			return null;
		}
	}catch(e)
	{
		log("getExpertList"+e+"errorInfo"+e.messageInfo+"step:"+step+"comeFrom:SubmittDomElementsForStep");
	}
}

function getExpertScore(data,step)
{ 
	try{
		if(data.length>0)
		{
			return JSON.parse(data[0].expertScore);
		}else{
			return null;
		}
	}catch(e)
	{
		log("getExpertScore"+e+"errorInfo"+e.messageInfo+"step:"+step+"comeFrom:SubmittDomElementsForStep");
	}
}


/**
 * submit提交函数入口控制
 * @parm step ，data;
 * 处理各个小步骤提交
 * **/


function subMitFnCall(step,data)
{
	var taskId=mainParm.parm.taskId;
	switch(step)
	{		
	//STEP_1 提交按钮执行初期的指定评审专家
	case controllerStep.STEP_1:
		initPeerReviewedData(step,data,taskId);
		
	break; 
	
	 //STEP_2 提交按钮执行初期的指定评审打分
		case controllerStep.STEP_2:
			inputExpertScore(step,data.beginData,taskId);
		break; 
		
     //STEP_3 提交按钮执行初期的委员会打分
		case controllerStep.STEP_3:
		 committeeSuggestionDate(step,data,taskId)
		break ;
		
		 //提交按钮执行初期的经理打分
		case controllerStep.STEP_4:
			managerSuggestionDate(step,data,taskId);
		break ;
		
		 //经理提交中期评审材料
		case controllerStep.STEP_9:
		case controllerStep.STEP_7:
		case controllerStep.STEP_13:
		case controllerStep.STEP_15:
		case controllerStep.STEP_19:	
			//leaderMiddleSubmit(step,taskId);
			var scope=getAngularScope("initSeeData");
			var projectAnnexs = scope.fileUpload[mainParm.detailStep];
			var taskId = mainParm.parm.taskId;
			if(!taskId)
			{
			   taskId =  mainCurrentTaskId;
			}
			 var obj={
	        	"request.projectAnnexs":JSON.stringify(projectAnnexs),
	        	"request.taskId":taskId,
	        }
			 submitMaterial(obj,function(data){
				 window.history.go(-1);
		        });
		break ;
		//STEP_8 提交按钮执行中期的指定评审
		case controllerStep.STEP_8:
			initPeerReviewedData(step,data,taskId);
		break; 
		
		//STEP_10 提交按钮执行中期的评审专家大打分
		case controllerStep.STEP_10:
	        inputExpertScore(step,data.middleData,taskId);
		break;
		
		//STEP_11 提交按钮执行中期的决策委员会评价
		case controllerStep.STEP_11:
			committeeSuggestionDate(step,data,taskId);
		break; 
		
		  //STEP_12 提交按钮执行中期的经理评价
		case controllerStep.STEP_12:
			managerSuggestionDate(step,data,taskId);
		break ;
		
		//经理提交终期评审材料
		case controllerStep.STEP_13:
			//leaderFinalSubmit(step,taskId);
		break ;
		
		//STEP_14 提交按钮执行终期的指定专家
		case controllerStep.STEP_14:
			initPeerReviewedData(step,data,taskId);
		break; 
		//STEP_16 终期的专家打分
		case controllerStep.STEP_16:
			inputExpertScore(step,data.finalData,taskId)
		break;
		
		 //STEP_17 提交按钮执行终期的委员会评价
		case controllerStep.STEP_17:
		  committeeSuggestionDate(step,data,taskId);
		break ;
		
		 //STEP_18 提交按钮执行终期的经理评价
		case controllerStep.STEP_18:
			managerSuggestionDate(step,data,taskId);
		break ;
		
		 //STEP_20 提交按钮执行终期的项目经理自评
		case controllerStep.STEP_20:
			projectManagerEvaluate(step,data,taskId);
		break ;
		
		 //STEP_21 提交按钮执行终期的部门经理评价
		case controllerStep.STEP_21:
			departManagerEvaluate(step,data,taskId);
		break ;
		
	}
}
//指定评审专家的后台调用
function initPeerReviewedData (step,data,taskId){
	//保存指定的专家
	try{
		//alert(taskId);
	var tableData=JSON.stringify(tableSave('reviewExpertTable'));
	var obj={	   
    		"request.taskId":taskId,	
    		"request.expertList":tableData
	};
		peerReviewedData(obj,function(data){
			if(data.result == 0){
				if(mainStepControl.MIDDLE_STEP==mainParm.mainStep||mainParm.mainStep==mainStepControl.FINNEL_STEP){
					window.history.go(-1);//getNewTaskId回调函数找不到，不知道什么意思
				/*	getNewTaskId(step,mainParm.parm.projectId,function(){
						window.history.go(-1);
						//window.location.replace('myAgencyTask.html');
					});	*/	
				}else{
					window.history.go(-1);
					//window.location.replace('myAgencyTask.html');
				}
			}else{
				getErrorMsg(data.result);
			}
	    });	
	}catch(e){
		log("fn:initPeerReviewedData STEP:"+step+"ERROR:"+e.messageInfo);
	}
}

//预加载评审专家打分,获得recordId
function beforeExpertScore(step,data){
	
	try{
		var scope=getAngularScope("initSeeData");
		var obj={	   
				"request.projectId": mainParm.parm.projectId,
		};
		beforeExpert(obj,function(data){
			if(data.result == 0){	
				scope.recordId=data.responseInfo.expertReview.recordId;
			}
		});	
	}catch(e){
		log("fn:beforeExpertScore STEP:"+step+"ERROR:"+e.messageInfo);
	}
}

/*
 * yxg 2017-02-17
 * 项目经理自评
 * 
 * */
function projectManagerEvaluate(state,data,taskId)
{
	var scope=getAngularScope("initSeeData");
	var obj={
			"request.selfAppraise":scope.projectEvaluateContent,//后台接口绑定到页面输入框
			"request.taskId":taskId,
	}
	console.log("项目经理自评内容："+scope.projectEvaluateContent);
	projectManagerEvaluateByServer(obj,function(data){
		if(data.result == 0){
			window.history.go(-1);
			//后台返回值绑定到页面
			//scope.projectEvaluateContentView=data.responseInfo.expertReview.recordId;
			//window.history.go(-1);
		}
	})
}

/*
 * yxg 2017-02-18
 * 部门经理评价
 * 
 * */
function departManagerEvaluate(state,data,taskId)
{
	var scope=getAngularScope("initSeeData");
	var obj={
			"request.departLeaderAppraise":scope.deparEvaluateContent,//后台接口绑定到页面输入框
			"request.taskId":taskId,
	}
	console.log("部门经理评价内容:"+scope.deparEvaluateContent);
	departManagerEvaluateByServer(obj,function(data){
		if(data.result == 0){
			console.log("项目经理自评提交后台成功");
			window.history.go(-1);
			//后台返回值绑定到页面
			//scope.deparEvaluateContentView=data.responseInfo.expertReview.recordId;
			//window.history.go(-1);
		}else{
			console.log("erropr");
		}
	})
}

//
function leaderMiddleSubmit(state,taskId)
{
	
	if(controllerStep.STEP_7==state){
		alert("经理提交中期评审材料start");
		var obj={
				"request.taskId":taskId
			};
		console.log(taskId);
		/*submitMidMaterial(obj,function(data){
			if(data.result == 0){
				alert("经理提交中期评审材料Finished");
			}
	    });	*/	
	}
}



//提交评审材料
function fileUpload(id){
	var scope=getAngularScope("initSeeData");
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    //async:true,
	    success: function (data)
	    {
	    	data=JSON.parse(data);
	    	if(!scope.fileUpload)
	    	{
	    		scope.fileUpload=new Object();
	    	}
	    	scope.fileUpload[mainParm.detailStep]= data.responseInfo.projectAnnexs;
	    	scope.projectAnnexs=data.responseInfo.projectAnnexs;
	    	scope.$applyAsync(scope.projectAnnexs);
	        var obj={
	        	"request.projectAnnexs":JSON.stringify(data.responseInfo.projectAnnexs),
	        	"request.taskId":mainParm.parm.taskId,
	        }
	        submitMaterial(obj,function(data){
	        	console.log(data);
	        });
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}

function leaderFinalSubmit(state,taskId)
{
	if(controllerStep.STEP_13==state){
		alert("经理提交终期评审材料start");
		var obj={
				"request.taskId":taskId
			};
		submitEndMaterial(obj,function(data){
			if(data.result == 0){
				alert("经理提交终期评审材料Finished");
				window.history.go(-1);
				//window.location.replace('myAgencyTask.html');		
			}
	    });
	}
}


//录入专家评分
  function inputExpertScore(step,data,taskId){
	 
		  var recordId=data.expertReview[0].recordId;
		  var scope=getAngularScope("initSeeData");
		  	var expertList=scope.expertList;
		  	var expertScoresMap=[];
	  		for(var i=0; i<expertList.length;i++)
	  		{   
		  		var newName=expertList[i].postName;
				var expertScore=expertList[i].expertScore;
				var newExpertScoreMap={
						postName:newName,
						expertScore:expertScore
				}
				expertScoresMap[i]=newExpertScoreMap;
	  		};
	  	
			var obj={
					"request.expertScore":JSON.stringify(expertScoresMap),
					"request.taskId":taskId,
					"request.recordId":recordId,		
			};
			try{
				
			expertScores(obj,function(data){
				if(data.result == 0){
					//location.replace('myAgencyTask.html');
					window.history.go(-1);
				}else{
					log(data.result);
				}
		    });	
	  }catch(e)
	  {
		  log(e);
	  }
	
}
//决策委员会意见后台数据交互调用
function committeeSuggestionDate(step,data,taskId){
	try{
		var userInfo= localStorage.getItem("userInfo");
		userInfo=JSON.parse(userInfo);
		var scope=getAngularScope("initSeeData");
		scope.userId=userInfo.uid;
			var obj={	   
					"request.taskId":taskId,
					"request.conclusion":scope.suggestion.suggestionFlag,
					"request.comments": scope.suggestion.suggestionDescription,
					"request.userId": userInfo.uid,
			};
			committeeSuggestion(obj,function(data){
				if(data.result == 0){	
					window.history.go(-1);
					
					//window.location.replace('myAgencyTask.html');
				}
		    });	
	}catch(e){
		 log("fn:committeeSuggestionDate  STEP:"+step+"ERROR:"+e.messageInfo);
	}
}
//部门经理审批的后台调用
  function managerSuggestionDate(step,data,taskId){
	  try{
		  var userInfo= localStorage.getItem("userInfo");
			userInfo=JSON.parse(userInfo);
			var scope=getAngularScope("initSeeData");
			var obj={	   
					"request.taskId":taskId,
					"request.conclusion":scope.managerSuggestion.suggestionFlag,
					"request.comments": scope.managerSuggestion.suggestionDescription,
					"request.userId": userInfo.uid
		
			};
			managerSuggestion(obj,function(data){
				if(data.result == 0){
					window.history.go(-1);
					//window.location.replace('myAgencyTask.html');
				}
		    });
	 }catch(e)
	 {
		  log("fn:managerSuggestionDate  STEP:"+step+"ERROR:"+e.messageInfo);
	 }
}



function log(message)
{
	if(config.log)
	{
		console.log(message);
	}
}
