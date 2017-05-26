package com.group.pbox.pvbs.persist.ccyEx;

import java.util.List;

import com.group.pbox.pvbs.model.currencyRate.CurrencyRate;

public interface CcyExchangeRateMapper {

	public List<CurrencyRate> getAllExchangeRate();
	
	public CurrencyRate getCcyExRateByCurrCode(String currencyCode);
	
}
