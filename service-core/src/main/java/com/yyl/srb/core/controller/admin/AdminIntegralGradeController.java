package com.yyl.srb.core.controller.admin;

import com.yyl.common.exception.Assert;
import com.yyl.common.result.R;
import com.yyl.common.result.ResponseEnum;
import com.yyl.srb.core.pojo.entity.IntegralGrade;
import com.yyl.srb.core.service.IntegralGradeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 积分等级管理接口
 */
@CrossOrigin
@RestController
@RequestMapping("/admin/core/integralGrade")
@Api(tags = "积分等级管理")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;

    @GetMapping("/list")
    @ApiOperation("积分等级列表")
    public R listAll() {
//        log.info("hi i'm info");
//        log.warn("warning!!!");
//        log.error("it's a error");
        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list", list);
    }

    @DeleteMapping("/remove/{id}")
    @ApiOperation(value = "根据id删除积分等级", notes = "逻辑删除")
    public R removeById(
            @ApiParam(value = "数据ID", required = true, example = "100")
            @PathVariable Long id) {
        boolean result = integralGradeService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("删除失败");
        }
    }

//        @DeleteMapping("/remove/{id}")
//        @ApiOperation(value = "根据id删除积分等级", notes = "逻辑删除")
//        public Boolean removeById(@PathVariable Long id){
//        return integralGradeService.removeById(id);
//        }

    @ApiOperation("新增积分等级")
    @PostMapping("/save")
    public R save(
            @ApiParam(value = "积分等级对象", required = true)
            @RequestBody IntegralGrade integralGrade) {
        //如果借款额度为空就手动抛出一个自定义的异常！
//        if (integralGrade.getBorrowAmount() == null){
//            throw new BusinessException(ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
//        }
        Assert.notNull(integralGrade.getBorrowAmount(), ResponseEnum.BORROW_AMOUNT_NULL_ERROR);
        boolean save = integralGradeService.save(integralGrade);
        if (save){
            return R.ok().message("保存成功");
        }else {
            return R.error().message("保存失败");
        }
    }
}
