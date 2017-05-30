package com.group.pbox.pvbs.persist.ccyex;

import java.util.List;

import com.group.pbox.pvbs.model.currencyrate.CurrencyRate;

public interface CcyExchangeRateMapper {

	public List<CurrencyRate> getAllExchangeRate();
	
	public CurrencyRate getCcyExRateByCurrCode(String currencyCode);
	
}
