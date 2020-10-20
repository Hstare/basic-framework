package com.hew.basicframework.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.hew.basicframework.entity.ExcelDataListener;
import com.hew.basicframework.mapper.ExcelMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author HeXiaoWei
 * @date 2020/10/17 16:08
 */
public class ExcelUtils {

    public static void read(InputStream is, Class head, ExcelMapper excelMapper) {
        EasyExcel.read(is, head, new ExcelDataListener(excelMapper))
                .sheet().doRead();
    }

    public static void write(String fileName, Class head, String sheetName, List data) throws IOException {
        HttpServletResponse response = HttpUtils.getResponse();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
        fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ExcelTypeEnum.XLSX.getValue());
        EasyExcel.write(response.getOutputStream(), head).sheet(sheetName).doWrite(data);

    }
}
