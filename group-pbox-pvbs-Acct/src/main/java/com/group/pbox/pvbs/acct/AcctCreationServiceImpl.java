package com.group.pbox.pvbs.acct;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.model.acct.Account;
import com.group.pbox.pvbs.persist.acct.AcctMapper;

@Service
public class AcctCreationServiceImpl implements IAcctCreationService
{
    @Resource
    AcctMapper acctMapper;

    public int addAcct(Account acctRequest)
    {
        return acctMapper.addAcct(acctRequest);
    }

    public Integer fetchAcct()
    {
        return acctMapper.fetchAcct();
    }
    
    public int accountValid(Account account)
    {
        return acctMapper.accountValid(account);
    }
}
