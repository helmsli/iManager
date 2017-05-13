package com.xinwei.process.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.constant.ApprovedConstants;
import com.xinwei.process.dao.CompanyMapper;
import com.xinwei.process.entity.Company;
import com.xinwei.process.service.CompanyService;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.page.Page;

@Service
public class CompanyServiceImpl implements CompanyService {
	private final String COMPANY_SEQ = "company_seq";//单位编号
	@Autowired
	private CompanyMapper dao;

	@Resource
	private XwSysSeqService xwSysSeqService;

	@Override
	public Company selectByPrimaryKey(Long companyId) {
		return dao.selectByPrimaryKey(companyId);
	}

	@Override
	public void delete(Long companyId) {
		dao.deleteByPrimaryKey(companyId);
	}

	@Override
	public void update(Company company) {
		dao.updateByPrimaryKey(company);
	}

	@Override
	public Long save(Company company) {
		// 生成单位编号
		Long companyId = xwSysSeqService.getXwSequence(COMPANY_SEQ, 1)
				.getStartSequence();
		// 设置单位编号
		company.setCompanyId(companyId);
		company.setCreateTime(new Date());
		// 保存
		dao.insert(company);
		return company.getCompanyId();
	}

	@Override
	public void updateCurrentStatusByCompanyId(String currentStatus,
			Long companyId) {
		dao.updateCurrentStatusByCompanyId(currentStatus,companyId);
	}

	@Override
	public Page<Company> selectApprovedCompanies() {
		Page<Company> page = new Page<Company>(dao.countApprovedCompanies());
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(dao.selectApprovedCompanies(map));
		return page;
	}

	@Override
	public List<Company> selectApprovedCompaniesIdAndName() {
		return dao.selectApprovedCompaniesIdAndName();
	}

	@Override
	public Company selectApprovedCompaniesByName(String companyName) {
		return dao.selectApprovedCompaniesByName(companyName);
	}

	@Override
	public Company selectCompaniesByName(String companyName) {
		
		return dao.selectCompaniesByName(companyName);
	}
}
