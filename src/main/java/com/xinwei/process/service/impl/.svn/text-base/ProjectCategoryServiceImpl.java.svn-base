package com.xinwei.process.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.ProjectCategoryMapper;
import com.xinwei.process.entity.ProjectCategory;
import com.xinwei.process.service.ProjectCategoryService;
import com.xinwei.security.exception.ExistedException;

@Service
public class ProjectCategoryServiceImpl implements ProjectCategoryService {
	
	@Autowired
	private ProjectCategoryMapper dao;
	
	@Override
	public void delete(Long category_id) {
		dao.deleteByPrimaryKey(category_id);
	}

	@Override
	public void update(ProjectCategory projectCategory) {
		dao.updateByPrimaryKey(projectCategory);
	}

	@Override
	public void save(ProjectCategory projectCategory) throws ExistedException {
		dao.insert(projectCategory);
	}

	@Override
	public List<ProjectCategory> selectAll() {
           return dao.selectAll();
	}

	@Override
	public ProjectCategory selectByPrimaryKey(Long category_id) {
		return 	dao.selectByPrimaryKey(category_id);
	}
}
