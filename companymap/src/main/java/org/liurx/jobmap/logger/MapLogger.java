package org.liurx.jobmap.logger;
//
//import java.util.Properties;
//
//import com.dawning.gridview.common.gvshare.commonresource.base.export.exception.GridviewException;
//import com.dawning.gridview.common.gvshare.commonresource.base.export.exception.GridviewNullPointerException;
//import com.dawning.gridview.common.log.application.logprint.export.logger.GLogger;
//import com.dawning.gridview.common.log.application.logprint.export.logger.GlobalLog;
//import com.dawning.gridview.common.util.service.commonfunction.export.validator.ParamValidator;
//
///**
// * 日志类
// * @class MapLogger
// * @date 2015年6月12日 下午4:50:15
// * @author liurx
// * @since 4.0.0
// */
public class MapLogger {
//
//	private static GLogger logger = null;
//	// 默认日志文件
//    private static final String LOG_CONFIG_SOURCE_PATH = "logconf/config_common_log4j.properties";
//    // 目标日志文件
//    private static final String LOG_CONFIG_TARGET_PATH = "/config/logconf/config_common_log4j.properties";
//    
//    public static GLogger getLogger() {
//        try {
//            if (logger == null) {
//                logger = createLogger("config.share.log");
//            }
//        } catch (GridviewException e) {
//            e.printStackTrace();
//        }
//        return logger;
//    }
//	
//	private static synchronized GLogger createLogger(String loggerName) throws GridviewNullPointerException {
//
//        if (ParamValidator.validatorParamsIsEmpty(loggerName)) {
//            throw new GridviewNullPointerException("loggerName is NULL!");
//        }
//
//        GLogger gLogger = null;
//
//        try {
//        	gLogger = GlobalLog.getLogger(loggerName, LOG_CONFIG_TARGET_PATH, Activator.class, LOG_CONFIG_SOURCE_PATH);
//        } catch (GridviewException e) {
//            Properties props = new Properties();
//            gLogger = GlobalLog.getLogger(loggerName, props);
//            gLogger.logException(e);
//        }
//        return gLogger;
//    }
}
