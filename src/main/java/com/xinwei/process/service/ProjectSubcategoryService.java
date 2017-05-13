package com.xinwei.process.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.process.entity.ProjectSubcategory;

public interface ProjectSubcategoryService{
	
	/**
	 * 获取所有
	 */
	List<ProjectSubcategory> selectAll();
	
	/**
	 * 获取ById
	 */
	ProjectSubcategory selectByPrimaryKey(Long subcategory_id);

	/**
	 * 保存
	 */
	void save(ProjectSubcategory projectSubcategory);

	/**
	 * 删除
	 */
	void delete(Long subcategory_id);
	
	/**
	 * 修改
	 */
	void update(ProjectSubcategory projectSubcategory);
}
