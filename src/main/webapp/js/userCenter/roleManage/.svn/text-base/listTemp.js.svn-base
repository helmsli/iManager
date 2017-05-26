$(function(){
	/*单击行，添加选中样式*/
	$("table tbody").delegate("tr","click",function(event){
		var target = event.target||event.srcElement;
		var _input = $(this).find('input');
		var _nodeName = target.nodeName.toLowerCase();
		if(_nodeName=='input'){
			_input.checked = !_input.checked;  
		}else if(_nodeName!='label'){
			_input.click();
		}
	});
});
var nodeInfo={};
/*定义module*/
App.controller('roleManageController', function($scope,$ocLazyLoad){
    /*标题*/
	$scope.titleList=[
		"用户姓名",
		"电话号码"
	];
    $scope.role={};
	$scope.loadBootstrap = function(){
		var myFileList=[];
		myFileList=loadFileList(myFileList);
		//console.log("要加载的文件:"+myFileList);
        $ocLazyLoad.load(myFileList);
	};
    $scope.loadBootstrap();
    /*初始化role*/
    $scope.initRole = function(){
    	$scope.bindRoleEvent();
    	$scope.getRoles();
    };
    /*委托事件，为角色行绑定click事件*/
    $scope.bindRoleEvent = function(){
   	   var roleList=$("#list-content");
			roleList.delegate("li","click",function(){
				roleList.children("li").removeClass("current_selected");
				$(this).addClass("current_selected");
				 
				if (!$(this).hasClass("current_selected")) {
			        var currentA=$(this).parent("li").siblings("li");
			        currentA.removeClass("current_selected"); 
			    }
		});
    };
    /*后台查询角色列表*/
	$scope.getRoles  = function(){
    	getAllRoles("",function(data){
    		$scope.roles=data.responseInfo.roles;
    		$scope.role=data.responseInfo.roles[0];
    		$scope.$applyAsync($scope.role);
    		//初始化默认用户列表		
    		$scope.getRoleUser($scope.role,1);
    		//初始化默认权限树
    		$scope.getPermissionList($scope.role);
    	});
    };
    
    /*角色列表选中状态判断*/
    $scope.selected=function(index){
    	
    	if(index == 0){
    		return "current_selected";
    	}else{
    		return "";
    	}
    };
    
	var count=0;
	//用于跳转页面时，更新分页信息	
	$scope.getCount=function (){
    	count = 0;
    };
    /*查询角色对应的用户列表*/
	$scope.getRoleUser=function (role,page){
		/*角色的用户信息*/
		var obj={
			"request.role_id":role.id,
			"request.containRoleId":0,
			"page.pageNum":page,
			"page.pageSize":10
		};
		getRoleUsers(obj,function(data){
		 	if(data.result == "0"){
		 		$scope.roleUsers=data.responseInfo.lists;
		 		var pages=data.responseInfo.page.pages;
				$scope.$applyAsync($scope.roleUsers);
				if(count == 0){
					pageNav.go(1,data.responseInfo.page.pages);
		 			count++;
				}
		 	}else{
		 		console.log("获取数据失败");
		 	}
			
		 });
		$scope.role=role;
		$scope.$broadcast("getRoleUser",role);
	};
	
	 /*分页*/
	pageNav.fn = function(p,pn){
		$("#che_0").prop("checked", false);
		$("#che_1").prop("checked", false);
		$scope.getRoleUser($scope.role,p);
    };  
    /*全选样式变换*/
	$scope.checkAll=function (event){
		var target = event.target||event.srcElement;
		var _checked  = target.checked;
		var checkbox=$("#box input[type='checkbox'][data-admin='false']");
		if(checkbox.length>0){
			checkbox.prop("checked", _checked);
		}
		var checkAll = event.target.id=="che_0"?"che_1":"che_0";
		$("#"+checkAll).prop("checked", !$("#"+checkAll).prop("checked"));
		
	};
    
    /*删除用户*/
	$scope.deleteUser=function (role){
		removeUserFormRole(role);
	};
	/*在角色中移除用户*/
	function removeUserFormRole(role){
		var userIds = [];
		$("#box input[type='checkbox']:checked").each(function(index){
			var id = $(this).attr("id");
			var myData = $scope.roleUsers[id];
			if(typeof myData !='undefined'){
				userIds.push(myData.id);
				if(myData.id =="1"){
					swal("不允许移除admin管理员账号");
					return false;
			    }
			}
		});
		//后台删除
		var obj={
				"request.roleId":role.id,
				"request.userIds":userIds,
		};
		if(userIds.length == 0){
			swal("请您先选择您要操作的用户！");
		}else{
			swal({
		        title: "确认要移除选择的用户吗？",
		        type: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
		        confirmButtonText: "确定",
		        cancelButtonText:"取消",
		        closeOnConfirm: false
		    }, function () {
		    	deleteRoleUsers(obj,function(data){
					if(data.result == "0"){
						swal("用户移除成功！", "", "success");
						count = 0;
						$scope.getRoleUser(role,1);	
					}
				});
		    });
		}
	}
	/*去配置权限按钮*/
	$scope.editAuthority=function (role)
	{
		$("#user-panel").hide();
		$("#permission-panel").show();
	};
	/*获取权限*/
	$scope.getPermissionList = function(role){
		$scope.$broadcast("getPermissionChecked",role);
	}
	/*删除角色*/
	$scope.deleteRole=function (roleId){
		//$scope.$broadcast("deleteRole",id);
		if(roleId =="1"){
			swal(getErrMsg("1501"));
			return false;
	    }
		swal({
	        title: "确认要删除该角色吗？",
	        //text: "删除后，用户数据将无法恢复",
	        type: "warning",
	        showCancelButton: true,
	        confirmButtonColor: "#DD6B55",
	        confirmButtonText: "确定",
	        cancelButtonText:"取消",
	        closeOnConfirm: false
	    }, function () {
	    	$scope.deleteRoleTrue(roleId);
	    });
	};
	//删除角色	
	$scope.deleteRoleTrue=function (id){
		/*角色的用户信息*/
		var obj={
			"request.id":id,
		};
		deleteRole(obj,function(data){
			if(data.result == "0"){
				$scope.getRoles();
				swal("删除成功", "", "success");
			}else {
				swal(getErrMsg(data.result));
			}
		});
	}
	/*编辑角色*/
	$scope.editRole=function (role){
		$scope.$broadcast("editRole",role);
	}
	/*添加角色*/
	$scope.addRole=function (type){
		$scope.$broadcast("addRole",type);
	}
	/*添加角色对应的用户*/
	$scope.addRoleUser=function(id){
		$scope.$broadcast("addRoleUser",id);
	}
	//定义变量ID
	$scope.$on("editRole",function(e,flag){
		console.log("editflag"+flag)
		if(flag == true){
			$scope.getRoles();
		}
	});
	$scope.$on("addUsers",function(e,role){
		count = 0;
		
		$scope.getRoleUser($scope.role,1);
	});
	
});
/*添加角色控制器*/
App.controller('addRole', function($scope){
	//添加表单验证
	$scope.formValid = new FormValid({
		"formId":"theForm",
		"formField":[{
			"id":"roleName",
			"validateRule":{"numberLetter":true}
		}]
	});
	//获取角色id
	$scope.$on("editRole",function(e,role){
		console.log(role);
		$scope.role={
				"id":role.id,
				"name":role.name,
				"disc":role.description
		};	
	});
	
	$scope.$on("addRole",function(e,type){
		if(type == null){
			$scope.role={
				"name":"",
				"disc":""
			};
		}
	});
    /*编辑角色*/
	$scope.editRole=function(id)
	{   var subFlag=$scope.formValid.beforeSubmit(); 	
		if(!subFlag){//表单验证失败
			return;
		}
		var roleInfo={
				"request.name":$scope.role.name,
				"request.description":$scope.role.disc
			};
		var editStatus=false;
		//增加角色
		if(id == undefined){
			createRole(roleInfo,function(data){
				if(data.result == "0"){
					editStatus = true;
					$scope.$emit("editRole",editStatus);
				}
			});
		}else{
			//编辑角色
			roleInfo["request.id"]=id;
			updateRole(roleInfo,function(data){
				editStatus = true;
				$scope.$emit("editRole",editStatus);
			})
		}
		$scope.role= {};
		$('#creatModal').modal('hide');
	}
});
/*添加角色用户控制器*/
App.controller('addRoleUsers', function($scope){
	//获取角色id
	var roleDefault="";
	var url=window.location.search;
	var roleId=url.split("=")[1];
	$scope.$on("addRoleUser",function(e,id){
		if(roleId == undefined){
			roleDefault=id;
		}else{
			roleDefault=roleId;
		}
		init(roleDefault);
	});
	
	function init(id){
		var obj={
			"request.role_id":id,
			"request.containRoleId":1,
			"page.pageNum":1,
			"page.pageSize":10
		};
		getRoleUsers(obj,function(data){
		 	if(data.result == "0"){
				$scope.userList=data.responseInfo.lists;
				$scope.$applyAsync($scope.userList);
			}
		 });
	}
	
	
	$scope.addUsers=function (){
		var userChecked=[];
		$("#userBox input[type='checkbox']").each(function(){
			var _self=$(this);
			var checkFlag=_self.prop("checked");
			if(checkFlag == true){
				userChecked.push(_self.val());
			}
		});
		if(userChecked.length==0){
			swal("没有选择用户！");
			return;
		}
		var obj={
				"request.roleId":roleDefault,
				"request.userIds":userChecked,
		};
		var role={
				"id":roleDefault
		}
		addRoleUsers(obj,function(data){
			if(data.result == "0"){
				$('#addUserModal').modal('hide');
				$scope.$emit("addUsers",role);
				swal("用户添加成功！", "", "success");
			}else{
				swal("用户添加失败！");
			}
		});
	};
	 /*全选样式变换*/
	$scope.checkAllUsers=function (event){
		var checkbox=$("#userBox input");
		if(checkbox.length>0){
			if(checkbox.prop("checked") == true){
		    	checkbox.prop("checked", false);
		    }else{
		    	checkbox.prop("checked", true);
		    }
		}
		var checkAll = event.target.id=="che_2"?"che_3":"che_2";
		$("#"+checkAll).prop("checked", !$("#"+checkAll).prop("checked"));
	};
	
});

$(function(){
	/*选择用户列表模态框隐藏的事件*/
	$('#addUserModal').on('hide.bs.modal', function () {
		  //重置全选复选框为不选中状态
		  $("#che_2").prop("checked", false);
		  $("#che_3").prop("checked", false);
	});
});




