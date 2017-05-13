package com.xinwei.process.service.impl;

import java.sql.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.CommitteeApprovalMapper;
import com.xinwei.process.entity.CommitteeApproval;
import com.xinwei.process.service.CommitteeApprovalService;
import com.xinwei.security.exception.ExistedException;
import com.xinwei.system.xwsequence.service.XwSysSeqService;

@Service
public class CommitteeApprovalServiceImpl implements  CommitteeApprovalService {
	
	@Autowired 
	private CommitteeApprovalMapper dao;
	
	@Resource
	private XwSysSeqService xwSysSeqService;
	
	private final String COMMITTEE_APPROVAL_SEQ = "committee_approval_seq"; //部门领导审批序号
	@Override
	public void delete(Long role_id) {
		dao.deleteByPrimaryKey(role_id);
	}

	@Override
	public void update(CommitteeApproval committeeApproval) {
		dao.updateByPrimaryKey(committeeApproval);
	}

	@Override
	public Long save(CommitteeApproval committeeApproval)
			throws ExistedException {
		// 生成部门领导审批编号
		Long recordId = xwSysSeqService.getXwSequence(
				COMMITTEE_APPROVAL_SEQ, 1).getStartSequence();
		committeeApproval.setRecordId(recordId);
		// 获取当前时间
		Date now = new Date(System.currentTimeMillis());
		// 设置审批时间
		committeeApproval.setTime(now);
		dao.insert(committeeApproval);
		return recordId;
	}

	@Override
	public List<CommitteeApproval> selectAll() {
           return dao.selectAll();
	}

	@Override
	public CommitteeApproval selectByPrimaryKey(Long role_id) {
		return 	dao.selectByPrimaryKey(role_id);
	}

	@Override
	public List<CommitteeApproval> getCommitteeApprovalByStage(Long project_id,
			String stage) {
		return 	dao.getCommitteeApprovalByStage(project_id,stage);
	}
}
