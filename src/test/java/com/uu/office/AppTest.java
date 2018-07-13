package com.uu.office;

import com.uu.office.entity.User;
import com.uu.office.utils.InsertData2Word.CustomXWPFDocument;
import com.uu.office.utils.InsertData2Word.WordUtil;
import com.uu.office.utils.Java8Utils;
import com.uu.office.utils.exportExcel.ExcelConverter;
import com.uu.office.utils.readExcel.ImportExcelUnit;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * 导出 Excel 测试
     *
     * @throws Exception
     */
    @Test
    public void testExportExcel() throws Exception {
        String sheetName = "test";
        String heads = "姓名,年龄,性别,生日";
        String fields = "name,age,sex,birthday";
        List<Map<String, Object>> rrs = new ArrayList<Map<String, Object>>();

        for (int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("name", " 编号" + i);
            map.put("age", new Random().nextInt(10) + 10);
            map.put("sex", new Random().nextInt(1));
            map.put("birthday", "2018070" + new Random().nextInt(31) + 1);
            rrs.add(map);
        }

        Workbook workbook = ExcelConverter.tranListToExcel(sheetName, heads, rrs, fields);

        workbook.write(new FileOutputStream(new File("template.xls")));
    }


    /**
     * 测试从excel 中读取数据
     */
    @Test
    public void testReadExcel() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/template.xls");
        if (is != null) {
            System.out.println("读取到excel 文件");
        }
        HSSFWorkbook hw = ImportExcelUnit.readInputStreanAndToWorkbook(is);
        List<User> users = ImportExcelUnit.readExcelContentToUser(hw, 0, 1);
        users.stream().forEach(entity -> {
            System.out.println(entity.getName());
        });
    }

    /**
     * 读取excel 的标题
     */
    @Test
    public void testReadExcelTitle() throws IOException {
        InputStream is = this.getClass().getResourceAsStream("/template.xls");
        if (is != null) {
            System.out.println("读取到excel 文件");
        }
        HSSFWorkbook hw = ImportExcelUnit.readInputStreanAndToWorkbook(is);
        List<String> list = ImportExcelUnit.readTitles(hw, 0);
        Java8Utils.forEach(list, System.out::println);
    }

    @Test
    public void replaceWordText() throws IOException {
        List<String> list = Arrays.asList("${SchoolName}", "${managerNature}");
        List<String> data = Arrays.asList("安利驾校", "民营");

        String fileName = "outFile.docx";
        createFile(fileName);

        InputStream is = this.getClass().getResourceAsStream("/test.docx");
        OutputStream os = new FileOutputStream(new File(fileName));
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));

        String line = null;
        while ((line = br.readLine()) != null) {
            for (int i = 0; i < list.size(); i++) {
                String key = list.get(i);
                boolean flag = line.contains(key);
                if (flag) {  //此行包含关键字  替换，并移除关键字
                    line = line.replace(key, data.get(i));
                }
            }
            bw.write(line);
            line = null;
        }
    }

    public void createFile(String fileName) throws IOException {

        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    @Test
    public void testWordInsertPicture() throws IOException {
        //文字标记
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("${name}", "qiucx");
        param.put("${zhuanye}", "计算机科学与技术");
        param.put("${sex}", "男");
        param.put("${school_name}", "福建农林大学");
        param.put("${date}", new Date().toString());


        String path = this.getClass().getResource("/mm2.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);


        //图片标记，路径，宽高
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("width", 150);
        header.put("height", 150);
        header.put("type", "jpg");
        header.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(path + "/mm2.jpg"), true));
        param.put("${header}", header);

        //原文件
        CustomXWPFDocument doc = WordUtil.generateWord(param, path + "/template.docx");
        //输出文件


        FileOutputStream fopts = new FileOutputStream(path + "/outFile.docx");
        doc.write(fopts);
        fopts.close();
    }

    @Test
    public void testPath() {
        String path = this.getClass().getResource("/mm2.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);
        System.out.println(path + "........");

    }

}
