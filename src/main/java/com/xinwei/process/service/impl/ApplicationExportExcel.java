package com.xinwei.process.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.xinwei.process.constant.ApprovedConstants;
import com.xinwei.process.controller.CommonBizController;
import com.xinwei.process.entity.Application;
import com.xinwei.process.entity.CommonBiz;
import com.xinwei.process.entity.ProjectAnnex;
import com.xinwei.util.ExceptionApp;
import com.xinwei.util.JsonUtil;
import com.xinwei.util.date.DateUtil;

public class ApplicationExportExcel {
	
	/**
	 * 存放原始的申报书上传的目录
	 */
	private String uploadDir="";
	private Logger logger = LoggerFactory.getLogger(ApplicationExportExcel.class);
	private static ApplicationExportExcel applicationExportExcel= new ApplicationExportExcel();
	public static ApplicationExportExcel getInstance()
	{
		return applicationExportExcel;
	}
    protected String applicationExportPath="files";	
    
    protected String applicationTempPath="tempAppExport";	
    
    
    
    
    
    public String getApplicationTempPath() {
		return applicationTempPath;
	}


	public void setApplicationTempPath(String applicationTempPath) {
		this.applicationTempPath = applicationTempPath;
	}


	public String getUploadDir() {
		return uploadDir;
	}


	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}
	protected String defaultAppTemplateFile="excel-template.xlsx";	
	private Gson gson = new GsonBuilder()
			.serializeNulls()//序列化null
			.setDateFormat("yyyy-MM-dd HH:mm:ss")// 设置日期时间格式
			.create();
	private int  fileIndex=0;
	
	
	
	
	public String getDefaultAppTemplateFile() {
		return defaultAppTemplateFile;
	}


	public void setDefaultAppTemplateFile(String defaultAppTemplateFile) {
		this.defaultAppTemplateFile = defaultAppTemplateFile;
	}


	public   String getTempRelativeFilePath()
	{
		int localFileIndex = 0;
		//构造一个临时文件索引
		synchronized(this)
		{
			localFileIndex=fileIndex++;
			if(localFileIndex>0xFFFFFF)
			{
				fileIndex=0;
			}
		}
	    return "total-"+ DateUtil.DateToString(new Date(),"YYYY-MM-dd-") + String.valueOf(localFileIndex);
		
	}
	
	
	protected boolean insertCommonBizToExcel(CommonBiz commonBiz,Workbook workbook,Row row,String path)
	{
		
		Application application=null;
		try {
			application = gson.fromJson(commonBiz.getData5(), Application.class);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		CreationHelper createHelper = workbook.getCreationHelper();
		Cell cell;
		
		int columnIndex=0;
		CellStyle cellStyle=workbook.createCellStyle();    
		cellStyle.setWrapText(true); 
		
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		
		String keshi="无";
		String district=commonBiz.getData1();
		 
		 
		//项目类型
		 if(StringUtils.equalsIgnoreCase(commonBiz.getTaskId(), ApprovedConstants.Application_type.APP_DING))
		 {
			 cell.setCellValue("定向项目");
			 keshi=commonBiz.getData1();
			 district=application.getAddress();
			 if(StringUtils.isEmpty(district)||district.compareTo("科室")==0)
			 {
				 district="无";
		     }
		 }
		 else if(StringUtils.equalsIgnoreCase(commonBiz.getTaskId(), ApprovedConstants.Application_type.APP_WEI))
		 {
			 cell.setCellValue("微创投项目");
		 }
		 else
		 {
			 cell.setCellValue("申请项目");
		 }
		
		//项目名称
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(commonBiz.getProjectName());
		//申报单位
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(commonBiz.getData3());
		
		//科室
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(keshi);
		
		//地区
				cell=row.createCell((short) columnIndex++);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(district);
				
		
		//金额
		cell=row.createCell((short) columnIndex++);
		cell.setCellValue(commonBiz.getData2());
		//process_instance_id预计服务人数
		cell=row.createCell((short) columnIndex++);
		cell.setCellValue(commonBiz.getProcessInstanceId());
		
		//申报人名字
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(commonBiz.getCreatePersonName());
		//申报人电话
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(commonBiz.getCreatePerson());
		//申报人邮箱
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
	
		
		try {
			//logger.debug(application.toString());
			cell.setCellValue(application.getEmail());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		//申报人时间
		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		cell.setCellValue(DateUtil.DateToString(commonBiz.getCreateTime(),"YYYY-MM-dd hh:mm:ss"));
		

		cell=row.createCell((short) columnIndex++);
		cell.setCellStyle(cellStyle);
		if(StringUtils.equalsIgnoreCase(commonBiz.getStatus(), String.valueOf(ApprovedConstants.ApplicationResult.application)))
		 {
			cell.setCellValue("等待审批");
 
		 }
		 else if(StringUtils.equalsIgnoreCase(commonBiz.getStatus(), String.valueOf(ApprovedConstants.ApplicationResult.needChange)))
		 {
			
			 cell.setCellValue("需要修改");
		 }
		 else if(StringUtils.equalsIgnoreCase(commonBiz.getStatus(), String.valueOf(ApprovedConstants.ApplicationResult.reject)))
		 {
			 
			 cell.setCellValue("审批不通过");
		 }
		 else if(StringUtils.equalsIgnoreCase(commonBiz.getStatus(), String.valueOf(ApprovedConstants.ApplicationResult.agree)))
		 {
			 cell.setCellValue("审批通过");
		 }
		 else
		 {			
			 cell.setCellValue("等待审批");
		 }
		 try {
			//申报书
			//ProjectAnnex[] projectAnnex=gson.fromJson(commonBiz.getData6(), ProjectAnnex.class);
			 cell=row.createCell((short) columnIndex++);
				            			 
			 JsonParser parser = new JsonParser();
			 JsonElement el = parser.parse(commonBiz.getData6());
			 JsonObject jsonObj = null;
			 if(el.isJsonObject()){
			 jsonObj = el.getAsJsonObject();  
			 }
			//把JsonElement对象转换成JsonArray
			 JsonArray jsonArray = null;
			 if(el.isJsonArray()){
			 jsonArray = el.getAsJsonArray();
			 }
			 ProjectAnnex projectAnnex = null;
			 Iterator it = jsonArray.iterator();
			 while(it.hasNext()){
			 JsonElement e = (JsonElement)it.next();
			 //JsonElement转换为JavaBean对象
			
			 projectAnnex = gson.fromJson(e, ProjectAnnex.class);
			 
			 }
			//附件
			 logger.debug("copyfile:" +projectAnnex.toString()+uploadDir +File.separator+ projectAnnex.getAnnexName() );
			 
			 //构造申报书的文件名称
			 String lastPrefx = getFilelastPrefx(projectAnnex.getOriginalFilename());
			 String destFile =  commonBiz.getData3()+"_"+(row.getRowNum()+1) + "_0." + lastPrefx;
			 copyFile(uploadDir + File.separator+projectAnnex.getAnnexName(),path+File.separator +applicationExportPath+ File.separator +destFile);
			Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
			link.setAddress( applicationExportPath+ "/" +destFile);
			cell.setCellValue("申报书");
			cell.setHyperlink(link);
			
			CellStyle hlink_style = workbook.createCellStyle();
			Font hlink_font = workbook.createFont();
			hlink_font.setUnderline(Font.U_SINGLE);
			hlink_font.setColor(IndexedColors.BLUE.getIndex());
			hlink_style.setFont(hlink_font);
			cell.setCellStyle(hlink_style);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //资质附件
		 try {
				//
				//ProjectAnnex[] projectAnnex=gson.fromJson(commonBiz.getData6(), ProjectAnnex.class);
			    cell=row.createCell((short) columnIndex++);
				 
				 JsonParser parser = new JsonParser();
				 JsonElement el = parser.parse(commonBiz.getData7());
				 JsonObject jsonObj = null;
				 if(el.isJsonObject()){
				 jsonObj = el.getAsJsonObject();  
				 }
				//把JsonElement对象转换成JsonArray
				 JsonArray jsonArray = null;
				 if(el.isJsonArray()){
				 jsonArray = el.getAsJsonArray();
				 }
				 ProjectAnnex projectAnnex = null;
				 Iterator it = jsonArray.iterator();
				 while(it.hasNext()){
				 JsonElement e = (JsonElement)it.next();
				 //JsonElement转换为JavaBean对象
				 projectAnnex = gson.fromJson(e, ProjectAnnex.class);
				 
				 }
				//附件
				 logger.debug(projectAnnex.toString());
				
				Hyperlink link = createHelper.createHyperlink(Hyperlink.LINK_FILE);
				 String lastPrefx = getFilelastPrefx(projectAnnex.getOriginalFilename());
				 String destFile =  commonBiz.getData3()+"_"+(row.getRowNum()+1) + "_1." + lastPrefx;
				copyFile(uploadDir + File.separator+projectAnnex.getAnnexName(),path+File.separator +applicationExportPath+ File.separator +destFile);
					

				
				link.setAddress( applicationExportPath+ "/" +destFile);
				cell.setCellValue("资质");
				
				cell.setHyperlink(link);
				CellStyle hlink_style = workbook.createCellStyle();
				Font hlink_font = workbook.createFont();
				hlink_font.setUnderline(Font.U_SINGLE);
				hlink_font.setColor(IndexedColors.BLUE.getIndex());
				hlink_style.setFont(hlink_font);
				cell.setCellStyle(hlink_style);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

								
		
		return true;
	}
	
	/**
	 * 返回文件名称
	 * @param lists
	 * @return
	 */
	public String createApplicationReport(List<CommonBiz> lists,String destPath,String templatePath) throws Exception
	{
		String templateFilePath = templatePath;
		String srcFilePath = destPath;		
		String indexFileName = File.separator+"申报项目列表.xlsx";
		//创建临时目录
		logger.debug(destPath + ":(((((((((:" + templatePath);
		 try {
			
			 createDir(srcFilePath);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 //创建存放申报书的临时目录
		 try {
				
				 
				 createDir(srcFilePath+File.separator+applicationExportPath);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			 
		 
		 
		//String destFilePath = getDestFilePath();
		FileInputStream fileInputStream=null;
		Workbook workbook=null;
		try {
			fileInputStream = new FileInputStream(templateFilePath);
			workbook = new XSSFWorkbook(fileInputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			throw new ExceptionApp(ExceptionApp.EXCEPTION_SrcFileNoExist);
		}
		finally{
			if(fileInputStream!=null)
			{
				fileInputStream.close();	
			}
		}
		
		int sheetNumber = workbook.getNumCellStyles();
		if(sheetNumber<=0)
		{
			System.out.print("aaaaaaaaaaaaaaaaaaaaaaa");
			return "";
		}
		logger.debug("((((((((((((((((((((((((((((((");
		Sheet sheet = workbook.getSheetAt(0);
		
		
		int rowIndex=1;
		for(CommonBiz commonBiz:lists)
		{
			logger.debug("******************+"+ commonBiz);
			Row row = sheet.createRow(rowIndex++);
			insertCommonBizToExcel(commonBiz,workbook,row,srcFilePath);
		}
		
		
		
		FileOutputStream out = new FileOutputStream(srcFilePath+indexFileName);
		try {
			workbook.write(out);
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		finally
		{
			
			try {
				out.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		File file=ZipUtils.zip(srcFilePath,null);
		logger.debug(file.getAbsolutePath() + ":" + file.getName());
		try {
			deleteDirectory(srcFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file.getName();
		
	}
	
	public void copyFile(String oldPath, String newPath) { 
		logger.debug(oldPath+":" + newPath);
		InputStream inStream=null;
		FileOutputStream fs=null;
		try { 
			int bytesum = 0; 
			int byteread = 0; 
			File oldfile = new File(oldPath); 
			if (oldfile.exists()) { //文件存在时 
				 inStream = new FileInputStream(oldPath); //读入原文件 
				 fs = new FileOutputStream(newPath); 
				byte[] buffer = new byte[1444]; 
				int length; 
				while ( (byteread = inStream.read(buffer)) != -1) { 
					bytesum += byteread; //字节数 文件大小 
					System.out.println(bytesum); 
					fs.write(buffer, 0, byteread); 
				} 
				
			} 
		} 
		catch (Exception e) { 
			System.out.println("复制单个文件操作出错"); 
			e.printStackTrace(); 
	
		}
		finally
		{
			try {
				if(inStream!=null)
				{
					inStream.close();
				}
				if(fs!=null)
				{
					fs.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	public static boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {// 判断目录是否存在
			System.out.println("创建目录失败，目标目录已存在！");
			return false;
		}
		if (!destDirName.endsWith(File.separator)) {// 结尾是否以"/"结束
			destDirName = destDirName + File.separator;
		}
		if (dir.mkdirs()) {// 创建目标目录
			System.out.println("创建目录成功！" + destDirName);
			return true;
			
		} else {
			System.out.println("创建目录失败！");
			return false;
		}
	}

	
	public boolean deleteFile(String filePath) {// 删除单个文件
		boolean flag = false;
		File file = new File(filePath);
		if (file.isFile() && file.exists()) {// 路径为文件且不为空则进行删除
			file.delete();// 文件删除
			flag = true;
		}
		
		return flag;
	}
	
	public boolean deleteDirectory(String dirPath) {// 删除目录（文件夹）以及目录下的文件
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!dirPath.endsWith(File.separator)) {
			dirPath = dirPath + File.separator;
		}
		File dirFile = new File(dirPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles();// 获得传入路径下的所有文件
		for (int i = 0; i < files.length; i++) {// 循环遍历删除文件夹下的所有文件(包括子目录)
			if (files[i].isFile()) {// 删除子文件
				flag = deleteFile(files[i].getAbsolutePath());
				System.out.println(files[i].getAbsolutePath() + " 删除成功");
				if (!flag)
					break;// 如果删除失败，则跳出
			} else {// 运用递归，删除子目录
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;// 如果删除失败，则跳出
			}
		}
		if (!flag)
			return false;
		if (dirFile.delete()) {// 删除当前目录
			return true;
		} else {
			return false;
		}
	}
	
	public String getFilelastPrefx(String srcFileName)
	{
		String prefix = srcFileName.substring(srcFileName.lastIndexOf(".")+1);
		return prefix;	
	}
}
