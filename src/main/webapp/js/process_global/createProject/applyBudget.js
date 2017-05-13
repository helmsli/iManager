$(function () {	
   //可编辑规则表格初始化
   init("tableRule",setTfootCall);
   initTableSubtotal();
   initNum();
  // $("#tableRule tfoot").hide();
});


//初始化小计
function initTableSubtotal()
{
	 //blur check	 
	 $("table").on("blur","td div[contenteditable='true']",function(){
			var _this=$(this);
			var tr=_this.parent('td').parent("tr");
			var cloum=tr.attr("data-group");
			var name=_this.parent('td').attr("data-datename");
			var myPrice="";
			var myNumber="";
			var newSubtotal=0;
			var newPriceTd=tr.find("td").eq(2).children("div");
			var newNumberTd=tr.find("td").eq(3).children("div");
			var subTotalTd=tr.find("td").eq(4);
			if(name=="payPrice"||name=="payNumber")
			{
				      var _this=$(this);
				      var myNumval=  _this.html();
				      _this.html(myNumval);
					  myPrice=newPriceTd.text();
					  myNumber=newNumberTd.text();
					  newSubtotal=myPrice*myNumber;
					  subTotalTd.text(newSubtotal.toFixed(2));
			}
			
	 }); 

}


function prevent(e) {
	e.returnValue = false;
}
//处理只允许输入数字的函数（可带小数点）
function digitInputNumber(el, e) {  
    var ee = e || window.event; // FF、Chrome IE下获取事件对象  
    var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    var val = el.text();  
    if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
        if(val.indexOf(".") >= 0)
        {
        	return false;
            prevent(e);
        }
        
    } else if(c==9){
    	return true;
    }
    else {
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
//处理只允许输入数字(不带小数点)
function digitInputInt(el, e) {  
    var ee = e || window.event; // FF、Chrome IE下获取事件对象  
    var c = e.charCode || e.keyCode; //FF、Chrome IE下获取键盘码  
    var val = el.text();  
    if (c == 110 || c == 190){ // 110 (190) - 小(主)键盘上的点  
        	return false;
            prevent(e);
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

//单价和数量控制只能输入数字
function initNum(){
	//获取所有的edit，并且Data-type是number
	 $("table").on("keydown","div[contenteditable='true']",function(e){
		 var _this=$(this);
		 var dataType=_this.attr("data-type");
		 if(dataType=="number")
			{
			 return digitInputNumber($(this), e);  
			 return;
			}
		
	 });
	 
	//获取所有的edit，并且Data-type是int
	 $("table").on("keydown","div[contenteditable='true']",function(e){
		 var _this=$(this);
		 var dataType=_this.attr("data-type");
		 if(dataType=="int")
			{
			 return digitInputInt($(this), e);  
			 return;
			}
	 });


}