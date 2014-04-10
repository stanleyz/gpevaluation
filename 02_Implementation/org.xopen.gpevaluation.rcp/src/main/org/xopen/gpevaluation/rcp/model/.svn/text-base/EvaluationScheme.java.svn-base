package org.xopen.gpevaluation.rcp.model;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.StringUtils;

public class EvaluationScheme extends SimpleModel {
	private String author;

	/**
	 * The file path to evaluation system.
	 */
	private String path;
	/**
	 * Flag to indicate if the system is complete.
	 */
	private boolean completed;
	private long createTime;
	private String products;
	private String industry;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		String[] scheme = { Messages.EvaluationScheme_0, this.getName(), Messages.EvaluationScheme_1, this.getAuthor(), };
		return StringUtils.join(scheme, "\n");
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		if (obj == this)
			return true;
		EvaluationScheme scheme = (EvaluationScheme) obj;
		if (!scheme.getPath().equals(this.getPath()))
			return false;
		else
			return true;
	}

	@Override
	public int hashCode() {
		return this.getPath().hashCode();
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getProducts() {
		return products;
	}

	public void setProducts(String products) {
		this.products = products;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public void firePropertyChange(String _s, Object newValue)
			throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Configuration config = Factors.getInstance().getConfig(this.getPath());

		try {
			config.setProperty(_s, newValue);
		} catch (IllegalArgumentException e) {
			config.addProperty("/ " + _s, newValue);
		}
	}
}
