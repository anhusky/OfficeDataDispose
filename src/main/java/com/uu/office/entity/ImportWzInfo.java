package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-19 下午4:09
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportWzInfo implements Serializable {
    /**
     * 标题
     */
    private String title;
    /**
     * 日期
     */
    private Date date;
    /**
     * 类型
     */
    private String type;
    /**
     * 数据
     */
    private List<ImportWzInfoBase> data;
}
