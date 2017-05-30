package com.group.pbox.pvbs.clientmodel.termdeposit;

import java.util.Date;

import com.group.pbox.pvbs.common.model.BaseClientReqModel;

public class TermDepositReqModel extends BaseClientReqModel
{
    private String transAccountNum;
    private String accountNumber;
    private String accountId;
    private String depositNumber;
    private double depositAmount;
    private String termPeriod;
    private double termInterestRate;
    private Date maturityDate;
    private double maturityInterset;
    private double maturityAmount;
    private String maturityStatus;
    private String ccy;

    public String getAccountNumber()
    {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber)
    {
        this.accountNumber = accountNumber;
    }

    public String getDepositNumber()
    {
        return depositNumber;
    }

    public void setDepositNumber(String depositNumber)
    {
        this.depositNumber = depositNumber;
    }

    public double getDepositAmount()
    {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount)
    {
        this.depositAmount = depositAmount;
    }

    public String getTermPeriod()
    {
        return termPeriod;
    }

    public void setTermPeriod(String termPeriod)
    {
        this.termPeriod = termPeriod;
    }

    public double getTermInterestRate()
    {
        return termInterestRate;
    }

    public void setTermInterestRate(double termInterestRate)
    {
        this.termInterestRate = termInterestRate;
    }

    public Date getMaturityDate()
    {
        return maturityDate;
    }

    public void setMaturityDate(Date maturityDate)
    {
        this.maturityDate = maturityDate;
    }

    public double getMaturityInterset()
    {
        return maturityInterset;
    }

    public void setMaturityInterset(double maturityInterset)
    {
        this.maturityInterset = maturityInterset;
    }

    public double getMaturityAmount()
    {
        return maturityAmount;
    }

    public void setMaturityAmount(double maturityAmount)
    {
        this.maturityAmount = maturityAmount;
    }

    public String getMaturityStatus()
    {
        return maturityStatus;
    }

    public void setMaturityStatus(String maturityStatus)
    {
        this.maturityStatus = maturityStatus;
    }

    public String getCcy()
    {
        return ccy;
    }

    public void setCcy(String ccy)
    {
        this.ccy = ccy;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getTransAccountNum()
    {
        return transAccountNum;
    }

    public void setTransAccountNum(String transAccountNum)
    {
        this.transAccountNum = transAccountNum;
    }

}
