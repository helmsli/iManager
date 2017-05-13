package com.xinwei.process.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.RoleServiceTypeMapper;
import com.xinwei.process.entity.RoleServiceType;
import com.xinwei.process.service.RoleServiceTypeService;
import com.xinwei.system.xwsequence.service.XwSysSeqService;

@Service
public class RoleServiceTypeServiceImpl implements RoleServiceTypeService {
	private final String ROLESERVICETYPE_SEQ = "roleservicetype_seq";//单位编号
	private Map<String,List> privileges = new HashMap<String,List>();
	private long lastQueryPrivilege = 0;
	@Autowired
	private RoleServiceTypeMapper dao;

	@Resource
	private XwSysSeqService xwSysSeqService;

	@Override
	public RoleServiceType selectByPrimaryKey(Long id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public void update(RoleServiceType roleServiceType) {
		dao.updateByPrimaryKey(roleServiceType);
	}

	@Override
	public Long save(RoleServiceType roleServiceType) {
		// 生成单位编号
		Long id = xwSysSeqService.getXwSequence(ROLESERVICETYPE_SEQ, 1)
				.getStartSequence();
		roleServiceType.setId(id);
		dao.insert(roleServiceType);
		return id;
	}

	@Override
	public List<String> selectServiceTypeListByRole(Integer roleId) {
		List<String> serviceTypeList =dao.selectServiceTypeListByRole(roleId);
		return serviceTypeList;
	}
	
	@Override
	public	List<String> selectServiceTypeByCache(Integer roleId)
	{
		synchronized(this)
		{
			if(System.currentTimeMillis() - this.lastQueryPrivilege < 3600)
			{
				if(privileges.containsKey(roleId.toString()))
				{
					List<String> privilege =privileges.get(roleId.toString()) ;
					return privilege;
				}
			}
		}
		List<String> serviceTypeList =dao.selectServiceTypeListByRole(roleId);
		synchronized(this)
		{
			if(serviceTypeList.size()>0)
			{
				privileges.put(roleId.toString(), serviceTypeList);
			}
		}
		return serviceTypeList;
	}
	
	@Override
	public void delete(Long id) {
		dao.deleteByPrimaryKey(id);
	}

	@Override
	public List<RoleServiceType> selectAll() {
		return dao.selectAll();
	}
	@Override
	public void reset(Object para)
	{
		lastQueryPrivilege =0;
	}
}
