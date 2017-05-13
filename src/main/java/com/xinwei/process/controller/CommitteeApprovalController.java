 
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

import com.xinwei.process.entity.CommitteeApproval;
import com.xinwei.process.service.CommitteeApprovalService;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/process/committeeapproval")
public class CommitteeApprovalController {

	@Autowired
	private CommitteeApprovalService service;

	
	@ModelAttribute("preloadCommitteeApproval")
	public CommitteeApproval getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			CommitteeApproval CommitteeApproval = service.selectByPrimaryKey(id);
			return CommitteeApproval;
		}
		return null;
	}
	
	
	/**
	 * save
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(CommitteeApproval CommitteeApproval) {	
		service.save(CommitteeApproval);
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
	public @ResponseBody String update(@ModelAttribute("preloadCommitteeApproval")CommitteeApproval committeeApproval) 
	{
		service.update(committeeApproval);
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
	@RequestMapping(value="/getAllCommitteeApprovals", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllCommitteeApprovals() {
		List<CommitteeApproval> committeeApprovals = service.selectAll();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("committeeApprovals", committeeApprovals);
		return result.toString();
	}
}
