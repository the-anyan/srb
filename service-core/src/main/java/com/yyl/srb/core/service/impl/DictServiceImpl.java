package com.yyl.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yyl.srb.core.listener.ExcelDictDTOListener;
import com.yyl.srb.core.pojo.entity.Dict;
import com.yyl.srb.core.mapper.DictMapper;
import com.yyl.srb.core.pojo.dto.ExcelDictDTO;
import com.yyl.srb.core.service.DictService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
    @Resource RedisTemplate redisTemplate;

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

    //根据父节点获取子节点列表
    @Override
    public List<Dict> listByParentId(Long parentId) {

        //先查询redis中是否存在数据列表
        List<Dict> dictList = null;
        try {
            dictList = (List<Dict>) redisTemplate.opsForValue().get("srb:core:dictList:" + parentId);
            if (dictList != null){
                log.info("从Redis中取值");
                return dictList;
            }
        }catch (Exception e){
            log.error("Redis服务异常："+ e.getStackTrace());//此处不抛出异常，继续执行后面的代码
        }
        log.info("从数据库中取值");

        dictList = baseMapper.selectList(new QueryWrapper<Dict>().eq("parent_id", parentId));
        dictList.forEach(dict -> {
            //如果有子节点，则非叶子结点，表示此结点有叶子节点
            dict.setHasChildren(this.hasChildren(parentId));

        });

        //将数据传入Redis
        try{
            redisTemplate.opsForValue().set("srb:score:dictList:"+parentId,dictList,5, TimeUnit.MINUTES);
            log.info("数据存入Redis");
        } catch (Exception e){
            log.error("Redis服务异常："+ e.getStackTrace());//此处不抛出异常，继续执行后面的代码

        }
        return dictList;
    }

    /**
     * 判断节点是否有子节点
     */
    private Boolean hasChildren(Long id) {

        QueryWrapper<Dict> query = new QueryWrapper<Dict>().eq("parent_id", id);
        Integer integer = baseMapper.selectCount(query);
        if (integer.intValue() > 0) {
            return true;
        }
        return false;
    }
}
