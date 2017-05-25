package com.group.pbox.pvbs.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionRespModel;
import com.group.pbox.pvbs.model.acct.AccountBalance;
import com.group.pbox.pvbs.persist.acct.AccountBalanceMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.Utils;

@Service
public class AccountBalanceServiceImpl implements IAccountBalanceService
{
    private static final Logger logger = Logger.getLogger(AccountBalanceServiceImpl.class);

    @Resource
    AccountBalanceMapper accountBalanceMapper;

    @Override
    public TransactionRespModel deposit(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountNum(transactionReqModel.getAccountNumber());
        accountBalance.setBalance(transactionReqModel.getAmount());
        accountBalance.setCurrencyCode(transactionReqModel.getCurrency());
        accountBalance.setAccountId(transactionReqModel.getAccountId());
        AccountBalance sourceAccountBalance = accountBalanceMapper.getAccountBalance(accountBalance);
        int result = 0;
        if (null == sourceAccountBalance)
        {
            accountBalance.setId(Utils.getUUID());
            result = accountBalanceMapper.insertAccountBalance(accountBalance);
        }
        else
        {
            sourceAccountBalance.setBalance(sourceAccountBalance.getBalance() + accountBalance.getBalance());
            result = accountBalanceMapper.updateAccountBalance(sourceAccountBalance);
        }
        if (result == -1)
        {
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        else
        {
            transactionRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        }
        return transactionRespModel;
    }

    @Override
    public TransactionRespModel withDrawal(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountNum(transactionReqModel.getAccountNumber());
        accountBalance.setBalance(transactionReqModel.getAmount());
        accountBalance.setCurrencyCode(transactionReqModel.getCurrency());
        AccountBalance sourceAccountBalance = accountBalanceMapper.getAccountBalance(accountBalance);

        if (null == sourceAccountBalance || accountBalance.getBalance() > sourceAccountBalance.getBalance())
        {
            errorList.add(ErrorCode.INSUFFICIENT_FUNDING);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

        sourceAccountBalance.setBalance(sourceAccountBalance.getBalance() - accountBalance.getBalance());
        int updateResult = accountBalanceMapper.updateAccountBalance(sourceAccountBalance);
        if (updateResult == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        transactionRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        return transactionRespModel;
    }

    @Override
    public TransactionRespModel transfer(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();
        AccountBalance searchSourceAccountBalance = new AccountBalance();
        searchSourceAccountBalance.setAccountNum(transactionReqModel.getAccountNumber());
        searchSourceAccountBalance.setBalance(transactionReqModel.getAmount());
        searchSourceAccountBalance.setCurrencyCode(transactionReqModel.getCurrency());
        AccountBalance sourceAccountBalance = accountBalanceMapper.getAccountBalance(searchSourceAccountBalance);
        // check insuffcient funding.
        if (null == sourceAccountBalance || searchSourceAccountBalance.getBalance() > sourceAccountBalance.getBalance())
        {
            errorList.add(ErrorCode.INSUFFICIENT_FUNDING);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

        Map<String, String> params = transactionReqModel.getParams();
        String targetAccountNum = params.get("targetAccountNum");
        AccountBalance searchTargetAccountBalance = new AccountBalance();
        searchTargetAccountBalance.setAccountNum(targetAccountNum);
        searchTargetAccountBalance.setBalance(transactionReqModel.getAmount());
        searchTargetAccountBalance.setCurrencyCode(transactionReqModel.getCurrency());
        searchTargetAccountBalance.setAccountId(transactionReqModel.getAccountId());
        AccountBalance targetAccountBalance = accountBalanceMapper.getAccountBalance(searchTargetAccountBalance);
        int result = 0;
        if (null == targetAccountBalance)
        {
            searchTargetAccountBalance.setId(Utils.getUUID());
            result = accountBalanceMapper.insertAccountBalance(searchTargetAccountBalance);
        }
        else
        {
            targetAccountBalance.setBalance(sourceAccountBalance.getBalance() + searchSourceAccountBalance.getBalance());
            result = accountBalanceMapper.updateAccountBalance(targetAccountBalance);
        }
        if (result == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }

        sourceAccountBalance.setBalance(sourceAccountBalance.getBalance() - searchTargetAccountBalance.getBalance());
        int updateResult = accountBalanceMapper.updateAccountBalance(sourceAccountBalance);
        if (updateResult == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        transactionRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        return transactionRespModel;
    }

}
