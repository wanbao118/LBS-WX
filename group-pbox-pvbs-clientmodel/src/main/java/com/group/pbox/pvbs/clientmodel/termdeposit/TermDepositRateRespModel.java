package com.group.pbox.pvbs.clientmodel.termdeposit;

import java.util.ArrayList;
import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class TermDepositRateRespModel  extends BaseClientRespModel {
	private List<TermDepositRateRespData> listData = new ArrayList<TermDepositRateRespData>();

	public List<TermDepositRateRespData> getListData() {
		return listData;
	}

	public void setListData(List<TermDepositRateRespData> listData) {
		this.listData = listData;
	}

}
