(function(skin){
	//
	skin.cookieName="mystyle";
	skin.defaultName="default";
	
	skin.init=function()
	{
		var myStyle=this.getSkinCookie(this.cookieName);
		this.setSkin(myStyle);
	}
	
	skin.setSkin=function(cookie_style)
	{
		
		if(!cookie_style || cookie_style==this.defaultName)
		{
			return;
			
		}else{
			//需求变动这里暂时关闭，通过动态加载skin皮肤样式
			var doc=document.all;
			for(var i=0;i<doc.length; i++)
			{
				var newName=doc[i].nodeName.toLowerCase();
				if(newName=="link")
				{
					var title=doc[i].title;
					if(title!="")
					{
						if(title!=cookie_style)
						{
							doc[i].disabled=true;
						}else{
							doc[i].disabled=false;
						}
					}
				}
			}
		}
	}
	
	skin.changeSkin=function(type)
	{
		var myStyle=this.getSkinCookie(this.cookieName);
		if(type!==myStyle)
		{
			this.setSkinCookie(type);
			location.reload();
		}
	}
	
	skin.getSkinCookie=function(mystyle)
	{
		var returnSkin=getCookie(mystyle);
		if(!returnSkin)
		{
			return this.defaultName;
		}else{
			return returnSkin;
		}
	}
	
	
	skin.setSkinCookie=function(skinName)
	{
		
		setCookie(this.cookieName,skinName,{expires:1000*60*24*365});
	}
	
	mySkin.init();
	
}(window.mySkin={}));








