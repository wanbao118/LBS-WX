package com.group.pbox.pvbs.model.termdeposit;

import java.math.BigDecimal;

public class TermDepositRate {
	
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
