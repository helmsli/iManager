package com.xinwei.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import com.xinwei.security.MessageCode;
import com.xinwei.security.exception.ServiceException;
import com.xinwei.security.vo.ResultVO;


/**
 * 全局异常处理，返回json字符串，用于前台ajax访问
 * @author shaoyong
 *
 */
public class JsonExceptionResolver extends AbstractHandlerExceptionResolver {
	
	Logger logger = Logger.getLogger(JsonExceptionResolver.class);
	
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		try {
			logger.error(ex.getMessage());
			ex.printStackTrace();
			if(ex instanceof AuthorizationException){
				ResultVO result = new  ResultVO<>(MessageCode.NO_PERMISSION);
				response.getWriter().print(result.toString());
			}else if(ex instanceof ServiceException){
				ResultVO result = new  ResultVO<>(ex.getMessage());
				response.getWriter().print(result.toString());
			}else{
				ResultVO result = new  ResultVO<>(MessageCode.OTHER_ERROR);
				response.getWriter().print(result.toString());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new ModelAndView();
	}

}
