package com.group.pbox.pvbs.persist.acct;

import com.group.pbox.pvbs.model.acct.Account;

public interface AcctMapper
{
    int addAcct(Account acctRequest);
    String fetchAcct();
    int accountValid(Account account);
    
    int fetchAcctStatus(Account account);
    int closeStatus(Account account);
    int accountValidByRealNum(Account account);
	int realAcctNum(Account account);
}
