package com.group.pbox.pvbs.transaction;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.model.account.AccountBalance;
import com.group.pbox.pvbs.persist.account.AccountBalanceMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.Utils;

@Service
public class AccountBalanceServiceImpl implements IAccountBalanceService
{
    @Resource
    AccountBalanceMapper accountBalanceMapper;

    public String deposit(AccountBalance accountBalance)
    {
        AccountBalance sourceAccountBalance = accountBalanceMapper
                .getAccountBalance(accountBalance);
        int result = 0;
        if (null == sourceAccountBalance)
        {
            accountBalance.setId(Utils.getUUID());
            // should get account id by account num.
            // hurrican will do this.we will refference.
            result = accountBalanceMapper.insertAccountBalance(accountBalance);
        }
        else
        {
            sourceAccountBalance.setBalance(sourceAccountBalance.getBalance()
                    + accountBalance.getBalance());
            result = accountBalanceMapper
                    .updateAccountBalance(sourceAccountBalance);
        }
        if (result != -1)
        {
            return ErrorCode.RESPONSE_SUCCESS;
        }
        else
        {
            return ErrorCode.RESPONSE_ERROR;
        }
    }

    public String withDrawal(AccountBalance accountBalance)
    {
        AccountBalance sourceAccountBalance = accountBalanceMapper
                .getAccountBalance(accountBalance);
        if (null == sourceAccountBalance)
        {
            return ErrorCode.RECORD_NOT_FOUND;
        }

        if (accountBalance.getBalance() > sourceAccountBalance.getBalance())
        {
            return ErrorCode.ACCOUNT_BALANCE_LESS;
        }

        sourceAccountBalance.setBalance(sourceAccountBalance.getBalance()
                - accountBalance.getBalance());
        int updateResult = accountBalanceMapper
                .updateAccountBalance(sourceAccountBalance);
        if (updateResult == -1)
        {
            return ErrorCode.DB_OPERATION_ERROR;
        }
        return ErrorCode.RESPONSE_SUCCESS;
    }

}
