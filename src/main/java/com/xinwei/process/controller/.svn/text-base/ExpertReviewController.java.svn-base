 
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

import com.xinwei.process.entity.ExpertReview;
import com.xinwei.process.service.ExpertReviewService;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/process/expertreview")
public class ExpertReviewController {

	@Autowired
	private ExpertReviewService service;

	
	@ModelAttribute("preloadExpertReview")
	public ExpertReview getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			ExpertReview ExpertReview = service.selectByPrimaryKey(id);
			return ExpertReview;
		}
		return null;
	}
	
	
	/**
	 * save
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(ExpertReview ExpertReview) {	
		service.save(ExpertReview);
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
	public @ResponseBody String update(@ModelAttribute("preloadExpertReview")ExpertReview expertReview) 
	{
		service.update(expertReview);
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
	@RequestMapping(value="/getAllExpertReviews", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllExpertReviews() {
		List<ExpertReview> expertreviews = service.selectAll();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("ExpertReviews", expertreviews);
		return result.toString();
	}
}
