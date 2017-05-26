package com.xinwei.process.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinwei.process.dao.ExpertReviewMapper;
import com.xinwei.process.entity.ExpertReview;
import com.xinwei.process.entity.ProjectAnnex;
import com.xinwei.process.service.ExpertReviewService;
import com.xinwei.process.service.ProjectAnnexService;
import com.xinwei.security.exception.ExistedException;
import com.xinwei.system.xwsequence.service.XwSysSeqService;

@Service
public class ExpertReviewServiceImpl implements ExpertReviewService {
	
	@Autowired
	private ExpertReviewMapper dao;
	
	@Resource
	private XwSysSeqService xwSysSeqService;
	
	private final String EXPERT_REVIEW_SEQ = "expert_review_seq"; //专家评审序号
	@Resource
	private ProjectAnnexService projectAnnexServiceImpl;//项目附件服务
	
	@Override
	public void delete(Long record_id) {
		dao.deleteByPrimaryKey(record_id);
	}

	@Override
	public void update(ExpertReview expertReview) {
		dao.updateByPrimaryKey(expertReview);
	}

	@Override
	public Long save(ExpertReview expertReview) throws ExistedException {
		// 生成专家评审编号
		Long recordId = xwSysSeqService.getXwSequence(EXPERT_REVIEW_SEQ, 1)
				.getStartSequence();
		// 获取当前时间
		Date now = new Date(System.currentTimeMillis());
		expertReview.setTime(now);
		expertReview.setRecordId(recordId);
		dao.insert(expertReview);
		return recordId;
	}

	@Override
	public List<ExpertReview> selectAll() {
           return dao.selectAll();
	}

	@Override
	public ExpertReview selectByPrimaryKey(Long record_id) {
		return 	dao.selectByPrimaryKey(record_id);
	}
	
	@Override
	public List<ExpertReview> getExpertReviewByProjectId(Long projectId) {
		List<ExpertReview> expertReviews = dao.getExpertReviewByProjectId(projectId);
		return expertReviews;
	}
	
	/**
	 * 获取阶段性项目评审详情
	 */
	@Override
	public List<ExpertReview>  getExpertReviewByStage(Long project_id, String stage) {
		List<ExpertReview> expertReviews =	dao.getExpertReviewByStage(project_id,stage);
		//评审材料
		List<ProjectAnnex> viewMaterials= new ArrayList<ProjectAnnex>();
		//评审报告
		List<ProjectAnnex> viewReports= new ArrayList<ProjectAnnex>();
		if(stage.equals("begin")){
			for(ExpertReview expertReview : expertReviews){
				//初期评审材料
				viewMaterials = projectAnnexServiceImpl.selectByProjectIdAndTypeId(project_id, 1L);
				expertReview.setViewMaterials(viewMaterials);
			}
		}
	   if(stage.equals("middle")){
			for(ExpertReview expertReview : expertReviews){
				//中期评审材料
				viewMaterials = projectAnnexServiceImpl.selectByProjectIdAndTypeId(project_id, 2L);
				expertReview.setViewMaterials(viewMaterials);
				//中期评审报告
				viewReports=  projectAnnexServiceImpl.selectByProjectIdAndTypeId(project_id, 3L);
				expertReview.setViewReports(viewReports);
			}
		}
		else if(stage.equals("last")){
			for(ExpertReview expertReview : expertReviews){
				//中期评审材料
				viewMaterials = projectAnnexServiceImpl.selectByProjectIdAndTypeId(project_id, 4L);
				expertReview.setViewMaterials(viewMaterials);
				//中期评审报告
				viewReports=  projectAnnexServiceImpl.selectByProjectIdAndTypeId(project_id, 5L);
				expertReview.setViewReports(viewReports);
			}
		}
		return expertReviews; 
	}

}
