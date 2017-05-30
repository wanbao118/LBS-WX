package com.group.pbox.pvbs.clientmodel.ccyex;

import java.math.BigDecimal;

import com.group.pbox.pvbs.common.model.BaseClientReqModel;

public class CcyExchangeRateReqModel extends BaseClientReqModel {
	private String userId;

	private String id;
	
	private String currencyCode;
	
	private BigDecimal exchangeRate;

	private String acctNumber;

	private double changeAmount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public String getAcctNumber() {
		return acctNumber;
	}

	public void setAcctNumber(String acctNumber) {
		this.acctNumber = acctNumber;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(double changeAmount) {
		this.changeAmount = changeAmount;
	}

}
