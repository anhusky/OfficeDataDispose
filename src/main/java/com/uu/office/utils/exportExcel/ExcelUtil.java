package com.uu.office.utils.exportExcel;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFCellUtil;
import org.apache.poi.ss.usermodel.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel工具类
 *
 * @author Administrator
 */
public class ExcelUtil {
    /**
     * 生成表头
     *
     * @param workbook工作表
     * @param sheet工作表的sheet
     * @param cellStyle样式
     * @param heads表头数据
     * @return
     */
    public static void toHead(Workbook workbook, Sheet sheet, CellStyle cellStyle, List<String[]> headList, boolean isauto) {
        if (sheet == null) sheet = workbook.createSheet();
        List<Integer> columns = new ArrayList<Integer>();
        List<Integer> lines = new ArrayList<Integer>();
        Map<Integer, List<Integer>> colsMap = new HashMap<Integer, List<Integer>>();
        for (int i = 0; i < headList.size(); i++) {
            Row row = sheet.createRow(i);
            row.setHeightInPoints((short) 25);
            String[] headArr = headList.get(i);
//			if(headArr==null||headArr.length==0)
//				throw new RuntimeException("第["+(i+1)+"]行表头中的值为空");
            for (int j = 0; j < headArr.length; j++) {
                Object obj = headArr[j];
                Cell cell = row.createCell(j);
//				if(obj==null||"".equals(obj))
//					throw new RuntimeException("第["+(i+1)+"]行，第["+(j+1)+"]列的表头为空");
                if ("#rowspan".equalsIgnoreCase(obj.toString())) {
                    cell.setCellValue("");
                    columns.add(j);
                }
                if ("#colspan".equalsIgnoreCase(obj.toString())) {
                    cell.setCellValue("");
                    if (!lines.contains(i))
                        lines.add(i);
                    List<Integer> cols = null;
                    if (!colsMap.containsKey(i)) {
                        cols = new ArrayList<Integer>();
                    } else {
                        cols = colsMap.get(i);
                    }
                    cols.add(j);
                    colsMap.put(i, cols);
                } else
                    cell.setCellValue(obj.toString());
                if (isauto) {
                    sheet.setColumnWidth((short) (j), 1000);
                } else {
                    sheet.setColumnWidth((short) (j), 6000);
                }
                cell.setCellStyle(cellStyle);
            }
        }
        mergedRegionRow(columns, sheet, cellStyle, 0, headList.size() - 1);
        mergedRegionColumns(lines, sheet, cellStyle, colsMap);
    }

    /**
     * 生成一行数据
     *
     * @param workbook工作表
     * @param sheet工作表的sheet
     * @param cellStyle样式
     * @param list行数据
     * @return
     */
    public static void toRow(Workbook workbook, Sheet sheet, CellStyle cellStyle, List<Object> list, boolean isauto) {
        if (sheet == null) sheet = workbook.createSheet();
        int line = sheet.getLastRowNum() + 1;
        Row row = sheet.createRow(line);
        row.setHeightInPoints((short) 25);
        int endLine = 0;
        List<Integer> columns = new ArrayList<Integer>();
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            Cell cell = row.createCell(i);
            if (obj instanceof String) {
                cell.setCellValue(obj.toString());
                columns.add(i);
            } else if (obj instanceof Double) {
                cell.setCellValue((Double) obj);
                columns.add(i);
            } else if (obj instanceof Integer) {
                cell.setCellValue((Integer) obj);
                columns.add(i);
            } else if (obj instanceof Float) {
                cell.setCellValue((Float) obj);
                columns.add(i);
            } else if (obj instanceof BigDecimal) {
                cell.setCellValue(((BigDecimal) obj) == null ? "" : ((BigDecimal) obj).toString());
                columns.add(i);
            } else if (obj instanceof Date) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cell.setCellValue(sdf.format((Date) obj));
                columns.add(i);
            } else if (obj instanceof Boolean) {
                cell.setCellValue((Boolean) obj);
                columns.add(i);
            } else if (obj instanceof Character) {
                cell.setCellValue((Character) obj);
                columns.add(i);
            } else if (obj instanceof List) {
                List rows = (List) obj;
                toColumn(workbook, sheet, line, i, cellStyle, rows, list.size());
                int tempEndLine = line + rows.size() - 1;
                if (endLine < tempEndLine)
                    endLine = tempEndLine;
            } else {
                cell.setCellValue(obj == null ? "" : obj.toString());
                columns.add(i);
            }
            if (isauto) {
                sheet.setColumnWidth((short) (i), 1000);
            } else {

            }
            sheet.setColumnWidth((short) (i), 6000);

