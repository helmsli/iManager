package com.xinwei.process.dao;

import com.xinwei.process.entity.ThreeleaderApplication;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ThreeleaderApplicationMapper {
    int deleteByPrimaryKey(Long applicationId);

    int insert(ThreeleaderApplication record);

    ThreeleaderApplication selectByPrimaryKey(Long applicationId);

    List<ThreeleaderApplication> selectAll();

    int updateByPrimaryKey(ThreeleaderApplication record);
    
    /**
     * 根据申报ID更改当前任务状态
     * @param currentState
     * @param applicationId
     * @return
     */
    int updateCurrentStateByApplicationId(@Param("currentState")String currentState, @Param("applicationId")Long applicationId);
    
    /**
     * 根据申报ID更改三级部门经理申报流程实例Id
     * @param processInstanceId
     * @param applicationId
     * @return
     */
    int updateProcessInstanceIdByApplicationId(@Param("processInstanceId")String processInstanceId, @Param("applicationId")Long applicationId);
    
    /**
     * 根据申报ID更改状态（结束标志）
     * @param state
     * @param applicationId
     * @return
     */
    int updateStateByApplicationId(@Param("state")String state, @Param("applicationId")Long applicationId);
    
    /**
     * 根据申报人ID以及种类Id进行统计
     * @param map
     * @return
     */
	Long countByUserIdCategoryId(Map<String, Object> map);

    /**
     * 根据申报人ID以及种类Id进行分页查询
     * @param map
     * @return
     */
	List<ThreeleaderApplication> selectByUserIdCategoryId(Map<String, Object> map);

	/**
     * 根据种类Id进行统计
     * @param map
     * @return
     */
	Long countByCategoryId(Map<String, Object> map);

	/**
     * 根据种类Id进行分页查询
     * @param map
     * @return
     */
	List<ThreeleaderApplication> selectByCategoryId(Map<String, Object> map);

	/**
	 * 根据申报人ID和部门经理发布ID进行查询
	 * @param applyPerson
	 * @param departleaderPublishId
	 * @return
	 */
	ThreeleaderApplication selectByUserIdAndPublishId(@Param("applyPerson")String applyPerson,
			@Param("departleaderPublishId")Long departleaderPublishId);
    
}