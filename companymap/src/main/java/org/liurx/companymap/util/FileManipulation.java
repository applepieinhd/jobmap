package org.liurx.companymap.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.liurx.companymap.data.Company;
import org.liurx.companymap.param.GlobalParam;

public class FileManipulation {
	public static File[] listFile(String dirPath) {
		File[] fileList = null;
		File dir = new File(dirPath);
		if (dir.isDirectory()) {
			fileList = dir.listFiles();
		}
		return fileList;
	}
	
	public static void downloadHtml(List<Company> companyList) {
		for (Company company:companyList) {
			try {
	//			System.out.println(companyMap.get("companyId") + ", name: " + companyMap.get("companyShortName"));
				String id = company.getId();
				
				String htmlFile = id + ".html";
				File html = new File (GlobalParam.TARGET_DIR + htmlFile);
				
				String companyPage = null;
				if (!html.exists()) {
					
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					String url = GlobalParam.URL_PREFIX + htmlFile;
					companyPage = HttpUtil.get(url);
					saveFile(companyPage, GlobalParam.TARGET_DIR + htmlFile);
				}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println("no page: " + company.getShortName() + ", id: " + company.getId());
			}
			
		}
		
		
		
//		output(companyList, TARGET_DIR + "summary");
	}
	
	public static String readFromFile(String name) {
		File file = new File(name);
		return readFromFile(file);
	}
	
	public static String readFromFile(File file) {
		if (!file.exists()) {
			return null;
		}
		
		FileReader reader = null;
		BufferedReader br = null;
		try {
			reader = new FileReader(file);
			br = new BufferedReader(reader);
			StringBuffer buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) !=null) {
				buffer.append(line);
			}
			
			return buffer.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}

	private static void saveFile(String content, String target) {
		FileWriter writer = null;
		File outFile = new File(target);
		
		try {
			writer = new FileWriter(outFile);
			writer.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void output(List<Company> list, String target) {
		String head = "id,shortName,address\n";
		
		FileWriter writer = null;
		File outFile = new File(target);
		try {
			writer = new FileWriter(outFile);
			writer.write(head);
			for (Company com:list) {
				writer.write(com.getId() + "," + com.getShortName() + "," + com.getAddressList() + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
