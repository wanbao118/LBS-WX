package com.group.pbox.pvbs.acct;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.acct.AcctReqModel;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespData;
import com.group.pbox.pvbs.clientmodel.acct.AcctRespModel;
import com.group.pbox.pvbs.clientmodel.sysconf.SysConfRespData;
import com.group.pbox.pvbs.model.acct.Account;
import com.group.pbox.pvbs.model.acct.AccountBalance;
import com.group.pbox.pvbs.model.acct.AccountMaster;
import com.group.pbox.pvbs.persist.acct.AccountBalanceMapper;
import com.group.pbox.pvbs.persist.acct.AcctMapper;
import com.group.pbox.pvbs.persist.acct.AcctMasterMapper;
import com.group.pbox.pvbs.util.Constant;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.Utils;

@Service
public class AcctCreationServiceImpl implements IAcctCreationService {
	@Resource
	AcctMapper acctMapper;
	@Resource
	AcctMasterMapper acctMasterMapper;
	@Resource
	AccountBalanceMapper accountBalanceMapper;

	public AcctRespModel addAcct(AcctReqModel acctRequest, List<SysConfRespData> listSysConfRespData) throws Exception {
		AcctRespModel acctResp = new AcctRespModel();
		AccountMaster accountMaster = new AccountMaster();
		AccountBalance accountBalance = new AccountBalance();

		Account account = new Account();
		account.setId(Utils.getUUID());
		account.setAccountNumber(acctRequest.getAccountNumber());
		account.setBranchNumber(acctRequest.getBranchNumber());
		account.setClearingCode(acctRequest.getClearingCode());
		account.setRealAccountNumber(acctRequest.getRealAccountNumber());
		String acct = null;
		int acctValid = acctMapper.accountValid(account);

		if (acctValid > 0) {
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_FOUND);
			return acctResp;
		}

		String maxAcctNumber = acctMapper.fetchAcct();
		Integer newAcctNumber = Integer.valueOf(maxAcctNumber) + 1;

		switch (newAcctNumber.toString().length()) {
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
		account.setRealAccountNumber(account.getClearingCode() + account.getBranchNumber() + acct);
		account.setStatus(0);

		accountMaster.setId(Utils.getUUID());
		accountMaster.setAccountId(account.getId());
		accountMaster.setCustomerName(acctRequest.getCustomerName());
		accountMaster.setCustomerId(acctRequest.getCustomerId());
		accountMaster.setAddress(acctRequest.getAddress());
		accountMaster.setContactAddress(acctRequest.getContactAddress());
		accountMaster.setContactNumber(acctRequest.getContactNumber());
		String date = acctRequest.getDateOfBirth();
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		Date insertdate = format.parse(date);
		accountMaster.setDateOfBirth(insertdate);
		accountMaster.setWechatId(acctRequest.getWechatId());
		accountMaster.setEmployment(acctRequest.getEmployment());

		int addAcct = acctMapper.addAcct(account);
		int addMaster = acctMasterMapper.insertAccountMaster(accountMaster);
		int addBalance = 0;

		for (int i = 0; i < listSysConfRespData.size(); i++) {
			accountBalance.setId(Utils.getUUID());
			accountBalance.setAccountId(account.getId());
			accountBalance.setCurrencyCode(listSysConfRespData.get(i).getValue());
			accountBalance.setBalance(0);
			SimpleDateFormat timeFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date newDate = new Date();
			newDate = timeFormater.parse(timeFormater.format(newDate));
			accountBalance.setLastUpatedDate(newDate);

			addBalance = accountBalanceMapper.insertAccountBalance(accountBalance);
		}

		if (addAcct > 0 && addMaster > 0 && addBalance > 0) {
			acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
			acctResp.getErrorCode().add(ErrorCode.ADD_ACCOUNT_SUCCESS);
			return acctResp;
		}

		acctResp.setResult(ErrorCode.RESPONSE_ERROR);
		acctResp.getErrorCode().add(ErrorCode.ADD_ACCOUNT_FAILED);

		return acctResp;
	}

