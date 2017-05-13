 
package com.xinwei.process.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.process.constant.ApprovedConstants;
import com.xinwei.process.entity.Company;
import com.xinwei.process.service.CompanyService;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {

	@Autowired
	private CompanyService service;
	
	@Value("${roleId_admin}")
	private Long roleId_admin;//管理员角色ID
	/**
	 * Ajax校验单位名称
	 * @return
	 */
	@RequestMapping(value="/verifyCompanyName", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String verifyCompanyName(String companyName) {
		Company company = null;
		ResultVO<Object> result = new  ResultVO<>();
		if (companyName != null) {
			company = service.selectCompaniesByName(companyName.trim());
			boolean verifyResult = true;
			//如果查询到的单位对象不为空，校验不通过返回false;
			if(null!= company){
				verifyResult = false;
			}
			//给客户端响应
			result.setOthers("verifyResult", verifyResult);	
		}else{
			result.setResult(ResultVO.FAILURE);
		}
		return result.toString();
	}
	
	/**
	 * 6.12.1.2   新增单位
	 * @return
	 */
	@RequestMapping(value="/createCompany", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(Company company) {	
		ResultVO<Object> result = new  ResultVO<>();
		//获取当前用户信息，如果是管理员则设置单位信息状态为审核通过
		User currentUser = this.getCurrentUser();
		if(null != currentUser){
			List<Role> roles = currentUser.getRoles();
			for(Role role:roles){
				if(roleId_admin.equals(role.getId())){
					//设置单位信息状态为审批通过
					company.setCurrentStatus(ApprovedConstants.ApproveResult.CODE_Agree.toString());
				}
			}
		}
		Long companyId = service.save(company);
		//给客户端响应
		result.setOthers("companyId", companyId);
		return result.toString();
	}
	
	
	/**
	 * 6.12.1.3   修改单位
	 * @param Company
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(Company company) 
	{
		Company oldEntity = service.selectByPrimaryKey(company.getCompanyId());
		company.setCreatePerson(oldEntity.getCreatePerson());
		company.setCreateTime(oldEntity.getCreateTime());
		company.setApprovePerson(oldEntity.getApprovePerson());
		company.setApproveTime(oldEntity.getApproveTime());
		company.setCurrentStatus(oldEntity.getCurrentStatus());
		service.update(company);
		return new ResultVO<>().toString();
	}
	
	/**
	 * 6.12.1.4   删除单位
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delete(Long companyId) {
		service.delete(companyId);
		return new ResultVO<>().toString();
	}
	
	/**
	 * 6.12.1.5   分页查看所有单位信息
	 * @return
	 */
	@RequestMapping(value="/getAllCompanys/list", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllCompanys() {
		Page<Company> page = service.selectApprovedCompanies();
		ResultVO<Company> result = new  ResultVO<>();
		result.setPage(page); 
		result.setLists(page.getList());
		return result.toString();
	}
	
	/**
	 * 6.12.1.5   查看所有单位信息（只包含单位ID、单位名称）
	 * @return
	 */
	@RequestMapping(value="/selectAllCompanyIdsAndNames", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String selectAllCompanyIdsAndNames() {
		List<Company> companyLists = service.selectApprovedCompaniesIdAndName();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("companyLists", companyLists);
		return result.toString();
	}
	
	/**
	6.12.1.6   通过单位名称查看单位信息
	 * @return
	 */
	@RequestMapping(value="/selectByCompanyName", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String selectByCompanyName(String companyName) {
		ResultVO<Object> result = new  ResultVO<>();
		if (companyName != null) {
			Company company = service.selectApprovedCompaniesByName(companyName);
			//给客户端响应
			result.setOthers("company", company);
			return result.toString();
		}
		return null;
	}
	
	
	/**
	 * 6.12.1.7   通过单位ID查看单位信息
	 * selectByPrimaryKey
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/selectByPrimaryKey", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String selectByPrimaryKey(Long companyId) {	
		ResultVO<Object> result = new  ResultVO<>();
		if (companyId!=null) {
			Company company = service.selectByPrimaryKey(companyId);
			//给客户端响应
			result.setOthers("company", company);
			return result.toString();
			
		}
		return "";
	}
}
