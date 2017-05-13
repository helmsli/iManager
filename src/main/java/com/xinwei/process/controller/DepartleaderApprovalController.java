 
package com.xinwei.process.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.process.entity.DepartleaderApproval;
import com.xinwei.process.service.DepartleaderApprovalService;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/process/departleaderapproval")
public class DepartleaderApprovalController {

	@Autowired
	private DepartleaderApprovalService service;

	
	@ModelAttribute("preloadDepartleaderApproval")
	public DepartleaderApproval getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			DepartleaderApproval DepartleaderApproval = service.selectByPrimaryKey(id);
			return DepartleaderApproval;
		}
		return null;
	}
	
	
	/**
	 * save
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(DepartleaderApproval DepartleaderApproval) {	
		service.save(DepartleaderApproval);
		return new ResultVO<>().toString();
	}
	
	
	/**
	 * update
	 * @param department
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(@ModelAttribute("preloadDepartleaderApproval")DepartleaderApproval departleaderApproval) 
	{
		service.update(departleaderApproval);
		return new ResultVO<>().toString();
	}
	
	/**
	 * delete
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delete(Long id) {
		service.delete(id);
		return new ResultVO<>().toString();
	}
	
	/**
	 * 获取所有
	 * @return
	 */
	@RequestMapping(value="/getAllDepartleaderApprovals", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllDepartleaderApprovals() {
		List<DepartleaderApproval> departleaderApprovals = service.selectAll();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("departleaderApprovals", departleaderApprovals);
		return result.toString();
	}
}
