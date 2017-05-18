package com.group.pbox.pvbs.model.account;

import java.math.BigDecimal;
import java.util.Date;

public class AccountBalance {
	
	private String id;
	
	private String accountId;
	
	private String currencyCode;
	
	private BigDecimal balance;
	
	private Date lastUpatedDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Date getLastUpatedDate() {
		return lastUpatedDate;
	}

	public void setLastUpatedDate(Date lastUpatedDate) {
		this.lastUpatedDate = lastUpatedDate;
	}
}
