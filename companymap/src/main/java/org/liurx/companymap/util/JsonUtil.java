package org.liurx.companymap.util;

import java.io.IOException;
import java.util.Map;

import org.liurx.companymap.logger.MapLogger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	public static Map getMap4Json(String jsonString) {
		try {
			return mapper.readValue(jsonString, Map.class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Map[] getMapArray4Json(String jsonString) {
		try {
			return mapper.readValue(jsonString, Map[].class);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
//		String json = "{\"a\":\"1\", \"b\":\"2\", \"c\":\"3\"}";
//		String jsonArray = "[{\"a\":\"1\", \"b\":\"2\", \"c\":\"3\"}, {\"a\":\"4\", \"b\":\"5\", \"c\":\"6\"}]";
////		MapLogger.debug(JsonUtil.getMap4Json(json));
//		Map[] strB = JsonUtil.getMapArray4Json(jsonArray);
//		
//		for (Map str:strB) {
//			MapLogger.debug(str);
//		}
		
		String c = "{\"a\":\"1\",\"b\":\"2\",\"employees\":[{\"firstName\":\"Bill\",\"lastName\":\"Gates\"},{\"firstName\":\"George\",\"lastName\":\"Bush\"},{\"firstName\":\"Thomas\",\"lastName\":\"Carter\"}]}";
		MapLogger.debug(JsonUtil.getMap4Json(c));
	}

}
