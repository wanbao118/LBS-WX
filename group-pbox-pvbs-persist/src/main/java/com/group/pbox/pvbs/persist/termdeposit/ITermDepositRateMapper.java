package com.group.pbox.pvbs.persist.termdeposit;

import java.util.List;

import com.group.pbox.pvbs.model.termdeposit.TermDepositRate;

public interface ITermDepositRateMapper {
	List<TermDepositRate> fetchAllTermDepositRate(); 
}
