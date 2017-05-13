package com.xinwei.process.dao;

import com.xinwei.process.entity.TaskDefKeyName;

import java.util.List;

public interface TaskDefKeyNameMapper {
    int deleteByPrimaryKey(String taskDefKey);

    int insert(TaskDefKeyName record);

    TaskDefKeyName selectByPrimaryKey(String taskDefKey);
    
    String selectStateNameByPrimaryKey(String taskDefKey);

    List<TaskDefKeyName> selectAll();

    int updateByPrimaryKey(TaskDefKeyName record);
}