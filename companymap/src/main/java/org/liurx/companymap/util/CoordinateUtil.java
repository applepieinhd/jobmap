package org.liurx.companymap.util;

import org.liurx.companymap.data.Coordinate;
import org.liurx.companymap.param.GlobalParam;

public class CoordinateUtil {
	public static boolean isInBJ(Coordinate cor) {
		if ((cor.getLatitude().compareTo(GlobalParam.MIN_LATITUDE) < 0) || cor.getLatitude().compareTo(GlobalParam.MAX_LATITUDE) > 0) {
			return false;
		}
		if ((cor.getLongitude().compareTo(GlobalParam.MIN_LOGITUDE) < 0) || cor.getLongitude().compareTo(GlobalParam.MAX_LOGITUDE) > 0) {
			return false;
		}
		return true;
	}

}
