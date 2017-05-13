/**
 * 文件上传调用通用方法
 */

//提交评审材料
function fileUpload(id,obj){
	document.getElementById('submitMaterialMetaphaseInput').value=obj.value
	uploadFile.ajaxFileUpload({
	    url: getBasePath()+'/projectAnnex/upload', //用于文件上传的服务器端请求地址
	    fileElementId: id, //文件上传空间的id属性  <input type="file" id="file" name="file" />
	    success: function (data)
	    {
	    	data=JSON.parse(data);
	    },
	    error: function (data, status, e)//服务器响应失败处理函数  
	    {
	        alert(e);
	    }
	});
}