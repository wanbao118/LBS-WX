package com.group.pbox.pvbs.acct;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.acct.model.AcctRequest;
import com.group.pbox.pvbs.persist.acct.AcctMapper;

@Service
public class AcctCreationServiceImpl implements IAcctCreationService
{
    @Resource
    AcctMapper acctMapper;

    public int addAcct(AcctRequest acctRequest)
    {
        return acctMapper.addAcct(acctRequest.getAcctflag());
    }

    public Integer fetchAcct()
    {
        return acctMapper.fetchAcct();
    }
}
