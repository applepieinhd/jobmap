package org.liurx.companymap.extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.liurx.companymap.data.Company;
import org.liurx.companymap.util.FileManipulation;
import org.liurx.companymap.util.HttpConfigUtil;
import org.liurx.companymap.util.TypeConverter;

import com.dawning.gridview.common.util.service.commonfunction.export.json.JsonUtil;

public class DataBuffer {
//	private static List<Map> companyList = new ArrayList<Map>();
	private static List<Company> companyList = new ArrayList<Company>();
	private static final String URL_PREFIX = "http://www.lagou.com/gongsi/";
	private static final String SRC_DIR = "/Users/lrx/Documents/my_workspace/companyMap/";
	private static final String TARGET_DIR = "/Users/lrx/Documents/my_workspace/companyMap/out/";

//	private static void extractCompanyProp() {
//		try {
//			for (Map companyMap:companyList) {
//	//			System.out.println(companyMap.get("companyId") + ", name: " + companyMap.get("companyShortName"));
//				String id = companyMap.get("companyId").toString();
//				try {
//					Thread.sleep(500);
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				}
//				String htmlFile = id + ".html";
//				File html = new File (TARGET_DIR + htmlFile);
//				
//				String companyPage = null;
//				if (!html.exists()) {
//					String url = URL_PREFIX + htmlFile;
//					companyPage = HttpConfigUtil.getHttpResponse(url);
//					output(companyPage, TARGET_DIR + htmlFile);
//				} else {
//					companyPage = readFromFile(html);
//				}
//				
//				//多个地址的取第一个
//				int firstIndex = companyPage.indexOf("mlist_li_desc");
//				
//				if (firstIndex > 0) {
//					String address = companyPage.substring(firstIndex+15).replaceAll("</p>.*", "").trim();
//					companyMap.put("address", address);
//				}
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
//		
//		
//		output(companyList, TARGET_DIR + "summary");
//	}


//	private static void output(String content, String target) {
//		FileWriter writer = null;
//		File outFile = new File(target);
//		
//		try {
//			writer = new FileWriter(outFile);
//			writer.write(content);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (writer != null) {
//				try {
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}

//	private static void output(List<Map> list, String target) {
////		System.out.println(list);
//		FileWriter writer = null;
//		String head = "id\tshortName\t\t\taddress\n";
//		File outFile = new File(target);
//		try {
//			writer = new FileWriter(outFile);
//			writer.write(head);
//			for (Map map:list) {
//				writer.write(map.get("companyId") + "\t" + map.get("companyShortName") + "\t\t\t" + map.get("address") + "\n");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			if (writer != null) {
//				try {
//					writer.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//		
//		
//	}

	public static void addToCompanyList(File[] fileList) {
		for (File file:fileList) {
			String content = FileManipulation.readFromFile(file);
			Map firstMap = JsonUtil.getMap4Json(content);
			String value = firstMap.get("result").toString();
			//去掉头尾的[和]
			value = value.substring(1, value.length()-1);
			String[] companyArray = value.split("},");
			
			for (int i=0; i<companyArray.length-1; i++) {
				Map<String, String> jsonMap = JsonUtil.getMap4Json(companyArray[i].concat("}"));
				Company company = TypeConverter.map2Company(jsonMap);
				
				if (company != null) {
					companyList.add(company);
				}
			}
		}
	} 

	
	public static List<Company> getCompanyList() {
		return companyList;
	}
}
