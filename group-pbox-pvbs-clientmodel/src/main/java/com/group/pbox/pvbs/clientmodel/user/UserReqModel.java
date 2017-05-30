package com.group.pbox.pvbs.clientmodel.user;

import com.group.pbox.pvbs.common.model.BaseClientReqModel;

public class UserReqModel extends BaseClientReqModel {

	private String operationCode;
	private String userId;
	private String userName;
	private String userPosition;
	private Double transactionLimit;
	private Double termDepositeLimit;
	private Double exchangeRateLimit;
	private String userPassword;
	private String userStatus;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPosition() {
		return userPosition;
	}

	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}

	public Double getTransactionLimit() {
		return transactionLimit;
	}

	public void setTransactionLimit(Double transactionLimit) {
		this.transactionLimit = transactionLimit;
	}

	public Double getTermDepositeLimit() {
		return termDepositeLimit;
	}

	public void setTermDepositeLimit(Double termDepositeLimit) {
		this.termDepositeLimit = termDepositeLimit;
	}

	public Double getExchangeRateLimit() {
		return exchangeRateLimit;
	}

	public void setExchangeRateLimit(Double exchangeRateLimit) {
		this.exchangeRateLimit = exchangeRateLimit;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOperationCode() {
		return operationCode;
	}

	public void setOperationCode(String operationCode) {
		this.operationCode = operationCode;
	}
}
