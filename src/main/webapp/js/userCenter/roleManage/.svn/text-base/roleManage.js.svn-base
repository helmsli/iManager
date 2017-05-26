
$(function(){
	
	$("a.curSelectedNode").click();
});
var roleManage = {};
roleManage.roleId = "";//选中的角色Id
roleManage.savePermission = function(){//保存角色-权限的方法
	var arr = treeList.getAllChecked();
	if (roleManage.roleId!==''){
		/*var obj = {
			"request.roleId":roleManage.roleId,
			"request.permissionList":arr
		}
		saveRolePermission(obj,function(){
			alert('保存成功');
		});*/
		role_permission[roleManage.roleId] = arr;
		roleManage.showAlert('保存成功','smallModal');
	}else{
		roleManage.showAlert('请选择一个角色','smallModal');
	}
	
};
roleManage.showPermission = function(roleId){//显示角色-权限的方法
	treeList.clearTree();//清除上次已选
	roleManage.roleId = roleId;
	var permissionList = role_permission[roleId];
	if (typeof permissionList!=='undefined'
			&&permissionList.length>0){
		treeList.renderTreeChecked(permissionList);		
	}

};
roleManage.addRole = function(role){
	role.id = (new Date()).getTime();
	roleData.roleList.push(role);
	return roleData.roleList;
};
roleManage.showAlert = function(msg,modelId){
	
    $('#'+modelId+" p").html(msg);
	$('#'+modelId).modal('show');
	
};
roleManage.hideAlert = function(modelId){
	$('#'+modelId+" p").html('');
	$('#'+modelId).modal('hide');
};
roleManage.openEditWin=function (role) {
	window.location.href = "roleAuthority_edit.html";
};
roleManage.goBackWin=function (role) {
	window.location.href = "roleManage.html";
};