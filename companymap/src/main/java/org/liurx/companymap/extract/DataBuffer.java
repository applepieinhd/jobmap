package org.liurx.companymap.extract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.liurx.companymap.data.Company;
import org.liurx.companymap.logger.MapLogger;
import org.liurx.companymap.util.FileManipulation;
import org.liurx.companymap.util.JsonUtil;
import org.liurx.companymap.util.TypeConverter;

public class DataBuffer {
	private static List<Company> companyList = new ArrayList<Company>();//所有公司的地址
	private static List<Company> textList = new ArrayList<Company>();//没有坐标只有文字地址的公司

	public static void addToCompanyList(File[] fileList) {
		for (File file:fileList) {
			MapLogger.debug("file: " + file.getName());
			String content = FileManipulation.readFromFile(file);
			Map firstMap = JsonUtil.getMap4Json(content);
			if (firstMap == null) {
				continue;
			}
			List<Map> companyMapList = (List<Map>) firstMap.get("result");
			for (Map companyMap:companyMapList) {
				Company company = TypeConverter.map2Company(companyMap);
				companyList.add(company);
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
