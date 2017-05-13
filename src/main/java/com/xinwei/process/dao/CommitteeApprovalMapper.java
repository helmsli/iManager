package com.xinwei.process.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xinwei.process.entity.CommitteeApproval;

public interface CommitteeApprovalMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(CommitteeApproval record);

    CommitteeApproval selectByPrimaryKey(Long recordId);

    List<CommitteeApproval> selectAll();

    int updateByPrimaryKey(CommitteeApproval record);
    
    List<CommitteeApproval> getCommitteeApprovalByStage(@Param("projectId")Long projectId, @Param("stage") String stage);

}