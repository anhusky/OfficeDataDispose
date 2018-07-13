package com.uu.office.utils.exportExcel;

import java.util.*;

public class AnalyzeParams {
    /**
     * 列对应需要替换的键值对
     *
     * @param replaceData
     * @return
     */
    public static Map<Integer, Map<String, String>> toReplaceMap(String replaceData) {
        Map<Integer, Map<String, String>> repaceMap = new HashMap<Integer, Map<String, String>>();
        if (replaceData == null || "".equalsIgnoreCase(replaceData))
            return null;
        String[] c = replaceData.split("&");
        for (String s : c) {
            String[] t = s.split("=");
            if (t.length != 2)
                throw new RuntimeException("[replaceData]参数解析失败,无效的表达式:" + replaceData);
            String[] m = t[1].split(",");
            Map<String, String> map = new HashMap<String, String>();
            for (String m1 : m) {
                String[] k = m1.split(":");
                if (k.length != 2)
                    throw new RuntimeException("[replaceData]参数解析失败,无效的表达式:" + replaceData);
                map.put(k[0], k[1]);
            }
            String regex = "^[0-9]*$";
            if (!t[0].matches(regex))
                throw new RuntimeException("[replaceData]参数解析失败,无效的表达式:" + replaceData);
            repaceMap.put(Integer.parseInt(t[0]), map);
        }
        return repaceMap;
    }

    /**
     * 将字段对应的常量转为map
     *
     * @param constant
     * @return
     */
    public static Map<String, String> toConstant(String constant) {
        Map<String, String> constantMap = new HashMap<String, String>();
        if (constant == null || "".equalsIgnoreCase(constant))
            return null;
        String[] c = constant.split(",");
        for (String s : c) {
            String[] t = s.split(":");
            if (t.length != 2)
                throw new RuntimeException("[constant]参数解析失败,无效的表达式:" + constant);
            constantMap.put(t[0], t[1]);
        }
        return constantMap;
    }

    /**
     * 将数据属性字段转为集合
     *
     * @param fields
     * @return
     */
    public static List<String> toField(String fields) {
        if (fields == null || "".equalsIgnoreCase(fields))
            return null;

        String[] h = fields.split(",");
        return Arrays.asList(h);
    }

    /**
     * 将表头字符串转为数据集合
     *
     * @param heads
     * @return
     */
    public static List<String[]> toHeadExcel(String heads) {
        List<String[]> headExcel = new ArrayList<String[]>();
        if (heads == null || "".equalsIgnoreCase(heads))
            return null;
        String[] h = heads.split("&");
        for (String s : h) {
            headExcel.add(s.split(","));
        }
        return headExcel;
    }
}
