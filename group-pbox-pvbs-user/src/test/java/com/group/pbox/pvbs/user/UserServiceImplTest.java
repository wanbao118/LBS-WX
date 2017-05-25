package com.group.pbox.pvbs.user;

import static org.junit.Assert.assertEquals;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.group.pbox.pvbs.model.user.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml" })
public class UserServiceImplTest
{
    @Resource
    IUserService userService;

    @Test
    public void accountValidTest()
    {
        User user = new User();
        user.setUserId("0001");
        user.setUserPassword("1");
        assertEquals(userService.accountValid(user), 1);
    }
}
