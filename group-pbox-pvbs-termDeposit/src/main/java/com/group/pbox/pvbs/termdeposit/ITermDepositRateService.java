package com.group.pbox.pvbs.termdeposit;

import java.util.List;

import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositRateRespModel;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositReqModel;

public interface ITermDepositRateService {
	public TermDepositRateRespModel inquiryTermDepositRate(TermDepositReqModel termDepositReqModel) throws Exception;

	public List<TermDepositRateRespModel> inquiryAllTermDepositRate() throws Exception;
}
