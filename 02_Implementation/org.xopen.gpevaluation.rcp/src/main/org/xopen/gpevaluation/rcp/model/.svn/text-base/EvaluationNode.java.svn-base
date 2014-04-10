/*
 * All Rights Reserved. 
 */
package org.xopen.gpevaluation.rcp.model;

import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.graphics.Image;
import org.xopen.gpevaluation.rcp.Activator;

/**
 * This class is ...
 * 
 * @author <a href="mailto:patent@nuaa.edu.cn">郭剑坤</a>
 */
public class EvaluationNode implements IEvaluationNode {
	private Evaluation eval;
	private ISchemeNode scheme;

	public EvaluationNode(ISchemeNode _scheme, Evaluation _eval) {
		this.scheme = _scheme;
		this.eval = _eval;
	}

	@SuppressWarnings("unchecked")
	public List getChildren() {
		return null;
	}

	public Image getImage() {
		return Activator.getDefault().getImageRegistry().get(
				Activator.EVALUATION_ICON);
	}

	public String getName() {
		return eval.getName();
	}

	public Object getParent() {
		return this.scheme;
	}

	public boolean hasChildren() {
		return false;
	}

	public Evaluation getEvaluation() {
		return this.eval;
	}

	@Override
	public boolean equals(Object obj) {
		if ((obj == null) || (getClass() != obj.getClass()))
			return false;
		if (obj == this)
			return true;
		IEvaluationNode node = (IEvaluationNode) obj;
		if (this.getEvaluation().getId().equals(node.getEvaluation().getId())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.getEvaluation().getId().hashCode();
	}

	public void setAdaptedInstance(Object ai) {
		this.eval = (Evaluation) ai;
	}

	public void setParent(Object p) {
		this.scheme = (ISchemeNode) p;
	}

	public Object getAdaptedInstance() {
		return this.eval;
	}

	@Override
	public String toString() {
		String[] scheme = { Messages.EvaluationNode_0, this.getName(),
				Messages.EvaluationNode_1, this.getEvaluation().getAuthor(), };
		return StringUtils.join(scheme, "\n");
	}

	public void setName(String name) throws ConfigurationException {
		eval.setName(name);
		EvaluationSchemes.getInstance().rename(this, name);
	}
}
