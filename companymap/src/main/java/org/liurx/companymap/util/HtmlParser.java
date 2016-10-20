package org.liurx.companymap.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.liurx.companymap.logger.MapLogger;
import org.liurx.companymap.util.JsonUtil;

public class HtmlParser {
	
	private static Document parseFile(String filePath) {
		File input = new File(filePath);
		if (input.isFile()) {
			try {
				return Jsoup.parse(input, "UTF-8", "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public static String getData(String filePath, String dataId) {
		Document doc = parseFile(filePath);
		if (doc != null) {
			Element infoData = doc.getElementById(dataId);
			if (infoData != null) {
				List<DataNode> dataList = infoData.dataNodes();
				return dataList.get(0).toString();
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		String filePath = "/Users/lrx/Documents/my_workspace/companyData/html/147.html";
		
		Document doc = parseFile(filePath);
		Element infoData = doc.getElementById("companyInfoData");
		
		List<DataNode> dataList = infoData.dataNodes();
//		MapLogger.debug(dataList);
		for (DataNode node:dataList) {
			ArrayList<Map> locationStr = (ArrayList) JsonUtil.getMap4Json(node.toString()).get("location");
//			Map[] mapArray = JsonUtil.getMapArray4Json(locationStr.toString());
			for (Map location:locationStr) {
				MapLogger.debug(location.get("detailPosition"));
			}
		}
	}
}
