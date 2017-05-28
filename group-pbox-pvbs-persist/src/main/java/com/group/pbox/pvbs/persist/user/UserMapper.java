package com.group.pbox.pvbs.persist.user;

import java.util.List;

import com.group.pbox.pvbs.model.user.User;

public interface UserMapper
{
    User accountValid(User user);

    boolean addUser(User user);

    int updateUser(User user);

    User fetchUserDetlByUserId(String userId);

    List<User> queryUserByParams(User user);

    List<User> queryUserByParamsCount(User user);
}
