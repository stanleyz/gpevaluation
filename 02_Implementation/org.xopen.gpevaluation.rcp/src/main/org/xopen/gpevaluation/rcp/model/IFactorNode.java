package org.xopen.gpevaluation.rcp.model;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;

public interface IFactorNode extends ISimpleNode {

	public List<IFactorNode> getChildren();

	public IFactorNode addChildren();

	public IFactorNode getParent();

	public void setFactorProperty(String _string, Object _newValue);

	public Factor getFactor();

	public FactorValues getFactorValue();

	public void setFactorValues(String realValue) throws ConfigurationException;
	
	public boolean calculateWeight();
	
	public void clearChildren();
	
	public void calculateRealWeight();
}
