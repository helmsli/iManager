$(function(){
	/*可编辑表格组件数据模型*/
	var dataModel={
        chooseArr:{
            "01":[{text:"1年",value:"1年"},
                {text:"2年",value:"2年"},
                {text:"3年",value:"3年"},
                {text:"其他",value:"其他"}],
                //创建申请
                "02": [{text:"教育",value:"教育"},
                       {text:"劳动就业",value:"劳动就业"},
                       {text:"养护照料",value:"养护照料"},
                       {text:"交通出行",value:"交通出行"},
                       {text:"无障碍",value:"无障碍"},
                       {text:"康复",value:"康复"},
                       {text:"辅助器具",value:"辅助器具"},
                       {text:"文化体育",value:"文化体育"},
                       {text:"其他",value:"其他"}]
        }
    };
	/*可编辑不规则表格初始化*/
	initTableEdit(dataModel,"tableEdit");
	var num=0;
	init("tableRuleInner");
	//$("#tableRuleInner tfoot").hide();
    addInput();
    //指定评审专家
    init("reviewExpertTable");
	//$("#reviewExpertTable tfoot").hide();
	valid();
	
	//校验表单
	//checkTdData();
	//初始化提示语
	initTips();
	 $('input:radio[name="01"]').change( function(){
		 $("#period").val("");
	 })
	  $('input:radio[name="02"]').change( function(){
		 $("#category").val("");
	 })
	
});

//初始化提示语
function initTips(){
	var taskDescribe=$("td[data-datename='taskDescribe']").children("div");
	var taskBackground=$("td[data-datename='taskbackground']").children("div");
	var taskGoal=$("td[data-datename='taskgoal']").children("div");
	var taskDanger=$("td[data-datename='taskDanger']").children("div");
	var taskCreate=$("td[data-datename='taskcreate']").children("div");
	
	taskDescribe.prepend("<p class='tip'>概述项目希望解决的问题，以及计划通过何种方式达到什么目标？300字以内 </p>");
	taskBackground.prepend('<p class="tip">限1000字以内</p>');
	taskGoal.prepend('<p class="tip">限100字以内</p>');
	taskDanger.prepend('<p class="tip">1000字以内</p>');
	taskCreate.prepend('<p class="tip">200字以内</p>');
	
	taskDescribe.prop("class","innMinht");
	taskBackground.prop("class","innMinht");
	taskGoal.prop("class","innMinht");
	taskDanger.prop("class","innMinht");
	taskCreate.prop("class","innMinht");
}

//提示语隐藏
function tipHide(obj){
	$(obj).children("div").find("p").hide();
}



function setTfootCall(tableId)
{   
	var tfoot=$("#"+tableId+" tfoot");
	var hasTfoot=checkHasTfoot(tableId);
	var myTfootInfo='<tr data-id="'+tableId+'">'
					+'<th dataType-id="'+tableId+'"colspan="7" style="text-align:rigth !important;"><label data-for="'+tableId+'"></label></th>'
					+'</tr>';
	if(hasTfoot)
	{
		tfoot.append(myTfootInfo);
	}else{
		tfoot='<tfoot>'
				+myTfootInfo
	        +'</tfoot>'
	        $("#"+tableId).append(myTfootInfo);
	}
}


/***
 * checktd data
 * 
 * **/

function checkTdData()
{
	 //blur check	 
	 $("table").on("blur","td div[contentEditable='true']",function(){
		    
			var _this=$(this);
			var name=_this.parent("td").attr("data-datename");
			
			var checkFlag=checkTdNameAndValue(name,_this);

			pageUICheckController(_this,checkFlag,name);
			//变更处理
			var currentState="008";
			if(currentState=="008")
			{
				//watchDynamicInput(_this);
				watchInPut(_this);
			}
		 }); 
	 $("table td div[contenteditable='true']").keyup(function(){
			valid();
	}); 
	 
	 $("table td div[contenteditable='true']").focus(function(){
		 
		 if($(this).hasClass("projectChecked"))
			 {
			 	$(this).removeClass("projectChecked");
			 	$(this).html("");
			 }
		 //projectShow();
	}); 
	
}

/**
 * 
 * @param name
 * @param obj
 * @returns {Boolean}  true -- valid  false--invalid
 */
function checkTdNameAndValue(name,obj)
{
	var checkFlag=true;
	switch(name)
	{
		case "projectName":
		case "projectId":
			checkFlag=checkIsEmpty(obj);
			break;
		case "telno":
			checkFlag=checkTelphone(obj.html());
			break;
		case "email":
			checkFlag=checkEmail(obj.html());
			break;
		case "startTime":
		case "endTime":
		case "project_period":
		case "project_category":
			checkFlag=true;
			break;
		case "taskDescribe":
		case "taskbackground":
		case "taskgoal":
		case "taskDanger":
		case "taskcreate":
			checkFlag=checkIsEmpty(obj);
		break;
		
		
		
	}
	
	return checkFlag;
}
/**
 * 
 * @param obj
 * @param flag  -- true 
 * @param name
 */
