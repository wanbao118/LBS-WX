package com.group.pbox.pvbs.clientmodel.transaction;

import java.util.Date;

public class TransactionRespData
{
    private String accountNum;

    private String id;

    private String accountId;

    private String currencyCode;

    private double balance;

    private Date lastUpatedDate;

    public String getAccountNum()
    {
        return accountNum;
    }

    public void setAccountNum(String accountNum)
    {
        this.accountNum = accountNum;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getCurrencyCode()
    {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode)
    {
        this.currencyCode = currencyCode;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public Date getLastUpatedDate()
    {
        return lastUpatedDate;
    }

    public void setLastUpatedDate(Date lastUpatedDate)
    {
        this.lastUpatedDate = lastUpatedDate;
    }
}
