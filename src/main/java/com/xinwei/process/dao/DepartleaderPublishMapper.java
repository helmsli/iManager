package com.xinwei.process.dao;

import com.xinwei.process.entity.DepartleaderPublish;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface DepartleaderPublishMapper {
    int deleteByPrimaryKey(Long publishId);

    int insert(DepartleaderPublish record);

    DepartleaderPublish selectByPrimaryKey(Long publishId);

    List<DepartleaderPublish> selectAll();
    
    int updateByPrimaryKey(DepartleaderPublish record);
    
    /**
     * 统计所有发布
     * @return
     */
    Long countAll();
    
    /**
     * 分页获取所有发布
     * @param map
     * @return
     */
    List<DepartleaderPublish> selectAllByPage(Map<String, Object> map);

    /**
     * 根据种类ID统计
     * @param map包含 categoryId
     * @return
     */
    Long countByCategoryId(Map<String, Object> map);
    
    /**
     * 根据种类ID分页查询
     * @param map
     * @return
     */
	List<DepartleaderPublish> selectByCategoryId(Map<String, Object> map);

	/**
	 * 根据发布者进行统计
	 * @param createPerson
	 * @return
	 */
	Long countByUser(String createPerson);

	/**
	 * 根据发布者进行分页查询
	 * @param map
	 * @return
	 */
	List<DepartleaderPublish> selectByUser(Map<String, Object> map);

	/**
	 * 根据发布者和种类Id进行统计
	 * @param queryMap
	 * @return
	 */
	Long countByUserAndCategoryId(Map<String, Object> queryMap);

	/**
	 * 根据发布者和种类Id进行分页查询
	 * @param queryMap
	 * @return
	 */
	List<DepartleaderPublish> selectByUserAndCategoryId(
			Map<String, Object> queryMap);

	/**
	 * 统计用户的可申报列表
	 * @param map
	 * @return
	 */
	Long countApplyListByUser(Map<String, Object> map);
	
	/**
	 * 根据用户分页查询其可申报的列表
	 * @param map
	 * @return
	 */
	List<DepartleaderPublish> selectApplyListByUser(Map<String, Object> map);

	/**
	 * 根据项目种类和用户统计可申报列表
	 * @param queryMap
	 * @return
	 */
	Long countApplyListByUserAndCategoryId(Map<String, Object> map);

	/**
	 * 根据项目种类和用户分页查询可申报列表
	 * @param queryMap
	 * @return
	 */
	List<DepartleaderPublish> selectApplyListByUserAndCategoryId(
			Map<String, Object> map);

	/**
	 * 根据发布id列表查询
	 * @param dataIdList
	 * @return
	 */
	List<DepartleaderPublish> selectByIdList(@Param("idList")List<Long> idList);

}