package com.group.pbox.pvbs.ccyEx;

import java.util.List;

import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateReqModel;
import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateRespModel;


public interface IExchangeRateService {
	
	CcyExchangeRateRespModel getAllCcyExchangeRate() throws Exception;
	
	CcyExchangeRateRespModel getCcyExRateByCurrCode(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception;
	
}
