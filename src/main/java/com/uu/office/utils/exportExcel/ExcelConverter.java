package com.uu.office.utils.exportExcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelConverter {
	/**
	 * 将list数据转为excel导出
	 * 
	 * @param sheetIndex sheet的下标，从0开始
	 * @param sheetName excel的sheet名称
	 * @param heads 表头数据
	 * @param allList 表数据
	 * @param fields 数据属性字段
	 * @param classType 类类型
	 * @param constantMap 常量
	 * @return
	 * @throws Exception
	 */
	public static Workbook tranListToExcel(Workbook workbook,int sheetIndex,String sheetName, List<String[]> heads,List allList, List<String> fields, Class classType,Map<String,String> constantMap,boolean isatuo)throws Exception {
		try{
	//		if (allList == null || allList.size() == 0)
	//			throw new RuntimeException("数据为空，无法转为Excel");
			if (allList == null)
				allList = new ArrayList();
			Sheet sheet = workbook.createSheet();
			if (sheetName == null || "".equals(sheetName))
				sheetName = "sheet1";
			workbook.setSheetName(sheetIndex, sheetName);
			if(heads!=null && heads.size()>0)
				ExcelUtil.toHead(workbook,sheet,ExcelUtil.getCellStyle(workbook),heads,isatuo);
			CellStyle dataCellStyle = ExcelUtil.getDataCellStyle(workbook);
			if (fields != null &&fields.size() > 0){
				for (int i = 0; i < allList.size(); i++) {
					Object obj = allList.get(i);
					List<Object> list = new ArrayList<Object>();
					for (int t = 0; t < fields.size(); t++) {
						String fieldName = fields.get(t);
						if(constantMap!=null&&constantMap.containsKey(fieldName))
							list.add(constantMap.get(fieldName));
						else if(obj instanceof Map)
							list.add(((Map)obj).get(fieldName));
						else{
							if(classType==null)
								list.add(ReflectUtil.getValueByFieldName(obj,fieldName));
							else
								list.add(ReflectUtil.getValueByFieldName(classType, obj,fieldName));
						}
					}
					ExcelUtil.toRow(workbook, sheet,dataCellStyle ,list,isatuo);
				}
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("["+e.getLocalizedMessage()+"]参数解析失败,未找到对应的 类");
		} catch (NoSuchMethodException e){
			throw new RuntimeException("[fields]参数解析失败,未找到对应的 方法："+e.getLocalizedMessage());
		} catch (Exception e){
			throw e;
		}
		return workbook;
	}
	/**
	 * 将list数据转为excel导出
	 * 
	 * @param sheetName excel的sheet名称
	 * @param heads 表头数据
	 * @param allList 表数据
	 * @param fields 数据属性字段
	 * @return
	 * @throws Exception
	 */
	public static Workbook tranListToExcel(String sheetName, String heads,List allList,String fields)throws Exception {
		List<String[]> headExcel = AnalyzeParams.toHeadExcel(heads);
		List<String> fieldNames = AnalyzeParams.toField(fields);
		Workbook workbook = new HSSFWorkbook();
		return tranListToExcel(workbook,0,sheetName, headExcel, allList, fieldNames, null,null,false);
	}
	/**
	 * 将list数据转为excel导出
	 * 
	 * @param sheetIndex sheet的下标，从0开始
	 * @param sheetName excel的sheet名称
	 * @param heads 表头数据
	 * @param allList 表数据
	 * @param fields 数据属性字段
	 * @return
	 * @throws Exception
	 */
	public static Workbook tranListToExcel(int sheetIndex,String sheetName, String heads,List allList,String fields)throws Exception {
		List<String[]> headExcel = AnalyzeParams.toHeadExcel(heads);
		List<String> fieldNames = AnalyzeParams.toField(fields);
		Workbook workbook = new HSSFWorkbook();
		return tranListToExcel(workbook,sheetIndex,sheetName, headExcel, allList, fieldNames, null,null,false);
	}
	/**
	 * 将list数据转为excel导出
	 * 
	 * @param sheetIndex sheet的下标，从0开始
	 * @param sheetName excel的sheet名称
	 * @param heads 表头数据
	 * @param allList 表数据
	 * @param fields 数据属性字段
	 * @return
	 * @throws Exception
	 */
	public static Workbook tranListToExcel(Workbook workbook,int sheetIndex,String sheetName, String heads,List allList,String fields)throws Exception {
		List<String[]> headExcel = AnalyzeParams.toHeadExcel(heads);
		List<String> fieldNames = AnalyzeParams.toField(fields);
		return tranListToExcel(workbook,sheetIndex,sheetName, headExcel, allList, fieldNames, null,null,false);
	}
	/**
	 * 将list数据转为excel导出
	 * 
	 * @param sheetName excel的sheet名称
	 * @param heads 表头数据
	 * @param allList 表数据
	 * @param fields 数据属性字段
	 * @return
	 * @throws Exception
	 */
	public static Workbook tranListToExcel(String sheetName, List<String[]> heads,List allList, List<String> fields)throws Exception {
		Workbook workbook = new HSSFWorkbook();
		return tranListToExcel(workbook,0,sheetName, heads, allList, fields, null,null,false);
	}
	/**
	 * 将list数据转为excel导出
	 * 
	 * @param sheetName excel的sheet名称
	 * @param heads 表头数据
	 * @param allList 表数据
	 * @param fields 数据属性字段
	 * @param constantMap 常量
	 * @return
	 * @throws Exception
	 */
	public static Workbook tranListToExcel(String sheetName, List<String[]> heads,List allList, List<String> fields,Map<String,String> constantMap)throws Exception {
		Workbook workbook = new HSSFWorkbook();
		return tranListToExcel(workbook,0,sheetName, heads, allList, fields, null,constantMap,false);
	}
	
	/**
	 * 将list数据转为excel导出
	 * 
	 * @param sheetName excel的sheet名称
	 * @param heads 表头数据
	 * @param allList 表数据
	 * @param fields 数据属性字段
	 * @return
	 * @throws Exception
	 */
	public static Workbook tranListToExcel(String sheetName, String heads,List allList,String fields,boolean isauto)throws Exception {
		List<String[]> headExcel = AnalyzeParams.toHeadExcel(heads);
		List<String> fieldNames = AnalyzeParams.toField(fields);
		Workbook workbook = new HSSFWorkbook();
		return tranListToExcel(workbook,0,sheetName, headExcel, allList, fieldNames, null,null,isauto);
	}
}
