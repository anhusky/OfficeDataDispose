package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-19 下午3:20
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImportWzInfoBase {
    private String order;
    private String name;
    private String unit;
    private Integer count;
    private List<String> peoples;
    private String remark;
}
