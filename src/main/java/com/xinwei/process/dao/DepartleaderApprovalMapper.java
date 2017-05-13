package com.xinwei.process.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.process.entity.DepartleaderApproval;

public interface DepartleaderApprovalMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(DepartleaderApproval record);

    DepartleaderApproval selectByPrimaryKey(Long recordId);

    List<DepartleaderApproval> selectAll();

    int updateByPrimaryKey(DepartleaderApproval record);
    
    List<DepartleaderApproval> getDepartleaderApprovalByStage(@Param("projectId")Long projectId, @Param("stage") String stage);
}