package com.group.pbox.pvbs.acct;

import com.group.pbox.pvbs.clientmodel.acct.AcctReqModel;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespModel;

public interface IAcctCreationService
{
	AcctRespModel addAcct(AcctReqModel acctRequest) throws Exception;
    AcctRespModel accountValid(AcctReqModel acctRequest) throws Exception;
}
