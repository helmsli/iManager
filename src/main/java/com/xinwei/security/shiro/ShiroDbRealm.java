
package com.xinwei.security.shiro;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import com.xinwei.security.entity.Role;
import com.xinwei.security.entity.User;
import com.xinwei.security.service.RolePermissionService;
import com.xinwei.security.service.UserRoleService;
import com.xinwei.security.service.UserService;
import com.xinwei.util.encrypt.Digests;
import com.xinwei.util.encrypt.Encodes;


public class ShiroDbRealm extends AuthorizingRealm {
	private static final int INTERATIONS = 1024;
	private static final int SALT_SIZE = 8;
	private static final String ALGORITHM = "SHA-1";

	protected boolean useCaptcha = false;
	
	protected UserService userService;
	
	protected UserRoleService userRoleService;
	
	protected RolePermissionService rolePermissionService;
	
	/**
	 * 给ShiroDbRealm提供编码信息，用于密码密码比对
	 * 描述
	 */	
	public ShiroDbRealm() {
		super();
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(ALGORITHM);
		matcher.setHashIterations(INTERATIONS);
		setCredentialsMatcher(matcher);
	}

	/**
	 * 验证密码 是否与当前登录用户的密码 一致
	 * @param password
	 * @return
	 */ 
	public Boolean accordancePassword(String password) {
		//这里为什么没有直接获取Shiro session中的用户？
		//因为在User类中 password salt 被设置为transient，所以这些字段为空（前台传输的时候，不能传输这些字段）。只有重新获取
		
		User user = UserHelper.getUser();
		UsernamePasswordToken token = new UsernamePasswordToken( user.getUsername(), password );
		
		User dbUser = userService.get(user.getUsername());
		ShiroUser shiroUser = new ShiroUser(dbUser.getId(), dbUser.getUsername(), dbUser);
		byte[] salt = Encodes.decodeHex(dbUser.getSalt());
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(shiroUser, dbUser.getPassword(), ByteSource.Util.bytes(salt), getName());
		
		return getCredentialsMatcher().doCredentialsMatch(token, info);
	}
	
	/**
	 * 认证回调函数, 登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		/*
		if (useCaptcha) {
			CaptchaUsernamePasswordToken token = (CaptchaUsernamePasswordToken) authcToken;
			
			String parm = token.getCaptcha();
			String c = (String)SecurityUtils.getSubject().getSession()
					.getAttribute(SimpleCaptchaServlet.CAPTCHA_KEY);
			// 忽略大小写
			if (!parm.equalsIgnoreCase(c)) {
				throw new IncorrectCaptchaException("验证码错误！"); 
			} 
		} 
		UsernamePasswordToken token = (UsernamePasswordToken)authcToken;
		*/
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		
		
		User user = userService.get(token.getUsername());
		if (user != null) {
			if (user.getStatus()==1 || user.getIsDisabled() == 1) {
				throw new DisabledAccountException();
			}
			
			byte[] salt = Encodes.decodeHex(user.getSalt());
			
			ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(), user);
			return new SimpleAuthenticationInfo(shiroUser, user.getPassword(), ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
		
	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.fromRealm(getName()).iterator().next();
		
		Subject subject = SecurityUtils.getSubject();
		
		//获取用户的所有角色权限信息
		List<Role> roles = userRoleService.findRoleDetailsByUserId(shiroUser.getId());
		shiroUser.getUser().setRoles(roles);
		
		if (!roles.isEmpty()) 
		{
			List<Long> roleIds = new ArrayList<Long>();
			SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			for (Role role : roles) {
				roleIds.add(role.getId());
				//基于Permission的权限信息
				info.addStringPermissions(role.getPermissionStrs());
			}
			//设置roleIds
			shiroUser.getUser().setRoleIds(roleIds);
			return info;
		} else {
			return null;
		}
	}
	
	public static class HashPassword {
		public String salt;
		public String password;
	}
	
	public HashPassword encrypt(String plainText) {
		HashPassword result = new HashPassword();
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		result.salt = Encodes.encodeHex(salt);

		byte[] hashPassword = Digests.sha1(plainText.getBytes(), salt, INTERATIONS);
		result.password = Encodes.encodeHex(hashPassword);
		return result;

	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(String principal) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(principal, getName());
		clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 清除所有用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
		if (cache != null) {
			for (Object key : cache.keys()) {
				cache.remove(key);
			}
		}
	}
	
	/**  
	 * 设置 useCaptcha 的值  
	 * @param useCaptcha
	 */
	public void setUseCaptcha(boolean useCaptcha) {
		this.useCaptcha = useCaptcha;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	/**  
	 * 设置 userRoleService 的值  
	 * @param userRoleService
	 */
	public void setUserRoleService(UserRoleService userRoleService) {
		this.userRoleService = userRoleService;
	}
	
	

	public RolePermissionService getRolePermissionService() {
		return rolePermissionService;
	}

	public void setRolePermissionService(RolePermissionService rolePermissionService) {
		this.rolePermissionService = rolePermissionService;
	}

	public UserService getUserService() {
		return userService;
	}

	public UserRoleService getUserRoleService() {
		return userRoleService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1748602382963711884L;
		private Long id;
		private String username;
		private User user;
		
		public ShiroUser() {
			
		}
		
		/**  
		 * 构造函数
		 * @param id
		 * @param loginName
		 * @param email
		 * @param createTime
		 * @param xinweius  
		 */ 
		public ShiroUser(Long id, String loginName, User user) {
			this.id = id;
			this.username = loginName;
			this.user = user;
		}

		/**  
		 * 返回 id 的值   
		 * @return id  
		 */
		public Long getId() {
			return id;
		}

		/**  
		 * 返回 loginName 的值   
		 * @return loginName  
		 */
		public String getUsername() {
			return username;
		}

		/**  
		 * 返回 user 的值   
		 * @return user  
		 */
		public User getUser() {
			return user;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return username;
		}
	}
}
