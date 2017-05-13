$(function () {	
	/*可编辑表格组件数据模型*/
	var dataModel={
        chooseArr:{
            "01":[{text:"1年",value:"1年"},
                {text:"2年",value:"2年"},
                {text:"3年",value:"3年"},
                {text:"其他",value:"其他"}],
            "02": [{text:"核心网",value:"核心网"},
                {text:"网管",value:"网管"},
                {text:"互联网",value:"互联网"},
                {text:"Coomarts",value:"Coomarts"},
                {text:"CamTalk",value:"CamTalk"},
                {text:"Coobill",value:"Coobill"},
                {text:"XBOSS",value:"XBOSS"},
                {text:"博彩",value:"博彩"},
                {text:"其他",value:"其他"}]
        }
    };
   //可编辑规则表格初始化
	initTableEdit(dataModel,"tablePost");
	//init("tablePost",setTfootCall);
	//$("#tablePost tfoot").hide();
});
