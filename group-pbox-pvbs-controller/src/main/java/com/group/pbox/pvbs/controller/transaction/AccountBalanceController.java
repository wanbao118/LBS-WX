package com.group.pbox.pvbs.controller.transaction;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.controller.model.BaseResponseModel;
import com.group.pbox.pvbs.controller.model.request.TransactionReq;
import com.group.pbox.pvbs.model.account.AccountBalance;
import com.group.pbox.pvbs.transaction.IAccountBalanceService;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

@Controller
@RequestMapping("/accountbalance")
public class AccountBalanceController
{
    @Resource
    IAccountBalanceService accountBalanceService;

    @RequestMapping(value = "/transaction", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public Object transaction(final HttpServletRequest request,
            final HttpServletResponse response,
            @RequestBody TransactionReq transactionReq)
    {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountNum(transactionReq.getAccountNumber());
        accountBalance.setBalance(transactionReq.getAmount());
        accountBalance.setCurrencyCode(transactionReq.getCurrency());
        BaseResponseModel resp = new BaseResponseModel();
        String result = "";
        if (StringUtils.equalsIgnoreCase(transactionReq.getOperation(),
                OperationCode.TRANS_DEPOSIT))
        {
            // Deposit
            result = accountBalanceService.deposit(accountBalance);
        }
        else if (StringUtils.equalsIgnoreCase(transactionReq.getOperation(),
                OperationCode.TRANS_WITHDRAW))
        {
            // withdrawl
            result = accountBalanceService.withDrawal(accountBalance);

        }
        if (!StringUtils.equalsIgnoreCase(result, ErrorCode.RESPONSE_SUCCESS)
                && !StringUtils.equalsIgnoreCase(result,
                        ErrorCode.RESPONSE_ERROR))
        {
            resp.setResult(ErrorCode.RESPONSE_ERROR);
            resp.getErrorCode().add(result);
        }
        return resp;
    }

}
