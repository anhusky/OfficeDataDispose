package com.uu.office.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.uu.office.annotation.IgnoreFiled;
import com.uu.office.annotation.SonObjectFiled;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 描述：word 模板转换工具类
 *
 * @author liupenghao
 * @create 2018-07-16 下午2:23
 **/
public class WordDataUtils {
    /**
     * 获取对象中的属性，并把它封装在map中
     *
     * @param map
     * @param object        对象
     * @param isInner       是否是内层递归调用
     * @param propertyNames 记录层级  对象 名称 ，需要递归调用时传递
     * @param innerIndex    遍历list时候记录
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public static Map<String, Object> getParamMap(Map map, Object object, boolean isInner, List<String> propertyNames, Integer innerIndex)
            throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        String key = null;  // map 中的 键 临时变量
        Field[] fields = object.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组

        // 遍历所有属性
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String propertyName = fields[i].getName();

            // 若此属性是this$0 或者   有IgnoreFiled注解，则遍历下一个
            if (propertyName.equals("this$0")
                    || propertyName.equals("serialVersionUID")
                    || field.getAnnotation(IgnoreFiled.class) != null) {
                continue;
            }

            String name = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1); // 将属性的首字符大写，方便构造get，set方法
            String type = fields[i].getGenericType().toString(); // 获取属性的类型
            System.out.println("propertyName----" + propertyName + "\r\n type:" + type);

            Method m = object.getClass().getMethod("get" + name);
            Object value = m.invoke(object); // 调用getter方法获取属性值
            if (value == null) {
                switch (type) {// 默认值设置
                    case "class java.lang.Integer":
                        value = 1314;
                        break;
                    case "class java.lang.String":
                        value = "（-_-）";
                        break;
                    default:
                        continue;
                }
            }

            // 若此属性 有SonObjectFiled 属性，递归遍历
            if (field.getAnnotation(SonObjectFiled.class) != null) {
                List<String> copyPropertys = new ArrayList<>();
                if (propertyNames != null) {
                    copyPropertys.addAll(propertyNames);
                }
                copyPropertys.add(propertyName);
                getParamMap(map, value, true, copyPropertys, -1);
                continue;
            }

            switch (type) {
                case "class java.util.Date":
                    JSONField jf = field.getAnnotation(JSONField.class);
                    String format = jf.format();
                    if (format != null && !format.equals("")) {
                        SimpleDateFormat sdf = new SimpleDateFormat(format);
                        String date = sdf.format(value);
                        value = date;
                    }
                    break;
            }

            // 若此属性是list集合 ，递归遍历
            if (type.contains("List")) {
                System.out.println("---------------");
                System.out.println(propertyNames.toString());
                System.out.println(propertyName + "----" + type);

                // 是否是String 类型的list
                boolean isStringList = false;
                if (type.equals("java.util.List<java.lang.String>")) {
                    isStringList = true;
                }
                Method method = value.getClass().getMethod("size");
                int size = (Integer) method.invoke(value);
                System.out.println("size =" + size);
                if (size > 0) {
                    for (int k = 0; k < size; k++) {
                        Method m2 = value.getClass().getMethod("get", int.class);
                        Object obj = m2.invoke(value, k);
                        List<String> copyPropertys = new ArrayList<>();
                        if (propertyNames != null) {
                            copyPropertys.addAll(propertyNames);
                        }
                        copyPropertys.add(propertyName);
                        if (isStringList) {
                            saveDataToMap(map, true, copyPropertys, k + 1, "*", obj);
                        } else {
                            getParamMap(map, obj, true, copyPropertys, k + 1);
                        }

                    }
                }
                continue;
            }
            saveDataToMap(map, isInner, propertyNames, innerIndex, propertyName, value);
        }
        return map;
    }

    /**
     * 保存数据到Map中
     */
    public static void saveDataToMap(Map map, boolean isInner, List<String> propertyNames, Integer innerIndex,
                                     String propertyName, Object value) {
        String key = "";
        if (isInner) {
            // 中间拼接对象  形似  object.object1.object2
            String temp = "";
            for (String s : propertyNames) {
                temp += s + ".";
            }
            // 是子属性（对象）中的值
            if (innerIndex + 1 == 0) {
                key = "${" + temp + propertyName + "}";
                // 是子属性（List）中的值
            } else {
                key = "${" + temp + innerIndex + "." + propertyName + "}";
            }
        } else {
            key = "${" + propertyName + "}";
        }
        //System.out.println(key + "。。。。" + value);

        map.put(key, value.toString());
    }


}
