package com.yyl.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

/**
 *定义返回结果json的统一格式
 */
@Getter
@AllArgsConstructor
@ToString
public enum ResponseEnum {

    Success(0,"成功"),
    Error(-1,"服务器内部错误"),
    BAD_SQL_GRAMMAR_ERROR(-101,"sql语法错误" ),
    BORROW_AMOUNT_NULL_ERROR(-201, "借款额度不能为空"),
    SERVLET_ERROR(-102, "servlet请求异常"),
    UPLOAD_ERROR(-103, "文件上传错误"),
    EXPORT_DATA_ERROR(104, "数据导出失败"),

    ALIYUN_RESPONSE_FAIL(-501,"阿里云响应失败"),
    ALIYUN_SMS_LIMIT_CONTROL_ERROR(-502,"短信发送过于频繁"),
    ALIYUN_SMS_ERROR(-503,"短信发送失败"),

    MOBILE_NULL_ERROR(-202,"手机号不能为空"),
    MOBILE_ERROR(-203,"手机号不正确"),
    ;
    //相应状态码
    private Integer code;
    //响应信息
    private String message;
}
