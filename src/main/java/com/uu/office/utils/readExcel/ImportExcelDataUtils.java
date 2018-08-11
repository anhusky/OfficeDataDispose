package com.uu.office.utils.readExcel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.List;


/**
 * <pre>
 *
 *  读取Excel 时 的帮助类：
 *      用于帮助判断：
 *              行是否为空了，
 *              获取表格元素了(根据cell,即一个单元格）
 *
 * </pre>
 */
public class ImportExcelDataUtils {
    private POIFSFileSystem fs;
    private HSSFWorkbook wb;

    private static ImportExcelDataUtils exportExcelDataUtils = null;

    //读取文件流
    public HSSFWorkbook readInputStreanAndToWorkbook(InputStream is) throws IOException {
        this.fs = new POIFSFileSystem(is);
        this.wb = new HSSFWorkbook(this.fs);
        return wb;
    }

    public static HSSFWorkbook getInstanceForHSSFWorkbook(InputStream is) throws IOException {
        if (exportExcelDataUtils == null) {
            exportExcelDataUtils = new ImportExcelDataUtils();
        }
        return exportExcelDataUtils.readInputStreanAndToWorkbook(is);
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
     * @param xsheet
     * @param list
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
     * @param xcell
     * @param xsheet
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


}
