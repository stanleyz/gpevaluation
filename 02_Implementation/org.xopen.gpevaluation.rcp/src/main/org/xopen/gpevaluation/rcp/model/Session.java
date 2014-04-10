/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.model;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.xopen.gpevaluation.rcp.SystemConstants;
import org.xopen.gpevaluation.rcp.file.FileFactory;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class Session {
	private User user;
	private static Session session;
	private Configuration config;
	private boolean authenticated;

	private Session() throws ConfigurationException {
		if (config == null) {
			String filePath = SystemConstants.CONFIDIR
					+ SystemConstants.USERFILE;
			config = FileFactory.getXPathConfiguation(filePath);
		}
	}

	public static Session getInstance() throws ConfigurationException {
		if (session == null) {
			session = new Session();
		}
		return session;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void login() {
		StringBuilder builder = new StringBuilder("user[@name='");
		builder.append(user.getName());
		builder.append("']/");

		String password = config.getString(builder.toString() + "password");
		if (user.getPassword() != null && user.getPassword().equals(password)) {
			user.setFullName(config.getString(builder.toString() + "fullname"));
			user.setPermissions(Integer.valueOf(config.getString(builder
					.toString()
					+ "permissions")));

			authenticated = true;

			return;
		}

		authenticated = false;
	}

	public boolean isAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}

	public boolean checkPermission(int level) {
		return user.getPermissions() >= level;
	}
}
