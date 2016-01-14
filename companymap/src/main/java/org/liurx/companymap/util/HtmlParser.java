package org.liurx.companymap.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.dawning.gridview.common.util.service.commonfunction.export.json.JsonUtil;

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
		String filePath = "/Users/lrx/Documents/my_workspace/companyMap/out/147.html";
		
		Document doc = parseFile(filePath);
		Element infoData = doc.getElementById("companyInfoData");
		
		List<DataNode> dataList = infoData.dataNodes();
//		System.out.println(dataList);
		for (DataNode node:dataList) {
//			System.out.println(node);
			
//			String[] strArray = JsonUtil.getStringArray4Json(node.toString());
//			for (String str:strArray) {
//				System.out.println(str);
//			}
			Object locationStr = JsonUtil.getMap4Json(node.toString()).get("location");
			String[] strArray = JsonUtil.getStringArray4Json(locationStr.toString());
			for (String str:strArray) {
				System.out.println(JsonUtil.getMap4Json(str).get("detailPosition"));
			}
		}
	}
}
