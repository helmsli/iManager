

/***
���CooKie
**/
function setCookie(cname,cvalue,exdays)
{
	var d = new Date();
	exdays=exdays||30;
	d.setTime(d.getTime()+(exdays*24*60*60*1000));
	var expires = "expires="+d.toGMTString();
	document.cookie = cname + "=" + cvalue + "; " + expires;
}


/***
����CooKie
*/
function getCookie(cname)
{
	var name = cname + "=";
	var ca = document.cookie.split(';');
	/*for(var i=0; i<ca.length; i++)
	  {
	  var c = ca[i].trim();
		  if (c.indexOf(name)==0){
			  return c.substring(name.length,c.length);
			  break;
		  }
	  }
	  return "";
	*/
	var cookieName=localStorage[cname];
	if(cookieName!="")
	{
		return cookieName;
	}
	
} 

/***
ɾ��CooKie
*/

function deleteCooKie(cname)
{
	document.cookie = "'+username+'=; expires=Thu, 01 Jan 1970 00:00:00 GMT"; 
}

/***
У��CooKie
*/
function checkCookie(cookieKey)
{
	var hasCookey=getCookie(cookieKey);
	var hasKey=false;
	if(hasCookey!="")
	{
		hasKey=true;
	}
	return hasKey;
}

