package com.xinwei.process.service;

import java.util.List;
import com.xinwei.process.entity.DepartleaderApproval;

public interface DepartleaderApprovalService{
	
	/**
	 * 获取所有
	 */
	List<DepartleaderApproval> selectAll();
	
	
	/**
	 * 获取ById
	 */
	DepartleaderApproval selectByPrimaryKey(Long record_id);

	/**
	 * 保存
	 */
	Long save(DepartleaderApproval departleaderApproval);

	/**
	 * 删除
	 */
	void delete(Long record_id);
	
	/**
	 * 修改
	 */
	void update(DepartleaderApproval departleaderApproval);
	
	/**
	 * 获取阶段性项目评审详情
	 */
	 List<DepartleaderApproval> getDepartleaderApprovalByStage(Long project_id,String stage);

}
