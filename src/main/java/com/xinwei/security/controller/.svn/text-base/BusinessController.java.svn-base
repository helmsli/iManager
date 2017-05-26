 
package com.xinwei.security.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.security.entity.Business;
import com.xinwei.security.service.BusinessService;
import com.xinwei.security.shiro.UserHelper;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.date.DateStyle;
import com.xinwei.util.date.DateUtil;
import com.xinwei.util.page.Page;

@Controller
@RequestMapping("/management/business")
public class BusinessController {

	@Autowired
	private BusinessService businessService;
	
	
	
	@ModelAttribute("preloadBusiness")
	public Business getOne(@RequestParam(value = "id", required = false) Long id) {
		if (id != null) {
			Business business = businessService.get(id);
			return business;
		}
		return null;
	}
	
	
	/**
	 * 查询业务列表
	 * @param keywords
	 * @return
	 */
	@RequestMapping(value="/list", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String list(String type,String no,Long deparment_id,String queryKey) {
		
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("type", type);
		queryMap.put("no", no);
		queryMap.put("deparment_id", deparment_id);
		queryMap.put("queryKey", queryKey);
		Page<Business> page = businessService.findByPage(queryMap);
		
		ResultVO<Business> resultVO = new ResultVO<Business>();
		resultVO.setPage(page);
		resultVO.setLists(page.getList());
		
		return resultVO.toString();
	}
	
	
	
	
	/**
	 * 创建业务
	 * @param business
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(Business business) {	
		business.setNo(generateNo());
		business.setCreateTime(new Date());
		business.setCreateUser(UserHelper.getUserId());
		businessService.save(business);
		
		ResultVO<Business> result = new ResultVO<>();
		result.setEntity(business);
		return result.toString();
	}
	
	
	/**
	 * 产生一个日期 + 四位随机数 的 业务编号
	 * @return
	 */
	private String  generateNo(){
		String date = DateUtil.getDate(new Date(), DateStyle.YYYYMMDDHHMMSSSSS );
		return date + ( new Random().nextInt(9999-1000+1)+1000 + "");
	}
	
	
	/**
	 * 获取单个业务信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/getBusiness", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String getBusiness(Long id) {
		Business business = businessService.get(id);
		
		ResultVO<Business> result = new ResultVO<>();
		result.setEntity(business);
		return result.toString();
	}
	
	
	/**
	 * 修改业务（管理员修改业务信息）
	 * @param business
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(@ModelAttribute("preloadBusiness")Business business) 
	{
		business.setUpdateTime(new Date());
		business.setUpdateUser(UserHelper.getUserId());
		businessService.update(business);
		return new ResultVO<>().toString();
	}
	
	
	
	
	
	
	
	/**
	 * 删除业务
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delete(Long id) {
		businessService.delete(id);
		return new ResultVO<>().toString();
	}
	

	
	
	
	
	
}
