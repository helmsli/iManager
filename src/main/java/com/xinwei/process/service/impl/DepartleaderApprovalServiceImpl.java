package com.xinwei.process.service.impl;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.DepartleaderApprovalMapper;
import com.xinwei.process.entity.DepartleaderApproval;
import com.xinwei.process.service.DepartleaderApprovalService;
import com.xinwei.security.exception.ExistedException;
import com.xinwei.system.xwsequence.service.XwSysSeqService;

@Service
public class DepartleaderApprovalServiceImpl implements DepartleaderApprovalService {
	
	@Autowired
	private DepartleaderApprovalMapper dao;
	
	@Resource
	private XwSysSeqService xwSysSeqService;
	
	private final String DEPARTLEADER_APPROVAL_SEQ = "departleader_approval_seq"; //部门领导审批序号
	@Override
	public void delete(Long record_id) {
		dao.deleteByPrimaryKey(record_id);
	}

	@Override
	public void update(DepartleaderApproval departleaderApproval) {
		dao.updateByPrimaryKey(departleaderApproval);
	}

	@Override
	public Long save(DepartleaderApproval departleaderApproval) throws ExistedException {
		// 生成部门领导审批编号
		Long recordId = xwSysSeqService.getXwSequence(DEPARTLEADER_APPROVAL_SEQ, 1)
				.getStartSequence();
		departleaderApproval.setRecordId(recordId);
		// 获取当前时间
		Date now = new Date(System.currentTimeMillis());
		// 设置审批时间
		departleaderApproval.setTime(now);
		//保存到数据库
		dao.insert(departleaderApproval);
		return recordId;
	}

	@Override
	public List<DepartleaderApproval> selectAll() {
           return dao.selectAll();
	}

	@Override
	public DepartleaderApproval selectByPrimaryKey(Long record_id) {
		return 	dao.selectByPrimaryKey(record_id);
	}

	@Override
	public  List<DepartleaderApproval> getDepartleaderApprovalByStage(Long project_id,
			String stage) {
		return 	dao.getDepartleaderApprovalByStage(project_id,stage);
	}
}
