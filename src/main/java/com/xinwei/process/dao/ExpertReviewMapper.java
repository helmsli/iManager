package com.xinwei.process.dao;

import com.xinwei.process.entity.ExpertReview;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ExpertReviewMapper {
    int deleteByPrimaryKey(Long recordId);

    int insert(ExpertReview record);

    ExpertReview selectByPrimaryKey(Long recordId);

    List<ExpertReview> selectAll();

    int updateByPrimaryKey(ExpertReview record);

    List<ExpertReview> getExpertReviewByProjectId(Long projectId);
    
    List<ExpertReview> getExpertReviewByStage(@Param("projectId")Long projectId,@Param("stage") String stage);
}