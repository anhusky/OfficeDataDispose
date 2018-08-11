package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:24
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PromoteChance implements Serializable {
    /**
     * 当地有哪些政策制约着驾校的发展？（影响到招生和考证）
     */
    private String restrictInfo;

    /**
     * 当地有哪些政策有利于着驾校的发展？（影响到招生和考证）
     */
    private String advantageInfo;

    /**
     * 驾校所在位置，未来3年内，政府规划等，是否会影响到变化？
     */
    private String threeYearInfo;

    /**
     * 其他
     */
    private String other;

}
