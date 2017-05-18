package com.group.pbox.pvbs.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.model.user.User;
import com.group.pbox.pvbs.user.IUserService;
import com.group.pbox.pvbs.util.Response;

@Controller
@RequestMapping("/user")
public class UserController
{
    @Resource
    IUserService userService;

    @RequestMapping("/loginCheck")
    @ResponseBody
    public Object loginCheck(final HttpServletRequest request,
            final HttpServletResponse response, User user)
    {
        Response resp = new Response();
        int result = userService.accountValid(user);
        if (result > 0)
        {
            resp.setResult(200);
        }
        else
        {
            resp.setResult(203);
        }
        return resp;
    }
}
