package com.group.pbox.pvbs.controller.acct;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.acct.IAcctCreationService;
import com.group.pbox.pvbs.acct.model.AcctRequest;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.Response;

@Controller
@RequestMapping("/acct")
public class AcctController {

    @Resource
    IAcctCreationService acctCreationService;

    @RequestMapping(value = "/addAcct", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8;", consumes = "application/json")
    @ResponseBody
    public Object addAcct(final HttpServletRequest request,
            final HttpServletResponse response, @RequestBody AcctRequest acctRequest)
    {
        Response resp = new Response();
        Integer maxAcctNumber = acctCreationService.fetchAcct();
        
        if (null == maxAcctNumber)
        {
        	int result = acctCreationService.addAcct(acctRequest);
        	
        	if (result > 0)
        	{
        		resp.setResult(ErrorCode.RESPONSE_SUCCESS);
        	}
        	else
        	{
        		resp.setResult(ErrorCode.RESPONSE_ERROR);
        	}
        }
        else
        {
        	Integer newAcctNumber = maxAcctNumber+1;
        	acctRequest.setAccountNumber(newAcctNumber.toString());
        	acctRequest.setRealAccountNumber(acctRequest.getClearingCode()+acctRequest.getBranchNumber()+newAcctNumber.toString());
        	int result = acctCreationService.addAcct(acctRequest);
        	
        	if (result > 0)
        	{
        		resp.setResult(ErrorCode.RESPONSE_SUCCESS);
        	}
        	else
        	{
        		resp.setResult(ErrorCode.RESPONSE_ERROR);
        	}
        }
        
        return resp;
    }
}
