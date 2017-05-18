package com.group.pbox.pvbs.controller;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.servlet.ModelAndView;

public class UserControllerTest extends JUnitActionBase
{

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
