package com.group.pbox.pvbs.model.termDeposit;

import java.math.BigDecimal;
import java.util.Date;

public class TermDepositMaster {

	private String id;
	
	private String accountId;
	
	private String depositNumber;
	
	private BigDecimal depositAmount;
	
	private String termPeriod;
	
	private BigDecimal termInterestRate;
	
	private Date maturityDate;
	
	private BigDecimal maturityInterest;
	
	private BigDecimal maturityAmount;

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

	public String getDepositNumber() {
		return depositNumber;
	}

	public void setDepositNumber(String depositNumber) {
		this.depositNumber = depositNumber;
	}

	public BigDecimal getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(BigDecimal depositAmount) {
		this.depositAmount = depositAmount;
	}

	public String getTermPeriod() {
		return termPeriod;
	}

	public void setTermPeriod(String termPeriod) {
		this.termPeriod = termPeriod;
	}

	public BigDecimal getTermInterestRate() {
		return termInterestRate;
	}

	public void setTermInterestRate(BigDecimal termInterestRate) {
		this.termInterestRate = termInterestRate;
	}

	public Date getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(Date maturityDate) {
		this.maturityDate = maturityDate;
	}

	public BigDecimal getMaturityInterest() {
		return maturityInterest;
	}

	public void setMaturityInterest(BigDecimal maturityInterest) {
		this.maturityInterest = maturityInterest;
	}

	public BigDecimal getMaturityAmount() {
		return maturityAmount;
	}

	public void setMaturityAmount(BigDecimal maturityAmount) {
		this.maturityAmount = maturityAmount;
	}

}
