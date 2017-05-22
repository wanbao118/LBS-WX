package com.group.pbox.pvbs.acct;

import com.group.pbox.pvbs.acct.model.AcctRequest;

public interface IAcctCreationService
{
    int addAcct(AcctRequest acctRequest);
    Integer fetchAcct();
}
