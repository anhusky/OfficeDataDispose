package com.uu.office.utils.readExcel;

import com.uu.office.entity.ImportWzInfo;
import com.uu.office.entity.ImportWzInfoBase;
import com.uu.office.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-19 下午5:33
 **/
public class ImportExcelDateServiceImpl {

    /**
     * 读取excel 标题
     *
     * @param hw
     * @param shellIndex
     * @return
     */
    public static List<String> readTitles(HSSFWorkbook hw, Integer shellIndex) {
        HSSFSheet shell = hw.getSheetAt(shellIndex);
        HSSFRow row = shell.getRow(0);
        int columns = row.getPhysicalNumberOfCells();

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < columns; i++) {
            list.add(row.getCell(i).getStringCellValue());
        }
        return list;
    }


    /**
     * 读取excel数据封装对对象
     *
     * @param wk
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static List<User> readExcelContentToUser(HSSFWorkbook wk, int sheetIndex, int rowIndex) throws IOException {
        HSSFSheet sheet = wk.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        List<User> list = new ArrayList<User>();

        for (int i = 1; i < rowNum + 1; i++) {
            HSSFRow row = sheet.getRow(i);
            if (ImportExcelDataUtils.isBlankRow(row)) continue;
            User user = new User();

            user.setName(ImportExcelDataUtils.cell2Str(row.getCell(0)));
            user.setAge(Integer.parseInt(ImportExcelDataUtils.cell2Str(row.getCell(1))));
            user.setSex(Integer.parseInt(ImportExcelDataUtils.cell2Str(row.getCell(2))));
            user.setBirthday(ImportExcelDataUtils.cell2Str(row.getCell(3)));
            list.add(user);


            // 设置格式
            HSSFCell cell = row.getCell(1);
            HSSFCellStyle cellStyle = cell.getCellStyle();
            cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);

            HSSFFont font = wk.createFont();
            font.setColor(HSSFColor.RED.index);//HSSFColor.VIOLET.index //字体颜色
            font.setFontHeightInPoints((short)12);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
            //把字体应用到当前的样式
            cellStyle.setFont(font);

            HSSFCell cell2 = row.getCell(2);
            HSSFCellStyle cellStyle2 = cell2.getCellStyle();
            cellStyle2.setFillForegroundColor(HSSFColor.BLUE.index);

            HSSFFont font2 = wk.createFont();
            font2.setColor(HSSFColor.YELLOW.index);//HSSFColor.VIOLET.index //字体颜色
            font2.setFontHeightInPoints((short)12);
            font2.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);         //字体增粗
            //把字体应用到当前的样式
            cellStyle2.setFont(font2);

            //System.out.println("------");

        }


        wk.write(new FileOutputStream("/resource/outFile.xls"));

        return list;
    }


    /**
     * 读取导入的物资信息
     *
     * @param wk
     * @return
     */
    public static void readExcelContentToWzInfo(HSSFWorkbook wk) throws ParseException {
        int sheetNum = wk.getNumberOfSheets();
        List<ImportWzInfo> importWzInfos = new ArrayList<>();
        for (int i = 0; i < sheetNum; i++) {

            ImportWzInfo info = new ImportWzInfo();
            //遍历，获取每一个sheet中的数据
            HSSFSheet sheet = wk.getSheetAt(i);
            String sheetName = sheet.getSheetName();
            System.out.println("sheetName = " + sheetName);
            info.setType(sheetName);

            // 获取首行-----即标题
            HSSFRow row = sheet.getRow(0);
            String title = row.getCell(0).getStringCellValue();
            info.setTitle(title);
            int start = title.lastIndexOf("(");
            int end = title.lastIndexOf(")");
            if (start + 1 == 0) {
                start = title.lastIndexOf("（");
            }
            if (end + 1 == 0) {
                end = title.lastIndexOf("）");
            }
            String dateStr = title.substring(start + 1, end);
            System.out.println(dateStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            Date date = sdf.parse(dateStr);
            info.setDate(date);

            // 获取行数
            int rowNum = sheet.getLastRowNum();

            List<ImportWzInfoBase> data = new ArrayList<>();
            // 跳过 表的标题  遍历表中的数据
            for (int j = 2; j <= rowNum; j++) {
                HSSFRow dateRow = sheet.getRow(j);
                String name = dateRow.getCell(1).getStringCellValue();
                // 物品名称 单位  判断数量 判断人 均不能为空
                if (name == null || name.equals("")) {
                    continue;
                }

                String unit = dateRow.getCell(2).getStringCellValue();
                Integer count = Integer.parseInt(ImportExcelDataUtils.cell2Str(dateRow.getCell(3)));
                String peoples = dateRow.getCell(4).getStringCellValue();
                String remark = dateRow.getCell(5).getStringCellValue();

                ImportWzInfoBase infoBase = new ImportWzInfoBase();
                if (peoples != null && !peoples.equals("")) {
                    List<String> peopleList = Arrays.asList(peoples.split("、"));
                    infoBase.setPeoples(peopleList);
                }
                infoBase.setName(name);
                infoBase.setUnit(unit);
                infoBase.setCount(count);
                infoBase.setRemark(remark);
                data.add(infoBase);
            }
            info.setData(data);
            importWzInfos.add(info);
        }
        System.out.println("-----------");
    }
}
