package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:16
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Installation implements Serializable {
    /**
     * B1 车数量
     */
    private Integer carB1Count;

    /**
     * B2 车数量
     */
    private Integer carB2Count;

    /**
     * A1 车数量
     */
    private Integer carA1Count;

    /**
     * A2 车数量
     */
    private Integer carA2Count;

    /**
     * C1 车数量
     */
    private Integer carC1Count;

    /**
     * C2 车数量
     */
    private Integer carC2Count;

    /**
     * C5 车数量
     */
    private Integer carC5Count;

    /**
     * 车辆折旧年限
     */
    private String depreciation;

    /**
     * 场地的设计与规划、安全隐患
     */
    private String securityRisks;

    /**
     * 驾校环境的清洁程度：门前环境
     */
    private String envDoor;

    /**
     * 驾校环境的清洁程度：教练场地
     */
    private String envCoachGround;

    /**
     * 驾校环境的清洁程度：业务大厅
     */
    private String envBusinessHall;

    /**
     * 驾校环境的清洁程度：考试场地
     */
    private String envExamGround;

    /**
     * 驾校环境的清洁程度：食堂
     */
    private String envDiningHall;

    /**
     * 驾校环境的清洁程度：宿舍（学员）
     */
    private String envStudentRoom;

    /**
     * 驾校环境的清洁程度：宿舍（员工）
     */
    private String envEmployeeRoom;

}
