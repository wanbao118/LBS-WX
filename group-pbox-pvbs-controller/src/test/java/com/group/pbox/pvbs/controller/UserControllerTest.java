package com.group.pbox.pvbs.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml", })
public class UserControllerTest extends JUnitActionBase
{

    @Test
    public void userLoginTest() throws Exception
    {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.setServletPath("/user/loginCheck");
        request.addParameter("userId", "0001");
        request.addParameter("password", "1");
        request.setMethod("post");
        final ModelAndView mav = this.excuteAction(request, response);

    }
}
