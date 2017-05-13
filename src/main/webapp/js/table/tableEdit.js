/*定义组件select、checkbox、radio数据模型*/
//组件模型数据示例
/*var dataModel={
    selectArr:{
        "01":["开发","测试","产品"],
        "02":["sfdsafd","测试","产品"]
         },
    chooseArr:{
        "01":[{text:"1年",value:"1"},
            {text:"2年",value:"2"}],
        "02": [{text:"核心网",value:"5"},
            {text:"网管",value:"6"}]
    }
};*/

/*初始化表格数据源*/
var dataListEdit={
    select:{},
    radio:{},
    checkbox:{}
};

/*初始化函数*/
function initTableEdit(dataModel,tableId){
	initEdit(dataModel,tableId);
}

function initEdit(data,tableId) {
    /*组件初始化*/
    componentsInit(data,tableId);
}

/*定义表格需加入的组件并初始化组件*/
function componentsInit(data,tableId) {
    /*初始化组件select、radio、checkbox、datePicker*/
    initSelect(data.selectArr,tableId);
    initRadio(data.chooseArr,tableId);
    initCheckbox(data.chooseArr,tableId);
    initDatePicker('yyyy-MM-dd',tableId);

    /*可编辑输入框*/
    editableToInput(tableId);
}

function editableToInput(tableId) {
    /*可编辑输入框转换*/
    var editable=$("#"+tableId+" tr td[data-editable='edit']");
    editable.attr("data-editValue","");
    editable.append("<div contentEditable='true' onblur='javascript:getTextEditValue(this)' onafterpaste='javascript:getTextEditValue(this)' class='innDiv'>&nbsp;</div>");
    //editable.attr("contentEditable",true);
    //editable.bind("click", dataEdit);
    
    /*下拉框数据切换绑定事件*/
    $(".dropdown-menu li a").bind("click",dataSelect);
}


//可编辑输入框td的data-editValue属性赋值
function getTextEditValue(obj){
	var oldHtml=$(obj).html();
	var newHTML=obj.innerHTML;
	var tableTdValue=obj.innerHTML.replace("&nbsp;","").replace(/>\/?.+?</gi,"").replace(/<\/?.+?>/gi,"");
	obj.innerText=tableTdValue;
	$(obj).parent("td").attr("data-editValue",tableTdValue);
}

//下拉框数据切换
function dataSelect(e) {
    var selected=$(e.currentTarget).text();
    $("#role").text(selected);
    dataListEdit.role=selected;
}

/**
 * 初始化select组件
 * @param selectArr：定义组件数据模型
 * */
function initSelect(selectArr,tableId)
{
    initCommon("select",selectArr,selectEditTemplate,'',tableId);
}

/**
 * 初始化radio组件
 * @param chooseArr：定义组件数据模型
 * */
function initRadio(chooseArr,tableId)
{
    initCommon("radio",chooseArr,radioEditTemplate,'',tableId);
}

/**
 * 初始化checkbox组件
 * @param chooseArr：定义组件数据模型
 * */
function initCheckbox(chooseArr,tableId)
{
    initCommon("checkbox",chooseArr,checkboxEditTemplate,'',tableId);
}

/**
 * 初始化时间组件
 * @param format：定义时间格式
* */
function initDatePicker(format,tableId)
{
    initCommon("datetimepicker","","",format,tableId);
}

/**
 * 加载组件
 * @param dataType:自定义组件属性data-editableType
 *         arr:组件数据模型[]
 *         template:组件模板selectTemplate、radioTemplate、checkboxTemplate、dateTemplate
 *         format:日期格式
* */
function initCommon(dataType,arr,template,format,tableId)
{
    $("#"+tableId+" tr td[data-editableType="+dataType+"]").each(function(){
        var _self=$(this);
        var datename=_self.attr("data-datename");
        _self.attr("id",datename);
        var _thisId=datename;

        if(dataType=="datetimepicker")
        {
        	 
            _self.append(dateTemplate(datename));//加上时间的id
            console.log(format);
            $('#'+_thisId).datetimepicker({
                format: 'yyyy-MM-dd'
            });
        }else{
            var myData=arr[datename];
            var html=template(myData,datename);
            initEditModel(datename,myData,dataType);
            _self.append(html);
        }
    });
}

/**
 * 初始化数据模型
 * @param dataType:组件类型名称select、checkbox、radio
 *         name:单个组件标识字段名称
 *         data:单个组件对应的数据模型[]
 * */
function initEditModel(name,data,dataType)
{
	dataListEdit[dataType][name]=data;
}

//可编辑输入框
function dataEdit(e) {
    $(e.currentTarget).attr("contentEditable",true);
}

