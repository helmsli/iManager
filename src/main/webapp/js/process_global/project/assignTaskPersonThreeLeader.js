//controller作用域
App.controller('myDataController', ['$scope', '$ocLazyLoad', function($scope) { 
	$scope.deletFlag=false;
	//表格标题
	$scope.titleList = [
			"姓名",
			"所属部门",
			"所属单位"
	];
	$scope.arrList=[];
	$scope.pageFlag=true;
	$scope.showFlag=true;
//	$scope.dataList = data.dataList;
	$scope.initTab=function(){
		//console.log("初始化指定三级部门经理列表");
	};
	$scope.queryKey="";
	//全选列表里的checkbox
	$scope.setCheckAll=function(obj){
		var checkbox=$("#box input[data-checkBoxname='listCheckbox']");
		var checkBoxAll=$("#"+obj).prop("checked");
		checkbox.prop("checked",checkBoxAll);
		var dataList=$scope.dataList;
		//选中
		if(checkBoxAll)
		{
			for(var i=0;i<dataList.length; i++)
			{
				var isHide=$("#"+dataList[i].id).is(':hidden');
				//如果当前checkbox 是显示状态 就添加
				if(!isHide)
				{
					//取出来roleId
					//var roles=dataList[i].roles;
					//var roleId=roles[0];
					$scope.addChecked(dataList[i].id,dataList[i].firstname);
					//console.log("hahahhahah");
					console.log(dataList[i].firstname);
				}
			}
		}else{
			
			for(var i=0;i<dataList.length;i++)
			{
				var isHide=$("#"+dataList[i].id).is(':hidden');
				if(!isHide)
				{
					$scope.deleteChecked(dataList[i].id);
				}
			}			
		}
		
	}

	//单选中列表里的checkbox
	$scope.checkOnly=function(id,firstname)
	{
		$scope.setCheckState();
	}
	
	//已选列表里是否已存在?
	$scope.isCheckedArray=function(id)
	{
		var hasObj=false;
		var checkedList=$scope.arrList;
		var ii=0;
		while (ii<checkedList.length)
		{
			if(checkedList[ii].id==id)
			{
				hasObj=true;
				break;
			}
			ii++;
		}
		return hasObj;
	}
	
	$scope.findIndexForArray=function(id)
	{
		var findIndex=0;
		var checkedList=$scope.arrList;
		var i=0;
		while (i<checkedList.length)
		{
			if(checkedList[i].id==id)
			{
				findIndex=i;
				break;
			}
			i++;
		}
		return findIndex;
	}
	//选中添加到已选
	$scope.addChecked=function(id,firstname)
	{  
		var obj={id:id,firstname:firstname};
		var hasObj=$scope.isCheckedArray(id);
		if(!hasObj)
		{
			$scope.arrList.push(obj);
		}
		$scope.setCheckState();
	}
	//选中从已选列表里删除
	$scope.deleteChecked=function(id)
	{
		var checkedList=[];
		for(var object in $scope.arrList)
		{
			checkedList[object] = $scope.arrList[object];
		}
		var hasId=$scope.isCheckedArray(id);
		if(hasId)
		{
			var findIndex=$scope.findIndexForArray(id);
			console.log(findIndex);
			var checkedList1=checkedList.splice(findIndex,1);
			$scope.arrList=checkedList;
			
		}
		//界面有，数组没有
		
		//$scope.setCheckState();
	}
	//数组发生变更时候都调用一下，把所有的checkbox过滤一遍，检查是否有漏掉的。
	$scope.setCheckState=function()
	{
		var dataList=$scope.arrList;
		 //console.log("$scope.arrList");
		 //console.log($scope.arrList);
		var checkboxAll=$("#box input[data-checkBoxname='listCheckbox']");
		checkboxAll.each(function(){
			var _self=$(this);
			var id=_self.attr("id");
			var firstname=_self.attr("data-checkName");
			var checkedFlag=document.getElementById(id).checked;
			var hasId=$scope.isCheckedArray(id);
			/*
			 * 二选一
			 * 1.以checked选中为准
			 * 2.以arrList得到的数据为准
			 * */
			//checked为准
			if(checkedFlag)
			{
				if(!hasId)
				{
					$scope.arrList.push({id:id,firstname:firstname});
				}
			//界面没有选中，数组选中	
			}else{
				if(hasId)
				{
					var findIndex=$scope.findIndexForArray(id);
					$scope.arrList.splice(findIndex,1);
				}
			}
			if($scope.arrList.length>0)
			{
				$("#che_2").prop("checked",true);
				$scope.showFlag=false;
			}else{
				$("#che_2").prop("checked",false);
				$scope.showFlag=true;
			}
			
			//已数据为准
			/*if(hasId)
			{
				checkedFlag=true;
			}else{
				checkedFlag=false;
			}*/
		})
		//console.log($scope.arrList);
	}
	
	//删除已选中的列表
	$scope.deleteOnly=function(id){
		
		var checkedList=$scope.arrList;
		$scope.deleteChecked(id);
		var leng=checkedList.length;
		if(leng<=0)
		{
			$("#che_2").prop("checked",false);
			$scope.showFlag=true;
		}
		$("#box input[type='checkbox']").each(function(){
			var _self=$(this);
			var value=_self.val();
			if(id==value)
			{
				_self.prop("checked", false);
			}
		})
	}
	
	//确定三级部门经理名称并返给页面input
	$scope.confirmSelectAssignPersion=function ()
	{
		var parentScope=getAngularScope("projectManagerModel");
		$('#selectedModal').modal('hide');
		var checkedList=$scope.arrList;
		parentScope.project.assignPersonText="";
		for(var i =0; i<checkedList.length ;i++)
		{
			//console.log("checkedList");
			//console.log(checkedList);
			//var roleId=checkedList[i].id;
			//console.log("roleId")
			//console.log(roleId);
			var id=checkedList[i].id;
			var obj={roleType:1,privilege:0,id:id};
			if($scope.deletFlag==false){
				parentScope.threeLeaderIdLsit=[];
				$scope.deletFlag=true;
			}
			obj.name = checkedList[i].firstname;
			//console.log(obj);
			parentScope.threeLeaderIdLsit.push(obj);
			//发送送给后台的指定三级部门经理
			parentScope.project.assignPersonText +=checkedList[i].firstname+",";
			
		}
		//console.log("测试threeLeaderIdLsit");
		//console.log(JSON.stringify(parentScope.threeLeaderIdLsit));
	}
	
	/**
	 * 模糊查询匹配的接口
	 * @param id
	 */
	$scope.query=function(){
		//console.log("开始搜索了");
		var parentScope=getAngularScope("projectManagerModel");
		var obj={
			 "request.role_id":parentScope.id,
			 "request.keyWords":$scope.queryKey,//关键字
			/* "page.pageNum":1,
			 "page.pageSize":10*/
		};
		fuzzyQuery(obj,function(data){
			//console.log("开始搜索了调用fuzzyQuery");
		 	if(data.result == "0"){
		 		//console.log("竟然成功了");
		 		//console.log(data);
		 		$scope.dataList=data.responseInfo.lists;
		 		//console.log($scope.dataList);
		 		$scope.$applyAsync($scope.dataList);
			}
		 });
		
	}
	
	//已经选中的第二次点击选择的时候要被带回的
	$scope.getChecked=function(id){	//已经选中的第二次点击选择的时候要被带回的
		$scope.getChecked=function(id){
			var scope=getAngularScope("projectManagerModel");
			var flag=false;
			var threeLeaderLsit=$scope.arrList;
			for(var i in threeLeaderLsit){
					 if(id == threeLeaderLsit[i].id){
						 flag= true;
						 break;
					 }
			}
			return flag;
		}
		}
	
}]);

//
function initTab(){
	//向服务器查询所有的部门经理；
	
}

function initThreeLeader(){
	var scope=getAngularScope("projectManagerModel");
	var id=scope.id;
	//alert(id);
	initSetPersonData(id);
}


