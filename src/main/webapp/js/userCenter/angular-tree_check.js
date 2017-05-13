(function(owner){
	//
	owner.treeTarget="";//权限html结构渲染的目标dom节点
	/***
	 * 初始化权限树状结构
	 * @param {Object} elId 权限树要渲染的目标节点id
	 * @param {Object} treeData 权限树JSON模型
	 */
	owner.init=function(elId){
		owner.treeTarget=document.getElementById(elId);
		/***
		 * 绑定click事件
		 */
		owner.bindCheckEvent(elId);
	}
	/***
	 * 委托绑定click事件
	 */
	owner.bindCheckEvent = function(elId){
		$("#"+elId).delegate("input[type='checkbox']","click", function (e) {
			    var checkId=e.target.id; 
				var checkFlag=e.target.checked;
				//设置所有父节点的check属性
				owner.setTreeParentsCheck(checkId,checkFlag);
				//设置所有孩子节点的check属性
				owner.setTreeChildrensCheck(checkId,checkFlag);
		});
	};
	/***
	 * 设置所有父级节点的check属性
	 * @param {Object} id 当前节点id
	 * @param {Object} checkFlag check属性 true 选中，false反选
	 */
	owner.setTreeParentsCheck = function(id,checkFlag){
		var currentDom = document.getElementById(id);
		var pid = currentDom.getAttribute("data-pid");
		var parentDom = document.getElementById(pid);
		if(pid &&parentDom){//父节点存在
			if(checkFlag){//选中	
				parentDom.checked = true;
				owner.setTreeParentsCheck(pid,checkFlag);//递归父节点
			}else{//反选
				//每个父节点的孩子节点若都没被选中，则改父节点也反选
				var childrenChecked  = owner.getTreeChildrensChecked(pid);
				if (!childrenChecked){//孩子节点都没有被选中
				    parentDom.checked = false;
				    owner.setTreeParentsCheck(pid,checkFlag);//递归父节点			
				}
			}
		}
	};
	/***
	 * 设置所有子节点的check属性
	 * @param {Object} id 当前节点id
	 * @param {Object} checkFlag check属性 true 选中，false反选
	 */
	owner.setTreeChildrensCheck = function(id,checkFlag){
		var permissionDom=owner.treeTarget;
		var items=permissionDom.getElementsByTagName("input");
		for(var i=0; i<items.length; i++)
		{   var item = items[i];
			if(item.type=='checkbox'&& item.getAttribute("data-pid")===id)
			{  
				item.checked = checkFlag;
				owner.setTreeChildrensCheck(item.id,checkFlag);//递归
			}
		
		}
	}
	/***
	 * 判断当前节点的孩子节点是否有被选中的，
	 * @param {Object} id 当前节点的id
	 * return true 存在；false 不存在
	 */
	owner.getTreeChildrensChecked = function(id){
		var permissionDom=owner.treeTarget;
		var items=permissionDom.getElementsByTagName("input");
		for(var i=0; i<items.length; i++)
		{   var item = items[i];
			if(item.type=='checkbox'&& item.getAttribute("data-pid")==id)
			{
				if(item.checked){//存在选中的子节点，返回true
					return true;
				}
			}
		}
		return false;
	};
	/***
	 * 获取权限树种所有被选中的checkbox的id值
	 */
	owner.getAllChecked = function(){
		var result = [];
		var menus = [];
		var functions = [];
		var permissionDom=owner.treeTarget;
		var items=permissionDom.getElementsByTagName("input");
		for(var i=0; i<items.length; i++)
		{   var item = items[i];
			//获取选中状态的checkbox的id放到数组中
			if(item.type=='checkbox'&& item.checked &&item.id!=='')
			{   
				var id = item.getAttribute("data-pmid");
				var type = item.getAttribute("data-pmtype");
				if (type==0){//菜单
					menus.push(id);
				}else if(type==1){//按钮
					functions.push(id);
				}
			
			}
		}
		result.push(menus);
		result.push(functions);
		return result;
	};
	/***
	 * 根据后台返回的已选权限列表，渲染权限树
	 * @param {Object} permissionList[{id:1,type:2}]
	 */
	owner.renderTreeChecked = function(permissionList){
		for(var i=0;i<permissionList.length;i++){
			var item = document.getElementById("tree_list_"+permissionList[i].resource_type+"_"+permissionList[i].resource_id);
		    item.checked = true;
			
		}
	};
	/**
	 * 清除所有已选的
	 */
	owner.clearTree = function(){
		if(owner.treeTarget!==""){
			var permissionDom=owner.treeTarget;
			var items =permissionDom.getElementsByTagName("input");
			for(var i=0; i<items.length; i++)
			{   var item = items[i];
				//获取选中状态的checkbox的id放到数组中
				if(item.type=='checkbox')
				{
					item.checked = false;
				}
			}
		}	
	};
}(window.angularTreeList={}))
