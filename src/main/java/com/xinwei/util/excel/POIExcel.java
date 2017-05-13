package com.xinwei.util.excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel工具类
 * 基于Apache的POI类库
 */
public class POIExcel {
	private static Log log = LogFactory.getLog(POIExcel.class);  

	private InputStream inputStream;

	private OutputStream outStream;
	
	private Workbook workBook ;
	
	//excel中 导出的所有列项
	private List<String> columnNames = new ArrayList<String>();
	
	//excel中 导出的所有列项 国际化
	private List<String> columnNamesText = new ArrayList<String>();

	public Workbook getWorkBook() {
		return workBook;
	}
	
	/**
	 * 读取excel文件
	 * @param file
	 * @throws Exception
	 */
	public POIExcel(String pathName) throws Exception {
		
		this.inputStream = new FileInputStream(pathName);
		this.workBook = WorkbookFactory.create(this.inputStream);	
		if (this.inputStream != null) {
			try {
				this.inputStream.close();
			} catch (Exception e) {
			}
		} 
	}
	
	/**
	 * 获取sheet
	 * @param sheetName
	 * @return
	 */
	public Sheet getSheet(String sheetName){
		return this.workBook.getSheet(sheetName);
	}
	
	/**
	 * 获取sheet
	 * @param index
	 * @return
	 */
	public Sheet getSheet(int index){
		return this.workBook.getSheetAt(index);
	}
	
	
	/**
	 * 获取sheet中最后一行序号
	 * @param sheetName
	 * @return
	 */
	public int getLastRowNum(String sheetName){
		Sheet sheet = this.workBook.getSheet(sheetName);
		return sheet.getLastRowNum();
	}
	
	/**
	 * 获取sheet中最后一行序号
	 * @param sheetNum
	 * @return
	 */
	public int getLastRowNum(int sheetNum){
		Sheet sheet = this.workBook.getSheetAt(sheetNum);
		return sheet.getLastRowNum();
	}
	
	/**
	 * 创建一个sheet
	 * @param sheetName
	 */
	public Sheet createSheet(String sheetName){
		Sheet sheet = this.workBook.getSheet(sheetName);
		if(null == sheet){
			sheet = this.workBook.createSheet(sheetName);
		}
		return sheet;
	}
	
	
    /** 
     * 创建WorkBook对象 
     * @param flag   true:xlsx(2007以上版本) false:xls(2007以下) 
     * @return 
     */  
    public Workbook createWorkbook(boolean flag) {  
        return flag ? new XSSFWorkbook() : new HSSFWorkbook();  
    }  
  
    
	/**
	 * 写入一组值
	 * 
	 * @param sheetNum 			写入的sheet的编号
	 * @param fillRow 			是写入行还是写入列
	 * @param startRowNum 		开始行号
	 * @param startColumnNum 	开始列号
	 * @param contents 			写入的内容数组
	 * @throws Exception
	 */
	public void writeArrayToExcel(int sheetNum, boolean fillRow, int startRowNum, int startColumnNum, List<Object> contents)
			throws Exception {
		Sheet sheet = this.workBook.getSheetAt(sheetNum);
		writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
	}

	/**
	 * 写入一组值
	 * 
	 * @param sheetNum 			写入的sheet的名称
	 * @param fillRow 			是写入行还是写入列
	 * @param startRowNum 		开始行号
	 * @param startColumnNum 	开始列号
	 * @param contents 			写入的内容数组
	 * @throws Exception
	 */
	public void writeArrayToExcel(String sheetName, boolean fillRow, int startRowNum, int startColumnNum,
			List<Object> contents) throws Exception {
		Sheet sheet = this.workBook.getSheet(sheetName);
		writeArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
	}

