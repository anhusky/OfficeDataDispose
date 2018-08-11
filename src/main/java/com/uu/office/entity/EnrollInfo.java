package com.uu.office.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 描述：招生方式及部署、促销活动及方式
 *
 * @author liupenghao
 * @create 2018-07-16 上午11:21
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EnrollInfo implements Serializable {

    /**
     * 是否有直营的招生点？几个？位置考评？
     * （所在位置是否明显/招牌是否完整清洁/内部环境是否干净整洁/人员是否专业和态度友善）
     */
    private String directOutlets;

    /**
     * 是否有代理招生点？几个？位置考评？
     * （所在位置是否明显/招牌是否完整清洁/内部环境是否干净整洁/人员是否专业和态度友善）
     */
    private String proxyOutlets;

    /**
     * 是否有分校？几家？分布位置？招生情况？
     */
    private String branchSchools;

    /**
     * 是否有自己的网站和公众号等，运营情况？
     */
    private String siteAndGzh;

    /**
     * 是否有独立的招生团队并定期展开招生活动？
     */
    private String enrollTeam;

    /**
     * 驾校日常的品宣及广告和宣传时间
     */
    private String promotion;

    /**
     * 驾校举办过哪些促销活动？具体内容和宣传方式？
     */
    private String activity;

    /**
     * 最近一次的促销活动在什么时间？具体内容和宣传方式？
     */
    private String lastActivity;

}
