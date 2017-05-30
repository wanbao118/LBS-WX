package com.group.pbox.pvbs.clientmodel.acct;

import java.util.List;

import com.group.pbox.pvbs.common.model.BaseClientRespModel;

public class AcctRespModel extends BaseClientRespModel
{
	private List<AcctRespData> acctData;
	
	private List listData;

	public List getListData() {
		return listData;
	}

	public void setListData(List listData) {
		this.listData = listData;
	}

	public List<AcctRespData> getAcctData() {
		return acctData;
	}

	public void setAcctData(List<AcctRespData> acctData) {
		this.acctData = acctData;
	}
	
}
