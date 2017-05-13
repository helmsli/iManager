(function(owner){
	//
	owner.treeData={};//权限数据模型对象
	owner.treeTarget="";//权限html结构渲染的目标dom节点
	owner.disabled = false;
	/***
	 * 初始化权限树状结构
	 * @param {Object} elId 权限树要渲染的目标节点id
	 * @param {Object} treeData 权限树JSON模型
	 */
	owner.init=function(elId,treeData,disabled){
		
		if(typeof disabled ==='undefind'){
			owner.disabled = false;
		}else{
			owner.disabled = disabled;
		}
		owner.treeData=treeData;
		owner.treeTarget=document.getElementById(elId);
		/***
		 * 创建树状列表
		 */
		owner.createTreeList();
		/***
		 * 绑定click事件
		 */
		owner.bindCheckEvent();
	}
		
	/***
	 * 创建树状列表
	 */
	owner.createTreeList=function(){
		var data=owner.treeData;
		/***
		 * 生成权限树HTMLdom节点
		 */
		var myHtml=owner.eachData(data);
		console.log(myHtml);
		/***
		 * 把权限树dom节点插入到目标节点中
		 */
		owner.treeTarget.appendChild(myHtml);
	}
	/***
	 * 生成权限树HTMLdom节点
	 * @param {Object} data 权限树模型
	 */
	owner.eachData=function(data)
	{   
		var dom=document.createDocumentFragment();
		/***
		 * 遍历JSON树第一层，
		 */
		for(var i=0; i<data.length; i++)
		{
			var child=data[i].children;
			var div=document.createElement("div");//外围div
			var item=document.createElement("div");
			div.id="tree_list_"+data[i].id;
			div.className="tree_list_"+i;
			var checkBoxName="tree_list_checkbox_"+data[i].pId;
			var checkBoxId = "tree_list_checkbox_"+data[i].id;
			var pid = "tree_list_checkbox_"+data[i].pId;
			//checkbox的input value值定义为JSON模型的id值，唯一性
			var checkbox='<label class="checkbox-inline">'
			+'<input type="checkbox" class="regular-checkbox" id="'+checkBoxId+'" data-pid="'+pid+'" value="'+data[i].id+'" name="'+checkBoxName+'" ';
			if (owner.disabled){
				checkbox +=' disabled  checked="checked"/>';
			}else{
				checkbox +=' />';
			}
			checkbox +='<label for="'+checkBoxId+'"></label>{{'+data[i].name+'|translate}}</label>';
			item.innerHTML=checkbox;
			div.appendChild(item);
			dom.appendChild(div);
			if(child.length>0)
			{   
				/***
				 * 递归遍历子节点
				 */
				var childHtml=owner.createChild(child);
				div.appendChild(childHtml);//孩子节点放到外围div内部
			}
		}
		return dom;
	}
	/***
	 * 递归遍历子节点。生成树状结构
	 * @param {Object} data 子节点数组
	 */
	owner.createChild=function (data)
	{
		var ul=document.createElement("ul");
		for(var i=0; i<data.length; i++)
		{
			var li=document.createElement("li");
			var checkBoxName="tree_list_checkbox_"+data[i].pId;
			var checkBoxId = "tree_list_checkbox_"+data[i].id;
			var pid = "tree_list_checkbox_"+data[i].pId;
			var checkbox='<label class="checkbox-inline">'
			+'<input type="checkbox" class="regular-checkbox" value="'+data[i].id+'" id="'+checkBoxId+'" data-pid="'+pid+'" name="'+checkBoxName+'" ';
			if (owner.disabled){
				checkbox +=' disabled  checked="checked" />';
			}else{
				checkbox +=' />';
			}
			checkbox +='<label for="'+checkBoxId+'"></label>{{'+data[i].name+'|translate}}</label>';
			
			li.innerHTML=checkbox;
			ul.appendChild(li);
			var children=data[i].children;
				if(children)
				{
					if(children.length>0)
					{   
						/***
						 * 递归遍历孩子节点
						 */
						var newUl=owner.createChild(children,checkBoxName,checkBoxId);
						li.appendChild(newUl);
					}
				}
		}
		return ul;
	}
	


	/***
	 * 委托绑定click事件
	 */
	owner.bindCheckEvent = function(){
		var permissionDom=owner.treeTarget;
		permissionDom.addEventListener("click",function(e){
			var nodeType=e.target.type ;//event的目标节点类型
			var eName=e.target.getAttribute("name");
			var checkId=e.target.id;
			if(nodeType=='checkbox')//是checkbox的点击事件
			{
				var checkFlag=e.target.checked;
				//设置所有父节点的check属性
				owner.setTreeParentsCheck(checkId,checkFlag);
				//设置所有孩子节点的check属性
				owner.setTreeChildrensCheck(checkId,checkFlag);
			}
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
			if(item.type=='checkbox'&& item.getAttribute("data-pid")==id)
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
		var permissionDom=owner.treeTarget;
		var items=permissionDom.getElementsByTagName("input");
		for(var i=0; i<items.length; i++)
		{   var item = items[i];
			//获取选中状态的checkbox的id放到数组中
			if(item.type=='checkbox'&& item.checked &&item.id!=='')
			{
				var originId = item.id.replace("tree_list_checkbox_","");
				result.push(originId);
			}
		}
		return result;
	};
	/***
	 * 根据后台返回的已选权限列表，渲染权限树
	 * @param {Object} permissionList
	 */
	owner.renderTreeChecked = function(permissionList){
		for(var i=0;i<permissionList.length;i++){
			var item = document.getElementById("tree_list_checkbox_"+permissionList[i]);
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
		
	}
	/**
	 * 得到所有父节点
	 * @param {Object} id 当前节点的id
	 */
	owner.getTreeParents = function(id){
		
	};
	/***
	 * 得到所有子孙节点
	 * @param {Object} id 当前节点的id
	 */
	owner.getTreeChildrens = function(id){
		
	};
	/***
	 * 得到所有兄弟节点
	 * @param {Object} id当前节点的id
	 */
	owner.getTreeSiblings = function(id){
	
	};
}(window.treeList={}))
