package com.yyl.srb.core.service.impl;

import com.yyl.srb.core.mapper.UserInfoMapper;
import com.yyl.srb.core.pojo.entity.UserInfo;
import com.yyl.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author yyl
 * @since 2022-03-07
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
