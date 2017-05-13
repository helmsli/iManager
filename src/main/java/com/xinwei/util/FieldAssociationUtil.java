package com.xinwei.util;

import org.apache.commons.lang3.StringUtils;


/**
 * 字段关联工具类，主要用于字段中存储外键关系。  例如 ,12,123,1234,
 * @author shaoyong
 *
 */
public class FieldAssociationUtil {
	/**
	 * 获取字段替换后的值
	 * @param fieldValue 原字段值
	 * @param id		 添加id  或者 删除id
	 * @param isAdd		true：添加   false：删除
	 * @return
	 */
	public static String getFieldValue(String fieldValue,Long id,Boolean isAdd){
		String result = null;
		//分配基站到组
		if(isAdd){
			result = StringUtils.isBlank(fieldValue) ? ("," + id + ",") : (fieldValue + id + ",");
		}else{
			//删除该组的基站
			result = fieldValue.replace("," + id , "");
			if(result.equals(",")){
				result = null;
			}
		}
		return result;
	}
}
