package com.uu.office.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <pre>
 *
 *      描述：word 模板替换时，是个对象的子属性
 *              加上此注解表示此子属性需要  递归遍历
 * </pre>
 *
 * @author liupenghao
 * @create 2018-07-17 下午2:21
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface SonObjectFiled {
    int value() default 0;
}
