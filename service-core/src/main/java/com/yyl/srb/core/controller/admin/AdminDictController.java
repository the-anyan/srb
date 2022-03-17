package com.yyl.srb.core.controller.admin;

import com.alibaba.excel.EasyExcel;
import com.yyl.common.exception.BusinessException;
import com.yyl.common.result.R;
import com.yyl.common.result.ResponseEnum;
import com.yyl.srb.core.pojo.dto.ExcelDictDTO;
import com.yyl.srb.core.pojo.entity.Dict;
import com.yyl.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Api(tags = "数据字典管理")
@RestController
@RequestMapping("/admin/core/dict")
@Slf4j
@CrossOrigin
public class AdminDictController {

    @Resource
    private DictService dictService;

    @ApiOperation("根据上级id获取子节点数据列表")
    @GetMapping("/listByParentId/{parentId}")
    public R listByParentId(@ApiParam(value = "上级节点id", required = true)
                            @RequestParam Long parentId) {
        List<Dict> dicts = dictService.listByParentId(parentId);
        return R.ok().data("dictList",dicts);

    }


    @ApiOperation("Excel批量导入数据字典")
    @PostMapping("/import")
    public R batchImport(
            @ApiParam(value = "Excel文件", required = true)
            @RequestParam("file") MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            dictService.importData(inputStream);
            return R.ok().message("批量导入成功");
        } catch (Exception e) {
            throw new BusinessException(ResponseEnum.UPLOAD_ERROR, e);
        }
    }

    @ApiOperation("Excel 数据导出")
    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        try {
            //swagger可能会有问题
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            // 这里URLEncoder.encode可以防止中文乱码 和easyexcel没有关系
            String fileName = URLEncoder.encode("mydict", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            EasyExcel.write(response.getOutputStream(), ExcelDictDTO.class).sheet("数据字典").
                    doWrite(dictService.listDictData());
        } catch (IOException e) {
            throw new BusinessException(ResponseEnum.EXPORT_DATA_ERROR,e);
        }
    }
}
