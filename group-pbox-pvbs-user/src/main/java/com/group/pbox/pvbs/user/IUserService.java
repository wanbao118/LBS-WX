package com.group.pbox.pvbs.user;

import java.util.List;

import com.group.pbox.pvbs.clientmodel.user.UserReqModel;
import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.model.user.User;

public interface IUserService
{
    int accountValid(User user);
    UserRespModel addUser(UserReqModel userReqModel);
    UserRespModel updateUser(UserReqModel userReqModel);
    int deleteUser(String userId);
    User queryUserDetail(String userId);
    List<User> fetchAllUser();
    UserRespModel fetchUserByUserId(UserReqModel userReqModel);
}
