package com.group.pbox.pvbs.clientmodel.user;

import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class UserRespModel extends BaseClientRespModel{
    private List listData;

	public List getListData() {
		return listData;
	}

	public void setListData(List listData) {
		this.listData = listData;
	}
}
