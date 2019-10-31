package com.fh.admin.util;

import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.xssf.usermodel.*;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ExcelUtil {

    public static XSSFWorkbook getXSSDworkbook(String title, List datas, String[] heads, String[] prpos, Class clazz){
        XSSFWorkbook book = new XSSFWorkbook();//创建workbook
        XSSFCellStyle dateStyle = book.createCellStyle();//创建样式
        dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));//单元格时间格式
        XSSFCellStyle numStyle = book.createCellStyle();//创建样式
        numStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));//单元格数值格式
        HashMap<String, XSSFCellStyle> styleMap = new HashMap<>();//样式map
        styleMap.put("dateStyle",dateStyle);
        styleMap.put("numStyle",numStyle);
        XSSFSheet sheet = book.createSheet(title);//创建sheet
        getXSSFWorkbookHead(datas, heads, prpos, clazz, styleMap, sheet);
        return book;
    }

    private static void getXSSFWorkbookHead(List datas, String[] heads, String[] prpos, Class clazz, HashMap<String, XSSFCellStyle> styleMap, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);//第一行为表头行
        for(int i=0;i<heads.length;i++){//循环设置表头
            row.createCell(i).setCellValue(heads[i]);
        }
        getXSSFWorkbookBody(datas, prpos, clazz, styleMap, sheet);
    }

    private static void getXSSFWorkbookBody(List datas, String[] prpos, Class clazz, HashMap<String, XSSFCellStyle> styleMap, XSSFSheet sheet) {
        for(int i=0;i<datas.size();i++){//设置主体
            XSSFRow row1 = sheet.createRow(i + 1);//数据条数决定行数
            Object obj = datas.get(i);//取出当前循环对象
            getXSSFWorkbookRow(datas, prpos, clazz, styleMap, obj, row1);
        }
    }

    private static void getXSSFWorkbookRow(List datas, String[] prpos, Class clazz, HashMap<String, XSSFCellStyle> styleMap, Object obj, XSSFRow row) {
        for(int j=0;j<prpos.length;j++){
            XSSFCell cell = row.createCell(j);
            try {
                Field field = clazz.getDeclaredField(prpos[j]);//获取所有的属性
                field.setAccessible(true);//暴力访问
                Object o = field.get(obj);
                Class<?> type = field.getType();
                if(null != o){
                    if(type == String.class) cell.setCellValue(String.valueOf(o));
                    if(type == Long.class) cell.setCellValue(Integer.valueOf(String.valueOf(o)));
                    if(type == Integer.class) {
                        cell.setCellStyle(styleMap.get("numStyle"));
                        cell.setCellValue((Integer)o);
                    }
                    if(type == BigDecimal.class) {
                        cell.setCellStyle(styleMap.get("numStyle"));
                        cell.setCellValue(((BigDecimal)o).doubleValue());
                    }
                    if(type == Double.class) {
                        cell.setCellStyle(styleMap.get("numStyle"));
                        cell.setCellValue((Double)o);
                    }
                    if(type == Date.class) {
                        cell.setCellStyle(styleMap.get("dateStyle"));
                        cell.setCellValue((Date) o);
                    }
                }else{
                    cell.setCellValue("");
                }
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

}
