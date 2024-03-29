package com.yyl.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.yyl.srb.core.mapper.DictMapper;
import com.yyl.srb.core.pojo.dto.ExcelDictDTO;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j//日志，lombok可以直接打印log
//@AllArgsConstructor//全参
@NoArgsConstructor//无参
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO> {

    /**
     * 没隔5条存储数据库，实际使用中可以3000条，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 5;
    List<ExcelDictDTO> list = new ArrayList();

    private DictMapper dictMapper;

    //传入mapper对象
    public ExcelDictDTOListener(DictMapper dictMapper){
        this.dictMapper = dictMapper;
    }

    /**
     * 遍历每一行的记录
     * @param data
     * @param context
     */

    @Override
    public void invoke(ExcelDictDTO data, AnalysisContext context) {
        log.info("解析到一条记录:{}",data);
        list.add(data);
        if (list.size() >= BATCH_COUNT){
            saveData();
            //存储完成，清理list
            list.clear();

        }

    }

    /**
     * 数据解析完成调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
//保存数据，确保最后遗留的数据也存储进去
        saveData();
        log.info("所有数据解析完成");
    }

    private void saveData(){
        log.info("{}条数据，开始存储数据库",list.size());
        dictMapper.insertBatch(list);  //批量插入
        log.info("存储数据库成功！");
    }
}
