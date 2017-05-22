package com.group.pbox.pvbs.acct.model;

import com.group.pbox.pvbs.model.acct.Account;

public class AcctRequest extends Account{
	
	private String acctflag;

	public String getAcctflag() {
		return acctflag;
	}

	public void setAcctflag(String acctflag) {
		this.acctflag = acctflag;
	}
}
