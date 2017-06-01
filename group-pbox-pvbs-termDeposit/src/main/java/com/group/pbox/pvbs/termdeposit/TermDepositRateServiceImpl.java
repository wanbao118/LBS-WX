package com.group.pbox.pvbs.termdeposit;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositRateRespData;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositRateRespModel;
import com.group.pbox.pvbs.clientmodel.termdeposit.TermDepositReqModel;
import com.group.pbox.pvbs.model.termdeposit.TermDepositRate;
import com.group.pbox.pvbs.persist.termdeposit.TermDepositRateMapper;
import com.group.pbox.pvbs.util.ErrorCode;

@Service
public class TermDepositRateServiceImpl implements ITermDepositRateService {

	@Resource
	TermDepositRateMapper termDepositRateMapper;

	public TermDepositRateRespModel inquiryTermDepositRate(TermDepositReqModel termDepositReqModel) throws Exception {
		TermDepositRateRespModel termDepositRateRespModel = new TermDepositRateRespModel();
		List<TermDepositRateRespData> termDepositRateRespDataList = new ArrayList<TermDepositRateRespData>();

		TermDepositRate termDepositRate = new TermDepositRate();
		String period = termDepositReqModel.getTermPeriod();
		termDepositRate.setTermDeposiPeriod(period);
		termDepositRate = termDepositRateMapper.fetchTermDepositRateByPeriod(termDepositRate);

		if (termDepositRate != null) {
			TermDepositRateRespData termDepositRateRespData = new TermDepositRateRespData();
			termDepositRateRespData.setId(termDepositRate.getId());
			termDepositRateRespData.setTermDeposiInterestRate(termDepositRate.getTermDeposiInterestRate());
			termDepositRateRespData.setTermDeposiPeriod(termDepositRate.getTermDeposiPeriod());

			termDepositRateRespDataList.add(termDepositRateRespData);
			termDepositRateRespModel.setListData(termDepositRateRespDataList);

			termDepositRateRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
		} else {
			termDepositRateRespModel.setResult(ErrorCode.RECORD_NOT_FOUND);
		}

		return termDepositRateRespModel;
	}

	public TermDepositRateRespModel inquiryAllTermDepositRate() throws Exception {
		TermDepositRateRespModel termDepositRateRespModel = new TermDepositRateRespModel();
		List<TermDepositRateRespData> termDepositRateRespDataList = new ArrayList<TermDepositRateRespData>();

		List<TermDepositRate> termDepositList = new ArrayList<TermDepositRate>();

		termDepositList = termDepositRateMapper.fetchAllTermDepositRate();

		if (termDepositList.size() > 0) {
			for (TermDepositRate termDepositRate : termDepositList) {
				TermDepositRateRespData termDepositRateRespData = new TermDepositRateRespData();

				termDepositRateRespData.setId(termDepositRate.getId());
				termDepositRateRespData.setTermDeposiInterestRate(termDepositRate.getTermDeposiInterestRate());
				termDepositRateRespData.setTermDeposiPeriod(termDepositRate.getTermDeposiPeriod());

				termDepositRateRespDataList.add(termDepositRateRespData);
			}

			termDepositRateRespModel.setListData(termDepositRateRespDataList);
			termDepositRateRespModel.setResult(ErrorCode.RESPONSE_SUCCESS);
		} else {
			termDepositRateRespModel.setResult(ErrorCode.RECORD_NOT_FOUND);
		}

		return termDepositRateRespModel;
	}

}
