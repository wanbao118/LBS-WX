package com.group.pbox.pvbs.persist.termdeposit;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.group.pbox.pvbs.model.acct.Account;
import com.group.pbox.pvbs.model.acct.AccountBalance;
import com.group.pbox.pvbs.model.acct.TransferHistory;
import com.group.pbox.pvbs.model.termdeposit.TermDepositRate;
import com.group.pbox.pvbs.persist.acct.AccountBalanceMapper;
import com.group.pbox.pvbs.persist.acct.AcctMapper;
import com.group.pbox.pvbs.persist.acct.TransferHistoryMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class TermDepositRateMapperTest
{
    @Resource
    private TermDepositRateMapper termDepositRateMapper;

    

    @Test
    public void TransferHistoryTest()
    {
        
        List<TermDepositRate> list = termDepositRateMapper.fetchAllTermDepositRate();
        for(TermDepositRate term:list){
           System.out.println( term.getTermDeposiInterestRate()+" "+term.getTermDeposiPeriod());
        }
    }

    

}
