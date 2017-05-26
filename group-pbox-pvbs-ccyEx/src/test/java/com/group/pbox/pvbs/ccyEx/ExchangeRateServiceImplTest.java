package com.group.pbox.pvbs.ccyEx;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateRespModel;
import com.group.pbox.pvbs.model.currencyRate.CurrencyRate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:conf/spring-mybatis.xml" })
public class ExchangeRateServiceImplTest {

	@Resource
	IExchangeRateService iExchangeRateService;
	
	@Test
	public void getAllCcyExchangeRate() throws Exception{
		CcyExchangeRateRespModel object = iExchangeRateService.getAllCcyExchangeRate();
		assertNotNull(object);
	}
}
