package com.group.pbox.pvbs.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.transaction.TransHisRespData;
import com.group.pbox.pvbs.clientmodel.transaction.TransHisRespModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.model.acct.Account;
import com.group.pbox.pvbs.model.acct.TransferHistory;
import com.group.pbox.pvbs.persist.acct.AcctMapper;
import com.group.pbox.pvbs.persist.acct.TransferHistoryMapper;
import com.group.pbox.pvbs.util.ErrorCode;

@Service
public class TransferHistoryServiceImpl implements ITransferHistoryService
{

    private static final Logger logger = Logger.getLogger(TransferHistoryServiceImpl.class);

    @Resource
    TransferHistoryMapper transferHistoryMapper;
    @Resource
    AcctMapper acctMapper;

    private static final String TYPE = "Type";

    @Override
    public TransHisRespModel inquiryTransferHistory(TransactionReqModel transactionReqModel) throws Exception
    {
        TransHisRespModel transHisRespModel = new TransHisRespModel();
        List<String> errorCode = new ArrayList<String>();

        // check account exsit.
        Account account = new Account();
        account.setRealAccountNumber(transactionReqModel.getAccountNumber());
        account = acctMapper.getAccountInfo(account);
        if (null != account)
        {
            transactionReqModel.setAccountId(account.getId());
        }
        else
        {
            transHisRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            errorCode.add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
            transHisRespModel.setErrorCode(errorCode);
            return transHisRespModel;
        }

        // seach transfer history.
        TransferHistory transferHistory = new TransferHistory();
        transferHistory.setCurrency(transactionReqModel.getCurrency());
        transferHistory.setSourceAccountId(transactionReqModel.getAccountId());
        Map<String, String> frontParams = transactionReqModel.getParams();
        if (frontParams != null)
        {
            transferHistory.setTransferType(frontParams.get(TYPE));
        }
        List<TransferHistory> list = new ArrayList<TransferHistory>();
        list = transferHistoryMapper.inquiryTransferHistory(transferHistory);
        List<TransHisRespData> listData = new ArrayList<TransHisRespData>();

        for (TransferHistory tmp : list)
        {
            TransHisRespData transHisRespData = new TransHisRespData();
            BeanUtils.copyProperties(transHisRespData, tmp);
            account.setId(tmp.getSourceAccountId());
            account.setRealAccountNumber(null);
            account = acctMapper.getAccountInfo(account);
            transHisRespData.setSourceAccountNum(account.getRealAccountNumber());
            account.setId(tmp.getTargetAccountId());
            account.setRealAccountNumber(null);
            account = acctMapper.getAccountInfo(account);
            transHisRespData.setTargetAccountNum(account.getRealAccountNumber());
            listData.add(transHisRespData);
        }
        transHisRespModel.setListData(listData);
        transHisRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
        return transHisRespModel;
    }

}
