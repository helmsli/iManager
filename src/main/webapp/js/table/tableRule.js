                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      /*定义组件select、checkbox、radio数据模型*/
//组件模型数据示例
var dataModelRule={
    selectArr:{
        "01":["开发","测试","产品"],
        "02":["sfdsafd","测试","产品"]
    },
    chooseArr:{
        "01": [{text:"1年",value:"1"},
            {text:"2年",value:"2"},
            {text:"3年",value:"3"}],
        "02": [{text:"5年",value:"1"},
            {text:"2年",value:"2"},
            {text:"3年",value:"3"}],
        "03": [{text:"1分",value:"4"},
            {text:"2分",value:"5"},
            {text:"3分",value:"6"},
            {text:"4分",value:"7"},
            {text:"5分",value:"8"}]
    }
};

/*初始化表格数据源*/
var dataList={
    select:{},
    radio:{},
    checkbox:{} 
};

var rowNumArr={};

function init(tableId,setTfootCallBack){
	console.log("====================");
	/*初始化列属性*/
    initColumnRule(tableId,setTfootCallBack);
    /*初始化函数*/
    initRule(dataModelRule,0,tableId);
    rowNumArr[tableId]=[];
    rowNumArr[tableId]=[1];
    $(".deleteRule"+tableId).hide();
}

/*初始化增加行*/
function initColumnRule(tableId,callBack){
    $("#"+tableId).append("<tbody></tbody>");
    var inner = initRowRule(tableId,0,0);
    $("#"+tableId+" tbody tr").eq(0).append(inner);
    if(callBack)
    	{
    		callBack(tableId);
    	}else{
    		$("#"+tableId+" tfoot").hide();
    	}
}

/*初始化行属性*/
function initRowRule(tableId,rowNumber,row) {
	var innerRow="";
	if(rowNumber == 0){
		$("#"+tableId+" tbody").append("<tr data-group="+rowNumber+"></tr>");
	}else{
		$("#"+tableId+" tbody tr").eq(row-1).after("<tr data-group="+rowNumber+"></tr>");
	}
	
    $("#"+tableId+" thead").find('th').each(function (thindex, thitem) {
        var columnType=$(this).attr("data-columnType");
        var columnName=$(this).attr("data-columnName");
		var columnDateName=$(this).attr("data-columnDateName");
		var type=$(this).attr("data-type");
		//console.log(columnName+"---"+columnType+"---"+columnDateName);
        if(columnType == "select"){
            innerRow +="<td data-editableType='select' data-datename='"+columnDateName+"' data-columnName='"+columnName+"' data-group="+ rowNumber +"></td>";
        }else if(columnType == "edit"){
            innerRow +="<td class='pre-wrap' data-datename='"+columnName+"' data-editableType='edit' data-group="+ rowNumber +" data-type='"+ type + "'"+"><div data-type='"+ type + "' contentEditable='true' onblur='javascript:getTextValue(this)' class='innDiv'>&nbsp;</div></td>";
        }else if(columnType == "radio"){
            innerRow +="<td data-editableType='radio' data-datename='"+columnDateName+"' data-columnName='"+columnName+"' data-group="+ rowNumber +"></td>";
        }else if(columnType == "checkbox"){
            innerRow +="<td data-editableType='checkbox' data-datename='"+columnDateName+"' data-columnName='"+columnName+"' data-group="+ rowNumber +"></td>";
        }else if(columnType == "datetimepicker"){
            innerRow +="<td data-editableType='datetimepicker' data-datename='"+columnName+"' data-group="+ rowNumber +" ></td>";
        }else if(columnType == "operate"){
            innerRow +='<td style="cursor: pointer;"><span onClick="javascript:getTableRule(event,\''+tableId+'\')">增加</span><span style="padding-left:10px;" class="deleteRule'+tableId+'" onClick="javascript:deleteTableRule(event,\''+tableId+'\')">删除</span></td>';
        }else{
        	if(thindex==0){
        		var rowNum=rowNumber+1;
                innerRow +="<td class='align-center'>"+rowNum+"</td>";
        	}else{
        		innerRow +="<td class='align-center'></td>";
        	}
        }
    });
    return innerRow; 
}

