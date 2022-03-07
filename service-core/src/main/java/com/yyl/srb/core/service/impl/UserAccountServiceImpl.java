package com.yyl.srb.core.service.impl;

import com.yyl.srb.core.mapper.UserAccountMapper;
import com.yyl.srb.core.pojo.entity.UserAccount;
import com.yyl.srb.core.service.UserAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户账户 服务实现类
 * </p>
 *
 * @author yyl
 * @since 2022-03-07
 */
@Service
public class UserAccountServiceImpl extends ServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

}
