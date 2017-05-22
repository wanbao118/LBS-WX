package com.group.pbox.pvbs.persist.account;

import com.group.pbox.pvbs.model.account.AccountBalance;

public interface AccountBalanceMapper
{
    AccountBalance getAccountBalance(AccountBalance accountBalance);

    int updateAccountBalance(AccountBalance accountBalance);

    int insertAccountBalance(AccountBalance accountBalance);

}
