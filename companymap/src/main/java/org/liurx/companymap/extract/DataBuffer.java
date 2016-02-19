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
import org.liurx.companymap.util.HttpUtil;
import org.liurx.companymap.util.TypeConverter;

import com.dawning.gridview.common.util.service.commonfunction.export.json.JsonUtil;

public class DataBuffer {
//	private static List<Map> companyList = new ArrayList<Map>();
	private static List<Company> companyList = new ArrayList<Company>();
	private static List<Company> textList = new ArrayList<Company>();//没有坐标只有文字地址的公司
	private static final String URL_PREFIX = "http://www.lagou.com/gongsi/";
	private static final String SRC_DIR = "/Users/lrx/Documents/my_workspace/companyMap/";
	private static final String TARGET_DIR = "/Users/lrx/Documents/my_workspace/companyMap/out/";

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

	public static void addToTextList(Company company) {
		textList.add(company);
	}

	public static List<Company> getTextList() {
		return textList;
	}
	
	
}
