package com.shen.service.impl;

import com.shen.exception.BizException;
import com.shen.exception.UserNotFoundException;
import com.shen.httpresult.HttpResultCodeEnum;
import com.shen.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public void queryUser() {
//        throw new UserNotFoundException();
        throw new BizException(HttpResultCodeEnum.USER_NOT_FOUND);
    }
}
