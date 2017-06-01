package com.group.pbox.pvbs.termdeposit;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositReqModel;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositRespModel;
import com.group.pbox.pvbs.model.termdeposit.TermDepositMaster;
import com.group.pbox.pvbs.model.termdeposit.TermDepositRate;
import com.group.pbox.pvbs.persist.termdeposit.TermDepositMapper;
import com.group.pbox.pvbs.persist.termdeposit.TermDepositRateMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

import com.group.pbox.pvbs.util.Utils;

@Service
public class TermDepositServiceImpl implements ITermDepositService {

	@Resource
	TermDepositMapper termDepositMapper;
	
	@Resource
    TermDepositRateMapper termDepositRateMapper;

	public TermDepositRespModel creatTermDeposit(TermDepositReqModel termDepostReqModel) {
		// TODO Auto-generated method stub
		TermDepositRespModel termDepositRespModel = new TermDepositRespModel();

		String termDepositNumber = "";
		String maxTermDepositNumber = termDepositMapper.genereateMaxTDNum();
		if(StringUtils.isBlank(maxTermDepositNumber)){
		    maxTermDepositNumber ="0";
		}
		Integer termDepositNumberTemp = Integer.valueOf(maxTermDepositNumber) + 1;

		switch (termDepositNumberTemp.toString().length()) {
		case 1:
			termDepositNumber = "0000" + termDepositNumberTemp;
			break;
		case 2:
			termDepositNumber = "000" + termDepositNumberTemp;
			break;
		case 3:
			termDepositNumber = "00" + termDepositNumberTemp;
			break;
		case 4:
			termDepositNumber = "0" + termDepositNumberTemp;
			break;
		case 5:
			termDepositNumber = termDepositNumberTemp.toString();
			break;
		}

		Double maturityAmount = new Double(termDepostReqModel.getDepositAmount()
				+ termDepostReqModel.getMaturityInterset());

		Date createDate = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(createDate);
		calendar.add(Calendar.MONTH, Integer.parseInt(termDepostReqModel.getTermPeriod()));

		Date maturityDate = new Date(sdf.format(calendar.getTime()));

		TermDepositMaster termDepositMaster = new TermDepositMaster();
		
		//get rate info.
        TermDepositRate termDepositRate = new TermDepositRate();
        termDepositRate.setTermDeposiPeriod(termDepostReqModel.getTermPeriod());
        termDepositRate = termDepositRateMapper.fetchTermDepositRateByPeriod(termDepositRate);
        termDepositMaster.setTermInterestRate(termDepositRate.getTermDeposiInterestRate());
		
		termDepositMaster.setAccountId(termDepostReqModel.getAccountId());
		termDepositMaster.setId(Utils.getUUID());
		termDepositMaster.setDepositAmount(termDepostReqModel.getDepositAmount());
		termDepositMaster.setDepositNumber(termDepositNumber);
		termDepositMaster.setMaturityAmount(termDepostReqModel.getDepositAmount()*(termDepositRate.getTermDeposiInterestRate()+100)*0.01);
		
		
		
		termDepositMaster.setMaturityDate(maturityDate);
		termDepositMaster.setMaturityInterest(termDepostReqModel.getDepositAmount()*termDepositRate.getTermDeposiInterestRate()*0.01);
		termDepositMaster.setTermInterestRate(termDepositRate.getTermDeposiInterestRate());
		termDepositMaster.setTermPeriod(termDepostReqModel.getTermPeriod());
		termDepositMaster.setCreateTime(new Timestamp(new Date().getTime()));

		boolean result = termDepositMapper.addTermDeposit(termDepositMaster);
		if (result) {
			termDepositRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
		} else {
			termDepositRespModel.setResult(ErrorCode.RESPONSE_ERROR);
		}

		return termDepositRespModel;
	}

	public TermDepositRespModel inquiryTermDeposit(TermDepositReqModel termDepositReqModel) {
		TermDepositRespModel termDepositRespModel = new TermDepositRespModel();
		String accountId = termDepositReqModel.getAccountId();
		String depositNum = termDepositReqModel.getDepositNumber();

		List<TermDepositMaster> termDeposit = termDepositMapper.enquiryTermDeposit(accountId, depositNum);

		if (termDeposit.size() > 0) {
			termDepositRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
		}
		else {
			termDepositRespModel.setResult(ErrorCode.RESPONSE_ERROR);
		}
		
		return termDepositRespModel;
	}

	public TermDepositRespModel reNewal(TermDepositReqModel termDepositReqModel) {
		// TODO Auto-generated method stub
		return null;
	}

	public TermDepositRespModel drawDown(TermDepositReqModel termDepositReqModel) {

		TermDepositRespModel termDepositResp = new TermDepositRespModel();

		List<TermDepositMaster> termDepositData = termDepositMapper
				.enquiryTermDeposit(termDepositReqModel.getAccountNumber(), termDepositReqModel.getDepositNumber());

		if (termDepositData.size() == 0) {
			termDepositResp.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositResp.getErrorCode().add(ErrorCode.RECORD_NOT_FOUND);
			return termDepositResp;
		}
		
		if (OperationCode.DROPDOWN.equals(termDepositData.get(0).getMaturityStatus()))
		{
			termDepositResp.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositResp.getErrorCode().add(ErrorCode.TD_HAS_DROPDOWN);
			return termDepositResp;
		}
		
		Date nowdate = new Date();
		Date maturityDate = termDepositReqModel.getMaturityDate();
		
		if (maturityDate.getTime() > nowdate.getTime())
		{
			termDepositResp.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositResp.getErrorCode().add(ErrorCode.TRANSACTION_IS_NOT_MATURE);
			return termDepositResp;
		}
		
		return termDepositResp;
	}
	
	public TermDepositRespModel updateStatus(TermDepositReqModel termDepositReqModel)
	{
		TermDepositRespModel termDepositResp = new TermDepositRespModel();
		TermDepositMaster termDeposit = new TermDepositMaster();
		termDeposit.setDepositNumber(termDepositReqModel.getDepositNumber());
		int dropDownStatus = termDepositMapper.updateTermDeposit(termDeposit);
		
		if (dropDownStatus > 0)
		{
			termDepositResp.setResult(ErrorCode.RESPONSE_ERROR);
			termDepositResp.getErrorCode().add(ErrorCode.UPDATE_STATUS_FAIL);
			return termDepositResp;
		}
		
		termDepositResp.setResult(ErrorCode.RESPONSE_SUCCESS);
		termDepositResp.getErrorCode().add(ErrorCode.ACCEPT_TRANSACTION);
		return termDepositResp;
	}
}
