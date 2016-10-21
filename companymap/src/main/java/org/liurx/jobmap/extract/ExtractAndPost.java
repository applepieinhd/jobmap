package org.liurx.jobmap.extract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.liurx.jobmap.data.Company;
import org.liurx.jobmap.data.Coordinate;
import org.liurx.jobmap.param.GlobalParam;
import org.liurx.jobmap.util.CoordinateUtil;
import org.liurx.jobmap.util.FileManipulation;
import org.liurx.jobmap.util.HtmlParser;
import org.liurx.jobmap.util.HttpUtil;

import com.dawning.gridview.common.util.service.commonfunction.export.json.JsonUtil;

/**
 * 程序入口，本地测试执行main
 * 执行前提：
 * 1. json数据文件已下载到指定目录：/Users/lrx/Documents/my_workspace/companyData/，用resources/script/down.sh下载
 * 2. geotable表中为空，否则数据会重复
 * @class ExtractAndPost
 * @date 2016年1月14日 下午5:26:47
 * @author liurx
 * @since 4.0.0
 */
public class ExtractAndPost {
	public static void main(String[] args) {
		//取json数据，解析company id
		File[] srcFile = FileManipulation.listFile(GlobalParam.SRC_DIR + "json/json");
		
		DataBuffer.addToCompanyList(srcFile);
		List<Company> companyList = DataBuffer.getCompanyList();
		
		//根据id下载html页面，保存到指定目录 - 如果html都已经提前下好，就注释掉这一步，初次运行时这一步耗时最长
		FileManipulation.downloadHtml(companyList);
		//从html中提取经纬度、地址
		extractAddress(companyList);
		System.out.println("size: " + companyList.size());
		
//		FileManipulation.output(companyList, GlobalParam.TARGET_DIR + "summary");
		
		//保存到LBS云
		postData(companyList);
		//将没有坐标的输出到文件
		FileManipulation.output(DataBuffer.getTextList(), GlobalParam.TARGET_DIR + "text");
	}

	private static void postData(List<Company> companyList) {
		StringBuffer paramStr;
		for (Company company:companyList) {
			List<String> addrList = company.getAddressList();
			List<Coordinate> corList = company.getCoordinateList();
			//如果有多个地址，每个地址上传为一条记录
			if (addrList == null) {
				continue;
			}
			if (addrList.size() > 1) {
				for (int i=0; i< addrList.size(); i++) {
					String addr = addrList.get(i);
					Coordinate cor = corList.get(i);
					String latitude = cor.getLatitude();
					if (latitude == null || latitude.equals("") || !CoordinateUtil.isInBJ(cor)) {
						DataBuffer.addToTextList(company);
						continue;
					}
					paramStr = genParam(company, addr, cor);
					try {
						String result = HttpUtil.post(GlobalParam.CREATE_POI, paramStr.toString());
						
						if (!GlobalParam.SUCCESS.equals(JsonUtil.getMap4Json(result).get("status").toString())) {
							System.err.println("fail: " + result);
							System.out.println(paramStr);
							break;
						}
					} catch(Exception e) {
						e.printStackTrace();
						System.out.println("param: " + paramStr);
						break;
					}
					
//					paramStr.delete(0, paramStr.length());
				}
			} else if (addrList.size() == 1){
				String addr = addrList.get(0);
				Coordinate cor = corList.get(0);
				String latitude = cor.getLatitude();
				
				if (latitude == null || latitude.equals("") || !CoordinateUtil.isInBJ(cor)) {
					DataBuffer.addToTextList(company);
					continue;
				}
				paramStr = genParam(company, addr, cor);
				
				try {
					String result = HttpUtil.post(GlobalParam.CREATE_POI, paramStr.toString());
					if (!"0".equals(JsonUtil.getMap4Json(result).get("status").toString())) {
						System.err.println("post failed: " + result);
						System.out.println(paramStr);
						break;
					}
				} catch(Exception e) {
					e.printStackTrace();
					System.out.println("param: " + paramStr);
					break;
				}
//				paramStr.delete(0, paramStr.length());
			}
			try {
				System.out.println("post company: " + company.getId() + ", name: " + company.getName());
				Thread.sleep(20);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static StringBuffer genParam(Company company, String addr, Coordinate cor) {
		StringBuffer str = new StringBuffer();
		return str.append("title=").append(company.getShortName())
				.append("&address=").append(addr).append("&tags=").append(company.getId())
				.append("&latitude=").append(cor.getLatitude())
				.append("&longitude=").append(cor.getLongitude())
				.append("&coord_type=3")
				.append("&link=").append(GlobalParam.URL_PREFIX).append(company.getId()).append(".html")
				.append("&geotable_id=132060&ak=ABrY0GjICSfXogULBMBdq3uK");
	}

	private static void extractAddress(List<Company> companyList) {
		for (Company company:companyList) {
			String path = GlobalParam.TARGET_DIR + company.getId() + ".html";
//			FileManipulation.readFromFile(GlobalParam.TARGET_DIR + company.getId() + ".html");
			String companyInfo = HtmlParser.getData(path, GlobalParam.DATA_TAG);
			
			if (companyInfo == null) {
				continue;
			}
			Map infoMap = JsonUtil.getMap4Json(companyInfo.toString());
			if (infoMap == null) {
				continue;
			}
			Object locationStr = infoMap.get("location");
			String[] strArray = JsonUtil.getStringArray4Json(locationStr.toString());
			List<String> addressList = new ArrayList<String>();
			List<Coordinate> positionList = new ArrayList<Coordinate>(); 
			for (String str:strArray) {
				Map detailMap = JsonUtil.getMap4Json(str);
				try {
					if (detailMap != null) {
						
						addressList.add(detailMap.get("detailPosition").toString());
						Object latitude = detailMap.get("latitude");
						if (latitude != null) {
							positionList.add(new Coordinate(detailMap.get("longitude").toString(), latitude.toString()));
						} else {
							//没有坐标的，填充默认坐标，以免地址与坐标错位
							positionList.add(new Coordinate(GlobalParam.DEFAULT_LOGITUDE, GlobalParam.DEFAULT_LATITUDE));
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
					System.err.println("company: " + company);
					System.err.println("map: " + detailMap);
					return;
				}
			}
			company.setAddressList(addressList);
			company.setCoordinateList(positionList);
			
		}
		
	}

}
