package com.group.pbox.pvbs.persist.acct;

import java.util.List;

import com.group.pbox.pvbs.model.acct.TransferHistory;

public interface TransferHistoryMapper {
	
	int insertTransferHistory(TransferHistory transferHistory);
	
	List<TransferHistory> inquiryTransferHistory(TransferHistory transferHistory);
		
}
