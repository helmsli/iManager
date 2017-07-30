
var projectName = "camtalk";

/*
 * 项目申报
 */
function projectApply(){
	
	
	//views/camtalk/newApply.html?projectName=camtalk
			
	//url= "../"+projectName+"/departmentReleaseNeedDeclare.html?projectName="+projectName;
	url= "../applycenter.html";
	
	location.href = url;
}
function projectApplyed(){
	
	
	//views/camtalk/newApply.html?projectName=camtalk
			
	//url= "../"+projectName+"/departmentReleaseNeedDeclare.html?projectName="+projectName;
	url= "../"+projectName+"/newApply.html?projectName="+projectName;
	
	location.href = url;
}
/*
 * 数据监测
 */
function projectMonitor(){
	//dataMonitorByCommittee.html?
	url= "../"+projectName+"/dataMonitorByCommittee.html?serviceType=submitMonthlyReport&projectName="+projectName;
	location.href = url;
}

/*
 * 项目结项
 */
function projectEnd(){
	url= "../"+projectName+"/concludeManage.html?projectName="+projectName;
	location.href = url;
}