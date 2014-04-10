/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.model;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.eclipse.swt.graphics.Image;
import org.xopen.gpevaluation.rcp.Activator;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class IndustryNode implements IIndustryNode {
	private Industry industry;

	public IndustryNode(Industry _industry) {
		this.industry = _industry;
	}

	public List<ISchemeNode> getChildren() {
		try {
			return EvaluationSchemes.getInstance().getSchemes(this);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Image getImage() {
		return Activator.getDefault().getImageRegistry().get(
				Activator.INDUSTRY_ICON);
	}

	public String getName() {
		return industry.getName();
	}

	public Object getParent() {
		return null;
	}

	public boolean hasChildren() {
		return true;
	}

	public Industry getIndustry() {
		return this.industry;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		if (obj == this)
			return true;
		IIndustryNode node = (IIndustryNode) obj;
		if (this.getIndustry().getId().equals(node.getIndustry().getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.getIndustry().getId().hashCode();
	}

	public void setAdaptedInstance(Object ai) {
		this.industry = (Industry) ai;
	}

	public void setParent(Object p) {
	}

	public Object getAdaptedInstance() {
		return this.industry;
	}

	public void setName(String name) throws ConfigurationException {
		industry.setName(name);
		EvaluationSchemes.getInstance().rename(this, name);
	}
}
