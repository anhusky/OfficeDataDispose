package com.uu.office.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-12 下午3:02
 **/
@Data
public class User implements Serializable {
    private Integer age;
    private Integer sex;
    private String birthday;
    private String name;
}


