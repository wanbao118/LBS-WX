package com.group.pbox.pvbs.persist.account;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.group.pbox.pvbs.persist.acct.AccountBalanceMapper;
import com.group.pbox.pvbs.persist.acct.AcctMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class AccountBalanceMapperTest
{
    @Resource
    private AccountBalanceMapper accountBalanceMapper;

    @Resource
    private AcctMapper accountMapper;

    @Test
    public void getAccountBalanceTest()
    {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("1");
        accountBalance.setAccountNum("001000100001");
        accountBalance.setCurrencyCode("RMB");
        AccountBalance result = accountBalanceMapper.getAccountBalance(accountBalance);
        assertEquals(result.getAccountId(), accountBalance.getAccountId());
    }

    @Test
    @Rollback(true)
    public void insertAccountBalanceTest()
    {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("1");
        accountBalance.setCurrencyCode("MMM");
        accountBalance.setId("7");
        accountBalance.setBalance(27898.98);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        accountBalance.setLastUpatedDate(new Date());
        int result = 0;
        try
        {
            result = accountBalanceMapper.insertAccountBalance(accountBalance);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        // assertEquals(result, 1);
    }

    @Test
    public void getAccountTest()
    {
        Account account = new Account();
        // account.setRealAccountNumber("001000100001");
        account.setId("1");
        account = accountMapper.getAccountInfo(account);
        System.out.println(account.getId());
    }

}