/*增加固定行*/
function addTableRule(tableId,rowNum){
	$(".deleteRule"+tableId).show();
	for(var i=1;i<rowNum;i++){
		var inner=initRowRule(tableId,i,i);
		$("#"+tableId+" tbody tr").eq(i).append(inner);
		initRule(dataModelRule,i,tableId);
		rowNumArr[tableId].push(i+1);
	}
}

/*动态增加行*/
function getTableRule(event,tableId){
	$(".deleteRule"+tableId).show();
	var rowInsert="";
	var tableRow=rowNumArr[tableId];
	var row=event.currentTarget.parentNode.parentNode.rowIndex;
	for(var i in tableRow){
		if(row == tableRow[i]){
			rowInsert = tableRow.length;
			break;
		}else{
			rowInsert = row;
		}
	}
	//数组索引升序排序
	var tableRowIndex=tableRow.sort(compare);
	//数组不连续时，序号自动补充
	if(tableRowIndex[tableRowIndex.length-1]>tableRowIndex.length){
		rowInsert=tableIndex(rowNumArr[tableId])-1;
	}
	var inner = initRowRule(tableId,rowInsert,row);
	$("#"+tableId+" tbody tr").eq(row).append(inner);
	initRule(dataModelRule,row,tableId);
	tableRow.push(rowInsert+1);
}

//动态删除行
function deleteTableRule(event,tableId){
	var row=event.currentTarget.parentNode.parentNode.rowIndex;
	if(rowNumArr[tableId].length == 1){
		$(".deleteRule"+tableId).hide();
	}else{
		$(".deleteRule"+tableId).show();
		event.currentTarget.parentNode.parentNode.remove();
		var deleteIndex=Number(event.currentTarget.parentNode.parentNode.getAttribute("data-group"))+1;
		removeByValue(rowNumArr[tableId], deleteIndex);
	}
}

//删除行索引
function removeByValue(arr, val) {
    for(var i=0; i<arr.length; i++) {
	    if(arr[i] == val) {
	      arr.splice(i, 1);
	      break;
	    }
	}
}
	

function tableIndex(number){
    //循环nums数组，若中间有断的序号，则返回，若没有，则返回最大值加1
    var numsLen = number.length;
    if (numsLen>0){
        for (var i=0;i<=number[numsLen-1];i++){
            if (!contains(number,i+1)){//不连续
                return i+1;//返回当前下标作为序号
            }
        }
        return number[numsLen-1]+1;
    }
}

/***
 * 数字升序排序
 * @param {Object} value1
 * @param {Object} value2
 */
function compare(value1, value2) {
    if (value1 > value2) {
        return 1;
    } else if (value1 < value2) {
        return -1;
    } else {
        return 0;
    }
}

/***
* 数组中是否包含某值
* @param {Object} array
* @param {Object} obj
*/
function contains(array, obj) {
   var i = array.length;
   while(i--){
       if (array[i] === obj) {
           return true;
       }
   }
}

function initRule(data,row,tableId) {
    /*组件初始化*/
    componentsInitRule(data,row,tableId);
}

/*定义表格需加入的组件并初始化组件*/
function componentsInitRule(data,row,tableId) {
    /*初始化组件select、radio、checkbox、datePicker*/
    initRuleSelect(data.selectArr,row,tableId);
    initRuleRadio(data.chooseArr,row,tableId);
    initRuleCheckbox(data.chooseArr,row,tableId);
    initRuleDatePicker('yyyy-MM-dd',row,tableId);

    /*可编辑输入框*/
    initEditableRule(tableId);
}

function initEditableRule(tableId){
	$("#"+tableId+" tr td[data-editableType='edit']").each(function(){
		var _self=$(this);
		_self.attr("data-editValue","");
		//后期扩展
		//_self.attr("contentEditable",true);
	});
}

