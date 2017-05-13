package com.xinwei.process.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import tk.mybatis.mapper.util.StringUtil;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.xinwei.process.constant.ProjectConstants;
import com.xinwei.process.entity.ProjectAnnex;
import com.xinwei.process.service.ProcessService;
import com.xinwei.process.service.ProjectAnnexService;
import com.xinwei.process.service.UserTaskService;
import com.xinwei.security.entity.User;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.system.xwsequence.service.XwSysSeqService;
import com.xinwei.util.ChineseToSpellUtil;
import com.xinwei.util.date.DateUtil;

@Controller
@RequestMapping("/projectAnnex")
public class ProjectAnnexController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(ProjectAnnexController.class);
	@Resource
	private ProjectAnnexService projectAnnexServiceImpl;
	@Resource
	private XwSysSeqService xwSysSeqService;
	@Resource
	private UserTaskService userTaskServiceImpl;
	@Resource
	private ProcessService processServiceImpl;
	@Resource
	private RuntimeService runtimeService;// 流程运行时相关服务
	private final String ANNEX_SEQ = "annex_seq";// 项目编号

	@Value("${uploadPath}")
	private String uploadPath;  
	
	@ModelAttribute("preloadProject")
	public ProjectAnnex getOne(@RequestParam(value = "annexId", required = false) Long annexId) {
		if (annexId != null) {
			ProjectAnnex projectAnnex = projectAnnexServiceImpl.selectByPrimaryKey(annexId);
			return projectAnnex;
		}
		return null;
	}
	
	
	/**
	 * save
	 * @param project
	 * @return
	 */
	@RequestMapping(value="/create", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String create(ProjectAnnex projectAnnex) {	
		projectAnnexServiceImpl.save(projectAnnex);
		return new ResultVO<>().toString();
	}
	
	
	/**
	 * update
	 * @param department
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	@RequestMapping(value="/update", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String update(@ModelAttribute("preloadProjectAnnex")ProjectAnnex projectAnnex) 
	{
		projectAnnexServiceImpl.update(projectAnnex);
		return new ResultVO<>().toString();
	}
	
	/**
	 * delete
	 * @param annexId
	 * @return
	 */
	@RequestMapping(value="/delete", method={RequestMethod.GET, RequestMethod.POST})
	public @ResponseBody String delete(HttpServletRequest request,Long annexId) {
		ResultVO<Object> result = new ResultVO<>();
		// 删除服务器存储的文件
		ProjectAnnex projectAnnex = projectAnnexServiceImpl
				.selectByPrimaryKey(annexId);
		if (null != projectAnnex) {
			String annexName = projectAnnex.getAnnexName();
			//文件上传地址
			String path = request.getSession().getServletContext().getRealPath(uploadPath);
			//待删除文件
			File file = new File(path, annexName);
			if (file.delete()) {
				//如果文件删除成功删除附件对象
				projectAnnexServiceImpl.delete(annexId);
			} else {
				result.setResult(ResultVO.FAILURE);
			}
		} else {
			result.setResult(ResultVO.FAILURE);
		}
		return result.toString();
	}
	
	/**
	 * 获取所有
	 * @return
	 */
	@RequestMapping(value="/getAllProjectAnnexs", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String getAllProjectAnnexs() {
		List<ProjectAnnex> projectAnnexs = projectAnnexServiceImpl.selectAll();
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("projectAnnexs", projectAnnexs);
		return result.toString();
	}
	
	/**
	 * 根据项目ID获取月度报告
	 * @return
	 */
	@RequestMapping(value="/selectMonthlyReportByProjectId", method={RequestMethod.GET,RequestMethod.POST})
	public @ResponseBody String selectMonthlyReportByProjectId(Long projectId) {
		List<ProjectAnnex> projectAnnexs = projectAnnexServiceImpl.selectMonthlyReportByProjectId(projectId);
		ResultVO<Object> result = new  ResultVO<>();
		result.setOthers("projectAnnexs", projectAnnexs);
		return result.toString();
	}
	
	/**
	 * 项目附件上传（支持多文件上传）
	 */
	@RequestMapping(value="/upload",produces = "text/html;charset=UTF-8")
	public @ResponseBody String annexUpload(HttpServletRequest request){
		logger.debug("upload file start...");
		//项目附件列表
		List<ProjectAnnex> projectAnnexs = new ArrayList<ProjectAnnex>();
		//给客户端响应信息
		ResultVO<Object> result = new  ResultVO<>();
        try {
        	//将当前上下文初始化给  CommonsMutipartResolver （多部分解析器）
        	CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
        			request.getSession().getServletContext());
        	//文件上传地址
        	String path = request.getSession().getServletContext().getRealPath(uploadPath);
        	
        	//检查form中是否有enctype="multipart/form-data"
			if(multipartResolver.isMultipart(request))
			{
			    //将request变成多部分request
			    MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
			   //获取multiRequest 中所有的文件名
			    Iterator<String> iter=multiRequest.getFileNames();
			    while(iter.hasNext())
			    {
			        //一次遍历所有文件
			        MultipartFile file = multiRequest.getFile(iter.next());
			        if(file!=null)
			        {
			        	String originalFilename = file.getOriginalFilename();
			        	String postfix = "";
			    		String fileNamePrefix = originalFilename;
			    		if (originalFilename.contains("."))// 是否带后缀，如带后缀则进行分割
			    		{
			    			int splitPosition = originalFilename.lastIndexOf(".");
			    			postfix = originalFilename.substring(originalFilename.lastIndexOf("."));
			    			fileNamePrefix = originalFilename.substring(0,splitPosition);
			    		}
			        	Date uploadDate = new Date();
			        	// 保存文件时的文件名
			            String annexName = DateUtil.DateToString(uploadDate, "yyyy-MM-dd-HH-mm-ss") + "_" + ChineseToSpellUtil.cn2FirstSpell(fileNamePrefix) + postfix;
			            File targetFile = new File(path, annexName);
						if(!targetFile.exists()){  
				            targetFile.mkdirs();  
				        }
			            //上传
			            file.transferTo(targetFile);
			            //当前用户
			            User user = getCurrentUser();
			            //构建项目附件对象
			            ProjectAnnex projectAnnex = buildProjectAnnex(annexName,file.getOriginalFilename(),
			            		ProjectConstants.ANNEX_TYPE.ANNEX,user.getId().toString(),uploadDate);
						// 保存文件信息到数据库
						logger.info("annexUpload --> projectAnnex:  " + projectAnnex.toString());
						projectAnnexServiceImpl.save(projectAnnex);
						projectAnnexs.add(projectAnnex);
						logger.info("return --> projectAnnex:  " + projectAnnexs.toString());
			        }  
			    }   
			}else{
				logger.debug("filelist is null! ");
			}
			result.setOthers("projectAnnexs", projectAnnexs);
		} catch (Exception e) {
			logger.error("annexUpload erro :" + e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.FAILURE);
		} 
		return result.toString(); 
	}


	//提交项目相关材料
	@RequestMapping("/submitMaterial")
	public @ResponseBody String submitMaterial(String projectAnnexs,String taskId)
			throws IOException {
		//给客户端响应信息
		logger.debug(projectAnnexs + " taskID:" + taskId);
		ResultVO<Object> result = new  ResultVO<>();
		try {
			// 判断用户任务ID是否为空
			if (null != taskId && !"".equals(taskId)) {
				// 根据taskId获取任务对象
				Task task = userTaskServiceImpl.getTask(taskId);
				if (null != task) {
					// 获取项目ID
					String businessKey = processServiceImpl.getProcessInstance(
							task.getProcessInstanceId()).getBusinessKey();
					String projectId =processServiceImpl.getDataIdByBusinessKey(businessKey).toString();
					// 获取附件类型
					Long typeId = getAnnexTypeId(task.getTaskDefinitionKey());

					// 获取附件列表
					Gson gson = new GsonBuilder()
						.serializeNulls()//序列化null
						.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
						.create();
					List<ProjectAnnex> projectAnnexList = gson.fromJson(projectAnnexs, new TypeToken<List<ProjectAnnex>>() {}.getType());
					// 完善附件信息
					for(ProjectAnnex projectAnnex:projectAnnexList){
						projectAnnexServiceImpl.updateProjectIdAndTypeId(Long.parseLong(projectId), typeId, projectAnnex.getAnnexId());
					}
					// 用户完成任务
					Map<String, Object> variables = new HashMap<String, Object>();
					//如果为提交周期性报告，需根据终期部门经理审批意见设置是否需要继续提交
					if(ProjectConstants.State.SUBMITMONTHLYREPORT.contains(task.getName())){
						//获取流程中的变量--终期部门经理审批意见 
						String resultOfApprove = (String)runtimeService
								.getVariable(task.getProcessInstanceId(), "resultOfEndChecked");  	
						if("pass".equals(resultOfApprove)){
							variables.put("toSubmit","notNeed");
						}else{
							variables.put("toSubmit","need");
						}
					}
					userTaskServiceImpl.completeTask(taskId, variables);
					//返回成功信息
					result.setResult(ResultVO.SUCCESS);
				}else{
					logger.debug("task is null");
					result.setResult(ResultVO.FAILURE);
				}
			} else {
				logger.debug("taskId is null");
				result.setResult(ResultVO.FAILURE);
			}
		} catch (Exception e) {
			logger.error("submitMaterial erro :" + e.getMessage());
			e.printStackTrace();
			result.setResult(ResultVO.FAILURE);
		}
		return result.toString(); 
	}
	
    /**
    * 根据附件名称进行下载
    * @param request
    * @param response
    * @param annexName
    * @param annexId
    */
	@RequestMapping(value="/fileDownLoad",produces = "text/html;charset=UTF-8")   
	public void fileDownLoad(HttpServletRequest request, HttpServletResponse response,String annexName) {  
		logger.debug("fileDownLoad --> annexName: " + annexName);
		FileInputStream fis = null;
	    BufferedInputStream bis = null;
	    OutputStream os = null;
	    try{
	    	if(StringUtil.isNotEmpty(annexName)){
	    		//文件原始名字
	    		String originalFilename  = annexName;
		    	//根据附件名称获取附件对象
		    	ProjectAnnex annex = projectAnnexServiceImpl.selectByAnnexName(annexName);
		    	//如果附件对象不为空
		    	if(null != annex){
		    		// 获取文件原始名字
		    		originalFilename  = annex.getOriginalFilename();
		    	}
	    		response.setHeader("Content-disposition", "attachment; filename="  
	    				+new String(originalFilename.getBytes(Charset.forName("UTF-8")),"ISO8859-1"));
	    		response.setContentType("application/force-download");// 设置强制下载不打开
	    		//文件上传地址
	    		String path = request.getSession().getServletContext().getRealPath(uploadPath);   
	    		byte buffer [] = new byte[1024*1024*1];//1M    
	    		fis = new FileInputStream(new File(path,annexName));  
	    		bis = new BufferedInputStream(fis);
	    		os = response.getOutputStream();
	    		int length = 0;
	    		while(-1!=(length=bis.read(buffer))){  
	    			os.write(buffer,0,length);//每次写1M  
	    		}  
	    		os.flush();
	    	}
	    }catch (Exception e) {
	    	logger.error("fileDownLoad erro : "+ e.getMessage());
	        e.printStackTrace();
	    }finally{  
	        try {  
	            if(os!=null){  
	                os.close();  
	            }
	            if(bis!=null){  
	                bis.close();  
	            }
	            if(fis!=null){
	            	fis.close();
	            }
	        } catch (IOException e) {  
	        	 e.printStackTrace();
	        }  
	    }
	}
	
	/*
	 * 构建项目附件对象
	 */
	private ProjectAnnex buildProjectAnnex(String annexName,
			String originalFilename,Long typeId,String userId,
			Date uploadDate) throws UnsupportedEncodingException {
		// 生成附件编号
		Long annexId = xwSysSeqService.getXwSequence(ANNEX_SEQ, 1)
						.getStartSequence();
		ProjectAnnex projectAnnex = new ProjectAnnex();
		//设置ID
		projectAnnex.setAnnexId(annexId);
		//设置附件名称
		projectAnnex.setAnnexName(annexName);
		//设置附件原始名称
		projectAnnex.setOriginalFilename(originalFilename);
		//设置文件下载路径
		projectAnnex.setPath(annexName);
		//设置附件类型 (一般附件)
		projectAnnex.setTypeId(typeId);
		//设置上传者
		projectAnnex.setUserId(userId);
		//设置上传时间
		projectAnnex.setUploadTime(uploadDate);
		return projectAnnex;
	}
	
	/*
	 * 根据任务定义key得到相应的附件类型
	 */
	private Long getAnnexTypeId(String taskDefinitionKey){
		Long typeId = 1L;
		//设置附件类型
		if (ProjectConstants.State.PMSUBMITMIDMATERIAL.contains(taskDefinitionKey)) {
			//中期评审材料
			typeId=ProjectConstants.ANNEX_TYPE.MIDMATERIAL;
		}else if(ProjectConstants.State.MIDAPPROVALREPORT.contains(taskDefinitionKey)){
			//中期评审报告
			typeId=ProjectConstants.ANNEX_TYPE.MIDREPORT;
		}else if(ProjectConstants.State.PMSUBMITENDMATERIAL.contains(taskDefinitionKey)){
			//终期评审材料
			typeId=ProjectConstants.ANNEX_TYPE.TERMIANLMATERIAL;
		}else if(ProjectConstants.State.ENDAPPROVALREPORT.contains(taskDefinitionKey)){
			//终期评审报告
			typeId=ProjectConstants.ANNEX_TYPE.TERMIANLREPORT;
		}else if(ProjectConstants.State.SUBMITMONTHLYREPORT.contains(taskDefinitionKey)){
			//月度报告
			typeId=ProjectConstants.ANNEX_TYPE.MONTHLYREPORT;
		}
		return typeId;
	}
	
}
