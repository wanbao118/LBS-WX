package com.group.pbox.pvbs.persist.termDeposit;

import java.util.List;

import com.group.pbox.pvbs.model.termDeposit.TermDepositRate;

public interface ITermDepositRateMapper {
	List<TermDepositRate> fetchAllTermDepositRate(); 
}
