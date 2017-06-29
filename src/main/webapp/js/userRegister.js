//设置用户验证属性
var uservalidOptions= [];
//设置单位验证属性
var companyVaildOptions = [];
uservalidOptions = [{
					"id":"username",
					"validateRule":{"loginNamevalidate":true,"require":true}		
				},{
					"id":"firstname",
					"validateRule":{"numberLetter":true}
				},{
					"id":"password",
					"validateRule":{"require":true,"isPwd":true}
				},{
					"id":"confirmpwd",
					"validateRule":{"require":true,"compared":"password"}
				},{
					"id":"phone",
					"validateRule":{"require":true,"isNumber":true}
				},{
					"id":"email",
					"validateRule":{"isMail":true}
				}];
companyVaildOptions=[{
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
						"validateRule":{"require":true,"require":true}
					},{
						"id":"certificateId",
						"validateRule":{"require":true,"require":true}
					},{
						"id":"connectMan",
						"validateRule":{"require":true}
					},{
						"id":"connectTel",
						"validateRule":{"require":true,"isNumber":true}
					},{
						"id":"conectMail",
						"validateRule":{"isMail":true,"require":true}
					},{
						"id":"creatMan",
						"validateRule":{"require":true}
					}];

/***
 * 表单组件初始化
 */
function initComponents(){
	/*日期组件初始化*/
	$('#datetimepicker').datetimepicker({
		 endDate:new Date(),//最大值选今天
         pickTime:false,//屏蔽小时分秒面板
    	 collapse: true
    });
};
 
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
 * 提交前格式化数据
 */
function formateBeforSubmit(userInfo){
	userInfo.birthday = str2date(userInfo.birthday);
	return userInfo;
}

/***
 * 增加用户
 * @param userInfo 用户信息
 * @param subFlag 用户表单信息校验
 * @param callBack 回调函数
 */
function saveUserByServer(userInfo,subFlag,callBack){
	var userInfo = formateBeforSubmit(userInfo);
	var obj = {
			     //"request.company_name":$('#companyslist').val(),
			     "request.company_name":userInfo.company,
				 "request.username":userInfo.username,
				 "request.firstname":userInfo.firstname,
				 "request.lastname":userInfo.lastname, 
				 "request.address":userInfo.address,
				 "request.sex":userInfo.sex,
				 "request.phone":userInfo.phone,
				 "request.email":userInfo.email,
				 "request.cardtype":userInfo.cardtype,
				 "request.cardno":userInfo.cardno,
	};
	var companyInfo = {};
	companyInfo.companyName = userInfo.company;
	var companyExist=isCompanyExist(companyInfo);
	console.log(companyInfo.companyName);
	//alert(companyExist);
	//不存在
	if(companyExist)
	{
		swal("单位不存在,请添加单位！");	
		return;
	}
	
	
	if($("#datetimepicker_input").val()!=''){//生日不为空的时候才传送
		obj["request.birthday"] = str2date($("#datetimepicker_input").val());
	}
	if(subFlag ){//表单验证
		//保存用户信息
		obj["request.password"] = userInfo.password;
		createProjectManager(obj,function(data){
			if(data.result==0){
				swal("保存成功", "", "success");
				callBack();//关闭窗口
			}else{
				swal(getErrMsg(data.result));
			}
		},function(e){swal("保存失败");});
	}
}	

/***
 * 增加单位
 * @param companyInfo 单位信息
 * @param companys 所有单位
 * @param flag 单位是否存在
 */
