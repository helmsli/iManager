//创建申请项目的模板路径
function getTempletPath(projectName)
{
	var temPath="";
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	    temPath="createTemplet_A";
	    break;
	case "camtalk":
	    temPath="createTemplet_B"
	    break;
	case "lottery":
	    temPath="createTemplet_C"
	    break;
	
	}
   return temPath;
}

//查看项目详细信息的模板路径
function getDetailTempletPath(projectName)
{
	var temPath="";
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	    temPath="detailTemplate_A";
	    break;
	case "camtalk":
	     temPath="detailTemplate_B"
	     break;
	case "lottery":
	     temPath="detailTemplate_C"
	     break;
	
	}
   return temPath;
}

/**
 * 请求调查问题模板(表格)
 * **/
function getQuestionTemplatePath(projectName)
{
	var temPath="";
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
	    temPath="question_C.html";
	    break;
	case "camtalk":
	     temPath="question_B.html"
	     break;
	case "lottery":
	     temPath="question_C.html"
	     break;
	}
   return temPath;
	
}


/**
 * 请求项目初审模板(表格)
 * **/
function getExamineTrialTemplatePath(projectName)
{
	var temPath="";
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
	    temPath="examineTrial_A.html";
	    break;
	case "camtalk":
	     temPath="examineTrial_B.html"
	     break;
	case "lottery":
	     temPath="examineTrial_C.html"
	     break;
	}
   return temPath;
	
}

/**
 * 请求项目评审指标模板(表格)
 * **/
function getExamineIndicesTemplatePath(projectName)
{
	var temPath="";
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
	    temPath="examineIndices_A.html";
	    break;
	case "camtalk":
	     temPath="examineIndices_B.html"
	     break;
	case "lottery":
	     temPath="examineIndices_C.html"
	     break;
	}
   return temPath;
}

/**
 * 请求项目经理月度提交模板(表格)
 * **/
function getProjectMonthSubmitTemplatePath(projectName)
{
	var temPath="";
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
	    temPath="projectMonthProgress_A.html";
	    break;
	case "camtalk":
	     temPath="projectMonthProgress_B.html"
	     break;
	case "lottery":
	     temPath="projectMonthProgress_C.html"
	     break;
	}
   return temPath;
}

/**
 * 请求项目经理月度提交模板(表格)
 * **/
function getCommitteeMonitorTemplatePath(projectName)
{
	var temPath="";
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
	    temPath="committeeMonitor_A.html";
	    break;
	case "camtalk":
	     temPath="committeeMonitor_B.html"
	     break;
	case "lottery":
	     temPath="committeeMonitor_C.html"
	     break;
	}
   return temPath;
}






