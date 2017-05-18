package com.group.pbox.pvbs.model.user;

import java.io.Serializable;
import java.math.BigDecimal;

public class User implements Serializable
{

    /**
     *
     */
    private static final long serialVersionUID = 8921126290222373961L;
    private String id;
    private String userId;
    private String userName;
    private String userPosition;
    private BigDecimal transactionLimit;
    private BigDecimal termDepositeLimit;
    private BigDecimal exchangeRateLimit;
    private String password;

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

    public BigDecimal getTransactionLimit()
    {
        return transactionLimit;
    }

    public void setTransactionLimit(BigDecimal transactionLimit)
    {
        this.transactionLimit = transactionLimit;
    }

    public BigDecimal getTermDepositeLimit()
    {
        return termDepositeLimit;
    }

    public void setTermDepositeLimit(BigDecimal termDepositeLimit)
    {
        this.termDepositeLimit = termDepositeLimit;
    }

    public BigDecimal getExchangeRateLimit()
    {
        return exchangeRateLimit;
    }

    public void setExchangeRateLimit(BigDecimal exchangeRateLimit)
    {
        this.exchangeRateLimit = exchangeRateLimit;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

}
