package com.xinwei.process.service;

import java.util.List;
import com.xinwei.process.entity.CommitteeApproval;

public interface CommitteeApprovalService{
	
	/**
	 * 获取所有
	 */
	List<CommitteeApproval> selectAll();
	
	
	/**
	 * 获取ById
	 */
	CommitteeApproval selectByPrimaryKey(Long record_id);

	/**
	 * 保存
	 */
	Long save(CommitteeApproval committeeApproval);

	/**
	 * 删除
	 */
	void delete(Long record_id);
	
	/**
	 * 修改
	 */
	void update(CommitteeApproval committeeApproval);
	
	/**
	 * 获取阶段性项目评审详情
	 */
	List<CommitteeApproval> getCommitteeApprovalByStage(Long project_id,String stage);

}
