package com.group.pbox.pvbs.transaction;


import com.group.pbox.pvbs.clientmodel.transaction.TransHisRespModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;

public interface ITransferHistoryService{

	public TransHisRespModel inquiryTransferHistory(TransactionReqModel transactionReqModel) throws Exception;
	

}

