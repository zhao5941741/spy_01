package com.example.spy.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * excel工具类
 */
public class ExcelUtil {
    /**
     * 将Excel内容转换list
     * @param file
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> excelToList(MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0); //获取上传的第一个excel
        //行数
        int num = sheet.getLastRowNum();//得到excel的行
        //列数
        int col = sheet.getRow(0).getLastCellNum();
        List<Map<String, Object>> list = new ArrayList<>();
        String[] colName = new String[col+2];
        //获取第一行的单元格
        Row row = sheet.getRow(0);
        for (int i = 0; i < col; i++) {
            //获取列名
            String[] s = row.getCell(i).getStringCellValue().split("-");
            colName[i] = s[0];
            if (i+1 == col) {
                colName[i+1] = "初始化时间";
                colName[i+2] = "校对值";
            }
        }
        System.out.println(JSONObject.toJSON(colName));
        //将一行中每列数据放入一个map中,然后把map放入list
        int pre = 1;
        int a = 0;

        for (int i = 1; i <= num; i++) {
            Map<String, Object> map = new HashMap<>();
            //获取每一行数据
            Row row1 = sheet.getRow(i);
            if (row1 != null) {
                for (int j = 0; j < col; j++) {
                    Cell cell = row1.getCell(j); //获取当前行的数据
                    if (cell != null) {
                        cell.setCellType(Cell.CELL_TYPE_STRING);
                        if (j+1 == col) {
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            map.put(colName[j+1],sdf.format(new Date()));
                            int s = i;
                            if ((i-a)%10000 == 0) {
                                a = i-1;
                                System.out.println(a);
                            }
                            s-=a;
                            String suf = getJd(s);
                            String str = getJd((suf.equals("9999")?pre++:pre))+ "."+suf;
                            map.put(colName[j+2],str);
                        }
                        map.put(colName[j], cell.getStringCellValue());
                    }
                }
            }
            list.add(map);
        }
        return list;
    }
    public static String getJd(int i) {
        String str = i+"";
        switch (str.length()) {
            case 1:
                str = "000"+str;
                break;
            case 2:
                str = "00"+str;
                break;
            case 3:
                str = "0"+str;
                break;
        }
        return str;
    }

    /**
     * 导出集合到excel
     * @param response
     * @param name
     * @param list
     */
    public static void exportToExcel(HttpServletResponse response, String name, List<Map<String, Object>> list) {
        try {
            //文件名称
            String fileName = name + ".xls";
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
            HSSFSheet hssfSheet = hssfWorkbook.createSheet(name);
            int rowNum = 0;
            //新建行
            HSSFRow hssfRow = hssfSheet.createRow(rowNum++);
            //列
            int j = 0;
            if (list.size() > 0) {
                for (String i : list.get(0).keySet()) {
                    //新建第一行
                    hssfRow.createCell(j++).setCellValue(i);
                }
                //将数据放入表中
                for (int i = 0; i < list.size(); i++) {
                    //新建一行
                    HSSFRow row = hssfSheet.createRow(rowNum++);
                    Map map = list.get(i);
                    j = 0;
                    for (Object obj : map.values()) {
                        if (obj != null) {
                            row.createCell(j++).setCellValue(obj.toString());
                        } else {
                            row.createCell(j++);
                        }
                    }
                }
            }
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "utf-8"));
            hssfWorkbook.write(response.getOutputStream());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /***
     * 解析Excel日期格式
     * @param strDate
     * @return
     */
    public static String ExcelDoubleToDate(String strDate) {
        if (strDate.length() == 5) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date tDate = DoubleToDate(Double.parseDouble(strDate));
                return sdf.format(tDate);
            } catch (Exception e) {
                e.printStackTrace();
                return strDate;
            }
        }
        return strDate;
    }

    /**
     * 解析Excel日期格式
     * @param dVal
     * @return
     */
    public static Date DoubleToDate(Double dVal) {
        Date tDate = new Date();
        //系统时区偏移 1900/1/1 到 1970/1/1 的 25569 天
        long localOffset = tDate.getTimezoneOffset() * 60000;
        tDate.setTime((long) ((dVal - 25569) * 24 * 3600 * 1000 + localOffset));
        return tDate;
    }
}
