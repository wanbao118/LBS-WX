package com.group.pbox.pvbs.controller.ccyExchangeRate;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.group.pbox.pvbs.acct.IAcctCreationService;
import com.group.pbox.pvbs.ccyEx.IExchangeRateService;
import com.group.pbox.pvbs.clientmodel.acct.AcctReqModel;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespModel;
import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateReqModel;
import com.group.pbox.pvbs.clientmodel.ccyEx.CcyExchangeRateRespModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfReqModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespData;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionReqModel;
import com.group.pbox.pvbs.clientmodel.transaction.TransactionRespModel;
import com.group.pbox.pvbs.clientmodel.user.UserReqModel;
import com.group.pbox.pvbs.clientmodel.user.UserRespModel;
import com.group.pbox.pvbs.sysconf.ISysConfService;
import com.group.pbox.pvbs.transaction.IAccountBalanceService;
import com.group.pbox.pvbs.user.IUserService;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

@Controller
@RequestMapping("/ccyExRate")
public class CcyExchangeRateController {

	private static final Logger sysLogger = Logger.getLogger("customer");
	@Resource
	IExchangeRateService iExchangeRateService;
	@Resource
	ISysConfService sysConfService;
	@Resource
	IUserService userService;
	@Resource
	IAccountBalanceService accountBalanceService;
	@Resource
	IAcctCreationService acctCreationService;

