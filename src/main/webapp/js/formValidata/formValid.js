
/***
 *@ shangyine
 *@ date 20170220 
 *@version:0.0.2
 *@desction:修正循环校验的逻辑处理
 * 定义组件
 * @param options
 * 
 */
function FormValid(options) {
	
	this.options=options;
	this.ErrList={};
	this.blurList=0;
	
	this.initParm();
};


/*******
 * 
 * 初始化参数
 * @description 获取参数，如果存在绑定事件
 * 
 */
FormValid.prototype.initParm=function(){
    
	var options=this.options;
	var formId=options.formId;
	var formElement=options.formField;	
	
	
	for(var i=0;i<formElement.length;i++){

        var elTip=formElement[i].tip;
		if(formElement[i].id){  //如果元素id存在
			var formObj=formElement[i];
			var domObj=this.getDomObj(formObj.id);  //获取dom对象
			if(domObj){   //如果dom对象存在
				this.addEl(formObj.id);	
				this.initEvent(elTip,domObj,formObj.validateRule,formObj.ajaxValid);
			    
			}
		}else{
			console.log("未定义id");
		}
	}
}


/***
 * 
 * 获取 Dom元素
 * @param domId
 * @return myDom
 * 
 */
FormValid.prototype.getDomObj=function(domId){
	var myDom=document.getElementById(domId);
	if(myDom!=""){
		return myDom;
	}else{
		console.log("domError: check your formId");
	}
}


/***
 * 
 * 设置元素隐藏
 * @param dom
 * 
 */
FormValid.prototype.setDomHide=function(dom){
	dom.style.display="none";
}


/***
 * 
 * 设置元素可见
 * @param dom
 * 
 */
FormValid.prototype.setDomVisible=function(dom){
	dom.style.display="block";
}


/****
 * 
 * 初始化事件
 * @param  filed,rule,ajax
 * @description 给元素绑定blur和focus事件
 * 
 */
FormValid.prototype.initEvent=function(elTip,filed,rule,ajax){


   	    var _this=this;   	    
   	    var tipInf=_this.initTip(filed.id);
   	    //默认隐藏提示信息
   	    _this.setDomHide(tipInf);
   	    
   	    //默认隐藏错误信息
   	    _this.initErr(filed.id);
   	    _this._setCheckSuccAndFlag(filed.id);
   	      	      	    
		addEvent(filed,"blur",function(e){
			
			_this.setDomHide(tipInf);
			//记录下元素失焦校验过的次数,提交校验时会用到
			_this.blurList++;
		    _this.checkForm(filed,rule,ajax);
	    })
			
		addEvent(filed,"focus",function(e){
			_this._setCheckSuccAndFlag(filed.id);
			_this.setDomVisible(tipInf);
			var myTipInf=_this.getTipAttr(filed);  //获取页面定义的data-tip属性值
			if(myTipInf!="" && myTipInf)
			{
				tipInf.innerHTML=myTipInf;
			}else{	
				if(elTip==false){
					_this.setDomHide(tipInf);
				}else{
					var tipKey=_this.getErrKey(filed.id);
					tipInf.innerHTML=formValid_tipList[tipKey];
				}
				
			}         
	    })					
}


/*****
 * 
 * 失焦和提交时的校验函数
 * @param  myDom,myRule,myAjax
 * @description 校验校验规则和ajax是否通过，未通过显示错误信息
 * @return boolen
 */
