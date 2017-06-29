$(function(){
	/*单击行，添加选中样式*/
	$("table tbody").delegate("tr","click",function(event){
		var target = event.target||event.srcElement;
		$(this).siblings().find("input").prop("checked", false);
		var _input = $(this).find('input');
		var _nodeName = target.nodeName.toLowerCase();
		if(_nodeName=='input'){
			_input.checked = !_input.checked;  
		}else if(_nodeName!='label'){
			_input.click();
		}
	});
});

//设置单位验证属性
var companyVaildOptions = [];
companyVaildOptions=[{
			"id":"companyId",
			"validateRule":{"require":true,"isNumber":true}		
		},{
			"id":"companyType",
			"validateRule":{"require":true}		
		},{
			"id":"companyName",
			"validateRule":{"require":true}
		},{
			"id":"companyProperty",
			"validateRule":{"require":true}
		},{
			"id":"certificateType",
			"validateRule":{"require":true}
		},{
			"id":"certificateId",
			"validateRule":{"require":true,"isNumber":true}
		},{
			"id":"connectMan",
			"validateRule":{"require":true}
		},{
			"id":"connectTel",
			"validateRule":{"require":true,"isNumber":true}
		},{
			"id":"conectMail",
			"validateRule":{"require":true,"isMail":true}
		},{
			"id":"creatMan",
			"validateRule":{"require":true}
		}];

var nodeInfo={};
App.filter("jsonDate", function($filter) {
    return function(input, format) {

        //从字符串 /Date(1448864369815)/ 得到时间戳 1448864369815
        var date = new Date(input);

        //转成指定格式
        return $filter("date")(date, format);
   };
});

/*定义module*/
App.controller('app', ['$scope','$ocLazyLoad', function($scope, $ocLazyLoad){
	$scope.loadBootstrap = function(){
		var myFileList=[];
		myFileList=loadFileList(myFileList);

        $ocLazyLoad.load(myFileList);
    }
    
    $scope.loadBootstrap();
    try 
    { 
     $scope.beijing_diqu = beijing_diqu;
    }
    catch (e) 
    {}
    
    //alert(JSON.stringify($scope.beijing_diqu));
	//定义变量ID
	$scope.currentId = "";
	$scope.titleList = [
	    "",
	    "单位名称",
		"单位性质",
		"证件类型",
		"证件ID",
		"联系人",
		"当前状态",
		"操作"
	];
	//单位对象模型
	$scope.companyInfo = {};
	var count=0;
	//初始化函数
	$scope.init=function()
	{
		var companysList=[];
		//获取单位列表
		initCompanyList(1);
		/*校验格式化*/
		$scope.addCompanyValid = new FormValid({"formId":"companyEdit",formField:companyVaildOptions});
	}
	
	
	
	/***
	 * 获取单位列表
	 */
	function initCompanyList(p)
	{
		var obj={
					"page.pageNum":p,
				    "page.pageSize":10
				};
		getAllCompanysList(obj,function(data){
			if(data.result == 0){
				companysList=data.responseInfo.lists;
				console.log(data);
				if(count == 0){
					/*pageNav.go(1,data.responseInfo.page.pages);*///z这里因为在加单位的时候出现错误，所以先注掉，到时需要的话再放开
		 			count++;
				}
				$scope.companysList=companysList;
				$scope.$applyAsync($scope.companysList);
			}
		},function(e){
			console.log(e);
		});
	}
	
	$scope.openEditWin = function(companyId){
		if(typeof companyId ==='undefined'){
			companyId = "";
			window.location.href = "company_edit.html?companyId="+companyId+"&addFlag=true";
		}else{
			window.location.href = "company_edit.html?companyId="+companyId+"&addFlag=false";
		}    
	}
	
	

	/*pageNav.fn = function(p,pn){
		initCompanyList(p);
    };*///这里因为在加单位的时候出现错误，所以先注掉，到时需要的话再放开
    
	//取消保存
	$scope.cancelWin=function (){
		window.history.go(-1);
	}
	
	//用户记录修改	
	$scope.editRow=function(index)
	{
		var myData=$scope.companysList[index];
		$scope.openEditWin(myData.companyId);
	};
	
	
	
	/* 行删除*/
	$scope.deleteRow = function(id){
		 var obj={
				 "request.companyId": id
		 		};
		 swal({
		        title: "确定删除单位？",
		        text: "删除后，单位数据将无法恢复",
		        type: "warning",
		        showCancelButton: true,
		        confirmButtonColor: "#DD6B55",
		        confirmButtonText: "确定",
		        cancelButtonText:"取消"
		 }, function () {
				//后台删除
		    	deleteCompany(obj,function(data){
					if(data.result==0){
						initCompanyList();
					}
				});
		    });
		
	};
}]);



