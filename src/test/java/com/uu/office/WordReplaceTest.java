package com.uu.office;

import com.alibaba.fastjson.JSONObject;
import com.uu.office.annotation.IgnoreFiled;
import com.uu.office.entity.*;
import com.uu.office.utils.InsertData2Word.CustomXWPFDocument;
import com.uu.office.utils.InsertData2Word.WordUtil;
import com.uu.office.utils.WordDataUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 描述：
 *
 * @author liupenghao
 * @create 2018-07-17 下午2:09
 **/
public class WordReplaceTest {
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
        String path = this.getClass().getResource("/mm2.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);
        //原文件
        CustomXWPFDocument doc = WordUtil.generateWord(paramMap, path + "/template.docx");
        //输出文件
        FileOutputStream fopts = new FileOutputStream(path + "/outFile.docx");
        doc.write(fopts);
        fopts.close();
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
                        getParamMap(map, obj, true, propertyName, k);
                    }
                }
            }
            if (isInner)
                map.put("${" + innerPropertyName + "" + innerIndex + "" + propertyName + "}", value);
            else
                map.put("${" + propertyName + "}", value);


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

        private List<Inner> inner;

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


    /**
     * 通过反射将魔板替换的 对象转换成 map
     *
     * @return
     * @throws Exception
     */
    @Test
    public void getPrivateValueFromClassObject1() throws Exception {

        String path = this.getClass().getResource("/mm2.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);


        Diploma model = new Diploma("胡歌", "表演", "男", "上海\r\n戏剧学院");
        model.setOne("666");
        model.setTwo("不错");
        model.setThree("80%");

        Map<String, Object> header = new HashMap<String, Object>();
        header.put("width", 300);
        header.put("height", 400);
        header.put("type", "jpg");
        header.put("content", WordUtil.inputStream2ByteArray(new FileInputStream(path + "/mm2.jpg"), true));
        model.setHeaderImg(header);

        List<Inner> list = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            Inner inner = new Inner(2016 + i + "", 100 * i + "", 90 * i + "");
            list.add(inner);
        }
        model.setInner(list);

        Map<String, Object> map = new HashMap<String, Object>();
        getParamMap(map, model, false, null, null);
        map.put("${judge}", "□有");
        map.put("${experience.passRateForSubjectOne}", "666");

        System.out.println(map.entrySet().size() + "--------");
        testWordInsertPicture(map);
    }
    //-------------------------------------------- 正式 测试  ----------------------------------------------

    /**
     *
     * @throws Exception
     */
    @Test
    public void replaceTemplate2() throws Exception {
        SchoolCheck schoolCheck = new SchoolCheck();
        schoolCheck.setName("安利驾校");
        schoolCheck.setProperty("民营");
        schoolCheck.setOpenTime("20180101");
        schoolCheck.setType("校企合作");
        schoolCheck.setAddress("北京海淀");
        schoolCheck.setHeadMaster("张三");
        schoolCheck.setPhone("13838385438");
        schoolCheck.setCreateTime(new Date());


        //-------------学车体验-------------
        Experience experience = new Experience();
        experience.setPassRateForSubjectOne(666);
        experience.setPassRateForSubjectTwo(888);
        schoolCheck.setExperience(experience);

        Installation installation = new Installation();
        experience.setInstallation(installation);

        CostAndHour costAndHour = new CostAndHour();
        experience.setCostAndHour(costAndHour);

        ArrayList<String> others = new ArrayList<>();
        for (int i = 1; i <= 8; i++) {
            others.add(i + "");
        }
        costAndHour.setOthers(others);


        List<ClassType> classTypeList = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            ClassType classType = new ClassType();
            classType.setName("name" + i);
            classType.setPrice("11" + i);
            classType.setServer("server" + i);
            classTypeList.add(classType);
        }
        experience.setClassTypeList(classTypeList);
        experience.setDsClassTypeList(classTypeList);


        //------可见度 与方便性------
        Convenience convenience = new Convenience();
        schoolCheck.setConvenience(convenience);

        //-------招生方式及部署、促销活动及方式----------
        EnrollInfo enrollInfo = new EnrollInfo();
        schoolCheck.setEnrollInfo(enrollInfo);

        //-------商圈分析------------
        BusinessCircle businessCircle = new BusinessCircle();
        schoolCheck.setBusinessCircle(businessCircle);

        //-------社区关系---------
        Community community = new Community();
        schoolCheck.setCommunity(community);

        //-------驾校营业额提升的机会--------
        PromoteChance promoteChance = new PromoteChance();
        schoolCheck.setPromoteChance(promoteChance);
        //-------其它调研-----------
        OtherCheck otherCheck = new OtherCheck();
        schoolCheck.setOtherCheck(otherCheck);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map = (HashMap<String, Object>) WordDataUtils.getParamMap(
                map, schoolCheck, false, null, null);

        ArrayList<String> list = new ArrayList<>();
        map.forEach((key, value) -> {
            //System.out.println(key);
            list.add(key);
        });

        list.sort((s1, s2) -> s1.compareTo(s2));
        list.forEach(System.out::println);


        String path = this.getClass().getResource("/mm2.jpg").getPath();
        path = path.substring(0, path.lastIndexOf("/") + 1);
        //原文件
        CustomXWPFDocument doc = WordUtil.generateWord(map, path + "/templateLast.docx");
        //输出文件
        FileOutputStream fopts = new FileOutputStream(path + "/outFile.docx");
        doc.write(fopts);
        fopts.close();

    }


    @Test
    public void testJsonStr() {
        String s = "123";
        System.out.println(s.toString().toString());

    }
}

