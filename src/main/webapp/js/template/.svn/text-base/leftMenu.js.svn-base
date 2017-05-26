/***/

(function($,owner){
	

	
			//节点点击事件，加载右边基站列表
			
	owner.leftMenu=function(menuName, elId,type,callBack) {
	var setting = {
				view: {
					selectedMulti: false
				},
				edit: {
					editNameSelectAll: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick : callBack
				}};
	zNode = owner.getMenuData(menuName);
	if(type=='tree')
	{
		$.fn.zTree.init($("#" + elId), setting, zNode);
		this.setTreeMenuSelector();
	}else{
		owner.initMenu(zNode,elId);
	};
};
/**
 * 
 * 设置选中项;
 * return 菜单树Data;
 * **/
	owner.setTreeMenuSelector=function()
	{
		$("#tree a").eq(0).addClass("curSelectedNode");
	}

/**
 * 
 * 获取menuData;
 * return 菜单树Data;
 * **/
owner.getMenuData=function(menuName) {
	var zNode = null;
	switch (menuName) {
			case "config":
				zNode = configMenu;
			break;
			case "network":
				zNode = networkTree;
			break;
			case "roleList":
			    /*getRoleList("",function(data){
			    	zNode = data;
			    });*/
				zNode = roleData.roleList;
			break;
			case "treeData":
			    zNode = treeData;
			break;
	}
	//console.log(JSON.stringify(zNode));
	return zNode;
}


/**
 * 
 *初始化菜单
 * 参数：data :menuData,elId=ELEMENTid;
 * **/
owner.initMenu=function(data,elId)
{
	var menuHtml = owner.queryChild(data);
	if (typeof (menuHtml) == "object") {
		document.getElementById(elId).appendChild(menuHtml);
	};
};

/**
 * 
 *生成菜单子项DOM
 * 参数：data :menuData
 * 返回：list domElement
 * **/

owner.queryChild=function(data) {
	var leftMenu=document.createDocumentFragment();
	var wrap=document.createElement("div");
	wrap.className="content";
	var ul =document.createElement("ul");
	leftMenu.appendChild(wrap);
	wrap.appendChild(ul);
	for(var i=0; i<data.length; i++)
	{
		var li=document.createElement("li");
		li.id="swich+"+i;
		var parentTiltle=document.createElement("a");
		var myClass="icon_plus";
		if(i===0)
		{
			myClass="icon_minus";
		}
		parentTiltle.innerHTML='<i class="icon_menu"></i>'+data[i].name+'<i class="'+myClass+'"></i>';
		var child=data[i].children;
		ul.appendChild(li);
		li.appendChild(parentTiltle);
		if(child.length>0)
		{
			//
			var div=document.createElement("div");
			if(i==0){
				div.style.display="block";
			}
			for(var k=0; k<child.length; k++)
			{
				var childA=document.createElement("a");
				childA.href=child[k].url;
				childA.target=child[k].target;;
				childA.innerHTML=child[k].name;
				div.appendChild(childA);
			}
			li.appendChild(div);
		}
	};
	return leftMenu;
};


/** ******设置leftMenu打开方式******* */
function linkMenu() {
	var basePath = document.getElementById("basePath").value;
	$("#left_nav a").live("click", function(event) {
		event.preventDefault();
		var id = $(this).attr("id");
		var loadUrl = $(this).attr("href");
		if (typeof(loadUrl) != "undefined") {
			var loadId = "div_" + id;
			var hasNum = $(".mainFrame").children("#" + id).size();
			var fh = $(".mainFrame").height();
			if (hasNum < 1) {
				var div = document.createElement("div");
				var frame = document.createElement("iframe");
				var mv = $(window);
				// console.log(mv.width()+"=="+mv.height());
				div.id = "div_" + id;
				frame.id = loadId;
				frame.src ="/" + loadUrl;
				frame.name = loadId;
				frame.scrolling = "auto";
				frame.marginwidth = "0";
				frame.align = "top";
				frame.style.height = mv.height() - 114 + "px";
				frame.frameBorder = "0";
				div.appendChild(frame);
				var pageFrame = document.getElementById("pageFrame");
				$("#pageFrame").children("div").remove();
				pageFrame.appendChild(div);
			}
		}
	});
}
	
}(jQuery,window.menu={}))
