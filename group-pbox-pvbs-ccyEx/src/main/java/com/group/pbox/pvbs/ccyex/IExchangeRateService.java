package com.group.pbox.pvbs.ccyex;

import com.group.pbox.pvbs.clientmodel.ccyex.CcyExchangeRateReqModel;
import com.group.pbox.pvbs.clientmodel.ccyex.CcyExchangeRateRespModel;


public interface IExchangeRateService {
	
	CcyExchangeRateRespModel getAllCcyExchangeRate() throws Exception;
	
	CcyExchangeRateRespModel getCcyExRateByCurrCode(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception;
	
}
