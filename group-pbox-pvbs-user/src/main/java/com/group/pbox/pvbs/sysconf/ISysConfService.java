package com.group.pbox.pvbs.sysconf;

import com.group.pbox.pvbs.clientmodel.sysconf.SysConfReqModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespModel;

public interface ISysConfService
{
    public SysConfRespModel getAllSysConf() throws Exception;

    public SysConfRespModel getAllSysConfByParam(SysConfReqModel reqModel)
            throws Exception;
}
