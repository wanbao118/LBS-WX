package com.group.pbox.pvbs.acct;

import com.group.pbox.pvbs.clientmodel.acct.AcctReqModel;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespModel;

public interface IAcctCreationService
{
	AcctRespModel addAcct(AcctReqModel acctRequest) throws Exception;
    AcctRespModel accountValid(AcctReqModel acctRequest) throws Exception;
    AcctRespModel closeAcct(AcctReqModel acctRequest) throws Exception;
    AcctRespModel editAcct(AcctReqModel acctRequest) throws Exception;
	AcctRespModel enquiryAcctInfo(AcctReqModel acctRequest) throws Exception;
	AcctRespModel enquireBalance(AcctReqModel acctRequest) throws Exception;
}