/*保存表格数据*/
function tableEditSave(tableId){
    getCheckedVal('select','select',dataListEdit,tableId);
    getCheckedVal('checkbox','checkbox',dataListEdit,tableId);
    getCheckedVal('radio','radio',dataListEdit,tableId);
    dataListEdit.startTime=$('#startTime input').val();
    dataListEdit.endTime=$('#endTime input').val();
    getEditVaule(tableId);
  
   // console.log(tableId + ":******:" + dataListEdit);
    console.log(dataListEdit);
    localStorage.setItem(tableId,JSON.stringify(dataListEdit));
};

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
function selectEditTemplate(arr,name) {
    var selectList="";
    for(var i in arr){
        var selectLi= '<option value='+arr[i]+'>'+arr[i]+'</option>';
        selectList += selectLi;
    }
    return '<div class="form-line">'+
            '<select class="form-control show-tick" id="role" name='+name+'>'+
            '<option value="">请选择类型</option>'+selectList+
            '</select>'+
            '</div>'
            
}

/**
 * 日期模板
 * @param id:单个日期组件名称data-datename
 * return dateTemplate日期组件
 * */
function dateTemplate(id) { 	
 return 	'<div id="'+id+'" class="input-append date">'+
     '<input  type="text"  class="form-control"/>'+
     '<span class="add-on">'+
     '<i data-time-icon="icon-time" data-date-icon="fa fa-calendar icon-calendar"> </i>'+
     '</span>'+
     '</div>';
}

/**
 * 单选框模板
 * @param arr:组件数据模型[]
 *        name：自定义组件属性data-editableType
 * return radioList组件
 * */
function radioEditTemplate(arr,name) {
    var radioList="";
    for(var i in arr){
    	var radio="";
    	if(i==0){
    		 radio='<div class="radio-inline">'+
              '<input type="radio" checked="true" class="with-gap radio-col-blue" id="'+i+name+'" name="'+name+'" value="'+arr[i].value+'">'+
              '<label for="'+i+name+'">'+arr[i].text+'</label>'+
              '</div>';
    	}
    	else{
    		   radio='<div class="radio-inline">'+
              '<input type="radio" class="with-gap radio-col-blue" id="'+i+name+'" name="'+name+'" value="'+arr[i].value+'">'+
              '<label for="'+i+name+'">'+arr[i].text+'</label>'+
              '</div>';
        	}
            radioList += radio;
        }
        return radioList;
    }
   // function radioCheckde()
/**
* 复选框模板
* @param arr:组件数据模型[]
*        name：自定义组件属性data-editableType
* return checkboxList组件
* */
function checkboxEditTemplate(arr,name) {
    var checkboxList="";
    for(var i in arr){
        var checkbox='<div class="checkbox-inline">'+
                '<input type="checkbox" class="filled-in chk-col-blue" id="#'+i+'" name="'+name+'" value="'+arr[i].value+'">'+
                '<label for="#'+i+'">'+arr[i].text+'</label>'+
                '</div>';
        checkboxList += checkbox;
    }
    return checkboxList;
}

/**
 * 获取select、checkbox、radio选中项
 * @param dataName：自定义组件属性data-editableType
 *         type：组件类型名称select、checkbox、radio
 *         data：多组件下，select、checkbox、radio的所有数据模型
 * return data：{"01":["1","2"]}
 * **/
function getCheckedVal(dataName,type,data,tableId)
{
    //select ,radio,checkbox
    var myList=data[dataName];
    var checkedList={};
    for(var key in  myList)
    {
        var checkedArr=[];
        if(type=="select")
        {
            $("#"+tableId+" td[data-editableType='"+dataName+"']").each(function(){
                var _self=$(this);
                var _selfVal=_self.find("select").val();
                checkedArr.push(_selfVal);
            })
        }else{
            $("#"+tableId+" input[type='"+dataName+"'][name='"+key+"']").each(function(){
                var _self=$(this);
                var _selfVal=_self.val();
                var _selfId=_self.prop("id");
                if(type=="checkbox" || type== "radio" )
                {
                    var checkFlag=_self.prop("checked");
                    if(checkFlag)
                    {
                        checkedArr.push({"value":_selfVal,"id":_selfId});
                        
                    }
                }
            })
        }
        checkedList[key]=checkedArr;
        
    }
    switch (type)
    {
        case "checkbox":
            data["checkboxed"]=checkedList;
            break;
        case "radio":
            data["radioed"]=checkedList;
            break;
        default:
            data["selected"]=checkedList;
    }
    return data;
}

/*获取可编辑td数据*/
function getEditVaule(tableId){
	$("#"+tableId+" td[data-editable='edit']").each(function(){
        var _self=$(this);
        var _selfVal="";
        _selfVal=_self.children("div").html().replace("&nbsp;","").replace(/>\/?.+?</gi,"").replace(/<\/?.+?>/gi,"");
        var id=_self.prop("id");
        dataListEdit[id]=_selfVal; 
    });
}
	  
	
	