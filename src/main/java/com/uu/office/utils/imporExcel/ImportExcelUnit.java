package com.uu.office.utils.imporExcel;

import com.uu.office.entity.User;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ImportExcelUnit {
    private static POIFSFileSystem fs;
    private static HSSFWorkbook wb;
    private static HSSFSheet sheet;
    private static HSSFRow row;

    private static XSSFWorkbook xwb;
    private static XSSFSheet xsheet;
    private static XSSFRow xrow;

    //读取文件流
    public static HSSFWorkbook readInputStreanAndToWorkbook(InputStream in) throws IOException {
        fs = new POIFSFileSystem(in);
        wb = new HSSFWorkbook(fs);
        return wb;
    }


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
    public static List<User> readExcelContentToUser(HSSFWorkbook wk, int sheetIndex, int rowIndex) {
        sheet = wk.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        List<User> list = new ArrayList<User>();

        for (int i = 1; i < rowNum + 1; i++) {
            ImportExcelUnit.row = sheet.getRow(i);
            if (isBlankRow(ImportExcelUnit.row)) continue;
            User user = new User();
            user.setName(cell2Str(ImportExcelUnit.row.getCell(0)));
            user.setAge(Integer.parseInt(cell2Str(ImportExcelUnit.row.getCell(1))));
            user.setSex(Integer.parseInt(cell2Str(ImportExcelUnit.row.getCell(2))));
            user.setBirthday(cell2Str(ImportExcelUnit.row.getCell(3)));

            list.add(user);
        }
        return list;
    }


    /**
     * 读取excel数据封装对对象
     *
     * @param wb
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static String checkAttendances(HSSFWorkbook wk, int sheetIndex, int rowIndex) {
        String errors = "";
        sheet = wk.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        for (int i = rowIndex; i < rowNum + 1; i++) {
            row = sheet.getRow(i);
            if (isBlankRow(row)) continue;
            String empCode = cell2Str(row.getCell(0));
            String deptCode = cell2Str(row.getCell(2));
            if (empCode == null || "".equals(empCode)) {
                errors += "第" + i + "行员工编号为空;\n";
            }
            if (deptCode == null || "".equals(deptCode)) {
                errors += "第" + i + "行部门编号为空;\n";
            }
        }
        if (!"".equals(errors))
            errors += "无法导入！";
        return errors;
    }

    /**
     * 读取excel数据封装对对象
     *
     * @param wb
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static String checkPayroll(HSSFWorkbook wk, int sheetIndex, int rowIndex) {
        String errors = "";
        sheet = wk.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();

        String year = "";
        String month = "";
        HSSFRow row1 = sheet.getRow(0);
        String text = cell2Str(row1.getCell(0));
        if (text != null && !"".equals(text)) {
            year = text.trim().substring(0, text.trim().indexOf("年"));
            month = text.trim().substring(text.trim().indexOf("年") + 1, text.trim().indexOf("月"));
        }

        if (year == null || "".equals(year)) {
            errors += "工资单没有年份信息\n";
        }
        if (month == null || "".equals(month)) {
            errors += "工资单没有月份信息\n";
        }

        if (!"".equals(errors))
            errors += "无法导入！";
        return errors;
    }


    /**
     * 读取excel数据封装对对象
     *
     * @param wb
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static String checkAttendance2007(XSSFWorkbook xwk, int sheetIndex, int rowIndex) {
        String errors = "";
        xsheet = xwk.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = xsheet.getLastRowNum();
        for (int i = rowIndex; i < rowNum + 1; i++) {
            xrow = xsheet.getRow(i);
            if (isBlankRow(xrow)) continue;
            String empCode = cell2Str(xrow.getCell(0));
            String deptCode = cell2Str(xrow.getCell(2));
            if (empCode == null || "".equals(empCode)) {
                errors += "第" + i + "行员工编号为空;\n";
            }
            if (deptCode == null || "".equals(deptCode)) {
                errors += "第" + i + "行部门编号为空;\n";
            }
        }
        if (!"".equals(errors))
            errors += "无法导入！";
        return errors;
    }

    /**
     * 读取excel数据封装对对象
     *
     * @param wb
     * @param sheetIndex
     * @param rowIndex
     * @return
     */
    public static String checkPayroll2007(XSSFWorkbook xwk, int sheetIndex, int rowIndex) {
        String errors = "";
        xsheet = xwk.getSheetAt(sheetIndex);
        // 得到总行数
        int rowNum = xsheet.getLastRowNum();

        String year = "";
        String month = "";
        XSSFRow xrow1 = xsheet.getRow(0);
        String text = cell2Str(xrow1.getCell(0));
        if (text != null && !"".equals(text)) {
            year = text.trim().substring(0, text.trim().indexOf("年"));
            month = text.trim().substring(text.trim().indexOf("年") + 1, text.trim().indexOf("月"));
        }
        if (year == null || "".equals(year)) {
            errors += "工资单没有年份信息\n";
        }
        if (month == null || "".equals(month)) {
            errors += "工资单没有月份信息\n";
        }

//        for (int i = rowIndex; i < rowNum+1; i++) {
//            xrow = xsheet.getRow(i);
//            if(isBlankRow(xrow))continue;
////            String empName=cell2Str(xrow.getCell(1));
////            if(empName==null||"".equals(empName)){
////            	errors+="第"+i+"行员工姓名为空;\n";
////            }
//        }
        if (!"".equals(errors))
            errors += "无法导入！";
        return errors;
    }


    /**
     * 判断行是否为空
     *
     * @param row
     * @return true为空；false不为空
     */
    public static boolean isBlankRow(Row row) {
        if (row == null) return true;
        boolean result = true;
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i, Row.RETURN_BLANK_AS_NULL);
            String value = "";
            if (cell != null) {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        value = cell.getStringCellValue();
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                        value = String.valueOf((int) cell.getNumericCellValue());
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        value = String.valueOf(cell.getBooleanCellValue());
                        break;
                    case Cell.CELL_TYPE_FORMULA:
                        value = String.valueOf(cell.getNumericCellValue());
                    default:
                        break;
                }
                if (!"".equals(value.trim())) {
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * @param cell
     * @return
     */
    public static String cell2Str(Cell cell) {
        if (cell == null) return "";
        String value = "";
        if (cell != null) {
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                    value = cell.getStringCellValue();
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        value = sdf.format(cell.getDateCellValue());
                    } else {
                        value = String.valueOf((int) cell.getNumericCellValue());
                    }
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    value = String.valueOf(cell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    try {
                        value = String.valueOf(cell.getStringCellValue());
                    } catch (IllegalStateException e) {
                        value = String.valueOf(cell.getNumericCellValue());
                    }
                    //value = String.valueOf(cell.getCellFormula());
                default:
                    break;
            }
        }
        return value;
    }


    /**
     * 合并单元格处理--加入list
     *
     * @param sheet
     * @return
     */
    public static void getCombineCell(HSSFSheet sheet, List<CellRangeAddress> list) {
        // 获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = sheet.getNumMergedRegions();
        // 遍历合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            // 获得合并单元格加入list中
            CellRangeAddress ca = sheet.getMergedRegion(i);
            list.add(ca);
        }
    }

    /**
     * 合并单元格处理--加入list
     *
     * @param sheet
     * @return
     */
    public static void getCombineCell2007(XSSFSheet xsheet, List<CellRangeAddress> list) {
        // 获得一个 sheet 中合并单元格的数量
        int sheetmergerCount = xsheet.getNumMergedRegions();
        // 遍历合并单元格
        for (int i = 0; i < sheetmergerCount; i++) {
            // 获得合并单元格加入list中
            CellRangeAddress ca = xsheet.getMergedRegion(i);
            list.add(ca);
        }
    }

    /**
     * 判断单元格是否为合并单元格
     *
     * @param listCombineCell 存放合并单元格的list
     * @param cell            需要判断的单元格
     * @param sheet           sheet
     * @return
     */
    public static Boolean isCombineCell(List<CellRangeAddress> listCombineCell,
                                        HSSFCell cell, HSSFSheet sheet) {
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        for (CellRangeAddress ca : listCombineCell) {
            // 获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (cell.getColumnIndex() <= lastC && cell.getColumnIndex() >= firstC) {
                if (cell.getRowIndex() <= lastR && cell.getRowIndex() >= firstR) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断单元格是否为合并单元格
     *
     * @param listCombineCell 存放合并单元格的list
     * @param cell            需要判断的单元格
     * @param sheet           sheet
     * @return
     */
    public static Boolean isCombineCell2007(List<CellRangeAddress> listCombineCell,
                                            XSSFCell xcell, XSSFSheet xsheet) {
        int firstC = 0;
        int lastC = 0;
        int firstR = 0;
        int lastR = 0;
        for (CellRangeAddress ca : listCombineCell) {
            // 获得合并单元格的起始行, 结束行, 起始列, 结束列
            firstC = ca.getFirstColumn();
            lastC = ca.getLastColumn();
            firstR = ca.getFirstRow();
            lastR = ca.getLastRow();
            if (xcell.getColumnIndex() <= lastC && xcell.getColumnIndex() >= firstC) {
                if (xcell.getRowIndex() <= lastR && xcell.getRowIndex() >= firstR) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String s = "1997-2-13 1:23:23";
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            Date d = sf.parse(s);
            System.out.println(sf.format(d));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
