package com.group.pbox.pvbs.clientmodel.sysconf;

import java.util.ArrayList;
import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class SysConfRespModel extends BaseClientRespModel
{
    List<SysConfRespData> listData = new ArrayList<SysConfRespData>();

    public List<SysConfRespData> getListData()
    {
        return listData;
    }

    public void setListData(List<SysConfRespData> listData)
    {
        this.listData = listData;
    }
}