//可编辑输入框td的data-editValue属性赋值
function getTextValue(obj){
	var tableTdValue=$(obj).html().replace("&nbsp;","").replace(/>\/?.+?</g,"").replace(/<\/?.+?>/g,"");
	$(obj).parent("td").attr("data-editValue",tableTdValue);
}

/**
 * 初始化select组件
 * @param selectArr：定义组件数据模型
 * */
function initRuleSelect(selectArr,row,tableId)
{
	initRuleCommon("select",selectArr,selectTemplate,"",row,tableId);
}

/**
 * 初始化radio组件
 * @param chooseArr：定义组件数据模型
 * */
function initRuleRadio(chooseArr,row,tableId)
{
	initRuleCommon("radio",chooseArr,radioTemplate,"",row,tableId);
}

/**
 * 初始化checkbox组件
 * @param chooseArr：定义组件数据模型
 * */
function initRuleCheckbox(chooseArr,row,tableId)
{
	initRuleCommon("checkbox",chooseArr,checkboxTemplate,"",row,tableId);
}

/**
 * 初始化时间组件
 * @param format：定义时间格式
 * */
function initRuleDatePicker(format,row,tableId)
{
	initRuleCommon("datetimepicker","","",format,row,tableId);
}

/**
 * 加载组件
 * @param dataType:自定义组件属性data-editableType
 *         arr:组件数据模型[]
 *         template:组件模板selectTemplate、radioTemplate、checkboxTemplate、dateTemplate
 *         format:日期格式
 * */
function initRuleCommon(dataType,arr,template,format,row,tableId)
{
    $("#"+tableId+" tr").eq(row+1).children("td[data-editableType="+dataType+"]").each(function(){
        var _self=$(this);
        var dataName=_self.attr("data-datename");
        var dataGroup=_self.attr("data-group");
		var dataColumnName=_self.attr("data-columnName");
        if(dataType=="datetimepicker")
        {
            _self.attr("id",dataName);
            var _thisId=dataName;
            _self.append(dateTemplate(dataName,dataGroup));
            //alert(new Date());
            $('#'+_thisId+dataGroup).datetimepicker({
                format: format,
                startDate:new Date()
            });
        }else{
            var myData=arr[dataName];
            var html=template(myData,dataName,dataGroup,dataColumnName);
            initModel(dataName,myData,dataType);
            _self.append(html);
        }
    });
}

/**
 * 定义表格组件模板
 *selectTemplate：下拉选项模板
 *dateTemplate：日期模板
 *radioTemplate：单选框模板
 *checkboxTemplate：复选框模板
 * */
/**
 * 下拉选项模板
 * @param arr:组件数据模型[]
 *        name：自定义组件属性data-editableType
 * return selectTemplate组件
 * */
function selectTemplate(arr,name,rowNum) {
    var selectList="";
    for(var i in arr){
        var selectLi= '<option value='+arr[i]+'>'+arr[i]+'</option>';
        selectList += selectLi;
    }
    return '<div class="form-line">'+
            '<select class="form-control show-tick" id='+rowNum+name+ ' name='+name+'>'+
            '<option value="">请选择类型</option>'+selectList+
            '</select>'+
            '</div>'
}

/**
 * 日期模板
 * @param id:单个日期组件名称data-datename
 * return dateTemplate日期组件
 * */
function dateTemplate(id,rowNum) {
    return 	'<div id="'+id+rowNum+'" class="input-append date">'+
            '<input  type="text" class="form-control" data-format="dd/MM/yyyy" value='+getCurrentDate()+'/>'+
            '<span class="add-on">'+
            '<i data-time-icon="icon-time" data-date-icon="fa fa-calendar icon-calendar"> </i>'+
            '</span>'+
            '</div>';
}

/**
 * 获取当前时间
*/
function getCurrentDate(){
	var today=new Date();
	
	var year=today.getFullYear();
	var month=today.getMonth()+1;  
    var day=today.getDate();
    var hours=today.getHours();       //获取当前小时数(0-23)
	var minutes=today.getMinutes();     //获取当前分钟数(0-59)
	var seconds=today.getSeconds();
	month= month<10?"0"+month:month;   //  这里判断月份是否<10,如果是在月份前面加'0'
	day= day<10?"0"+day:day;        //  这里判断日期是否<10,如果是在日期前面加'0'
	return year+"-"+month+"-"+day+" "+hours+":"+minutes+":"+seconds;
}

