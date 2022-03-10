package com.yyl.srb.core.service;

import com.yyl.srb.core.pojo.dto.ExcelDictDTO;
import com.yyl.srb.core.pojo.entity.Dict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 数据字典 服务类
 * </p>
 *
 * @author yyl
 * @since 2022-03-07
 */
public interface DictService extends IService<Dict> {

    //excel上传文件
    void importData(InputStream inputStream);
    //excel下载文件
    List<ExcelDictDTO> listDictData();

}