	@RequestMapping(value = "/getCcyExRate")
	@ResponseBody
	public Object getCcyExRate(final HttpServletRequest request, final HttpServletResponse response,
			@RequestBody CcyExchangeRateReqModel ccyExchangeRateReqModel) {

		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();
		
		try {
			switch (ccyExchangeRateReqModel.getOperationCode()) {
			case OperationCode.FETCH_ALL_CCYEXRATE:
				ccyExchangeRateRespModel = getAllCcyExchangeRate(ccyExchangeRateReqModel);
				break;
			case OperationCode.FETCH_BY_CURRENCY_CODE:
				ccyExchangeRateRespModel = getCcyExRateByCurrCode(ccyExchangeRateReqModel);
				break;
			case OperationCode.BUY_CURRENCY:
				ccyExchangeRateRespModel = buyForEx(ccyExchangeRateReqModel);
				break;
			case OperationCode.SELL_CURRENCY:
				ccyExchangeRateRespModel = sellForEx(ccyExchangeRateReqModel);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			List<String> errorList = new ArrayList<String>();
			sysLogger.error(
					"[com.group.pbox.pvbs.controller.ccyExchangeRate.CcyExchangeRateController.getAllCcyExchangeRate()]",
					e);
			errorList.add(ErrorCode.SYSTEM_OPERATION_ERROR);
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			ccyExchangeRateRespModel.setErrorCode(errorList);
		}
		return ccyExchangeRateRespModel;
	}

	private CcyExchangeRateRespModel sellForEx(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception {
		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();

		//1.Check Currency Code
		ccyExchangeRateRespModel = checkCcyCode(ccyExchangeRateReqModel);
		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		//2.Check Account Num Record 12
		ccyExchangeRateRespModel = checkAcctValid(ccyExchangeRateReqModel);
		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		//3.check User ID and get limit
		ccyExchangeRateRespModel = checkUserIdAndGetLimit(ccyExchangeRateReqModel);
		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		//check CcyRate
		ccyExchangeRateRespModel = checkCcyRate(ccyExchangeRateReqModel);
		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		//update balance
		double rateSell = ccyExchangeRateRespModel.getListData().get(0).getExchangeRate().doubleValue();
		TransactionRespModel transactionRespModelTargetSell = new TransactionRespModel();
		TransactionRespModel transactionRespModelSourceSell = new TransactionRespModel();

		TransactionReqModel transactionReqModelTargetSell = new TransactionReqModel();
		transactionReqModelTargetSell.setAccountNumber(ccyExchangeRateReqModel.getAcctNumber());
		transactionReqModelTargetSell.setCurrency(ccyExchangeRateReqModel.getCurrencyCode());
		transactionReqModelTargetSell.setAmount(ccyExchangeRateReqModel.getChangeAmount());
		transactionRespModelTargetSell = accountBalanceService.withDrawal(transactionReqModelTargetSell);

		if (StringUtils.equalsIgnoreCase(transactionRespModelTargetSell.getResult(), ErrorCode.RESPONSE_ERROR)) {
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			ccyExchangeRateRespModel.setErrorCode(transactionRespModelTargetSell.getErrorCode());
			return ccyExchangeRateRespModel;
		}
		// Target foreign currency
		TransactionReqModel transactionReqModelSourceSell = new TransactionReqModel();
		transactionReqModelSourceSell.setAccountNumber(ccyExchangeRateReqModel.getAcctNumber());
		transactionReqModelSourceSell.setAmount(ccyExchangeRateReqModel.getChangeAmount() / rateSell);
		transactionReqModelSourceSell.setCurrency("RMB");
		transactionRespModelSourceSell = accountBalanceService.deposit(transactionReqModelSourceSell);

		if (!StringUtils.equalsIgnoreCase(transactionRespModelSourceSell.getResult(), ErrorCode.RESPONSE_SUCCESS)) {
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			ccyExchangeRateRespModel.setErrorCode(transactionRespModelSourceSell.getErrorCode());
			return ccyExchangeRateRespModel;
		}
		return ccyExchangeRateRespModel;
	}

	private CcyExchangeRateRespModel buyForEx(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception {
		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();

		//1.Check Currency Code
		ccyExchangeRateRespModel = checkCcyCode(ccyExchangeRateReqModel);
		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		//2.Check Account Num Record 12
		ccyExchangeRateRespModel = checkAcctValid(ccyExchangeRateReqModel);

		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		//3.check User ID and get limit
		ccyExchangeRateRespModel = checkUserIdAndGetLimit(ccyExchangeRateReqModel);
		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		//check CcyRate
		ccyExchangeRateRespModel = checkCcyRate(ccyExchangeRateReqModel);
		if (StringUtils.equalsIgnoreCase(ccyExchangeRateRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			return ccyExchangeRateRespModel;
		}

		double rate = ccyExchangeRateRespModel.getListData().get(0).getExchangeRate().doubleValue();
		TransactionRespModel transactionRespModelTarget = new TransactionRespModel();
		TransactionRespModel transactionRespModelSource = new TransactionRespModel();

		// Source RMB default
		TransactionReqModel transactionReqModelSource = new TransactionReqModel();
		transactionReqModelSource.setAccountNumber(ccyExchangeRateReqModel.getAcctNumber());
		transactionReqModelSource.setAmount(ccyExchangeRateReqModel.getChangeAmount() / rate);

		SysConfReqModel sysConfReqModel = new SysConfReqModel();
		sysConfReqModel.setItem("Primary_Ccy_Code");
		SysConfRespModel sysConfRespModel = new SysConfRespModel();
		sysConfRespModel = sysConfService.getAllSysConfByParam(sysConfReqModel);

		transactionReqModelSource.setCurrency(sysConfRespModel.getListData().get(0).getValue());
		transactionRespModelSource = accountBalanceService.withDrawal(transactionReqModelSource);

		if (StringUtils.equalsIgnoreCase(transactionRespModelSource.getResult(), ErrorCode.RESPONSE_ERROR)) {
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			ccyExchangeRateRespModel.setErrorCode(transactionRespModelSource.getErrorCode());
			return ccyExchangeRateRespModel;
		}
		// Target foreign currency
		TransactionReqModel transactionReqModelTarget = new TransactionReqModel();
		transactionReqModelTarget.setAccountNumber(ccyExchangeRateReqModel.getAcctNumber());
		transactionReqModelTarget.setAmount(ccyExchangeRateReqModel.getChangeAmount());
		transactionReqModelTarget.setCurrency(ccyExchangeRateReqModel.getCurrencyCode());
		transactionRespModelTarget = accountBalanceService.deposit(transactionReqModelTarget);

		if (!StringUtils.equalsIgnoreCase(transactionRespModelTarget.getResult(), ErrorCode.RESPONSE_SUCCESS)) {
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			ccyExchangeRateRespModel.setErrorCode(transactionRespModelSource.getErrorCode());
			return ccyExchangeRateRespModel;
		}
		return ccyExchangeRateRespModel;
	}

	private CcyExchangeRateRespModel getAllCcyExchangeRate(CcyExchangeRateReqModel ccyExchangeRateReqModel)
			throws Exception {

		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();

		ccyExchangeRateRespModel = iExchangeRateService.getAllCcyExchangeRate();

		return ccyExchangeRateRespModel;
	}

	private CcyExchangeRateRespModel getCcyExRateByCurrCode(CcyExchangeRateReqModel ccyExchangeRateReqModel)
			throws Exception {
		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();

		ccyExchangeRateRespModel = iExchangeRateService.getCcyExRateByCurrCode(ccyExchangeRateReqModel);

		return ccyExchangeRateRespModel;
	}

	private CcyExchangeRateRespModel checkCcyCode(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception {
		CcyExchangeRateRespModel currencyExchangeRespModel = new CcyExchangeRateRespModel();
		currencyExchangeRespModel = checkCurrencySurpport(ccyExchangeRateReqModel);
		return currencyExchangeRespModel;

	}

	private CcyExchangeRateRespModel checkAcctValid(CcyExchangeRateReqModel ccyExchangeRateReqModel) throws Exception {
		CcyExchangeRateRespModel currencyExchangeRespModel = new CcyExchangeRateRespModel();

		AcctReqModel acctReqModel = new AcctReqModel();
		acctReqModel.setRealAccountNumber(ccyExchangeRateReqModel.getAcctNumber());
		AcctRespModel acctRespModel = new AcctRespModel();

		acctRespModel = acctCreationService.accountValidByRealNum(acctReqModel);

		if (StringUtils.equalsIgnoreCase(acctRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			currencyExchangeRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			currencyExchangeRespModel.setErrorCode(acctRespModel.getErrorCode());
		}

		return currencyExchangeRespModel;
	}

	private CcyExchangeRateRespModel checkUserIdAndGetLimit(CcyExchangeRateReqModel exchangeRateReqModel) {
		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();
		UserReqModel userReqModel = new UserReqModel();

		/*read userId from session*/
		userReqModel.setUserId(exchangeRateReqModel.getUserId());
		UserRespModel userRespModel = userService.fetchUserByUserId(userReqModel);

		if (StringUtils.equalsIgnoreCase(userRespModel.getResult(), ErrorCode.RESPONSE_ERROR)) {
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			ccyExchangeRateRespModel.getErrorCode().add(ErrorCode.RECORD_NOT_FOUND);

			return ccyExchangeRateRespModel;
		}

		if (exchangeRateReqModel.getChangeAmount() > userRespModel.getListData().get(0).getExchangeRateLimit()) {
			ccyExchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			ccyExchangeRateRespModel.getErrorCode().add(ErrorCode.EXCEED_LIMIT);
		}

		return ccyExchangeRateRespModel;
	}

	private CcyExchangeRateRespModel checkCcyRate(CcyExchangeRateReqModel exchangeRateReqModel) throws Exception {
		CcyExchangeRateRespModel ccyExchangeRateRespModel = new CcyExchangeRateRespModel();
		ccyExchangeRateRespModel = iExchangeRateService.getCcyExRateByCurrCode(exchangeRateReqModel);

		return ccyExchangeRateRespModel;

	}

	private CcyExchangeRateRespModel checkCurrencySurpport(CcyExchangeRateReqModel exchangeRateReqModel)
			throws Exception {
		CcyExchangeRateRespModel exchangeRateRespModel = new CcyExchangeRateRespModel();
		boolean result = false;
		List<String> errorList = new ArrayList<String>();

		// check currency surpport
		SysConfReqModel sysConfReqModel = new SysConfReqModel();

		List<SysConfRespData> listSysConfRespData = new ArrayList<SysConfRespData>();
		sysConfReqModel.setItem("Support_Ccy");
		SysConfRespModel supportCcyResp = sysConfService.getAllSysConfByParam(sysConfReqModel);
		listSysConfRespData.addAll(supportCcyResp.getListData());

		sysConfReqModel.setItem("Primary_Ccy_Code");
		supportCcyResp = sysConfService.getAllSysConfByParam(sysConfReqModel);
		listSysConfRespData.addAll(supportCcyResp.getListData());

		for (SysConfRespData sysConfRespData : listSysConfRespData) {
			if (StringUtils.equalsIgnoreCase(sysConfRespData.getValue(), exchangeRateReqModel.getCurrencyCode())) {
				result = true;
				break;
			}
		}
		if (!result) {
			errorList.add(ErrorCode.CURRENCY_NOT_FOUND);
			exchangeRateRespModel.setResult(ErrorCode.RESPONSE_ERROR);
			exchangeRateRespModel.setErrorCode(errorList);
		}
		return exchangeRateRespModel;
	}
}