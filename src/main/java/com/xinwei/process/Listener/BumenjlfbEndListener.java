package com.xinwei.process.Listener;

import javax.annotation.Resource;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.xinwei.process.constant.DepartmentLeaderPublishConstants;
import com.xinwei.process.entity.StateInfo;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ThreeleaderApplicationService;
@Service
public class BumenjlfbEndListener implements ExecutionListener {
	
	private Logger logger = LoggerFactory.getLogger(BumenjlfbEndListener.class);
	
	@Resource
	private ProcessService processServiceImpl;//流程相关服务
	@Resource
	private ThreeleaderApplicationService threeleaderApplicationServiceImpl;//三级部门经理申报服务
	@Override
	public void notify(DelegateExecution execution) throws Exception {
		String businessKey = execution.getBusinessKey();// projectID
		if (null != businessKey) {
			try {
				Long dataId = processServiceImpl.getDataIdByBusinessKey(businessKey);
				// 更新申报状态
				threeleaderApplicationServiceImpl.updateStateByApplicationId(DepartmentLeaderPublishConstants.State.PM_BUMENJLFB_END, dataId);
				// 构建StateInfo对象
				StateInfo currentStateInfo = new StateInfo();
				currentStateInfo.setState(DepartmentLeaderPublishConstants.State.PM_BUMENJLFB_END);
				currentStateInfo.setStateName(DepartmentLeaderPublishConstants.State.PM_BUMENJLFB_END);
				currentStateInfo.setTaskId(null);
				//更改申报流程当前任务状态信息
				String currentStateInfoJson = currentStateInfo.toString();
				threeleaderApplicationServiceImpl.updateCurrentStateByApplicationId(currentStateInfoJson, dataId);
			} catch (Exception e) {
				logger.debug("BumenjlfbEndListener Exception --> businessKey: "+ businessKey);
				e.printStackTrace();
			}
		}
	}
}