package com.xinwei.process.service;

import java.util.List;
import java.util.Map;

import com.xinwei.process.entity.DataPermission;

public interface DataPermissionService {

	/**
	 * 保存
	 * @param record
	 * @return
	 */
	int save(DataPermission record);

	/**
	 * 获取所有
	 * @return
	 */
    List<DataPermission> getAll();
    
    /**
     * 根据条件进行删除
     * @param queryMap
     */
    void deleteByConditions(Map<String, Object> queryMap);
    
    /**
     * 根据查询条件统计
     * @param queryMap
     * @return
     */
	Long countByConditions(Map<String, Object> queryMap);

	/**
	 * 根据查询条件分页查询
	 * @param queryMap
	 * @return
	 */
	List<String> selectListByConditions(Map<String, Object> queryMap);
}
