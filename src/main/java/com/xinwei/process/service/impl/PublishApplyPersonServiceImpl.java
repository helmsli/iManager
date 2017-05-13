package com.xinwei.process.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xinwei.process.dao.PublishApplyPersonMapper;
import com.xinwei.process.entity.PublishApplyPerson;
import com.xinwei.process.service.PublishApplyPersonService;

@Service
public class PublishApplyPersonServiceImpl implements PublishApplyPersonService {

	@Resource
	private PublishApplyPersonMapper publishApplyPersonMapper;
	
	@Override
	public void save(PublishApplyPerson record) {
		publishApplyPersonMapper.insert(record);
	}

	@Override
	public List<PublishApplyPerson> getAll() {
		return publishApplyPersonMapper.selectAll();
	}

	@Override
	public void updateByPublishIDAndApplyPerson(PublishApplyPerson record) {
		publishApplyPersonMapper.updateByPublishIDAndApplyPerson(record);
	}

}
