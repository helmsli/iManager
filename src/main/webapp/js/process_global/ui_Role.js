function userRoleController()
{
	
	var buttonFlag={
			p_button_view:true,
			p_button_modify:false,
			pc_button_create:false,
			pc_button_waitApproval:false,
			pc_button_cancel:false,
			pc_button_confirm:false,
			//我的代办任务列表的按钮
			p_button_approval:false,
			p_button_survey:false,
			p_button_period_report:false,
			p_button_middle_report:false,
			p_button_end_report:false,
			p_button_view:false
			
	};
	
	var userRole=getUserRole();
	setButtonFlag(userRole,state);
}

function getUserRole()
{
	var userId=getUserInfo();
	parm=userId;
	return {};
}

function setButtonFlag(data,state)
{
	for(var i=0; i<data.length;i++)
	{
		if(data[i].name=="taskExchange" && state==008)
		{
			p_button_approval=true;
		}
		
	}

}
