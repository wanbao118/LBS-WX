package com.group.pbox.pvbs.controller.acct;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.acct.IAcctCreationService;
import com.group.pbox.pvbs.controller.model.BaseResponseModel;
import com.group.pbox.pvbs.controller.model.request.AcctReq;
import com.group.pbox.pvbs.model.acct.Account;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;
import com.group.pbox.pvbs.util.Utils;

@Controller
@RequestMapping("/acct")
public class AcctController {

	@Resource
	IAcctCreationService acctCreationService;

	@RequestMapping(value = "/addAcct", method = RequestMethod.POST, consumes = "application/json")
	@ResponseBody
	public Object addAcct(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody AcctReq acctRequest) {

		BaseResponseModel resp = new BaseResponseModel();
		int result;
		@SuppressWarnings("unused")
		String acct = null;
		if (StringUtils.equalsIgnoreCase(acctRequest.getOperation(), OperationCode.ACCT_CREATION)) {
			Account account = new Account();
			account.setId(Utils.getUUID());
			account.setAccountNumber(acctRequest.getAccountNumber());
			account.setBranchNumber(acctRequest.getBranchNumber());
			account.setClearingCode(acctRequest.getClearingCode());
			account.setRealAccountNumber(acctRequest.getRealAccountNumber());

			int accountResult = acctCreationService.accountValid(account);

			if (accountResult > 0) {
				resp.setResult(ErrorCode.ACCOUNT_HAVE_FOUND);
			} else {
				
				String maxAcctNumber = acctCreationService.fetchAcct();
				Integer newAcctNumber = Integer.valueOf(maxAcctNumber) + 1;
				if (newAcctNumber.toString().length() == 1)
		    	{
		    		acct = "0000" + newAcctNumber;
		    	}else if (newAcctNumber.toString().length() == 2)
		    	{
		    		acct = "000" + newAcctNumber;
		    	}else if (newAcctNumber.toString().length() == 3)
		    	{
		    		acct = "00" + newAcctNumber;
		    	}else if (newAcctNumber.toString().length() == 4)
		    	{
		    		acct = "0" + newAcctNumber;
		    	}else if (newAcctNumber.toString().length() == 5)
		    	{
		    		acct = newAcctNumber.toString();
		    	}
				
				account.setAccountNumber(acct);
				account.setRealAccountNumber(
						account.getClearingCode() + account.getBranchNumber() + acct);
				result = acctCreationService.addAcct(account);

				if (result > 0) {
					resp.setResult(ErrorCode.RESPONSE_SUCCESS);
				} else {
					resp.setResult(ErrorCode.RESPONSE_ERROR);
				}
			}
		}
		return resp;
	}
}
