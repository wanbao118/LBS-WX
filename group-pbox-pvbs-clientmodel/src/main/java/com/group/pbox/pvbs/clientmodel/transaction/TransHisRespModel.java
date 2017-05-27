package com.group.pbox.pvbs.clientmodel.transaction;

import java.util.ArrayList;
import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class TransHisRespModel extends BaseClientRespModel{
	private List<TransHisRespData> listData = new ArrayList<TransHisRespData>();

	public List<TransHisRespData> getListData() {
		return listData;
	}

	public void setListData(List<TransHisRespData> listData) {
		this.listData = listData;
	}
}
