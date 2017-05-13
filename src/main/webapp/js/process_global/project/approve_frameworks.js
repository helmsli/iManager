
/**
 * submit提交函数入口控制
 * @parm step ，data;
 * 处理各个小步骤提交
 * **/
function subMitFnCall(callBack)
{
	
	//获取步聚
	var parm = parseQueryString();
	var step=parm.state;
	//alert(callBack);
	callBack(parm);
	return;
	
	
	
	
	
	switch(step)
	{
		//STEP_1 提交按钮执行初期的指定评审专家
		case controllerStep.STEP_1:
			initPeerReviewedData(step,data,taskId);
		break; 
		 //STEP_2 提交按钮执行初期的指定评审打分
			case controllerStep.STEP_2:
				inputExpertScore(step,data,taskId);
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
			case controllerStep.STEP_7:
				leaderMiddleSubmit(step,taskId);
			break ;
			//STEP_8 提交按钮执行中期的指定评审
			case controllerStep.STEP_8:
				initPeerReviewedData(step,data,taskId);
			break; 
			
			//STEP_10 提交按钮执行中期的评审专家大打分
			case controllerStep.STEP_10:
		        inputExpertScore(step,data,taskId);
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
				leaderFinalSubmit(step,taskId);
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
	}
}