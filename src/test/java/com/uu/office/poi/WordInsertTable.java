package com.uu.office.poi;

import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;

import java.io.*;
import java.net.URL;
import java.util.List;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-25 下午3:24
 **/
public class WordInsertTable {
    @Test
    public void testOne() throws IOException {
        URL url = this.getClass().getResource("/wordInsertTableTemplate.docx");
        String path = url.getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);
        InputStream inputStream = new FileInputStream(path + "/wordInsertTableTemplate.docx");

        XWPFDocument document = new XWPFDocument(inputStream);
        List<XWPFTable> tables = document.getTables();

        //这里简单取第一个表格
        XWPFTable table = tables.get(0);
        //获取表头，这里没什么用，只是打印验证下
        XWPFTableRow header = table.getRow(0);
        System.out.println(header.getCell(0).getText());

        XWPFParagraph p = document.createParagraph();

        for(int i = 1; i<= 5; i++){
            XWPFTableRow tempRow = table.createRow();
            setCell(tempRow.getCell(1)," " + i,p);
        }

        inputStream.close();
        //写到目标文件
        OutputStream output = new FileOutputStream(path + "outFile.docx");
        document.write(output);
        output.close();
    }

    /**
     * 设置表格内容及对齐方式
     * @param cell
     * @param text
     * @param p
     */
    private void setCell(XWPFTableCell cell, String text, XWPFParagraph p){
        if(cell.getParagraphs().size()>0){
            p=cell.getParagraphs().get(0);
        }else{
            p=cell.addParagraph();
        }
        XWPFRun pRun=p.createRun();
        pRun.setText(text);
        //垂直居中
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        //水平居中
        p.setAlignment(ParagraphAlignment.LEFT);

    }
}
