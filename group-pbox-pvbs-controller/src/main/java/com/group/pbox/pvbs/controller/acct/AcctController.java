package com.group.pbox.pvbs.controller.acct;

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
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

@Controller
@RequestMapping("/acct")
public class AcctController {

	private static final Logger sysLogger = Logger.getLogger("customer");

	@Resource
	IAcctCreationService acctCreationService;

	@RequestMapping(value = "/acctMaintenance ", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object acctMaintenance(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody AcctReqModel acctRequest) {
		
		AcctRespModel acctResp = new AcctRespModel();
		List<String> errorList = new ArrayList<String>();
		
		try {

			sysLogger.info("start acctMaintenance:" + acctRequest.getAccountNumber() + "|" + acctRequest.getBranchNumber() + "|" + acctRequest.getClearingCode() + "|"
                    + acctRequest.getOperationCode());
			
			switch (acctRequest.getOperationCode()) {
			case OperationCode.ACCT_CREATION:
				// add
				acctResp = acctCreationService.addAcct(acctRequest);
				break;
			case OperationCode.ACCT_ENQUIRY:
				// enquiryAcctInfo
				acctResp = acctCreationService.enquiryAcctInfo(acctRequest);
				break;
			case OperationCode.ACCT_MAINTENANCE:
				// edit
				acctResp = acctCreationService.editAcct(acctRequest);
				break;
			case OperationCode.ACCT_CLOSURE:
				// delete
				acctResp = acctCreationService.closeAcct(acctRequest);
				break;
			case OperationCode.ACCT_BALANCE_ENQUIRY:
				// inquire balance
				acctResp = acctCreationService.enquireBalance(acctRequest);
				break;
			}
			
			 sysLogger.info("end acctMaintenance:" + acctRequest.getAccountNumber() + "|" + acctRequest.getBranchNumber() + "|" + acctRequest.getClearingCode() + "|"
	                    + acctRequest.getOperationCode());
		} catch (Exception e) {
			sysLogger.error(
					"[com.group.pbox.pvbs.controller.acct.AcctController.acctMaintenance(AcctReqModel acctRequest)]",
					e);
			errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.setErrorCode(errorList);
		}
		
		return acctResp;
	}
}
