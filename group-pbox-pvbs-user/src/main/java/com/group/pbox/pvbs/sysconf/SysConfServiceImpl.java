package com.group.pbox.pvbs.sysconf;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.sysconf.SysConfReqModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespData;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespModel;
import com.group.pbox.pvbs.model.sysconf.SysConf;
import com.group.pbox.pvbs.persist.sysconf.SysConfMap;
import com.group.pbox.pvbs.util.ErrorCode;

@Service
public class SysConfServiceImpl implements ISysConfService
{
    @Resource
    SysConfMap sysConfMap;

    public SysConfRespModel getAllSysConf() throws Exception
    {
        List<SysConf> listData = sysConfMap.getAllSysConf();
        SysConfRespModel sysConfRespModel = new SysConfRespModel();
        sysConfRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        List<SysConfRespData> listRespData = new ArrayList<SysConfRespData>();
        for (SysConf sysConf : listData)
        {
            SysConfRespData sysConfRespData = new SysConfRespData();
            sysConfRespData.setId(sysConf.getId());
            sysConfRespData.setItem(sysConf.getItem());
            sysConfRespData.setRemark(sysConf.getRemark());
            sysConfRespData.setSort(sysConf.getSort());
            sysConfRespData.setValue(sysConf.getValue());
            listRespData.add(sysConfRespData);
        }
        sysConfRespModel.setListData(listRespData);
        return sysConfRespModel;
    }

    public SysConfRespModel getAllSysConfByParam(SysConfReqModel reqModel) throws Exception
    {
        SysConf sysConf = new SysConf();
        sysConf.setItem(reqModel.getItem());
        List<SysConf> listData = sysConfMap.getAllSysConfByParam(sysConf);
        List<SysConfRespData> listRespData = new ArrayList<SysConfRespData>();
        for (SysConf tmp : listData)
        {
            SysConfRespData sysConfRespData = new SysConfRespData();
            sysConfRespData.setId(tmp.getId());
            sysConfRespData.setItem(tmp.getItem());
            sysConfRespData.setRemark(tmp.getRemark());
            sysConfRespData.setSort(tmp.getSort());
            sysConfRespData.setValue(tmp.getValue());
            listRespData.add(sysConfRespData);
        }
        SysConfRespModel sysConfRespModel = new SysConfRespModel();
        sysConfRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        sysConfRespModel.setListData(listRespData);
        return sysConfRespModel;
    }

}
