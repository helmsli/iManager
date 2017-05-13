package com.xinwei.process.service;

import java.util.List;

import com.xinwei.process.entity.TaskDefKeyName;

public interface TaskDefKeyNameService {

	/**
	 * 获取所有
	 */
	List<TaskDefKeyName> selectAll();

	/**
	 * 获取ById
	 */
	TaskDefKeyName selectByPrimaryKey(String taskDefKey);
	
	//根据taskDefKey查询状态名称
	String selectStateNameByPrimaryKey(String taskDefKey);
	/**
	 * 保存
	 */
	String save(TaskDefKeyName taskDefKeyName);

	/**
	 * 删除
	 */
	void delete(String taskDefKey);

	/**
	 * 修改
	 */
	void update(TaskDefKeyName taskDefKeyName);
}
