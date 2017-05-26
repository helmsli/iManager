package com.xinwei.process.dao;

import com.xinwei.process.entity.Company;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CompanyMapper {
    
	int deleteByPrimaryKey(Long companyId);

    int insert(Company company);

    int updateByPrimaryKey(Company record);
    
    Company selectByPrimaryKey(Long companyId);
    
    /**
     * 统计所有审核通过的单位信息
     */
    Long countApprovedCompanies();
    
    /**
     * 分页查询所有审核通过的单位信息
     */
    List<Company> selectApprovedCompanies(Map<String,Object> map);

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
	 * 修改单位的当前审核状态 
	 * @param currentStatus 审核状态
	 * @param companyId 单位ID
	 */
	void updateCurrentStatusByCompanyId(@Param("currentStatus") String currentStatus,@Param("companyId") Long companyId);
	
}