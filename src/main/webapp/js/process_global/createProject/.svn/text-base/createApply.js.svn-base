App.controller('EditData', ['$scope',function($scope) {	
	//edeitData数据模型存储修改的数据
	$scope.edeitData={
				upData:{},
				deletData:{},
				addData:{
				}
		};
	
	var userInfo=localStorage.getItem("userInfo");
	var projectManager=JSON.parse(userInfo).fullName;
	$scope.projectManager=projectManager;
	$scope.templateName="";
	$scope.initTemplate=function()
	{
		 //通过URL获取参数
		var parm = parseQueryString();
		var projectName=parm.projectName;
		var templateName=getTempletPath(projectName);
		//加载模板
		$scope.templateName=templateName;
	}
	//判断是否为修改
	$scope.init=function (){
		var parm = parseQueryString();
		var projectId=parm.projectId;
		var projectName=parm.projectName;
		if(projectId){
			var obj=null;
			console.log("判断是不是走的变更");
			obj=getMyProjectOptions(projectName);
			//获取项目信息
			getMyApplyEditDataInfo(projectName,obj);
		
		}else{
			setInitTime();
			
		}
	}
}]);

//动态增加行表格数据渲染
function tableEditContext(id,data){
	addTableRule(id,data.length);
   	$("#"+id+" tbody tr").each(function(){
           var _selfTr=$(this);
           var dataGroupNum=_selfTr.attr("data-group");
           _selfTr.children("td").each(function(){
           	    var _self=$(this);
           	    var type=_self.attr("data-editabletype");
	            var dataName=_self.attr("data-datename");     
	            if(type == "edit" && dataName != undefined){
	        	   _self.append(data[dataGroupNum][dataName]);
	            } 
           });
    });	
}
//radio选中状态改变
function getRadioChecked(item,radioCategory){
	//$("#001").prop("checked",false);
	 var scope=getAngularScope("EditData"); 
	$("#002").prop("checked",false);
	var info=JSON.parse(item)[0];
	if(radioCategory=="projectCycle"){
		scope.projectCycle=info.value;
	}
	if(radioCategory=="projectCategory"){
		scope.projectCategory=info.value;
	}

	if(info.id == "802"){
		$("#category").val(info.value);
		
		
	}else if(info.id == "301"){
		$("#period").val(info.value);
		
	}
	$("#"+info.id).prop("checked",true);
}

//日期格式转换  示例：dataFormat("Jan 5, 2017 2:17:13 PM")
function dataFormat(data,flag){
	var scope=getAngularScope("EditData"); 
	var myDate=new Date(data);
	var h1=myDate.getFullYear();
    var m1=myDate.getMonth()+1;  
    var d1=myDate.getDate();
    var h=myDate.getHours();       //获取当前小时数(0-23)
	var m=myDate.getMinutes();     //获取当前分钟数(0-59)
	var s=myDate.getSeconds();
	m1= m1<10?"0"+m1:m1;           //这里判断月份是否<10,如果是在月份前面加'0'
	d1= d1<10?"0"+d1:d1;           //这里判断日期是否<10,如果是在日期前面加'0'
	
	var time = h1+"-"+m1+"-"+d1+" "+h+":"+m+":"+s;
	console.log("#"+data+"input[type='text']");
	if(flag==true){
		$("#startTime input[type='text']").prop("value",time);
		scope.startTime=time;	
	}else{
		$("#endTime input[type='text']").prop("value",time);
		scope.endTime=time;
	}
} 
















