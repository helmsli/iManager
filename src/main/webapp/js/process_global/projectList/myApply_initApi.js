/***
 * 请求我的项目参数
 * 
 * **/
function getMyProjectOptions(projectName,pageNum) {
	var projectName=projectName.toLowerCase();
	var obj = null;
	pageNum = pageNum||1;
	var  pageSize = 10, userId = "";
	var userInfo=localStorage.getItem("userInfo");
	userId=JSON.parse(userInfo).uid;
	var parm = parseQueryString();
	var projectId = parm.projectId;
	switch (projectName) {
	case "coomarts":
		categoryId = 1;
		break;
	case "camtalk":
		categoryId = 2;
		break;
	case "lottery":
		categoryId = 3;
		break;
	}
	obj = {
		"request.projectId": projectId,	
		"request.userId" : userId,
		"request.categoryId" : categoryId,
		"page.pageNum" : pageNum,
		"page.pageSize" : pageSize
	};
	return obj;
}



/***
 * 我的申请项目中根据参数请求不同的数据接口
 * 
 * **/
function getMyApplyDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
		case "coomarts":
		case "camtalk":
		case "lottery":
			getCooMartsApplyListByServer(obj,getCooMartsApplyListCall);
			break;
		case "camtalk":
			getCamTalkApplyListByServer(obj,getCamTalkApplyListCall);
		    break;
		case "lottery":
			getLotteryApplyListByServer(obj,getLotteryApplyListCall);
		   break;
	}
}

/***
 * 我的申请已完成项目中根据参数请求不同的数据接口
 * 
 * **/
function getUserFinishedTasklist(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
		case "coomarts":
		case "camtalk":
		case "lottery":
			getCooMartsFinishedListByServer(obj,getCooMartsFinishedListCall);
			break;
		case "camtalk":
			getCamTalkFinishedListByServer(obj,getCamTalkFinishedListCall);
		    break;
		case "lottery":
			getLotteryFinishedListByServer(obj,getLotteryFinishedListCall);
		   break;
	}
}

/***
 * 我的代办任务中根据参数请求不同的数据接口
 * 
 * **/
function getMyAgentTaskDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
		case "coomarts":
		case "camtalk":
		case "lottery":
			getCooMartsAgentTaskListByServer(obj,getCooMartsAgentTaskListCall);
			break;
		case "camtalk":
			getCamTalkAgentTaskListByServer(obj,getCamTalkAgentTaskListCall);
		    break;
		case "lottery":
			getLotteryAgentTaskListByServer(obj,getLotteryAgentTaskListCall);
		   break;
	}
}
/***
 * 我的代办任务中已完成根据参数请求不同的数据接口
 * 
 * **/
function getMyAgentTaskFinishedDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
		case "coomarts":
		case "camtalk":
		case "lottery":
			getCooMartsAgentTaskFinishedListByServer(obj,getCooMartsAgentFinishedTaskListCall);
			break;
		case "camtalk":
			getCamTalkAgentTaskFinishedListByServer(obj,getCamTalkAgentFinishedTaskListCall);
		    break;
		case "lottery":
			getLotteryAgentTaskFinishedListByServer(obj,getLotteryAgentFinishedTaskListCall);
		   break;
	}
}
/***
 * 我的申请修改项目根据参数请求不同的数据接口
 * 
 * **/
function getMyApplyEditDataInfo(projectName,obj)
{
	projectName=projectName.toLowerCase();
	console.log(projectName);
	switch(projectName)
	{
		case "coomarts":
		case "camtalk":
		case "lottery":
			getMyApplycoomartsEditDataInfoByServer(obj,getMyApplycoomartsEditDataInfoCall);
			break;
		case "camtalk":
			getMyApplycamtalkEditDataInfoByServer(obj,getMyApplycamtalkEditDataInfoCall);
		    break;
		case "lottery":
			getMyApplylotteryEditDataInfoByServer(obj,getMyApplylotteryEditDataInfoCall);
		   break;
	}
}


/***
 * 我的申请项目--相关资料--根据参数请求不同的数据接口
 * 
 * **/
function getMyApplyRelevantFileDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
		case "coomarts":
			getMyApplyCoomartsRelevantFileDataListByServer(obj,getMyApplyCoomartsRelevantFileDataListCall);
			break;
		case "camtalk":
			getMyApplyCamtalkRelevantFileDataListByServer(obj,getMyApplyCamtalkRelevantFileDataListCall);
		    break;
		case "lottery":
			getMyApplyLotteryRelevantFileDataListByServer(obj,getMyApplyLotteryRelevantFiletDataListCall);
		   break;
	}
}

/***
 * 查看所有项目--根据参数请求不同的数据接口
 * 
 * **/
