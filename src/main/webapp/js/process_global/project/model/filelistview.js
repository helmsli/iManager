function refreshuiCallback(scope)
{
	
	alert("asdfasfdsadfasdf");
	
}
function initfileList()
{
	//console.log("3333333333333");
	
	var scope=getAngularScope("projectModel");
	console.log(scope.gloablParm);
	//说明HTML已经完毕；
	var stateName="state_"+scope.gloablParm.state;
	if(typeof scope.registerHtmlFlag!="undefined" && typeof scope.registerHtmlFlag[stateName] !="undefined")
	{
		refreshuiCallback(scope);
	}
	else
	{
		globalRegisterJs(scope.gloablParm.state,refreshuiCallback);
	}
	
}
initfileList();



//init 
/**
 * 拿到系统的scope，赋值，向你register；如果页面加载完毕，由框架调用callbackHtml;
 * 如果HTML没有加载完毕，
 */

