package com.group.pbox.pvbs.util.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.net.SyslogAppender;

public class TransactionLog
{

    /**
     * extends Level
     *
     * @author Sevencm
     *
     */
    private static class TransactionLogLevel extends Level
    {
        public TransactionLogLevel(int level, String levelStr,
                int syslogEquivalent)
        {
            super(level, levelStr, syslogEquivalent);
        }
    }

    /**
     * 自定义级别名称，以及级别范围
     */
    private static final Level CustomerLevel = new TransactionLogLevel(20050,
            "TRANSACTION", SyslogAppender.LOG_LOCAL0);

    /**
     * 使用日志打印logger中的log方法
     *
     * @param logger
     * @param objLogInfo
     */
    public static void customerLog(Logger logger, Object objLogInfo)
    {
        logger.log(CustomerLevel, objLogInfo);
    }

}