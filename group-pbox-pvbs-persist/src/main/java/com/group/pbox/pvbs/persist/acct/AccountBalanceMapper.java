package com.group.pbox.pvbs.persist.acct;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.group.pbox.pvbs.model.acct.AccountBalance;

public interface AccountBalanceMapper {
	AccountBalance getAccountBalance(AccountBalance accountBalance);

	int updateAccountBalance(AccountBalance accountBalance);

	int insertAccountBalance(AccountBalance accountBalance);

	double getBalance(@Param("realAccountNum") String realAccountNum);

	List<AccountBalance> enquireAccountBalance(@Param("accountNum") String accountNum);

}
