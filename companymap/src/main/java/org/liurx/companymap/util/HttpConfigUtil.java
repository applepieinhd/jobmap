package org.liurx.companymap.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

/**
 * 工具类，获取HTTP配置，json转成嵌套的Map
 * @class HttpConfigUtil
 * @date 2015年8月20日 下午5:40:33
 * @author liurx
 * @since 4.0.0
 */
public class HttpConfigUtil {
//	private static GLogger objLogger = MapLogger.getLogger();
	private static final int TIMEOUT = 30000;
	
	public static String getHttpResponse(String allConfigUrl) {
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
	
	@SuppressWarnings("rawtypes")
	public static Map getMap4Json(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Iterator keyIter = jsonObject.keys();
		String key;
		Object value;
		Map<String, Object> valueMap = new HashMap<String, Object>();

		while (keyIter.hasNext()) {
			key = (String) keyIter.next();
			value = jsonObject.get(key);
			
			String valueStr = value.toString();
			if (valueStr.startsWith("{") && valueStr.endsWith("}")) {
				value = getMap4Json(valueStr);
			}
			valueMap.put(key, value);
		}

		return valueMap;
	}

}
