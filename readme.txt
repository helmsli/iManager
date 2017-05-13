									项目流程管理系统V0.1
									
一、系统功能
	1、	用户管理
	2、	角色管理
	3、	角色权限管理
	4、     项目流程管理
	


二、代码结构
	Src/main/java
	---com.xinwei.filter                系统中自定义的过滤器
	---com.xinwei.security				权限系统中的controller、service、dao、entity等
	---com.xinwei.util					系统中自定义的工具类
	---com.xinwei.process               项目流程中的controller、service、dao、entity等
	
	Src/main/resources
	----deployment
	--------projectprocess.bpmn20.xml   项目流程定义文件
	--------projectprocess.png          项目流程模型图片
	----ehcache
	--------ehcache-mybatis.xml   		mybatis的ehcache缓存配置
	--------ehcache-shiro.xml     		shiro的ehcache缓存配置
	
	----mapper/mysql
	--------security/**.xml       		权限框架的mybatis映射配置文件
	--------process/**.xml       		项目流程的mybatis映射配置文件
	
	----mybatis/mybatis-config.xml 		mybatis公共配置文件
	
	----schema/mysql/schema.sql  		mysql的数据库脚本文件
	
	----application.properties        	数据库连接配置信息
	----applicationContext-shiro.xml  	shiro配置文件
	----applicationContext.xml		 	spring配置文件
	----spring_activiti.xml				activiti的配置文件
	----spring-mvc.xml				 	spring-mvc配置文件
	
	
	Src/main/webapp
	----css
	----fonts
	----images
	----js
	----plugins
	
	Src/main/webapp/WEB-INF/views
	----userCenter                       用户管理模块

	


三、安装说明
	1、执行mysql数据库安装
	mysql数据库脚本在 /src/main/resources/schema/mysql/schema.sql
	
	注意：该sql中包含中文，如果通过客户端工具操作，需要注意中文乱码的问题。
	
	2、修改项目数据库连接信息
	在/src/main/resources/application.properties中 ，修改数据库连接，用户名、密码
	
	3、将项目字符集编码设置为utf-8

	4、配置项目环境。目前采用的是tomcat7、jdk7
	
	5、运行项目后，直接访问http://localhost:8080/process，
	    初始用户：admin   密码：123456



四、扩展说明
	1、在后面编写自己的模块，需要在applicationContext-shiro.xml中，加入权限控制
	<property name="filterChainDefinitions">
			<value>
				/js/** = anon
				/css/** = anon
				/styles/** = anon
				/images/** = anon
				/plugins/** = anon
				/fonts/** = anon
				/login/timeout = anon
				/logout = logout
				/login/tologin = anon
				/views/** = user,permissionFilter
				/management/** = user,permissionFilter
		    	/** = user
		 	</value>
	</property>
	
	只需要像上面加入一行
	/management/** = user,permissionFilter（这个就是系统管理模块）


	2、查询列表接口以/list 结尾
	比如查询用户列表   /management/user/list
	因为项目中以/**/list方式 做了过滤器操作，对分页参数进行了封装。


	3、	如果有菜单添加
	在security_menu表中维护一条数据

	有按钮权限添加
	在security_function表中 维护一条数据

	然后初始化security_role_permission，给admin添加权限配置。（因为默认 admin，拥有所有的权限）



