package org.liurx.companymap.extract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.liurx.companymap.data.Company;
import org.liurx.companymap.data.Coordinate;
import org.liurx.companymap.param.GlobalParam;
import org.liurx.companymap.util.FileManipulation;
import org.liurx.companymap.util.HtmlParser;

import com.dawning.gridview.common.util.service.commonfunction.export.json.JsonUtil;

public class Entry {
	public static void main(String[] args) {
		File[] srcFile = FileManipulation.listFile(GlobalParam.SRC_DIR + "json");
		
		DataBuffer.addToCompanyList(srcFile);
		List<Company> companyList = DataBuffer.getCompanyList();
		
		//保存到out目录下
		FileManipulation.downloadHtml(companyList);
		extractAddress(companyList);
		System.out.println("size: " + companyList.size());
		for (Company com:companyList) {
			System.out.println(com);
		}
		FileManipulation.output(companyList, GlobalParam.TARGET_DIR + "summary");
	}

	private static void extractAddress(List<Company> companyList) {
		for (Company company:companyList) {
			String path = GlobalParam.TARGET_DIR + company.getId() + ".html";
//			FileManipulation.readFromFile(GlobalParam.TARGET_DIR + company.getId() + ".html");
			String companyInfo = HtmlParser.getData(path, GlobalParam.DATA_TAG);
			
			Object locationStr = JsonUtil.getMap4Json(companyInfo.toString()).get("location");
			String[] strArray = JsonUtil.getStringArray4Json(locationStr.toString());
			List<String> addressList = new ArrayList<String>();
			List<Coordinate> positionList = new ArrayList<Coordinate>(); 
			for (String str:strArray) {
				Map detailMap = JsonUtil.getMap4Json(str);
				if (detailMap != null) {
					addressList.add(detailMap.get("detailPosition").toString());
					positionList.add(new Coordinate(detailMap.get("longitude").toString(), detailMap.get("latitude").toString()));
				}
			}
			company.setAddressList(addressList);
			company.setCoordinateList(positionList);
			
		}
		
	}

}
