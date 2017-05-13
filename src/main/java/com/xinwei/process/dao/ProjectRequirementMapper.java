package com.xinwei.process.dao;

import com.xinwei.process.entity.ProjectRequirement;
import java.util.List;

public interface ProjectRequirementMapper {
    int deleteByPrimaryKey(Long requirementId);

    int insert(ProjectRequirement record);

    ProjectRequirement selectByPrimaryKey(Long requirementId);

    List<ProjectRequirement> selectAll();

    int updateByPrimaryKey(ProjectRequirement record);
}