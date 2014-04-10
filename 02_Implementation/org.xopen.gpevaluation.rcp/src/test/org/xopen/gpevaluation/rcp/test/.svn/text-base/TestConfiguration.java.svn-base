/*
 * Xopen, All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.ExpressionEngine;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

/**
 * This class is ...
 * 
 * @author <a href="mailto:phinux.zx.zhang@gmail.com">Phinux Zhang</a>
 */
public class TestConfiguration {
	public static void main(String[] args) throws ConfigurationException {
		String filePath = "resources/conf/evalschemes.xml";
		File file = new File(filePath);
		System.out.println(file.length());
		ExpressionEngine enginer = new XPathExpressionEngine();
		XMLConfiguration config = new XMLConfiguration(file);
		config.setExpressionEngine(enginer);

		InputStreamReader input = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(input);
		String a = "";
		try {
			a = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		config.addProperty("/ fuck", a);

		((XMLConfiguration) config).save();
		System.out.println(config.getString("//industry[scheme='1267429205984']"));
	}
}
