package com.group.pbox.pvbs.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.util.ErrorCode;

public class PageInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String requestUri = request.getRequestURI();
        boolean noFilter = noFilter(requestUri);

        if (noFilter == false)
        {
            final String emp = (String) request.getSession().getAttribute("userId");
            if (null != emp)
            {
                return true;
            }
            response.setContentType("text/json; charset=utf-8");
            UserRespModel userResp = new UserRespModel();
            userResp.setResult(ErrorCode.RESPONSE_ERROR);
            userResp.getErrorCode().add(ErrorCode.SESSION_NOT_FOUND);
            return false;
        }

        return true;
    }

    public boolean noFilter(String requestUri)
    {
        if (requestUri.indexOf("/service/user/loginCheck") > -1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
