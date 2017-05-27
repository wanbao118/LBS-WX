package com.group.pbox.pvbs.clientmodel.acct;

import java.util.Date;

import com.group.pbox.pvbs.common.model.BaseClientReqModel;

public class AcctReqModel extends BaseClientReqModel{
	
	private String realAccountNumber;
	
	private String clearingCode;
	
	private String branchNumber;
	
	private String accountNumber;

	private String accountId;

    private String customerName;

    private String customerId;

    private String dateOfBirth;

    private String address;

    private String contactAddress;

    private String contactNumber;

    private String wechatId;

    private String employment;
    
	private String currencyCode;
	
	private double balance;
	
	private Date lastUpdateDate;

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

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	
}
