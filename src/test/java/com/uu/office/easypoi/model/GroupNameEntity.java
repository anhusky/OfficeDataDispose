package com.uu.office.easypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.util.Date;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-13 上午10:59
 **/
@Data
public class GroupNameEntity implements java.io.Serializable {
    /**
     * id
     */
    private String id;
    // 电话号码(主键)
    @Excel(name = "电话号码", orderNum = "1")
    private String clientPhone;
    // 客户姓名
    @Excel(name = "姓名", orderNum = "2")
    private String clientName;
    // 备注
    @Excel(name = "备注", orderNum = "3")
    private String remark;

    // 生日
    @Excel(name = "出生日期", orderNum = "4", format = "yyyy-MM-dd", width = 20)
    private Date birthday;

    /**
     * 学校信息 只适用于导入，导入慎用
     */
   /* @Excel(name = "学校名称", orderNum = "5", width = 20, enumExportField = "name",
            enumImportMethod = "getName")*/
   /* private IdName schoolInfo;*/

    /**
     * 学校名称
     */
    @Excel(name = "学校名称", orderNum = "5", width = 20)
    private String schoolName;
}
