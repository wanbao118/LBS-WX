package com.group.pbox.pvbs.clientmodel.ccyex;

import java.util.ArrayList;
import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class CcyExchangeRateRespModel extends BaseClientRespModel {
	
	private List<CcyExchangeRateRespData> listData = new ArrayList<CcyExchangeRateRespData>();

	public List<CcyExchangeRateRespData> getListData() {
		return listData;
	}

	public void setListData(List<CcyExchangeRateRespData> listData) {
		this.listData = listData;
	}

}
