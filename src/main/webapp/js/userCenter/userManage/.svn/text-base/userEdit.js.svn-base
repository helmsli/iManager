App.controller('addUser', function($scope){
	//获取编辑用户的id
	$scope.userId = userEdit.getQueryString("userId");
	//判断是新增用户还是编辑用户
	var addFlag = userEdit.getQueryString("addFlag");
	$scope.addFlag = addFlag?(addFlag=="true"?true:false):false;
	 //语言参数
    $scope.lang = window.localStorage.lang||'zh-CN';
	//用户对象模型
	$scope.userInfo = {};
	//动态获取角色列表
	$scope.roleList =[];
	//获取角色列表
	$scope.permissionChecked = true;
	$scope.permissionDisabled = true;
	$scope.permissionTree = [];
		
	/*用户表单渲染完成后，初始化页面数据*/
	$scope.initAddUser  = function(){
		/*日期组件初始化*/
		$('#datetimepicker').datetimepicker({
			 endDate:new Date(),//最大值选今天
	         pickTime:false,//屏蔽小时分秒面板
	    	 collapse: true
	    });
		/*用户信息初始化*/
		$scope.showUserInfo();
		
		angularTreeList.init("powerInfo");
		/*校验格式化*/
		$scope.formValid = new FormValid({"formId":"addorEditUser",formField:validOptions});
	};
	/*显示用户信息*/
	$scope.showUserInfo = function(){
		if($scope.userId!=null&&$scope.userId!=''){//修改用户信息时，去后台调取用户资料
				var requestInfo={"id":$scope.userId};
				getUser(requestInfo,function(data){
					
					if(data.result==0){
						$scope.userInfo = data.responseInfo.entity;
						var date = $scope.userInfo.birthday;
						$scope.userInfo.birthday = new Date(date).pattern("yyyy-MM-dd");
						/* 角色列表初始化*/
						$scope.getRoleList();
					}
				},function(e){});
			}else{
				/* 角色列表初始化*/
				$scope.getRoleList();
			}
	};
	/*获取所有角色列表*/
	$scope.getRoleList = function(){
		getAllRoles({},function(data){
			
			if(data.result==0){
				var roleList = data.responseInfo.roles;
				$scope.roleList = roleList;
				$scope.generateRoleOptions();//生成角色列表
			}
		});
	};
	/*动态生成角色列表select标签的option标签*/
	$scope.generateRoleOptions = function(){
		$("#rolelist").html('');
		$.each($scope.roleList,function (index,item){
			var optstr = "<option value='"+item.id+"'>"+item.name+"</option>";
			$("#rolelist").append(optstr);
		});
		$("#rolelist").selectpicker('refresh');
		$('#rolelist').selectpicker('show');
		$scope.initRoleValue();//初始化角色输入框的值
	};
	/*初始化角色输入框的值*/
	$scope.initRoleValue = function(){
		/*获取角色id集合，渲染权限树*/
		var roles = $scope.userInfo.roles;
		$scope.userInfo.roleIds = [];//新定义roleIds属性，保存时候用
		for(i in roles){
			$scope.userInfo.roleIds.push(roles[i].id);
		}
		$scope.$apply();//需要手动刷新 
		//没有角色的话，默认选择普通用户
		var initVal = $scope.userInfo.roleIds.length>0?
								$scope.userInfo.roleIds:["2"];
		$("#rolelist").selectpicker('val',initVal);//初始化角色输入框
		$("#idType").selectpicker('val',$scope.userInfo.cardtype);//初始化证件类型
		userEdit.queryPermissionTree(initVal);
	}
	/*设置表单验证属性*/
	var validOptions= [];
	if($scope.addFlag){
		validOptions =[{
							"id":"username",
							"validateRule":{"loginNamevalidate":true,"require":true}		
						},{
							"id":"firstname",
							"validateRule":{"numberLetter":true}
						},{
							"id":"password",
							"validateRule":{"require":true,"isPwd":true}
						},{
							"id":"confirmpwd",
							"validateRule":{"require":true,"compared":"password"}
						},{
							"id":"phone",
							"validateRule":{"require":true,"isNumber":true}
						},{
							"id":"email",
							"validateRule":{"isMail":true}
						}];
	}else{
		validOptions = [{
							"id":"username",
							"validateRule":{"loginNamevalidate":true,"require":true}		
						},{
							"id":"firstname",
							"validateRule":{"require":true}
						},{
							"id":"phone",
							"validateRule":{"require":true,"isNumber":true}
						},{
							"id":"email",
							"validateRule":{"isMail":true}
						}];
	}
	
});
var userEdit = {};
/*角色改变监听事件*/
userEdit.roleChangeBind = function(){
	var roleIds = $('#rolelist').val();//获取选中的roleIds
	var addUserScope = getAngularScope("addUser");
    if(roleIds==null){
    	addUserScope.permissionTree = [];
    	addUserScope.$apply();
    	addUserScope.formValid._setCheckErrAndFlag("rolelist");
    }else{
    	userEdit.queryPermissionTree(roleIds);
      	addUserScope.formValid._setCheckSuccAndFlag("rolelist");
    }
}
/*根据角色id查询权限树*/
userEdit.queryPermissionTree = function(roleIds){
	var obj = {
		"roleIds":roleIds
	};
	getRolePermissionTree(obj,function(data){
		if(data.result==0){
			var permissionTree = data.responseInfo.menuFunctions;
			var addUserScope = getAngularScope("addUser");
			addUserScope.permissionTree = permissionTree;
			addUserScope.$apply();
		}
	},function(e){
		
	});
}
/***
 * 保存用户信息
 */
