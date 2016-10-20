package org.liurx.companymap.logger;
/**
 * 日志类
 * @class MapLogger
 * @date 2015年6月12日 下午4:50:15
 * @author liurx
 * @since 4.0.0
 */
public class MapLogger {
	
	public static void debug(Object obj) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[DEBUG] - ").append(getLineNumber()).append(" - ").append(obj.toString());
		System.out.println(strBuffer.toString());
	}
	
	public static void error(Object obj) {
		StringBuffer strBuffer = new StringBuffer();
		strBuffer.append("[ERROR]-").append(getLineNumber()).append(obj.toString());
		System.err.println(strBuffer.toString());
	}
	
    public static String getLineNumber()
    {
        StackTraceElement ste = new Throwable().getStackTrace()[2];
        return ste.getFileName() + ":" + ste.getLineNumber();
    }


	
}