App.controller('editCompany', ['$scope','$ocLazyLoad', function($scope, $ocLazyLoad){
	//获取编辑用户的id
	$scope.companyId = userEdit.getQueryString("companyId");
	//判断是新增用户还是编辑用户
	var addFlag = userEdit.getQueryString("addFlag");
	$scope.addFlag = addFlag?(addFlag=="true"?true:false):false;
	 //语言参数
	$scope.lang = window.localStorage.lang||'zh-CN';
	//单位信息
	$scope.companyInfo={};
    var companysList=[];

	/***
	 * 保存单位信息
	 */
	$scope.addSectorInfo=function(){
		var companyInfo=$scope.companyInfo;
		var companys=$scope.companysList;
		//单位信息表单校验
		var flag=$scope.addCompanyValid.beforeSubmit();
		//单位信息接口
		if($scope.addFlag){
			addSectorByServer(companyInfo,companys,flag);
		}else{
			updateCompanyByServer(companyInfo,companys);
		}
		$scope.companyInfo={};
	}
	
	getCompanyInfo($scope.companyId);
	//获取编辑信息
	function getCompanyInfo(companyId){
		getAllCompanysList({},function(data){
			if(data.result == 0){
				companysList=data.responseInfo.lists;
				for(var i in companysList){
					if(companysList[i].companyId==companyId){
						$scope.companyInfo=companysList[i];
						$scope.$applyAsync($scope.companyInfo);
						break;
					}
				}
			}
		},function(e){
			console.log(e);
		});
	}

	$scope.selectCompanyDistrict =function(code,name)
	{
		
		
		setTimeout(function () {
			$scope.$apply(function() {
				$scope.companyInfo.registerAddress=name;
				$scope.companyInfo.businessAddress=code;
			});
		     // AngularJS unaware of update to $scope
		  }, 200);
		$('#modelForRegisterAddress').modal('hide');
		
	}
	
}]);

/***
 * 增加单位
 * @param companyInfo 单位信息
 * @param companys 所有单位
 * @param flag 单位是否存在
 */
function addSectorByServer(companyInfo,companys,flag){
	var obj={
			"request.companyId":companyInfo.companyId,
			"request.companyName":companyInfo.companyName,
			"request.companyProperty":companyInfo.companyProperty,
			"request.certificateType":companyInfo.certificateType,
			"request.certificateId":companyInfo.certificateId,
			"request.registerAddress":companyInfo.registerAddress,
			"request.businessAddress":companyInfo.businessAddress,
			"request.linkMan":companyInfo.linkMan,
			"request.linkTel":companyInfo.linkTel,
			"request.email":companyInfo.email,
			"request.createPerson":companyInfo.createPerson
		}
	var createCompanyFlag=false;
	if(flag){
		var companyExist=isCompanyExist(companyInfo);
		if(companyExist){
			createCompany(obj,function(data){
				if(data.result == 0){
					swal("保存成功", "", "success");
					createCompanyFlag = true;
					window.history.go(-1);
				}else{
					swal(getErrMsg(data.result));
				}	
			},function(e){
				swal("保存失败");
			});
		}else{
			swal("单位已存在！");
		}
	}
	return createCompanyFlag;
} 

//编辑单位信息
function updateCompanyByServer(companyInfo,companys){
	var obj={
			"request.companyId":companyInfo.companyId,
			"request.companyName":companyInfo.companyName,
			"request.companyProperty":companyInfo.companyProperty,
			"request.certificateType":companyInfo.certificateType,
			"request.certificateId":companyInfo.certificateId,
			"request.registerAddress":companyInfo.registerAddress,
			"request.businessAddress":companyInfo.businessAddress,
			"request.linkMan":companyInfo.linkMan,
			"request.linkTel":companyInfo.linkTel,
			"request.email":companyInfo.email,
			"request.createPerson":companyInfo.createPerson
		}
	//判断单位名称用户是否修改    true:未修改    false:已修改
	var companyNameFlag=getCompanyNameFlag(companyInfo,companys);
	if(companyNameFlag){
		//判断单位是否已存在 true:不存在  false：已存在
		var companyExist=isCompanyExist(companyInfo);
		if(companyExist){
			//调用后台接口存储编辑后的单位信息
			console.log("调用后台接口存储编辑后的单位信息");
			updateCompanyInfo(obj);
		}else{
			swal("单位已存在！");
		}
	}else{
		//调用后台接口存储编辑后的单位信息
		console.log("调用后台接口存储编辑后的单位信息");
		updateCompanyInfo(obj);
	}
}

/**
 * 调用后台接口存储编辑后的单位信息
 */
function updateCompanyInfo(obj){
	updateCompany(obj,function(data){
		if(data.result == 0){
			swal("保存成功", "", "success");
			window.history.go(-1);
		}else{
			swal(getErrMsg(data.result));
		}	
	},function(e){
		swal("保存失败");
	});
}
/**
 * 判断单位名称用户是否修改
 * @param companyInfo 新增单位
 * @param companys 所有单位
 */
function getCompanyNameFlag(companyInfo,companys){
	//默认单位名未修改
	var companyChangeFlag=false;
	for(var i in companys){
		if(companys[i].companyId == companyInfo.companyId){
			var companysName=companys[i].companyName;
			if(companysName != companyInfo.companyName){
				//单位名称修改
				companyChangeFlag = true;
				break;
			}
		}
	}
	return companyChangeFlag;
}

/***
 * 判断单位是否已存在
 * @param companyInfo 新增单位
 */
function isCompanyExist(companyInfo){
	var obj={
			"request.companyName":companyInfo.companyName
			};
	var verifyResult=false;
	verifyCompanyName(obj,function(data){
		if(data.result == 0){
			verifyResult=data.responseInfo.verifyResult;
		}else{
			swal(getErrMsg(data.result));
			verifyResult=false;
		}
	},function(e){
		console.log(e);
	});
	return verifyResult;
}

/***
 * 动态生成单位列表select标签的option标签
 * @param companysList 所有单位
 */
function generateCompanyOptions(companysList){
	$("#companyslist").html('');
	$.each(companysList,function (index,item){
		var optstr = "<option value='"+item.companyName+"'>"+item.companyName+"</option>";
		$("#companyslist").append(optstr);
	});
	$("#companyslist").selectpicker('refresh');
	$('#companyslist').selectpicker('show');
};

//取消保存
function cancelWin(){
	window.history.go(-1);
}

