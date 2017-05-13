package com.xinwei.process.service;

import java.util.List;

import com.xinwei.process.entity.ProjectAnnex;

public interface ProjectAnnexService{
	
	/**
	 * 获取所有
	 */
	List<ProjectAnnex> selectAll();
	
	/**
	 * 获取ById
	 */
	ProjectAnnex selectByPrimaryKey(Long annex_id);

	/**
	 * 根据附件名获取
	 * @param annexName
	 * @return
	 */
	ProjectAnnex selectByAnnexName(String annexName);
	
	/**
	 * 保存
	 */
	void save(ProjectAnnex projectannex);

	/**
	 * 删除
	 */
	void delete(Long annex_id);
	
	/**
	 * 修改
	 */
	void update(ProjectAnnex projectannex);
	
	/**
	 * 获取项目周期报告byProjectId
	 */
	List<ProjectAnnex> selectMonthlyReportByProjectId(Long project_id);

	/**
	 * 通过ProjectId查询
	 * @param project_id 项目ID
	 * @return
	 */
	List<ProjectAnnex> selectByProjectId(Long project_id);
	
	/**
	 * 通过ProjectId和typeId查询
	 * @param project_id 项目ID
	 * @param typeId 类型ID
	 * @return
	 */
	List<ProjectAnnex> selectByProjectIdAndTypeId(Long project_id,Long typeId);
	
	/**
	 * 修改项目ID和类型ID
	 */
	int updateProjectIdAndTypeId(Long projectId, Long typeId,Long annexId);

}