FormValid.prototype.checkForm=function(myDom,myRule,myAjax){
	
	//console.log(myDom+"---"+JSON.stringify(myRule));
	var id=myDom.id;
	var myValue=myDom.value;
	
	//如果元素需要校验的规则中存在maxLength和minLength
	if(myRule.hasOwnProperty("maxLength") || myRule.hasOwnProperty("minLength"))
	{
		//长度校验
		var checkLenFlag=this.checkStrLen(myRule,myValue);
		if(checkLenFlag)
		{
			//校验成功，删除错误信息
			this._setCheckSuccAndFlag(id);
			
		}else{
			//校验失败，添加错误信息
			this._setCheckErrAndFlag(id);
		}
		return checkLenFlag;
		
	}else if(myRule.hasOwnProperty("compared")){
		var checkPasFlag=this.checkPasFlag(myRule,myValue);
		if(checkPasFlag)
		{
			//校验成功，删除错误信息
			this._setCheckSuccAndFlag(id);
			
		}else{
			//校验失败，添加错误信息
			this._setCheckErrAndFlag(id);
		}
		return checkPasFlag;
	}else{
		//否则判断元素需要校验的规则是否存在于校验规则中
			var checkFlag=true;
			
			//必填
			if(formValid_ruleList.require)
			{
				checkFlag=this.checkAllItem(myRule,formValid_ruleList,myValue,id,myAjax);
				
			}
			
			//非必填
			if(!formValid_ruleList.require)
			{
				if(myValue!="")
				{
					checkFlag=this.checkAllItem(myRule,formValid_ruleList,myValue,id,myAjax);
				}
				
			}
			if(!checkFlag)
			{
				this._setCheckErrAndFlag(id);
			}
			//console.log(id+"--"+myValue+"---"+JSON.stringify(myRule));
			
			return checkFlag;
	}
}

FormValid.prototype.checkAllItem=function (myRule,formValid_ruleList,myValue,id,myAjax)
{
	var checkFlag=true;
	for( key in myRule)
	{     
		if(formValid_ruleList.hasOwnProperty(key))
		{
			//如果存在，校验并返回boolen
				checkFlag=this.checkMyValue(formValid_ruleList[key],myValue);
				if(checkFlag)
				{
					//ajax校验
					checkFlag=this._checkAjaxResult(myAjax,id);
				}else{
					break;
				}
		}
	}
	return checkFlag;
}


/****
 * 
 * 如果存在ajaxValidate。校验ajax，并处理结果
 * @param  myAjax,id
 * @return boolen
 * 
 */
FormValid.prototype._checkAjaxResult=function(myAjax,id)
{
	var checkFlag=true;
	if(!myAjax)  //如果未定义ajaxValid
	{
		this._setCheckSuccAndFlag(id);
	}else{
		if(this.checkAjax(myAjax)){    //如果ajax校验成功
		    this._setCheckSuccAndFlag(id);
		}else{
			checkFlag=false;
	        this._setCheckErrAndFlag(id);
		}
	}
	return checkFlag;
}


/****
 * 
 * 设置错误信息
 * @param id
 * 
 */
FormValid.prototype._setCheckErrAndFlag=function(id)
{
	//错误处理
	this.ErrList[id]=false;				
	//设置错误信息显示
	this.setErrInfo(id,"show");
}


/****
 * 
 * 设置成功时的信息
 * @param id
 * 
 */
FormValid.prototype._setCheckSuccAndFlag=function(id)
{
	this.setErrInfo(id,"hide");
    delete this.ErrList[id];    
}
/****
 * 
 * 返回校验规则结果
 * @param rule,val
 * @return boolen
 * 
 */
FormValid.prototype.checkMyValue=function(rule,val)
{
	return(rule.test(val));		
}


/***
 * 校验输入长度
 * @param  rule,val
 * @return boolen
 */
FormValid.prototype.checkStrLen=function(rule,val)
{
	var min=rule.minLength;
	var max=rule.maxLength;
	var regLen=new RegExp("^\\S{"+min+","+max+"}$");
	return(regLen.test(val));
}



FormValid.prototype.checkPasFlag=function(rule,val)
{
	var com=rule.compared;
	var myDom=this.getDomObj(com);
	//alert(val+">>>"+myDom.value);
	var checkFlag;
	if(val==myDom.value){
		checkFlag = true;
	}else{
		checkFlag = false;
	}
	return checkFlag;
}



/****
 * 校验ajax
 * @param myAjax
 * @return boolen
 */
FormValid.prototype.checkAjax=function(myAjax){
	myAjax.async=false;
	
	var checkFlag;
	this.getAjaxData(myAjax,function(data,ajaxcallBack){
		
	var myResult=ajaxcallBack(data);    //2，拿到ajaxValid里面回调函数的结果
	checkFlag=myResult;
		
	});
	
	return checkFlag;   // 4，最后返回请求结果
}


