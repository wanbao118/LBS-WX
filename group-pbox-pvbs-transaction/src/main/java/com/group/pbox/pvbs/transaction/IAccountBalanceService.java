package com.group.pbox.pvbs.transaction;

import com.group.pbox.pvbs.model.account.AccountBalance;

public interface IAccountBalanceService
{
    public String deposit(AccountBalance accountBalance);

    public String withDrawal(AccountBalance accountBalance);
}
