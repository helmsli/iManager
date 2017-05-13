package com.xinwei.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xinwei.security.MessageCode;
import com.xinwei.security.entity.Function;
import com.xinwei.security.entity.Menu;
import com.xinwei.security.service.FunctionService;
import com.xinwei.security.service.MenuService;
import com.xinwei.security.vo.ResultVO;
import com.xinwei.util.ResponseOutJsonUtil;

public class PermissionControllerFilter extends AuthorizationFilter {

	@Autowired
	private MenuService menuService;

	@Autowired
	private FunctionService functionService;

	private final Logger logger = LoggerFactory.getLogger(PermissionControllerFilter.class.getName());

	
	
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;  
        HttpServletResponse httpResponse = (HttpServletResponse) response;  
  
        Subject subject = getSubject(request, response);  
  
        if (subject.getPrincipal() == null) {  
            if (isAjax(httpRequest)) {  
            	ResponseOutJsonUtil.out(httpResponse,new ResultVO(MessageCode.SESSION_TIMEOUT).toString() );
            } else {  
                saveRequestAndRedirectToLogin(request, response);  
            }  
        } else {  
            if (isAjax(httpRequest)) {  
            	ResponseOutJsonUtil.out(httpResponse,new ResultVO(MessageCode.NO_PERMISSION).toString() );
            } else {  
                String unauthorizedUrl = getUnauthorizedUrl();  
                if (StringUtils.hasText(unauthorizedUrl)) {  
                    WebUtils.issueRedirect(request, response, unauthorizedUrl);  
                } else {  
                    WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);  
                }  
            }  
        }  
        return false;  
	}
	
	

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {

		// 根据数据库menu、function表 获取映射关系（ action -> 权限关键字 ）
		Map<String, String> actionPermissions = new HashMap<>();
		List<Menu> menus = menuService.findAll();
		List<Function> functions = functionService.findAll();
		for (Menu menu : menus)
			actionPermissions.put(menu.getAction(), menu.getSn());
		for (Function function : functions)
			actionPermissions.put(function.getAction(), function.getSn());

		// 获取当前action的权限关键字
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String action = httpServletRequest.getServletPath();
		String currentURL = httpServletRequest.getServletPath();
		String permission = actionPermissions.get(currentURL);

		// 数据库中未配置，则不需要权限验证
		if (org.apache.commons.lang3.StringUtils.isEmpty(permission)) 
			return true;
		else {
			Subject subject = getSubject(request, response);
			return subject.isPermitted(permission) ? true : false;
		}
	}
	
	
	
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	

}
