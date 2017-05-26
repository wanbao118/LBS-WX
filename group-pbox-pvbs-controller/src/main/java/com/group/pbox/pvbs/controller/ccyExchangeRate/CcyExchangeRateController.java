package com.group.pbox.pvbs.controller.ccyExchangeRate;


import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.ccyEx.IExchangeRateService;
import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateReqModel;
import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateRespModel;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;


@Controller
@RequestMapping("/ccyExRate")
public class CcyExchangeRateController {
	
	private static final Logger sysLogger = Logger.getLogger("customer");
	@Resource
	IExchangeRateService iExchangeRateService;

	@RequestMapping(value="/getCcyExRate")
	@ResponseBody
	public Object getCcyExRate(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody CcyExchangeRateReqModel ccyExchangeRateReqModel){

		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();
		try{
			switch (ccyExchangeRateReqModel.getOperationCode()) {
			case OperationCode.FETCH_ALL_CCYEXRATE:
				ccyExchangeRateRespModel = getAllCcyExchangeRate(ccyExchangeRateReqModel);
				break;
			case OperationCode.FETCH_BY_CURRENCY_CODE:
				ccyExchangeRateRespModel = getCcyExRateByCurrCode(ccyExchangeRateReqModel);
				break;

			default:
				break;
			}
		} 
		catch (Exception e) {
			List<String> errorList = new ArrayList<String>();
            sysLogger.error("[com.group.pbox.pvbs.controller.ccyExchangeRate.CcyExchangeRateController.getAllCcyExchangeRate()]", e);
            errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
            ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
            ccyExchangeRateRespModel.setErrorCode(errorList);
		}
		return ccyExchangeRateRespModel;
	}
	
	private CcyExchangeRateRespModel getAllCcyExchangeRate(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception
	{

		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();

		ccyExchangeRateRespModel = iExchangeRateService.getAllCcyExchangeRate();

		return ccyExchangeRateRespModel;
	}

	
	private CcyExchangeRateRespModel getCcyExRateByCurrCode(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception{
		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();
		
		ccyExchangeRateRespModel = iExchangeRateService.getCcyExRateByCurrCode(ccyExchangeRateReqModel);
		
		return ccyExchangeRateRespModel;
	}
}