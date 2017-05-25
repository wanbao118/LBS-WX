package com.group.pbox.pvbs.clientmodel.transaction;

import com.group.pbox.pvbs.common.model.BaseClientReqModel;

public class TransactionReqModel extends BaseClientReqModel
{
    private String accountNumber;
    private String currency;
    private double amount;

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getCurrency()
    {
        return currency;
    }

    public void setCurrency(String currency)
    {
        this.currency = currency;
    }

    public double getAmount()
    {
        return amount;
    }

    public void setAmount(double amount)
    {
        this.amount = amount;
    }
}
