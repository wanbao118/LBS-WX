package com.group.pbox.pvbs.persist.user;

import com.group.pbox.pvbs.model.user.User;

public interface UserMapper
{
    int accountValid(User user);
    boolean addUser(User user);
    int updateUser(User user);
    User fetchUserDetlByUserId(String userId);
}
