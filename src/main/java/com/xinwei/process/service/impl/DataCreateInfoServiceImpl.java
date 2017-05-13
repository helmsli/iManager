package com.xinwei.process.service.impl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.xinwei.process.dao.DataCreateInfoMapper;
import com.xinwei.process.entity.DataCreateInfo;
import com.xinwei.process.service.DataCreateInfoService;

/**
 * 业务数据创建服务
 *
 */
@Service
public class DataCreateInfoServiceImpl implements DataCreateInfoService {

	@Resource
	private DataCreateInfoMapper dataCreateInfoMapper;
	@Override
	public int save(DataCreateInfo record) {
		
		return dataCreateInfoMapper.insert(record);
	}

	@Override
	public List<DataCreateInfo> selectAll() {
		
		return dataCreateInfoMapper.selectAll();
	}

	@Override
	public List<String> selectByConditions(Map<String, Object> queryMap) {

		return dataCreateInfoMapper.selectByConditions(queryMap);
	}

	@Override
	public Long countByConditions(Map<String, Object> queryMap) {
	
		return dataCreateInfoMapper.countByConditions(queryMap);
	}

	@Override
	public List<String> selectListByConditions(Map<String, Object> queryMap) {
		
		return dataCreateInfoMapper.selectListByConditions(queryMap);
	}

}
