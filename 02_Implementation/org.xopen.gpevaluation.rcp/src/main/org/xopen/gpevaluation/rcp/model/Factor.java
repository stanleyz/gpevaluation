package org.xopen.gpevaluation.rcp.model;

import java.util.Properties;

public class Factor extends SimpleModel {
	private String fatherId;
	private String level;
	private String weight;
	private String realWeight;
	private Properties importances;

	// 以下三个属性只对当节点是终极节点时
	private String type;
	private String function;
	private String threshold;
	private Boolean removed;

	public String getFatherId() {
		return fatherId;
	}

	public void setFatherId(String fatherId) {
		this.fatherId = fatherId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public Properties getImportances() {
		return importances;
	}

	public void setImportances(Properties importances) {
		this.importances = importances;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public String getThreshold() {
		return threshold;
	}

	public void setThreshold(String threshold) {
		this.threshold = threshold;
	}

	public Boolean getRemoved() {
		return removed;
	}

	public void setRemoved(Boolean removed) {
		this.removed = removed;
	}

	/**
	 * @param dWeight
	 *            the dWeight to set
	 */
	public void setRealWeight(String realWeight) {
		this.realWeight = realWeight;
	}

	/**
	 * @return the dWeight
	 */
	public String getRealWeight() {
		return realWeight;
	}
}