/****
 * 获取ajax data,发送请求
 * @param  obj,call
 * 
 */
FormValid.prototype.getAjaxData=function(obj,call)
{

	obj.callBack=function(data)
	{
		call(data,obj.ajaxCallBack);  //1，obj.ajaxCallBack是拿到的ajaxValid里面的回调函数
	}
		
	requestAjax(obj);   //3，在getAjaxData()里拿到返回结果后，发送请求
}


/*
 * 获取提示和错误信息dom元素
 */
FormValid.prototype.getTipDiv=function(objId){
	//var myTipDiv=this.addEl(objId);
	
	var myDom=this.getDomObj(objId);
	var myTip;
	if(myDom.type=="button"){
		///
		myTip=myDom.parentNode.parentNode.childNodes;
	}else{
		
		myTip=myDom.parentElement.parentElement.nextElementSibling;
		return myTip;
	}
	
}


/***
 * 
 * 动态添加提示信息和错误信息的div
 * @param objId
 * @return myTip元素;
 * 
 */
FormValid.prototype.addEl=function(objId){
	var myTip=document.createElement("div");
	myTip.className="input-tips";
	var dom=this.getDomObj(objId);	
	if(dom.type=="button"){
		/////
		dom.parentElement.parentElement.appendChild(myTip);
	}else{
		dom.parentElement.parentElement.appendChild(myTip);
	}	
	
	//return myTip;
}


/**
 * 初始化提示信息
 * @param objId
 * @description 生成提示信息元素
 * @return tip元素;
 * */
FormValid.prototype.initTip=function(objId)
{

	var t_id=objId+"_tip";
	var tip=document.createElement("div");
	var tipKey=this.getErrKey(objId);   //获取字段key值
	tip.id=t_id;
	tip.className="help-info";
	var myObj=this.getDomObj(objId);
	var myTip=myObj.parentElement.nextElementSibling;
    myTip.appendChild(tip);    
	return tip;
}


/**
 * 初始化错误信息
 * @param objId;
 * @description 生成错误信息元素
 * 
 * */
FormValid.prototype.initErr=function(objId)
{
	var t_id=objId+"_ERR";
	var err=document.createElement("label");
	err.id=t_id;
	err.className="error";			
	var myObj=this.getDomObj(objId);
	var myTip=myObj.parentElement.nextElementSibling;
    myTip.appendChild(err);
	
}


/***
 * 
 * 设置并显示错误信息内容
 * @param id,type
 *
 */
FormValid.prototype.setErrInfo=function(id,type)
{
	var myDom=this.getDomObj(id);
	var myErrInfo=this.getErrAttr(myDom);
	var errId=id+"_ERR";
	var errKey=this.getErrKey(id);
	var dom=this.getDomObj(errId);
	//if (typeof confirmpwd =='undefind'){
	//}
	var domStr=dom.innerHTML;
	
	
	if(type=="show")   //当参数type为show的时候，显示错误信息
	{
		if(domStr=="")
		{
			if(myErrInfo!="" && myErrInfo)  //如果在页面上设置了data-error属性值，则其为错误信息内容
			{
				dom.innerHTML=myErrInfo;
			}else{
				dom.innerHTML=formValid_errMsgList[errKey];  //否则错误信息内容为formValid_errMsgList里面对应的内容
			}			
		}
		this.setDomVisible(dom);
	}else{
		this.setDomHide(dom);
	}		
}


/***
 * 
 * 获取页面设置的错误信息属性值
 * @param dom
 * @return errorInfo
 * 
 */
FormValid.prototype.getErrAttr=function(dom)
{
	var errorInfo=dom.getAttribute("data-error");  //获取元素在页面上定义的data-error的错误信息内容
	return errorInfo;
}

/***
 * 
 * 获取页面设置的提示信息属性值
 * @param  dom
 * @return tipInfo
 */
FormValid.prototype.getTipAttr=function(dom)
{
	var tipInfo=dom.getAttribute("data-tip");  //获取元素在页面上定义的data-tip的提示信息内容
	return tipInfo;
}


