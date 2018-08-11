package com.uu.office.entity;

import com.uu.office.annotation.SonObjectFiled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 描述：学车体检
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:12
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Experience implements Serializable {
    /**
     * 科目一合格率
     */
    private Integer passRateForSubjectOne;

    /**
     * 科目二合格率
     */
    private Integer passRateForSubjectTwo;

    /**
     * 科目三合格率
     */
    private Integer passRateForSubjectThree;

    /**
     * 科目三安全文明常识合格率
     */
    private Integer passRateForSubjectFour;

    /**
     * 科目一初考天数
     */
    private Integer firstExamDayForSubjectOne;

    /**
     * 科目二初考天数
     */
    private Integer firstExamDayForSubjectTwo;

    /**
     * 科目三初考天数
     */
    private Integer firstExamDayForSubjectThree;

    /**
     * 科目三安全文明常识初考天数
     */
    private Integer firstExamDayForSubjectFour;

    /**
     * 科目一补考天数
     */
    private Integer makeUpExamDayForSubjectOne;

    /**
     * 科目二补考天数
     */
    private Integer makeUpExamDayForSubjectTwo;

    /**
     * 科目三补考天数
     */
    private Integer makeUpExamDayForSubjectThree;

    /**
     * 科目三安全文明常识补考天数
     */
    private Integer makeUpExamDayForSubjectFour;

    /**
     * 学员积压情况
     */
    private String studentBacklog;

    /**
     * 主动性、友善度、耐心、行为规范
     */
    private String serverAction;

    /**
     * 环境设施
     */
    @SonObjectFiled
    private Installation installation;

    /**
     * 直接成本及学时要求
     */
    @SonObjectFiled
    private CostAndHour costAndHour;

    /**
     * 班型、价格及提供的服务
     */
    private List<ClassType> classTypeList;

    /**
     * 竞争对手的班型、价格及提供的服务
     */
    private List<ClassType> dsClassTypeList;

}
