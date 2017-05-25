package com.group.pbox.pvbs.util;

public class ErrorCode
{
    // 0-system level
    // response success
    public static final String RESPONSE_SUCCESS = "00000";
    // response error
    public static final String RESPONSE_ERROR = "00011";

    // DB operation error.
    public static final String DB_OPERATION_ERROR = "00012";

    public static final String SYSTEM_OPERATION_ERROR = "00012";
    // 1-business level
    // Account Balance.
    // Currency Not Found
    public static final String CURRENCY_NOT_FOUND = "10001";
    // Exceed Limit
    public static final String EXCEED_LIMIT = "10002";
    // Insufficient Fund
    public static final String INSUFFICIENT_FUNDING = "10003";
    // Record Not Found
    public static final String RECORD_NOT_FOUND = "10004";
    // Account Balance Not Zero
    public static final String ACCOUNT_BALANCE_NOT_ZERO = "10005";
    // can not withDraw so much money.too more.
    public static final String ACCOUNT_BALANCE_LESS = "10006";

    // Account Have Found
    public static final String ACCOUNT_HAVE_FOUND = "10007";
    // Account Have Not Found
    public static final String ACCOUNT_HAVE_NOT_FOUND = "10008";
    // Add Account Success
    public static String ADD_ACCOUNT_SUCCESS = "10009";
    // Add Account Failed
    public static final String ADD_ACCOUNT_FAILED = "10010";
}