/****
 * 获取错误和提示信息的key值,并返回
 * @param id
 * @return returnKey
 */
FormValid.prototype.getErrKey=function(id)
{
	
	var options=this.options;
	var obj=options.formField;
	var returnKey;

	for(var i=0; i<obj.length; i++)
	{
		var myObj=obj[i];
		if(myObj.id==id)  //如果id一致的情况下
		{			
			for(var key in myObj.validateRule)  //遍历出给元素定义的所有validateRule
			{
				//这里需要优化下 ,优化方向：多个key值怎么处理
				if(formValid_ruleList.hasOwnProperty(key))  //如果定义的这个validateRule存在在校验规则里面
				{
					returnKey=key;  
					break;  
				}
			}
		}
	}	
	return returnKey;	//则返回这个key字段
}


/****
 * 
 * 获取表单数据
 * @return myItemList
 * 
 */
FormValid.prototype.getFormData=function(){
	var myOptions=this.options;
	var myForm=this.getDomObj(myOptions.formId);
	//console.log(JSON.stringify(myOptions));
	var myDom=document.getElementById(myOptions.formId);
	var myEle=myForm.elements;
	var myItemList={};
	var checkBoxList={};
	
	
	for(var i=0;i<myEle.length;i++){
		var itemName=myEle[i].id||myEle[i].name;
		var itemValue=myEle[i].value;
		var itemType=myEle[i].type;		
		
		
		//存在问题：如果element.id存在多个相同的，怎么办；
		
		if(itemType!="radio" && itemType!="checkbox"){  //如果是普通的input输入框
			myItemList[itemName]=itemValue;
		}else{
			var itemNameR=myEle[i].name;
			if(itemType=="radio"){
				if(myEle[i].checked){
					myItemList[itemNameR]=itemValue;
				}
			}
			if(itemType=="checkbox"){  
							
				if(myEle[i].checked)
				{
					if(itemNameR)  //如果存在这个name的checkBox
					{
						if(!checkBoxList[itemNameR])
						{
							checkBoxList[itemNameR]=[];
						}
						checkBoxList[itemNameR].push(itemValue);
					}
				}
			}
		}
	}
	
	for(var key in checkBoxList)
	{
		myItemList[key]=checkBoxList[key].join(",");
	}
	
	return myItemList;
}





/****
 * 提交前对form表单进行校验
 * @return checkSubFlag
 * 
 */
FormValid.prototype.beforeSubmit=function(){
	
	var errList=[];
	var checkSubFlag=false;
	var myErr=this.ErrList;
	var formFied=this.options.formField;
	if(this.blurList==formFied.length && this.blurList!=0)  //之前记录的blurList就可以在这里用来判断
	{
		if(!isEmptyObject(myErr))  //在表单提交前全部都校验过了，而且校验失败的都显示了错误信息
		{							
			checkSubFlag=false;
			if(checkSubFlag==false){
				errList.push(checkSubFlag);					
			}
		}else{
			for(var key in myErr)  //ErrList为空的就添加错误信息
			{
				this.setErrInfo(key); 
			}			
		}
		
		
	}else{   //如果在点击提交前还未校验过
		var formData=this.getFormData();
		for(var i=0; i<formFied.length; i++)
		{
			var inputId=formFied[i].id;
			var rule=formFied[i].validateRule;
			var ajax=formFied[i].ajaxValid;
			var errKey=this.getErrKey(inputId);			
			
			if(formData.hasOwnProperty(inputId))  //如果获取的表单数据里有inputId，就调用校验函数，返回boolen值.
					
			{   
				var myInput=this.getDomObj(inputId);
				var checkFlag=this.checkForm(myInput,rule,ajax);
				checkSubFlag=checkFlag;
				if(checkSubFlag==false){
					errList.push(checkSubFlag);					
				}
			}
		}
	}	
	//alert(errList);
	if(errList==""){
		checkSubFlag=true;   
	}else{
		checkSubFlag=false; 
	}
	return checkSubFlag;
	
}


