package com.uu.office.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 描述：word 模板替换时 需要忽略的 属性添加此注解
 *
 * @author liupenghao
 * @create 2018-07-13 下午4:24
 **/
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = ElementType.FIELD)
public @interface IgnoreFiled {
    int value() default 0;
}
