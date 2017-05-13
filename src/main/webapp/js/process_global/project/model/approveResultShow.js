var projectShow={
	begainProjectShow:false,
	middleProjectShow:false,
	LastProjectShow:false
}
var  expertList={
				firstExpertList:{},
				middleExpertList:{},
				lastExpertList:{}
         }

var  experScore={
		    firstexperScore:{},
		    middleExperScore:{},
		    lastExperScore:{}
}
var  committeeProposal={
		first:{},
		middle:{},
		last:{}
		
}










































/*
 * 初期中期最终期评审结果的展示
 
var approveResult={
		    expertList:{},
			expertScore:{},
			committee:{
				conclusion:"",
				comments:""
			},
			departLeader:{
				conclusion:"",
				comments:""
			},
	};
var approveResultFlag={
		  expertListFlag:{
			  firstFlag:false,
			  middleFlag:false,
			  lastFlag:false
		  },
		  expertScoreFlag:{
			  firstFlag:false,
			  middleFlag:false,
			  lastFlag:false
		  },
		  committeeFlag:{
			  firstFlag:false,
			  middleFlag:false,
			  lastFlag:false
		  },
		  departLeaderFlag:{
			  firstFlag:false,
			  middleFlag:false,
			  lastFlag:false
		  },
		
}

function refreshApproveResultShowCallback(scope)
{
   	
}
function initApproveResultShow()
{
	var scope=getAngularScope("projectModel");
	var stateName="state_"+scope.gloablParm.state;
	var scope=getAngularScope("projectModel");
	var obj={
			"request.projectId":scope.parm.projectId
		};
	//从后台取到数据，显示在前台as
	queryItem(obj,function(data){
		var projectDetailInfo=data.responseInfo.projectDetailInfo;
		var resultObject=null;
		
		
		var myExpreList={
					first:beginsate.exprel;
					middle:middleexpre,
					last:middelexler
				
		}
		
		
		switch(scope.gloablParm.state){
		    
		}
		
	})
	if(typeof scope.registerHtmlFlag[stateName] !="undefined")
	{
		refreshApproveResultShowCallback(scope);
	}
	else
	{
		globalRegisterJs(scope.gloablParm.state,refreshApproveResultShowCallback);
	}
	
}
initApproveResultShow();

*/

