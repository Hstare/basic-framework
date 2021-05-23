package com.hew.basicframework.entity;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.alibaba.fastjson.JSON;
import com.hew.basicframework.mapper.ExcelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * 不被spring管理，要每次读取excel都要new
 *
 * @author HeXiaoWei
 * @date 2020/10/17 16:23
 */
public class ExcelDataListener<T> extends AnalysisEventListener<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelDataListener.class);
    /**
     * 每隔1000条存储数据库，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 1000;
    List<T> list = new ArrayList();

    @Autowired
    ExcelMapper<T> excelMapper;

    public ExcelDataListener(ExcelMapper excelMapper) {
        this.excelMapper = excelMapper;
    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(data));
        list.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        LOGGER.info("所有数据解析完成！");
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) {
        LOGGER.error("解析失败，但是继续解析下一行:{}", exception.getMessage());
        // 如果是某一个单元格的转换异常 能获取到具体行号
        // 如果要获取头的信息 配合invokeHeadMap使用
        if (exception instanceof ExcelDataConvertException) {
            ExcelDataConvertException excelDataConvertException = (ExcelDataConvertException) exception;
            Integer rowIndex = excelDataConvertException.getRowIndex();
            Integer columnIndex = excelDataConvertException.getColumnIndex();
            LOGGER.error("第{}行，第{}列解析异常", rowIndex, columnIndex);
            throw new ExcelDataConvertException(rowIndex, columnIndex, null, null, "第" + rowIndex + "行，第" + (columnIndex + 1) + "列数据解析异常", exception);
        } else {
            throw new RuntimeException("Excel 数据转换异常");
        }
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        excelMapper.save(list);
        LOGGER.info("存储数据库成功！");
    }
}
