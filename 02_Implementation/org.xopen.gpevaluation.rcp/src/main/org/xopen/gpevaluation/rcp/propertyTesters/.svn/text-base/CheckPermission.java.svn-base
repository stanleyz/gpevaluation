/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.propertyTesters;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.core.expressions.PropertyTester;
import org.xopen.gpevaluation.rcp.model.Session;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class CheckPermission extends PropertyTester {

	/**
	 * 
	 */
	public CheckPermission() {
	}

	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		int level = (Integer) args[0];
		boolean b = false;
		try {
			b = Session.getInstance().checkPermission(level);
		} catch (ConfigurationException e) {
		}
		return b;
	}
}
