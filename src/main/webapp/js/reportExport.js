App.controller('reportExportCtrl',['$scope','$ocLazyLoad', function($scope, $ocLazyLoad){
	$scope.loadBootstrap = function(){
		var myFileList=[];
		myFileList=loadFileList(myFileList);
		console.log("要加载的文件:"+myFileList);
        $ocLazyLoad.load(myFileList);
    }
    
    $scope.loadBootstrap();
  
    /*操作步骤初始化变量*/
    $scope.stepParam={
    	currteStep:0,
    	previous:false,
    	next:true
    }

    $scope.chooseTypeStep=function(step)
    {
    	$scope.stepParam.currteStep=step;
    	$("#stepLi li").eq(step).addClass("current").siblings("li").removeClass("current");
    	$("#stepTemplate .row").each(function(){
    		var _this=$(this);
    		var index=_this.index();
    		if(index==step)
    		{
    			_this.addClass("show").removeClass("hide");
    		}else{
    			_this.addClass("hide").removeClass("show");
    		
    		}
    	})
    	if(step==0){
    		$scope.stepParam.previous=false;
    	}else{
    		$scope.stepParam.previous=true;
    	}
    	if(step==5){
    		$scope.stepParam.next=false;
    	}else{
    		$scope.stepParam.next=true;
    	}
    
    }
    
    $scope.chooseStepType=function(type)
    {
    	if(type==0)
    	{
    		 $scope.chooseTypeStep($scope.stepParam.currteStep+1);
    	}else{
    		 $scope.chooseTypeStep($scope.stepParam.currteStep-1);
    	}
    	
    }
    
}]);
