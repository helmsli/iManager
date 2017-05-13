App.controller('approvalUser', function($scope){
	//获取编辑用户的id
	$scope.userId = userEdit.getQueryString("userId");
	
	$scope.companyInfoDetails=false;
	getUserInfo();
	
	function getUserInfo(){
		var obj={
				"request.userId":$scope.userId
		}
		getRigisterUserInfo(obj,function(data){
			console.log(data);
			$scope.userInfo=data.responseInfo.user;
			$scope.companyInfo=data.responseInfo.company;
			if($scope.companyInfo == undefined){
				$scope.companyInfoDetails=false;
			}else{
				$scope.companyInfoDetails=true;
				$scope.companyId=$scope.companyInfo.companyId;
			}
			
		},function(e){
			console.log(e);
		})
	}
	
	$scope.approvalUserPass=function(companyId){
		approvalUserByServer($scope.userId,companyId,"pass");
	}
	
	$scope.approvalUserNo=function(companyId){
		approvalUserByServer($scope.userId,companyId,"reject");
	}
});

function approvalUserByServer(userId,companyId,status){
	var obj={
			"request.userId":userId,
			"request.companyId":companyId, 
			"request.approvalResult":status
			};
	console.log(obj);
	approvalUser(obj,function(data){
		console.log(data);
		if(data.result == 0){
			swal("保存成功", "", "success");
			//window.history.go(-1);
		}else{
			swal(getErrMsg(data.result));
		}	
	},function(e){});
}