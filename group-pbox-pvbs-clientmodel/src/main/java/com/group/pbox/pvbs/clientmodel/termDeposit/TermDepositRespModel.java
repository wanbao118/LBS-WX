package com.group.pbox.pvbs.clientmodel.termDeposit;

import java.util.ArrayList;
import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class TermDepositRespModel extends BaseClientRespModel {
	private List<TermDepositRespData> listData = new ArrayList<TermDepositRespData>();

	public List<TermDepositRespData> getListData() {
		return listData;
	}

	public void setListData(List<TermDepositRespData> listData) {
		this.listData = listData;
	}

}
