package com.group.pbox.pvbs.sysconf;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.group.pbox.pvbs.model.sysConf.SysConf;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml" })
public class SysConfServiceImplTest
{
    @Resource
    ISysConfService sysConfService;

    @Test
    public void getAllSysConf()
    {
        List<SysConf> list = sysConfService.getAllSysConf();
        assertNotNull(list);
    }

    @Test
    public void getSysConfByParams()
    {
        SysConf param = new SysConf();
        param.setItem("Support_Ccy");
        List<SysConf> list = sysConfService.getAllSysConfByParam(param);
        assertNotNull(list);
    }
}
