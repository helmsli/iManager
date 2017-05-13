package com.xinwei.process.service;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Service;

@Service("monthlyReportTimerTask")
public class MonthlyReportTimerTask implements JavaDelegate {
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		System.out.println("通知用户，周期性报告提交处理完毕...");
	}	
}