package com.group.pbox.pvbs.termdeposit;

import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositReqModel;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositRespModel;

public interface ITermDepositService {
	public TermDepositRespModel creatTermDeposit(TermDepositReqModel termDepostReqModel);

	public TermDepositRespModel inquiryTermDeposit(TermDepositReqModel termDepositReqModel);

	public TermDepositRespModel reNewal(TermDepositReqModel termDepositReqModel);

	public TermDepositRespModel drawDown(TermDepositReqModel termDepositReqModel);
	
	public TermDepositRespModel updateStatus(TermDepositReqModel termDepositReqModel);
}
