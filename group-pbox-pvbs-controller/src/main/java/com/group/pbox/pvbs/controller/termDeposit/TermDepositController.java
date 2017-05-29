package com.group.pbox.pvbs.controller.termDeposit;

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

import com.group.pbox.pvbs.clientmodel.termDeposit.TermDepositReqModel;
import com.group.pbox.pvbs.clientmodel.termDeposit.TermDepositRespModel;
import com.group.pbox.pvbs.termDeposit.ITermDepositService;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

@Controller
@RequestMapping("/termDeposit")
public class TermDepositController {
	private static final Logger businessLogger = Logger.getLogger(TermDepositController.class);
	private static final Logger sysLogger = Logger.getLogger("customer");

	@Resource
	ITermDepositService termDepositService;

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

	private TermDepositRespModel drawDown(TermDepositReqModel termDepositReqModel) {
		// TODO Auto-generated method stub
		return null;
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
