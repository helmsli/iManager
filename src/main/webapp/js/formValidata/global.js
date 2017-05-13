//事件绑定
function addEvent(obj,type,handle)
{
   	try{
        obj.addEventListener(type,handle,false);
    }catch(e){
        try{  // IE8.0及其以下版本
            obj.attachEvent('on' + type,handle);
        }catch(e){  // 早期浏览器
        	obj&&(obj['on' + type] = handle);
        }
    }
}

//取消事件绑定
function removeEvent(o,e,f){
  if(window.detachEvent)
  {
  	o.detachEvent("on"+e,f);
  }else if(window.removeEventListener)
  {
  	o.removeEventListener(e,f,false);
  }
  else{
  	o["on"+e]=null;
  }
}



//是否空对象
function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
}