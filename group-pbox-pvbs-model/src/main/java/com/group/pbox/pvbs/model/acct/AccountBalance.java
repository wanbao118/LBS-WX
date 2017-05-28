package com.group.pbox.pvbs.model.acct;

import java.util.Date;

import com.group.pbox.pvbs.common.model.BasePage;

public class AccountBalance extends BasePage
{
    private String accountNum;

    private String id;

    private String accountId;

    private String currencyCode;

    private double balance;

    private Date lastUpatedDate;

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

    public Date getLastUpatedDate()
    {
        return lastUpatedDate;
    }

    public void setLastUpatedDate(Date lastUpatedDate)
    {
        this.lastUpatedDate = lastUpatedDate;
    }

    public double getBalance()
    {
        return balance;
    }

    public void setBalance(double balance)
    {
        this.balance = balance;
    }

    public String getAccountNum()
    {
        return accountNum;
    }

    public void setAccountNum(String accountNum)
    {
        this.accountNum = accountNum;
    }
}
