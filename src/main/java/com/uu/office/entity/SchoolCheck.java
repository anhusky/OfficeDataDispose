package com.uu.office.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.uu.office.annotation.SonObjectFiled;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SchoolCheck implements Serializable {
    // @Id
    private String id;

    /**
     * 驾校名称
     */
    private String name;

    /**
     * 经营性质 民营/股份/有限责任
     */
    private String property;

    /**
     * 开业时间
     */
    private String openTime;

    /**
     * 驾校类型 一类驾校/二类驾校/三类驾校
     */
    private String type;

    /**
     * 校长姓名
     */
    private String headMaster;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 驾校地址
     */
    private String address;

    /**
     * 1、学车体验
     */
    @SonObjectFiled
    private Experience experience;

    /**
     * 2、可见度、方便性
     */
    @SonObjectFiled
    private Convenience convenience;

    /**
     * 3、招生方式及部署、促销活动及方式
     */
    @SonObjectFiled
    private EnrollInfo enrollInfo;

    /**
     * 4、商圈分析
     */
    @SonObjectFiled
    private BusinessCircle businessCircle;

    /**
     * 5、社区关系
     */
    @SonObjectFiled
    private Community community;

    /**
     * 6、驾校营业额提升的机会
     */
    @SonObjectFiled
    private PromoteChance promoteChance;

    /**
     * 7、其他调研
     */
    @SonObjectFiled
    private OtherCheck otherCheck;


    /**
     * 填写人
     */
    private IdName entryBy;

    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date updateTime;

}