function addSectorByServer(companyInfo,companys,flag){
	var obj={
			"request.companyName":companyInfo.companyName,
			"request.companyProperty":companyInfo.companyProperty,
			"request.certificateType":companyInfo.certificateType,
			"request.certificateId":companyInfo.certificateId,
			"request.registerAddress":companyInfo.registerAddress,
			"request.businessAddress":companyInfo.registerAddressCode,
			"request.linkMan":companyInfo.connectMan,
			"request.linkTel":companyInfo.connectTel,
			"request.email":companyInfo.conectMail,
			"request.createPerson":companyInfo.creatMan,
			"request.currentStatus":"1"
		};
	//alert(JSON.stringify(obj));
	
	var createCompanyFlag=false;
	if(flag){
		var companyExist=isCompanyExist(companyInfo);
		var scope=getAngularScope("registerUser");
		if(companyExist){
			createCompany(obj,function(data){
				if(data.result == 0){
					$('#addCompanyModal').modal('hide');
					scope.companysList=companys.concat([{"companyName":companyInfo.companyName,"companyId":companyInfo.companyId}]);
					generateCompanyOptions(scope.companysList);
					swal("保存成功", "", "success");
					createCompanyFlag = true;
					scope.userInfo.company=scope.companyInfo.companyName;
				}else{
					swal(getErrMsg(data.result));
				}	
			},function(e){
				swal("保存失败");
			});
		}else{
			scope.userInfo.company=scope.companyInfo.companyName;
			$('#addCompanyModal').modal('hide');
			swal("单位已存在！");
		}
		scope.companyInfo={};
	}
	return createCompanyFlag;
}

//用户注册控制器
App.controller('registerUser', function($scope){
	//语言参数
    $scope.lang = window.localStorage.lang||'zh-CN';
	//用户对象模型
	$scope.userInfo = {};
	//单位对象模型
	$scope.companyInfo = {};
	/*校验格式化*/
	$scope.formValid = new FormValid({"formId":"addorEditUser",formField:uservalidOptions});
	$scope.addCompanyValid = new FormValid({"formId":"companyInfoEdit",formField:companyVaildOptions});
	
	try 
    { 
     $scope.beijing_diqu = beijing_diqu;
    }
    catch (e) 
    {}
    
    //初始化函数
	$scope.init=function()
	{
		var companysList=[];
		//获取单位列表
		$scope.initCompanyList();
		//表单组件初始化
		initComponents();
	}
	
	$scope.selectCompanyDistrict =function(code,name)
	{
		
		
		setTimeout(function () {
			 $scope.$apply(function() {
			$scope.companyInfo.registerAddress=name;
			$scope.companyInfo.registerAddressCode=code;
			 });
			 var subFlag=$scope.formValid.beforeSubmit('registerAddress');
			 var checkbox = document.getElementById(code);
				checkbox.checked = false; 
				
		     // AngularJS unaware of update to $scope
		  }, 200);
		$('#modelForRegisterAddress').modal('hide');
		
	}
	
	/***
	 * 保存单位信息
	 */
	$scope.addSectorInfo=function(){
		var companyInfo=$scope.companyInfo;
		var companys=$scope.companysList;
		var data=$scope.addCompanyValid.getFormData();
		//单位信息表单校验
		var flag=$scope.addCompanyValid.beforeSubmit();
		//单位信息接口
	   // console.log(companyInfo);
		var addSectorFlag=addSectorByServer(companyInfo,companys,flag);
		if(addSectorFlag){
			$('#companyslist').val(companyInfo.companyName);
			$("#companyslist").selectpicker('refresh');
			$('#companyslist').selectpicker('show');
		}
	}
		
	/***
	 * 保存用户信息
	 */
	$scope.saveUserInfo = function(userInfo,subFlag,callBack){
		var userInfo = $scope.userInfo;
		//用户信息表单校验
		var subFlag=$scope.formValid.beforeSubmit();
		//用户信息接口
		saveUserByServer(userInfo,subFlag,$scope.cancelWin);
	}

	//关闭窗口
	$scope.cancelWin=function(){
		window.location.href=getBasePath()+'/login/tologin';
	}

	/***
	 * 获取单位列表
	 */
	$scope.initCompanyList=function ()
	{
		getAllCompanys({},function(data){
			if(data.result == 0){
				companysList=data.responseInfo.companyLists;
				$scope.companysList=companysList;
				$scope.$applyAsync($scope.companysList);
				generateCompanyOptions($scope.companysList);
			}
		},function(e){
			console.log(e);
		});
	}
});






