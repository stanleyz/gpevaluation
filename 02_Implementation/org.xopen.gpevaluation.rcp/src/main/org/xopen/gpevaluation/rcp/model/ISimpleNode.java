/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.model;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.swt.graphics.Image;

/**
 * This class is ...
 *
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public interface ISimpleNode {
	public String getName();

	public Image getImage();

	@SuppressWarnings("unchecked")
	public List getChildren();

	public boolean hasChildren();

	public Object getParent();
	
	public void setParent(Object _p);
	
	public void setAdaptedInstance(Object _ai);
	
	public Object getAdaptedInstance();
	
	public void setName(String _name) throws ConfigurationException;
}
