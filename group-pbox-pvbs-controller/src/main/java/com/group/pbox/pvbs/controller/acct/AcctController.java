package com.group.pbox.pvbs.controller.acct;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.acct.IAcctCreationService;
import com.group.pbox.pvbs.clientmodel.acct.AcctReqModel;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespModel;
import com.group.pbox.pvbs.controller.transaction.AccountBalanceController;
import com.group.pbox.pvbs.util.OperationCode;
import com.group.pbox.pvbs.util.log.TransactionLog;

@Controller
@RequestMapping("/acct")
public class AcctController {

    private static final Logger transactionLogger = Logger
            .getLogger(AccountBalanceController.class);
    private static final Logger logger = Logger.getLogger("customer");
	
	@Resource
	IAcctCreationService acctCreationService;

	@RequestMapping(value = "/acctMaintenance ", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object addAcct(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody AcctReqModel acctRequest) {

		logger.info("system log.");
        TransactionLog.customerLog(transactionLogger, "transaction log begin");
        AcctRespModel acctResp = new AcctRespModel();
        switch (acctRequest.getOperationCode())
        {
            case OperationCode.ACCT_CREATION:
                // add
            	acctResp = acctCreationService.addAcct(acctRequest);
                break;
            case OperationCode.ACCT_MAINTENANCE:
                // edit
                break;
            case OperationCode.ACCT_CLOSURE:
                // delete
                break;
        }
		
		TransactionLog.customerLog(transactionLogger, "transaction log end");
		return acctResp;
	}
}
