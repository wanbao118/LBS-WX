package com.group.pbox.pvbs.controller.sysconf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.clientmodel.sysconf.SysConfReqModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespModel;
import com.group.pbox.pvbs.sysconf.ISysConfService;
import com.group.pbox.pvbs.util.ErrorCode;

@Controller
@RequestMapping("/sysconf")
public class SysConfController
{
    private static final Logger sysLogger = Logger.getLogger("customer");

    @Resource
    ISysConfService sysConfService;

    @RequestMapping(value = "/getSysConfList ", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Object querySysConf(final HttpServletRequest request, final HttpServletResponse response, @RequestBody SysConfReqModel sysConfReqModel)
    {
        SysConfRespModel sysConfRespModel = new SysConfRespModel();
        try
        {
            sysConfRespModel = sysConfService.getAllSysConfByParam(sysConfReqModel);
        }
        catch (Exception e)
        {
            sysConfRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            List<String> errorList = new ArrayList<String>();
            errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
            sysConfRespModel.setErrorCode(errorList);
        }
        return sysConfRespModel;
    }
}
