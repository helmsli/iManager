package com.xinwei.filter;

import java.io.IOException;

import javax.servlet.FilterChain;  
import javax.servlet.FilterConfig;  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.xinwei.util.page.Page;
import com.xinwei.util.page.PaginationContext;  

public class PageFilter extends BaseFilter {  
    @Override  
    public void init() throws ServletException {//初始化  
        FilterConfig config = getConfig();  
    }  
      
    
    
      
    public void doWhiteFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)  
        throws IOException, ServletException {  
                  
		//判断是否是请求分页
		String parameter = request.getParameter("pageNum");
		if(StringUtils.isBlank(parameter)){
			chain.doFilter(request, response);
		}else{
			try {
				PaginationContext.setPageNum(getPageNum(request));
				PaginationContext.setPageSize(getPageSize(request));
				chain.doFilter(request, response);
			}
			// 使用完Threadlocal，将其删除。使用finally确保一定将其删除
			finally {
				PaginationContext.removePageNum();
				PaginationContext.removePageSize();
			}
		}
    }  
      
    @Override  
    public void destroy() {//销毁  
    }  
    
    
    
    /**
	 * 获得pager.offset参数的值
	 * 
	 * @param request
	 * @return
	 */
	protected int getPageNum(HttpServletRequest request) {
		int pageNum = 1;
		try {
			String pageNums = request.getParameter("pageNum");
			if (pageNums != null && StringUtils.isNumeric(pageNums)) {
				pageNum = Integer.parseInt(pageNums);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pageNum;
	}

	/**
	 * 设置默认每页大小
	 * 
	 * @return
	 */
	protected int getPageSize(HttpServletRequest request) {
		int pageSize = Page.DEFAULT_PAGE_SIZE; // 默认每页10条记录
		try {
			String pageSizes = request.getParameter("pageSize");
			if (pageSizes != null && StringUtils.isNumeric(pageSizes)) {
				pageSize = Integer.parseInt(pageSizes);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return pageSize;
	}
      
}  

