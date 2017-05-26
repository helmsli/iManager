package com.xinwei.util.page;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class Page<T> implements Serializable {
	public final static String ORDER_DIRECTION_ASC = "ASC";
	public final static String ORDER_DIRECTION_DESC = "DESC";
	/**
	 * 默认每页记录数
	 */
	public static final int DEFAULT_PAGE_SIZE = 15;

	private static final long serialVersionUID = 8656597559014685635L;
	private long totalNum=0; // 总记录数
	private int pageNum = 1; // 第几页
	private int pageSize = DEFAULT_PAGE_SIZE; // 每页记录数
	private int startRow=0; // 起始行
	private int pages=0; // 总页数

	private String orderField = "";
	private String direct = ORDER_DIRECTION_DESC;
	
	private transient List<T> list; // 结果集

	/**
	 * 设置分页参数
	 * @param pageNum
	 * @param pageSize
	 * @param total
	 */
	public Page(Long totalSize) {
		//setPageNum(PaginationContext.getPageNum());
		//setPageSize(PaginationContext.getPageSize());
		if(null == totalSize)
			totalSize = 0L;
		initPage();
		setTotalNum(totalSize);
	}


	private void initPage() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		int pageSize = Page.DEFAULT_PAGE_SIZE; // 默认每页记录
		int pageNum = 1;
		try {
			String pageSizes = request.getParameter("pageSize");
			if (pageSizes != null && StringUtils.isNumeric(pageSizes)) {
				pageSize = Integer.parseInt(pageSizes);
			}
			/*else if(pageSizes == null){
				pageSizes = (String) request.getAttribute("pageSize");
				if (pageSizes != null && StringUtils.isNumeric(pageSizes)) {
					pageSize = Integer.parseInt(pageSizes);
				}
			}*/
			
			String pageNums = request.getParameter("pageNum");
			if (pageNums != null && StringUtils.isNumeric(pageNums)) {
				pageNum = Integer.parseInt(pageNums);
			}
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		this.pageSize = pageSize;
		this.pageNum = pageNum;
	}


	public long getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(long totalNum) {
		this.totalNum = totalNum;
		if (pageSize > 0) {
			pages = (int) (totalNum / pageSize + ((totalNum % pageSize == 0) ? 0 : 1));
		} else {
			pages = 0;
		}
		if (pageNum > pages) {
			pageNum = pages;
		}
		this.startRow = this.pageNum > 0 ? (this.pageNum - 1) * this.pageSize  : 0;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	/**
	 * 返回 pageNum 的值
	 * 
	 * @return pageNum
	 */
	public int getPageNum() {
		if (pageNum > pages) {
			pageNum = pages;
		}
		return pageNum;
	}

	/**
	 * 设置 pageNum 的值
	 * 
	 * @param pageNum
	 */
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum > 0 ? pageNum : 1;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

}