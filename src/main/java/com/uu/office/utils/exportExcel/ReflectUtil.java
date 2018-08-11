package com.uu.office.utils.exportExcel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ReflectUtil {
	/**
	 * 获取字段的值
	 * @param o类对象
	 * @param fieldName字段名称
	 * @return
	 * @throws Exception
	 */
	public static Object getValueByFieldName(Object o,String fieldName)throws Exception {
		return getValueByFieldName(o.getClass(),o,fieldName);
	}
	/**
	 * 通过字段名称获取字段值(反射）
	 * @param classType 类名称
	 * @param o 类对象
	 * @param fieldName 字段名称
	 * @return 字段值
	 * @throws Exception
	 */
	public static Object getValueByFieldName(Class classType,Object o,String fieldName) throws Exception {
		if(fieldName.startsWith("#")){//格式为#empList.id
			String fieldList = fieldName.substring(1,fieldName.indexOf("."));
			String subField = fieldName.substring(fieldName.indexOf(".")+1);
			Object l = getValueByGetter(classType, o, "get"+upperFirstLetter(fieldList));
			List list = new ArrayList();
			if(l instanceof List){
				for (Object object : (List)l) {
					list.add(getValueByFieldName(object.getClass(),object,subField));
				}
			}else
				throw new RuntimeException("["+fieldName+"]参数解析失败，非集合类型数据");
			return list;
		}else if(fieldName.contains(".")){
			String[] fieldNames = fieldName.split("\\.");
			String getName="get"+ upperFirstLetter(fieldNames[fieldNames.length-1]);
			for (int i = 1; i < fieldNames.length; i++) {
				String getSubNamePre = "get"+upperFirstLetter(fieldNames[i-1]);
				String getSubName="get"+upperFirstLetter(fieldNames[i]);
				o = getValueByGetter(classType, o, getSubNamePre,getSubName);
			}
			return getValueByGetter(classType, o, getName);
		}else{
			String getName="get"+upperFirstLetter(fieldName);//获得相应属性的getXXX方法名称 
			return getValueByGetter(classType, o, getName);
		}
	}
	
	/**
	 * 通过getter方法反射获取字段值
	 * @param classType 类名称
	 * @param o 类对象
	 * @param String 类中属性的get方法名称
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object getValueByGetter(Class classType,Object o,String getMethodName) 
		throws NoSuchMethodException,
		IllegalAccessException, InvocationTargetException {
		Method getMethod=classType.getMethod(getMethodName, new Class[]{}); //获取相应的方法    
		Object value=getMethod.invoke(o);//调用源对象的getXXX（）方法
		return value;
	}
	/**
	 * 通过getter方法反射获取对象对应的字段值
	 * @param classType
	 * @param o
	 * @param getMethodName
	 * @param getSubMethodName
	 * @return
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Object getValueByGetter(Class classType,Object o,String getMethodName,String getSubMethodName) 
		throws NoSuchMethodException,
		IllegalAccessException, InvocationTargetException {
		Method getMethod=classType.getMethod(getMethodName, new Class[]{}); //获取相应的方法    
		Object value=getMethod.invoke(o);//调用源对象的getXXX（）方法
		return value;
	}
	/**
	 * 将首字母转大写
	 * @param str
	 * @return
	 */
	public static String upperFirstLetter(String str){
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}
}
