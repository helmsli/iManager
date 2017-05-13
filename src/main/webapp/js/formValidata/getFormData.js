            var formValid=new FormValid({           	
				"formId":"theForm",
				"formField":[{
					"id":"userName",
					"validateRule":{"maxLength":10,"minLength":3}					
					/*"ajaxValid":{
						url:
						data:
						ajaxcallBack:function(data)
						{
							return bool;
						}
					}*/
				},{
					"id":"userType",
					"validateRule":{"require":true},
					"tip":false
				},{
					"id":"pwd",
					"validateRule":{"isPwd":true}
				},{
					"id":"pwdCom",
					"validateRule":{"compared":"pwd"}
				},{
					"id":"userSel",
					"validateRule":{"require":true}
				},{
					"id":"email",
					"validateRule":{"isMail":true}				
				},{
					"id":"myNum",
					"validateRule":{"isNumber":true}	
				}]
			});
			
			var mybtn=document.getElementById("submitBtn");
			var checkboxError=document.getElementById("checkboxError");
			formValid.setDomHide(checkboxError);
		    addEvent(mybtn,"click",function(){
			
			    var subFlag=formValid.beforeSubmit();
           
           	    var formData=formValid.getFormData();
           	    
           	    if(formData.hobby!=undefined){
           	    	formValid.setDomHide(checkboxError);           	    	
           		    if(subFlag==true){
           			    console.log("success");
           		    }
           	    }else{
           	    	formValid.setDomVisible(checkboxError);
           	    }
           
		    });
		


var obj=document.getElementById("userType");
changeSelect(obj);	
function changeSelect(obj){
	var myul=obj.nextElementSibling;
	var myli=myul.childNodes;
	
	//遍历出所有li标签
	for(var i=0;i<myli.length;i++){
		addEvent(myli[i],"click",function(){
			
			console.log("cli");
			//获取a标签里的内容
			var aEle = this.childNodes[0];
			var aText = aEle.innerHTML;
						
			//赋值
			var liVal = this.value;
			obj.value=(liVal==0)?"":liVal;
			obj.innerHTML=aText+"<span class='caret'></span>";
		})
	}
}
		