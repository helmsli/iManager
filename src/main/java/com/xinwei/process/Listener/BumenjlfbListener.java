package com.xinwei.process.Listener;

import javax.annotation.Resource;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.entity.ThreeleaderApplication;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ThreeleaderApplicationService;
import com.xinwei.util.JsonUtil;

@Service("bumenjlfbListener")
public class BumenjlfbListener implements TaskListener {
	private Logger logger = LoggerFactory.getLogger(BumenjlfbListener.class);
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private ThreeleaderApplicationService threeleaderApplicationServiceImpl;//三级部门经理申报服务
	@Override
	public void notify(DelegateTask delegateTask) {
		
		//获取业务key
		String businessKey = delegateTask.getExecution()
				.getProcessBusinessKey();
		try {
			//获取业务数据Id
			Long dataId = processServiceImpl.getDataIdByBusinessKey(businessKey);
			//获取三级部门经理申报对象
			ThreeleaderApplication threeleaderApplication = threeleaderApplicationServiceImpl.getById(dataId);
			//获取类别
			String categoryId =  threeleaderApplication.getCategoryId().toString();
			//将类别设置到任务表中
			delegateTask.setCategory(categoryId);
			
			//更改申报流程当前任务状态信息
			String currentStateInfoJson = processServiceImpl.getStateInfoByDelegateTask(delegateTask) ;
			threeleaderApplicationServiceImpl.updateCurrentStateByApplicationId(currentStateInfoJson, dataId);
		} catch (Exception e) {
			logger.debug("updateCurrentStateByApplicationId Exception --> task: " + delegateTask.getTaskDefinitionKey() 
					+ ", businessKey: "+ businessKey);
			e.printStackTrace();
		}
	}
}
	