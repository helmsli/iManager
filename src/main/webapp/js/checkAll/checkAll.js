/**
 * checkbox选择框插件

 * @ 参数： checkbox name;
 * <input type="checkbox" name="testCheckbox" value="123" />
 * 调用方法
 *  var tesCheckAPI=new CheckBoxApi(testCheckbox);
 * tesCheckAPI.setAllChecked(checkFlag) //参数:bool false/true;
 * tesCheckAPI.reverseCheck() //反选
 * tesCheckAPI.getCheckAllValue() //获取选中的和非选择的checkbox value 值; 返回 {checkedAll:[],nocheckAll:[]}
 */
function CheckBoxApi(options)
{
	this.options=options;
	this.checkBoxName=options;
	this.checkBox=document.querySelectorAll("input[type='checkbox'][name="+options+"]");
}

CheckBoxApi.prototype.setAllChecked=function(checkedFlag)
{
	var _self=this;
	var checkBox=_self.checkBox;
	for(var i=0;i<checkBox.length;i++)
	{
		checkBox[i].checked=checkedFlag;
	}
}

CheckBoxApi.prototype.reverseCheck=function()
{
	
	var _self=this;
	var checkBox=_self.checkBox;
	for(var i=0;i<checkBox.length;i++)
	{
		var checkFlag=checkBox[i].checked;
		checkBox[i].checked=(checkFlag)?false:true;
	}
}
CheckBoxApi.prototype.resetCheckbox=function()
{
	var _self=this;
	var checkBox=_self.checkBox;
	for(var i=0;i<checkBox.length;i++)
	{
		checkBox[i].checked=false;
	}
}

CheckBoxApi.prototype.getCheckAllValue=function()
{
		var myCheckBox={};
		var checkedVal=[];
		var nocheckAll=[];
		var _self=this;
		var checkBox=_self.checkBox;
		for(var i=0;i<checkBox.length;i++)
		{
			var value=checkBox[i].value;
			if(checkBox[i].checked==true)
			{
				checkedVal.push(value);
			}else{
				nocheckAll.push(value);
			}
		}
		myCheckBox={
					checkedAll:checkedVal,
					nocheckAll:nocheckAll
					};
		return myCheckBox;
}

