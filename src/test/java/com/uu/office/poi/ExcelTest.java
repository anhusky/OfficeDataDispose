package com.uu.office.poi;

import com.uu.office.entity.User;
import com.uu.office.utils.Java8Utils;
import com.uu.office.utils.exportExcel.ExcelConverter;
import com.uu.office.utils.readExcel.ImportExcelDateServiceImpl;
import com.uu.office.utils.readExcel.ImportExcelDataUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;
import java.text.ParseException;
import java.util.*;

/**
 * Unit test for simple App.
 */
public class ExcelTest {

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

        workbook.write(new FileOutputStream(new File("outFile.xls")));
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
        HSSFWorkbook hw = ImportExcelDataUtils.getInstanceForHSSFWorkbook(is);

        List<User> users = ImportExcelDateServiceImpl.readExcelContentToUser(hw, 0, 1);
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
        HSSFWorkbook hw = ImportExcelDataUtils.getInstanceForHSSFWorkbook(is);
        List<String> list = ImportExcelDateServiceImpl.readTitles(hw, 0);
        Java8Utils.forEach(list, System.out::println);
    }

    /**
     *
     */
    @Test
    public void testReadWzInfo() throws IOException, ParseException {
        InputStream is = this.getClass().getResourceAsStream("/盘点表.xls");
        if (is != null) {
            System.out.println("读取到excel 文件");
        }
        HSSFWorkbook hw = ImportExcelDataUtils.getInstanceForHSSFWorkbook(is);
        ImportExcelDateServiceImpl.readExcelContentToWzInfo(hw);
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

}
