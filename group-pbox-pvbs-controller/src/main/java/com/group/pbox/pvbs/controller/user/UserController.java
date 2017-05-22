package com.group.pbox.pvbs.controller.user;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.controller.model.BaseResponseModel;
import com.group.pbox.pvbs.model.user.User;
import com.group.pbox.pvbs.user.IUserService;
import com.group.pbox.pvbs.util.ErrorCode;

@Controller
@RequestMapping("/user")
public class UserController
{
    @Resource
    IUserService userService;

    @RequestMapping(value = "/loginCheck", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Object loginCheck(final HttpServletRequest request,
            final HttpServletResponse response, @RequestBody User user)
    {
        BaseResponseModel resp = new BaseResponseModel();
        int result = userService.accountValid(user);
        if (result > 0)
        {
            resp.setResult(ErrorCode.RESPONSE_SUCCESS);
        }
        else
        {
            resp.setResult(ErrorCode.RESPONSE_ERROR);
        }
        return resp;
    }
}
