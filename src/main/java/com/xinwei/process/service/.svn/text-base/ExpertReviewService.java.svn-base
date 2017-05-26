package com.xinwei.process.service;

import java.util.List;

import com.xinwei.process.entity.ExpertReview;

public interface ExpertReviewService{
	
	/**
	 * 获取所有
	 */
	List<ExpertReview> selectAll();
	
	
	/**
	 * 获取ById
	 */
	ExpertReview selectByPrimaryKey(Long record_id);

	/**
	 * 保存
	 */
	Long save(ExpertReview expertReview);

	/**
	 * 删除
	 */
	void delete(Long record_id);
	
	/**
	 * 修改
	 */
	void update(ExpertReview expertReview);
	
	/**
	 * 根据项目编号获取该项目专家评审记录
	 * @param project_id 项目ID
	 * @return
	 */
	List<ExpertReview> getExpertReviewByProjectId(Long projectId);
	
	/**
	 * 获取阶段性项目评审集合
	 */
	List<ExpertReview> getExpertReviewByStage(Long project_id,String stage);
	

}
