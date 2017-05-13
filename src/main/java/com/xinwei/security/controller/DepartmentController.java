 
package com.xinwei.security.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.security.entity.Department;
import com.xinwei.security.service.DepartmentService;
import com.xinwei.security.service.UserService;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/management/department")
public class DepartmentController {

	@Autowired
	private DepartmentService departmentService;
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("preloadDepartment")
	public Department getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Department department = departmentService.get(id);
			return department;
		}
		return null;
	}
	
	
	/**
	 * 创建部门
	 * @param department
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(Department department) {	
		departmentService.save(department);
		return new ResultVO<>().toString();
	}
	
	
	/**
	 * 修改部门\移动部门
	 * @param department
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(@ModelAttribute("preloadDepartment")Department department) 
	{
		departmentService.update(department);
		return new ResultVO<>().toString();
	}
	
	
	
	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delete(Long id) {
		departmentService.delete(id);
		return new ResultVO<>().toString();
	}
	
	
	/**
	 * 删除部门
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/moveUser", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String moveUser(@RequestParam(value = "userIds[]", required = false)List<Long> userIds,Long department_id) {
		userService.moveDepartment(userIds, department_id);
		return new ResultVO<>().toString();
	}
	
	
	/**
	 * 获取单个部门信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getDepartment", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getDepartment(Long id) {
		Department department = departmentService.get(id);
		ResultVO<Department> result = new ResultVO<>();
		result.setEntity(department);
		return result.toString();
	}
	
	
	/**
	 * 获取所有的部门信息（树）
	 * @return
	 */
	@RequestMapping(value="/getAllModules", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllDepartment() {
		
		List<Department> departments = departmentService.getAllDepartmentTree();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("departments", departments);
		return result.toString();
	}

}
