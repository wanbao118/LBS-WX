package com.group.pbox.pvbs.util;

public class ErrorCode
{
    // 0-system level
    // response success
    public static String RESPONSE_SUCCESS = "00000";
    // response error
    public static String RESPONSE_ERROR = "00011";

    // DB operation error.
    public static String DB_OPERATION_ERROR = "00012";

    // 1-business level
    // Account Balance.
    // Currency Not Found
    public static String CURRENCY_NOT_FOUND = "10001";
    // Exceed Limit
    public static String EXCEED_LIMIT = "10002";
    // Insufficient Fund
    public static String INSUFFICIENT_FUNDING = "10003";
    // Record Not Found
    public static String RECORD_NOT_FOUND = "10004";
    // Account Balance Not Zero
    public static String ACCOUNT_BALANCE_NOT_ZERO = "10005";
    // can not withDraw so much money.too more.
    public static String ACCOUNT_BALANCE_LESS = "10006";
    // Account Have Found
    public static String ACCOUNT_HAVE_FOUND = "10007";
}
