package com.group.pbox.pvbs.sysconf;

import java.util.List;

import com.group.pbox.pvbs.model.sysConf.SysConf;

public interface ISysConfService
{
    public List<SysConf> getAllSysConf();

    public List<SysConf> getAllSysConfByParam(SysConf sysConf);
}
