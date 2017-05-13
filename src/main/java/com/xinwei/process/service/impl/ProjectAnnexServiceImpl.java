package com.xinwei.process.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.ProjectAnnexMapper;
import com.xinwei.process.entity.ProjectAnnex;
import com.xinwei.process.service.ProjectAnnexService;
import com.xinwei.security.exception.ExistedException;

@Service
public class ProjectAnnexServiceImpl implements ProjectAnnexService {
	
	@Autowired
	private ProjectAnnexMapper dao;
	
	@Override
	public void delete(Long annex_id) {
		dao.deleteByPrimaryKey(annex_id);
	}

	@Override
	public void update(ProjectAnnex annexType) {
		dao.updateByPrimaryKey(annexType);
	}

	@Override
	public void save(ProjectAnnex projectAnnex) throws ExistedException {
		dao.insert(projectAnnex);
	}

	@Override
	public List<ProjectAnnex> selectAll() {
           return dao.selectAll();
	}

	@Override
	public ProjectAnnex selectByPrimaryKey(Long annex_id) {
		return 	dao.selectByPrimaryKey(annex_id);
	}

	@Override
	public List<ProjectAnnex> selectMonthlyReportByProjectId(Long project_id) {
		return 	dao.selectMonthlyReportByProjectId(project_id);
	}
	
	@Override
	public List<ProjectAnnex> selectByProjectId(Long project_id) {
		// TODO Auto-generated method stub
		return dao.selectByProjectId(project_id);
	}	
	
	@Override
	public List<ProjectAnnex> selectByProjectIdAndTypeId(Long projectId,Long typeId) {
		return 	dao.selectByProjectIdAndTypeId(projectId,typeId);
	}

	@Override
	public int updateProjectIdAndTypeId(Long projectId, Long typeId,
			Long annexId) {
		// TODO Auto-generated method stub
		return dao.updateProjectIdAndTypeId(projectId, typeId, annexId);
	}

	@Override
	public ProjectAnnex selectByAnnexName(String annexName) {
		return dao.selectByAnnexName(annexName);
	}
	
}
