package com.group.pbox.pvbs.user;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.model.user.User;
import com.group.pbox.pvbs.persist.user.UserMapper;

@Service
public class UserServiceImpl implements IUserService
{
    @Resource
    UserMapper userMapper;

    public int accountValid(User user)
    {
        return userMapper.accountValid(user);
    }

}
