package com.group.pbox.pvbs.persist.sysconf;

import java.util.List;

import com.group.pbox.pvbs.model.sysconf.SysConf;

public interface SysConfMap
{
    public List<SysConf> getAllSysConf();

    public List<SysConf> getAllSysConfByParam(SysConf sysConf);
}
