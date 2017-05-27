package com.group.pbox.pvbs.controller.transaction;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.group.pbox.pvbs.clientmodel.transaction.TransHisRespModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionRespModel;
import com.group.pbox.pvbs.clientmodel.user.UserReqModel;
import com.group.pbox.pvbs.clientmodel.user.UserRespData;
import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.sysconf.ISysConfService;
import com.group.pbox.pvbs.transaction.IAccountBalanceService;
import com.group.pbox.pvbs.transaction.ITransferHistoryService;
import com.group.pbox.pvbs.user.IUserService;
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
    @Resource
    ITransferHistoryService transferHistoryService;
    @Resource
    IUserService userService;

    @RequestMapping(value = "/transHis", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Object transHis(final HttpServletRequest request, final HttpServletResponse response, @RequestBody TransactionReqModel transactionReqModel)
    {
        TransHisRespModel transHisRespModel = new TransHisRespModel();
        List<String> errorList = new ArrayList<String>();

        try
        {
            sysLogger.info("start transaction history module:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                    + transactionReqModel.getOperationCode());

            // check currency code.
            transHisRespModel = checkCurrencySurpportForHis(transactionReqModel);
            if (StringUtils.equalsIgnoreCase(transHisRespModel.getResult(), ErrorCode.RESPONSE_ERROR))
            {
                return transHisRespModel;
            }

            switch (transactionReqModel.getOperationCode())
            {
                case OperationCode.TRANS_HISTORY_QUERY:
                    transHisRespModel = transactionHistory(transactionReqModel);
                    break;
            }
            sysLogger.info("end transaction history module:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                    + transactionReqModel.getOperationCode());
        }
        catch (Exception e)
        {
            sysLogger.error("[com.group.pbox.pvbs.controller.transaction.AccountBalanceController.transHis(TransactionReqModel transactionReqModel)]", e);
            errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
            transHisRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transHisRespModel.setErrorCode(errorList);
        }
        return transHisRespModel;
    }

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

            // check currency code.
            transactionRespModel = checkCurrencySurpport(transactionReqModel);
            if (StringUtils.equalsIgnoreCase(transactionRespModel.getResult(), ErrorCode.RESPONSE_ERROR))
            {
                return transactionRespModel;
            }

            // check exceed limit
            transactionRespModel = checkExcedLimit(request, transactionReqModel);
            if (StringUtils.equalsIgnoreCase(transactionRespModel.getResult(), ErrorCode.RESPONSE_ERROR))
            {
                return transactionRespModel;
            }

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
                case OperationCode.TRANS_TRANSFER:
                    // transfer
                    transactionRespModel = transfer(transactionReqModel);
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

    private TransactionRespModel transfer(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionLog.customerLog(businessLogger, "start transfer:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        TransactionRespModel transactionRespModel = new TransactionRespModel();

        transactionRespModel = accountBalanceService.transfer(transactionReqModel);

        TransactionLog.customerLog(businessLogger, "end deposit:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());

        return transactionRespModel;
    }

    private TransactionRespModel deposit(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionLog.customerLog(businessLogger, "start deposit:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        TransactionRespModel transactionRespModel = new TransactionRespModel();

        transactionRespModel = accountBalanceService.deposit(transactionReqModel);
        TransactionLog.customerLog(businessLogger, "end deposit:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());

        return transactionRespModel;
    }

    private TransactionRespModel withDrawal(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionLog.customerLog(businessLogger, "start withDrawal:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());

        TransactionRespModel transactionRespModel = new TransactionRespModel();

        // handler withDrawal
        transactionRespModel = accountBalanceService.withDrawal(transactionReqModel);

        TransactionLog.customerLog(businessLogger, "end withDrawal:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        return transactionRespModel;
    }

    private TransactionRespModel checkCurrencySurpport(TransactionReqModel transactionReqModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();

        List<String> errorList = new ArrayList<String>();
        // check currency surpport
        boolean result = checkCurrency(transactionReqModel);
        if (!result)
        {
            errorList.add(ErrorCode.CURRENCY_NOT_FOUND);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        return transactionRespModel;
    }

    private TransHisRespModel checkCurrencySurpportForHis(TransactionReqModel transactionReqModel) throws Exception
    {
        TransHisRespModel transactionRespModel = new TransHisRespModel();
        List<String> errorList = new ArrayList<String>();
        // check currency surpport
        boolean result = checkCurrency(transactionReqModel);
        if (!result)
        {
            errorList.add(ErrorCode.CURRENCY_NOT_FOUND);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        return transactionRespModel;
    }

    private TransHisRespModel transactionHistory(TransactionReqModel transactionReqModel) throws Exception
    {
        sysLogger.info("start transaction history inquiry:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());

        TransHisRespModel transHisRespModel = new TransHisRespModel();

        transHisRespModel = transferHistoryService.inquiryTransferHistory(transactionReqModel);

        sysLogger.info("end transaction history inquiry:" + transactionReqModel.getAccountNumber() + "|" + transactionReqModel.getCurrency() + "|" + transactionReqModel.getAmount() + "|"
                + transactionReqModel.getOperationCode());
        return transHisRespModel;

    }

    private TransactionRespModel checkExcedLimit(HttpServletRequest request, TransactionReqModel transactionRepModel) throws Exception
    {
        TransactionRespModel transactionRespModel = new TransactionRespModel();
        List<String> errorList = new ArrayList<String>();
        HttpSession session = request.getSession();
        Object userId = session.getAttribute("userId");
        UserReqModel userReqModel = new UserReqModel();
        if (null == userId)
        {
            java.util.Map<String, String> user = transactionRepModel.getParams();
            userReqModel.setUserId(user.get("userId"));
        }
        else
        {
            userReqModel.setUserId((String) userId);
        }

        UserRespModel userRespModel = userService.fetchUserByUserId(userReqModel);
        UserRespData userData = userRespModel.getListData().get(0);
        Double excedLimit = userData.getTransactionLimit();
        if (transactionRepModel.getAmount() > excedLimit)
        {
            errorList.add(ErrorCode.EXCEED_LIMIT);
            transactionRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            transactionRespModel.setErrorCode(errorList);
        }
        return transactionRespModel;

    }

    private boolean checkCurrency(TransactionReqModel transactionReqModel) throws Exception
    {
        boolean result = false;
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
        return result;
    }
}
