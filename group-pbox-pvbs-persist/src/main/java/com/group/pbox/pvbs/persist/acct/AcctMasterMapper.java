package com.group.pbox.pvbs.persist.acct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.group.pbox.pvbs.model.acct.AccountMaster;

public interface AcctMasterMapper {

	int insertAccountMaster(AccountMaster accountMaster);
	int editAcctMaster(AccountMaster accountMaster);
	List<AccountMaster> enquiryAcctInfo(@Param("realAcctNum")String realAcctNum);
}