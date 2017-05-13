var userManage = {};

/***
 * 打开编辑框
 * @param {Object} userId
 */
userManage.openEditWin = function(userId){
	if(typeof userId ==='undefined'){
		userId = "";
		window.location.href = "userManage_edit.html?userId="+userId+"&addFlag=true";
	}else{
		window.location.href = "userManage_edit.html?userId="+userId+"&addFlag=false";
	}    
}

userManage.openCheckWin = function (uId){
	window.location.href = "userManage_check.html?userId="+uId;
}
/***
 * 删除用户
 * @param {Object} user
 */
userManage.deleteUser = function(userIds,callback){
	var obj = {"request.id":userIds};
	//后台删除用户
	deleteUser(obj,function(data){
		if (data.result==0){
			callback();
		
			swal("删除成功", "", "success");
		}else{
			swal(getErrMsg(data.result));
		}
	},function(e){
		swal("删除失败");
	});
	
};
/**
 * 查询用户列表
 * @param {Object} obj
 * @param {Object} callback
 */
userManage.queryUserList = function(obj,callback){
	//调用server方法，查询数据
	getUserList(obj,function(data){
		if (data.result==0){
			callback(data);
		}else{
			//alert('用户列表查询失败');
		}

	},function(e){
		//alert('用户列表查询失败');
	});
}
