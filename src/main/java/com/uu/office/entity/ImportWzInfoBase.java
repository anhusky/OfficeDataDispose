package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class ImportWzInfoBase implements Serializable {

    /**
     * 物品名称
     */
    private String name;
    /**
     * 单位
     */
    private String unit;
    /**
     * 盘点数量
     */
    private Integer count;
    /**
     * 盘点人
     */
    private List<String> peoples;
    /**
     * 备注
     */
    private String remark;
}
