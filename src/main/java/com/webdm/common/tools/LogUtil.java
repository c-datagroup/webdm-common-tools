package com.webdm.common.tools;

import org.slf4j.Logger;

/**
 * Created by fengbingjian on 15/9/10 12:03.
 */
public class LogUtil {

    public static void info(Logger logger, String log, Object ... params){
        logger.info(log, params);
    }
    public static void info(Logger logger, String log){
        logger.info(log);
    }

    public static void info(Logger logger,String log, Throwable throwable){
        logger.info(log,throwable);
    }

    public static void error(Logger logger, String log, Object ... params){
        logger.error(log, params);
    }
    public static void error(Logger logger, String log){
        logger.info(log);
    }

    public static void error(Logger logger,String log, Throwable throwable){
        logger.error(log, throwable);
    }


    public static void debug(Logger logger, String log, Object ... params){
        logger.debug(log, params);
    }
    public static void debug(Logger logger, String log){
        logger.debug(log);
    }

    public static void debug(Logger logger,String log, Throwable throwable){
        logger.debug(log, throwable);
    }


}
