package com.yyl.srb.sms.service.impl;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.Gson;
import com.yyl.srb.sms.util.SmsProperties;
import com.yyl.srb.sms.service.SmsService;
import com.yyl.common.exception.Assert;
import com.yyl.common.exception.BusinessException;
import com.yyl.common.result.ResponseEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void send(String mobile, String templateCode, Map<String, Object> param) {
        //创建远程连接客户端对象
        DefaultProfile profile = DefaultProfile.getProfile(
                SmsProperties.REGION_Id,
                SmsProperties.KEY_ID,
                SmsProperties.KEY_SECRET);
        IAcsClient client = new DefaultAcsClient(profile);

        //创建远程连接的请求参数
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.putQueryParameter("RegionId",SmsProperties.REGION_Id);
        request.putQueryParameter("PhoneNumbers",mobile);
        request.putQueryParameter("SignName",SmsProperties.SIGN_NAME);
        request.putQueryParameter("TemplateCode",templateCode);

        Gson gson = new Gson();
        String json = gson.toJson(param);
        request.putQueryParameter("TemplateParm",json);

        try {
            //使用客户端对象携带请求对象发送请求并得到响应结果
            CommonResponse response = client.getCommonResponse(request);
            boolean success = response.getHttpResponse().isSuccess();

            //-501 响应失败
            Assert.isTrue(success, ResponseEnum.ALIYUN_RESPONSE_FAIL);

            String data = response.getData();
            HashMap<String,String> resultMap = gson.fromJson(data, HashMap.class);
            String code = resultMap.get("Code");
            String message = resultMap.get("Message");
            log.info("阿里云短信发送结果：");
            log.info("code:" + code);
            log.info("message:" + message);
            //-502,短信发送频繁// 业务限流
            Assert.equals("isv.BUSINESS_LIMIT_CONTROL",code,ResponseEnum.ALIYUN_SMS_LIMIT_CONTROL_ERROR);
            //-503 短信发送失败   //其他失败
            Assert.equals("OK",code,ResponseEnum.ALIYUN_SMS_ERROR);


        } catch (ServerException e) {
            log.error("阿里云短信发送SDK调用失败：");
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
            throw  new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR,e);
//            e.printStackTrace();
        } catch (ClientException e) {
            log.error("阿里云短信发送SDK调用失败：");
            log.error("ErrorCode=" + e.getErrCode());
            log.error("ErrorMessage=" + e.getErrMsg());
            throw  new BusinessException(ResponseEnum.ALIYUN_SMS_ERROR,e);
        }


    }
}
