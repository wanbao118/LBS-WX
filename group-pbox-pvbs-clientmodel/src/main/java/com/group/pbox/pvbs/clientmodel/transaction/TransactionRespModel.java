package com.group.pbox.pvbs.clientmodel.transaction;

import java.util.ArrayList;
import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class TransactionRespModel extends BaseClientRespModel
{
    private List<TransactionRespData> listData = new ArrayList<TransactionRespData>();

    public List<TransactionRespData> getListData()
    {
        return listData;
    }

    public void setListData(List<TransactionRespData> listData)
    {
        this.listData = listData;
    }

}
