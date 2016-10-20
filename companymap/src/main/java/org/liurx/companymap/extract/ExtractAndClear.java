package org.liurx.companymap.extract;

import java.io.File;
import java.util.List;

import org.liurx.companymap.data.Company;
import org.liurx.companymap.logger.MapLogger;
import org.liurx.companymap.param.GlobalParam;
import org.liurx.companymap.util.FileManipulation;
import org.liurx.companymap.util.HttpUtil;
import org.liurx.companymap.util.JsonUtil;

/**
 * 从云端删除指定公司数据
 * @class ExtractAndClear
 * @date 2016年10月19日 下午7:49:42
 * @author liurx
 * @since 4.0.0
 */
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
			if (!FileManipulation.hasPost(company)) {
				continue;
			}
			StringBuffer param = genParam(company);
			
			String result = HttpUtil.post(GlobalParam.DELETE_POI, param.toString());
			
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//批量操作的返回码为21
			if (!GlobalParam.BATCH_SUCCESS.equals(JsonUtil.getMap4Json(result).get("status").toString())) {
				MapLogger.error("fail: " + result);
				MapLogger.debug("param is: " + param);
				break;
			}
			MapLogger.debug("clear " + company.getName());
		}
	}
	
	private static StringBuffer genParam(Company company) {
		StringBuffer str = new StringBuffer();
		return str.append("title=").append(company.getShortName())
				.append("&is_total_del=1")
				.append("&geotable_id=")
				.append(GlobalParam.LBS_TABLE_ID)
				.append("&ak=ABrY0GjICSfXogULBMBdq3uK");
	}

}
