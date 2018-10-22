package com.uu.office.easypoi.model;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公司信息 Created by JueYue on 2017/8/25.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyHasImgModel {


    @Excel(name = "公司名称")
    private String companyName;
    @Excel(name = "公司LOGO", type = 2 ,width = 40 , height = 20,imageType = 1)
    private String companyLogo;
    @Excel(name = "公司地址" ,width = 60)
    private String companyAddr;


}
