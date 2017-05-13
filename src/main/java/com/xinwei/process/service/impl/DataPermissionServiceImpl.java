package com.xinwei.process.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinwei.process.dao.DataPermissionMapper;
import com.xinwei.process.entity.DataPermission;
import com.xinwei.process.service.DataPermissionService;

@Service
public class DataPermissionServiceImpl implements DataPermissionService {
	@Resource
	private DataPermissionMapper dataPermissionMapper;
	
	@Override
	public int save(DataPermission record) {
		return dataPermissionMapper.insert(record);
	}

	@Override
	public List<DataPermission> getAll() {
		
		return dataPermissionMapper.selectAll();
	}

	@Override
	public void deleteByConditions(Map<String, Object> queryMap) {
		dataPermissionMapper.deleteByConditions(queryMap);
		
	}

	@Override
	public Long countByConditions(Map<String, Object> queryMap) {
		return dataPermissionMapper.countByConditions(queryMap);
	}

	@Override
	public List<String> selectListByConditions(Map<String, Object> queryMap) {
		return dataPermissionMapper.selectListByConditions(queryMap);
	}

}
