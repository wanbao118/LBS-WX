package com.group.pbox.pvbs.termdeposit;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositReqModel;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositRespModel;
import com.group.pbox.pvbs.model.termdeposit.TermDepositMaster;
import com.group.pbox.pvbs.persist.termdeposit.TermDepositMapper;
import com.group.pbox.pvbs.util.ErrorCode;
import com.group.pbox.pvbs.util.OperationCode;

import com.group.pbox.pvbs.util.Utils;

@Service
public class TermDepositServiceImpl implements ITermDepositService {

	@Resource
	TermDepositMapper termDepositMapper;

	public TermDepositRespModel creatTermDeposit(TermDepositReqModel termDepostReqModel) {
		// TODO Auto-generated method stub
		TermDepositRespModel termDepositRespModel = new TermDepositRespModel();

		String termDepositNumber = "";
		String maxTermDepositNumber = termDepositMapper.genereateMaxTDNum();
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

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");//格式化对象
		Calendar calendar = Calendar.getInstance();//对象
		calendar.setTime(createDate);//设置当前日期
		calendar.add(Calendar.MONTH, Integer.parseInt(termDepostReqModel.getTermPeriod()));

		Date maturityDate = new Date(sdf.format(calendar.getTime()));

		TermDepositMaster termDepositMaster = new TermDepositMaster();
		termDepositMaster.setAccountId(""/*TODO*/);
		termDepositMaster.setId(Utils.getUUID());
		termDepositMaster.setDepositAmount(termDepostReqModel.getDepositAmount());
		termDepositMaster.setDepositNumber(termDepositNumber);
		termDepositMaster.setMaturityAmount(maturityAmount);
		termDepositMaster.setMaturityDate(maturityDate);
		termDepositMaster.setMaturityInterest(termDepostReqModel.getMaturityInterset());
		termDepositMaster.setTermInterestRate(termDepostReqModel.getTermInterestRate());
		termDepositMaster.setTermPeriod(termDepostReqModel.getTermPeriod());
		termDepositMaster.setCreateDate(new Date());

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
		String accountNum = termDepositReqModel.getTransAccountNum();
		String depositNum = termDepositReqModel.getDepositNumber();

		List<TermDepositMaster> termDeposit = termDepositMapper.enquiryTermDeposit(accountNum, depositNum);

		if (termDeposit.size() > 0) {
			termDepositRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
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
