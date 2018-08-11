package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:25
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OtherCheck implements Serializable {
    /**
     * 学车周期，影响原因？
     */
    private String learnCarCycle;

    /**
     * 竞争对手的员工薪资情况？
     */
    private String dsWageInfo;

}