/**
 * 单选框模板
 * @param arr:组件数据模型[]
 *        name：自定义组件属性data-editableType
 * return radioList组件
 * */
function radioTemplate(arr,name,rowNum,columnName) {
    var radioList="";
    for(var i in arr){
        var radio='<div class="radio-inline">'+
                  '<input type="radio" class="with-gap radio-col-blue" id="'+i+rowNum+'" name="'+name+rowNum+'" columnName="'+columnName+'"  value="'+arr[i].text+'">'+
                  '<label for="'+i+rowNum+'">'+arr[i].text+'</label>'+
                  '</div>';
        radioList += radio;
    }
    return radioList;
}

/**
 * 复选框模板
 * @param arr:组件数据模型[]
 *        name：自定义组件属性data-editableType
 * return checkboxList组件
 * */
function checkboxTemplate(arr,name,rowNum,columnName) {
    var checkboxList="";
    for(var i in arr){
        var checkbox='<div class="checkbox-inline">'+
                '<input type="checkbox" class="filled-in chk-col-blue" id="#'+i+rowNum+'" name="'+name+rowNum+'" columnName="'+columnName+'" value="'+arr[i].text+'">'+
                '<label for="#'+i+rowNum+'">'+arr[i].text+'</label>'+
                '</div>';
        checkboxList += checkbox;
    }
    return checkboxList;
}

/**
 * 初始化数据模型
 * @param dataType:组件类型名称select、checkbox、radio
 *         name:单个组件标识字段名称
 *         data:单个组件对应的数据模型[]
 * */
function initModel(name,data,dataType)
{
    dataList[dataType][name]=data;
}

//鼠标移出时存储对象
/*function dataSave(e) {
    var _self=e.currentTarget.id;
    dataList[id]=$("#"+id).text().replace(/\s/g, "");
}*/

/*保存表格数据*/
var dataLists=[];

function tableSave(tableId){
	var tableDate={};
	tableDate[tableId]=[];
	for(var i=0;i<rowNumArr[tableId].length;i++){
		dataLists[i]={};
        getCheckedValue('select','select',dataList,i,tableId);
        getCheckedValue('checkbox','checkbox',dataList,i,tableId);
        getCheckedValue('radio','radio',dataList,i,tableId);
        getCheckedValue('datetimepicker','datetimepicker',dataList,i,tableId);
        getCheckedValue('edit','edit',dataList,i,tableId);
		addData(dataLists[i],"selected",tableId);
		addData(dataLists[i],"radioed",tableId);
		addData(dataLists[i],"checkboxed",tableId);
		addData(dataLists[i],"time",tableId);
		addData(dataLists[i],"edit",tableId);
		tableDate[tableId].push(dataLists[i]);
    }
	
	/*console.log("全部数据");
	console.log(tableDate);
	console.log("表单数据");
    console.log(tableDate[tableId]);
	console.log("模型数据");
	console.log(dataList);*/
	localStorage.setItem(tableId,JSON.stringify(tableDate[tableId]));
	console.log(JSON.stringify(tableDate[tableId]));
	
	return tableDate[tableId];
    /*alert(JSON.stringify(tableDate[tableId]));*/
	/*var test=localStorage.getItem(tableId);
	console.log(test);*/
}

/*编辑与不可编辑状态切换*/
function editFalse(data){
	$("#tableRule tr").each(function(){
        var _selfTr=$(this);
        var dataGroupNum=_selfTr.attr("data-group");
        _selfTr.children("td").each(function(){
        	var _self=$(this);
        	var type=_self.attr("data-editabletype");
	            var dataName=_self.attr("data-columnname");
	            var timeName=_self.attr("data-datename");
	           if(type == "edit"){
	            	_self.removeAttr("contenteditable");
	            }else if(type == "datetimepicker"){
	            	_self.children().hide(); 
	            	_self.append("<span data-text='list'>"+data[dataGroupNum][timeName]+"</span>");
	            }else{
	            	_self.children().hide();
	            	if(data[dataGroupNum][dataName] == undefined){
	            		_self.append("<span data-text='list'>"+""+"</span>");
	            	}else{
	            		_self.append("<span data-text='list'>"+data[dataGroupNum][dataName]+"</span>");
	            	}
	            }
        });
    });
}

