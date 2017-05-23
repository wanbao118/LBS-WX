package com.group.pbox.pvbs.sysconf;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.model.sysConf.SysConf;
import com.group.pbox.pvbs.persist.sysconf.SysConfMap;

@Service
public class SysConfServiceImpl implements ISysConfService
{
    @Resource
    SysConfMap sysConfMap;

    public List<SysConf> getAllSysConf()
    {

        return sysConfMap.getAllSysConf();
    }

    public List<SysConf> getAllSysConfByParam(SysConf sysConf)
    {
        return sysConfMap.getAllSysConfByParam(sysConf);
    }

}