	private void writeArrayToExcel(Sheet sheet, boolean fillRow, int startRowNum, int startColumnNum, List<Object> contents)
			throws Exception {
		for (int i = 0, length = contents.size(); i < length; i++) {
			int rowNum;
			int columnNum;
			// 以行为单位写入，行序列不变，列序列递增
			if (fillRow) {
				rowNum = startRowNum;
				columnNum = startColumnNum + i;
			}
			// 以列为单位写入,列序列不变，行序列递增
			else {
				rowNum = startRowNum + i;
				columnNum = startColumnNum;
			}
			this.writeToCell(sheet, rowNum, columnNum, convertString(contents.get(i)));
		}
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetNum 		sheet的编号
	 * @param rowNum 		行号
	 * @param columnNum 	列号
	 * @param value 		写入的值
	 * @throws Exception
	 */
	public void writeToExcel(int sheetNum, int rowNum, int columnNum, Object value) throws Exception {
		Sheet sheet = this.workBook.getSheetAt(sheetNum);
		this.writeToCell(sheet, rowNum, columnNum, value);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetName 	sheet的名称
	 * @param columnRowNum 	单元格的位置
	 * @param value 		写入的值
	 * @throws Exception
	 */
	public void writeToExcel(String sheetName, int rowNum, int columnNum, Object value) throws Exception {
		Sheet sheet = this.workBook.getSheet(sheetName);
		this.writeToCell(sheet, rowNum, columnNum, value);
	}
	
	public void writeToExcel(String sheetName, int rowNum, int columnNum, Object value,CellStyle cellStyle) throws Exception {
		Sheet sheet = this.workBook.getSheet(sheetName);
		this.writeToCell(sheet, rowNum, columnNum, value,cellStyle);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetNum 		sheet的编号
	 * @param columnRowNum 	单元格的位置
	 * @param value 		写入的值
	 * @throws Exception
	 */
	public void writeToExcel(int sheetNum, String columnRowNum, Object value) throws Exception {
		Sheet sheet = this.workBook.getSheetAt(sheetNum);
		this.writeToCell(sheet, columnRowNum, value);
	}

	/**
	 * 向一个单元格写入值
	 * 
	 * @param sheetNum 		sheet的名称
	 * @param columnRowNum 	单元格的位置
	 * @param value 		写入的值
	 * @throws Exception
	 */
	public void writeToExcel(String sheetName, String columnRowNum, Object value) throws Exception {
		Sheet sheet = this.workBook.getSheet(sheetName);
		this.writeToCell(sheet, columnRowNum, value);
	}

	private void writeToCell(Sheet sheet, String columnRowNum, Object value) throws Exception {
		int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
		int rowNum = rowNumColumnNum[0];
		int columnNum = rowNumColumnNum[1];
		this.writeToCell(sheet, rowNum, columnNum, value);
	}

	/**
	 * 将单元格的行列位置转换为行号和列号,例如C10 => [9,2]
	 * 
	 * @param columnRowNum 		行列位置
	 * @return 					长度为2的数组，第1位为行号，第2位为列号
	 */
	private static int[] convertToRowNumColumnNum(String columnRowNum) {
		columnRowNum = columnRowNum.toUpperCase();
		char[] chars = columnRowNum.toCharArray();
		int rowNum = 0;
		int columnNum = 0;
		for (char c : chars) {
			if ((c >= 'A' && c <= 'Z')) {
				columnNum = columnNum * 26 + ((int) c - 64);
			} else {
				rowNum = rowNum * 10 + new Integer(c + "");
			}
		}
		return new int[] { rowNum - 1, columnNum - 1 };
	}

	private void writeToCell(Sheet sheet, int rowNum, int columnNum, Object value) throws Exception {
		Row row = sheet.getRow(rowNum);
		if(null == row){
			row = sheet.createRow(rowNum);
		}
		Cell cell = row.getCell(columnNum);
		if (cell == null) {
			cell = row.createCell(columnNum);
		}
		cell.setCellValue(convertString(value));
	}
	
	private void writeToCell(Sheet sheet, int rowNum, int columnNum, Object value,CellStyle cellStyle) throws Exception {
		Row row = sheet.getRow(rowNum);
		if(null == row){
			row = sheet.createRow(rowNum);
		}
		Cell cell = row.getCell(columnNum);
		if (cell == null) {
			cell = row.createCell(columnNum);
		}
		cell.setCellValue(convertString(value));
		
		//FileUtil.copyCellStyle(cellStyle, cellStyle2);
		
		//CellStyle newCellStyle = this.workBook.createCellStyle();
		//newCellStyle.cloneStyleFrom(cellStyle);
		
		CellStyle cellStyle2 = cell.getCellStyle();
		
		cellStyle2.cloneStyleFrom(cellStyle);
		cell.setCellStyle(cellStyle2);
		
		
	}

	/**
	 * 读取一个单元格的值
	 * 
	 * @param sheetName 	sheet的名称
	 * @param columnRowNum 	单元格的位置
	 * @return
	 * @throws Exception
	 */
	public Object readCellValue(String sheetName, String columnRowNum) throws Exception {
		Sheet sheet = this.workBook.getSheet(sheetName);
		int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
		int rowNum = rowNumColumnNum[0];
		int columnNum = rowNumColumnNum[1];
		Row row = sheet.getRow(rowNum);
		if (row != null) {
			Cell cell = row.getCell(columnNum);
			if (cell != null) {
				return getCellValue(cell);
			}
		}
		return null;
	}

	/**
	 * 获取单元格中的值
	 * 
	 * @param cell 	单元格
	 * @return
	 */
	public static Object getCellValue(Cell cell) {
		int type = cell.getCellType();
		switch (type) {
		case Cell.CELL_TYPE_STRING:
			return (Object) cell.getStringCellValue();
		case Cell.CELL_TYPE_NUMERIC:
			Double value = cell.getNumericCellValue();
			return (Object) (value.intValue());
		case Cell.CELL_TYPE_BOOLEAN:
			return (Object) cell.getBooleanCellValue();
		case Cell.CELL_TYPE_FORMULA:
			return (Object) cell.getArrayFormulaRange().formatAsString();
		case Cell.CELL_TYPE_BLANK:
			return (Object) "";
		default:
			return null;
		}
	}

	/**
	 * 插入一行并参照与上一行相同的格式
	 * 
	 * @param sheetNum 	sheet的编号
	 * @param rowNum 	插入行的位置
	 * @throws Exception
	 */
	public void insertRowWithFormat(int sheetNum, int rowNum) throws Exception {
		Sheet sheet = this.workBook.getSheetAt(sheetNum);
		insertRowWithFormat(sheet, rowNum);
	}

	/**
	 * 插入一行并参照与上一行相同的格式
	 * 
	 * @param sheetName 	sheet的名称
	 * @param rowNum 		插入行的位置
	 * @throws Exception
	 */
	public void insertRowWithFormat(String sheetName, int rowNum) throws Exception {
		Sheet sheet = this.workBook.getSheet(sheetName);
		insertRowWithFormat(sheet, rowNum);
	}

	private void insertRowWithFormat(Sheet sheet, int rowNum) throws Exception {
		sheet.shiftRows(rowNum, rowNum + 1, 1);
		Row newRow = sheet.createRow(rowNum);
		Row oldRow = sheet.getRow(rowNum - 1);
		for (int i = oldRow.getFirstCellNum(); i < oldRow.getLastCellNum(); i++) {
			Cell oldCell = oldRow.getCell(i);
			if (oldCell != null) {
				CellStyle cellStyle = oldCell.getCellStyle();
				newRow.createCell(i).setCellStyle(cellStyle);
			}
		}
	}

	
	/**
	 * 重命名一个sheet
	 * 
	 * @param sheetNum 	sheet的编号
	 * @param newName 	 新的名称
	 */
	public void renameSheet(int sheetNum, String newName) {
		this.workBook.setSheetName(sheetNum, newName);
	}

	/**
	 * 重命名一个sheet
	 * 
	 * @param oldName 	旧的名称
	 * @param newName 	新的名称
	 */
	public void renameSheet(String oldName, String newName) {
		int sheetNum = this.workBook.getSheetIndex(oldName);
		this.renameSheet(sheetNum, newName);
	}

	/**
	 * 删除一个sheet
	 * 
	 * @param sheetName 	sheet的名称
	 */
	public void removeSheet(String sheetName) {
		this.workBook.removeSheetAt(this.workBook.getSheetIndex(sheetName));
	}

	/** 
     * Sheet复制  (可以是同一个excel、也可以是不同excel中的sheet)
     * @param fromSheet 
     * @param toSheet 
     * @param copyValueFlag 
     */  
    public void copySheet(Sheet fromSheet, Sheet toSheet, boolean copyValueFlag) {  
        //合并区域处理  
        mergerRegion(fromSheet, toSheet);  
        for (Iterator rowIt = fromSheet.rowIterator(); rowIt.hasNext();) {  
            Row tmpRow = (Row) rowIt.next();  
            Row newRow = toSheet.createRow(tmpRow.getRowNum());  
            //行复制  
            copyRow(fromSheet,toSheet,tmpRow,newRow,copyValueFlag);  
        }  
    }  
    
    /**
     * 复制多行到指定行
     * @param startRow 想要复制的多行的第一行的行号
     * @param endRow 复制多行的最后一行的行号
     * @param positionRow 指定的行的标示
     * @param sheet 
     */
    public void copyRows(int startRow, int endRow, int positionRow,  
			Sheet sheet) {  
        int pStartRow = startRow - 1;  
        int pEndRow = endRow - 1;  
        int targetRowFrom;  
        int targetRowTo;  
        int columnCount;  
        CellRangeAddress region = null;  
        int i;  
        int j;  
        if (pStartRow == -1 || pEndRow == -1) {  
            return;  
        }  
        // 拷贝合并的单元格  
        for (i = 0; i < sheet.getNumMergedRegions(); i++) {  
            region = sheet.getMergedRegion(i);  
            if ((region.getFirstRow() >= pStartRow)  
                    && (region.getLastRow() <= pEndRow)) {  
                targetRowFrom = region.getFirstRow() - pStartRow + positionRow;  
                targetRowTo = region.getLastRow() - pStartRow + positionRow;  
                CellRangeAddress newRegion = region.copy();  
                newRegion.setFirstRow(targetRowFrom);  
                newRegion.setFirstColumn(region.getFirstColumn());  
                newRegion.setLastRow(targetRowTo);  
                newRegion.setLastColumn(region.getLastColumn());  
                sheet.addMergedRegion(newRegion);  
            }  
        }  
        // 设置列宽  
        for (i = pStartRow; i <= pEndRow; i++) {  
        	Row sourceRow = sheet.getRow(i);  
            columnCount = sourceRow.getLastCellNum();  
            if (sourceRow != null) {  
            	Row newRow = sheet.createRow(positionRow - pStartRow + i);  
                newRow.setHeight(sourceRow.getHeight());  
                for (j = 0; j < columnCount; j++) {  
                	Cell templateCell = sourceRow.getCell(j);  
                    if (templateCell != null) {  
                    	Cell newCell = newRow.createCell(j);  
                        copyCell(templateCell, newCell);  
                    }  
                }  
            }  
        }  
    }  
  
    //复制单元格
    private void copyCell(Cell srcCell, Cell distCell) {  
        distCell.setCellStyle(srcCell.getCellStyle());  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (srcCellType == Cell.CELL_TYPE_NUMERIC) {  
            if (HSSFDateUtil.isCellDateFormatted(srcCell)) {  
                distCell.setCellValue(srcCell.getDateCellValue());  
            } else {  
                distCell.setCellValue(srcCell.getNumericCellValue());  
            }  
        } else if (srcCellType == Cell.CELL_TYPE_STRING) {  
            distCell.setCellValue(srcCell.getRichStringCellValue());  
        } else if (srcCellType == Cell.CELL_TYPE_BLANK) {  
            // nothing 
        } else if (srcCellType == Cell.CELL_TYPE_BOOLEAN) {  
            distCell.setCellValue(srcCell.getBooleanCellValue());  
        } else if (srcCellType == Cell.CELL_TYPE_ERROR) {  
            distCell.setCellErrorValue(srcCell.getErrorCellValue());  
        } else if (srcCellType == Cell.CELL_TYPE_FORMULA) {  
            distCell.setCellFormula(srcCell.getCellFormula());  
        } else { // nothing 
  
        }  
    }

    /**
     * 行复制功能   (如果原始行中有隐藏列，则不复制)
     * @param fromSheet
     * @param toSheet
     * @param fromRow
     * @param toRow
     * @param copyValueFlag
     */
    public void copyRow(Sheet fromSheet, Sheet toSheet,Row fromRow,Row toRow,boolean copyValueFlag){
    	//复制行高
    	toRow.setHeight(fromRow.getHeight());
    	//隐藏列 数目
    	int hiddenCount = 0;
    	List<String> newColumnNames = new ArrayList<String>();
    	
    	
        for (Iterator cellIt = fromRow.cellIterator(); cellIt.hasNext();) {  
            Cell oldCell = (Cell) cellIt.next();  
            int columnIndex = oldCell.getColumnIndex();
			
            
			if(fromSheet.isColumnHidden(columnIndex)){
				hiddenCount++;
				continue;
			}
			String cellName = oldCell.getStringCellValue();
			//特殊处理，只有当需要对列 进行过滤的情况，才会执行下面的处理，（将columnNames内部的顺序重新排序）
			if(!CollectionUtils.isEmpty(columnNames) && !columnNames.contains(cellName)){
				hiddenCount++;
				continue;
			}else if(!CollectionUtils.isEmpty(columnNames) && columnNames.contains(cellName)){
				newColumnNames.add(cellName);
			}
			
			Cell newCell = toRow.createCell(columnIndex-hiddenCount);  
			
			//复制单元格样式、值
            copyCell(oldCell, newCell, copyValueFlag);
            //设置列宽
            toSheet.setColumnWidth(columnIndex-hiddenCount, fromSheet.getColumnWidth(columnIndex));
        }
        
        //特殊处理，列有过滤的情况，如果重新排列，则重新赋值
        if(!CollectionUtils.isEmpty(newColumnNames)){
        	this.columnNames = newColumnNames;
        }
    }  
    
    /** 
    * 复制原有sheet的合并单元格到新创建的sheet 
    * @param sheetCreat 新创建sheet 
    * @param sheet      原有的sheet 
    */  
    public void mergerRegion(Sheet fromSheet, Sheet toSheet) {  
       int sheetMergerCount = fromSheet.getNumMergedRegions();  
       for (int i = 0; i < sheetMergerCount; i++) {  
        CellRangeAddress mergedRegion = fromSheet.getMergedRegion(i);  
        toSheet.addMergedRegion(mergedRegion);  
       }  
    }  
    
    /** 
     * 复制单元格 
     * @param srcCell 
     * @param distCell 
     * @param copyValueFlag  true则连同cell的内容一起复制 
     */  
    public void copyCell(Cell srcCell, Cell distCell, boolean copyValueFlag) {  
        CellStyle newstyle=this.workBook.createCellStyle();  
        newstyle.cloneStyleFrom(srcCell.getCellStyle());
        distCell.setCellStyle(newstyle);  
        
        //评论  
        if (srcCell.getCellComment() != null) {  
            distCell.setCellComment(srcCell.getCellComment());  
        }  
        
        // 不同数据类型处理  
        int srcCellType = srcCell.getCellType();  
        distCell.setCellType(srcCellType);  
        if (copyValueFlag) {  
            if (srcCellType == Cell.CELL_TYPE_NUMERIC) {  
                if (DateUtil.isCellDateFormatted(srcCell)) {  
                    distCell.setCellValue(srcCell.getDateCellValue());  
                } else {  
                    distCell.setCellValue(srcCell.getNumericCellValue());  
                }  
            } else if (srcCellType == Cell.CELL_TYPE_STRING) {  
                distCell.setCellValue(srcCell.getRichStringCellValue());  
            } else if (srcCellType == Cell.CELL_TYPE_BLANK) {  
                // nothing21  
            } else if (srcCellType == Cell.CELL_TYPE_BOOLEAN) {  
                distCell.setCellValue(srcCell.getBooleanCellValue());  
            } else if (srcCellType == Cell.CELL_TYPE_ERROR) {  
                distCell.setCellErrorValue(srcCell.getErrorCellValue());  
            } else if (srcCellType == Cell.CELL_TYPE_FORMULA) {  
                distCell.setCellFormula(srcCell.getCellFormula());  
            } else { // nothing29  
            }  
        }  
    }  
	
    /**
	 * 合并单元格
	 * @param sheet
	 * @param firstRow 	开始行
	 * @param lastRow 	最后行
	 * @param firstCol 	开始列
	 * @param lastCol 	最后列
	 */
	public void mergeCell(Sheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	/**
	 * 设置单元格对齐方式
	 * @param halign
	 * @param valign
	 */
	public CellStyle setAlign(short halign, short valign) {
		CellStyle style = this.workBook.createCellStyle();
		style.setAlignment(halign);
		style.setVerticalAlignment(valign);
		return style;
	}          
	
	
	/**
	 * 合并单元格
	 * @param sheet 	要合并单元格的excel 的sheet
	 * @param rowNum 	要合并的列
	 * @param startRow 	要合并列的开始行
	 * @param endRow    要合并列的结束行
	 */
	public  void addMergedRegion(Sheet sheet, int rowNum, int startRow, int endRow) {

		CellStyle style = this.workBook.createCellStyle(); // 样式对象

		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 垂直
		style.setAlignment(CellStyle.ALIGN_CENTER);// 水平
		// 获取第一行的数据,以便后面进行比较
		String s_will = sheet.getRow(startRow).getCell(rowNum).getStringCellValue();

		int count = 0;
		int start = startRow;
		boolean flag = false;
		for (int i = start; i <= endRow; i++) {
			String s_current = sheet.getRow(i).getCell(0).getStringCellValue();
			if (s_will.equals(s_current)) 
			{
				s_will = s_current;
				if (flag) {
					sheet.addMergedRegion(new CellRangeAddress(startRow - count + 1, startRow, rowNum, rowNum));
					Row row = sheet.getRow(startRow - count + 1);
					String cellValueTemp = sheet.getRow(startRow - count + 1).getCell(0).getStringCellValue();
					Cell cell = row.createCell(0);
					cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
					cell.setCellStyle(style); // 样式
					count = 0;
					flag = false;
				}
				startRow = i;
				count++;
			} else {
				flag = true;
				s_will = s_current;
			}
			// 由于上面循环中合并的单元放在有下一次相同单元格的时候做的，所以最后如果几行有相同单元格则要运行下面的合并单元格。
			if (i == endRow && count > 0) {
				sheet.addMergedRegion(new CellRangeAddress(endRow - count, endRow, rowNum, rowNum));
				String cellValueTemp = sheet.getRow(startRow - count).getCell(0).getStringCellValue();
				Row row = sheet.getRow(startRow - count);
				Cell cell = row.createCell(0);
				cell.setCellValue(cellValueTemp); // 跨单元格显示的数据
				cell.setCellStyle(style); // 样式
			}
		}
	}
	
	/**
	 * 写入Excel文件并关闭
	 */
	public void writeAndClose(String pathName) {
		if (this.workBook != null) {
			try {
				outStream = new FileOutputStream(pathName);
				this.workBook.write(outStream);
				if (outStream != null) {
					outStream.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	private static String convertString(Object value) {
		if (value == null) {
			return "";
		} else {
			return value.toString();
		}
	}

	public List<String> getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(List<String> columnNames) {
		this.columnNames = columnNames;
	}

	public List<String> getColumnNamesText() {
		return columnNamesText;
	}

	public void setColumnNamesText(List<String> columnNamesText) {
		this.columnNamesText = columnNamesText;
	}
	
}
