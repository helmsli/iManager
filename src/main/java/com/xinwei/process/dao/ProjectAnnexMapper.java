package com.xinwei.process.dao;

import com.xinwei.process.entity.ProjectAnnex;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ProjectAnnexMapper {
    int deleteByPrimaryKey(Long annexId);

    int insert(ProjectAnnex record);

    ProjectAnnex selectByPrimaryKey(Long annexId);
 
    List<ProjectAnnex> selectAll();

    int updateByPrimaryKey(ProjectAnnex record);
    
    /**
     * 根据附件名称查询
     * @param annexName
     * @return 附件对象
     */
    ProjectAnnex selectByAnnexName(String annexName);

    List<ProjectAnnex> selectMonthlyReportByProjectId(Long projectId);
   
    List<ProjectAnnex> selectByProjectId(Long projectId);
    
    /**
     * 查询附件（附件、中期评审材料、中期评审报告、终期评审材料、终期评审报告、项目周期报告）
     * @param projectId
     * @param typeId
     * @return 附件列表
     */
    List<ProjectAnnex> selectByProjectIdAndTypeId(@Param("projectId")Long projectId, @Param("typeId")Long typeId);
    
    /**
     * 修改项目ID和类型ID
     * @param projectId
     * @param typeId
     * @param annexId
     * @return
     */
    int updateProjectIdAndTypeId(@Param("projectId")Long projectId, @Param("typeId")Long typeId,@Param("annexId")Long annexId);

}