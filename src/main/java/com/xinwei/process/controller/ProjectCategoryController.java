 
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

import com.xinwei.process.entity.ProjectCategory;
import com.xinwei.process.service.ProjectCategoryService;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/projectcategory")
public class ProjectCategoryController {

	@Autowired
	private ProjectCategoryService service;

	
	@ModelAttribute("preloadProjectCategory")
	public ProjectCategory getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			ProjectCategory ProjectCategory = service.selectByPrimaryKey(id);
			return ProjectCategory;
		}
		return null;
	}
	
	
	/**
	 * save
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(ProjectCategory ProjectCategory) {	
		service.save(ProjectCategory);
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
	public @ResponseBody String update(@ModelAttribute("preloadProjectCategory")ProjectCategory projectCategory) 
	{
		service.update(projectCategory);
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
	@RequestMapping(value="/getAllProjectCategorys", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllProjectCategorys() {
		List<ProjectCategory> projectcategorys = service.selectAll();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("projectcategorys", projectcategorys);
		return result.toString();
	}
}
