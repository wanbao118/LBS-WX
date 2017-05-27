package com.group.pbox.pvbs.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.group.pbox.pvbs.clientmodel.user.UserReqModel;
import com.group.pbox.pvbs.model.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml" })
public class UserServiceImplTest
{
    @Resource
    IUserService userService;

    @Test
    public void accountValidTest() throws Exception
    {
        UserReqModel userRequestModel = new UserReqModel();
        userRequestModel.setUserId("0001");
        userRequestModel.setUserPassword("1");
        assertNotNull(userService.accountValid(userRequestModel));
    }
}
