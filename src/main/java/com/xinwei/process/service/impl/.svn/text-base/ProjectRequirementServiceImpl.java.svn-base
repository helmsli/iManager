package com.xinwei.process.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.ProjectRequirementMapper;
import com.xinwei.process.entity.ProjectRequirement;
import com.xinwei.process.service.ProjectRequirementService;
import com.xinwei.security.exception.ExistedException;

@Service
public class ProjectRequirementServiceImpl implements ProjectRequirementService {
	
	@Autowired
	private ProjectRequirementMapper dao;
	
	@Override
	public void delete(Long requirement_id) {
		dao.deleteByPrimaryKey(requirement_id);
	}

	@Override
	public void update(ProjectRequirement projectRequirement) {
		dao.updateByPrimaryKey(projectRequirement);
	}

	@Override
	public void save(ProjectRequirement projectRequirement) throws ExistedException {
		dao.insert(projectRequirement);
	}

	@Override
	public List<ProjectRequirement> selectAll() {
           return dao.selectAll();
	}

	@Override
	public ProjectRequirement selectByPrimaryKey(Long requirement_id) {
		return 	dao.selectByPrimaryKey(requirement_id);
	}
}
