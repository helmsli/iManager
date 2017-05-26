
package com.xinwei.security.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinwei.security.Constants;
import com.xinwei.security.MessageCode;
import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.security.service.UserService;
import com.xinwei.security.shiro.ShiroDbRealm;
import com.xinwei.security.shiro.UserHelper;
import com.xinwei.security.vo.ResultVO;

@Controller
@RequestMapping("/login")
public class LoginControllerNew {
	private static final String LOGIN_PAGE = "login";
	private static final String HOME_PAGE = "home";

	@Autowired
	private UserService userService;

	@Autowired
	UserRoleService userRoleService;

	
	/**
	 * 获取基础路径，前台用于判断是否要加上项目名
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "getContextPath", method=RequestMethod.POST)
	public @ResponseBody String getContextPath(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		
		ResultVO result = new ResultVO();
		result.setOthers("contextPath", contextPath);
		return result.toString();
	}
	
	@RequestMapping(value = "tologin", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser && currentUser.isAuthenticated()) {
			// return HOME_PAGE;
			return "redirect:/views/home.html";
		}
		return LOGIN_PAGE;

		
	}

	/*
	 * @RequestMapping(method = RequestMethod.POST) public String fail(
	 * 
	 * @RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String
	 * username, Map<String, Object> map, HttpServletRequest request) { String
	 * msg = parseException(request); map.put("msg", msg); map.put("username",
	 * username); return LOGIN_PAGE; }
	 */

	@RequestMapping(value = "tologin", method = { RequestMethod.POST })
	public @ResponseBody String login(String username, String password, HttpServletRequest request) {

		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		// token.setRememberMe(true);
		Subject currentUser = SecurityUtils.getSubject();

		String messageCode = ResultVO.SUCCESS;

		try {
			currentUser.login(token);
		} catch (UnknownAccountException ex) {
			messageCode = MessageCode.LOGIN_ACCOUNT_ERROR;
			ex.printStackTrace();
		} catch (IncorrectCredentialsException ex) {
			messageCode = MessageCode.LOGIN_PASSWORD_ERROR;
			ex.printStackTrace();
		} catch (LockedAccountException ex) {
			messageCode = MessageCode.LOGIN_ACCOUNT_LOCKED;
			ex.printStackTrace();
		} catch (ExcessiveAttemptsException ex) {
			messageCode = MessageCode.LOGIN_EXCESSIVE_ERROR;
			ex.printStackTrace();
		} catch (DisabledAccountException ex) {
			messageCode = MessageCode.LOGIN_DISABLED_ERROR;
			ex.printStackTrace();
		} catch (AuthenticationException ex) {
			messageCode = MessageCode.LOGIN_AUTHTICATION_FAIL;
			ex.printStackTrace();
		}

		if (currentUser.isAuthenticated()) {
			User user = UserHelper.getUser();
			// 设置用户角色
			List<Role> roles = userRoleService.findRolesByUserId(user.getId());
			user.setRoles(roles);
			ResultVO<User> result = new ResultVO<>();
			result.setEntity(user);
			return result.toString();
		} else {
			return new ResultVO<>(messageCode).toString();
		}

	}

	private String parseException(HttpServletRequest request) {
		String error = (String) request

				.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String msg = "其他错误！";
		if (error != null) {
			if ("org.apache.shiro.authc.UnknownAccountException".equals(error))
				msg = "未知帐号错误！";
			else if ("org.apache.shiro.authc.IncorrectCredentialsException".equals(error))
				msg = "密码错误！";
			else if ("com.ketayao.security.shiro.IncorrectCaptchaException".equals(error))
				msg = "验证码错误！";
			else if ("org.apache.shiro.authc.AuthenticationException".equals(error))
				msg = "认证失败！";
			else if ("org.apache.shiro.authc.DisabledAccountException".equals(error))
				msg = "账号被冻结！";
		}
		return "登录失败，" + msg;
	}
}
