package com.group.pbox.pvbs.termDeposit;

import java.util.List;

import com.group.pbox.pvbs.clientmodel.termDeposit.TermDepositRateRespModel;
import com.group.pbox.pvbs.clientmodel.termDeposit.TermDepositReqModel;

public interface ITermDepositRateService {
	public TermDepositRateRespModel inquiryTermDepositRate(TermDepositReqModel termDepositReqModel) throws Exception;

	public List<TermDepositRateRespModel> inquiryAllTermDepositRate() throws Exception;
}