function pageUICheckController(obj,flag,name)
{
	var errorInfo='';
	var errorClassName="";
	if(!flag)
	{
		 errorInfo='不能为空';
		 errorClassName="projectChecked";
		 obj.text(errorInfo);
		 obj.addClass(errorClassName);
	}
	else
	{
		 errorClassName="projectChecked";
		obj.removeClass(errorClassName);
	}
	
		
	 
	switch(name)
	{
		case "taskDescribe":
		case "taskbackground":
		case "taskgoal":
		case "taskDanger":
		case "taskcreate":
			if(!flag)
			{
				errorInfo='<p class="projectChecked">此项为必填项</p>';
				obj.append(errorInfo);
			}else{
				
				 errorClassName="projectChecked";
				obj.removeClass(errorClassName);
			}
		break;
	}
	 
}

/**
 * 
 * @param obj
 */
function getValue(obj)
{
	var myVal=obj.html();
	var phtml=obj.children().html();
	myVal = myVal.split("<p");
	return myVal[0];
	
}

/**
 * true is valid,not null
 * false  null
 *
 * **/

function checkIsEmpty(obj)
{
	var myVal=obj.html();
	var myText=getValue(obj);
	myVal=myVal.replace("&nbsp;","");
	myText=myText.replace("&nbsp;","");
	var phtml=obj.children();
	//$(obj).html().replace("&nbsp;","").replace(/>\/?.+?</gi,"").replace(/<\/?.+?>/gi,"");
	var pText=phtml.text();
	var snodeName=(phtml.length>0)?phtml[0].nodeName:"";
	snodeName=snodeName.toLowerCase();
	//console.log(snodeName + ":" + myText + ":" + pText + ":"+ phtml.html());
	if(snodeName=="p")
	{
		if(myText==pText)
			{
				return false;
			}else{
				if(myText!="" && myText!=null)
				{
					return true;
					
				}else{
					return false;
				}
			}
	}else{
		if(myVal!="" && myVal!=null)
		{
			return true;
			
		}else{
			return false;
		}
	}
	
}
/**
 * 
 * true --valid false-- invalid
 * **/
function checkTelphone(num)
{
	var reg=/^[0-9]*[1-9][0-9]*$/;
	num=num.replace(/\s/g,"")
	if(!reg.test(num))
	{
		return false;
		
	}else{
		return true;
		
	}
}
/**
 * 
 * true - valid false --invalid
 * **/

function checkEmail(str)
{
	
	  var reg=/(^(?:\w+\.?)*\w+@(?:\w+\.)+\w+$)/;
	  str=str.replace(/\s/g,"")
	if(!reg.test(str))
		{
			return false;
		}else{
			return true;
			
		}
}

function checkHasTfoot(tableId)
{
	var tfoot=$("#"+tableId+" tfoot");
	if(tfoot.length>=1)
	{
		return true;
	}else{
		return false;
	}
}


//radio组增加input
function addInput(){
	$(".project_period").append('<input id="period" class="form-control form-control-inline" onClick="checkedOther(this)" type="text"/>');
	$(".project_category").append('<input id="category" class="form-control form-control-inline" onClick="checkedOther(this)" type="text"/>');
} 

//input与radio对应选中
function checkedOther(obj){
	$(obj).parents().siblings("div").children("input").prop("checked",false); 
	$(obj).prev("div").children("input").prop("checked","checked");
}


/*//所有的时间td在初始化的时候进行赋值
$("table td[data-editabletype='datetimepicker']").datetimepicker({
    format: "yyyy-MM-dd hh:mm:ss",
    minView:'month',
    language: 'zh-CN',
	autoclose:true,
	startDate:new Date()
});
$("table td[data-editabletype='datetimepicker']").datetimepicker({
	 format: "yyyy-MM-dd hh:mm:ss",
	  minView:'month',
	  language: 'zh-CN',
	  autoclose:true,
});*/

/*   $("table td[data-editabletype='datetimepicker'] i").on("click",function(){
		var endTimeValue=$("#endTimeundefined .form-control").val();
	    var EndTime= new Date(endTimeValue);
        $(this).datetimepicker("setEndDate",EndTime);
    });*/
/*常贵春*/
//step表格校验
var requireFlag=true;
function setInitTime(){
    var defaultStartTime=today("beginTime");
	var defaultEndTime=today("overTime");
	$("#startTime input").attr("value",defaultStartTime);
	$("#endTime input").attr("value",defaultEndTime); 
	
	
};

//必填项校验
function tipShow(obj){
	 if(obj==undefined||obj.children[0]==undefined){
			return; 
		 }
	var context=obj.innerText;
	context=context.replace(/\s/g,"");
	if(context.length == 0){
		obj.children[0].style.display="block";
		var child=$(obj).children("p").children("p");
		if(child.html()){
			return;
		}else{
			$(obj).children("p").append("<p id='tips' style='color:red'>此项为必填项</p>");
		}
	}
}


  //设置时间默认为今天常贵春修改
