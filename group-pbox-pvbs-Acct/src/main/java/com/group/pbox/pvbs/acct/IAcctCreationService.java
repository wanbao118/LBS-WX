package com.group.pbox.pvbs.acct;

import java.util.List;

import com.group.pbox.pvbs.clientmodel.acct.AcctReqModel;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespData;

public interface IAcctCreationService
{
	AcctRespModel addAcct(AcctReqModel acctRequest, List<SysConfRespData> listSysConfRespData) throws Exception;
    AcctRespModel accountValid(AcctReqModel acctRequest) throws Exception;
    AcctRespModel closeAcct(AcctReqModel acctRequest) throws Exception;
    AcctRespModel editAcct(AcctReqModel acctRequest) throws Exception;
	AcctRespModel enquiryAcctInfo(AcctReqModel acctRequest) throws Exception;
	AcctRespModel enquireBalance(AcctReqModel acctRequest) throws Exception;
	AcctRespModel accountValidByRealNum(AcctReqModel acctRequest) throws Exception;
	AcctRespModel getAcctInfoByRealNum(AcctReqModel acctRequest) throws Exception;
}
