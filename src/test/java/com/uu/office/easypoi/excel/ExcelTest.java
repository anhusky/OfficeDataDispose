package com.uu.office.easypoi.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.uu.office.easypoi.model.CompanyHasImgModel;
import com.uu.office.easypoi.model.GroupNameEntity;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-08-13 上午11:00
 **/
public class ExcelTest {
    /**
     * excel 导出测试-- 单个
     *
     * @throws Exception
     */
    @Test
    public void export() throws Exception {

        List<GroupNameEntity> list = new ArrayList<>();//initData();
        ExportParams params = new ExportParams("GroupName测试", "测试", ExcelType.XSSF);
        Workbook workbook = ExcelExportUtil.exportExcel(params, GroupNameEntity.class, list);
        String file = this.getClass().getResource("").getFile();
        File savefile = new File(file);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file + "/groupName.xlsx");
        workbook.write(fos);
        fos.close();
    }


    /**
     * excel 导出测试-- 多个
     *
     * @throws Exception
     */
    @Test
    public void exportMuti() throws Exception {

        //--------------------------------------------数据模拟start----------------------------------------------
        List<Map<String, Object>> mapList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            List<GroupNameEntity> dataList = initData();
            ExportParams params = new ExportParams("GroupName测试", "测试" + i, ExcelType.XSSF);
            HashMap<String, Object> map = new HashMap<>();
            map.put("title", params);
            map.put("entity", GroupNameEntity.class);
            map.put("data", dataList);
            mapList.add(map);
        }

        //-------------------------------------------- 数据模拟    start ----------------------------------------------
        Workbook workbook = ExcelExportUtil.exportExcel(mapList, ExcelType.XSSF);

        String file = this.getClass().getResource("").getFile();
        File savefile = new File(file);
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(file + "/groupName_muti.xlsx");
        workbook.write(fos);
        fos.close();
    }

    /**
     * 读取excel 单个
     */
    @Test
    public void readExcel() throws Exception {
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        InputStream is = this.getClass().getResourceAsStream("/read/groupName.xlsx");

        List<GroupNameEntity> list = ExcelImportUtil.importExcel(is, GroupNameEntity.class, params);
        System.out.println(list.size());
    }

    /**
     * 读取excel 多个
     * StartSheetIndex 从0开始
     */
    @Test
    public void readExcelMuti() throws Exception {
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        params.setStartSheetIndex(0);
        InputStream is = this.getClass().getResourceAsStream("/read/groupName_muti.xlsx");

        List<GroupNameEntity> list = ExcelImportUtil.importExcel(is, GroupNameEntity.class, params);

        Assert.assertTrue(list.isEmpty());
    }


    /**
     * 导出带图片的表格
     *
     * @throws Exception
     */
    @Test
    public void exportCompanyImg() throws Exception {

        List<CompanyHasImgModel> list = new ArrayList<CompanyHasImgModel>();
        list.add(new CompanyHasImgModel("百度", "http://i1.umei.cc/uploads/tu/201808/9999/f74db9c2fc.jpg", "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new CompanyHasImgModel("阿里巴巴", "imgs/company/ali.png", "北京市海淀区西北旺东路10号院百度科技园1号楼"));
        list.add(new CompanyHasImgModel("Lemur", "imgs/company/lemur.png", "亚马逊热带雨林"));
        list.add(new CompanyHasImgModel("一众", "imgs/company/one.png", "山东济宁俺家"));

        File savefile = new File("/resource/output/excel/");
        if (!savefile.exists()) {
            savefile.mkdirs();
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), CompanyHasImgModel.class, list);
        FileOutputStream fos = new FileOutputStream("/resource/output/excel/ExcelExportHasImgTest.exportCompanyImg.xls");
        workbook.write(fos);
        fos.close();
    }

    //--------------------------------------------初始化数据----------------------------------------------
    private List<GroupNameEntity> initData() {
        List<GroupNameEntity> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            GroupNameEntity client = new GroupNameEntity();
            client.setBirthday(new Date());
            client.setClientName("小明" + i);
            client.setClientPhone("18797" + i);
            client.setId("1" + i);
            client.setRemark("测试" + i);

            // IdName 新增
            client.setSchoolName("宇宙飞船驾校" + i);

            list.add(client);
        }
        return list;
    }
}
