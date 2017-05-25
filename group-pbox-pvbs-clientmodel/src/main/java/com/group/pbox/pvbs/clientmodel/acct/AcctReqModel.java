package com.group.pbox.pvbs.clientmodel.acct;

import com.group.pbox.pvbs.common.model.BaseClientReqModel;

public class AcctReqModel extends BaseClientReqModel{
	
	private String id;
	
	private String realAccountNumber;
	
	private String clearingCode;
	
	private String branchNumber;
	
	private String accountNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRealAccountNumber() {
		return realAccountNumber;
	}

	public void setRealAccountNumber(String realAccountNumber) {
		this.realAccountNumber = realAccountNumber;
	}

	public String getClearingCode() {
		return clearingCode;
	}

	public void setClearingCode(String clearingCode) {
		this.clearingCode = clearingCode;
	}

	public String getBranchNumber() {
		return branchNumber;
	}

	public void setBranchNumber(String branchNumber) {
		this.branchNumber = branchNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	
}
