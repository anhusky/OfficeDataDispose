package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：社区关系
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:23
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Community implements Serializable {
    /**
     * 所在区域正规驾校数量
     */
    private Integer totalSchoolCount;

    /**
     *  所在区域非正规驾校数量
     */
    private Integer totalUnSchoolCount;

    /**
     * 同等规模和硬件设施的驾校数量
     */
    private Integer sameSchoolCount;

    /**
     * 驾校的知名度排名
     */
    private String schoolLevel;

    /**
     * （以顾客身份调查）调查信息记录
     */
    private String checkLog;

}
