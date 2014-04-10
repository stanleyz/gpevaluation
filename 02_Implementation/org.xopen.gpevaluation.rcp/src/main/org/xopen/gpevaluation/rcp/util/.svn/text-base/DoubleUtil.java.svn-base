/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.util;

import org.xopen.gpevaluation.rcp.SystemConstants;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class DoubleUtil {

	/**
	 * Check if the string could be converted to double value. <br/>
	 * This method is check by if the method
	 * <code>Double.parDouble(String)</code>throws NumberFormatException.
	 * 
	 * @param s
	 *            String
	 * @return boolean
	 */
	public static boolean isPositiveDouble(String s) {
		int i = s.indexOf("/");

		try {
			if (i > 0) {
				if (Double.parseDouble(s.substring(0, i)) <= 0
						|| Double.parseDouble(s.substring(i + 1)) <= 0)
					return false;
			} else {
				if (Double.parseDouble(s) <= 0)
					return false;
			}
		} catch (NumberFormatException e) {
			return false;
		}

		return true;
	}

	/**
	 * Get the double value from the string.
	 * 
	 * @param s
	 *            String
	 * @return double value
	 */
	public static double valueOf(String s) {
		if (s == null) {
			return Double.valueOf(SystemConstants.ROOT);
		}

		int i = s.indexOf("/");
		double d;

		if (i > 0) {
			double d1 = Double.parseDouble(s.substring(0, i));
			double d2 = Double.parseDouble(s.substring(i + 1));
			d = d1 / d2;
		} else {
			d = Double.parseDouble(s);
		}

		return d;
	}

	/**
	 * Get the reciprocal of the specified double-valued string. The parameter
	 * string must be a double value checked by the method
	 * <code>DoubleUtil.isDouble(String)</code> of this class.
	 * 
	 * @param s
	 *            Double-valued string
	 * @return reciprocal of the parameter
	 */
	public static String getReciprocal(String s) {
		int i = s.indexOf("/");
		int j = s.indexOf("0.");
		String d;

		if (i > 0) {
			String fraction = s.substring(0, i);
			String denominator = s.substring(i + 1);
			if (fraction.equals(SystemConstants.ONE)) {
				d = denominator;
			} else {
				d = denominator + "/" + fraction;
			}
		} else {
			if (j > -1) {
				double result = 1 / Double.parseDouble(s);
				d = String.valueOf(result);
			} else {
				d = "1/" + s;
			}
		}

		return d;
	}
}
