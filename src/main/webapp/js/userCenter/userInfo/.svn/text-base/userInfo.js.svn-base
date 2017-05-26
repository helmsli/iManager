/*定义module*/
App.controller('app', ['$scope','$ocLazyLoad', function($scope, $ocLazyLoad){
    $scope.uid = '';
	//用户数据
	$scope.userInfo = {};
    //页面是否编辑
    $scope.edit = false;
    //语言参数
    $scope.lang = window.localStorage.lang||'zh-CN';
	$scope.loadBootstrap = function(){
		var myFileList=[];
		myFileList=loadFileList(myFileList);
        $ocLazyLoad.load(myFileList);
    };
    $scope.formValid = new FormValid({           	
		"formId":"userInfoForm",
		"formField":[{
			"id":"loginname",
			"validateRule":{"require":true,"maxLength":20,"minLength":3}					
		},{
			"id":"firstname",
			"validateRule":{"numberLetter":true}
		},{
			"id":"phone",
			"validateRule":{"require":true,"isNumber":true}
		},{
			"id":"email",
			"validateRule":{"isMail":true}
		}]
	});
     $scope.loadBootstrap();
    /*请求后台获取用户信息*/
    $scope.getUserInfo = function(){
    	 $scope.edit = false;
    	var session_userInfo = localStorage.getItem("userInfo");
    	var userInfo = JSON.parse(session_userInfo);
    	var uid = userInfo?userInfo.uid:'';
    	if (uid!=''){
    		$scope.uid = uid;
    		var requestInfo={"id":uid};
    		getUser(requestInfo,function(data){
    			if(data.result==0){
    				$scope.userInfo = data.responseInfo.entity;
    				var date = $scope.userInfo.birthday;
    				$scope.userInfo.birthday = new Date(date).pattern("yyyy-MM-dd");
    				$("#idType").selectpicker('val',$scope.userInfo.cardtype);//初始化证件类型
    				var roles = $scope.userInfo.roles;
    				$scope.userInfo.roleIds = [];//新定义roleIds属性，保存时候用
    				for(role in roles){
    					$scope.userInfo.roleIds.push(role.id);
    				}
    				$scope.$applyAsync($scope.userInfo);
    			}
    		},function(e){});
    	}else{
    		swal("无法获取用户id");
    	}
    }
    //触发编辑
	$scope.editUserInfo = function(){
		 $scope.edit = true;
	};
	//触发保存
	$scope.saveUserInfo = function(){
		 var userInfo = $scope.userInfo;
		 var subFlag=$scope.formValid.beforeSubmit(); 	
   		 if(subFlag==true){//本地校验成功
	   		 //后台保存
   			 var obj = {
   					 "request.id":userInfo.id,
   					 "request.username ":userInfo.username,
   					 "request.firstname":userInfo.firstname,
   					 "request.lastname":userInfo.lastname,
	   				 "request.birthday":str2date($("#datetimepicker_input").val()),
	   				 "request.address":userInfo.address,
	   				 "request.sex":userInfo.sex,
   					 "request.phone":userInfo.phone,
   					 "request.email":userInfo.email,
   					 "request.cardtype":userInfo.cardtype,
   					 "request.cardno":userInfo.cardno,
   					 "request.roleId":userInfo.roleIds
   			 }; 
   			updateSelf(obj,function(data){
	   			 if(data.result==0){
	   				 $scope.edit = false;
	   				 $scope.$applyAsync($scope.edit);
	   				 //重置
	   				 $scope.getUserInfo();
	   				swal("保存成功", "", "success");
	   				$scope.resetLocalStorageUserInfo(userInfo);
	   			 }else{
	   				swal("保存失败");
	   			 }
	   		 },function(e){
	   			
	   			swal("保存失败");
	   		 });
   		 }else{
   			 
   		 } 
	};
	//触发取消
	$scope.cancel = function(){
		
		 var myErr = $scope.formValid.ErrList;
		 for(var key in myErr)  //ErrList为空的就添加错误信息
			{
			 $scope.formValid.setErrInfo(key,"hidden"); 
			}
		 $scope.edit = false;
		 $scope.$applyAsync($scope.edit);
		 //重置
		 $scope.getUserInfo();
	};
	/*重置用户名基础信息*/
	$scope.resetLocalStorageUserInfo =function(user){
		var fullName =user.firstname;
		//用户信息对象，保存到cookie中
		var userInfo = {
				fullName:fullName,
				uid:user.id
		}
		//保存到sessionstoragezhong，有效期1天
		localStorage.setItem("userInfo",JSON.stringify(userInfo));
		var headScope = getAngularScope("head_ms");
		headScope.getUserName();
		headScope.$apply();
	}
}]);
$(function() {
	 $('#idType').selectpicker();
     $('#datetimepicker').datetimepicker({
    	 endDate:new Date(),//最大值选今天
         pickTime:false,//屏蔽小时分秒面板
    	 collapse: true
    });
});