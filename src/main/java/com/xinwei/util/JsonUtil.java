package com.xinwei.util;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;


public class JsonUtil{
	
	// 支持转换的日期格式
	public static final DateFormat[] ACCEPT_DATE_FORMATS = {
			new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"),
			new SimpleDateFormat("yyyy-MM-dd"),
			new SimpleDateFormat("yyyy/MM/dd HH:mm:ss"),
			new SimpleDateFormat("yyyy/MM/dd") };

	/**
	 * 将对象序列化为json字符串,只对有@expose注解的进行序列化
	 * @param obj
	 * @return
	 */
	public static String toJsonWithExclusive(Object obj){
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
		                             .registerTypeAdapter(Date.class, new MyDateSerializer())
		                             .create();
		String json = gson.toJson(obj); 			
		return json;
	}
	
	/**
	 * 将对象序列化为json字符串，不区分注解
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj){
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new MyDateSerializer())
								     .create();
		String json = gson.toJson(obj); 			
		return json;
	}
	
	/**
	 * 将对象序列化为json字符串，不区分注解
	 * @param obj
	 * @return
	 */
	public static String toJson0(Object obj){
		String json = toJson(obj, Date.class, new MyTimestampSerializer());	
		return json;
	}
	
	/**
	 * 将对象序列化为json字符串，不区分注解
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String toJson(Object obj,Class clazz,JsonSerializer serializer){
		Gson gson = new GsonBuilder().registerTypeAdapter(clazz, serializer)
								     .create();
		String json = gson.toJson(obj); 			
		return json;
	}
	
	/**
	 * 将json字符串反序列化为对象,不区分注解
	 * @param jsonStr
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJson(String jsonStr,Type type){
		Gson gson = new GsonBuilder().registerTypeAdapter(Date.class, new MyDateDeserializer())
		                             .create();
		return (T)gson.fromJson(jsonStr, type);
	}
	
	/**
	 * 将json字符串反序列化为对象,区分注解
	 * @param jsonStr
	 * @param classOfT
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T fromJsonWithExclusive(String jsonStr,Type type){
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
		 						     .registerTypeAdapter(Date.class, new MyDateDeserializer())
		                             .create();
		return (T)gson.fromJson(jsonStr, type);
	}
	
	
	@SuppressWarnings("rawtypes")
	public static Map fromJson(String jsonStr){
		
		Type type = new TypeToken<Map>(){}.getType();
		Gson gson = new Gson(); 
		return gson.fromJson(jsonStr, type);
	}
	
	/**
	 * date类型反序列化器
	 * @author jiweibin
	 *
	 */
	private static class MyDateDeserializer implements JsonDeserializer<Date>{
		 
		
		public Date deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext context) throws JsonParseException {

			// 遍历日期支持格式，进行转换
			for (DateFormat format : ACCEPT_DATE_FORMATS) {
                try {
                    return format.parse(json.getAsString());
                } catch (Exception e) {
                      continue;
                }
           }
           return null;
           
          /* SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				return sdf.parse(json.getAsString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return null;*/
	
		}
		
	}
	/**
	 * timestamp类型反序列化器
	 * @author jiweibin
	 *
	 */
	private static class MyTimestampSerializer implements JsonSerializer<Date>{
	
		public JsonElement serialize(Date src, Type typeOfSrc,
				JsonSerializationContext context) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return new JsonPrimitive(sdf.format(src));
		}
		
	}
	
	/**
	 * date类型序列化器
	 * @author jiweibin
	 *
	 */
	private static class MyDateSerializer implements JsonSerializer<Date>{

		public JsonElement serialize(Date src, Type typeOfSrc,
				JsonSerializationContext context) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return new JsonPrimitive(sdf.format(src));
		}
		
	}
	
	
	public static void main(String[] args){
		String json = "{'name': 'helloworlda','array':[{'a':'111','b':'222','c':'333'},{'a':'999'}],'address':'111','people':{'name':'happ','sex':'girl'}}";
		JsonUtil.fromJson(json, JsonUtil.class);
	}
}
