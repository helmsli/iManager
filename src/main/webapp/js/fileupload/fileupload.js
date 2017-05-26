/*
 * 无刷新上传组件 fileUpload.js
 * xinwei JavaScript Library
 * Copyright (c) 2017
 * @jeatStone
 * v0.0.0.3 
 * last modify:2017/05/23 增加异常处理; jeastone
 * 增加了兼容处理
 */
(function(w){
	var uploadFile={};
	uploadFile.createUploadIframe=function(id,url)
	{
            var frameId = 'WUI_UploadFrame_' + id;
            var iframeHtml=document.getElementById(frameId);
            if(iframeHtml==null)
            {
	            iframeHtml=document.createElement("iframe");
				iframeHtml.id=frameId;
				iframeHtml.name=frameId;
				iframeHtml.style.display="none";
	           	document.body.appendChild(iframeHtml);
            }else{
            	var time=new Date();
            	//iframeHtml.src=url+"?time="+time.getTime();
            }
		   return iframeHtml;
	};
	
	uploadFile.createUploadForm=function(id, fileElementId, data)
	{
		//create form	
		var formId = 'WUI_UploadForm_' + id;
		var fileId = 'WUI_UploadFile_' + id;
		var form=document.getElementById(formId);
		if(form==null)
		{
			form=document.createElement("form");
			form.method="post";
			form.id=formId;
			form.style.display="none";
			form.enctype="multipart/form-data";
			if(data)
			{
				for(name in data)
				{
					var fileInput=document.createElement("input");
					fileInput.type="hidden";
					fileInput.value=data[name];
					fileInput.name=name;
					form.appendChild(fileInput);
				}
			}
			
			var oldElement = document.getElementById(fileElementId);
			var newElement = oldElement.cloneNode(true);
			var body=document.body;
			var oldParent=oldElement.parentElement;
			oldParent.insertBefore(newElement,oldElement);
       		oldElement.id=fileId;
			form.appendChild(oldElement);
			body.appendChild(form);
		}
		return form;
		
	};
	
	uploadFile.ajaxFileUpload=function(options)
	{
		
		var id = options.fileElementId;
		var fileElementId=options.fileElementId;
		var data=options.data;
		var async=options.async||false;
		
		//注：异步有兼容性处理问题，仅支持高级版本浏览器
		if(async)
		{
			//如果需要异步
			this.ajaxFile(options);
			return;	
		}
		var form = uploadFile.createUploadForm(id, fileElementId, (typeof(options.data)=='undefined'?false:options.data));
		var postPath=options.url;
		var io = this.createUploadIframe(id, postPath);
		var frameId = 'WUI_UploadFrame_' + fileElementId;
		var formId = 'WUI_UploadForm_' + fileElementId;
		var iframe = document.getElementById(frameId);
		var time=new Date();
		form.action=postPath+"?time="+time.getTime();
		form.target=frameId;
		
		var responseInfo={};
		 try 
			{				
			if(iframe.contentWindow)
			{
				iframe.onload=function()
				{
					responseInfo.responseText = iframe.contentWindow.document.body?iframe.contentWindow.document.body.innerHTML:null;
				 	responseInfo.responseXML = iframe.contentWindow.document.XMLDocument?iframe.contentWindow.document.XMLDocument:io.contentWindow.document;
					try{
						var JSONparse=JSON.parse(responseInfo.responseText);
						options.success(responseInfo.responseText);
					}catch(e){
						options.error(options, responseInfo, null, e);
					}
				}
			}else if(iframe.contentDocument)
			{
				io.onload=function()
				{
					responseInfo.responseText = iframe.contentDocument.document.body?iframe.contentDocument.document.body.innerHTML:null;
					responseInfo.responseXML = iframe.contentDocument.document.XMLDocument?iframe.contentDocument.document.XMLDocument:io.contentDocument.document;
				}
			}						
		}catch(e)
		{
			options.error(options, responseInfo, null, e);
		}
		
		form.submit();
	}
	
	//这里有个兼容性问题要处理单文件上传，IE8以下不可以用
	uploadFile.ajaxFile=function(options)
	{
		var fileInput=document.getElementById(options.fileElementId);
		var file=fileInput.files[0];
		var name=fileInput.name;
		var data={name:file};
		var src = window.URL.createObjectURL(file);
		var fileName=file.name;
		var fileType=file.type;
		
		//做兼容处理
		var reader = new FileReader();
		reader.readAsDataURL(file);  
		
		//调用ajax组件;
		requestAjax(option.url,data,options.callBack)
		console.log(name);
	};
	
	
	uploadFile.uploadAjax=function(options)
	{
		var xhr = null;
			var isIexplore=false;
			if (window.XMLHttpRequest) {
				xhr = new XMLHttpRequest();
				if (window.XDomainRequest) {
					isIexplore=true;
					xhr = new XDomainRequest();
				}
			}
			methodType=methodType||"post";
			async=async||true;
			xhr.open(methodType, url, async);
			if(!isIexplore)
			{
				xhr.onreadystatechange = function() {
					if (xhr.readyState == 4) {
						if (xhr.status == 200){
							var resultData = JSON.parse(xhr.responseText);
							options.success(resultData);
						}else{
							options.error(resultData);
						}
					}
				};
			}else{
				xhr.onload = function () {
			    	options.success(JSON.parse(xhr.responseText));
			    };
			    xhr.onerror=function()
			    {
			    	options.error(e);
			    }
			}
			xhr.send(data);
					
	}

	window.uploadFile=uploadFile;
}(window))
