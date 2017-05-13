package com.xinwei.process.dao;

import com.xinwei.process.entity.PublishApplyPerson;
import java.util.List;

public interface PublishApplyPersonMapper {
    
	int insert(PublishApplyPerson record);

    List<PublishApplyPerson> selectAll();

	void updateByPublishIDAndApplyPerson(PublishApplyPerson record);
}