package com.group.pbox.pvbs.persist.termdeposit;

import java.util.List;

import com.group.pbox.pvbs.model.termdeposit.TermDepositRate;

public interface TermDepositRateMapper {
	List<TermDepositRate> fetchAllTermDepositRate(); 
	TermDepositRate fetchTermDepositRateByPeriod(String period);
}
