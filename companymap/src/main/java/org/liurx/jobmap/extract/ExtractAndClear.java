package org.liurx.jobmap.extract;

import java.io.File;
import java.util.List;

import org.liurx.jobmap.data.Company;
import org.liurx.jobmap.param.GlobalParam;
import org.liurx.jobmap.util.FileManipulation;
import org.liurx.jobmap.util.HttpUtil;

import com.dawning.gridview.common.util.service.commonfunction.export.json.JsonUtil;

public class ExtractAndClear {
	public static void main(String[] args) {
		//取json数据，解析company id
		File[] srcFile = FileManipulation.listFile(GlobalParam.SRC_DIR + "json/json");
		
		DataBuffer.addToCompanyList(srcFile);
		List<Company> companyList = DataBuffer.getCompanyList();
		
		clearData(companyList);
	}

	private static void clearData(List<Company> companyList) {
		for (Company company:companyList) {
			StringBuffer param = genParam(company);
			
			String result = HttpUtil.post(GlobalParam.DELETE_POI, param.toString());
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//批量操作的返回码为21
			if (!GlobalParam.BATCH_SUCCESS.equals(JsonUtil.getMap4Json(result).get("status").toString())) {
				System.err.println("fail: " + result);
				System.out.println(param);
				break;
			}
		}
		
	}
	
	private static StringBuffer genParam(Company company) {
		StringBuffer str = new StringBuffer();
		return str.append("title=").append(company.getShortName())
				.append("&is_total_del=1")
				.append("&geotable_id=132060&ak=ABrY0GjICSfXogULBMBdq3uK");
	}

}
