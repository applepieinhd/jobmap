package org.liurx.companymap.param;

public class GlobalParam {
	//百度API
	public static final String CREATE_POI = "http://api.map.baidu.com/geodata/v3/poi/create";
	public static final String DELETE_POI = "http://api.map.baidu.com/geodata/v3/poi/delete";
	
	public static final String SUCCESS = "0";
	public static final String BATCH_SUCCESS = "21";
	
	
	public static final String SRC_DIR = "/Users/lrx/Documents/my_workspace/companyData/";
	public static final String TARGET_DIR = "/Users/lrx/Documents/my_workspace/companyData/html/";
	public static final String URL_PREFIX = "http://www.lagou.com/gongsi/";
	public static final String DATA_TAG = "companyInfoData";
	public static final String DEFAULT_LATITUDE = "40.219279";//南邵坐标
	public static final String DEFAULT_LOGITUDE = "116.296879";//南邵坐标
	
	//选定的北京市区域
	public static final String  MIN_LATITUDE = "39.569621";
	public static final String  MAX_LATITUDE = "40.35006";
	public static final String  MIN_LOGITUDE = "115.703447";
	public static final String  MAX_LOGITUDE = "116.90387";
	

}
