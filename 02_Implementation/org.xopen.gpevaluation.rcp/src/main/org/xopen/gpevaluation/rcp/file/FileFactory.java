package org.xopen.gpevaluation.rcp.file;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.tree.xpath.XPathExpressionEngine;

public class FileFactory {
	public static XMLConfiguration getXPathConfiguation(File file)
			throws ConfigurationException {
		XMLConfiguration config = new XMLConfiguration(file);
		config.setExpressionEngine(new XPathExpressionEngine());
		config.setEncoding("UTF-8");
		return config;
	}

	public static XMLConfiguration getXPathConfiguation(String filePath)
			throws ConfigurationException {
		return getXPathConfiguation(new File(filePath));
	}
}
