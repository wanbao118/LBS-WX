package com.group.pbox.pvbs.controller.termDeposit;

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
import com.group.pbox.pvbs.termDeposit.ITermDepositService;

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
		return false;
	}
}
