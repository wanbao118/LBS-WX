package com.group.pbox.pvbs.persist.account;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.group.pbox.pvbs.model.account.AccountBalance;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml" })
public class AccountBalanceMapperTest
{
    @Resource
    private AccountBalanceMapper accountBalanceMapper;

    @Test
    public void getAccountBalanceTest()
    {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("1");
        accountBalance.setAccountNum("001000100001");
        accountBalance.setCurrencyCode("RMB");
        AccountBalance result = accountBalanceMapper
                .getAccountBalance(accountBalance);
        assertEquals(result.getAccountId(), accountBalance.getAccountId());
    }

    @Test
    public void insertAccountBalanceTest()
    {
        AccountBalance accountBalance = new AccountBalance();
        accountBalance.setAccountId("1");
        accountBalance.setCurrencyCode("MMM");
        accountBalance.setId("5");
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

}
