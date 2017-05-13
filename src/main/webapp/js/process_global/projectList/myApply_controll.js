var nodeInfo = {};
/*定义module*/
App.controller('myDataController', ['$scope', '$ocLazyLoad', function($scope) { 
	//表格标题
	$scope.titleList = [
			"项目编号",
			"项目名称",
			"申请时间",
			"项目状态",
			"下个审批人",
			"操作"
	];
    /*initMyApply(10);
    function initMyApply(pageNum)
    {
    	    var myParm=parseQueryString();//所有的参数
            var projectName=myParm.projectName; 
    	    var obj=null;
    	    obj = getMyProjectOptions(projectName,pageNum);
    	    getMyApplyDataList(projectName,obj);
    }*/
    
    $scope.pageFlag=true;
	//数据初始化--我的申请项目	
	$scope.initTab=function(pageNum){
		var myParm=parseQueryString();//所有的参数
        var projectName=myParm.projectName; 
	    var obj=null;
	    obj = getMyProjectOptions(projectName,pageNum);
	    getMyApplyDataList(projectName,obj);
	}
    
    
    
	/***
	 * 查看申请项目
	 * @param {Object} userId
	 */
	$scope.openMyapplySeeWin = function(projectId,state,changeStata,seeState){
		var url="",
			addFlag=false;
		if(!projectId){
			projectId = "";
			addFlag=true;
		}
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url = "myApply_see.html?projectName="+projectName+"&projectId="+projectId+"&state="+state+"&seeState="+seeState+"&from="+from+"&changeStata="+changeStata;
		location.href=url;
		
	}
	$scope.openNewMyapplySeeWin = function(projectId,state,changeStata,seeState){
		var url="",
			addFlag=false;
		if(!projectId){
			projectId = "";
			addFlag=true;
		}
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		seeState=(seeState==1)?true:false;
		//url = "myApply_see.html?projectName="+projectName+"&projectId="+projectId+"&state="+state+"&seeState="+seeState+"&from="+from+"&changeStata="+changeStata;
		url="projectManager/project_detail_view.html?projectName="+projectName+"&projectId="+projectId+"&state="+state+"&seeState="+seeState+"&from="+from+"&bg="+changeStata;
		location.href=url;
		
	}
	/***
	 *查看变更项目
	 * @param {Object} userId
	 */
	$scope.openSeeChangeBg = function(projectId,state,changeStata){
		var url="";
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		url = "myApply_seeBg.html?projectName="+projectName+"&projectId="+projectId+"&state="+state+"&from="+from+"&changeStata="+changeStata;
		location.href=url;
		
	}

	/***
	 * 修改申请项目
	 * @param {Object} userId
	 */
	$scope.openMyapplyEditWin = function(projectId,state,changeStata){
		console.log(state);
		var url="",
			addFlag=false;
		var editFlag=false;
		if(!projectId){
			projectId = "";
			addFlag=true;
		}
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		 urlEdit = "newApply.html?projectName="+projectName+"&projectId="+projectId+"&changeStata="+changeStata+"&addFlag="+addFlag+"&from="+from;
        location.href=urlEdit;
	/*	if(state=="007"||state=="013"){
			 editFlag=true;
			 urlEdit = "myApply_see.html?projectName="+projectName+"&projectId="+projectId+"&addFlag="+addFlag+"&edit="+editFlag+"&from="+from;
			location.href=urlEdit;	
		}else{
			location.href=urlSee;
		}*/
	}
	$scope.openCreateBg=function(projectId,state,changeStata){
		var url="",
			addFlag=false;
		var editFlag=false;
		if(!projectId){
			projectId = "";
			addFlag=true;
		}
		var parm=parseQueryString();
		var projectName =parm.projectName||""; 
		 urlEdit = "newBg.html?projectName="+projectName+"&projectId="+projectId+"&Bg="+changeStata+"&addFlag="+addFlag+"&from="+from;
        location.href=urlEdit;
	}
	
}]);
