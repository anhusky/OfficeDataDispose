package com.uu.office.utils.InsertData2Word;


import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.*;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/********************************************************
 * 类名：WordUtil
 * 功能：POI Word读取编辑工具类
 * 作者：qiucx 2017年4月12日
 * 修改记录：
 *          日期                 修改人                 修改说明
 ********************************************************/
public class WordUtil {


    /********************************************************
     * 函数名: generateWord
     * 功能 : 通用功能处理
     * 作者 : qiucx 2017年4月12日
     * 参数表: @param param
     * 参数表: @param template
     * 参数表: @return    
     * 返回值: CustomXWPFDocument    
     * 修改记录：
     *          日期                 修改人                 修改说明
     ********************************************************/
    public static CustomXWPFDocument generateWord(Map<String, Object> param, String template) {
        CustomXWPFDocument doc = null;
        try {
            OPCPackage pack = POIXMLDocument.openPackage(template);
            doc = new CustomXWPFDocument(pack);
            if (param != null && param.size() > 0) {

                //处理段落
                List<XWPFParagraph> paragraphList = doc.getParagraphs();
                processParagraphs(paragraphList, param, doc);

                //处理表格
                Iterator<XWPFTable> it = doc.getTablesIterator();
                while (it.hasNext()) {
                    XWPFTable table = it.next();
                    List<XWPFTableRow> rows = table.getRows();
                    for (XWPFTableRow row : rows) {
                        List<XWPFTableCell> cells = row.getTableCells();
                        for (XWPFTableCell cell : cells) {
                            List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
                            processParagraphs(paragraphListTable, param, doc);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doc;
    }


    /********************************************************
     * 函数名: processParagraphs
     * 功能 : 处理段落
     * 作者 : qiucx 2017年4月12日
     * 参数表: @param paragraphList
     * 参数表: @param param
     * 参数表: @param doc    
     * 返回值: void    
     * 修改记录：
     *          日期                 修改人                 修改说明
     ********************************************************/
    public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param, CustomXWPFDocument doc) {
        if (paragraphList != null && paragraphList.size() > 0) {
            for (XWPFParagraph paragraph : paragraphList) {
                CopyOnWriteArrayList<XWPFRun> runs = new CopyOnWriteArrayList<>();
                runs.addAll( paragraph.getRuns());

                for (XWPFRun run : runs) {
                    String text = run.getText(0);
                    if (text != null) {
                        boolean isSetText = false;
                        for (Map.Entry<String, Object> entry : param.entrySet()) {
                            String key = entry.getKey();
                            if (text.indexOf(key) != -1) {
                                isSetText = true;
                                Object value = entry.getValue();
                                if (value instanceof String) {//文本替换
                                    text = text.replace(key, value.toString());
                                } else if (value instanceof Map) {//图片替换
                                    text = text.replace(key, "");
                                    Map pic = (Map) value;
                                    int width = Integer.parseInt(pic.get("width").toString());
                                    int height = Integer.parseInt(pic.get("height").toString());
                                    int picType = getPictureType(pic.get("type").toString());
                                    byte[] byteArray = (byte[]) pic.get("content");
                                    ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                                    try {
                                        //int ind = doc.addPicture(byteInputStream, picType);
                                        String ind = doc.addPictureData(byteInputStream,picType);
                                        System.out.println(ind + "------------");

                                        doc.createPicture(0, width, height, paragraph);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                        if (isSetText) {
                            run.setText(text, 0);
                        }
                    }
                }
            }
        }
    }


    /********************************************************
     * 函数名: getPictureType
     * 功能 : 根据图片类型，取得对应的图片类型代码
     * 作者 : qiucx 2017年4月12日
     * 参数表: @param picType
     * 参数表: @return    
     * 返回值: int    
     * 修改记录：
     *          日期                 修改人                 修改说明
     ********************************************************/
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }


    /********************************************************
     * 函数名: inputStream2ByteArray
     * 功能 : 将输入流中的数据写入字节数组
     * 作者 : qiucx 2017年4月12日
     * 参数表: @param in
     * 参数表: @param isClose
     * 参数表: @return    
     * 返回值: byte[]    
     * 修改记录：
     *          日期                 修改人                 修改说明
     ********************************************************/
    public static byte[] inputStream2ByteArray(FileInputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    System.out.println("关闭流失败");
                }
            }
        }
        return byteArray;
    }
}
