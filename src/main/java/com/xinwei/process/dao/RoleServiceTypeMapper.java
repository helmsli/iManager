package com.xinwei.process.dao;

import com.xinwei.process.entity.RoleServiceType;

import java.util.List;

public interface RoleServiceTypeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(RoleServiceType roleServiceType);

    RoleServiceType selectByPrimaryKey(Long id);

    List<RoleServiceType> selectAll();

    int updateByPrimaryKey(RoleServiceType roleServiceType);

	List<String> selectServiceTypeListByRole(Integer roleId);

	
}