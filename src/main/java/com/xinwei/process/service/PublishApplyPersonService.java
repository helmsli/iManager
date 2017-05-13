package com.xinwei.process.service;

import java.util.List;

import com.xinwei.process.entity.PublishApplyPerson;

public interface PublishApplyPersonService {

	void save(PublishApplyPerson record);
	
	List<PublishApplyPerson> getAll();
	
	/**
	 * 根据发布ID、申报人更新申报项目Id和申报项目状态
	 * @param record
	 */
	void updateByPublishIDAndApplyPerson(PublishApplyPerson record);
}