function getAllProjectSeeList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
		case "coomarts":
		case "camtalk":
		case "lottery":
			getCoomartsAllProjectSeeListByServer(obj,getCoomartsAllProjectSeeListCall);
			break;
		case "camtalk":
			getCamtalkAllProjectSeeListByServer(obj,getCamtalkAllProjectSeeListCall);
		    break;
		case "lottery":
			getLotteryAllProjectSeeListByServer(obj,getLotteryAllProjectSeeListCall);
		   break;
	}
}

/***
 * 查看所有进行中的项目--根据参数请求不同的数据接口
 * 
 * **/
function getAllProjectMarchSeeList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
		case "coomarts":
		case "camtalk":
		case "lottery":
			getCoomartsAllProjectMarchSeeListByServer(obj,getCoomartsAllProjectMarchSeeListCall);
			break;
		case "camtalk":
			getCamtalkAllProjectMarchSeeListByServer(obj,getCamtalkAllProjectMarchSeeListCall);
		    break;
		case "lottery":
			getLotteryAllProjectMarchSeeListByServer(obj,getLotteryAllProjectMarchSeeListCall);
		   break;
	}
}

/***
 * 我的发布列表--根据参数请求不同的数据接口
 * 
 * **/
function getDepartmentReleaseDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
		getCooMartsDepartmentReleaseListByServer(obj,getCooMartsDepartmentReleaseListCall);
		break;
	case "camtalk":
		getCamTalkDepartmentReleaseListByServer(obj,getCamTalkDepartmentReleaseListCall);
	    break;
	case "lottery":
		getLotteryDepartmentReleaseListByServer(obj,getLotteryDepartmentReleaseListCall);
	   break;
	}
}




/***
 * 我发布的任务列表--根据参数请求不同的数据接口
 * 
 * **/
function getDepartmentReleaseDataForThreeLeaderList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
		getCooMartsDepartmentReleaseForThreeLeaderListByServer(obj,getCooMartsDepartmentReleaseListForThreeLeaderCall);
		break;
	case "camtalk":
		getCamTalkDepartmentReleaseForThreeLeaderListByServer(obj,getCamtalkDepartmentReleaseListForThreeLeaderCall);
	    break;
	case "lottery":
		getLotteryDepartmentReleaseForThreeLeaderListByServer(obj,getLotteryDepartmentReleaseListForThreeLeaderCall);
	   break;
	}
}




/***
 * 我申报的项目列表--根据参数请求不同的数据接口
 * 
 * **/
function getThreeleaderDeclareDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
		getCooMartsprojectManagerDeclareListByServer(obj,getCooMartsProjectManagerDeclareListCall);
		break;
	case "camtalk":
		getCamtalksprojectManagerDeclareListByServer(obj,getCamtalksProjectManagerDeclareListCall);
	    break;
	case "lottery":
		getLotterysprojectManagerDeclareListByServer(obj,getLotteryProjectManagerDeclareListCall);
	   break;
	}
}

/***
 * 待申报的项目列表--根据参数请求不同的数据接口
 * 
 * **/
function getDepartmentReleaseDataForShenBaoList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
		getCooMartsprojectShenBaoByServer(obj,getCooMartsProjectShenBaoListCall);
		break;
	case "camtalk":
		getCamtalksprojectShenBaoByServer(obj,getCamtalksProjectShenBaoListCall);
	    break;
	case "lottery":
		getLotterysprojectShenBaoByServer(obj,getLotteryProjectShenBaoListCall);
	   break;
	}
}



/***
 * 数据监测列表--搜索
 * getDataMonitorListByServer
 * **/
function getDataMonitorDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
		getCooMartsDataMonitorListByServer(obj,getCooMartsDataMonitorListCall);
		break;
	case "camtalk":
		getCamtalkDataMonitorListByServer(obj,getCamtalksDataMonitorListCall);
	    break;
	case "lottery":
		getLotteryDataMonitorListByServer(obj,getLotteryDataMonitorListCall);
	   break;
	}
}
/***
 * 数据监测列表--初始化
 * getDataMonitorListByServer
 * **/
function getDataMonitorInitDataList(projectName,obj)
{
	projectName=projectName.toLowerCase();
	switch(projectName)
	{
	case "coomarts":
	case "camtalk":
	case "lottery":
		getCooMartsDataMonitorInitListByServer(obj,getCooMartsDataMonitorListCall);
		break;
	case "camtalk":
		getCamtalkDataMonitorInitListByServer(obj,getCamtalksDataMonitorListCall);
	    break;
	case "lottery":
		getLotteryDataMonitorInitListByServer(obj,getLotteryDataMonitorListCall);
	   break;
	}
}





