userEdit.saveUserInfo = function(){
	var addUserScope = getAngularScope("addUser");
	var userInfo = addUserScope.userInfo;
	userInfo = userEdit.formateBeforSubmit(userInfo);
	var obj = {
				 "request.username":userInfo.username,
				 "request.firstname":userInfo.firstname,
				 "request.lastname":userInfo.lastname, 
				 "request.address":userInfo.address,
				 "request.sex":userInfo.sex,
				 "request.phone":userInfo.phone,
				 "request.email":userInfo.email,
				 "request.cardtype":userInfo.cardtype,
				 "request.cardno":userInfo.cardno,
				 "request.roleId":userInfo.roleIds
	};
	//"request.birthday":str2date($("#datetimepicker_input").val()),
	if($("#datetimepicker_input").val()!=''){//生日不为空的时候才传送
		obj["request.birthday"] = str2date($("#datetimepicker_input").val());
	}
	
	 var subFlag=addUserScope.formValid.beforeSubmit(); 
	if(subFlag ){//表单验证
		if ( $('#rolelist').val()==null){//验证角色
			$scope.formValid._setCheckSuccAndFlag("rolelist");
			return ;
		}
		//保存用户信息
		if(addUserScope.addFlag){//新增页面
			obj["request.password"] = userInfo.password;//修改页面添加id
			createUser(obj,function(data){
				if(data.result==0){
					swal("保存成功", "", "success");
					userEdit.cancelWin();//关闭窗口
				}else{
					swal(getErrMsg(data.result));
				}
			},function(e){swal("保存失败");});
		}else{//修改页面
			obj["request.id"] = userInfo.id;//修改页面添加id
			updateUser(obj,function(data){
				if(data.result==0){
					swal("保存成功", "", "success");
					userEdit.cancelWin();//关闭窗口
				}else{
					swal(getErrMsg(data.result));
				}
			},function(e){swal("保存失败");});
		}
	}else{
		if ( $('#rolelist').val()==null|| $('#rolelist').val()=="null"){//验证角色
			addUserScope.formValid._setCheckErrAndFlag("rolelist");
			return ;
		}
	}
		
}
/*提交前格式化数据*/
userEdit.formateBeforSubmit = function(userInfo){
	userInfo.birthday = str2date(userInfo.birthday);
	userInfo.roleIds = $('#rolelist').val(); 
	return userInfo;
}
userEdit.cancelWin = function(){
	window.location.replace('userManage.html');
}
userEdit.getQueryString = function (name)
		{
		     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
		     var r = window.location.search.substr(1).match(reg);
		     if(r!=null)return  unescape(r[2]); return null;
		}
