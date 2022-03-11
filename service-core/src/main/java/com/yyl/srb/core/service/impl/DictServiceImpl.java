package com.yyl.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.yyl.srb.core.listener.ExcelDictDTOListener;
import com.yyl.srb.core.pojo.entity.Dict;
import com.yyl.srb.core.mapper.DictMapper;
import com.yyl.srb.core.pojo.dto.ExcelDictDTO;
import com.yyl.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author yyl
 * @since 2022-03-07
 */
@Slf4j
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {

    //    此处添加了事务处理，默认情况下rollbackFor = RuntimeException.class
    @Transactional(rollbackFor = {Exception.class})
    @Override
    public void importData(InputStream inputStream) {
        //指定读用哪个class方法，然后读取第一个sheet，文件流自动关闭
        EasyExcel.read(inputStream, ExcelDictDTO.class, new ExcelDictDTOListener(baseMapper)).sheet().doRead();
        log.info("import finished");
    }

    //导出到excel
    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dicts = baseMapper.selectList(null);
        ArrayList<ExcelDictDTO> excelDictList = new ArrayList<>(dicts.size());
        dicts.forEach(dict -> {

            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict, excelDictDTO);
            excelDictList.add(excelDictDTO);

        });
        return excelDictList;
    }
}
