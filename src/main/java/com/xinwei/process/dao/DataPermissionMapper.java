package com.xinwei.process.dao;

import com.xinwei.process.entity.DataPermission;

import java.util.List;
import java.util.Map;

public interface DataPermissionMapper {
    int insert(DataPermission record);

    List<DataPermission> selectAll();

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