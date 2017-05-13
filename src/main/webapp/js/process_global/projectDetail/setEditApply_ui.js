/***
 * 我申请的项目修改的渲染界面(coomarts项目)
 * 
 * **/
 function getMyApplycoomartsEditDataInfoCall(data){
	 var scope=getAngularScope("EditData");
		if(data.result == 0){
		 scope=getAngularScope("EditData");
		 var myParm=parseQueryString();//所有的参数
		 //如果是变更修改则显示拒绝修改的按钮
		 if(myParm.changeCurrentState=="modifyChange"){
			 var  refuse={
					  state:true	  
			  }
			 scope.refuse=refuse;
			 scope.$apply(scope.refuse);
			 console.log(scope.refuse);
		 }
			scope.data=data;
			console.log(data);
			//获取项目信息、额外信息
			var project=data.responseInfo.project;
			
			scope.telno=project.telno;
			scope.email=project.email;
			scope.changeDataId=project.changeDataId;
			//项目名称 、项目负责人、 联系电话、 电子邮箱
			$('#taskName').children('div').html(project.projectName);
			$('#myNum').children('div').html(scope.telno);
			$('#email').children('div').html(scope.email);
			
			var projectExtInfo=JSON.parse(project.projectExtInfo);
			//项目基本情况-对应页面组件
			var startTime=project.startTime;
			var endTime=project.completeTime; 
			
			var cycleType=project.cycleType;
			var category=project.subcategory;
			//使用原生给项目概述、项目背景、项目目标、项目预期的风险、项目创新性及推广性进行赋值
			var taskDescribe=projectExtInfo.taskDescribe;
			var taskbackground=projectExtInfo.taskbackground;
			var taskgoal=projectExtInfo.taskgoal;
			var taskDanger=projectExtInfo.taskDanger;
			var taskcreate=projectExtInfo.taskcreate;
			
			//项目基本情况
			scope.ProjectId=project.projectId;	
			scope.ProjectApplyTime=project.projectApplyTime;
			scope.StateName=project.stateName;
			scope.projectManagerId=project.projectManagerId;
			 
			//使用原生给项目概述、项目背景、项目目标、项目预期的风险、项目创新性及推广性进行赋值
			scope.taskDescribe=taskDescribe;
			scope.taskbackground=taskbackground;
			scope.taskgoal=taskgoal;
			scope.taskDanger=taskDanger;
			scope.taskcreate=taskcreate;
			
			//项目概述、项目背景、项目目标、项目预期的风险、项目创新性及推广性
			// 获取所有的P元素
		    $(".tip").remove();
		    
		    $('#taskDescribe').children('div').html(taskDescribe);
		    $('#taskbackground').children('div').html(taskbackground);
		    $('#taskgoal').children('div').html(taskgoal);
		    $('#taskDanger').children('div').html(taskDanger);
		    $('#taskcreate').children('div').html(taskcreate);

			//日期转换-开始时间、结束时间  
			dataFormat(startTime,true);
			dataFormat(endTime,false);
			//项目周期、项目类型
			
			getRadioChecked(cycleType,"projectCycle");
			getRadioChecked(category,"projectCategory");
			//项目实施计划及输出、项目预算、项目成员岗位要求
			var tableRuleInner= JSON.parse(projectExtInfo.taskplan);
			var tableRule=JSON.parse(data.responseInfo.project.projectCosts)
			var tablePost=JSON.parse(data.responseInfo.project.projectTaskDetail);
			
			//项目需要的准备条件
			var cpu=tablePost.CPU;
			var otherConditions=tablePost.otherConditions;
			var memory=tablePost.memory;
			var bandWidth=tablePost.bandWidth;
			var UpgradeTime=tablePost.UpgradeTime;
			var serversNumber=tablePost.serversNumber;
			$("#CPU").children('div').html(cpu);
			$("#memory").children('div').html(memory);
			$("#bandWidth").children('div').html(bandWidth);
			$("#serversNumber").children('div').html(serversNumber);
			$("#UpgradeTime").children('div').html(UpgradeTime);
			$("#otherConditions").children('div').html(otherConditions);
						
			tableEditText("tableRuleInner",tableRuleInner);
			tableEditText("tableRule",tableRule);
			
			//项目预算合计
			getTotalValue();

			/*
			//处理变成列list
			function dynamicFormList(ListName,LineName){
				for(var i=0;i++;i<ListVlue.length){
					//LineName[i].columnName
				}
			}*/
			//修改取得的老数据（变更之前的）
			var oldInfo={
					projectName:project.projectName,
					telno:scope.telno,
					email:scope.email,
					taskDescribe:taskDescribe,
					taskbackground:taskbackground,
					taskgoal:taskgoal,
					taskDanger:taskDanger,
					taskcreate:taskcreate,
					projectCycle:scope.projectCycle,
					projectCategory:scope.projectCategory,
					startTime:scope.startTime,
					endTime:scope.endTime,
					tableRuleInner:tableRuleInner,
					tableRule:tableRule,
					tablePost:tablePost
			}
			var newInfo=oldInfo;
			scope.oldInfo=oldInfo;
			scope.newInfo=newInfo;
			//进入修改页面就进行判断，数据全有则可以提交，数据不是全有则提交置灰
			valid();	
		}
 }
 
//radio选中状态改变
 function getRadioChecked(item){
 	//$("#001").prop("checked",false);
	console.log(item);
 	$("#002").prop("checked",false);
 	var info=JSON.parse(item)[0];
 	if(info.id == "802"){
 		$("#category").val(info.value);
 	}else if(info.id == "301"){
 		$("#period").val(info.value);
 	}
 	$("#"+info.id).prop("checked",true);
 }

 //日期格式转换  示例：dataFormat("Jan 5, 2017 2:17:13 PM")
 function dataFormat(data){
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
 	 $("#"+data+" input").prop("value",time);
 }
 
//动态增加行表格数据渲染
 function tableEditText(id,data){
 		addTableRule(id,data.length);
    	$("#"+id+" tbody tr").each(function(){
            var _selfTr=$(this);
            var dataGroupNum=_selfTr.attr("data-group");
            _selfTr.children("td").each(function(){
            	var _self=$(this);
            	var type=_self.attr("data-editabletype");
 	            var dataName=_self.attr("data-datename");     
 	            if(type == "edit" && dataName != undefined){
 	        	   _self.children("div").append(data[dataGroupNum][dataName]);
 	            } 
            });
     });	
 }
 
//项目预算合计
function getTotalValue(){
	 $("#tableRule tbody tr td div[contenteditable='true']").each(function(){
			var _this=$(this);
			var tr=_this.parent('td').parent("tr");
			var cloum=tr.attr("data-group");
			var name=_this.parent('td').attr("data-datename");
			var myPrice="";
			var myNumber="";
			var newSubtotal=0;
			var newPriceTd=tr.find("td").eq(2).children("div");
			var newNumberTd=tr.find("td").eq(3).children("div");
			var subTotalTd=tr.find("td").eq(4);
			if(name=="payPrice"||name=="payNumber")
			{
			      var _this=$(this);
			      var myNumval=  _this.html();
			      _this.html(myNumval);
				  myPrice=newPriceTd.text();
				  myNumber=newNumberTd.text();
				  newSubtotal=myPrice*myNumber;
				  subTotalTd.text(newSubtotal.toFixed(2));
			}
	 	});
 }

 