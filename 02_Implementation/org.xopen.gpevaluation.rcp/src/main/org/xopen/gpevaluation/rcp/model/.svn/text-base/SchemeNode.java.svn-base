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
public class SchemeNode implements ISchemeNode {
	private IIndustryNode industry;
	private EvaluationScheme scheme;
	private List<IEvaluationNode> children;

	public SchemeNode(EvaluationScheme _scheme) {
		this(null, _scheme);
	}

	public SchemeNode(IIndustryNode _industry, EvaluationScheme _scheme) {
		this.industry = _industry;
		this.scheme = _scheme;
	}

	public List<IEvaluationNode> getChildren() {
		try {
			children = EvaluationSchemes.getInstance().getEvaluations(this);
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}
		return children;
	}

	public Image getImage() {
		return Activator.getDefault().getImageRegistry().get(
				Activator.SCHEME_ICON);
	}

	public String getName() {
		return scheme.getName();
	}

	public Object getParent() {
		return this.industry;
	}

	public boolean hasChildren() {
		if (children == null) {
			this.getChildren();
		}
		return children.size() > 0 ? true : false;
	}

	public EvaluationScheme getScheme() {
		return this.scheme;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		if (obj == this)
			return true;
		ISchemeNode node = (ISchemeNode) obj;
		if (this.getScheme().getId().equals(node.getScheme().getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.getScheme().getId().hashCode();
	}

	public void setAdaptedInstance(Object ai) {
		this.scheme = (EvaluationScheme) ai;
	}

	public void setParent(Object p) {
		this.industry = (IIndustryNode) p;
	}

	public Object getAdaptedInstance() {
		return this.scheme;
	}

	public void setName(String name) throws ConfigurationException {
		scheme.setName(name);
		EvaluationSchemes.getInstance().rename(this, name);
	}
}
