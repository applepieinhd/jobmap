package org.liurx.jobmap.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

/**
 * 工具类，获取HTTP配置，json转成嵌套的Map
 * @class HttpUtil
 * @date 2015年8月20日 下午5:40:33
 * @author liurx
 * @since 4.0.0
 */
public class HttpUtil {
//	private static GLogger objLogger = MapLogger.getLogger();
	private static final int TIMEOUT = 30000;
	
	public static String get(String allConfigUrl) {
		BufferedReader in = null;
		StringBuffer result = null;
		try {
			
			URI uri = new URI(allConfigUrl);
			URL url = uri.toURL();
			URLConnection connection = url.openConnection();
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.setRequestProperty("Charset", "utf-8");
			connection.setConnectTimeout(TIMEOUT);
			connection.setReadTimeout(TIMEOUT);
        
            connection.connect();
            
            result = new StringBuffer();
            //读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            
            return result.toString();
			
		} catch (Exception e) {
//			objLogger.logException(e);
			e.printStackTrace();
		}finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
//            	objLogger.logException(e2);
            	e2.printStackTrace();
            }
        }
		
		return null;
		
	}
	
//	@SuppressWarnings("rawtypes")
//	public static Map getMap4Json(String jsonString) {
//		JSONObject jsonObject = JSONObject.fromObject(jsonString);
//		Iterator keyIter = jsonObject.keys();
//		String key;
//		Object value;
//		Map<String, Object> valueMap = new HashMap<String, Object>();
//
//		while (keyIter.hasNext()) {
//			key = (String) keyIter.next();
//			value = jsonObject.get(key);
//			
//			String valueStr = value.toString();
//			if (valueStr.startsWith("{") && valueStr.endsWith("}")) {
//				value = getMap4Json(valueStr);
//			}
//			valueMap.put(key, value);
//		}
//
//		return valueMap;
//	}
	
	/**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
	public static String post(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("Charset", "utf-8");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    } 

}
