package com.xinwei.process.dao;

import com.xinwei.process.entity.DataCreateInfo;
import java.util.List;
import java.util.Map;

public interface DataCreateInfoMapper {
    int insert(DataCreateInfo record);

    List<DataCreateInfo> selectAll();
    
    /**
	 * 根据查询条件进行查询
	 * @param queryMap
	 * @return dataId集合
	 */
	List<String> selectByConditions(
			Map<String, Object> queryMap);

    /**
     * 根据查询条件进行统计
     * @param queryMap
     * @return 记录数
     */
	Long countByConditions(Map<String, Object> queryMap);

	/**
	 * 根据查询条件进行分页查询
	 * @param queryMap
	 * @return dataId集合
	 */
	List<String> selectListByConditions(
			Map<String, Object> queryMap);
     
}