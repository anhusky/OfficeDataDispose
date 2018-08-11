package com.uu.office.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:59
 **/
@NoArgsConstructor
@Data
public class IdName implements Serializable {

    private String id;

    private String name;

    private String role;

    public IdName(String id) {
        this.id = id;
        if ("0".equals(id)) {
            this.name = "准备报考";
        } else if ("1".equals(id)) {
            this.name = "科目一";
        } else if ("2".equals(id)) {
            this.name = "科目二";
        } else if ("3".equals(id)) {
            this.name = "科目三";
        } else if ("4".equals(id)) {
            this.name = "科目四";
        } else if ("5".equals(id)) {
            this.name = "拿证";
        }
    }
}
