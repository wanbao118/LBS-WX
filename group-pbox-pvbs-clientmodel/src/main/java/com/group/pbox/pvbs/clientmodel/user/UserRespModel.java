package com.group.pbox.pvbs.clientmodel.user;

import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class UserRespModel extends BaseClientRespModel{
    private List<UserRespData> listData;

	public List<UserRespData> getListData() {
		return listData;
	}

	public void setListData(List<UserRespData> listData) {
		this.listData = listData;
	}
}
