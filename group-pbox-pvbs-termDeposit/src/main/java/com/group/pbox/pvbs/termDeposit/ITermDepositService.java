package com.group.pbox.pvbs.termDeposit;

import com.group.pbox.pvbs.clientmodel.termDeposit.TermDepositReqModel;
import com.group.pbox.pvbs.clientmodel.termDeposit.TermDepositRespModel;

public interface ITermDepositService {
	public TermDepositRespModel creatTermDeposit(TermDepositReqModel termDepostReqModel);

	public TermDepositRespModel inquiryTermDeposit(TermDepositReqModel termDepositReqModel);

	public TermDepositRespModel reNewal(TermDepositReqModel termDepositReqModel);

	public TermDepositRespModel drawDown(TermDepositReqModel termDepositReqModel);
}
