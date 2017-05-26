 
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

import com.xinwei.process.entity.ProjectRequirement;
import com.xinwei.process.service.ProjectRequirementService;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/projectrequirement")
public class ProjectRequirementController {

	@Autowired
	private ProjectRequirementService service;

	
	@ModelAttribute("preloadProjectRequirement")
	public ProjectRequirement getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			ProjectRequirement ProjectRequirement = service.selectByPrimaryKey(id);
			return ProjectRequirement;
		}
		return null;
	}
	
	
	/**
	 * save
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(ProjectRequirement ProjectRequirement) {	
		service.save(ProjectRequirement);
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
	public @ResponseBody String update(@ModelAttribute("preloadProjectRequirement")ProjectRequirement projectRequirement) 
	{
		service.update(projectRequirement);
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
	@RequestMapping(value="/getAllProjectRequirements", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllProjectRequirements() {
		List<ProjectRequirement> projectRequirements = service.selectAll();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("projectRequirements", projectRequirements);
		return result.toString();
	}
}
