package com.group.pbox.pvbs.transaction;

import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionRespModel;

public interface IAccountBalanceService
{
    public TransactionRespModel deposit(TransactionReqModel transactionReqModel) throws Exception;

    public TransactionRespModel withDrawal(TransactionReqModel transactionReqModel) throws Exception;
}
