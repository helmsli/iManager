//判断是不是更改的校验修改校验(非动态变化表格的判断),dynamicAdd,dynamicDelet,dynamicupdata分别代表那些行变化了
 var dynamicAdd=[];
 var dynamicDelet=[];
 var dynamicUpdata=[];
 function watchInPut(_this){
	 var newValue="";
	 var oldValue="";
	 var scope=getAngularScope("EditData"); 
	 var oldInfo=scope.oldInfo;
	 var key =_this.attr("data-datename");
	 var data_editabletype=_this.attr("data-editabletype");
	 if(oldInfo==undefined){
		  return;
	 }
	 
	 var data_group =_this.attr("data-group");
	 if(data_group)
	 {
		 watchDynamicInput(_this);
		 return;
	 }
	 oldValue=oldInfo[key];	
	 if(data_editabletype=="radio"){
		 if(key=="01"){
			 key="projectCycle";
			 oldValue=oldInfo[key];	
		 }
		 if(key=="02"){
			 key="projectCategory";
			 oldValue=oldInfo[key];	
		 }
		
		newValue=$(_this).find("input[type='radio']:checked").val();
			
	    }else if(data_editabletype=="datetimepicker"){
	    	newValue=$(_this).find("input[type='text']").val();
	    }else{ 
	    	newValue=_this.html();
		 }
	 //存储非动态变化的表格的修改的数据
	   var upDataArray=[key,oldValue,newValue];
	   upDataArray=JSON.stringify(upDataArray);
	   scope.edeitData.upData[key]=upDataArray;
	   console.log("测试修改的数据");
	   console.log(scope.edeitData);
	   
	 if(oldValue!=newValue){
		 if(key=="projectCycle"){
			 key="01";
		 }
		 if(key=="projectCategory"){
			 key="02";
		 }
		 $("label[data-for="+key+"]").addClass("label-tips");
	     }else{
	    	 $("label[data-for="+key+"]").removeClass("label-tips");
	     }
 }
 //判断动态表格是不是修改了
function watchDynamicInput(_this){
	var flag=false;
	 var scope=getAngularScope("EditData"); 
	 var oldInfo=scope.oldInfo;
	 var data_group =_this.attr("data-group");
	 data_group=parseInt(data_group);
	 var data_datename=_this.attr("data-datename");
	 var tableId=$(_this).parents().parents().parents().attr('id');
	 if(oldInfo==undefined){
		 return;
	 }
	 oldValue=oldInfo[tableId];//老数据
	  var tablePostData=tableSave(tableId);//新数据
	  var lengNew=tablePostData.length;
	  var lengOld=oldValue.length;
	  var key="";
	console.log(tablePostData);
	console.log(oldValue);
	  //如果新数据和老数据的长度不一样的话，那么肯定发生了改变，否则比较数据
	  if(lengNew!=lengOld){
		   var addLineData={};
		   var deletLineData={};
		     //因为现在不确定要把删除的事件绑在哪里，所以现在先不管删除
		  if(lengOld<lengNew){
			  //遍历出所有的增加数据
		    	for(var i=lengOld;i<lengNew;i++){
		    		addLineData[i]=tablePostData[i];
		    		dynamicAdd.push(i+1);
		    	}
		    	addLineData=JSON.stringify(addLineData);
		    	scope.edeitData.addData[tableId]=addLineData;
		    	//console.log(scope.edeitData);
		    	
		    }else{
		    	for(var i=lengNew;i<lengOld;i++){
		    		deletLineData[i]=oldValue[i];
		    		dynamicDelet.push(i+1);
		    	}
		    	deletLineData=JSON.stringify(deletLineData);
		    	scope.edeitData.deletData[tableId]=deletLineData;
		    	console.log(scope.edeitData);
		    }
			 flag=true; 
		 }else{
			 for(var i=0;i<lengOld;i++){
				 var PostData=tablePostData[i];
				 var oldData=oldValue[i];
				 var updataflag=false;//表示本行是不是修改
				 for(var j in PostData){
					 if(PostData[j]!=oldData[j]){
						 flag=true;//表示整个表格是不是修改
						 updataflag=true
						 key=tableId+"_"+i+"_"+j;
						 var upDataArray=[key,oldData[j],PostData[j]];
						 upDataArray=JSON.stringify(upDataArray);
						 scope.edeitData.upData[key]=upDataArray;
					 }
				 }
				 if(updataflag){
					 dynamicUpdata.push(i+1);
				 }
				 
			 }
			
		 }
	if(flag==false){
    	 $("label[data-for="+tableId+"]").removeClass("label-tips");
     }else{
    	 $("label[data-for="+tableId+"]").addClass("label-tips");
     }
	console.log("JSON.stringify(scope.edeitData)");
	console.log(JSON.stringify(scope.edeitData));
 }
 
 