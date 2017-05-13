package com.xinwei.process.service.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xinwei.process.dao.TaskDefKeyNameMapper;
import com.xinwei.process.entity.TaskDefKeyName;
import com.xinwei.process.service.TaskDefKeyNameService;

@Service
public class TaskDefKeyNameServiceImpl implements TaskDefKeyNameService {
	@Autowired
	private TaskDefKeyNameMapper dao;
	Map<String, String> TaskDefKeyNameCaches = new ConcurrentHashMap<String, String>();
	@Override
	public List<TaskDefKeyName> selectAll() {
		return dao.selectAll();
	}

	@Override
	public TaskDefKeyName selectByPrimaryKey(String taskDefKey) {

		return dao.selectByPrimaryKey(taskDefKey);
	}

	@Override
	public String save(TaskDefKeyName taskDefKeyName) {
		// 保存
		dao.insert(taskDefKeyName);
		return taskDefKeyName.getTaskDefKey();
	}

	@Override
	public void delete(String taskDefKey) {
		dao.deleteByPrimaryKey(taskDefKey);

	}

	@Override
	public void update(TaskDefKeyName taskDefKeyName) {
		dao.updateByPrimaryKey(taskDefKeyName);
	}

	@Override
	public String selectStateNameByPrimaryKey(String taskDefKey) {
		
		if (TaskDefKeyNameCaches.containsKey(taskDefKey)) {
			String value =TaskDefKeyNameCaches.get(taskDefKey);
			return value;
		} else {
			String value = dao.selectStateNameByPrimaryKey(taskDefKey);
			if(StringUtils.isNotBlank(value))
			{
				TaskDefKeyNameCaches.put(taskDefKey, value);
			}
			else
			{
				TaskDefKeyNameCaches.put(taskDefKey, "");
				 value = "";
			}
			return value;	  
		}
	}
}
