package com.xinwei.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

public class ListUtil {
	/***
	 * 将list按照指定大小 分隔成多个list
	 * @param list
	 * @param pageSize
	 * @return
	 */
	public static <T> List<List<T>> splitList(List<T> list, int pageSize) {
		if(CollectionUtils.isEmpty(list)) 	return null;
		
        List<List<T>> listArray = new ArrayList<List<T>>(); 
        
        ArrayList<T> all = new ArrayList<T>();
        for(T x : list){
            all.add(x);
            if (pageSize == all.size()){
                listArray.add(all);
                all = new ArrayList<T>();
            }
        }
        
        if (0 != all.size())
            listArray.add(all);
        
        return listArray;
    }
	
	/**
	 * 将字符串集合转换成Long类型的集合
	 * @param inList 字符串集合
	 * @return
	 */
	public static List<Long> fromStringToLongList(List<String> inList) {
		List<Long> lList = new ArrayList<Long>(inList.size());
		CollectionUtils.collect(inList, new Transformer() {
			public java.lang.Object transform(java.lang.Object input) {
				return new Long((String) input);
			}
		}, lList);
		return lList;
	}
}
