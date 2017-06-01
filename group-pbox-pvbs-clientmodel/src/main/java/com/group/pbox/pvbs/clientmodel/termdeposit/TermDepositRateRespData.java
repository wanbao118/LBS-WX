package com.group.pbox.pvbs.clientmodel.termdeposit;

import java.math.BigDecimal;

public class TermDepositRateRespData {
	
	private String id;
	
	private String termDeposiPeriod;
	
	private double termDeposiInterestRate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTermDeposiPeriod() {
		return termDeposiPeriod;
	}

	public void setTermDeposiPeriod(String termDeposiPeriod) {
		this.termDeposiPeriod = termDeposiPeriod;
	}

	public double getTermDeposiInterestRate() {
		return termDeposiInterestRate;
	}

	public void setTermDeposiInterestRate(double termDeposiInterestRate) {
		this.termDeposiInterestRate = termDeposiInterestRate;
	}
}
