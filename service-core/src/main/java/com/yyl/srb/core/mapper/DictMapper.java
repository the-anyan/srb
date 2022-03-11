package com.yyl.srb.core.mapper;

import com.yyl.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yyl.srb.core.pojo.dto.ExcelDictDTO;

import java.util.List;

/**
 * <p>
 * 数据字典 Mapper 接口
 * </p>
 *
 * @author yyl
 * @since 2022-03-07
 */
public interface DictMapper extends BaseMapper<Dict> {

    /**
     * excel批量插入数据
     * @param list
     */
    void insertBatch(List<ExcelDictDTO> list);
}
