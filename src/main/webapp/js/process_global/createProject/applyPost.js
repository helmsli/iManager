$(function () {	
	/*可编辑表格组件数据模型*/
	var dataModel={
        chooseArr:{
            "01":[{text:"1年",value:"1年"},
                {text:"2年",value:"2年"},
                {text:"3年",value:"3年"},
                {text:"其他",value:"其他"}],
                "02": [{text:"教育",value:"教育"},
                       {text:"劳动就业",value:"劳动就业"},
                       {text:"养护照料",value:"养护照料"},
                       {text:"交通出行",value:"交通出行"},
                       {text:"无障碍",value:"无障碍"},
                       {text:"康复",value:"康复"},
                       {text:"辅助器具",value:"辅助器具"},
                       {text:"文化体育",value:"文化体育"},
                       {text:"其他",value:"其他"}]
        }
    };
   //可编辑规则表格初始化
	initTableEdit(dataModel,"tablePost");
	//init("tablePost",setTfootCall);
	//$("#tablePost tfoot").hide();
});
