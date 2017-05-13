//定义默认页面对象，保存页面路径，定义页面初始化方法
var pageList={
	page:[
		{
			partial : "step1.html",//页面路径		
			renderDomId :'step1'
		},
		{
			partial : "step2.html",//页面路径		
			renderDomId :'step2'
		},
		{
			partial : "step3.html",//页面路径		
			renderDomId :'step3'
		},
		{
			partial : "step4.html",//页面路径		
			renderDomId :'step4'
		},
		{
			partial : "step5.html",//页面路径		
			renderDomId :'step5'
		},
		{
			partial : "step6.html",//页面路径		
			renderDomId :'step6'
		}
],

	notfound:{
			partial : "template/404.html",//页面路径		
			renderDomId :'renderDomId'
		}
	
	
}


/*
home.init = function(){   //初始化方法
     console.log('嵌入到demo中了！');
}*/


var miniSPA = {};
/***
 * onhashchange事件方法
 * window[url].partial获取html的url
 * ajaxRequest获取html片段，嵌入到divDemo中
 */
miniSPA.changeUrl = function(type) { 

    var url = location.hash.replace('#','');
    if(url === ''){
        //url = 'home';//default page
       url = miniSPA.queryActiveHash(type);//查找默认页面
    }
    
    /*if(!window[url]){
        url = "notfound";
    }
    */
    var newURL=getNewURL(url)
    
    miniSPA.hideAllContent();//隐藏所有的div容器
    
    var divDemo = document.getElementById(newURL.renderDomId);
    var hasChild = /[<>]/.test(divDemo.innerHTML);//正则判断是否含子节点
   // divDemo.className = "content";//显示容器
   	var pagePath=basePath+"/WEB-INF/Manager/step/"
   newURL.partial=pagePath+newURL.partial;
  
    if (!hasChild){//容器不存在子节点，表示没有加载过，去加载模板
    	miniSPA.ajaxRequest(newURL.partial, 'GET', '',function(status, page){
	       		divDemo.innerHTML = page;
	       		divDemo.className="row export export-template show"
	            //miniSPA.initFunc(url);  //加载页面的初始化方法
        });
    }
}
/**
 * 和后端的服务进行交互，通过XMLHttpRequest发送请求（GET或POST），
 * @param {Object} url
 * @param {Object} method
 * @param {Object} data
 * @param {Object} callback
 */
miniSPA.ajaxRequest = function(url, method, data, callback) {    //load partial page
    var xmlhttp;
    if(window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest();
        xmlhttp.open(method, url, true);
        if(method === 'POST'){
            xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
        }
        xmlhttp.send(data);
        xmlhttp.onreadystatechange = function(){
            if(xmlhttp.readyState == 4){
                switch(xmlhttp.status) {
                    case 404:                             //if the url is invalid, show the 404 page
                        url = 'notfound';
                        break;
                    default:
                    break;
                }
                callback(xmlhttp.status, xmlhttp.responseText);
            }
        }
    }
    else{
        alert('Sorry, your browser is too old to run this app.')
        callback(404, {});
    }
}
/***
 * 解析片段对应的初始化方法，
 * 判断其类型是否为函数，并执行它。
 * @param {Object} partial 
 */
miniSPA.initFunc = function(partial) { 
    var fn = window[partial].init;
    if(typeof fn === 'function') {
        fn();
    }
}
/***
 * 获取当前激活的路由
 */
miniSPA.queryActiveHash = function(urlTy){
	
	var menus = document.getElementById("step_role");
	
	var li=menus.getElementsByTagName("li");
	for(var i = 0;i<li.length;i++){
		var menu = li[i];//某一个菜单a标签
		var menuHref=menu.getAttribute("data-active");
		if (menuHref)
		{
		    var href = menu.getAttribute("href");
		    return href.replace("#",""); 	
		}
	}
}

miniSPA.queryActiveHash = function(){
	
	var menus = document.getElementById("step_role");
	var li=menus.getElementsByTagName("li");
	for(var i = 0;i<li.length;i++){
		var menu = li[i];//某一个菜单a标签
		var menuHref=menu.getAttribute("data-active");
		if (menuHref)
		{
		    var href = menu.getAttribute("href");
		    return href.replace("#",""); 	
		}
	/*	addEvent(menu,"click",function(){
			
			alert(this.href);
		})*/
		
	}
}

/***
 * 隐藏所有内容区域
 */
miniSPA.hideAllContent = function(){
	var contents = document.querySelectorAll(".content div");
	for(var i = 0;i<contents.length;i++){
		contents[i].className = "row export export-type hide";
	}
}
/***
 * 初始化页面时home页面
 */
function getNewURL(pageName)
{
	var page=pageList.page;
	var returnPage=null;
	for(var i=0;i<page.length; i++)
	{
		if(page[i].partial==pageName)
		{
			returnPage=page[i]; 
			break;
			
		}
	}
	return returnPage;
}
