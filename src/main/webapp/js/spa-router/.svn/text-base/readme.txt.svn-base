###基础对象
首先是定义缺省的两个页面片段（缺省页面和出错页面，这两个页面是基础功能，所以放在库里）相关代码，对每个片段对应的url（例如 `home`）定义一个同名的对象，里面存放了对应的 html 片段文件路径、初始化方法。
###################javascript
var home = {};           
home.partial = "template/home.html";//页面路径
home.init = function(){   //初始化方法
    alert('嵌入到demo中了！');
}
var notfound = {}; //404 page
notfound.partial = "template/404.html";
notfound.init = function(){
    alert('URL does not exist. please check your code. You may also try manually inputing some other invalid url to get here.');
}
##################

随后是全局变量，包含局部刷新所在 div 的 DOM 对象：
###############javascript
var settings = {};
settings.divDemo = document.getElementById("demo");//加载片段的div容器

###################

###主程序
下面就是主程序了，所有的公用方法打包放到一个对象 `miniSPA` 中，这样可以避免污染命名空间：
##############javascript
// Main Object here
var miniSPA = {};
#################

然后是 changeUrl 方法，对应在 `index.html` 中有如下触发定义：
####################html
<body onhashchange="miniSPA.changeUrl();">
#####################

`onhashchange` 是在location.hash发生改变的时候触发的事件，能够通过它获取局部 url 的改变。在 `index.html` 中定义了如下的链接：
#############html
	<h1> Demo Contents:</h1>
    <a href="#home">Home (Default)</a>
    <a href="#wrong">Invalid url</a>
    <div id="demo"></div>
#############

每个 url 都以 `#` 号开头，这样就能被 `onhashchange` 事件抓取到。最后的 div 就是局部刷新的 html 片段嵌入的位置。
#################javascript
miniSPA.changeUrl = function() { 
    var url = location.hash.replace('#','');
    if(url === ''){
        url = 'home';//default page
    }
    if(! window[url]){
        url = "notfound";
    }
    miniSPA.ajaxRequest(window[url].partial, 'GET', '',function(status, page){
        if(status == 404){
            url = 'notfound';       //404页面
            miniSPA.ajaxRequest(window[url].partial,'GET','',function(status, page404){
                settings.divDemo.innerHTML = page404;
                miniSPA.initFunc(url); //加载404页面的初始化方法
            });
        }
        else{
            settings.divDemo.innerHTML = page;
            miniSPA.initFunc(url);  //加载页面的初始化方法
        }
    });
}
```
上面的代码先获取改变后的 url，先通过 `window[url]` 找到对应的对象（类似于最上部定义的 `home` 和 `notfound`），如对象不存在（无定义的路径）则转到 `404` 处理，否则通过 `ajaxRequest` 方法获取 `window[url].partial` 中定义的 html 片段并加载到局部刷新的div，并执行 `window[url].init` 初始化方法。

`ajaxRequest` 方法主要是和后端的服务进行交互，通过 `XMLHttpRequest` 发送请求（ `GET` 或 `POST`）
#####################javascript
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

`initFunc` 方法的作用是解析片段对应的初始化方法，判断其类型是否为函数，并执行它。这个方法是在 `changeUrl` 方法里调用的，每次访问路径的变化都会触发相应的初始化方法。
##############javascript
miniSPA.initFunc = function(partial) { 
    var fn = window[partial].init;
    if(typeof fn === 'function') {
        fn();
    }
}
####################
最后是 `miniSPA` 库自身的初始化，自动执行miniSPA.changeUrl方法，默认显示home页面
miniSPA.changeUrl();