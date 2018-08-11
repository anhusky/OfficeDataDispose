package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:18
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ClassType implements Serializable {
    /**
     * 班型名称
     */
    private String name;

    /**
     * 班型价格
     */
    private String price;

    /**
     * 提供的服务
     */
    private String server;

}