function today(id){
	var today=new Date();
	var h1=today.getFullYear();
    
	if(id=="overTime"){
	   h1=h1;
	}
    var m1=today.getMonth()+1;  
    var d1=today.getDate();
  /*  var h=today.getHours();       //获取当前小时数(0-23)
	var m=today.getMinutes();     //获取当前分钟数(0-59)
	var s=today.getSeconds();*/
	m1= m1<10?"0"+m1:m1;   //  这里判断月份是否<10,如果是在月份前面加'0'
	d1= d1<10?"0"+d1:d1; 
	return h1+"-"+m1+"-"+d1+" ";
    }  
  	//设置开始时间不能大于结束时间，结束时间不能早于开始时间，全部时间不可以早于今天
 	$("#startTimeundefined").datetimepicker({
	    format: "yyyy-MM-dd ",
	    minView:'month',
	    language: 'zh-CN',
		autoclose:true,
		startDate:new Date()
    });
   $("#startTimeundefined i").on("click",function(){
		var endTimeValue=$("#endTimeundefined .form-control").val();
	    var EndTime= new Date(endTimeValue);
	    $("#startTimeundefined").datetimepicker("setEndDate",EndTime)
    });
 $("#endTimeundefined").datetimepicker({
	    format: "yyyy-MM-dd",
	    minView:'month',
	    language: 'zh-CN',
	    autoclose:true,
	    //startDate: new Date()
	});
	 $("#endTimeundefined i").on("click",function(){
		 var BeginTimeValue=$("#startTimeundefined .form-control").val();
		 var BeginTime=new Date(BeginTimeValue);
	    $("#endTimeundefined").datetimepicker("setStartDate",BeginTime);
	 });
	 //把按钮变成不可以点击的状态  
    var flag=false;
    
    var valid=function(){
    	var everyOneEmpty="不能为空";
    	var  telNoErro  ="电话号码格式错误";
    	var  emilErro="邮箱格式错误";
	    flag=true;
	    
   	  $("td[data-editable='edit']").each(function(i,item){  
		  var context= item.innerHTML.split('<p class="tip"')[0];
		  context=context.split('<br')[0]
		  if(context==""||context==null||context==undefined||context=='<br style="display: block;">'||context==everyOneEmpty||context==telNoErro||context==emilErro){
			  flag=false;
		  }	
		
	  });
//项目实施计划及输出（主要是里程碑和输出）做特殊处理
	 
 $("#tableRuleInner td[data-editabletype='edit']").each(function(i,item){  
			  var context2= item.innerHTML.split('<p class="tip"')[0]; 
			  if(context2==""||context2==null||context2==undefined||context2=='<br style="display: block;">'||context2==everyOneEmpty||context2==telNoErro||context2==emilErro){
				  flag=false;
			  }	
			
		  });
		 var buttonDisabled=false;
		 buttonDisabled=(flag)?false:true;
		 //提交按钮在全不为空的情况下才能提交
		// $("#button2").attr("disabled",buttonDisabled);
    }
 
    function prevent(e) {
    	e.returnValue = false;
    
    }

    function digitInput(el, e) {  
        var ee = e || window.event; // FF、Chrome IE下获取事件对象  
        var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
        var val = el.text();  
        if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
            if(val.indexOf(".") >= 0)
            {
            	prevent(e);
            	return false;
             
            }
            
        } else if(c==9){
        	return true;
        }else {
            if ((c != 8 && c != 46 && // 8 - Backspace, 46 - Delete  
                (c < 37 || c > 40) && // 37 (38) (39) (40) - Left (Up) (Right) (Down) Arrow  
                (c < 48 || c > 57) && // 48~57 - 主键盘上的0~9  
                (c < 96 || c > 105)) // 96~105 - 小键盘的0~9  
                || e.shiftKey) { // Shift键，对应的code为16  
                prevent(e); // 阻止事件传播到keydown  
            	return false;
            }  
        }  
        return true;
    }  

 //电话只能输入数字
   $("#myNum").on("keyup keydown onafterpaste",function(e){
	   //console.log(e);
	   return digitInput($(this), e);  
		 return;
   })
   //电话只能输入数字
   $("#myNum").on("keyup keydown onafterpaste",function(e){
	   //console.log(e);
	   return inputLength(this,20);;  
		 return;
   })
   
 //限制输入框的长度
function inputLength(obj,length){
	   var isCanInput=true;
	   $(obj).children("div").find("p").hide();
	   var _this=$(obj);
	   var myValue=_this.text();
	   myValue=myValue.replace(/\s/g,"");
    if(myValue.length==length){
    	var e=this.event;
        var ee = e || window.event; // FF、Chrome IE下获取事件对象  
        var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    	if(c != 8 && c != 46){//8 - Backspace, 46 - Delete 
    		prevent(e);
    		isCanInput=false;
    	}
         }
    return isCanInput;
	}    
   

   
   