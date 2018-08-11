package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：可见度、方便性
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:19
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Convenience implements Serializable {
    /**
     * 驾校所在位置位于所在区域的
     */
    private String areaType;

    /**
     * 驾校是否有明显的位置指引牌，方便顾客查找和到达
     */
    private String arrival;

    /**
     * 驾校是否有方便到达的公交车 1 有 2 无
     */
    private Integer hasBus;

    /**
     * 共几条路线
     */
    private Integer busRoadCount;

    /**
     * 公交车一共多少站
     */
    private Integer busStationCount;

    /**
     * 驾校所在的位置是发车的第几站
     */
    private String schoolInStartRoad;

    /**
     * 终点的第几站
     */
    private String schoolInEndRoad;

    /**
     * 沿途是否有广告宣传
     */
    private Integer hasAdInRoad;

    /**
     * 驾校是否提供班车服务 1 有 2 无
     */
    private Integer hasShuttle;

    /**
     * 学员的到达方式及时间
     */
    private String arrivaType;

}
