package com.uu.office;

import com.alibaba.fastjson.JSONObject;
import com.uu.office.annotation.IgnoreFiled;
import com.uu.office.entity.User;
import com.uu.office.utils.InsertData2Word.CustomXWPFDocument;
import com.uu.office.utils.InsertData2Word.WordUtil;
import com.uu.office.utils.Java8Utils;
import com.uu.office.utils.exportExcel.ExcelConverter;
import com.uu.office.utils.readExcel.ImportExcelUnit;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
        param.put("${name}", "唐嫣");
        param.put("${major}", "计算机科学与技术");
        param.put("${sex}", "男");
        param.put("${schoolName}", "福建农林大学");
        param.put("${date}", new Date().toString());


        String path = this.getClass().getResource("/0.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);


        //图片标记，路径，宽高
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("width", 300);
        header.put("height", 400);
        header.put("type", "jpg");
        header.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(path + "/0.jpg"), true));
        param.put("${headerImg}", header);

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


    public void testWordInsertPicture(Map<String, Object> paramMap) throws IOException {
        String path = this.getClass().getResource("/0.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);
        //原文件
        CustomXWPFDocument doc = WordUtil.generateWord(paramMap, path + "/template.docx");
        //输出文件
        FileOutputStream fopts = new FileOutputStream(path + "/outFile.docx");
        doc.write(fopts);
        fopts.close();
    }




    /**
     * 通过反射将魔板替换的 对象转换成 map
     *
     * @return
     * @throws Exception
     */
    @Test
    public void getPrivateValueFromClassObject1() throws Exception {

        String path = this.getClass().getResource("/0.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);


        Diploma model = new Diploma("胡歌", "表演", "男", "上海\r\n戏剧学院");
        model.setOne("666");
        model.setTwo("不错");
        model.setThree("80%");

        Map<String, Object> header = new HashMap<String, Object>();
        header.put("width", 300);
        header.put("height", 400);
        header.put("type", "jpg");
        header.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(path + "/0.jpg"), true));
        model.setHeaderImg(header);

        List<Inner> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Inner inner = new Inner(2016 + i + "", 100 * i + "", 90 * i + "");
            list.add(inner);
        }
        model.setInners(list);

        Map<String, Object> map = new HashMap<String, Object>();
        getParamMap(map,model,false,null,null);

        System.out.println(map.entrySet().size() + "--------");
        testWordInsertPicture(map);
    }

    /**
     * 获取对象中的属性，并把它封装在map中
     *
     * @param object 对象
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public Map<String, Object> getParamMap(Map map, Object object, boolean isInner, String innerPropertyName, Integer innerIndex) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {

        Field[] fields = object.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组

        // 遍历所有属性
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            String propertyName = fields[i].getName();
            if (propertyName.equals("this$0") || field.getAnnotation(IgnoreFiled.class) != null) {
                continue;
            }
            String name = propertyName.substring(0, 1).toUpperCase() + propertyName.substring(1); // 将属性的首字符大写，方便构造get，set方法
            String type = fields[i].getGenericType().toString(); // 获取属性的类型
            System.out.println("type:" + type);

            Method m = object.getClass().getMethod("get" + name);
            Object value = m.invoke(object); // 调用getter方法获取属性值
            if (value == null) {
                continue;
            }

            if (type.contains("List")) {
                Method method = value.getClass().getMethod("size");
                int size = (Integer) method.invoke(value);
                System.out.println("size =" + size);
                if (size > 0) {
                    for (int k = 0; k < size; k++) {
                        Method m2 = value.getClass().getMethod("get", int.class);
                        Object obj = m2.invoke(value, k);
                        getParamMap(map, obj, true, "inner", k);
                    }
                }
            }
            if (isInner)
                map.put("${" + innerPropertyName + "" + innerIndex + "" + propertyName + "}", value);
            else
                map.put("${" + propertyName + "}", value);


            /*switch (type) {
                case "class java.lang.Integer":
                case "class java.util.Date":
                case "class java.lang.String":
                    //String value1 = (String) value;
                    map.put("${" + propertyName + "}", value);
                    break;
                case "java.util.Map<java.lang.String, java.lang.Object>":
                    map.put("${" + propertyName + "}", value);
                    break;
            }*/
        }
        return map;
    }


    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Diploma {
        private String name;
        private String major;
        private String sex;
        private String schoolName;
        private String one;
        private String two;
        private String three;

        private List<Inner> inners;

        @IgnoreFiled
        private String ignore;
        private Map<String, Object> headerImg;

        public Diploma(String name, String major, String sex, String schoolName) {
            this.name = name;
            this.major = major;
            this.sex = sex;
            this.schoolName = schoolName;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Inner {
        private String year;
        private String count;
        private String outCount;
    }


    @Test
    public void getPrivateValueFromClassObject() {
        Diploma diploma = new Diploma("胡歌", "表演", "男", "上海戏剧学院");
       /* String s = JSON.toJSONString(diploma).toString();
        System.out.println(diploma);*/

        String s1 = JSONObject.toJSONString(diploma);
        System.out.println(s1);

    }


    /**
     * 测试 获取list  属性 通过反射
     *
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    @Test
    public void testList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        HashMap<String, Object> map = new HashMap<>();

        List<Inner> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Inner inner = new Inner(2016 + i + "", 100 * i + "", 90 * i + "");
            list.add(inner);
        }

        Method method = list.getClass().getMethod("size");
        int size = (Integer) method.invoke(list);
        System.out.println("size =" + size);
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                Method m2 = list.getClass().getMethod("get", int.class);
                Object object = m2.invoke(list, i);
                getParamMap(map, object, true, "inner", i);
            }
        }

        System.out.println("--------");

    }


}
