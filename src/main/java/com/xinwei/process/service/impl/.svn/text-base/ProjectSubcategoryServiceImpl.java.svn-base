package com.xinwei.process.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.ProjectSubcategoryMapper;
import com.xinwei.process.entity.ProjectSubcategory;
import com.xinwei.process.service.ProjectSubcategoryService;
import com.xinwei.security.exception.ExistedException;

@Service
public class ProjectSubcategoryServiceImpl implements ProjectSubcategoryService {
	
	@Autowired
	private ProjectSubcategoryMapper dao;
	
	@Override
	public void delete(Long subcategory_id) {
		dao.deleteByPrimaryKey(subcategory_id);
	}

	@Override
	public void update(ProjectSubcategory projectSubcategory) {
		dao.updateByPrimaryKey(projectSubcategory);
	}

	@Override
	public void save(ProjectSubcategory projectSubcategory) throws ExistedException {
		dao.insert(projectSubcategory);
	}

	@Override
	public List<ProjectSubcategory> selectAll() {
           return dao.selectAll();
	}

	@Override
	public ProjectSubcategory selectByPrimaryKey(Long subcategory_id) {
		return 	dao.selectByPrimaryKey(subcategory_id);
	}
}
