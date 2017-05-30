package com.group.pbox.pvbs.persist.termdeposit;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.group.pbox.pvbs.model.termdeposit.TermDepositMaster;

public interface TermDepositMapper {
	boolean addTermDeposit(TermDepositMaster termDeposit);

	int updateTermDeposit(TermDepositMaster termDeposit);

	List<TermDepositMaster> enquiryTermDeposit(@Param("accountNum") String accountNum,
		@Param("depositNumber") String depositNumber);
	
	String genereateMaxTDNum();
}
