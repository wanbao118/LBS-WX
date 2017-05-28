package com.group.pbox.pvbs.transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
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

    private static final String PAGE_RECORDS = "pageRecorders";

    private static final String PAGE_CURRENT = "currentPage";

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

        // get spereate page infor.
        Map<String, String> params = transactionReqModel.getParams();
        String currentPage = params.get(PAGE_CURRENT);
        String pageRecords = params.get(PAGE_RECORDS);

        // set query page infor.
        if (StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(pageRecords))
        {
            transferHistory.setQueryPageParams(Integer.parseInt(currentPage), Integer.parseInt(pageRecords));
        }
        List<TransferHistory> listdata = transferHistoryMapper.inquiryTransferHistoryByParams(transferHistory);
        List<TransferHistory> listdataCount = transferHistoryMapper.inquiryTransferHistoryByParamsCount(transferHistory);
        Map<String, String> respParams = transferHistory.setRespPageParams(listdata, listdataCount);
        transHisRespModel.setParams(respParams);
        List<TransHisRespData> listData = new ArrayList<TransHisRespData>();

        for (TransferHistory tmp : listdata)
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
