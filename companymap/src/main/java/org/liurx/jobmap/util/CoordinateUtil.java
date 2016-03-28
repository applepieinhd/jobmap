package org.liurx.jobmap.util;

import org.liurx.jobmap.data.Coordinate;
import org.liurx.jobmap.param.GlobalParam;

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
