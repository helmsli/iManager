package com.xinwei.process.service;

import java.util.List;
import java.util.Map;

import com.xinwei.process.entity.Company;
import com.xinwei.util.page.Page;

public interface CompanyService{
	
	/**
	 * 保存
	 */
	Long save(Company company);

	/**
	 * 删除
	 */
	void delete(Long companyId);
	
	/**
	 * 修改
	 */
	void update(Company company);
	
	/**
	 * 获取ById
	 */
	Company selectByPrimaryKey(Long companyId) ;
	
	/**
     * 分页查询所有审核通过的单位信息
     */
    Page<Company> selectApprovedCompanies();
	
    /**
     * 查询所有审核通过的单位信息（只包含单位ID、单位名称）
     */
	List<Company> selectApprovedCompaniesIdAndName();
	
	/**
	 * 根据单位名称查询所有审核通过的单位信息
	 * @param companyName 单位名称
	 * @return
	 */
	Company selectApprovedCompaniesByName(String companyName);
	
	/**
	 * 根据单位名称查询所有单位信息(包含待审核的单位)
	 * @param companyName 单位名称
	 * @return
	 */
	Company selectCompaniesByName(String companyName);
	
	/**
	 * 修改当前状态（未审核/已审核）
	 */
	void updateCurrentStatusByCompanyId(String currentStatus,Long companyId);
	
}
