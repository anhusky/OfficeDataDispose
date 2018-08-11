package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：直接成本及学时要求
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:17
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CostAndHour implements Serializable {
    /**
     * 规费标准
     */
    private Integer standardFee;

    /**
     * 科目一补考费
     */
    private Integer makeUpFeeForSubjectOne;

    /**
     * 科目二补考费
     */
    private Integer makeUpFeeForSubjectTwo;

    /**
     * 科目三补考费
     */
    private Integer makeUpFeeForSubjectThree;

    /**
     * 科目三安全文明常识补考费
     */
    private Integer makeUpFeeForSubjectFour;

    /**
     * 科目一教练工资
     */
    private String coachFeeForSubjectOne;

    /**
     * 科目二教练工资
     */
    private String coachFeeForSubjectTwo;

    /**
     * 科目三教练工资
     */
    private String coachFeeForSubjectThree;

    /**
     * 科目三安全文明常识教练工资
     */
    private String coachFeeForSubjectFour;

    /**
     * 科目一学时要求
     */
    private Integer hoursForSubjectOne;

    /**
     * 科目二学时要求
     */
    private Integer hoursForSubjectTwo;

    /**
     * 科目三学时要求
     */
    private Integer hoursForSubjectThree;

    /**
     * 科目三安全文明常识学时要求
     */
    private Integer hoursForSubjectFour;

    /**
     * 其它（包含地域性费用，如税金、安全文明教育、考前模拟等等）
     */
    private List<String> others;

}
