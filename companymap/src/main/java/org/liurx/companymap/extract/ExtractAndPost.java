package org.liurx.companymap.extract;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.liurx.companymap.data.Company;
import org.liurx.companymap.data.Coordinate;
import org.liurx.companymap.logger.MapLogger;
import org.liurx.companymap.param.GlobalParam;
import org.liurx.companymap.util.CoordinateUtil;
import org.liurx.companymap.util.FileManipulation;
import org.liurx.companymap.util.HtmlParser;
import org.liurx.companymap.util.HttpUtil;
import org.liurx.companymap.util.JsonUtil;

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
		MapLogger.debug("size: " + companyList.size());
		for (Company company:companyList) {
			FileManipulation.downloadHtml(company);
			if (!FileManipulation.hasPost(company)) {
				//从html中提取经纬度、地址
				extractAddress(company);
				//保存到LBS云
				postData(company);
				
				FileManipulation.output(companyList, GlobalParam.TARGET_DIR + "summary");
				
				//将没有坐标的输出到文件
				FileManipulation.output(DataBuffer.getTextList(), GlobalParam.TARGET_DIR + "text");
			}
		}
	}

	private static void postData(Company company) {
		StringBuffer paramStr;
		List<String> addrList = company.getAddressList();
		List<Coordinate> corList = company.getCoordinateList();
		//如果有多个地址，每个地址上传为一条记录
		if (addrList == null) {
			return;
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
						MapLogger.error("fail: " + result);
						MapLogger.debug("param: " + paramStr);
						break;
					}
				} catch(Exception e) {
					e.printStackTrace();
					MapLogger.debug("param: " + paramStr);
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
				return;
			}
			paramStr = genParam(company, addr, cor);
			
			try {
				String result = HttpUtil.post(GlobalParam.CREATE_POI, paramStr.toString());
				if (!"0".equals(JsonUtil.getMap4Json(result).get("status").toString())) {
					MapLogger.error("post failed: " + result);
					MapLogger.debug("param: " + paramStr);
					return;
				}
			} catch(Exception e) {
				e.printStackTrace();
				MapLogger.debug("param: " + paramStr);
				return;
			}
//				paramStr.delete(0, paramStr.length());
		}
		try {
			MapLogger.debug("post company: " + company.getId() + ", name: " + company.getName());
			FileManipulation.recordPost(company);
			Thread.sleep(20);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	private static StringBuffer genParam(Company company, String addr, Coordinate cor) {
		StringBuffer str = new StringBuffer();
		return str.append("title=").append(company.getShortName())
				.append("&name=").append(company.getName())
				.append("&address=").append(addr)
				.append("&tags=").append(company.getId())
				.append("&latitude=").append(cor.getLatitude())
				.append("&longitude=").append(cor.getLongitude())
				.append("&coord_type=3")
				.append("&link=").append(GlobalParam.URL_PREFIX).append(company.getId()).append(".html")
				.append("&geotable_id=")
				.append(GlobalParam.LBS_TABLE_ID)
				.append("&ak=ABrY0GjICSfXogULBMBdq3uK");
	}

	private static void extractAddress(Company company) {
			String path = GlobalParam.TARGET_DIR + company.getId() + ".html";
//			FileManipulation.readFromFile(GlobalParam.TARGET_DIR + company.getId() + ".html");
			String companyInfo = HtmlParser.getData(path, GlobalParam.DATA_TAG);
			
			if (companyInfo == null) {
				return;
			}
			Map infoMap = JsonUtil.getMap4Json(companyInfo.toString());
			if (infoMap == null) {
				return;
			}
//			MapLogger.debug("infoMap: " + infoMap);
			List<Map> addrList = (List<Map>) infoMap.get("addressList");
			
			List<String> textAddrList = new ArrayList<String>();
			List<Coordinate> positionList = new ArrayList<Coordinate>(); 
			for (Map detailMap:addrList) {
				try {
					if (detailMap != null) {
						
						textAddrList.add(detailMap.get("detailAddress").toString());
						Object latitude = detailMap.get("lat");
						if (latitude != null) {
							positionList.add(new Coordinate(detailMap.get("lng").toString(), latitude.toString()));
						} else {
							//没有坐标的，填充默认坐标，以免地址与坐标错位
							positionList.add(new Coordinate(GlobalParam.DEFAULT_LOGITUDE, GlobalParam.DEFAULT_LATITUDE));
						}
					}
				} catch(Exception e) {
					e.printStackTrace();
					MapLogger.error("company: " + company);
					MapLogger.error("map: " + detailMap);
					return;
				}
			}
			company.setAddressList(textAddrList);
			company.setCoordinateList(positionList);
//			MapLogger.debug("position list: " + positionList);
			
	}

}
