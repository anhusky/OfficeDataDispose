package com.uu.office.utils;

import java.util.List;
import java.util.function.Consumer;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-12 下午3:59
 **/
public class Java8Utils {
    /**
     * 简易for循环
     *
     * @param list
     * @param c
     * @param <T>
     */
    public static <T> void forEach(List<T> list, Consumer<T> c) {
        for (T i : list) {
            c.accept(i);
        }
    }


}
