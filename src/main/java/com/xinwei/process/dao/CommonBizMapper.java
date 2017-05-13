package com.xinwei.process.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.xinwei.process.entity.CommonBiz;

public interface CommonBizMapper {
	/**
	 * 根据DataID查询
	 * @param dataId
	 * @return
	 */
    int deleteByPrimaryKey(String dataId);

    void insert(CommonBiz commonBiz);

    CommonBiz selectByPrimaryKey(String dataId);

    List<CommonBiz> selectAll();

    int updateByPrimaryKey(CommonBiz commonBiz);
    
    /**
     * 根据项目标识和状态进行查询
     * @param projectId
     * @param status
     * @return
     */
    List<CommonBiz> selectByProjectIdAndStatus(@Param("projectId")Long projectId, @Param("status") String status);
    
    /**根据项目ID进行查询
     * 
     * @param projectId
     * @return
     */
    List<CommonBiz> selectByProjectId(@Param("projectId")Long projectId);
    
    /**
     * 根据项目标识和业务类型进行查询
     * @param projectId
     * @param serviceType
     * @return
     */
    List<CommonBiz> selectByProjectIdAndServiceType(@Param("projectId")Long projectId,@Param("serviceType")String serviceType);
    
    void updateApprovalChange(String dataId);
	
    /**
     *  更新审批结果result、变更状态status，更新人updatePerson和更新时间 updateTime
     * @param commonBiz
     */
	void updateApprovalChangeByDataId(CommonBiz commonBiz);
	
	/**
	 * 更新变更状态status
	 * @param status
	 * @param dataId
	 */
	void updateChangeStatusByDataId(@Param("status") String status,@Param("dataId") String dataId);
	
	/**
	 * 更新流程信息processInstanceId&taskId
	 * @param processInstanceId
	 * @param taskId
	 * @param dataId
	 */
	void updateProcessInstanceAndTaskIdByDataId(@Param("processInstanceId") String processInstanceId,@Param("taskId") String taskId,@Param("dataId") String dataId);
	
	/**
	 * 统计某个项目类别下的周报和周期监测
	 * @param map
	 * @return
	 */
	Long countMonthReportAndCheck(Map<String, Object> map);

	/**
	 * 分页查询某个项目类别下的周报和周期监测
	 * @param map
	 * @return
	 */
	List<CommonBiz> selectMonthReportAndCheck(Map<String, Object> map);
	
	/**
	 * 根据项目类别、业务类型、创建人和项目名称（支持模糊查询）进行统计
	 * @param map
	 * @return
	 */
	Long countByCategoryTypePersonProjectName(Map<String, Object> map);
	
	/**
	 * 根据项目类别、业务类型、创建人和项目名称（支持模糊查询）进行分页查询
	 * @param map
	 * @return
	 */
	List<CommonBiz> selectByCategoryTypePersonProjectName(
			Map<String, Object> map);
	
	
}