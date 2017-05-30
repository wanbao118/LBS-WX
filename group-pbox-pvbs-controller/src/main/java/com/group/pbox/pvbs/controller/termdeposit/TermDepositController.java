package com.group.pbox.pvbs.controller.termdeposit;

import java.util.ArrayList;
import java.util.List;

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
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfReqModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespModel;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositReqModel;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositRespModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionRespModel;
import com.group.pbox.pvbs.sysconf.ISysConfService;
import com.group.pbox.pvbs.termdeposit.ITermDepositService;
import com.group.pbox.pvbs.transaction.IAccountBalanceService;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

@Controller
@RequestMapping("/termDeposit")
public class TermDepositController {
	private static final Logger businessLogger = Logger.getLogger(TermDepositController.class);
	private static final Logger sysLogger = Logger.getLogger("customer");

	@Resource
	IAcctCreationService acctCreationService;
	@Resource
	ITermDepositService termDepositService;
    @Resource
    IAccountBalanceService accountBalanceService;
	@Resource
	ISysConfService sysConfService;

	@RequestMapping(value = "/termDepositDepatcher", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object termDeposit(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody TermDepositReqModel termDepositReqModel) {

		TermDepositRespModel termDepositRespModel = new TermDepositRespModel();
		List<String> errorList = new ArrayList<String>();

		try {
			switch (termDepositReqModel.getOperationCode()) {
			case OperationCode.TERM_CREATE:

				termDepositRespModel = createTermDeposit(termDepositReqModel);
				break;
			case OperationCode.TERM_ENQUIRY:

				termDepositRespModel = enquiryTermDeposit(termDepositReqModel);
				break;
			case OperationCode.TERM_DRAWDOWN:

				termDepositRespModel = drawDown(termDepositReqModel);
				break;
			case OperationCode.TERM_RENEWAL:

				termDepositRespModel = reNewal(termDepositReqModel);
				break;
			}

		} catch (Exception e) {
			sysLogger.error("", e);
			errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
			termDepositRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositRespModel.setErrorCode(errorList);
		}
		return termDepositRespModel;
	}

	private TermDepositRespModel reNewal(TermDepositReqModel termDepositReqModel) {
		// TODO Auto-generated method stub
		return null;
	}

	private TermDepositRespModel drawDown(TermDepositReqModel termDepositReqModel) throws Exception {
		TermDepositRespModel termDepositResp = new TermDepositRespModel();
		TransactionReqModel transactionReqModel = new TransactionReqModel();
		TransactionRespModel transactionRespModel = new TransactionRespModel();
		AcctReqModel acctRequest = new AcctReqModel();
		acctRequest.setRealAccountNumber(termDepositReqModel.getAccountNumber());
		AcctRespModel acctValid = acctCreationService.accountValidByRealNum(acctRequest);
		if ( acctValid.getResult().equals(ErrorCode.RESPONSE_ERROR)) {
			termDepositResp.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositResp.getErrorCode().add(ErrorCode.RECORD_NOT_FOUND);
			return termDepositResp;
		}
		
		termDepositResp = termDepositService.drawDown(termDepositReqModel);
		//enquery balance
		SysConfReqModel sysConfReqModel = new SysConfReqModel();
		sysConfReqModel.setItem("Primary_Ccy_Code");
		SysConfRespModel sysConfRespModel = new SysConfRespModel();
		sysConfRespModel = sysConfService.getAllSysConfByParam(sysConfReqModel);
		
		transactionRespModel = accountBalanceService.enquireAccountBalance(termDepositReqModel.getAccountNumber());
		String currenyCode = transactionRespModel.getListData().get(0).getCurrencyCode();
		String primaryCode = sysConfRespModel.getListData().get(0).getValue();
		
		if (!currenyCode.equals(primaryCode))
		{
			termDepositResp.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositResp.getErrorCode().add(ErrorCode.RECORD_NOT_FOUND);
			return termDepositResp;
		}
		//update balance
		double acctBalance = transactionRespModel.getListData().get(0).getBalance();
		double maturityAmount = termDepositReqModel.getMaturityAmount();
		transactionReqModel.setAccountNumber(termDepositReqModel.getAccountNumber());
		transactionReqModel.setAmount(acctBalance+maturityAmount);
		transactionReqModel.setCurrency(primaryCode);
		transactionRespModel = accountBalanceService.deposit(transactionReqModel);
		
		if (ErrorCode.RESPONSE_ERROR.equals(transactionRespModel.getResult()))
		{
			termDepositResp.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositResp.getErrorCode().add(ErrorCode.UPDATE_BALANCE_FAIL);
			return termDepositResp;
		}
		
		//edit status to 'D'
		termDepositResp = termDepositService.updateStatus(termDepositReqModel);
		
		return termDepositResp;
	}

	private TermDepositRespModel enquiryTermDeposit(TermDepositReqModel termDepositReqModel) {
		// TODO Auto-generated method stub
		return null;
	}

	private TermDepositRespModel createTermDeposit(TermDepositReqModel termDepositReqModel) {
		// TODO Auto-generated method stub
		return null;
	}

}
