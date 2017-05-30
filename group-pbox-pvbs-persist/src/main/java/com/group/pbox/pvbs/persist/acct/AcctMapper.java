package com.group.pbox.pvbs.persist.acct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
    Account getAccountInfo(Account account);
    List<Account> getAcctInfoByRealNum(@Param("realAccountNumber")String RealAccountNumber);
}
