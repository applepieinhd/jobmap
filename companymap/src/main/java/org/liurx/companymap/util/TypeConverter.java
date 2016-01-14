package org.liurx.companymap.util;

import java.util.Map;

import org.liurx.companymap.data.Company;

public class TypeConverter {
	public static Company map2Company(Map map) {
		try {
			Company company = new Company();
			company.setId(map.get("companyId").toString());
			company.setName(map.get("companyName").toString());
			company.setShortName(map.get("companyShortName").toString());
			return company;
		} catch(Exception e) {
			e.printStackTrace();
			System.err.println(map);
		}
		return null;
	}

}