function addData(dataLists,type,tableId) {
	var data="";
	var dataName="";
	var dataValue="";
	if(type=="selected" || type=="radioed" || type=="checkboxed"){
		for(var m in dataList[type]){
			for(var n in dataList[type][m]){
				data=dataList[type][m][n].split(":");
				dataName=data[0];
				dataValue=data[1];
				dataLists[dataName]=dataValue;
			}
		}
	}else{
		for(var j in dataList[type]){
			data=dataList[type][j].split(":");
			dataName=data[0];
			dataValue=data[1];
			dataLists[dataName]=dataValue;
		}
	}
}

/**
 * 获取select、checkbox、radio选中项
 * @param dataName：自定义组件属性data-editableType
 *         type：组件类型名称select、checkbox、radio
 *         data：多组件下，select、checkbox、radio的所有数据模型
 * return data：{"01":["1","2"]}
 * **/
function getCheckedValue(dataName,type,data,rowNum,tableId)
{
	//select,radio,checkbox
	var myList=data[dataName];
	var checkedList=[];
	var checkedArr=[];
	var columnName="";
	for(var key in  myList)
	{
		if(type=="select")
		{
			$("#"+tableId+" td[data-editableType='"+dataName+"']").each(function(){
				var _self=$(this);
				if(_self.attr("data-group") == rowNum){
					var _selfVal=_self.find("select").val();
					columnName=_self.attr("data-columnName");
					checkedArr.push(columnName+":"+_selfVal);
				}
			});
		}else if(type=="checkbox" || type=="radio"){
			var checkValue="";
			$("#"+tableId+" input[type='"+dataName+"'][name='"+key+rowNum+"']").each(function(){
				var _self=$(this);
				var _selfVal=_self.val();
				var _selfRowNum=_self.attr("name").split(key)[1];
				if(_selfRowNum == rowNum){
					var checkFlag=_self.prop("checked");
					columnName=_self.attr("columnName");
					if(checkFlag)
					{
						checkValue += (_selfVal+",");
					}
				}
			});
			var checked=checkValue.substr(0,checkValue.length-1); 
			checkedArr.push(columnName+":"+checked);
		}
		checkedList[key]=checkedArr;
	}

	if(type == "datetimepicker"){
		$("#"+tableId+" td[data-editableType='"+dataName+"']").each(function(){
			var _self=$(this);
			if(_self.attr("data-group") == rowNum){
				var _selfName=_self.attr("data-datename");
				var _selfVal=_self.find("input").val();
				checkedArr.push(_selfName+":"+_selfVal);
			}
		});
	}
	if(type == "edit"){
		$("#"+tableId+" td[data-editableType='"+dataName+"']").each(function(){
			var _self=$(this);
			if(_self.attr("data-group") == rowNum){
				var _selfName=_self.attr("data-datename");
				if(_self.children("div").html()!=undefined){
					var _selfVal=_self.children("div").html().replace("&nbsp;","").replace(/>\/?.+?</gi,"").replace(/<\/?.+?>/gi,"");
					checkedArr.push(_selfName+":"+_selfVal);
				}else{
					var _selfVal=_self.html();
					checkedArr.push(_selfName+":"+_selfVal);
				}
				
				
			}
		});
	}
	switch (type)
	{
		case "checkbox":
			data["checkboxed"]=checkedList;
			break;
		case "radio":
			data["radioed"]=checkedList;
			break;
		case "select":
			data["selected"]=checkedList;
			break;
		case "datetimepicker":
			data["time"]=checkedArr;
			break;
		case "edit":
			data["edit"]=checkedArr;
			break;
	}
	return data;
}

