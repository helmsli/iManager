$(function(){
	$("#user_code").on("focus",function(){
		 
		 $("#myTest").html('');
		 $("#user_error").html('');
	});
	$("#user_password").on("focus",function(){
		 $("#myTest").html('');
		 $("#pass_error").html('');
	});
	
});

App.controller('loginCtrl',["$scope",function($scope){
	document.onkeydown=function(event){
	  var e = event || window.event || arguments.callee.caller.arguments[0];
      if(e.keyCode == 13) {// -> 或者go按键 自动提交
    	 var x = document.activeElement.id;
    	 focusNextInput(x);
	  } 
	};
    /*服务器地址*/
 	$scope.server_url = getBasePath();
    /*验证用户名*/
    function validate_user() {
    	var user_err=document.getElementById("user_error");
        if($("#user_code").val().length != 0){
        	user_err.innerHTML="";
            return true;
        }else {
            var errInfo=getErrMsg("user");           
            user_err.innerHTML=errInfo;
            return false;
        }
    }

    /*验证密码*/
    function validate_password() {
    	var pass_err=document.getElementById("pass_error");
        if($("#user_password").val().length != 0){
        	pass_err.innerHTML="";
            return true;
        }else {
            var errInfo=getErrMsg("pass");
            pass_err.innerHTML=errInfo;
            return false;
        }
    }
    function focusNextInput(thisInput){
        var inputs = document.getElementsByTagName("input");
        var len = inputs.length;
        if (thisInput == inputs[len-1].id){
        	$("input").blur();//取消输入框焦点
        	$scope.loginIn();
        } 
    } 
    $scope.validate_user = function () {
        validate_user();
    };

    $scope.validate_password = function () {
        validate_password();
    };
    //标记是否登录中状态，如果为true，表示登录中，为false表示未登录
    $scope.logining = false;
    /*登录*/
    $scope.loginIn = function(){
    	if(!$scope.logining){
    		$scope.toggleLoginIcon("show");
            var userName=$("#user_code").val();
            var password=$("#user_password").val();
            if(validate_user() && validate_password()){
            	//登录前加按钮遮罩？？
                /*此处还需调用后台接口验证：用户名和密码是否匹配*/
                $scope.request = {
					"request.username":userName,
					"request.password":password
				};
				var obj=$scope.request;
				login(obj,function(data){
					
					var result=data.result;
					if(result==0){  
						var user = data.responseInfo.entity;
						
						//获取用户名
						var fullName =user.firstname;
						
						//用户信息对象，保存到cookie中
						var userInfo = {
								fullName:fullName,
								uid:user.id,
								roleIds:user.roleIds//2017-03-08  yxg  用户角色Id
						}
						localStorage.setItem("userInfo",JSON.stringify(userInfo));
						var url = $scope.server_url+"/views/home.html";
					    //跳转到首页
						window.location.href = url;
						$scope.toggleLoginIcon("hide");
					}else{
						//去掉按钮遮罩，显示登录错误码？？？
						var errMsg=data.errorMsg;
						var errInfo=getErrMsg(result);
						/*
						 * 2017-04-10  yxg
						 * document.getElementById("myTest").innerHTML=errInfo;
						 * */
						if(data.result=="2002"){
							document.getElementById("user_error").innerHTML=errInfo;	
						}
						if(data.result=="2003"){
							document.getElementById("pass_error").innerHTML=errInfo;	
						}
						$scope.toggleLoginIcon("hide");
					}
				},function(e){//error callback
					$scope.toggleLoginIcon("hide");
					//去掉按钮遮罩，显示登录错误码？？？
					 console.log(JSON.stringify(e));
				});
            }else{
            	$scope.toggleLoginIcon("hide");
            }
        }
    };//--loginIn-- end
    /*设置登录中样式*/
    $scope.toggleLoginIcon = function(flag){
    	if(flag=='show'){
    		$("#loginBtnText").text('登录中');
        	$("#loginIcon").removeClass("hide");;
        	$scope.logining = true;
    	}else{//隐藏登录中状态
    		$("#loginBtnText").text('登录');
        	$("#loginIcon").addClass("hide");;
        	$scope.logining = false;
    	}
    }
}]);