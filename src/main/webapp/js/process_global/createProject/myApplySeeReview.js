$(function(){
	var parm=parseQueryString();
	var projectId =parm.projectId; 
	var state=parm.state;
	
	init("reviewExpertTable");
	$("#reviewExpertTable tfoot").hide();
	if(state=="001"){
		flagExprtName=false;
		//ExpertValid();
		ExpertValidEvent();
	}
});
  
//指定专家的非空检验
 function  ExpertValid(){
	flagExprtName=true;
	$("#reviewExpertTable  td[data-editabletype='edit']").each(function(){
		var _this=$(this);	
		var item=_this;
		var itemHtml=item.html();
		  var context3= itemHtml.split('<p class="tip"')[0]; 
		  if(context3==""||context3==null||context3==undefined){
			  flagExprtName=false;
		  }	
	  });
	
	var submitDisabled=false;
	submitDisabled=flagExprtName?false:true;
	 $("#submmitButton").attr("disabled",submitDisabled);
	
}
 function  ExpertValidEvent(){
	 
	 $("#reviewExpertTable td[data-editabletype='edit']").blur(function(){
		 ExpertValid();
	 }); 
 }
