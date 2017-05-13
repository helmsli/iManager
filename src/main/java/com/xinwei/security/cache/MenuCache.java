package com.xinwei.security.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xinwei.security.dao.FunctionDao;
import com.xinwei.security.dao.MenuDao;
import com.xinwei.security.entity.Function;
import com.xinwei.security.entity.Menu;
import com.xinwei.util.spring.SpringFactory;

/**
 * 缓存 菜单、功能 数据(暂时未用)
 * @author shaoyong
 *
 */
public class MenuCache {
	private MenuCache(){}
	
	private static MenuDao menuDao = (MenuDao) SpringFactory.getBean("menuDao");
	
	private static FunctionDao functionDao = (FunctionDao) SpringFactory.getBean("functionDao");
	
	//所有的菜单
	private static List<Menu> menus = new ArrayList<Menu>();
	
	//菜单id <---> 功能列表
	private static Map<Long,List<Function>> menu2functions = new HashMap<>();
	
	
	static{
		menus = menuDao.findAll();
		List<Function> functions = functionDao.findAll();
		
		for (Menu menu : menus) {
			menu2functions.put(menu.getId(), new ArrayList<Function>(0));
		}
		
		for (Function function : functions) {
			addFunction(function);
		}
	}
	
	public static List<Menu> getMenus(){
		return menus;
	}

	
	public static List<Function> getFunctions(Long menuId){
		return menu2functions.get(menuId);
	}
	
	
	/**
	 * 增加功能
	 * @param function
	 */
	private static void addFunction(Function function) {
		List<Function> list = menu2functions.get(function.getMenu_id());
		list.add(function);
		//menu2functions.put(function.getMenu_id(), list);
	}
	
	
	
	
	
	
	
	/**
	 * 删除功能
	 * @param function
	 */
	/*private static void deleteFunction(Function function){
		List<Function> list = menu2functions.get(function.getMenu_id());
		list.remove(function);
	}*/
	
	
	
	
}