	public AcctRespModel accountValid(AcctReqModel acctRequest) throws Exception {
		AcctRespModel acctResp = new AcctRespModel();
		Account account = new Account();
		account.setId(Utils.getUUID());
		account.setAccountNumber(acctRequest.getAccountNumber());
		account.setBranchNumber(acctRequest.getBranchNumber());
		account.setClearingCode(acctRequest.getClearingCode());
		account.setRealAccountNumber(acctRequest.getRealAccountNumber());
		int acctValid = acctMapper.accountValid(account);

		if (acctValid > 0) {
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_FOUND);
			return acctResp;
		}

		acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
		acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);

		return acctResp;
	}

	public AcctRespModel editAcct(AcctReqModel acctRequest) throws Exception {
		AcctRespModel acctResp = new AcctRespModel();
		AccountMaster accountMaster = new AccountMaster();

		accountMaster.setAccountId(acctRequest.getAccountId());
		accountMaster.setAddress(acctRequest.getAddress());
		accountMaster.setContactAddress(acctRequest.getContactAddress());
		accountMaster.setContactNumber(acctRequest.getContactNumber());
		accountMaster.setWechatId(acctRequest.getWechatId());

		int editAcctMaster = acctMasterMapper.editAcctMaster(accountMaster);

		if (editAcctMaster > 0) {
			acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
			acctResp.getErrorCode().add(ErrorCode.EDIT_ACCOUNT_MASTER_SUCCESS);
			return acctResp;
		}
		acctResp.setResult(ErrorCode.RESPONSE_ERROR);
		acctResp.getErrorCode().add(ErrorCode.EDIT_ACCOUNT_MASTER_FAILED);
		return acctResp;

	}

	public AcctRespModel accountValidByRealNum(AcctReqModel acctRequest) throws Exception {
		AcctRespModel acctResp = new AcctRespModel();
		Account account = new Account();
		account.setRealAccountNumber(acctRequest.getRealAccountNumber());
		int vaild = acctMapper.accountValidByRealNum(account);

		if (vaild == 0) {
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
			return acctResp;
		}

		acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
		acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_FOUND);

		return acctResp;
	}

	public AcctRespModel closeAcct(AcctReqModel acctRequest) throws Exception {
		Account account = new Account();
		AcctRespModel acctResp = new AcctRespModel();
		String realAccountNumber = acctRequest.getRealAccountNumber();
		account.setRealAccountNumber(realAccountNumber);

		int acctValid = 0;
		if (account.getRealAccountNumber().length() == Constant.FIVE) {
			account.setAccountNumber(account.getRealAccountNumber());
			acctValid = acctMapper.accountValid(account);
		}
		if (account.getRealAccountNumber().length() == Constant.TWELVE) {
			acctValid = acctMapper.accountValidByRealNum(account);
		}

		if (acctValid == 0) {
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
			return acctResp;
		}

		int status = acctMapper.fetchAcctStatus(account);
		if (status == 1) {

			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_CLOSED);
			return acctResp;
		}
		List<AccountBalance> balanceData = new ArrayList<AccountBalance>();
		balanceData = accountBalanceMapper.getBalance(realAccountNumber);
		// double balance = accountBalanceMapper.getBalance(realAccountNumber);
		int numOfZero = 0;
		for (int i = 0; i < balanceData.size(); i++) {
			if (balanceData.get(i).getBalance() == 0.00) {
				numOfZero += 1;
			}
		}

		if (numOfZero != balanceData.size()) {
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_BALANCE_NOT_ZERO);
			return acctResp;
		}

		int result = acctMapper.closeStatus(account);

		if (result > 0) {
			acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
			acctResp.getErrorCode().add(ErrorCode.CLOSE_ACCOUNT_SUCCESS);
			return acctResp;
		}

		acctResp.setResult(ErrorCode.RESPONSE_ERROR);
		acctResp.getErrorCode().add(ErrorCode.CLOSE_ACCOUNT_FAILED);

		return acctResp;

	}

	public AcctRespModel enquiryAcctInfo(AcctReqModel acctRequest) throws Exception {
		AcctRespModel acctResp = new AcctRespModel();
		String realAcctNum = acctRequest.getRealAccountNumber();

		Account account = new Account();
		account.setRealAccountNumber(acctRequest.getRealAccountNumber());
		int acctValid = 0;
		if (account.getRealAccountNumber().length() == Constant.FIVE) {
			account.setAccountNumber(account.getRealAccountNumber());
			acctValid = acctMapper.accountValid(account);
		}
		if (account.getRealAccountNumber().length() == Constant.TWELVE) {
			acctValid = acctMapper.realAcctNum(account);
		}

		if (acctValid > 0) {

			// get spereate page infor.
			Map<String, String> params = acctRequest.getParams();
			String currentPage = params.get(Constant.PAGE_CURRENT);
			String pageRecords = params.get(Constant.PAGE_RECORDS);
			AccountMaster accountMaster = new AccountMaster();
			// set query page infor.
			if (StringUtils.isNotBlank(currentPage) && StringUtils.isNotBlank(pageRecords)) {
				accountMaster.setQueryPageParams(Integer.parseInt(currentPage), Integer.parseInt(pageRecords));
			}
			List<AccountMaster> result = acctMasterMapper.enquiryAcctInfo(realAcctNum, accountMaster.getPageStartRow(),
					accountMaster.getPageRecorders());
			List<AccountMaster> resultCount = acctMasterMapper.enquiryAcctInfoCount(realAcctNum,
					accountMaster.getPageStartRow(), accountMaster.getPageRecorders());
			Map<String, String> respParams = accountMaster.setRespPageParams(result, resultCount);
			acctResp.setParams(respParams);

			acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
			acctResp.setListData(result);
			return acctResp;
		}

		acctResp.setResult(ErrorCode.RESPONSE_ERROR);
		acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
		return acctResp;
	}

	public AcctRespModel enquireBalance(AcctReqModel acctRequest) throws Exception {
		AcctRespModel acctResp = new AcctRespModel();
		Account account = new Account();
		account.setRealAccountNumber(acctRequest.getRealAccountNumber());
		int acctValid = acctMapper.accountValidByRealNum(account);

		if (acctValid == 0) {
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ACCOUNT_HAVE_NOT_FOUND);
			return acctResp;
		}

		List<AccountBalance> accountBalance = new ArrayList<AccountBalance>();
		accountBalance = accountBalanceMapper.enquireAccountBalance(acctRequest.getRealAccountNumber());

		if (accountBalance == null) {
			acctResp.setResult(ErrorCode.RESPONSE_ERROR);
			acctResp.getErrorCode().add(ErrorCode.ENQUIRE_BALANCE_FAILED);
			return acctResp;
		}
		acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
		acctResp.getErrorCode().add(ErrorCode.ENQUIRE_BALANCE_SUCCESS);
		acctResp.setListData(accountBalance);

		return acctResp;
	}
	
	public AcctRespModel getAcctInfoByRealNum(AcctReqModel acctRequest) throws Exception
	{
		AcctRespModel acctResp = new AcctRespModel();
		List<AcctRespData> acctDataList = new ArrayList<AcctRespData>();
		List<Account> resultAccInfo = acctMapper.getAcctInfoByRealNum(acctRequest.getRealAccountNumber());
		for (int i = 0; i < resultAccInfo.size(); i++) {
			AcctRespData acctRespData = new AcctRespData();
			BeanUtils.copyProperties(acctRespData,resultAccInfo.get(i));
			acctDataList.add(acctRespData);
		}
		acctResp.setAcctData(acctDataList);
		acctResp.setResult(ErrorCode.RESPONSE_SUCCESS);
		
		return acctResp;
	}
}
