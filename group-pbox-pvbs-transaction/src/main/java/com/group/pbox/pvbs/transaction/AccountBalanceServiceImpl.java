package com.group.pbox.pvbs.transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionRespModel;
import com.group.pbox.pvbs.model.acct.Account;
import com.group.pbox.pvbs.model.acct.AccountBalance;
import com.group.pbox.pvbs.model.acct.TransferHistory;
import com.group.pbox.pvbs.persist.acct.AccountBalanceMapper;
import com.group.pbox.pvbs.persist.acct.AcctMapper;
import com.group.pbox.pvbs.persist.acct.TransferHistoryMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;
import com.group.pbox.pvbs.util.Utils;

@Service
public class AccountBalanceServiceImpl implements IAccountBalanceService
{
    private static final Logger logger = Logger.getLogger(AccountBalanceServiceImpl.class);

    private static final String TARGET_ACCOUNT_NUM = "targetAccountNum";

    @Resource
    AccountBalanceMapper accountBalanceMapper;
    @Resource
    AcctMapper acctMapper;
    @Resource
    TransferHistoryMapper transferHistoryMapper;

    @Override
    public TransactionRespModel deposit(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();

        // check account exist.
        Account account = getAccountByAccountRealNum(transactionReqModel.getAccountNumber());
        if (null == account)
        {
            errorList.add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

        // deposit
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountNum(transactionReqModel.getAccountNumber());
        accountBalance.setBalance(transactionReqModel.getAmount());
        accountBalance.setCurrencyCode(transactionReqModel.getCurrency());
        accountBalance.setAccountId(account.getId());
        Date currentDate = getCurrentDate();
        AccountBalance sourceAccountBalance = accountBalanceMapper.getAccountBalance(accountBalance);
        int result = 0;
        if (null == sourceAccountBalance)
        {
            accountBalance.setId(Utils.getUUID());
            accountBalance.setLastUpatedDate(currentDate);
            result = accountBalanceMapper.insertAccountBalance(accountBalance);
        }
        else
        {
            sourceAccountBalance.setBalance(sourceAccountBalance.getBalance() + accountBalance.getBalance());
            sourceAccountBalance.setLastUpatedDate(currentDate);
            result = accountBalanceMapper.updateAccountBalance(sourceAccountBalance);
        }
        if (result == -1)
        {
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

        // update transfer history.
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setSourceAccountId(sourceAccountBalance.getAccountId());
        transferHistory.setTargetAccountId(sourceAccountBalance.getAccountId());
        transferHistory.setTransferAmount(transactionReqModel.getAmount());
        transferHistory.setCurrency(transactionReqModel.getCurrency());
        transferHistory.setTransferType(OperationCode.TRANS_DEPOSIT);
        transferHistory.setCreateTime(currentDate);
        transferHistory.setId(Utils.getUUID());

        int insertResult = transferHistoryMapper.insertTransferHistory(transferHistory);
        if (insertResult == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }

        // success.
        transactionRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        return transactionRespModel;
    }

    @Override
    public TransactionRespModel withDrawal(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();

        // check account exist.
        Account account = getAccountByAccountRealNum(transactionReqModel.getAccountNumber());
        if (null == account)
        {
            errorList.add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

        // translet request obj to entity bean.
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountNum(transactionReqModel.getAccountNumber());
        accountBalance.setBalance(transactionReqModel.getAmount());
        accountBalance.setCurrencyCode(transactionReqModel.getCurrency());

        // check insufficient funding
        AccountBalance sourceAccountBalance = accountBalanceMapper.getAccountBalance(accountBalance);

        if (null == sourceAccountBalance || accountBalance.getBalance() > sourceAccountBalance.getBalance())
        {
            errorList.add(ErrorCode.INSUFFICIENT_FUNDING);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

        Date currentDate = getCurrentDate();
        sourceAccountBalance.setLastUpatedDate(currentDate);
        sourceAccountBalance.setBalance(sourceAccountBalance.getBalance() - accountBalance.getBalance());
        int updateResult = accountBalanceMapper.updateAccountBalance(sourceAccountBalance);

        // check update result.
        if (updateResult == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }

        // update transfer history.
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setSourceAccountId(sourceAccountBalance.getAccountId());
        transferHistory.setTargetAccountId(sourceAccountBalance.getAccountId());
        transferHistory.setTransferAmount(transactionReqModel.getAmount());
        transferHistory.setCurrency(transactionReqModel.getCurrency());
        transferHistory.setTransferType(OperationCode.TRANS_WITHDRAW);
        transferHistory.setCreateTime(currentDate);
        transferHistory.setId(Utils.getUUID());

        int insertResult = transferHistoryMapper.insertTransferHistory(transferHistory);
        if (insertResult == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        transactionRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        return transactionRespModel;
    }

    private Account getAccountByAccountRealNum(String acctRealNum)
    {
        Account account = new Account();
        account.setRealAccountNumber(acctRealNum);
        Account resultAccount = acctMapper.getAccountInfo(account);
        return resultAccount;
    }

    @Override
    public TransactionRespModel transfer(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();
        Map<String, String> frontParams = transactionReqModel.getParams();

        // check source account exist.
        Account sourceAccount = getAccountByAccountRealNum(transactionReqModel.getAccountNumber());
        if (sourceAccount == null)
        {
            errorList = new ArrayList<String>();
            errorList.add(ErrorCode.ACCOUNT_TRANSFER_SOURCE_NOT_FOUND);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

        // check target account exist.
        Account targetAccount = getAccountByAccountRealNum(frontParams.get(TARGET_ACCOUNT_NUM));
        if (targetAccount == null)
        {
            errorList = new ArrayList<String>();
            errorList.add(ErrorCode.ACCOUNT_TRANSFER_TARGET_NOT_FOUND);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
            return transactionRespModel;
        }

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

        // transfer

        //// deposit
        Map<String, String> params = transactionReqModel.getParams();
        String targetAccountNum = params.get(TARGET_ACCOUNT_NUM);
        AccountBalance searchTargetAccountBalance = new AccountBalance();
        searchTargetAccountBalance.setAccountNum(targetAccountNum);
        searchTargetAccountBalance.setBalance(transactionReqModel.getAmount());
        searchTargetAccountBalance.setCurrencyCode(transactionReqModel.getCurrency());
        searchTargetAccountBalance.setAccountId(targetAccount.getId());
        AccountBalance targetAccountBalance = accountBalanceMapper.getAccountBalance(searchTargetAccountBalance);

        Date currentDate = getCurrentDate();

        int result = 0;
        if (null == targetAccountBalance)
        {
            searchTargetAccountBalance.setId(Utils.getUUID());
            searchTargetAccountBalance.setLastUpatedDate(currentDate);
            result = accountBalanceMapper.insertAccountBalance(searchTargetAccountBalance);
        }
        else
        {
            targetAccountBalance.setBalance(sourceAccountBalance.getBalance() + searchSourceAccountBalance.getBalance());
            searchTargetAccountBalance.setLastUpatedDate(currentDate);
            result = accountBalanceMapper.updateAccountBalance(targetAccountBalance);
        }
        if (result == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }

        //// withdrawl
        sourceAccountBalance.setBalance(sourceAccountBalance.getBalance() - searchTargetAccountBalance.getBalance());
        sourceAccountBalance.setLastUpatedDate(currentDate);
        int updateResult = accountBalanceMapper.updateAccountBalance(sourceAccountBalance);
        if (updateResult == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }

        // transaction history
        TransferHistory transferHistory = new TransferHistory();

        transferHistory.setSourceAccountId(sourceAccount.getId());
        transferHistory.setTargetAccountId(targetAccount.getId());
        transferHistory.setTransferAmount(transactionReqModel.getAmount());
        transferHistory.setCurrency(transactionReqModel.getCurrency());
        transferHistory.setTransferType(OperationCode.TRANS_TRANSFER);
        transferHistory.setCreateTime(currentDate);
        transferHistory.setId(Utils.getUUID());
        int tempResult = transferHistoryMapper.insertTransferHistory(transferHistory);
        if (tempResult == -1)
        {
            errorList.add(ErrorCode.DB_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }

        transactionRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        return transactionRespModel;
    }

    private Date getCurrentDate() throws ParseException
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(new Date().getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateFormat.format(c.getTime());
        Date currentDate = (Date) dateFormat.parseObject(date);
        return currentDate;
    }

}
