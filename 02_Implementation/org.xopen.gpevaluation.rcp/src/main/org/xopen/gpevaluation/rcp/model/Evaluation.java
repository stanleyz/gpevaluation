/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.model;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.configuration.Configuration;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class Evaluation extends SimpleModel {
	private String author;
	private String path;
	private String background;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public void firePropertyChange(String _s, Object newValue)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Configuration config = Evaluations.getInstance().getConfig(this.path);

		try {
			config.setProperty(_s, newValue);
		} catch (IllegalArgumentException e) {
			config.addProperty("/ " + _s, newValue);
		}
	}
}
