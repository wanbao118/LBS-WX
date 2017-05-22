package com.group.pbox.pvbs.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.group.pbox.pvbs.model.user.User;

public class PageInterceptor extends HandlerInterceptorAdapter
{
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception
    {
        String requestUri = request.getRequestURI();

        final User emp = (User) request.getSession().getAttribute("user");
        return true;
    }

    public boolean noFilter(String requestUri)
    {
        if (requestUri.indexOf("service/manage/loginPage") > 0
                || requestUri.indexOf("service/manage/logout") > 0
                || requestUri.indexOf("service/manage/login") > 0
                || requestUri.indexOf("WEB-INF/page") > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
