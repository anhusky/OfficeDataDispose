package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 描述：商圈分析
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:22
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BusinessCircle {
    /**
     * 所在地区的人口数量
     */
    private Integer totalPeopleCount;

    /**
     * 本地人口数量
     */
    private Integer localPeopleCount;

    /**
     * 年出证数量
     */
    private Integer outCertForYear;

    /**
     * 本驾校出证占比
     */
    private Integer schoolRate;

    /**
     * 驾校招生情况 去年招生量
     */
    private Integer studentCount1;

    /**
     * 驾校招生情况 前年招生量
     */
    private Integer studentCount2;

    /**
     * 去年出证数量
     */
    private Integer outCertCount1;

    /**
     * 前年出证数量
     */
    private Integer outCertCount2;

    /**
     * 商圈地图图片地址
     */
    private String bcPictureUrl;


    /**
     * 中专数量
     */
    private Integer middleSchoolCount;
    /**
     * 中专名称
     */
    private List<String> middleSchoolNames;

    /**
     * 中专名称 ,方便获取
     */
    private String middleSchoolStr;

    /**
     * 高中数量
     */
    private Integer highSchoolCount;
    /**
     * 高中名称
     */
    private List<String> highSchoolNames;

    /**
     * 高中名称 ,方便获取
     */
    private String highSchoolStr;

    /**
     * 大学名称
     */
    private List<String> universityNames;
    /**
     * 大学数量
     */
    private Integer universityCount;

    /**
     * 大学名称 ,方便获取
     */
    private String universityStr;

    /**
     * 工厂数量
     */
    private Integer factoryCount;

    /**
     * 工厂名称 说明
     */
    private List<String> factoryNames;

    /**
     * 工厂名称 方便获取
     */
    private String factoryStr;

    /**
     * 大型的企事业单位 数量
     */
    private Integer bigPublicInstitutionCount;

    /**
     * 大型的企事业单位 说明
     */
    private List<String> bigPublicInstitutionNames;

    /**
     * 大型的企事业单位 方便获取
     */
    private String bigPublicInstitutionStr;
    /**
     * 其他说明
     */
    private String other;

    /**
     * 附近村庄数量
     */
    private Integer villageCount;

    /**
     * 村庄招生情况
     */
    private String villageEnroll;

}
