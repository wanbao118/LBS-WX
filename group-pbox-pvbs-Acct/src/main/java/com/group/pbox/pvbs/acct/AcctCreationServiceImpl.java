package com.group.pbox.pvbs.acct;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.acct.AcctReqModel;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespModel;
import com.group.pbox.pvbs.model.acct.Account;
import com.group.pbox.pvbs.persist.acct.AcctMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.Utils;

@Service
public class AcctCreationServiceImpl implements IAcctCreationService
{
    @Resource
    AcctMapper acctMapper;

    public AcctRespModel addAcct(AcctReqModel acctRequest)
    {
    	AcctRespModel acctResp = new AcctRespModel();
		Account account = new Account();
		account.setId(Utils.getUUID());
		account.setAccountNumber(acctRequest.getAccountNumber());
		account.setBranchNumber(acctRequest.getBranchNumber());
		account.setClearingCode(acctRequest.getClearingCode());
		account.setRealAccountNumber(acctRequest.getRealAccountNumber());
		String acct = null;
		int acctValid = acctMapper.accountValid(account);
		
		if (acctValid > 0)
		{
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_FOUND);
			return acctResp;
		}
		
		String maxAcctNumber = acctMapper.fetchAcct();
		Integer newAcctNumber = Integer.valueOf(maxAcctNumber) + 1;
		
        switch (newAcctNumber.toString().length())
        {
            case 1:
            	acct = "0000" + newAcctNumber;
                break;
            case 2:
            	acct = "000" + newAcctNumber;
                break;
            case 3:
            	acct = "00" + newAcctNumber;
                break;
            case 4:
            	acct = "0" + newAcctNumber;
                break;
            case 5:
            	acct = newAcctNumber.toString();
                break;
        }
		
		account.setAccountNumber(acct);
		account.setRealAccountNumber(
				account.getClearingCode() + account.getBranchNumber() + acct);
		int addAcct = acctMapper.addAcct(account);
		
		if (addAcct > 0)
		{
			acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
			acctResp.getErrorCode().add(ErrorCode.ADD_ACCOUNT_SUCCESS);
			return acctResp;
		}
		
		acctResp.setResult(ErrorCode.RESPONSE_ERROR);
		acctResp.getErrorCode().add(ErrorCode.ADD_ACCOUNT_FAILED);
		
        return acctResp; 
    }

    public String fetchAcct()
    {
        return acctMapper.fetchAcct();
    }
    
    public AcctRespModel accountValid(AcctReqModel acctRequest)
    {
    	AcctRespModel acctResp = new AcctRespModel();
    	Account account = new Account();
		account.setId(Utils.getUUID());
		account.setAccountNumber(acctRequest.getAccountNumber());
		account.setBranchNumber(acctRequest.getBranchNumber());
		account.setClearingCode(acctRequest.getClearingCode());
		account.setRealAccountNumber(acctRequest.getRealAccountNumber());
		int acctValid = acctMapper.accountValid(account);
		
		if (acctValid > 0)
		{
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_FOUND);
			return acctResp;
		}
		
		acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
		acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
		
        return acctResp;
    }
}
