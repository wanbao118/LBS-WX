package com.group.pbox.pvbs.persist.acct;

import com.group.pbox.pvbs.model.acct.AccountBalance;

public interface AccountBalanceMapper
{
    AccountBalance getAccountBalance(AccountBalance accountBalance);

    int updateAccountBalance(AccountBalance accountBalance);

    int insertAccountBalance(AccountBalance accountBalance);

}
