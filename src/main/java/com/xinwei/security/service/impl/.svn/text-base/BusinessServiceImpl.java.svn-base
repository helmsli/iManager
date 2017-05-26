 
package com.xinwei.security.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.security.MessageCode;
import com.xinwei.security.dao.BusinessDao;
import com.xinwei.security.entity.Business;
import com.xinwei.security.exception.ExistedException;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.security.service.BusinessService;
import com.xinwei.util.page.Page;

@Service
public class BusinessServiceImpl  implements BusinessService {
	private static final Logger logger = LoggerFactory.getLogger(BusinessServiceImpl.class);
	
	@Autowired
	private BusinessDao businessDao;
	
	
	public void save(Business business) throws ExistedException {	
		businessDao.save(business);
	}

	
	public void update(Business business) {
		if(null == business.getId() || 0 == business.getId()){
			throw new ServiceException(MessageCode.ID_NULL); 
		}
		businessDao.update(business);
	}

	public void delete(Long id) throws ServiceException {
		businessDao.delete(id);
	}

	@Override
	public Business get(Long id) {
		return businessDao.get(id);
	}


	@Override
	public Page<Business> findByPage(Map<String,Object> map) {
		Page<Business> page = new Page<Business>(businessDao.findByPageCount(map ));
		map.put("startRow", page.getStartRow());
		map.put("pageSize", page.getPageSize());
		page.setList(businessDao.findByPage(map));
		return page;
	}


	
	



	


	
}
