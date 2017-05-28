package com.group.pbox.pvbs.model.user;

import java.io.Serializable;

import com.group.pbox.pvbs.common.model.BasePage;

public class User extends BasePage implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 8921126290222373961L;
    private String id;
    private String userId;
    private String userName;
    private String userPosition;
    private Double transactionLimit;
    private Double termDepositeLimit;
    private Double exchangeRateLimit;
    private String userPassword;
    private String userStatus;

    public String getUserStatus()
    {
        return userStatus;
    }

    public void setUserStatus(String userStatus)
    {
        this.userStatus = userStatus;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserPosition()
    {
        return userPosition;
    }

    public void setUserPosition(String userPosition)
    {
        this.userPosition = userPosition;
    }

    public Double getTransactionLimit()
    {
        return transactionLimit;
    }

    public void setTransactionLimit(Double transactionLimit)
    {
        this.transactionLimit = transactionLimit;
    }

    public Double getTermDepositeLimit()
    {
        return termDepositeLimit;
    }

    public void setTermDepositeLimit(Double termDepositeLimit)
    {
        this.termDepositeLimit = termDepositeLimit;
    }

    public Double getExchangeRateLimit()
    {
        return exchangeRateLimit;
    }

    public void setExchangeRateLimit(Double exchangeRateLimit)
    {
        this.exchangeRateLimit = exchangeRateLimit;
    }

    public String getUserPassword()
    {
        return userPassword;
    }

    public void setUserPassword(String userPassword)
    {
        this.userPassword = userPassword;
    }

}
