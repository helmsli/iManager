package com.xinwei.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 用于部门上下级的工具类
 * 
 * @author shaoyong
 *
 */
public class LevelUtil {

	/**
	 * 判断childLevelCode 是否是 levelCode的 子级
	 * 
	 * @param currentLevelCode  当前用户的等级
	 * @param childLevelCode	子级
	 * @return
	 */
	public static Boolean isChild(String currentLevelCode, String childLevelCode) {
		if(StringUtils.isEmpty(currentLevelCode))
			return true;
		if(StringUtils.isEmpty(childLevelCode))
			return false;
		return childLevelCode.startsWith(currentLevelCode) ? true : false;
	}
	
	
	/**
	 * 判断 是否是 同级，或者子级
	 * @param currentLevelode	当前等级
	 * @param childLevelCode	子级
	 * @return
	 */
	public static Boolean isSameLevelOrChild(String currentLevelCode, String childLevelCode) {
		
		//顶级用户
		if(StringUtils.isEmpty(currentLevelCode))
			return StringUtils.isEmpty(childLevelCode) ? true : false;
		else{
			//不是顶级用户
			if(currentLevelCode.equals(childLevelCode)){
				return true;
			}else{
				return isChild(currentLevelCode,childLevelCode);
			}
		}
	}

	
	
	/**
	 * 编码code1 和 code2 是否有关联
	 * @param code1
	 * @param code2
	 * @return
	 */
	public static Boolean hasRelation(String code1,String code2){
		if(StringUtils.isEmpty(code1) || StringUtils.isEmpty(code2)){
			return true;
		}
		return code1.startsWith(code2) || code2.startsWith(code1) ? true : false;
	}
	
	
	
	
	
	
	/**
	 * 创建同级代码 
	 * @param levelCode			
	 * @return
	 */
	public static String createSameLevelCode(String levelCode) {
		String level = levelCode.substring(levelCode.length() - 2);
		int temp = Integer.parseInt(level) + 1;
		 
		String prefix = levelCode.substring(0,levelCode.length() - 2);
		return temp >= 10 ? prefix + temp : prefix + "0" + temp;
	}

	
	/**
	 * 创建子级代码
	 * @param levelCode
	 * @return
	 */
	public static String createChildLevelCode(String levelCode) {
		return StringUtils.isEmpty(levelCode) ? "01" : levelCode + "01";
	}
	
	
	

}