            cell.setCellStyle(cellStyle);
        }
        if (line < endLine)
            mergedRegionRow(columns, sheet, cellStyle, line, endLine);
    }

    /**
     * 合并行的方法
     *
     * @param columns需要合并行的列集合
     * @param sheet工作表的当前sheet
     * @param cellStyle样式
     * @param stateLine开始行
     * @param endLine结束行
     */
    public static void mergedRegionRow(List<Integer> columns, Sheet sheet, CellStyle cellStyle, int stateLine, int endLine) {
        for (int i = 0; i < columns.size(); i++) {
            Integer column = columns.get(i);
            CellRangeAddress region = new CellRangeAddress(stateLine, endLine, column, column);
            sheet.addMergedRegion(region);
            for (int j = region.getFirstRow(); j <= region.getLastRow(); j++) {
                HSSFRow row = HSSFCellUtil.getRow(j, (HSSFSheet) sheet);
                for (int l = region.getFirstColumn(); l <= region.getLastColumn(); l++) {
                    HSSFCell cell2 = HSSFCellUtil.getCell(row, (short) l);
                    cell2.setCellStyle(cellStyle);
                }
            }
        }
    }

    /**
     * 合并列的方法
     *
     * @param lines需要合并列的行集合
     * @param sheet工作表的当前sheet
     * @param cellStyle样式
     * @param colsMap行对应的列集合
     */
    public static void mergedRegionColumns(List<Integer> lines, Sheet sheet, CellStyle cellStyle, Map<Integer, List<Integer>> colsMap) {
        for (int i = 0; i < lines.size(); i++) {
            Integer line = lines.get(i);
            List<Integer> cols = colsMap.get(line);
            for (int k = cols.size() - 1; k >= 0; k--) {
                Integer column = cols.get(k);
                CellRangeAddress region = new CellRangeAddress(line, line, column - 1, column);
                sheet.addMergedRegion(region);
                for (int j = region.getFirstRow(); j <= region.getLastRow(); j++) {
                    HSSFRow row = HSSFCellUtil.getRow(j, (HSSFSheet) sheet);
                    for (int l = region.getFirstColumn(); l <= region.getLastColumn(); l++) {
                        HSSFCell cell2 = HSSFCellUtil.getCell(row, (short) l);
                        cell2.setCellStyle(cellStyle);
                    }
                }
            }
        }
    }


    /**
     * 多数据行
     *
     * @param workbook   workbook工作表
     * @param sheet      sheet工作表的当前sheet
     * @param line       line数据开始行
     * @param column     column数据开始列
     * @param cellStyle  cellStyle样式
     * @param list       list数据
     * @param allColumns allColumns总列数
     */
    public static void toColumn(Workbook workbook, Sheet sheet, int line, int column, CellStyle cellStyle, List<Object> list, int allColumns) {
        if (sheet == null) sheet = workbook.createSheet();
        for (int i = 0; i < list.size(); i++) {
            Row row = sheet.getRow(line + i);
            if (row == null) row = sheet.createRow(line + i);
            row.setHeightInPoints((short) 25);
            Object obj = list.get(i);
            for (int j = 0; j < allColumns; j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                    cell.setCellValue("");
                }
                if (j == column) {
                    if (obj instanceof String)
                        cell.setCellValue(obj.toString());
                    else if (obj instanceof Double)
                        cell.setCellValue((Double) obj);
                    else if (obj instanceof Integer)
                        cell.setCellValue((Integer) obj);
                    else if (obj instanceof Float)
                        cell.setCellValue((Float) obj);
                    else if (obj instanceof Boolean)
                        cell.setCellValue((Boolean) obj);
                    else if (obj instanceof Character)
                        cell.setCellValue((Character) obj);
                    else if (obj instanceof Date) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        cell.setCellValue(sdf.format((Date) obj));
                    } else if (obj instanceof BigDecimal) {
                        cell.setCellValue(((BigDecimal) obj) == null ? "" : ((BigDecimal) obj).toString());
                    } else
                        cell.setCellValue(obj == null ? "" : obj.toString());
                }
                sheet.setColumnWidth((short) (j), 6000);
                cell.setCellStyle(cellStyle);
            }
        }
    }

    /**
     * 按照给定的开始行到结束行，替换指定列的值
     *
     * @param workbook工作表
     * @param sheet工作sheet页面
     * @param column需要转换的列序号从0开始
     * @param startLine需要转换的开始行
     * @param endLine需要转换的结束行
     * @param replaceData转换的键值对
     */
    public static void replaceWorkBook(Workbook workbook, Sheet sheet, int column, int startLine, int endLine, Map<String, String> replaceData) {
        for (int i = startLine; i <= endLine; i++) {
            Row row = sheet.getRow(i);
            if (row == null) throw new RuntimeException("第" + i + "行数据不存在，替换失败");
            Cell cell = row.getCell(column);
            if (cell == null) throw new RuntimeException("第" + i + "行第" + column + "列数据不存在，替换失败");
            String key = cell.getStringCellValue();
            if (replaceData.containsKey(key)) {
                String value = replaceData.get(key);
                cell.setCellValue(value);
            }
        }
    }

    /**
     * 按照给定的开始行到结束行，替换指定列的值
     *
     * @param workbook工作表
     * @param column需要转换的列序号从0开始
     * @param replaceData转换的键值对
     */
    public static void replaceWorkBook(Workbook workbook, int column, Map<String, String> replaceData) {
        Sheet sheet = workbook.getSheetAt(0);
        replaceWorkBook(workbook, sheet, column, 0, sheet.getLastRowNum(), replaceData);
    }

    /**
     * 按照给定的开始行，替换指定列的值
     *
     * @param workbook工作表
     * @param startLine需要转换的开始行
     * @param replaceMap转换的键值对
     */
    public static void replaceWorkBook(Workbook workbook, Map<Integer, Map<String, String>> replaceMap) {
        Sheet sheet = workbook.getSheetAt(0);
        Set<Integer> columns = replaceMap.keySet();
        for (Integer column : columns) {
            Map<String, String> replaceData = replaceMap.get(column);
            replaceWorkBook(workbook, sheet, column, 0, sheet.getLastRowNum(), replaceData);
        }
    }

    /**
     * 按照给定的开始行，替换指定列的值
     *
     * @param workbook工作表
     * @param startLine需要转换的开始行
     * @param replaceMap转换的键值对
     */
    public static void replaceWorkBook(Workbook workbook, Sheet sheet, Map<Integer, Map<String, String>> replaceMap) {
        Set<Integer> columns = replaceMap.keySet();
        for (Integer column : columns) {
            Map<String, String> replaceData = replaceMap.get(column);
            replaceWorkBook(workbook, sheet, column, 0, sheet.getLastRowNum(), replaceData);
        }
    }

    /**
     * 按照给定的开始行到结束行，替换指定列的值
     *
     * @param workbook工作表
     * @param column需要转换的列序号从0开始
     * @param replaceData转换的键值对
     */
    public static void replaceWorkBook(Workbook workbook, Sheet sheet, int column, Map<String, String> replaceData) {
        replaceWorkBook(workbook, sheet, column, 0, sheet.getLastRowNum(), replaceData);
    }

    /**
     * 设置cell样式（数据样式）
     *
     * @param workbook
     * @return
     */
    public static CellStyle getDataCellStyle(Workbook workbook) {
        CellStyle headerBorder = workbook.createCellStyle();
        headerBorder.setFillForegroundColor((short) 13);// 设置背景色
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setBoldweight(Font.BOLDWEIGHT_NORMAL);//字体显示
        font.setFontHeightInPoints((short) 10);
        headerBorder.setFont(font);
        headerBorder.setAlignment(CellStyle.ALIGN_CENTER); // 水平布局：居中
        headerBorder.setWrapText(true);
        headerBorder.setBorderBottom(CellStyle.BORDER_THIN);
        headerBorder.setBorderLeft(CellStyle.BORDER_THIN);
        headerBorder.setBorderTop(CellStyle.BORDER_THIN);//上边框
        headerBorder.setBorderRight(CellStyle.BORDER_THIN);//右边框
        headerBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return headerBorder;
    }

    /**
     * 设置cell样式（表头样式）
     *
     * @param workbook
     * @return
     */
    public static CellStyle getCellStyle(Workbook workbook) {
        CellStyle headerBorder = workbook.createCellStyle();
        headerBorder.setFillForegroundColor((short) 13);// 设置背景色
        Font font = workbook.createFont();
        font.setFontName("宋体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//字体显示
        font.setFontHeightInPoints((short) 10);
        headerBorder.setFont(font);
        headerBorder.setAlignment(CellStyle.ALIGN_CENTER); // 水平布局：居中
        headerBorder.setWrapText(true);
        headerBorder.setBorderBottom(CellStyle.BORDER_THIN);
        headerBorder.setBorderLeft(CellStyle.BORDER_THIN);
        headerBorder.setBorderTop(CellStyle.BORDER_THIN);//上边框
        headerBorder.setBorderRight(CellStyle.BORDER_THIN);//右边框
        headerBorder.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return headerBorder;
    }
}
