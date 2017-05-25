package com.group.pbox.pvbs.controller.transaction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.clientmodel.sysconf.SysConfReqModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespData;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionRespModel;
import com.group.pbox.pvbs.sysconf.ISysConfService;
import com.group.pbox.pvbs.transaction.IAccountBalanceService;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;
import com.group.pbox.pvbs.util.log.TransactionLog;

@Controller
@RequestMapping("/accountbalance")
public class AccountBalanceController
{
    private static final Logger businessLogger = Logger.getLogger(AccountBalanceController.class);
    private static final Logger sysLogger = Logger.getLogger("customer");
    @Resource
    IAccountBalanceService accountBalanceService;
    @Resource
    ISysConfService sysConfService;

    @RequestMapping(value = "/transaction", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Object transaction(final HttpServletRequest request, final HttpServletResponse response, @RequestBody TransactionReqModel transactionReqModel)
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();
        try
        {
            sysLogger.info("start transaction:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                    + transactionReqModel.getOperationCode());
            switch (transactionReqModel.getOperationCode())
            {
                case OperationCode.TRANS_DEPOSIT:
                    // Deposit
                    transactionRespModel = deposit(transactionReqModel);
                    break;
                case OperationCode.TRANS_WITHDRAW:
                    // withdrawl
                    transactionRespModel = withDrawal(transactionReqModel);
                    break;
            }
            sysLogger.info("end transaction:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                    + transactionReqModel.getOperationCode());
        }
        catch (Exception e)
        {
            sysLogger.error("[com.group.pbox.pvbs.controller.transaction.AccountBalanceController.withDrawal(TransactionReqModel transactionReqModel)]", e);
            errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        return transactionRespModel;
    }

    private TransactionRespModel deposit(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionLog.customerLog(businessLogger, "start deposit:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        TransactionRespModel transactionRespModel = checkCurrencySurpport(transactionReqModel);
        if (StringUtils.equalsIgnoreCase(transactionRespModel.getResult(), ErrorCode.RESPONSE_ERROR))
        {
            return transactionRespModel;
        }
        // todo account not find
        // todo exced limit
        transactionRespModel = accountBalanceService.deposit(transactionReqModel);
        TransactionLog.customerLog(businessLogger, "end deposit:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        return transactionRespModel;
    }

    private TransactionRespModel withDrawal(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionLog.customerLog(businessLogger, "start withDrawal:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        TransactionRespModel transactionRespModel = checkCurrencySurpport(transactionReqModel);
        if (StringUtils.equalsIgnoreCase(transactionRespModel.getResult(), ErrorCode.RESPONSE_ERROR))
        {
            return transactionRespModel;
        }
        // todo account not find
        // todo exced limit
        transactionRespModel = accountBalanceService.withDrawal(transactionReqModel);

        TransactionLog.customerLog(businessLogger, "end withDrawal:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        return transactionRespModel;
    }

    private TransactionRespModel checkCurrencySurpport(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        boolean result = false;
        List<String> errorList = new ArrayList<String>();
        // check currency surpport
        SysConfReqModel sysConfReqModel = new SysConfReqModel();
        List<SysConfRespData> listSysConfRespData = new ArrayList<SysConfRespData>();
        sysConfReqModel.setItem("Support_Ccy");
        SysConfRespModel supportCcyResp = sysConfService.getAllSysConfByParam(sysConfReqModel);
        listSysConfRespData.addAll(supportCcyResp.getListData());
        sysConfReqModel.setItem("Primary_Ccy_Code");
        supportCcyResp = sysConfService.getAllSysConfByParam(sysConfReqModel);
        listSysConfRespData.addAll(supportCcyResp.getListData());
        for (SysConfRespData sysConfRespData : listSysConfRespData)
        {
            if (StringUtils.equalsIgnoreCase(sysConfRespData.getValue(), transactionReqModel.getCurrency()))
            {
                result = true;
                break;
            }
        }

        if (!result)
        {
            errorList.add(ErrorCode.CURRENCY_NOT_FOUND);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        return transactionRespModel;
    }

}
